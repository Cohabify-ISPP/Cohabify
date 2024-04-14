<script>
import { ref, onBeforeMount, computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useStore } from 'vuex';

export default {


    setup() {

        const userAdvertisementId = ref(""); 
        const store = useStore();
        const errorComentario = ref(null);
        const modalText = ref('');
        const currentUser = computed(() => store.state.user);
        const userAdvertisement = ref({});
        const valorations = ref([]);
        const route = useRoute();
        const router = useRouter();
        const commonHouses = ref([]);
        const clipboardMessage = ref(false);
        const currentUserAdvertisementRating = ref({});
        const erroresComentario = ref(null);
        const commonFlats = ref([]);
        const chatError = ref("");

        const fetchAdvertisement = async () => {
            try {
                const response = await fetch(import.meta.env.VITE_BACKEND_URL + `/api/advertisements/users/${userAdvertisementId.value}`,
                    {
                        method: "GET",
                        headers: {
                            'Authentication': 'Bearer ' + localStorage.getItem("authentication"),
                        },
                        credentials: "include",
                    });

                    if (response.ok) {
                        const data = await response.json();
                        userAdvertisement.value = data;
                        await fetchValorations()
                    } else {
                        router.push(`/404`);
                    }
                    getCommonFlats();
                } catch (error) {
                console.error("Error:", error);
            }
        };

        const deleteComment = () => {
            fetch(import.meta.env.VITE_BACKEND_URL + '/api/userRating/' + currentUser.value.id + '/' + userAdvertisement.value.author.id, {
                method: 'DELETE',
                headers: {
                    'Authentication': 'Bearer ' + localStorage.getItem("authentication"),
                },
            })
                .then(response => {
                    currentUserAdvertisementRating.value = {};
                    fetchAdvertisement();
                    if (!response.ok) {
                        console.error('Error al eliminar el comentario');
                    }
                })
                .catch(error => console.error('Error al enviar datos al backend:', error));
        };

        const saveComment = () => {
            const formData = new FormData();
            formData.append("string-data", new Blob([JSON.stringify({
                user: currentUser.value,
                ratedUser: userAdvertisement.value.author,
                comment: text.value
            })], { type: "application/json" }));
            fetch(import.meta.env.VITE_BACKEND_URL + '/api/userRating', {
                method: 'POST',
                headers: {
                                'Authentication': 'Bearer ' + localStorage.getItem("authentication"),
                            },
                body: formData,
            })
            .then((response) => {
                if (response.ok) {
                fetchValorations();
                fetchAdvertisement();
                closeModal();
                } else {
                    response.json()
                    .then((body) => {
                        erroresComentario.value = body ? body : [{"message": "Ha ocurrido un error inesperado"}];
                    });
                }
            })
            .catch(error => {
                    console.error('Error al enviar datos al backend:', error);
                    errorComentario.value = 'No puedes ponerte una reseña a ti mismo.';
                });
        };

        const fetchValorations = async () => {
            try {
                const response = await fetch(import.meta.env.VITE_BACKEND_URL + `/api/userRating/ratedUser/${userAdvertisement.value.author.id}`,
                    {
                        method: "GET",
                        headers: {
                            'Authentication': 'Bearer ' + localStorage.getItem("authentication"),
                        },
                        credentials: "include",
                    });
                const data = await response.json();
                valorations.value = data;
                console.log(valorations.value);
                for(const rating of valorations.value){
                    if(rating.user.username === currentUser.value.username){
                        currentUserAdvertisementRating.value = rating;
                        break;
                    }
                }
            } catch (error) {
                console.error("Error:", error);
            }
        };
        const openModal = () => {
            modalText.value = "";
            let modal = document.getElementById('loginModal');
            modal.style.display = "block";
            erroresComentario.value = null;
        }

        const closeModal = () => {
            let modal = document.getElementById('loginModal');
            modal.style.display = "none";
        }

        const deleteUserAd = () => {
    
            fetch(import.meta.env.VITE_BACKEND_URL+'/api/advertisements/users/' + userAdvertisement.value.id, {
                method: "DELETE",
                headers: {
                    'Authentication': 'Bearer ' + localStorage.getItem("authentication"),
                },
                credentials: "include"
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error('No se ha podido eliminar el anuncio de usuario')
                    }
                })
                .then(data => {
                    router.push(`/`);
                })
                .catch(() => {
                    router.push(`/404`);
                })
        }

        const toggleLike = async () => {
  
            try {
                const response = await fetch(import.meta.env.VITE_BACKEND_URL + `/api/user/like/${userAdvertisement.value.author.id}`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authentication': 'Bearer ' + localStorage.getItem("authentication"),
                    },
                });

                if (response.ok) {
                    if (userAdvertisement.value.author.likes.some((like) => like.id === currentUser.value.id)) {
                        userAdvertisement.value.author.likes = userAdvertisement.value.author.likes.filter((like) => like.id !== currentUser.value.id);
                    } else {
                        userAdvertisement.value.author.likes.push(currentUser.value);
                    }
                }

            } catch (error) {
                console.error("Error:", error);
            }
        };

        const getCommonFlats = async () => {
            try {
                const response = await fetch(import.meta.env.VITE_BACKEND_URL + '/api/advertisements/houses/users/'+currentUser.value.id+'/ads/'+userAdvertisement.value.author.id,
                    {
                        method: "GET",
                        headers: {
                            'Authentication': 'Bearer ' + localStorage.getItem("authentication"),
                        },
                        credentials: "include",
                    });

                    if (response.ok) {
                        const data = await response.json();
                        console.log(data)
                        commonHouses.value = data;
                    } else {
                        router.push(`/404`);
                    }

                } catch (error) {
                console.error("Error:", error);
            }
        };
        

        function copyToClipboard() {
            navigator.clipboard.writeText(window.location.href)
                .then(function() {
                    clipboardMessage.value = true;
                    setTimeout(() => {
                        clipboardMessage.value = false;
                    }, 1500);
                })
                .catch(function(error) {
                    console.error("Error al copiar al portapapeles: ", error);
                });
        }

        onBeforeMount(() => {
            userAdvertisementId.value = route.params.id;
            fetchAdvertisement();
        });
        const promoteAd = (id)=>{
            fetch(import.meta.env.VITE_BACKEND_URL+'/api/advertisements/users/promote/' + id, {
                method: "POST",
                headers: {
                    'Authentication': 'Bearer ' + localStorage.getItem("authentication"),
                },
                credentials: "include"
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('No se ha podido promocionar el anuncio de usuario')
                }
            })
            .then(data => {
                fetchAdvertisement()
            })
            .catch(error => {
                fetchError.value = error
            })

        }

        function openChat() {
            fetch(import.meta.env.VITE_BACKEND_URL + '/api/chat/', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authentication': 'Bearer ' + localStorage.getItem("authentication"),
                },
                credentials: 'include',
                body: JSON.stringify({
                    users: [userAdvertisement.value.author],
                }),
            })
            .then(async response => {
                if (!response.ok) {
                    if(response.status == 409) {
                        chatError.value = "Ya posee un chat con esta persona";
                    } else {
                        throw new Error('No se ha podido crear el chat, código: ' + response.status);
                    }
                } else {
                    router.push("/chat");
                }
                
            })
            .catch(error => {
                console.error(error);
                chatError.value = "Ha ocurrido un error creando el chat.";
            })
    }

        return {
            errorComentario,
            modalText,
            closeModal,
            deleteComment,
            openModal,
            saveComment,
            userAdvertisement,
            commonHouses,
            getCommonFlats,
            toggleLike,
            copyToClipboard,
            clipboardMessage,
            valorations,
            deleteUserAd,
            currentUser,
            promoteAd,
            currentUserAdvertisementRating,
            erroresComentario,
            commonFlats,
            openChat,
            chatError,
        }
        
    }
    
}
</script>

<template>
    <Navbar />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css">
    <div class="container d-flex align-items-center justify-content-center text-center mt-5">            
        <div class="panel">
            <div class="columna">
                <div class="subseccion">
                    <div id="loginModal" class="modal">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <div class="col">
                                        <h5 class="modal-title">Nuevo comentario</h5>
                                    </div>
                                    <div class="col">
                                        <span @click="closeModal" class="success-checkmark" style="position: relative; align-items:center; margin: 1vh; padding: 1vh; float: right; cursor: pointer;">X</span>
                                    </div>
                                </div>
                                <div class="modal-body">
                                    <form @submit.prevent="saveComment">
                                        <div class="form-group">
                                            <label for="commentText">Comentario</label>
                                            <textarea class="form-control" id="text" v-model="modalText"></textarea>
                                        </div>
                                        <div class="mt-3 alert alert-danger" role="alert" style="padding-top: 20px;" v-if="erroresComentario !== null">
                                            <i class="fas fa-exclamation-triangle"></i> {{ erroresComentario.message }}
                                            
                                        </div>
                                        <button type="submit" class="button boton" style="position: relative; align-items:center; margin-top: 1vh; padding: 1vh; float: right;"><strong style="color:white">Enviar</strong></button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="imagen-circulo">
                        <img :src="userAdvertisement.author?.imageUri" alt = "Imagen de perfil">
                    </div>

                    <div class= "botones" style="margin-top: 3%;">
                        <div class="d-flex justify-content-center align-items-center">
                            <div class="likes" style="margin-right: 1vw;">
                                <button :class="{ 'like-button': true, 'no-clickable' : Object.keys(currentUser).length === 0 || currentUser && userAdvertisement.author?.id == currentUser?.id }" :disabled="Object.keys(currentUser).length === 0 || userAdvertisement.author?.id == currentUser?.id" @click="toggleLike">
                                    <i v-if="userAdvertisement.author?.likes.some((like) => like.id === currentUser.id)" class="bi bi-heart-fill" style="margin-top:2px; margin-right: 5px; color:#e87878" ></i>
                                    <i v-else class="bi bi-heart" style="margin-top:2px; margin-right: 5px; color:#28426B"></i>
                                </button>
                                 
                                <span style="font-weight: bold; font-size: large; color:#28426B"> {{ userAdvertisement.author?.likes.length }} </span>
                            </div>
                            
                            <button @click.prevent="openChat()" v-if="currentUser.id !== userAdvertisement.author?.id" type="button" class="button boton" style="text-wrap: nowrap; width:100%; margin-left: 1vw;"><strong style="color:white">Iniciar chat <i class="bi bi-chat" style="margin-left: 5px;"></i></strong></button>
                            <div class="d-flex col" v-else>
                                <button type="button" class="btn btn-primary" style="display: flex; align-items: center; justify-content: center; width: 100%; margin-left: 1vw;" @click="promoteAd(userAdvertisement.id)" v-if="userAdvertisement.promotionExpirationDate === null">
                                    <strong>Promocionar</strong>
                                </button>
                                <button type="button" class="btn btn-success" @click="$router.push(`/advertisements/users/myAdvertisement`)" style="display: flex; align-items: center; justify-content: center; width: 100%; margin-left: 1vw;"><strong>Editar</strong><span class="material-symbols-outlined" style="margin-left: 0.5rem;">edit</span></button>
                                <button type="button" class="btn btn-danger"  @click="deleteUserAd(userAdvertisementId)" style="display: flex; align-items: center; justify-content: center; width: 100%; margin-left: 1vw;"><strong>Eliminar</strong><span class="material-symbols-outlined" style="margin-left: 0.5rem;">delete</span></button>
                            </div>
                            <div class="mt-3 alert alert-danger" role="alert" style="padding-top: 20px;" v-if="currentUser.id !== userAdvertisement.author?.id && chatError != ''">
                                <i class="fas fa-exclamation-triangle"></i> {{ chatError }}
                            </div>
                        </div>
                    </div>
                </div>
                
                <div style="flex-basis: 30%;" class="subseccion">

                    <div class="card mb-2" style="padding: 10px;">   
                        <div class="card-body">
                            <h4 style="text-align: left;" class="card-title">Descripción</h4>
                            <hr>
                            <p v-if="userAdvertisement.description === ''" style="text-align: justify; word-wrap: break-word" class="card-text">Este usuario no ha establecido una descripción</p>
                            <p v-else style="text-align: justify; word-wrap: break-word" class="card-text">{{ userAdvertisement.description }}</p>
                        </div>
                    </div>

                    <div style="margin-top: 5%;"> 
                        <div class="d-flex justify-content-between">
                            <h4 style=" text-align: left;">Comentarios</h4>
                            <i class="fas fa-trash-alt" @click="deleteComment" 
                                style="cursor: pointer; width: 38px; height: 38px; border: 0.2em solid black; border-radius: 50%; padding: 0.5em; background-color: #f2f2f2;" v-if="currentUser.username && userAdvertisement.author?.username !== currentUser.username && Object.keys(currentUserAdvertisementRating).length !== 0">
                            </i>
                            <button type="button" @click="openModal" class="button boton" style="padding: 1vh;" 
                            v-if="currentUser.username && userAdvertisement.author?.username !== currentUser.username && Object.keys(currentUserAdvertisementRating).length === 0">
                                <strong style="color:white">Comentar</strong>
                            </button>
                        </div>
                        <hr>
                        
                        <div v-if="valorations.length == 0" style="text-align: left;">Aún no hay comentarios...</div>

                        <div v-else style="overflow-y: auto; max-height: 50vh;">
                            <div class="card card-user mb-3 mt-3 shadow-sm" style="padding: 10px;" v-if="Object.keys(currentUserAdvertisementRating).length !== 0"> 
                                <div class="card-body" >
                                    <p style="font-weight:bold; text-align: left;" class="card-title"><img class="rounded-circle" :src="currentUserAdvertisementRating.user.imageUri" style="width:3vw; height: 3vw; margin-right: 1vw;"/>{{ currentUserAdvertisementRating.user.username}}</p>
                                    <p style="text-align: justify; word-wrap: break-word" class="card-text">{{ currentUserAdvertisementRating.comment }}</p>
                                </div>
                            </div>
                            <div v-for="comentario in valorations" :key="comentario">
                                <div class="card card-user mb-3 mt-3 shadow-sm" style="padding: 10px;" v-if="comentario.user.username !== currentUser.username"> 
                                    <div class="card-body">
                                        <p style="font-weight:bold; text-align: left;" class="card-title"><img class="rounded-circle" :src="comentario.user.imageUri" style="width:3vw; height: 3vw; margin-right: 1vw;"/>{{ comentario.user.username}}</p>
                                        <p style="text-align: justify; word-wrap: break-word" class="card-text">{{ comentario.comment }}</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="columna" style="margin-left: 5%;" >
                <div class="subseccion">
                    <div style="display: flex; align-items: center; justify-content: space-between;">
                        <h1 style="text-align: left;"> {{ userAdvertisement.author?.username}} 
                            <img v-if="userAdvertisement.author?.plan === 'explorer'"style="max-height: 35px;" src="/images/verificado.png" loading="lazy"/>
                            <i :class="{'bi':true,'bi-gender-male': userAdvertisement.author?.gender == 'MASCULINO', 'bi-gender-female':userAdvertisement.author?.gender == 'FEMENINO', 'bi-gender-ambiguous': userAdvertisement.author?.gender == 'OTRO'}"></i>
                        </h1>
                        <button class="btn btn-share" @click="copyToClipboard()">
                            <i class="bi bi-share-fill" ></i>
                        </button>
                    </div>
                    <h1 style="text-align: left;"> {{ userAdvertisement.maxBudget }}€</h1>
                    <transition name="fade">
                        <div v-show="clipboardMessage" class="alert alert-success alert-dismissible fade show" role="alert">
                            Enlace copiado al portapapeles.
                        </div>
                    </transition>
 
                    <div style="margin-top: 5%;">
                        <h4 style=" text-align: left;">Detalles</h4>
                        <hr>
                        <h5 style="color: #5D5E60; text-align: left;"><i class="bi bi-geo-alt" style="margin-left: 5px;"></i> {{ userAdvertisement.desiredLocation }}</h5>
                        <h5 style="color: #5D5E60; text-align: left;"><i class="bi bi-calendar-week-fill" style="margin-left: 5px;"></i> {{ userAdvertisement.entranceDate }}<span v-if="userAdvertisement.exitDate != null"> a {{ userAdvertisement.exitDate }}</span> </h5>
                        <h5 style="color: #5D5E60; text-align: left;"> Máximo {{ userAdvertisement.maxCohabitants }} compañero(s)</h5> 
                    </div>

                    <div v-if="userAdvertisement.author?.tag.length === 0" style="text-align: left; margin-top: 5%;">
                        <h5 style="color: #5D5E60">Este usuario no tiene etiquetas establecidas</h5>
                    </div>

                    <div v-else style="margin-bottom: 1%; margin-top: 5%;">
                        <h4 style=" text-align: left;">Etiquetas</h4>
                        <hr>
                        <div style=" display: flex; flex-wrap:wrap; margin-right: 10%;">      
                            <div style="display: inline-flex; align-items: start; justify-content: start; align-content: start; justify-items:start;" v-for="etiqueta in userAdvertisement.author?.tag" :key="etiqueta">
                                <span class="badge etiqueta shadow" style="font-size: 105%; margin: 0.25vh 0.25vw;"> {{ etiqueta.tag }} </span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="subseccion" style="overflow-y: auto;">
                    <div>
                        <h4 style=" text-align: left;">Pisos con me gustas en común</h4>
                        <hr>
                    </div>
                        
                    <div v-for="anuncio in commonHouses" :key="anuncio">

                        <div class="piso">
                            <img  class="img-piso" :src="anuncio.images[0]" alt="Imagen del piso">

                            <div class="columna-informacion">
                                <p style="text-align: left; font-weight: bold; margin: 0%;"> {{ anuncio.title }}</p>

                                <div style="display:flex; justify-content: space-between; align-content: left; margin-right: 20px;">
                                    <span>{{ anuncio.price }} €/mes</span>
                                    <span>{{ anuncio.house.area }} m2</span>
                                    <span style="">Planta {{ anuncio.house.floor }}</span>
                                </div>
                                <p class="text-truncate" style="text-align: left; word-wrap: break-word; margin: 10px 0;">{{ anuncio.description }}</p>
                            </div>
                        </div>
                   </div>
                </div>
            </div>
        </div>
    </div>

</template>

<style scoped>

.panel {
    max-width: 100%;
    display: flex;
    flex: 1;
}

.columna {
    display: flex;
    height: auto;
    flex-direction: column;
    flex: 1;
    overflow-y: auto;
    overflow-x: hidden;
    margin-bottom: 1%;
}

.subseccion {
    flex: 1;
    align-self: center;
    padding: 10px;
    width: 95%;
    height: auto;
    align-self: center;
}

.imagen-circulo {
    position: relative;
    width: 200px; 
    height: 200px;
    overflow: hidden;
    border-radius: 50%;
    display: flex;
    justify-content: center;
    align-items: center;
    margin: auto;
}

.imagen-circulo img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 50%;
    display: block; 
}

.likes {
    display: inline-flex;
}

.botones {
    display: flex;
    justify-content: space-around;
    align-items: center;
}

.boton {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-left: 1%; 
    background-color:#28426B;
    border-radius: 10px;
    width: 27%;
    height: 5vh;
}

.boton strong {
    display: flex;
    align-items: center;
}

.btn-share {
    border-radius: 50%;
    width: 40px;
    height: 40px;
    display: flex;
    line-height: 40px;
    justify-content: center;
    align-items: center;
    background-color: #28426B;
    color: #FFFFFF; 
    border: none;
}

.btn-share:hover {
    background-color: #28426B;
    color: #FFFFFF; 
    border: none;
}

.piso {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-left: 7px; 
    margin-top: 5px;
    background-color:#859FC4;
    border-radius: 10px;
    width: 100%;
    height: 70%;
    color: black;
    border: 2px solid #859FC4;
}

.img-piso {
    width: 20%;
    height: 100px;
    object-fit: cover;
    border-radius: 5%;
    display: block;
    margin: 1% 1% 1% 1%;
}

.columna-informacion {
    flex: 2;
    padding: 1%;
    max-height: 20vh;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.fade-enter-active, .fade-leave-active {
  transition: opacity 0.5s;
}

.fade-enter, .fade-leave-to {
  opacity: 0;
}

.card-user {
    border-top: 1vh solid #28426B;
}

.etiqueta {
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 15px;
  border: 1px solid #28426B;
  background-color: #FFFFFF;
  color: #28426B;
}

.like-button {
    cursor: pointer; 
    background-color: rgba(0,0,0,0); 
    border-color: rgba(0,0,0,0); 
    padding: 0%
}

.no-clickable {
    cursor: not-allowed;
}

</style>
