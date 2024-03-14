<script setup>
import { nextTick } from 'vue';
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';

const houseAdvertisement = ref(null);

const selectedImage = ref(0)

const clipboardMessage = ref(false);
const auth = ref();
const valorations = ref([]);
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
const fetchAdvertisement = async () => {
    try {
        const userFetch = await fetch(import.meta.env.VITE_BACKEND_URL + "/auth/getUser",
        {
            method: "POST",
            headers: {
            "Authentication":
                "Bearer " + sessionStorage.getItem("authentication"),
            },
        }
        );

        const userData = await userFetch.json();
        auth.value = userData;

    } catch (error) {
        console.error("Error:", error);
    }
};
const openModal = () => {
    deleteComment1();
    let modal = document.getElementById('loginModal');
    modal.style.display = "block";
}
const closeModal = () => {
    let modal = document.getElementById('loginModal');
    modal.style.display = "none";
}
const deleteComment1 = () => {
    fetch(import.meta.env.VITE_BACKEND_URL + '/api/userRating/ratedUser/' + auth.value.id + '/' + houseAdvertisement.value.author.id, {
        method: 'DELETE',
        headers: {
            'Authentication': 'Bearer ' + sessionStorage.getItem("authentication"),
        },
    })
        .then(response => {
            if (response.ok) {
                console.log('Comentario eliminado con éxito');
            } else {
                console.error('Error al eliminar el comentario');
            }
        })
        .catch(error => console.error('Error al enviar datos al backend:', error));
};
const deleteComment2 = () => {
    fetch(import.meta.env.VITE_BACKEND_URL + '/api/userRating/ratedUser/' + auth.value.id + '/' + houseAdvertisement.value.author.id, {
        method: 'DELETE',
        headers: {
            'Authentication': 'Bearer ' + sessionStorage.getItem("authentication"),
        },
    })
        .then(response => {
            setTimeout(() => {
                window.location.href = "/advertisements/houses/"+houseAdvertisement.value.id;
            }, 1000);
            if (response.ok) {
                console.log('Comentario eliminado con éxito');
            } else {
                console.error('Error al eliminar el comentario');
            }
        })
        .catch(error => console.error('Error al enviar datos al backend:', error));
};


const register = () => {
    const formData = new FormData();
    console.log(auth.value);
    formData.append("string-data", new Blob([JSON.stringify({
        user: houseAdvertisement.value.author,
        ratedUser: auth.value,
        comment: text.value
    })], { type: "application/json" }));
    fetch(import.meta.env.VITE_BACKEND_URL + '/api/userRating', {
        method: 'POST',
        headers: {
                        'Authentication': 'Bearer ' + sessionStorage.getItem("authentication"),
                    },
        body: formData,
    })
        .then(response => response.json())
        .then(jsonData => {
            setTimeout(() => {
                window.location.href = "/advertisements/houses/"+houseAdvertisement.value.id;
            }, 1000);
        }
        
        
        )
        .catch(error => console.error('Error al enviar datos al backend:', error));

};
const fetchValorations = async () => {
            try {
                const response = await fetch(import.meta.env.VITE_BACKEND_URL + "/api/userRating/user/"+houseAdvertisement.value.author.id,
                    {
                        method: "GET",
                        headers: {
                            'Authentication': 'Bearer ' + sessionStorage.getItem("authentication"),
                        },
                        credentials: "include",
                    });
                const data = await response.json();
                valorations.value = data;
            } catch (error) {
                console.error("Error:", error);
            }
};
onMounted(() => {
    fetchAdvertisement();
    fetch(import.meta.env.VITE_BACKEND_URL + '/api/advertisements/houses/' + id, {
        method: "GET",
        headers: {
            'Authentication': 'Bearer ' + localStorage.getItem("authentication"),
        },
        credentials: "include"
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('No se han podido cargar la vivienda')
        }
        
        return response.json();
        
    }).then(data => {
        isLoading.value = false
        houseAdvertisement.value = data
        fetchValorations();
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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css">
    <div class="container" >
        <div id="loginModal" class="modal">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <div class="col">
                            <h5 class="modal-title">Nuevo comentario</h5>
                        </div>
                        <div class="col">
                            <span @click="closeModal" class="success-checkmark">X</span>
                        </div>
                    </div>
                    <div class="modal-body">
                        <form @submit.prevent="register">
                            <div class="form-group">
                                <label for="commentText">Comentario</label>
                                <textarea class="form-control" id="text" v-model="text"></textarea>
                            </div>
                            <button type="submit" class="btn btn-primary">Enviar</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
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
                        <div class="carousel-inner mx-auto p-3">
                            <div class="carousel-item" v-for="(image,index) in houseAdvertisement.images" :key="image" :class="{'active': index === 0}">
                                <img :src="image" class="img-fluid carousel-image" alt="...">
                            </div>
                        </div>
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
                <div class="container w-75">
                    <div class="row align-items-center">
                        <div class="col-md-4 mb-4" v-for="(image,index) in houseAdvertisement.images" :key="image">
                            <img :src="image" class="d-block w-100 rounded" alt="..." data-bs-target="#imgCarousel" :data-bs-slide-to="index" :class="{'image-selected': selectedImage === index }">
                        </div>         
                    </div>
                </div>
            </div>    
            <div class="col px-6" style="padding-left: 5%; padding-right: 5%;">
                    <div style="display: flex; align-items: center; justify-content: space-between;">
                        <h1 style="text-align: left;"> {{ houseAdvertisement.title }}</h1>
                        <button class="btn btn-share" @click="copyToClipboard()">
                            <i class="bi bi-share-fill" ></i>
                        </button>
                    </div>
                    <h2 style="text-align: left;"> {{ houseAdvertisement.price }}€</h2>
                    <transition name="fade">
                        <div v-show="clipboardMessage" class="alert alert-success alert-dismissible fade show" role="alert">
                            Enlace copiado al portapapeles.
                        </div>
                    </transition>
                    <br>
                    <div >
                        <h4 style="text-align: left;">Detalles</h4> 
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
                        <div style="display: flex" class="mb-1">
                            <div style=" display: grid;margin-right: 10%;">
                                    <h5 style="color: #5D5E60;">Número</h5>
                                    <h5 style="color:black;text-align: center;">{{ houseAdvertisement.tenants.length }}</h5>
                            </div>
                            <div style=" display: grid;margin-right: 10%;">
                                <h5 style="color: #5D5E60;">Etiquetas</h5>
                                <div style="display: inline-flex;" v-for="etiqueta in houseAdvertisement.house.tags" :key="etiqueta">
                                    <span class="badge etiqueta shadow" style="font-size: 105%;"> {{ etiqueta.tag }} </span>
                                </div>
                            </div>
                        </div>
                        <div v-if="houseAdvertisement.tenants.length > 0" class="py-3 px-3 overflow-auto" style="height:20vh;">
                        <div v-for="tenant in houseAdvertisement.tenants" :key="tenant">
                            <div class="card card-user mb-3 mt-3 shadow-sm" style="padding: 10px;">
                                    <div class="card-body">
                                        <div class="d-flex">
                                            <div class="mx-2">
                                                <img :src="tenant.imageUri" style="border-radius: 50%; width: 10vh; height: 10vh;">
                                            </div>
                                            <div class="flex-column overflow-auto" style="height:10vh; padding-right: 5px;">
                                                <div class ="d-flex" style="margin-bottom: 5px;  align-items: center;">
                                                    <h5 style="text-align: left;" class="card-title">{{ tenant.username }}</h5>
                                                    <i class="bi bi-gender-female" style="margin-left: 5px;" v-if="tenant.gender == 'FEMENINO'"></i>
                                                    <i class="bi bi-gender-male" style="margin-left: 5px;" v-if="tenant.gender == 'MASCULINO'"></i>
                                                    <i class="bi bi-gender-ambiguous" style="margin-left: 5px;" v-if="tenant.gender == 'OTRO'"></i>
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
        <div class="row mt-1">
            <div class="col mb-4 mt-1" style="padding-left: 5%; padding-right: 5%;">
                <div class="d-flex flex-column align-items-left">
                    <div style="color:#5D5E60; display: flex; align-items: center;"> 
                        <h5>{{ houseAdvertisement.house.location }}</h5> 
                        <i class="bi bi-geo-alt" style="margin-left: 1%;"></i>
                    </div>  
                    <img src="https://motor.elpais.com/wp-content/uploads/2022/01/google-maps-22-1046x616.jpg" class="rounded-4" style="max-width:100%; max-height:100%;">
                </div>
            </div>
            

            <div class="col-6 mb-4 mt-5" style="padding-left: 5%; padding-right: 5%;">   
                <div class="card mb-3 shadow " style="padding: 10px;">   
                    <div class="card-body" >
                        <h4 style="text-align: left;" class="card-title">Descripción</h4>
                        <hr>
                        <p style="text-align: justify;" class="card-text">{{ houseAdvertisement.description }}</p>
                    </div>
                </div>  
                <div class="d-flex justify-content-center">
                    <button type="button" class="boton"><strong>Iniciar chat <i class="bi bi-chat" style="margin-left: 5px;"></i></strong></button>
                </div>
                <div style="margin-top: 5%;"> 
                    <div style="margin-top: 5;">
                        <div class="d-flex justify-content-between">
                            <h4 style=" text-align: left;">Comentarios</h4>
                            <div v-if="auth.value.id !== houseAdvertisement.value.author.id">
                                    <i class="fas fa-trash-alt" 
                                        @click="deleteComment2" 
                                        style="width: 38px; height: 38px; border: 0.2em solid black; border-radius: 50%; padding: 0.5em; background-color: #f2f2f2;">
                                    </i>
                                    <button type="button" @click="openModal" class="button boton" style="padding: 1vh;"><strong style="color:antiquewhite">Comentar</strong></button>
                            </div>
                        </div>
                        <hr>
                    </div>
                    <div v-if="valorations.length == 0" style="text-align: left;">Aún no hay comentarios...</div>

                    <div v-else style="overflow-y: auto; max-height: 50vh;">
                        <div v-for="comentario in valorations" :key="comentario">
                            <div class="card card-user mb-3 mt-3 shadow-sm" style="padding: 10px;"> 
                                <div class="card-body">
                                    <p style="font-weight:bold; text-align: left;" class="card-title"><img class="rounded-circle" :src="comentario.ratedUser.imageUri" style="width:3vw; height: 3vw; margin-right: 1vw;"/>{{ comentario.ratedUser.username}}</p>
                                    <p style="text-align: justify; word-wrap: break-word" class="card-text">{{ comentario.comment }}</p>
                                </div>
                            </div>
                        </div>
                    </div>
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
    width: 30%;
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
    border-radius: 15px;
    border: 1px solid #28426B;
    background-color: #FFFFFF;
    color: #28426B;
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

.card-user {
    border-top: 1vh solid #28426B;
}
</style>
