<script>
import { ref, onMounted, onBeforeMount, computed, inject } from 'vue';
import { useRoute } from 'vue-router';

export default {
    
    setup() {

        const userAdvertisementId = ref(""); 
        const currentUser = inject('user');
        const userAdvertisement = ref({});
        const valorations = ref([]);
        const likesCount = ref(0)
        const commentsCount = ref(0)
        const hasLike = ref(false)
        const route = useRoute();
        const commonHouses = ref([]);

        const fetchAdvertisement = async () => {
            try {
                const response = await fetch(import.meta.env.VITE_BACKEND_URL + `/api/userAdvertisement/${userAdvertisementId.value}`,
                    {
                        method: "GET",
                        credentials: "include",
                    });
                const data = await response.json();
                
                userAdvertisement.value = data;
                await fetchValorations()
            } catch (error) {
                console.error("Error:", error);
            }
        };

        const fetchValorations = async () => {
            try {
                const response = await fetch(import.meta.env.VITE_BACKEND_URL + `/api/userRating/user/${userAdvertisement.value.author.id}`,
                    {
                        method: "GET",
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
                    },
                });

                if (response.ok) {
                    hasLike.value = !hasLike.value;
                }

            } catch (error) {
                console.error("Error:", error);
            }
        };

        onMounted(() => {
            userAdvertisementId.value = route.params.id;
            fetchAdvertisement();
        });
        
        return {
            currentUser,
            userAdvertisement,
            commonHouses,
            likesCount,
            commentsCount,
            hasLike,
            toggleLike,
        }
    }
}
</script>

<template>

    <Navbar />

    <div class="contenedor d-flex align-items-center justify-content-center text-center">            
        <div class="panel">
            <div class="columna">
                <div class="subseccion">
                    <div class="imagen-circulo">
                        <img :src="'https://img.freepik.com/foto-gratis/cerrar-lindo-gato-interior_23-2148882585.jpg'" alt = "Imagen de perfil">
                    </div>

                    <div class ="botones" style="margin-top: 3%;">
                        <div class="likes">
                            <div v-if="hasLike" @click="toggleLike" style="cursor:pointer">
                                <i class="bi bi-heart-fill" style="margin-top:2px; margin-right: 5px; color:#e87878"></i>
                            </div>
                            <div v-else>
                                <i class="bi bi-heart" style="margin-top:2px; margin-right: 5px; color:#28426B"></i>
                            </div>
                            
                            <span style="font-weight: bold; font-size: large; color:#28426B"> {{ likesCount }} </span>
                        </div>
                        <button type="button" class="boton"><strong>Comentar</strong></button>
                        <button type="button" class="boton"><strong>Iniciar chat <i class="bi bi-chat" style="margin-left: 5px;"></i></strong></button>
                    </div>
                </div>
                
                <div style="overflow-y: auto; flex-basis: 30%;" class="subseccion">

                    <h5 style="color:#5D5E60; text-align: left;">Descripción</h5>
                    <p style="text-align: left; word-wrap: break-word; margin-bottom: 5px;">{{ userAdvertisement.description }}</p>

                    <h5 style="color:#5D5E60; text-align: left;">Comentarios</h5>
                
                    <div v-if="commentsCount == 0" style="text-align: left;">Aún no hay comentarios...</div>

                    <div v-else>
                        <div v-for="comentario in userAdvertisement.valorations" :key="comentario">
                            <div v-if="comentario.comment != null" class="comentario"> {{ comentario.comment }} </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="columna">
                <div class="subseccion">
                    <h3 style="text-align: left;"> {{ userAdvertisement.author?.username  }}</h3>

                    <br>

                    <div v-if="userAdvertisement.author?.tag.length === 0" style="text-align: left;">
                        Este usuario no tiene etiquetas establecidas
                    </div>
                    <div v-else style="margin-bottom: 5px;">
                        <h5 style="color:#5D5E60; text-align: left;">Etiquetas</h5> 
                        <div style="display:flex; justify-content: start; align-items:start;">
                            <div style="display: inline-flex;" v-for="etiqueta in userAdvertisement.author?.tag" :key="etiqueta">
                                <span class="etiqueta"> {{ etiqueta.tag }} </span>
                            </div>
                        </div>
                    </div>
 
                    <h5 style="color:#5D5E60; text-align: left;">Género</h5> 
                    <p style="text-align: left; font-weight: bold; margin-bottom: 5px;">{{  userAdvertisement.author?.gender?userAdvertisement.author.gender:"Otro" }} </p>

                    <h5 style="color:#5D5E60; text-align: left;">Presupuesto máximo</h5> 
                    <p style="text-align: left; font-weight: bold; margin-bottom: 5px;">{{ userAdvertisement.maxBudget }}€</p>

                    <h5 style="color:#5D5E60; text-align: left;">Localización deseada</h5> 
                    <p style="text-align: left; font-weight: bold; margin-bottom: 5px;">{{ userAdvertisement.desiredLocation }}</p>

                    <h5 style="color:#5D5E60; text-align: left;">Fecha de alojamiento</h5>
                        <div style="display: flex; align-items: flex-start;">
                            <p style="font-weight: bold; margin-bottom: 5px;"> De {{ userAdvertisement.entranceDate }}</p> 
                            <div v-if="userAdvertisement.exitDate != null"> 
                                <p style="font-weight: bold; margin-left: 4px; margin-bottom: 5px;"> a {{ userAdvertisement.exitDate }}</p>
                            </div>
                        </div>
                </div>

                <div class="subseccion" style="overflow-y: auto;">
                    <h5 style="color:#5D5E60; text-align: left;">Pisos promocionados cercanos</h5>

                    <p style="text-align: left;"> Próximamente...</p>
                        
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

.contenedor {
    position: relative;
    display: flex;
    height: auto;
    width: 97%;
    margin: 0.5%;
    justify-content: center;
    align-items: center;
}

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
    width: 80%;
    margin-top: 1%;
    margin-left: 10%;
    margin-right: 10%;
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

.etiqueta {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-left: 7px; 
    margin-top: 5px;
    background-color:#28426B;
    border-radius: 10px;
    width: auto;
    height: 40px;
    color: white;
    border: 2px solid #28426B;
}

.comentario {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-left: 7px; 
    margin-top: 5px;
    background-color:#859FC4;
    border-radius: 10px;
    width: auto;
    height: auto;
    min-height: 40px;
    color: black;
    border: 2px solid #859FC4;
    word-wrap: break-word;
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

</style>
