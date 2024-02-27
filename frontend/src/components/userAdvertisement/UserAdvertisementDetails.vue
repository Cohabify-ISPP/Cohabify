<script>
import { ref, onMounted } from 'vue';

export default {
    
    setup() {

        //props = ['id'];
        const userAdvertisement = ref({ description: "Desc de prueba", minBudget: 123.4, maxBudget: 567.8, desiredLocation: "Street 123", entranceDate: "2021-12-12", exitDate: "2022-12-12", maxCohabitants: 3, likes: 19});
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
            userAdvertisement,
        }
    }
}
</script>

<template>

    <Navbar />

    <div class="contenedor d-flex align-items-center justify-content-center text-center my-5">
        <div class="logo">
            <h2>Aquí logo</h2>
        </div>
            
        <div class="panel">
            <div class="fila">
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
                
                <div class="subseccion">


                </div>
            </div>
            <div class="fila">
                <div class="subseccion">3</div>
                <div class="subseccion">4</div>
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

.logo {
    width: 15%;
    align-self: self-start;
}

.panel {
    height: 100%;
    display: flex;
    flex: 1;
    flex-direction: column;
}

.fila {
    display: flex;
    flex: 1;
    border: 1px solid #20b318;
}

.subseccion {
    flex: 1;
    align-self: center;
    padding: 10px;
    width: 95%;
    height: 95%;
    margin: 1%;
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
    width: 145px;
    height: 40px
}

.boton strong {
    display: flex;
    align-items: center;
}

</style>
