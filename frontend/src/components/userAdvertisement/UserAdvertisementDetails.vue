<script>
import { ref, onMounted, computed } from 'vue';

export default {
    
    setup() {

        const user = ref({ id: "65e305e4597c9969f3b36f05", username: "Nombre de prueba", gender: "Masculino", img: "https://via.placeholder.com/200",  valorations: [{user: "id_usuario1", like: false, comment: "Comentario1"}, {user: "65e305e4597c9969f3b36f05", like: true, comment: null}, {user: "id_usuario3", like: false, comment: "Comentario3"}, {user: "id_usuario4",  like: true, comment: "Comentario4"}], 
                        tags: ["etiqueta1", "etiqueta2", "etiqueta3", "etiqueta4", "etiqueta5", "etiqueta6", "etiqueta7", "etiqueta8", "etiqueta9", "etiqueta10, etiqueta1"]})


        const userAdvertisement = ref({ description: "Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga ", 
                                        minBudget: 123.4, maxBudget: 567.8, desiredLocation: "Street 123", entranceDate: "2021-12-12", 
                                        exitDate: "2022-12-12", maxCohabitants: 3, author: user});
        
        const commonHouses = ref([{img: "https://via.placeholder.com/200", name:"Habitación de piso en calle Aderla", monthly_price: 123.4, size_m2:45, floor: "7th", description: "description description description description description description description description description description description description description description description description description description description"},
                                {img: "https://via.placeholder.com/200", name:"Habitación de piso en calle Cazorla", monthly_price: 124.4, size_m2:45, floor: "7th", description: "Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga  "},
                                {img: "https://via.placeholder.com/200", name:"Habitación de piso en calle Ibiza", monthly_price: 125.4, size_m2:45, floor: "7th", description: "description"},
                                {img: "https://via.placeholder.com/200", name:"Habitación de piso en calle Senderón", monthly_price: 126.4, size_m2:45, floor: "7th", description: "description"},
                                {img: "https://via.placeholder.com/200", name:"Habitación de piso en calle Bami", monthly_price: 127.4, size_m2:45, floor: "7th", description: "description"},
                                {img: "https://via.placeholder.com/200", name:"Habitación de piso en calle Calares", monthly_price: 128.4, size_m2:45, floor: "7th", description: "description"},
                                {img: "https://via.placeholder.com/200", name:"Habitación de piso en calle Web", monthly_price: 129.4, size_m2:45, floor: "7th", description: "description"}])
        

        const userAdvertisementId = "65e326184510d9c9a7c48173";
        const currentUser = user

        //props = ['id'];

        /*
        const fetchData = async () => {
            try {
                const response = await fetch(import.meta.env.VITE_API_URL + `/api/userAdvertisement/${id}`,
                    {
                        method: "GET",
                        credentials: "include",
                    });
                const data = await response.json();

                userAdvertisement.value = data;
            } catch (error) {
                console.error("Error:", error);
            }
        };
        */

        const likesCount = computed(() => {
            return user.value && user.value.valorations
                ? user.value.valorations.filter(valoration => valoration.like).length
                : 0;
        });

        const commentsCount = computed(() => {
            return user.value && user.value.valorations
                ? user.value.valorations.filter(valoration => valoration.comment !== null).length
                : 0;
        });

        const hasLike = computed(() => {
            return user.value.valorations.some(valoration => valoration.user === currentUser.value.id && valoration.like);
        });

        const toggleLike = async () => {
            try {
                const response = await fetch(import.meta.env.VITE_API_URL + `/api/addPositiveRater/${user.value.id}/${userAdvertisement.value.author.id}`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });

                if (response.ok) {
                    const likedIndex = user.value.valorations.findIndex(valoration => valoration.user === currentUser.value.id);
                    if (likedIndex !== -1) {
                        user.value.valorations[likedIndex].like = !user.value.valorations[likedIndex].like;
                    }
                }
            
            } catch (error) {
                console.error("Error:", error);
            }
        };

        return {
            user,
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
                        <img :src= user.img alt = "Imagen de perfil">
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
                        <div v-for="comentario in user.valorations" :key="comentario">
                            <div v-if="comentario.comment != null" class="comentario"> {{ comentario.comment }} </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="columna">
                <div class="subseccion">
                    <h3 style="text-align: left;"> {{ user.username }}</h3>

                    <br>

                    <div v-if="user.tags.length == 0" style="text-align: left;">Este usuario no tiene etiquetas establecidas</div>
                    <div v-else style="margin-bottom: 5px;">
                        <h5 style="color:#5D5E60; text-align: left;">Etiquetas</h5> 
                        <div style="display: inline-flex;" v-for="etiqueta in user.tags" :key="etiqueta">
                            <span class="etiqueta"> {{ etiqueta }} </span>
                        </div>
                    </div>

                    <h5 style="color:#5D5E60; text-align: left;">Género</h5> 
                    <p style="text-align: left; font-weight: bold; margin-bottom: 5px;">{{ user.gender }} </p>

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
    height: 100vh;
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
