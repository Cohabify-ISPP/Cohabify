<script>
import { ref, onMounted } from 'vue';

export default {
    
    setup() {
        const tenants = ref([{name: "Nombre de prueba", gender: "Hombre", photo: "https://via.placeholder.com/200", description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                        etiquetas: ["etiqueta1", "etiqueta2", "etiqueta3", "etiqueta4", "etiqueta5", "etiqueta6", "etiqueta7", "etiqueta8", "etiqueta9", "etiqueta10, etiqueta1", "etiqueta2", "etiqueta3", "etiqueta4", "etiqueta5", "etiqueta6", "etiqueta7", "etiqueta8", "etiqueta9", "etiqueta10"]}])

        const houseAdvertisement = ref({ title:  "Nombre de prueba", price:"0€", description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", 
        tenants: tenants, images: []});
        
        const house = ref({ location:"Zona desconocida", rooms:"20", bathrooms:"1",
            area:"10", heating:"Central", floor:"1",  etiquetas: ["No fumadores"]})

        const selectedImage = ref(false)

        const clipboardMessage = ref(false);

        const selectImage = (index) => {
            selectedImage.value = index;
        };

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
        
        onMounted(() => {
            document.getElementById('imgCarousel').addEventListener('slid.bs.carousel', function (e) {
            const activeSlideIndex = Array.from(e.target.querySelectorAll('.carousel-item')).findIndex(item => item.classList.contains('active'));
            selectedImage.value = activeSlideIndex;
        });
        });
        
        return {
            tenants,
            house,
            houseAdvertisement,
            selectImage,
            selectedImage,
            copyToClipboard,
            clipboardMessage
        }
    }
}
</script>
<template>

    <Navbar />

    <div class="container">
        <div class="row mt-5">
            <div class="col col-6 justify-content-center align-items-center">
                <div class="container" >
                    <div id="imgCarousel" class="carousel slide mb-4">
                        <!-- Imagen superior --> 
                        <div class="carousel-inner" >
                            <!-- <div class="carousel-item active" v-for="image in houseAdvertisement.images" :key="image">
                                <img style="object-fit: cover;" src="/images/user.png" class="img-fluid" alt="...">
                            </div> -->
                            <div class="carousel-item active">
                                <img style="object-fit: cover;" src="/images/user.png" class="img-fluid" alt="...">
                            </div>
                            <div class="carousel-item">
                                <img style="object-fit: cover;" src="/images/explorador.png" class="img-fluid" alt="...">
                            </div>
                            <div class="carousel-item">
                                <img style="object-fit: cover;" src="/images/LogoCohabifyCuadrado.png" class="img-fluid" alt="...">
                            </div>
                        </div>
                        <!-- Botones de control -->
                        <button class="carousel-control-prev" type="button" data-bs-target="#imgCarousel" data-bs-slide="prev">
                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span class="visually-hidden">Previous</span>
                        </button>
                        <button class="carousel-control-next" type="button" data-bs-target="#imgCarousel" data-bs-slide="next">
                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                            <span class="visually-hidden">Next</span>
                        </button>
                    </div>
                </div>
                <!-- Imágenes debajo -->
                <div class="container w-75">
                    <div class="row align-items-center">
                        <!-- <div class="col-md-4 mb-4" v-for="(image,index) in houseAdvertisement.images" :key="image">
                            <img :src="image" class="d-block w-100" alt="..." data-bs-target="#imgCarousel" :data-bs-slide-to="index" :@click="selectImage(index)" :class="{'shadow': selectedImage === index }">
                        </div> -->
                        <div class="col-md-4 mb-4">
                            <img src="/images/user.png" class="d-block w-100" alt="..." data-bs-target="#imgCarousel" data-bs-slide-to="0" @click="selectImage(0)" :class="{'shadow': selectedImage === 0 }">
                        </div>
                        <div class="col-md-4 mb-4">
                            <img src="/images/explorador.png" class="d-block w-100" alt="..." data-bs-target="#imgCarousel" @click="selectImage(1)" data-bs-slide-to="1" :class="{'shadow': selectedImage === 1 }">
                        </div>
                        <div class="col-md-4 mb-4">
                            <img src="/images/LogoCohabifyCuadrado.png" class="d-block w-100" alt="..." data-bs-target="#imgCarousel" data-bs-slide-to="2" @click="selectImage(2)" :class="{'shadow': selectedImage === 2 }">
                        </div>
                    </div>
                </div>
            </div>    
            <div class="col" style="padding-left: 5%; padding-right: 5%;">
                    <div style="display: flex; align-items: center; justify-content: space-between;">
                        <h1 style="text-align: left;"> {{ houseAdvertisement.title }}</h1>
                        <button class="btn btn-share" @click="copyToClipboard()">
                            <i class="bi bi-share-fill" ></i>
                        </button>
                    </div>
                    <h1 style="text-align: left;"> {{ houseAdvertisement.price }}</h1>
                    <transition name="fade">
                        <div v-show="clipboardMessage" class="alert alert-success alert-dismissible fade show" role="alert">
                            Enlace copiado al portapapeles.
                        </div>
                    </transition>
                    <br>
                    <div >
                        <h4 style=" text-align: left;">Detalles</h4> 
                        <hr>
                        <div style="color:black;display: flex; align-items: left;" class="justify-content-between">
                            <div style="display: grid;margin-right: 10%;">
                                <span class="material-icons" style="font-size: xx-large">bed</span>
                                <h5 style="color:#5D5E60;text-align: center;">{{ house.rooms }}</h5>
                            </div>
                            <div style=" display: grid;margin-right: 10%;">
                                <span class="material-icons" style="font-size: xx-large">shower</span>
                                <h5 style="color:#5D5E60;text-align: center;">{{ house.bathrooms }}</h5>
                            </div>
                            <div style=" display: grid;margin-right: 10%;">
                                <span class="material-icons" style="font-size: xx-large">square_foot</span>
                                <h5 style="color:#5D5E60;text-align: center;">{{ house.area }} m<sup>2</sup></h5>
                            </div>
                            <div style="display: grid;margin-right: 10%;">
                                <span class="material-symbols-outlined" style="font-size: xx-large">mode_fan</span>
                                <h5 style="color: #5D5E60; text-align: center;">{{ house.heating }}</h5>
                            </div>
                            <div style="display: grid;margin-right: 10%;">
                                <span class="material-symbols-outlined" style="font-size: xx-large">floor</span>
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
                                    <h5 style="color:black;text-align: center;">{{ houseAdvertisement.tenants.length }}</h5>
                            </div>
                            <div style=" display: grid;margin-right: 10%;">
                                <h5 style="color: #5D5E60;">Etiquetas</h5>
                                <div style="display: inline-flex;" v-for="etiqueta in house.etiquetas" :key="etiqueta">
                                    <span class="badge etiqueta" style="font-size: 105%;"> {{ etiqueta }} </span>
                                </div>
                            </div>
                        </div>
                        <div v-for="tenant in houseAdvertisement.tenants" :key="tenant">
                            <div class="card mb-2 mt-2" style="padding: 10px">
                                    <div class="d-flex justify-content-center align-items-center py-2 pr-2">
                                        <div class="d-flex mx-2">
                                            <img :src="tenant.photo" style="border-radius: 50%; width: 10vh; height: 10vh;">
                                        </div>
                                        <div class="d-flex flex-column">
                                            <div class ="d-flex align-items-center" style="margin-bottom: 5px;">
                                                <h5 style="text-align: left;" class="card-title">{{ tenant.name }}</h5>
                                                <i class="bi bi-gender-female" style="margin-left: 5px;" v-if="tenant.gender == 'Mujer'"></i>
                                                <i class="bi bi-gender-male" style="margin-left: 5px;" v-if="tenant.gender == 'Hombre'"></i>
                                                <i class="bi bi-gender-ambiguous" style="margin-left: 5px;" v-if="tenant.gender == 'No binario'"></i>
                                            </div>
                                            <p style="text-align: justify;" class="card-text">{{ tenant.description }}</p>
                                        </div>
                                    </div>   
                            </div>
                        </div>
                    </div>
            </div>

        </div>
        <div class="row">
            <div class="col-6 mb-4">   
                <div class="card mb-2" style="padding: 10px">   
                    <div class="card-body">
                        <h4 style="text-align: left;" class="card-title">Descripción</h4>
                        <hr>
                        <p style="text-align: justify;" class="card-text">{{ houseAdvertisement.description }}</p>
                    </div>
                </div>  
                <div class="d-flex justify-content-center align-items-center">
                    <button type="button" class="boton"><strong>Iniciar chat <i class="bi bi-chat" style="margin-left: 5px;"></i></strong></button>
                </div>
            </div>

            <div class="col mb-4" style="padding-left: 5%; padding-right: 5%;">
                <div class="d-flex flex-column align-items-left">
                    <div style="color:#5D5E60;display: flex;">
                        <h5>{{ house.location }}</h5> 
                        <i class="bi bi-geo-alt" style="margin-left: 5px;"></i>
                    </div>  
                    <img src="https://motor.elpais.com/wp-content/uploads/2022/01/google-maps-22-1046x616.jpg" class="rounded-4" style="max-width:80%; max-height:80%;">
                </div>
            </div>
        </div>
    </div>

</template>

<style scoped>
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
    color: #FFFFFF; 
    border: none;
}

.btn-share:hover {
    background-color: #28426B;
    color: #FFFFFF; 
    border: none;
}
.carousel-item {
    height: 45vh;
    object-fit: contain;
}

.carousel-item img {
    height: 45vh;
}

.fade-enter-active, .fade-leave-active {
  transition: opacity 0.5s;
}

.fade-enter, .fade-leave-to {
  opacity: 0;
}
</style>
