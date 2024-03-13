<script>
import { ref, onMounted, onBeforeMount, computed, inject } from 'vue';
import { useRoute } from 'vue-router';

export default {
    
    setup() {

        const userAdvertisementId = ref(""); 
        const currentUser = inject('user');
        const userAdvertisement = ref({});
        const valorations = ref([]);
        const hasLike = ref(false)
        const route = useRoute();
        const commonHouses = ref([]);
        const clipboardMessage = ref(false);

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
                        window.location.href = "/404";
                    }

                } catch (error) {
                console.error("Error:", error);
            }
        };

        const fetchValorations = async () => {
            try {
                const response = await fetch(import.meta.env.VITE_BACKEND_URL + `/api/userRating/user/${userAdvertisement.value.author.id}`,
                    {
                        method: "GET",
                        headers: {
                            'Authentication': 'Bearer ' + localStorage.getItem("authentication"),
                        },
                        credentials: "include",
                    });
                const data = await response.json();
                valorations.value = data;
            } catch (error) {
                console.error("Error:", error);
            }
        };

        const toggleLike = async () => {
  
            try {
                const response = await fetch(import.meta.env.VITE_BACKEND_URL + `/api/user/like/${userAdvertisement.value.id}/${currentUser.value.id}`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authentication': 'Bearer ' + localStorage.getItem("authentication"),
                    },
                });

                if (response.ok) {
                    hasLike.value = !hasLike.value;
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
        
        return {
            currentUser,
            userAdvertisement,
            commonHouses,
            hasLike,
            toggleLike,
            copyToClipboard,
            clipboardMessage,
            valorations,
        }
    }
}
</script>

<template>

    <Navbar />

    <div class="container d-flex align-items-center justify-content-center text-center mt-5">            
        <div class="panel">
            <div class="columna">
                <div class="subseccion">
                    <div class="imagen-circulo">
                        <img :src="userAdvertisement.author.imageUri" alt = "Imagen de perfil">
                    </div>

                    <div class= "botones" style="margin-top: 3%;">
                        <div class="d-flex justify-content-center align-items-center">
                        <div class="likes" style="margin-right: 1vw;">
                            <div v-if="hasLike" @click="toggleLike" style="cursor:pointer">
                                <i class="bi bi-heart-fill" style="margin-top:2px; margin-right: 5px; color:#e87878"></i>
                            </div>
                            <div v-else>
                                <i class="bi bi-heart" style="margin-top:2px; margin-right: 5px; color:#28426B"></i>
                            </div>
                            
                            <span style="font-weight: bold; font-size: large; color:#28426B"> {{ userAdvertisement.author.likes.length }} </span>
                        </div>
                        <button type="button" class="button boton" style="text-wrap: nowrap; width:100%; margin-left: 1vw;"><strong>Iniciar chat <i class="bi bi-chat" style="margin-left: 5px;"></i></strong></button>
                    </div>
                    </div>
                </div>
                
                <div style="flex-basis: 30%;" class="subseccion">

                    <div class="card mb-2 shadow" style="padding: 10px;">   
                        <div class="card-body">
                            <h4 style="text-align: left;" class="card-title">Descripción</h4>
                            <hr>
                            <p style="text-align: justify; word-wrap: break-word" class="card-text">{{ userAdvertisement.description }}</p>
                        </div>
                    </div>  

                    <div style="margin-top: 5%;"> 
                        <div style="margin-top: 5;">
                            <div class="d-flex justify-content-between">
                                <h4 style=" text-align: left;">Comentarios</h4>
                                <button type="button" class="button boton" style="padding: 1vh;"><strong>Comentar</strong></button>
                            </div>
                            <hr>
                        </div>
                        <div v-if="valorations.length == 0" style="text-align: left;">Aún no hay comentarios...</div>

                        <div v-else style="overflow-y: auto; max-height: 50vh;">
                            <div v-for="comentario in valorations" :key="comentario">
                                <div class="card card-user mb-3 mt-3 shadow-sm" style="padding: 10px;"> 
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
                        <h1 style="text-align: left;"> {{ userAdvertisement.author?.username}} <i :class="{'bi':true,'bi-gender-male': userAdvertisement.author?.gender == 'MASCULINO', 'bi-gender-female':userAdvertisement.author?.gender == 'FEMENINO', 'bi-gender-ambiguous': userAdvertisement.author?.gender == 'OTRO'}"></i></h1>
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
                        <h4 style=" text-align: left;">Pisos en común</h4>
                        <hr>
                    </div>

                    <h5 style="text-align: left;color: #5D5E60"> Próximamente...</h5>
                        
                    <div v-for="anuncio in commonHouses" :key="anuncio">

                        <div class="piso">
                            <img  class="img-piso" :src="anuncio.img" alt="Imagen del piso">

                            <div class="columna-informacion">
                                <p style="text-align: left; font-weight: bold; margin: 0%;"> {{ anuncio.name }}</p>

                                <div style="display:flex; justify-content: space-between; align-content: left; margin-right: 20px;">
                                    <span>{{ anuncio.monthly_price }}€/mes</span>
                                    <span>{{ anuncio.size_m2 }}m2</span>
                                    <span style="">{{ anuncio.floor }}</span>
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
    height: 25%;
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

</style>
