<script>
import { ref, onMounted } from 'vue';

export default {
    
    setup() {

        const userAdvertisement = ref({ description: "Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga ", 
                                        minBudget: 123.4, maxBudget: 567.8, desiredLocation: "Street 123", entranceDate: "2021-12-12", 
                                        exitDate: "2022-12-12", maxCohabitants: 3, likes: 19,
                                        comments: [{user: "Usuario1", comment: "Comentario1"}, {user: "Usuario2", comment: "Comentario2"}, {user: "Usuario3", comment: "Comentario3"}, {user: "Usuario4", comment: "Comentario4"}, {user: "Usuario5", comment: "Comentario5"}, {user: "Usuario6", comment: "Comentario6"}, {user: "Usuario7", comment: "Comentario7"}, {user: "Usuario8", comment: "Comentario8"}, {user: "Usuario9", comment: "Comentario9"}, {user: "Usuario10", comment: "Comentario10"}]});
        
        const commonHouses = ref([{img: "https://via.placeholder.com/200", name:"Habitación de piso en calle Aderla", monthly_price: 123.4, size_m2:45, floor: "7th", description: "description description description description description description description description description description description description description description description description description description description"},
                                {img: "https://via.placeholder.com/200", name:"Habitación de piso en calle Cazorla", monthly_price: 124.4, size_m2:45, floor: "7th", description: "Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga  "},
                                {img: "https://via.placeholder.com/200", name:"Habitación de piso en calle Ibiza", monthly_price: 125.4, size_m2:45, floor: "7th", description: "description"},
                                {img: "https://via.placeholder.com/200", name:"Habitación de piso en calle Senderón", monthly_price: 126.4, size_m2:45, floor: "7th", description: "description"},
                                {img: "https://via.placeholder.com/200", name:"Habitación de piso en calle Bami", monthly_price: 127.4, size_m2:45, floor: "7th", description: "description"},
                                {img: "https://via.placeholder.com/200", name:"Habitación de piso en calle Calares", monthly_price: 128.4, size_m2:45, floor: "7th", description: "description"},
                                {img: "https://via.placeholder.com/200", name:"Habitación de piso en calle Web", monthly_price: 129.4, size_m2:45, floor: "7th", description: "description"}])
        
        const user = ref({ name: "Nombre de prueba", gender: "Masculino", photo: "https://via.placeholder.com/200", 
                        etiquetas: ["etiqueta1", "etiqueta2", "etiqueta3", "etiqueta4", "etiqueta5", "etiqueta6", "etiqueta7", "etiqueta8", "etiqueta9", "etiqueta10, etiqueta1", "etiqueta2", "etiqueta3", "etiqueta4", "etiqueta5", "etiqueta6", "etiqueta7", "etiqueta8", "etiqueta9", "etiqueta10"]})

        //TODO: Fetch data from API

        /*

        props = ['id'];

        const fetchData = async () => {
            try {
                const response = await fetch(import.meta.env.VITE_API_URL + `/userAdvertisment/${id}`,
                    {
                        method: "GET",
                        credentials: "include",
                    });
                const data = await response.json();

                data.entranceDate = new Date(data.entranceDate);
                data.exitDate = new Date(data.exitDate);
                userAdvertisement.value = data;
            } catch (error) {
                console.error("Error:", error);
            }
        };
        */
        
        return {
            user,
            userAdvertisement,
            commonHouses,
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
                        <img :src= user.photo alt = "Imagen de perfil">
                    </div>

                    <div class ="botones" style="margin-top: 3%;">
                        <div class="likes">
                            <i class="bi bi-heart" style="margin-top:2px; margin-right: 5px; color:#28426B"></i>
                            <span style="font-weight: bold; font-size: large; color:#28426B"> {{ userAdvertisement.likes }} </span>
                        </div>
                        <button type="button" class="boton"><strong>Comentar</strong></button>
                        <button type="button" class="boton"><strong>Iniciar chat <i class="bi bi-chat" style="margin-left: 5px;"></i></strong></button>
                    </div>
                </div>
                
                <div style="overflow-y: auto; flex-basis: 30%;" class="subseccion">
                    <h5 style="color:#5D5E60; text-align: left;">Comentarios</h5>
                    
                    <div v-for="comentario in userAdvertisement.comments" :key="comentario">
                        <div class="comentario"> {{ comentario.comment }} </div>
                   </div>
                </div>
            </div>

            <div class="columna">
                <div class="subseccion">
                    <h3 style="text-align: left;"> {{ user.name }}</h3>

                    <br>

                    <h5 style="color:#5D5E60; text-align: left;">Etiquetas</h5> 
                    <div style="display: inline-flex;" v-for="etiqueta in user.etiquetas" :key="etiqueta">
                        <span class="etiqueta"> {{ etiqueta }} </span>
                    </div>

                    <h5 style="color:#5D5E60; text-align: left;">Género</h5> 
                    <p style="text-align: left; font-weight: bold;">{{ user.gender }} </p>

                    <h5 style="text-align: left;">Descripción</h5>
                    <p style="text-align: left; word-wrap: break-word">{{ userAdvertisement.description }}</p>
                </div>

                <div class="subseccion" style="overflow-y: auto;">
                    <h5 style="color:#5D5E60; text-align: left;">Pisos en común</h5>
                    
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
    margin: 1%;
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
    margin-bottom: 5px;
}

.subseccion {
    flex: 1;
    align-self: center;
    padding: 10px;
    width: 95%;
    height: auto;
    margin: 1%;
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
