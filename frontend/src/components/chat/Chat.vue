<template>
  <Navbar />
  <div class="row g-0 text-center">
    <div class="col-6 col-md-4">
      <div class = 'container'>
        <div class="contacts-list mt-5">
          <h5 class="text-white">Contactos</h5>
          <ul style="max-height: 70vh; overflow-y: auto;">
            <div v-for="chat in chats">
                <div class="card mb-3 mt-3" style="height: 100px; padding: 10px; background-color: #e1e9f7;" @click="selectChat(chat)">
                    <div class="chat-card-body">
                        <div class="flex-container" style="height: 100%; width: auto;">
                            <div class="image-container" v-if="chat.users?.length == 1">
                                <img :src="chat.users[0].imageUri" class="contacts-profile-image">
                            </div>
                            <div class="image-container" v-if="chat.users?.length > 1">
                                <img :src="chat.users[0].imageUri" class="contacts-profile-image">
                                <img :src="chat.users[1].imageUri" class="contacts-profile-image offset">
                                <div v-if="chat.users?.length > 2">
                                  <span class="offset2" style="font-weight: bolder; font-size: larger; color:black; text-align: end;"> +{{ chat.users?.length-2 }} </span>
                                </div>
                            </div>
                            <div class="flex-column overflow-auto" style="padding-right: 5px; max-width: 70%; max-height: 100%;">
                                <div class ="d-flex" style="margin-bottom: 5px;">
                                    <div class="chat-card-body d-flex align-items-center">
                                      <h5 style="text-align: left;" class="card-title">{{ contactsChatMembers(chat) }}</h5>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>  
                </div>
            </div>
          </ul>
        </div>
      </div>
    </div>
    <div class="col-sm-6 col-md-8">
      <div class="container">
          <div>
            <div class="card mb-3 mt-5" style="padding: 10px; background-color: #28426bc2; height: 65%; width: 98%; ">
                    <div class="card-body">
                      <div class="flex-container" style="height: 100%; width: auto;" v-if="selectedChat != null && selectedChat != undefined">
                            <div class="image-container" v-if="selectedChat.users?.length == 1">
                                <img :src="selectedChat.users[0].imageUri" class="chat-profile-image">
                            </div>
                            <div class="image-container" v-if="selectedChat.users?.length > 1">
                                <img :src="selectedChat.users[0].imageUri" class="chat-profile-image">
                                <img :src="selectedChat.users[1].imageUri" class="chat-profile-image offset4">
                                <div v-if="selectedChat.users?.length > 2">
                              <span class="chat-profile-image offset3" style="font-weight: bolder; font-size: larger; color:white"> +{{ selectedChat.users?.length-2 }} </span>
                            </div>
                            </div>
                            
                            <div class="flex-column overflow-auto" style="padding-right: 5px; max-width: 70%; max-height: 100%;">
                                <div class ="d-flex" style="margin-bottom: 5px;">
                                    <div class="card-body d-flex align-items-center">
                                      <h5 style="text-align: left;color: white;" class="card-title">{{ chatMembers(selectedChat) }}</h5>
                                    </div>
                                    <div class="card-body d-flex align-items-center" v-if="selectedChat.isChatOwned">
                                      <button @click.prevent="deleteChat" type="button" class="button boton-cancelar"
                                              style="text-wrap: nowrap; width:100%;">
                                        <strong>Borrar chat <i class="bi bi-x" style="margin-left: 5px;"></i></strong></button>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>  
                </div>
          </div>

          <div class="messages-area" ref="messagesArea">
            <div v-for="message in selectedChat?.messages" :class="['message', 'one', 'card', message.sender.username === currentUser.username ? 'c2' : 'c1']">
              <div :class="['username', message.sender.username === currentUser.username ? 'u2' : 'u1']">{{ message.sender.username }}</div>
              <span style="padding-left: 15px;">{{ message.text }}</span>
              <div class="date">{{ formatDate(message.timeSent) }}</div>
            </div>
          </div>
          <div class="sender-area">
            <div class="input-place">
              <input :disabled="!selectedChat" v-model="messageInput" placeholder="Escribe un mensaje..." @keyup.enter="sendMessage" class="send-input" type="text" maxlength="1500">
                <div class="send" @click="sendMessage()">
                  <i class="bi bi-send-fill"></i>
              </div>
            </div>
          </div>
        </div>
    </div>
  </div>

</template>

<script>
import { ref, computed, onMounted, onUpdated, nextTick,  } from 'vue';
import { useStore } from 'vuex';
import { CohabifyStompClient } from "../../utils/stomp.js";

export default {
  setup() {
    const store = useStore()
    const currentUser = computed(() => store.state.user);

    const updateMeta = (title, description) => {
            document.querySelector('meta[name="description"]').setAttribute('content', description);
            document.querySelector('meta[property="og:title"]').setAttribute('content', title);
            document.querySelector('meta[property="og:description"]').setAttribute('content', description);
            };

    onMounted(() => {
      updateMeta('Panel de Chat - Cohabify: Conecta y Comunícate Sin Interrupciones', 'Accede a tu panel de chat en Cohabify para interactuar con contactos, ver mensajes y mantenerte conectado. Simplifica la comunicación con una interfaz de chat intuitiva diseñada para mejorar tus interacciones.');
    });

    const chats = ref([])

    const chatId = ref(null);
    const selectedChat = ref(null);
    const chatMembers = (chat) => {
      return chat.users.map(u => u.username).join(", ");
    }

    const contactsChatMembers = (chat) => {
      if(chat.users.length <= 2) {
        return chat.users.map(u => u.username).join(", ");
      }else{
        return chat.users.slice(0,2).map(u => u.username).join(", ")+"...";
    
      }
    }

    const messagesArea = ref(null);
    const messageInput = ref("");
    const stompClient = new CohabifyStompClient();
    let subscriptions = [];

    const formatDate = (date) => {
      const options = { year: 'numeric', month: 'long', day: 'numeric', hour: 'numeric', minute: 'numeric' };
      return new Date(date).toLocaleDateString('es-ES', options);
    }

    onMounted(() => {
      fetch(import.meta.env.VITE_BACKEND_URL + '/api/chat/', {
          method: "GET",
          headers: {
              'Authentication': 'Bearer ' + localStorage.getItem("authentication"),
          },
          credentials: "include"
      })
      .then(async response => {
        if (!response.ok) {
            throw new Error('No se han podido cargar los chats, código: ' + response.status);
        }

        chats.value = await response.json();
      })
      .catch(error => {
        console.error(error);
        addSystemMessage("Ha ocurrido un error cargando los chats.");
      })
    })

    const selectChat = (chat) => {
      selectedChat.value = chat;
      selectedChat.value.isChatOwned = chat.openedBy.id == currentUser.value.id;

      subscribe();
    }

    async function subscribe() {
        await subscriptions.forEach(async subscription => {
          await subscription.unsubscribe(subscription);
        });

        subscriptions.push(await stompClient.subscribe('/chat/'+ selectedChat.value.id, (ChatMessage) => {
          addMessage(JSON.parse(ChatMessage.body));
        }));
    }

    function getCurrentTimeFormatted() {
      const now = new Date();
      const day = String(now.getDate()).padStart(2, '0');
      const month = String(now.getMonth() + 1).padStart(2, '0'); // Months are 0-based in JavaScript
      const year = now.getFullYear();
      const hours = String(now.getHours()).padStart(2, '0');
      const minutes = String(now.getMinutes()).padStart(2, '0');

      return `${day}-${month}-${year} ${hours}:${minutes}`;
    }

    function sendMessage() {
      if(selectedChat.value != null && selectedChat.value != undefined && 
        messageInput.value != null && messageInput.value != undefined && messageInput.value.replace(/\s/g, '') != "") {
        try {
            stompClient.send("/chat-msgs/" + selectedChat.value.id, {}, JSON.stringify({'msg': messageInput.value}));
            messageInput.value = "";
        } catch(error) {
          console.error(error);
          addSystemMessage("El servicio aún no se ha conectado, espere un poco o, si ya lo ha hecho, recargue la página.")
        }
      }
    }

    function addMessage(message) {
      if(!selectedChat.value) {
        selectedChat.value = {"users": [{"username": "Sistema"}], "messages": []};
      }
      selectedChat.value.messages.push(message);
      scrollToBottom();
    }

    function addSystemMessage(text) {
      addMessage({
          "sender": "Sistema",
          "timeSent": getCurrentTimeFormatted(),
          "text": text
        });
    } 

    onMounted(scrollToBottom)
    onUpdated(scrollToBottom)

    function scrollToBottom() {
      nextTick(() => {
        messagesArea.value.scrollTop = messagesArea.value.scrollHeight
      })
    }

    const deleteChat = async () => {
      if(await confirmDeleteChat()) {
        fetch(import.meta.env.VITE_BACKEND_URL + "/api/chat/" + selectedChat.value.id, {
          method: "DELETE",
          headers: {
            Authentication: "Bearer " + localStorage.getItem("authentication"),
          },
          credentials: "include",
        })
        .then((response) => {
          if (!response.ok) {
            throw new Error("No se ha podido eliminar el chat");
          }
        })
        .then((data) => {
          location.reload()
        })
        .catch((error) => {
          addSystemMessage("No se ha podido eliminar el chat")
        });
      }
    }

    const confirmDeleteChat = async () => {
        var response = confirm("¿Estás seguro de que quieres borrar este chat?");
        return response;
    }

    return {
      currentUser,
      chats,
      chatMembers,
      contactsChatMembers,
      selectChat,
      selectedChat,
      messagesArea,
      messageInput,
      sendMessage,
      formatDate,
      deleteChat,
    }
  },
}
</script>

<style scoped>
.boton-cancelar {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-left: 1%;
    background-color:#b32432;
    color: #FFFFFF;
    border-radius: 10px;
    width: 27%;
    height: 5vh;
}

.boton-cancelar:hover{
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
    transition: box-shadow 0.2s ease;
}

.container {
 
  display: flex;
  flex-direction: column;
  height: 80vh;
  width: 100%;
}

.nav-bar {
  width: 100%;
  height: 50px;
  background-color: none;
  display: flex;
  align-items: center;
  color:azure;
  justify-content: space-between;
}

.nav-bar a {
  color: #4950af;
  white-space: nowrap;
  margin-left: 10px;
  user-select: none;
}



.line {
  position: absolute;
  width: 20px;
  height: 3px;
  background-color: #acd5e7;
  border-radius: 30px;
}

.line.one {
  transform: rotate(45deg)
}

.line.two {
  transform: rotate(135deg)
}

.messages-area {
  border-radius: 10px;
  background-color: #e1e9f7;
  width: 98%;
  height: 100%;
  max-height: 60vh; 
  overflow-y: auto;
  border: 1px solid #35385896;

}

.sender-area {
  
  width: 98%;
  height: 70px;
  display: flex;
  border-radius: 8px;
}

.date{
  font-size: 12px;
  color: #b6cdef;
  text-align: right;
  margin-top: 5px;

}

.message {
  margin-top: 10px;
  margin-bottom: 5px;
}

.username {
  font-weight: bold;
  padding: 5px;
  font-size: 15px;
  color: #b6cdef;
  margin-bottom: 5px;
}

.message.one{
  
  color: white;
  text-align: left;
  
  
}

.message.two {
 
  color: white;
  text-align: right;
  
}

.send-img {
  width: 30px;
}

.send-input {
  outline: none;
  display: flex;
  border: none;
  background: none;
  height: 40px;
  width: 96%;
  border-radius: 7px;
  background: none;
  color: rgb(17, 22, 43);
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
  margin-left: 5px;
}

.send-input::placeholder {
  color: #515b68;
}

.input-place {
  display: flex;
  flex-direction: row;
  margin-top: 15px;
  
  align-items: center;
  background-color: #e1e9f7;
  border-radius: 7px;
  height: 50px;
  width:100%;
  gap: 5px;
  border: 1px solid #35385896;
}

.send {
  width: 70px;
  height: 70px;
  background-color: none;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  color:#253972;
}



.contacts-list {
  margin-top: 5px;
  width: 100%;
  height: 80vh;
  background-color: #28426bc2;
  color: white;
  border-radius: 10px;
  padding: 20px;
}

.contacts-list ul {
  list-style-type: none;
  padding: 0;
}

.contacts-list li {
  margin-bottom: 10px;
  cursor: pointer;
}

.c1 {
  border-radius: 25px; 
  margin-left: 10px;
  padding: 10px; 
  width: 70%; 
  border:none;
  background-color: #4a5e96; 
  white-space: pre-wrap; 
  overflow-wrap: break-word;
  box-shadow: 0 3px 4px rgba(0, 0, 0, 0.219);
}

.c2 {
  border-radius: 25px; 
  margin-right: 10px; 
  padding: 10px; 
  width: 70%; 
  border:none;
  background-color: #2f3852; 
  margin-left: auto;  
  white-space: pre-wrap; 
  overflow-wrap: break-word;
  box-shadow: 0 3px 4px rgba(0, 0, 0, 0.219);
}

.chat-card-body {
  height: 100%;
  color: var(--bs-card-color);
  padding: 0px;
}

.flex-container {
 display: flex;
 justify-content: space-between; 
 align-items: center; 
 padding: 4px 10px;
 height: 100%;
 position: relative; 
}


.image-container {
 height: 90%;
}

.chat-profile-image {
  border-radius: 50%;
  height: 10vh;
  width: 10vh;
  max-width: 20%;
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
}

.contacts-profile-image {
  border-radius: 50%;
  height: 8vh;
  width: 8vh;
  max-width: 20%;
  position: absolute;
}


.offset {
  transform: translate(50%, 0);
}
.offset2 {
  transform: translate(80%, 0);
  height: 8vh;
  width: 8vh;
  max-width: 20%;
  overflow: hidden;
  position: absolute;
}
.offset3 {
  transform: translate(125%, -50%);
}
.offset4 {
  transform: translate(65%, -50%);
}
.offset5 {
  transform: translate(65%, -50%);
}


.u1{
  color: white;
}

</style>