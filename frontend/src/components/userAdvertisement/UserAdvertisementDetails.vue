<script>
import { ref, onMounted } from 'vue';

export default {
    
    setup() {

        //props = ['id'];
        const userAdvertisement = ref({ description: "Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga ", 
                                        minBudget: 123.4, maxBudget: 567.8, desiredLocation: "Street 123", entranceDate: "2021-12-12", 
                                        exitDate: "2022-12-12", maxCohabitants: 3, likes: 19,
                                        comments: [{user: "Usuario1", comment: "Comentario1"}, {user: "Usuario2", comment: "Comentario2"}, {user: "Usuario3", comment: "Comentario3"}, {user: "Usuario4", comment: "Comentario4"}, {user: "Usuario5", comment: "Comentario5"}, {user: "Usuario6", comment: "Comentario6"}, {user: "Usuario7", comment: "Comentario7"}, {user: "Usuario8", comment: "Comentario8"}, {user: "Usuario9", comment: "Comentario9"}, {user: "Usuario10", comment: "Comentario10"}]});
        const user = ref({ name: "Nombre de prueba", gender: "Masculino", photo: "https://via.placeholder.com/200", 
                        etiquetas: ["etiqueta1", "etiqueta2", "etiqueta3", "etiqueta4", "etiqueta5", "etiqueta6", "etiqueta7", "etiqueta8", "etiqueta9", "etiqueta10, etiqueta1", "etiqueta2", "etiqueta3", "etiqueta4", "etiqueta5", "etiqueta6", "etiqueta7", "etiqueta8", "etiqueta9", "etiqueta10"]})

        /*
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
        }
    }
}
</script>

<template>

    <Navbar />

    <div class="contenedor d-flex align-items-center justify-content-center text-center my-5">            
        <div class="panel">
            <div class="columna">
                <div class="subseccion">
                    <div class="rectangulo">
                        <div class="imagen-circulo">
                            <img src="https://via.placeholder.com/200" alt="Imagen de perfil">
                        </div>
                    </div>

                    <div class ="botones">
                        <div class="likes">
                            <i class="bi bi-heart" style="margin-top:2px; margin-right: 5px; color:#28426B"></i>
                            <span style="font-weight: bold; font-size: large; color:#28426B"> {{ userAdvertisement.likes }} </span>
                        </div>
                        <button type="button" class="boton"><strong>Comentar</strong></button>
                        <button type="button" class="boton"><strong>Iniciar chat <i class="bi bi-chat" style="margin-left: 5px;"></i></strong></button>
                    </div>
                </div>
                
                <div style="overflow-y: auto;" class="subseccion">
                    <h5 style="color:#5D5E60; text-align: left;">Comentarios</h5>
                    
                    <div v-for="comentario in userAdvertisement.comments" :key="comentario">
                        <div class="comentario"> {{ comentario.comment }} </div>
                   </div>
                </div>
            </div>

            <div class="columna">
                <div class="subseccion">
                    <h3 style="text-align: left;"> {{ user.name }}</h3>

                    <h5 style="color:#5D5E60; text-align: left;">Etiquetas</h5> 
                    <div style="display: inline-flex;" v-for="etiqueta in user.etiquetas" :key="etiqueta">
                        <span class="etiqueta"> {{ etiqueta }} </span>
                    </div>

                    <h5 style="color:#5D5E60; text-align: left;">Género</h5> 
                    <p style="text-align: left; font-weight: bold;">{{ user.gender }} </p>

                    <h5 style="text-align: left;">Descripción</h5>
                    <p style="text-align:left;">{{ userAdvertisement.description }}</p>
                </div>

                <div class="subseccion">2</div>
            </div>
        
        </div>
    </div>

</template>

<style scoped>

.contenedor {
    position: relative;
    display: flex;
    height: 86vh;
    width: 97%;
    margin: 1%;
    justify-content: center;
    align-items: center;
}

.panel {
    height: 100%;
    max-width: 100%;
    display: flex;
    flex: 1;
}

.columna {
    display: flex;
    height: auto;
    flex-direction: column;
    flex: 1;
    border: 1px solid #20b318;
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
    border: 1px solid #b31818; 
}

.rectangulo {
    position: relative;
    top: 0%;
    left: 15%;
    width: 70%;
    height: 80%; 
    background-color: #E2E2E2; 
    border-radius: 6%;
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1;
}

.imagen-circulo {
    position: relative;
    width: 200px; 
    height: 200px;
    overflow: hidden;
    border-radius: 50%;
    z-index: 2; /* Coloca la imagen por encima del rectángulo */
}

.imagen-circulo img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 50%;
}

.likes {
    display: inline-flex;
}
.botones {
    display: flex;
    justify-content: space-around;
    width: 80%;
    margin-top: 10px;
    margin-left: 60px;
    margin-right: 60px;
    align-items: center;
}

.boton {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-left: 5px; 
    background-color:#28426B;
    border-radius: 10px;
    width: 32%;
    height: 40px;
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
    height: 40px;
    color: black;
    border: 2px solid #859FC4;
}

</style>
