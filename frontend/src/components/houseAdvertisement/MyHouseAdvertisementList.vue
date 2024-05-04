<script setup>
import { ref, onMounted, computed } from 'vue'
import Navbar from '../Navbar.vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex';
import { loadStripe } from '@stripe/stripe-js';

const price = ref(0)
const meters = ref(0)
const empty = ref(false)
const tenants = ref(0)
const minBathrooms = ref(null)
const maxBathrooms = ref(null)
const minBedrooms = ref(null)
const maxBedrooms = ref(null)
const errors = ref({})
const advertisements = ref([])
const filteredAdvertisements = ref([])
const filtered = ref(false);
const fetchError = ref(null)
const isLoading = ref(true)
const showFilters = ref(false)
const searchTerm = ref('')
const router = useRouter()
const store = useStore()
const user = computed(() => store.state.user)
const stripePromise = loadStripe('' + import.meta.env.VITE_STRIPE_PUBLISHABLE_KEY);
const loading = ref(false);
const lineItems = ref(null);

const updateMeta = (title, description) => {
            document.querySelector('meta[name="description"]').setAttribute('content', description);
            document.querySelector('meta[property="og:title"]').setAttribute('content', title);
            document.querySelector('meta[property="og:description"]').setAttribute('content', description);
            };

onMounted(() => {
    updateMeta('Encuentra y Promociona Propiedades en Cohabify', 'Descubre y promociona propiedades con facilidad en Cohabify. Utiliza filtros avanzados para encontrar rápidamente tu vivienda ideal o para aumentar la visibilidad de tus anuncios.');
    const urlParams = new URLSearchParams(window.location.search);
    const sessionId = urlParams.get('session_id');
    const houseId = urlParams.get('houseId');
    if (user) {
        if (sessionId){
            fetchPromotions(sessionId,houseId)
        }else{
            fetchMyAdvertisements()
        }
    } else {
        const store = useStore()
        store.watch(
            () => store.state.user,
            (newValue, oldValue) => {
                user.value = newValue
                if (sessionId){
                    fetchPromotions(sessionId,houseId)
                }
                fetchMyAdvertisements()
                
            }
        )
    }  
})

const handleCheckout = async (id) => {
        
        event.stopPropagation();
        lineItems.value = [{ price: 'price_1P4oHsBofFRUNSKsMTvgLfJE', quantity: 1}];
        if (lineItems.value !== null) {
            const stripe = await stripePromise;
            const { error } = await stripe.redirectToCheckout({
                lineItems: lineItems.value,
                mode: 'payment',
                successUrl: window.location.origin+'/myAdvertisements/house?session_id={CHECKOUT_SESSION_ID}&houseId=' + id,
                cancelUrl: window.location.origin,
            });

            if (error) {
                console.error(error);
            }
        } 
    };

const fetchPromotions = (sessionId,houseId) => {
    const response = fetch(import.meta.env.VITE_BACKEND_URL + '/api/stripe/session', {
            method: 'POST',
            headers: {
                'Authentication': 'Bearer ' + localStorage.getItem("authentication"),
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ sessionId: sessionId })
        }).then(response => {
            if (response.status === 200) {
                return response.json();
            } else { 
                throw new Error('Error al cargar la sesión de stripe');
            }
            }) 
            .then(jsonData => {
                promoteHouseAd(houseId);
            })
            .catch(error => {
                isLoading.value = false
                fetchError.value = error
            })
}

const fetchMyAdvertisements = () => {
    fetch(import.meta.env.VITE_BACKEND_URL+'/api/advertisements/houses/owner/' + user.value.id, {
        method: "GET",
        headers: {
            'Authentication': 'Bearer ' + localStorage.getItem("authentication"),
        },
        credentials: "include"
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('No se han podido cargar las viviendas')
            }
            return response.json()
        })
        .then(data => {
            setTimeout(() => {
                isLoading.value = false
                advertisements.value = data
            }, 500)
        })
        .catch(error => {
            isLoading.value = false
            fetchError.value = error
        })
}

const promoteHouseAd = (id) =>{
    //event.stopPropagation();
    fetch(import.meta.env.VITE_BACKEND_URL+'/api/advertisements/houses/promote/' + id, {
        method: "POST",
        headers: {
            'Authentication': 'Bearer ' + localStorage.getItem("authentication"),
        },
        credentials: "include"
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('No se ha podido promocionar el anuncio de vivienda')
            }
        })
        .then(data => {
            fetchMyAdvertisements()
        })
        .catch(error => {
            fetchError.value = error
        })

}

const deleteHouseAd = (id) => {
    
    event.stopPropagation();
    fetch(import.meta.env.VITE_BACKEND_URL+'/api/advertisements/houses/' + id, {
        method: "DELETE",
        headers: {
            'Authentication': 'Bearer ' + localStorage.getItem("authentication"),
        },
        credentials: "include"
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('No se ha podido eliminar el anuncio de vivienda')
            }
        })
        .then(data => {
            fetchMyAdvertisements()
        })
        .catch(error => {
            fetchError.value = error
        })
}

const goToEdit = (id) => {
    event.stopPropagation();
    router.push(`/advertisements/houses/edit/${id}`)
}

const currentAdvertisements = computed(() => {
 return filtered.value ? filteredAdvertisements.value : advertisements.value
})

const getImageUrl = (image) => {
    return image.startsWith('/') ? import.meta.env.VITE_BACKEND_URL + image : image
}

const search =  () => {
    price.value = 0;
    meters.value = 0;
    empty.value = false;
    tenants.value = 0;
    minBathrooms.value = null;
    maxBathrooms.value = null;
    minBedrooms.value = null;
    maxBedrooms.value = null;
    
    filteredAdvertisements.value = advertisements.value.filter(a => {
        if(searchTerm.value === ''){
            return true;
        }else{
            return a.house.location.toLowerCase().includes(searchTerm.value.toLowerCase()) || a.description.toLowerCase().includes(searchTerm.value.toLowerCase()) || a.title.toLowerCase().includes(searchTerm.value.toLowerCase());
        }
    })
    filtered.value = true;
}

const applyFilters = () => {

    if (price.value < 0 || price.value > 5000) {
        errors.value.priceVal = 'Precio fuera de rango'
    }

    if (meters.value < 0 || meters.value > 350) {
        errors.value.metersVal = 'Metros cuadrados fuera de rango'
    }

    if (tenants.value < 0 || tenants.value > 10) {
        errors.value.tenantsVal = 'Número de inquilinos fuera de rango'
    }

    if (minBathrooms.value !== null) {
       if (typeof minBathrooms.value !== 'number') {
            if(minBathrooms.value === '') {
                minBathrooms.value = null;
            } else {
                errors.value.minBathroomsVal = 'Valor no numérico'
            }
        } else if (minBathrooms.value < 0) {
            errors.value.minBathroomsVal = 'Valor negativo'
        }
    } 

    if (minBedrooms.value !== null) {
        if (typeof minBedrooms.value !== 'number') {
            if(minBedrooms.value === '') {
                minBedrooms.value = null;
            } else {
                errors.value.minRoomsVal = 'Valor no numérico'
            }
        } else if (minBedrooms.value < 0) {
            errors.value.minRoomsVal = 'Valor negativo'
        }
    }

    if(Object.keys(errors.value).length === 0){
        fetchFilteredAdvertisements();
    } 
}

const fetchFilteredAdvertisements = async () => {
    fetch(import.meta.env.VITE_BACKEND_URL+'/api/advertisements/houses/owner/' + user.value.id + "/filter", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        credentials: 'include',
        body: JSON.stringify({
            price: price.value,
            meters: meters.value,
            empty: empty.value,
            tenants: tenants.value,
            minBathrooms: minBathrooms.value,
            minBedrooms: minBedrooms.value
        }),
    })
        .then(response => {
            if (response.status === 200) {
                return response.json();
            } else {
                throw new Error('Error al aplicar los filtros.');
            }
        })
        .then(data => {
            filteredAdvertisements.value = data;
            filtered.value = true;
        })
        .catch(error => fetchError.value = error.message);
}

</script>
<template>
    <navbar />
        <div class="row h-100">
            <transition name="slide">
                <div class="col-md-3 filter-column p-4" style="padding: 10px;" v-if="showFilters">
                    <div class="d-flex flex-row-reverse">
                        <button class="form-button rounded-5 d-flex align-items-center" @click.prevent="showFilters=false" style="height: 40px;">
                            <span class="material-symbols-outlined">
                                keyboard_double_arrow_left
                            </span>
                        </button>
                    </div>
                    <form class="needs-validation" novalidate>
                        <!-- Precio -->
                        <div class="d-flex" style="width: 100%; height: 30px;">
                            <div>
                                <p>Max. Precio</p>
                            </div>
                        </div>
                        <input type="range" class="form-range" min="0" max="5000" step="50" v-model="price" id="priceVal" :class="{'is-invalid': errors.priceVal}">
                        <b>{{ price == 0 ? '-' : price == 5000 ? '+ ' + price.toString() + '€/mes' : price.toString() + '€/mes' }}</b>
                        <p v-if="errors.priceVal" class="text-danger">{{ errors.priceVal }}</p>
                        <!-- Metros cuadrados -->
                        <div class="mt-3 d-flex" style="width: 100%; height: 30px;">
                            <p>Min. Espacio</p>
                        </div>
                        <input type="range" class="form-range" min="0" max="350" step="5" v-model="meters" id="metersVal" :class="{'is-invalid': errors.metersVal}">
                        <b>{{ meters == 0 ? '-' : meters.toString() + 'm²' }}</b>
                        <p v-if="errors.metersVal" class="text-danger">{{ errors.metersVal }}</p>
                        <!-- Vivienda vacía -->
                        <div class="mt-3">
                            <p>Vivienda vacía</p>
                            <input type="checkbox" class="form-check-input" id="empty" v-model="empty" :class="{'is-invalid': errors.empty}">
                            <b style="margin-left: 10px;">{{ empty ? 'Sí' : 'No' }}</b>
                            <p v-if="errors.empty" class="text-danger">{{ errors.empty }}</p>
                        </div>
                        <!-- Inquilinos -->
                        <div v-if="!empty">
                            <div class="mt-3 d-flex" :invalid="true" style="width: 100%; height: 30px;">
                                <p>Max. Inquilinos</p>
                            </div>
                            <input type="range" class="form-range" min="0" max="10" step="1" v-model="tenants" id="tenantsVal" :class="{'is-invalid': errors.tenantsVal}">
                            <b>{{ tenants == 0 ? '-' : tenants == 10 ? '+ ' + tenants.toString() : tenants.toString() }}</b>
                            <p v-if="errors.tenantsVal" class="text-danger">{{ errors.tenantsVal }}</p>
                        </div>
                        <!-- Baños -->
                        <div class="mt-3 d-flex justify-content-between" style="width: 100%; height: 30px;">
                            <p>Baños</p>
                            <p><b>(Mínimo)</b></p>
                        </div>
                        <div class="mt-3 d-flex">
                            <div>
                                <input type="number" class="form-control" v-model="minBathrooms" min="0" id="minBathroomsVal" :class="{'is-invalid': errors.minBathroomsVal}">
                                <p v-if="errors.minBathroomsVal" class="text-danger">{{ errors.minBathroomsVal }}</p>
                            </div>
                        </div>
                        <!-- Habitaciones -->
                        <div class="mt-3 d-flex justify-content-between" style="width: 100%; height: 30px;">
                            <p>Habitaciones</p>
                            <p><b>(Mínimo)</b></p>
                        </div>
                        <div class="mt-3 d-flex">
                            <div>
                                <input type="number" class="form-control" v-model="minBedrooms" min="0" id="minRoomsVal" :class="{'is-invalid': errors.minRoomsVal}">
                                <p v-if="errors.minRoomsVal" class="text-danger">{{ errors.minRoomsVal }}</p>
                            </div>
                        </div>
                    </form>
                    <hr>
                    <div class="d-flex justify-content-between mb-2">
                        <button class="btn btn-success" @click.prevent="errors=[]; applyFilters()">Aplicar</button>
                        <button class="btn btn-danger" @click.prevent="errors=[]; filtered = false;price = 0; meters = 0; empty = false; tenants = 0; minBathrooms = null; maxBathrooms = null; minBedrooms = null; maxBedrooms = null">Borrar</button>
                    </div>
                </div>
            </transition>
            <div class="col">
                <div class="d-flex justify-content-center align-items-center mt-4">
                    <div class="search-bar">
                        <form class="d-flex w-100 justify-content-between">
                            <div id="searchForm" style="width:90%; padding-top: 5px; padding-right: 1px;">
                                <input class="searchInput" v-model= "searchTerm" type="text" style="color:black; padding-bottom: 1%;" id="searchInput" placeholder="Busco..." />
                            </div>
                            <button class="searchButton d-flex align-items-center" style="padding-top:15px; padding-right:1%;" type="submit" @click.prevent="search">
                                <img src="/images/search.png" alt="Buscar" />
                            </button>
                            <button @click.prevent="showFilters=!showFilters" class="searchButton d-flex align-items-center" style="margin-left: 2%; margin-right: 4%;">
                                <img src="/images/filter.png" alt="Filter" />
                            </button>
                        </form>
                    </div>
                </div>
                <div v-if="isLoading" class="spinner-border mt-5" role="status">
                    <span class="visually-hidden">Loading...</span>
                </div>
                <div v-else-if="fetchError" class="alert alert-danger" role="alert">
                    {{ fetchError }}
                </div>
                <div class="list-container mt-4" v-else>
                    <div class="list-item mt-2" v-for="advertisement in currentAdvertisements" :key="advertisement.id" @click="$router.push(`/advertisements/houses/${advertisement.id}`)" :class="{ highlighted: advertisement.promotionExpirationDate !== null }">
                        <img :src="getImageUrl(advertisement.images[0])" alt="house" class="list-item-image">
                        <div class="list-item-content">
                            <div class="d-flex justify-content-between w-100" style="margin-right: 2vw;">
                                <h3>{{ advertisement.title }}</h3>
                                <h3><b>{{ advertisement.price }}€/mes</b></h3>
                            </div>
                            <b>{{ advertisement.house.location }}</b>
                            <div class="d-flex justify-content-between align-items-center w-100">

                                <div class="d-flex justify-content-between w-50 mt-5 h-100 align-items-center">
                                    <div class="d-flex flex-column align-items-center">
                                        <span class="material-icons">bed</span>
                                        <p>{{ advertisement.house.roomsNumber }} dorm.</p>
                                    </div>
                                    <div class="d-flex flex-column align-items-center">
                                        <span class="material-icons">shower</span>
                                        <p>{{ advertisement.house.bathroomsNumber }} baños</p>
                                    </div>
                                    <div class="d-flex flex-column align-items-center">
                                        <span class="material-icons">square_foot</span>
                                        <p>{{ advertisement.house.area }} m²</p>
                                    </div>
                                    <div class="d-flex flex-column align-items-center">
                                        <span class="material-symbols-outlined">floor</span>
                                        <p>Planta {{ advertisement.house.floor }}</p>
                                    </div>
                                </div>

                                <div class="d-flex justify-content-end w-50 mt-5 h-100 align-items-center">
                                    <div class="d-flex flex-column align-times-center">
                                        <button  class="btn btn-warning active" style="margin-right: 1vw; height: 5.5vh; display: flex; justify-content: center; align-items: center; font-size: 1.2em;" v-if="advertisement.promotionExpirationDate !== null">
                                                <b>Promocionado</b>
                                                <div class="promo-icon"></div>
                                        </button>
                                    </div>
                                    <div class="d-flex flex-column align-items-center">
                                        <button class="btn btn-warning" style="margin-right: 1vw; height: 5.5vh; display: flex; justify-content: center; align-items: center; font-size: 1.2em;" @click.prevent="handleCheckout(advertisement.id)" v-if="advertisement.promotionExpirationDate === null">
                                            Promocionar
                                            <span class="material-symbols-outlined" style="margin-left:4px; font-size: 1.5em;">
                                            campaign
                                            </span>
                                        </button>
                                    </div>
                                    <div class="d-flex flex-column align-items-center">
                                        <button class="btn btn-success" style="margin-right: 1vw; height: 5.5vh;" @click="goToEdit(advertisement.id)">
                                            <span class="material-symbols-outlined">edit</span>
                                        </button>
                                    </div>
                                
                                    <div class="d-flex flex-column align-items-center">
                                        <button class="btn btn-danger" style="height: 5.5vh;" @click="deleteHouseAd(advertisement.id)">
                                            <span class="material-symbols-outlined">delete</span>
                                        </button>
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
.slide-enter-active, .slide-leave-active {
  transition: .3s;
}
.slide-leave-to {
  transform: translateX(-100%);
}

.slide-enter-from {
  transform: translateX(-100%);
}

.highlighted {
    background-color: #bbeeff;
    border: 2px solid black;
}

.searchInput {
  background-color: #ffff;
  border: none;
  border-radius: 5px;
  padding: 10px;
  width: 90%;
  margin-right: 10px;
}

.searchButton {
  background-color: transparent;
  border: none;
  align-self: center;
  cursor: pointer;
  width: 30px;
  height: 30px;
}

.searchButton img {
  width: 24px;
  align-self: right;
}

.form-range::-webkit-slider-thumb {
  background: #a4c7ff; 
}

.form-range::-moz-range-thumb {
  background: #a4c7ff; 
}

.form-range::-ms-thumb {
  background: #a4c7ff; 
}

.form-button {
  background: #28426B;
}

.form-button:hover {
    border-color:#ffffff;
}

.form-button:active {
    background: #3f5982;
}
.highlighted {
    background-color: #d4e4ff;
    border: 2px solid rgb(5, 92, 167);
}

.promo-button {
  margin-right: 1vw;
  height: 5.5vh;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 1.2em;
}

.promo-icon {
  width: 24px;
  height: 24px;
  margin-left: 4px;
  background-image: url('/images/megaphone.png');
  background-size: cover;
}

.list-item:hover .promo-icon {
  width: 30px;
  height: 30px;
  background-image: url('/images/megaphone.gif');
  background-size: cover;
}
</style>