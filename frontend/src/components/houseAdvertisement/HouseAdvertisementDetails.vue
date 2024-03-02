<script setup>
import { nextTick } from 'vue';
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';

const houseAdvertisement = ref(null);

const selectedImage = ref(0)

const clipboardMessage = ref(false);

const isLoading = ref(true);

const fetchError = ref(null);

const truncateDescription = (description) => {
    const words = description.split(' ');
    if (words.length > 10) {
        return words.slice(0, 10).join(' ') + '...';
    } else {
        return description;
    }
}

const route = useRoute();
const id = route.params.id;

function parseHeating(heating) {
    if (heating === 'CENTRAL_HEATING') {
        return 'Calefacción central';
    } else if (heating === 'AIR_CONDITIONING') {
        return 'Aire acondicionado';
    } 
    else if (heating == 'NATURAL_GAS') {
        return 'Gas natural';
    }
    else {
        return 'Radiador';
    }
}

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
    fetch(import.meta.env.VITE_BACKEND_URL + '/api/advertisements/' + id)
    .then(response => {
        if (!response.ok) {
            throw new Error('No se han podido cargar la vivienda')
        }
        
        return response.json();
        
    }).then(data => {
        isLoading.value = false
        houseAdvertisement.value = data
        nextTick(() => {
            const carousel = document.getElementById('imgCarousel');
            
            if (carousel) {
                carousel.addEventListener('slid.bs.carousel', function (e) {
                    const activeSlideIndex = Array.from(e.target.querySelectorAll('.carousel-item')).findIndex(item => item.classList.contains('active'));
                    selectedImage.value = activeSlideIndex;
                });
            }
        });
    })
    .catch(error => {
        isLoading.value = false
        fetchError.value = error.message
    });
});
        
        
    
</script>
<template>

    <Navbar />
    
    <div class="container" >
        <div v-if="isLoading" class="spinner-border" role="status">
            <span class="visually-hidden">Loading...</span>
        </div>
        <div v-else-if="fetchError" class="alert alert-danger" role="alert">
            {{ fetchError }}
        </div>
        <div v-else> 
        <div class="row mt-5" >
            <div class="col col-6 justify-content-center align-items-center">
                <div class="container" >
                    <div id="imgCarousel" class="carousel slide mb-4">
                        <!-- Imagen superior --> 
                        <div class="carousel-inner mx-auto p-3">
                            <div class="carousel-item" v-for="(image,index) in houseAdvertisement.images" :key="image" :class="{'active': index === 0}">
                                <img :src="image" class="img-fluid carousel-image" alt="...">
                            </div>
                        </div>
                        <!-- Botones de control -->
                        <button class="carousel-control-prev" type="button" data-bs-target="#imgCarousel" data-bs-slide="prev">
                            <span class="material-symbols-outlined" style="color: black;">
                                arrow_back_ios
                            </span>
                            <span class="visually-hidden">Previous</span>
                        </button>
                        <button class="carousel-control-next" type="button" data-bs-target="#imgCarousel" data-bs-slide="next">
                            <span class="material-symbols-outlined" style="color: black;">
                                arrow_forward_ios
                            </span>
                            <span class="visually-hidden">Next</span>
                        </button>
                    </div>
                </div>
                <!-- Imágenes debajo -->
                <div class="container w-75">
                    <div class="row align-items-center">
                        <div class="col-md-4 mb-4" v-for="(image,index) in houseAdvertisement.images" :key="image">
                            <img :src="image" class="d-block w-100 rounded" alt="..." data-bs-target="#imgCarousel" :data-bs-slide-to="index" :class="{'image-selected': selectedImage === index }">
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
                    <h1 style="text-align: left;"> {{ houseAdvertisement.price }}€</h1>
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
                            <div style="display: grid;">
                                <span class="material-icons" style="font-size: xx-large">bed</span>
                                <h5 style="color:#5D5E60;text-align: center;">{{ houseAdvertisement.house.roomsNumber }}</h5>
                            </div>
                            <div style=" display: grid;">
                                <span class="material-icons" style="font-size: xx-large">shower</span>
                                <h5 style="color:#5D5E60;text-align: center;">{{ houseAdvertisement.house.bathroomsNumber }}</h5>
                            </div>
                            <div style=" display: grid;">
                                <span class="material-icons" style="font-size: xx-large">square_foot</span>
                                <h5 style="color:#5D5E60;text-align: center;">{{ houseAdvertisement.house.area }} m<sup>2</sup></h5>
                            </div>
                            <div style="display: grid;">
                                <span class="material-symbols-outlined" style="font-size: xx-large">mode_fan</span>
                                <h5 style="color: #5D5E60; text-align: center;">{{ parseHeating(houseAdvertisement.house.heating) }}</h5>
                            </div>
                            <div style="display: grid;">
                                <span class="material-symbols-outlined" style="font-size: xx-large">floor</span>
                                <h5 style="color:#5D5E60;text-align: center;">{{ houseAdvertisement.house.floor }}</h5>
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
                                <div style="display: inline-flex;" v-for="etiqueta in houseAdvertisement.house.tags" :key="etiqueta">
                                    <span class="badge etiqueta" style="font-size: 105%;"> {{ etiqueta.tag }} </span>
                                </div>
                            </div>
                        </div>
                        <div v-if="houseAdvertisement.tenants.length > 0" class="py-3 px-3 overflow-auto" style="height:20vh;">
                        <div v-for="tenant in houseAdvertisement.tenants" :key="tenant">
                            <div class="card mb-3 mt-3" style="padding: 10px; background-color: #bdd2f4;">
                                    <div class="card-body">
                                        <div class="d-flex">
                                            <div class="mx-2">
                                                <img :src="tenant.image" style="border-radius: 50%; width: 10vh; height: 10vh;">
                                            </div>
                                            <div class="flex-column overflow-auto" style="height:10vh; padding-right: 5px">
                                                <div class ="d-flex" style="margin-bottom: 5px;">
                                                    <h5 style="text-align: left;" class="card-title">{{ tenant.username }}</h5>
                                                    <i class="bi bi-gender-female" style="margin-left: 5px;" v-if="tenant.gender == 'WOMAN'"></i>
                                                    <i class="bi bi-gender-male" style="margin-left: 5px;" v-if="tenant.gender == 'MAN'"></i>
                                                    <i class="bi bi-gender-ambiguous" style="margin-left: 5px;" v-if="tenant.gender == 'NON_BINARY'"></i>
                                                </div>
                                                <p style="text-align: justify;" class="card-text">{{ truncateDescription(tenant.description) }}</p>
                                            </div>
                                        </div>
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
                        <h5>{{ houseAdvertisement.house.location }}</h5> 
                        <i class="bi bi-geo-alt" style="margin-left: 5px;"></i>
                    </div>  
                    <img src="https://motor.elpais.com/wp-content/uploads/2022/01/google-maps-22-1046x616.jpg" class="rounded-4" style="max-width:80%; max-height:80%;">
                </div>
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
