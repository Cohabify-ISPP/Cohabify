<script>
import { ref, onMounted } from 'vue';

export default {
    
    setup() {

        const houseAdvertisement = ref({ title:  "Nombre de prueba", price:"0€", description: "Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga ", 
        tenants:"2", images: []});
        
        const house = ref({ location:"Zona desconocida", rooms:"20", bathrooms:"1",
            area:"10", heating:"Central", floor:"1",  etiquetas: ["No fumadores"]})

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
            house: house,
            houseAdvertisement: houseAdvertisement,
        }
    }
}
</script>
<template>

    <Navbar />

    <div class="contenedor d-flex align-items-center justify-content-center text-center">            
        <div class="panel">
            <div class="columna" style="overflow-y: hidden;">
                <div class="subseccion">
                    <div id="carouselExampleIndicators" class="carousel slide">
                        <div class="carousel-indicators">
                            <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                            <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
                            <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
                        </div>
                        <div class="carousel-inner">
                            <div class="carousel-item active">
                            <img src="https://media.istockphoto.com/id/1407271723/es/foto/gradiente-fondo-gris.jpg?s=612x612&w=0&k=20&c=Vbs_dZswYrvagaSRqLpVsWkGNH14tvNFlBSt4ZntW_I=" class="rounded d-block w-100" alt="First image">
                            </div>
                            <div class="carousel-item">
                            <img src="https://media.istockphoto.com/id/1407271723/es/foto/gradiente-fondo-gris.jpg?s=612x612&w=0&k=20&c=Vbs_dZswYrvagaSRqLpVsWkGNH14tvNFlBSt4ZntW_I=" class="rounded d-block w-100" alt="...">
                            </div>
                            <div class="carousel-item">
                            <img src="https://media.istockphoto.com/id/1407271723/es/foto/gradiente-fondo-gris.jpg?s=612x612&w=0&k=20&c=Vbs_dZswYrvagaSRqLpVsWkGNH14tvNFlBSt4ZntW_I=" class="rounded d-block w-100" alt="...">
                            </div>
                        </div>
                        <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span class="visually-hidden">Previous</span>
                        </button>
                        <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                            <span class="visually-hidden">Next</span>
                        </button>
                        </div>

                    </div>    
                    
                <div style=" vertical-align: top;" class="d-flex justify-content-center">
                    <div style="padding-left: 5%;">
                        <div style="color:#5D5E60;display: flex; align-items: left; padding-left: 10%;">
                            <h5 style=" text-align: left;">{{ house.location }}</h5> 
                            <i class="bi bi-geo-alt" style="margin-left: 5px;"></i>
                        </div>
                        <img src="https://motor.elpais.com/wp-content/uploads/2022/01/google-maps-22-1046x616.jpg" style="max-width:80%; max-height:80%;">
                    </div> 
                </div>    
            </div>

            <div class="columna">
                <div class="subseccion">
                    <div style="display: flex; align-items: center; justify-content: space-between;">
                        <h1 style="text-align: left;"> {{ houseAdvertisement.title }}</h1>
                        <button class="btn btn-share">
                            <i class="bi bi-share-fill" ></i>
                        </button>
                    </div>
                    <h1 style="text-align: left;"> {{ houseAdvertisement.price }}</h1>
                    
                    <br>
                    <div >
                        <h4 style=" text-align: left;">Detalles</h4> 
                        <hr>
                        <div style="color:black;display: flex; align-items: left;">
                            <div style="display: grid;margin-right: 10%;">
                                <h5>Dormitorios</h5>
                                <h5 style="color:#5D5E60;text-align: center;">{{ house.rooms }}</h5>
                            </div>
                            <div style=" display: grid;margin-right: 10%;">
                                <h5>Baños</h5>
                                <h5 style="color:#5D5E60;text-align: center;">{{ house.bathrooms }}</h5>
                            </div>
                            <div style=" display: grid;margin-right: 10%;">
                                <h5>Metros cuadrados</h5>
                                <h5 style="color:#5D5E60;text-align: center;">{{ house.area }}</h5>
                            </div>
                        </div>
                        
                        <div style="color:black;display: flex; align-items: left;">
                            <div style="display: grid;margin-right: 10%;">
                                <h5>Calefacción</h5>
                                <h5 style="color: #5D5E60; text-align: center;">{{ house.heating }}</h5>
                            </div>
                            <div style="display: grid;margin-right: 10%;">
                                <h5>Plantas</h5>
                                <h5 style="color:#5D5E60;text-align: center;">{{ house.floor }}</h5>
                            </div>
                            
                        </div>
                    </div>
                    <br>
                    <div>
                        <h4 style=" text-align: left;">Inquilinos</h4> 
                        <hr>
                        <div style="display: flex">
                            <div style=" display: grid;margin-right: 10%;">
                                    <h5 style="color: #5D5E60;">Número</h5>
                                    <h5 style="color:black;text-align: center;">{{ houseAdvertisement.tenants }}</h5>
                            </div>
                            <div style=" display: grid;margin-right: 10%;">
                                <h5 style="color: #5D5E60;">Etiquetas</h5>
                                <div style="display: inline-flex;" v-for="etiqueta in house.etiquetas" :key="etiqueta">
                                    <span class="badge etiqueta" style="font-size: 105%;"> {{ etiqueta }} </span>
                                </div>
                            </div>
                        </div>
                        
                    </div>
                    <br>
                    <h4 style="text-align: left;">Descripción</h4>
                    <p style="text-align: justify; word-wrap: break-word">{{ houseAdvertisement.description }}</p>
                </div>
                <div class="subseccion" style="text-align: right;">
                    <div class ="botones" style="left-down: 3%;">
                        <button type="button" class="boton"><strong>Iniciar chat <i class="bi bi-chat" style="margin-left: 5px;"></i></strong></button>
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
    color: #FFFFFF;
    border-radius: 15px;
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
    background-color: #2de63967;
    color: #04481c;
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
    color: #FFFFFF; /* Cambia el color del icono a blanco */
    border: none;
}

.btn-share:hover {
    background-color: #28426B;
    color: #FFFFFF; /* Cambia el color del icono a blanco */
    border: none;
}
</style>
