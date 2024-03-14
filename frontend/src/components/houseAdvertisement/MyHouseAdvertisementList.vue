<script setup>
import { ref, onMounted, computed } from 'vue'
import Navbar from '../Navbar.vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex';

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
const user = computed(() => useStore().state.user)

onMounted(() => {

    if (user) {
        fetchMyAdvertisements()
    } else {
        const store = useStore()
        store.watch(
            () => store.state.user,
            (newValue, oldValue) => {
                user.value = newValue
                fetchMyAdvertisements()
            }
        )
    }
})

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
                throw new Error('No se ha podido eliminar la vivienda')
            }
        })
        .then(data => {
            console.log("Vivienda eliminada correctamente")
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
    console.log(filteredAdvertisements.value)
    console.log(searchTerm.value)
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
    }

    else if (minBathrooms.value !== null) {
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

    else if (minBathrooms.value != null && maxBathrooms.value != null && minBathrooms.value > maxBathrooms.value) {
        console.log('entra')
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

    else if (minBedrooms.value != null && maxBedrooms.value != null && minBedrooms.value > maxBedrooms.value) {
        if (!errors.value.minRoomsVal) {
            errors.value.minRoomsVal = 'Valor mayor que el máximo seleccionado'
        }
    }

    else{
        filtered.value ? (
        filteredAdvertisements.value = filteredAdvertisements.value.filter(a => {
            return (price.value >= a.price || price.value == 0) &&
            (meters.value <= a.house.area || meters.value == 0) &&
            ((empty.value == a.house.empty) || (tenants.value>=a.house.tenants || tenants.value==0)) &&
            (minBathrooms.value <= a.house.bathroomsNumber || minBathrooms.value == null) &&
            (maxBathrooms.value >= a.house.bathroomsNumber || maxBathrooms.value == null) &&
            (minBedrooms.value <= a.house.roomsNumber || minBedrooms.value == null) &&
            (maxBedrooms.value >= a.house.roomsNumber || maxBedrooms.value == null);
        })):(
        filteredAdvertisements.value = advertisements.value.filter(a => {
            return (price.value >= a.price || price.value == 0) &&
            (meters.value <= a.house.area || meters.value == 0) &&
            ((empty.value == a.house.empty) || (tenants.value>=a.house.tenants || tenants.value==0)) &&
            (minBathrooms.value <= a.house.bathroomsNumber || minBathrooms.value == null) &&
            (maxBathrooms.value >= a.house.bathroomsNumber || maxBathrooms.value == null) &&
            (minBedrooms.value <= a.house.roomsNumber || minBedrooms.value == null) &&
            (maxBedrooms.value >= a.house.roomsNumber || maxBedrooms.value == null);
        })
        )
        filtered.value = true
    }

    console.log(errors.value)

}

</script>
<template>
    <navbar />
        <div class="row h-100">
            <transition name="slide">
                <div class="col-md-3 filter-column p-4" style="padding: 10px;" v-if="showFilters">
                    <div class="d-flex flex-row-reverse">
                        <button class="btn btn-primary rounded-5" @click.prevent="showFilters=false" style="height: 40px;">
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
                        <button class="btn btn-primary" @click="errors=[]; applyFilters()">Aplicar</button>
                        <button class="btn btn-danger" @click="errors=[]; filtered = false;price = 0; meters = 0; empty = false; tenants = 0; minBathrooms = null; maxBathrooms = null; minBedrooms = null; maxBedrooms = null">Borrar</button>
                    </div>
                </div>
            </transition>
            <div class="col">
                <div class="d-flex justify-content-center align-items-center mt-4">
                    <div class="search-bar">
                        <form class="d-flex w-100 justify-content-between">
                            <div class="w-100 my-auto">
                                <input class="search-input" v-model= "searchTerm" type="text" id="search-input" placeholder="Busco..."/>
                            </div>
                            <button class="search-button d-flex align-items-center" style="padding: 0" type="submit" @click.prevent="search">
                                <i class="bi bi-search"></i>
                            </button>
                            <button @click.prevent="showFilters=!showFilters" class="search-button d-flex align-items-center">
                                <i class="bi bi-funnel-fill"></i>
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
                    <div class="list-item mt-2" v-for="advertisement in currentAdvertisements" :key="advertisement.id" @click="$router.push(`/advertisements/houses/${advertisement.id}`)">
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
</style>