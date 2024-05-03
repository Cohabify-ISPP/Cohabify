<script setup>
import { ref, onMounted, computed } from 'vue'
import { useStore } from "vuex";
import Navbar from '../Navbar.vue'

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
const store = useStore();
const currentUser = computed(() => store.state.user);

const updateMeta = (title, description) => {
            document.querySelector('meta[name="description"]').setAttribute('content', description);
            document.querySelector('meta[property="og:title"]').setAttribute('content', title);
            document.querySelector('meta[property="og:description"]').setAttribute('content', description);
            };

onMounted(() => {
    updateMeta('Encuentra tu Vivienda Ideal en Cohabify: Búsqueda Personalizada y Filtros Avanzados', 'Utiliza nuestros filtros avanzados para encontrar viviendas en alquiler que se ajusten a tus necesidades específicas. Explora opciones por precio, tamaño, y más en Cohabify y descubre tu hogar ideal rápidamente.');
    fetch(import.meta.env.VITE_BACKEND_URL+'/api/advertisements/houses', {
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

            for (let i = 0; i < data.length; i++) {
                fetchValoration(data[i].id).then((valoration) => {
                    data[i].valoration = valoration;
                });
            }

            setTimeout(() => {
                isLoading.value = false
                advertisements.value = data
            }, 500)
        })
        .catch(error => {
            isLoading.value = false
            fetchError.value = error
        })
})

const currentAdvertisements = computed(() => {
 const ads = filtered.value ? filteredAdvertisements.value : advertisements.value;

 const orderedAds = ads.sort((a, b) => {
    if (a.promotionExpirationDate === null && b.promotionExpirationDate === null) {
        return 0; 
    } else if (a.promotionExpirationDate === null) {
        return 1; 
    } else if (b.promotionExpirationDate === null) {
        return -1; 
    }

    const dateA = new Date(a.promotionExpirationDate);
    const dateB = new Date(b.promotionExpirationDate);
    return dateA - dateB;
    });
 return orderedAds;
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

    else if (meters.value < 0 || meters.value > 350) {
        errors.value.metersVal = 'Metros cuadrados fuera de rango'
    }

    else if (tenants.value < 0 || tenants.value > 10) {
        errors.value.tenantsVal = 'Número de inquilinos fuera de rango'

    }else if (minBathrooms.value !== null) {
       if (typeof minBathrooms.value !== 'number') {
            errors.value.minBathroomsVal = 'Valor no numérico'
        } else if (minBathrooms.value < 0) {
            errors.value.minBathroomsVal = 'Valor negativo'
        } else if (maxBathrooms.value == null) {
            errors.value.maxBathroomsVal = 'Valor no indicado'
        }
    } 

    else if (maxBathrooms.value !== null) {
        if (typeof maxBathrooms.value !== 'number') {
            errors.value.maxBathroomsVal = 'Valor no numérico'
        } else if (maxBathrooms.value < 0) {
            errors.value.maxBathroomsVal = 'Valor negativo'
        } else if (minBathrooms.value == null) {
            errors.value.minBathroomsVal = 'Valor no indicado'
        }
    }

    else if (minBathrooms.value !== null && maxBathrooms.value !== null && minBathrooms.value > maxBathrooms.value) {
        if (!errors.value.minBathroomsVal) {
            errors.value.minBathroomsVal = 'Valor mayor que el máximo seleccionado'
        }
    }

    else if (minBedrooms.value !== null) {
        if (typeof minBedrooms.value !== 'number') {
            errors.value.minRoomsVal = 'Valor no numérico'
        } else if (minBedrooms.value < 0) {
            errors.value.minRoomsVal = 'Valor negativo'
        } else if (maxBedrooms.value == null) {
            errors.value.minRoomsVal = 'Valor no indicado'
        }
    }

    else if (maxBedrooms.value !== null) {
        if (typeof maxBedrooms.value !== 'number') {
            errors.value.maxRoomsVal = 'Valor no numérico'
        } else if (maxBedrooms.value < 0) {
            errors.value.maxRoomsVal = 'Valor negativo'
        } else if (minBedrooms.value == null) {
            errors.value.maxRoomsVal = 'Valor no indicado'
        }
    }

    else if (minBedrooms.value !== null && maxBedrooms.value !== null && minBedrooms.value > maxBedrooms.value) {
        if (!errors.value.minRoomsVal) {
            errors.value.minRoomsVal = 'Valor mayor que el máximo seleccionado'
        }
    }

    if(Object.keys(errors.value).length === 0){
        fetchFilteredAdvertisements();
    }
    
   
}
const fetchFilteredAdvertisements = async () => {
    fetch(import.meta.env.VITE_BACKEND_URL + '/api/advertisements/houses/filter', {
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
            maxBathrooms: maxBathrooms.value,
            minBedrooms: minBedrooms.value,
            maxBedrooms: maxBedrooms.value
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

const currentUserIsAuthor = (advertisement) => {
    return currentUser.value.id === advertisement.author.id;
}

const fetchValoration = async (id) => {
  try {
    const response = await fetch(
      import.meta.env.VITE_BACKEND_URL + `/api/houseRating/houseAdvertisements/${id}`,
      {
        method: "GET",
        credentials: "include",
      }
    );
    const valorations = await response.json();
    let total = 0;

    for(const rating of valorations){
        total = total + rating.rating;        
    }

    if(valorations.length > 0){
        return (total * 1.0) / valorations.length;
    } else {
        return 0;
    }

  } catch (error) {
    console.error("Error:", error);
  }
};

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
                            <p><b>(Mínimo - Máximo)</b></p>
                        </div>
                        <div class="mt-2">
                            <div class="d-flex">
                                <div>
                                    <input type="number" class="form-control" v-model="minBathrooms" min="0" id="minBathroomsVal" :class="{'is-invalid': errors.minBathroomsVal}">
                                    <p v-if="errors.minBathroomsVal" class="text-danger">{{ errors.minBathroomsVal }}</p>
                                </div>
                                <b style="margin: 0 10px;"> - </b>
                                <div>
                                    <input type="number" class="form-control" v-model="maxBathrooms" id="maxBathroomsVal" :class="{'is-invalid': errors.maxBathroomsVal}">
                                    <p v-if="errors.maxBathroomsVal" class="text-danger">{{ errors.maxBathroomsVal }}</p>
                                </div>
                            </div>
                        </div>
                        <!-- Habitaciones -->
                        <div class="mt-3 d-flex justify-content-between" style="width: 100%; height: 30px;">
                            <p>Habitaciones</p>
                            <p><b>(Mínimo - Máximo)</b></p>
                        </div>
                        <div class="d-flex justify-content-between mt-2">
                            <input type="number" class="form-control" v-model="minBedrooms" min="0" id="minRoomsVal" :class="{'is-invalid': errors.minRoomsVal}">
                            <b style="margin: 0 10px;"> - </b>
                            <input type="number" class="form-control" v-model="maxBedrooms" id="maxRoomsVal" :class="{'is-invalid': errors.maxRoomsVal}">
                        </div>
                    </form>
                    <hr>
                    <div class="d-flex justify-content-between mb-2">
                        <button class="btn btn-success" @click="errors=[]; applyFilters()">Aplicar</button>
                        <button class="btn btn-danger" @click="errors=[]; filtered = false;price = 0; meters = 0; empty = false; tenants = 0; minBathrooms = null; maxBathrooms = null; minBedrooms = null; maxBedrooms = null">Borrar</button>
                    </div>
                </div>
            </transition>
            <div class="col">
                <div class="d-flex justify-content-center align-items-center mt-4">
                    <div class="search-bar">
                        <form class="d-flex w-100 justify-content-between">
                            <div id="searchForm" style="width:90%;  padding-top: 5px; padding-right: 1px;">
                                <input class="searchInput" v-model= "searchTerm" type="text" style="color:black" id="searchInput" placeholder="Busco..." />
                            </div>
                            <button class="searchButton d-flex align-items-center" style="padding-top:15px; padding-right: 1%; " type="submit" @click.prevent="search" >
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
                        <img :src="getImageUrl(advertisement.images[0])" alt="house" class="list-item-image" style="width:20vw; max-width:20vw">
                        <div class="list-item-content">
                            <div class="d-flex justify-content-between w-100" style="margin-right: 2vw;">
                                <h3>{{ advertisement.title }}</h3>
                                <h3><b>{{ advertisement.price }}€/mes</b></h3>
                            </div>
                            <div class="d-flex justify-content-between w-100">
                                <b>{{ advertisement.house.location }}</b>

                                <div class="d-flex display-inline-flex">
                                    <div style="margin-right: 0.7vh;" class="d-flex align-items-center">
                                    <span style="font-weight: bold; font-size: large; margin-right: 2px;color: #28426b;"> {{ advertisement.house.likes.length }} </span>
                                    <span style="color: #e87878;" class="material-icons">favorite</span>
                                    </div>
                                    <div class="d-flex align-items-center" style="margin-right: 0.7vh;">
                                        <span style="font-weight: bold; font-size: large; margin-right: 2px;color: #28426b"> {{ parseFloat(advertisement.valoration ? advertisement.valoration.toFixed(2) : 0) }} </span>
                                        <span style="color: #ffa723;" class="material-icons">star</span>
                                    </div>
                                    <div v-if="currentUserIsAuthor(advertisement)" class="d-flex align-items-center">
                                        <span style="font-weight: bold; font-size: large; margin-right: 2px;color: #28426b">{{ advertisement.views }}</span>
                                        <span class="material-symbols-outlined" style="color: #28426b">visibility</span>
                                    </div>
                                </div>
                            </div>
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
                                <div class="d-flex justify-content-end w-50 mt-4 h-100 align-items-center">
                                    <div class="d-flex flex-column">
                                    <button  class="btn btn-warning active" style="height: 5.5vh; display: flex; justify-content: center; align-items: center; font-size: 1.2em;" v-if="advertisement.promotionExpirationDate !== null">
                                            <b>Promocionado</b>
                                            <div class="promo-icon"></div>
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

.hidden {
  opacity: 0;
  max-height: 0;
  max-width: 0;
  transition: opacity 0.2s ease, visibility 0.2s ease;
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