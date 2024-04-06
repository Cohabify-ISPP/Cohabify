<template>
  <Navbar />
  <div class="row g-0 text-center">
    <div class="col-6 col-md-4">
      <div class = 'container'>
        <div class="contacts-list mt-5">
          <h5 class="text-white">Contactos</h5>
          <ul style="max-height: 70vh; overflow-y: auto;">
            <div v-for="(tenant, index) in houseAdvertisement.tenants" :key="index">
                <div class="card mb-3 mt-3" style="padding: 10px; background-color: #e1e9f7; height: 30%;">
                    <div class="card-body">
                        <div class="d-flex">
                            <div class="mx-2">
                                <img :src="tenant.image" style="border-radius: 50%; width: 10vh; height: 10vh;">
                            </div>
                            <div class="flex-column overflow-auto" style="height:10vh; padding-right: 5px">
                                <div class ="d-flex" style="margin-bottom: 5px;">
                                    <div class="card-body d-flex align-items-center">
                                      <h5 style="text-align: left;" class="card-title">{{ tenant.username }}</h5>
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
            <div class="card mb-3 mt-5" style="padding: 10px; background-color: #28426bc2; height: 72%; width: 98%; ">
                    <div class="card-body">
                        <div class="d-flex">
                            <div class="mx-2">
                                <img :src="tenant.image" style="border-radius: 50%; width: 10vh; height: 10vh;">
                            </div>
                            <div class="flex-column overflow-auto" style="height:10vh; padding-right: 5px">
                                <div class ="d-flex" style="margin-bottom: 5px;">
                                    <div class="card-body d-flex align-items-center">
                                      <h5 style="text-align: left;" class="card-title text-white">{{ tenant.username }}</h5>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>  
                </div>
          </div>

          <div class="messages-area" ref="messagesArea">
                  
            <div class="message one card c1" >
                <div class="username">Jhon Doe</div>
                <p style="padding: 15px;">Hola, soy Jhon Doe.</p>
                <div class="date">06/04/2024 - 14:30</div>
            </div>
            <div class="message two card c2">
                <div class="username">Tú</div>
                <p style="padding: 15px;">Hola Jhon Doe, ¡yo también busco piso! </p>
                <div class="date">06/04/2024 - 14:30</div>
            </div>
            <div class="message one card c1" >
                <div class="username">Jhon Doe</div>
                <p style="padding: 15px;">Hola, estoy en búsqueda de un nuevo hogar en el área céntrica de la ciudad. Mi situación actual requiere una reubicación rápida y estoy emocionado por encontrar el lugar perfecto para establecerme. Necesito un apartamento con al menos dos habitaciones, ya que trabajo desde casa y necesito espacio adicional para mi oficina. Mi presupuesto mensual máximo es de $1000, pero estoy dispuesto a considerar ofertas un poco más altas si el lugar cumple con mis requisitos y ofrece comodidades adicionales como estacionamiento, acceso a áreas comunes o una ubicación conveniente cerca de transporte público y tiendas. Estoy buscando un contrato de alquiler a largo plazo, preferiblemente de un año o más, para establecer una sensación de estabilidad en mi vida y en mi trabajo. Además, me gustaría encontrar un lugar que acepte mascotas, ya que tengo un gato que es parte importante de mi vida y mi familia. Si tienes alguna propiedad disponible que pueda ser adecuada para mí, por favor házmelo saber lo antes posible. Estoy dispuesto a hacer una visita virtual o programar una cita para ver el lugar en persona si es necesario. Aprecio cualquier ayuda que puedas brindarme en mi búsqueda de un nuevo hogar. ¡Gracias de antemano por tu tiempo y consideración!</p>
                <div class="date">06/04/2024 - 14:30</div>
            </div>

          </div>
          <div class="sender-area">
            <div class="input-place">
              <input placeholder="Send a message." class="send-input" type="text" maxlength="1500">
                <div class="send">
                  <i class="bi bi-send-fill"></i>
              </div>
            </div>
          </div>
        </div>
    </div>
  </div>

</template>

<script>
import { ref, onMounted, onUpdated, nextTick } from 'vue'

export default {
  setup() {
    // Usuarios de ejemplo
    const houseAdvertisement = ref({
      tenants: [
        {
          username: 'John Doe',
          genre: 'MASCULINO',
          description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
          image: 'https://images.pexels.com/photos/1222271/pexels-photo-1222271.jpeg?cs=srgb&dl=pexels-justin-shaifer-1222271.jpg&fm=jpg'
        },
        {
          username: 'Jane Smith',
          genre: 'FEMENINO',
          description: 'Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas.',
          image: 'https://secrecyjewels.es/blog/wp-content/uploads/2022/10/esencia-de-una-persona.jpg'
        },
      ]
    })

    const tenant = ref({
      username: 'John Doe',
      genre: 'MASCULINO',
      description: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',
      image: 'https://images.pexels.com/photos/1222271/pexels-photo-1222271.jpeg?cs=srgb&dl=pexels-justin-shaifer-1222271.jpg&fm=jpg'
    })

    const messagesArea = ref(null)

    onMounted(scrollToBottom)
    onUpdated(scrollToBottom)

    function scrollToBottom() {
      nextTick(() => {
        messagesArea.value.scrollTop = messagesArea.value.scrollHeight
      })
    }

    return {
      houseAdvertisement,
      tenant,
      messagesArea
    }
  },
}
</script>

<style scoped>
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
  height: 60vh;
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

}

.message {
  margin-top: 10px;
  margin-bottom: 5px;
}

.username {
  font-weight: bold;
  padding: 15px;
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


</style>