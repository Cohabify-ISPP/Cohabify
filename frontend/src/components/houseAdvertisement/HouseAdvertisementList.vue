<script setup>
import { ref, onMounted } from 'vue'
import Navbar from '../Navbar.vue'

const price = ref(0)
const meters = ref(0)
const empty = ref(false)
const tenants = ref(0)
const minBathrooms = ref(null)
const maxBathrooms = ref(null)
const minBedrooms = ref(null)
const maxBedrooms = ref(null)
const errors = ref(['priceVal'])
const advertisements = ref([])
const fetchError = ref(null)
const isLoading = ref(true)

onMounted(() => {
    fetch(import.meta.env.VITE_BACKEND_URL+'/api/advertisements/houses', {
        method: "GET",
        headers: {
            'Authentication': 'Bearer ' + sessionStorage.getItem("authentication"),
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
})

const applyFilters = () => {

    if (price.value < 0 || price.value > 5000) {
        errors.value.push('priceVal')
    }

    if (meters.value < 0 || meters.value > 350) {
        errors.value.push('metersVal')
    }

    if (tenants.value < 0 || tenants.value > 10) {
        errors.value.push('tenantsVal')
    }

    if (minBathrooms.value !== null && typeof minBathrooms.value !== 'number' || minBathrooms.value < 0) {
        errors.value.push('minBathroomsVal')
    }

    if (maxBathrooms.value !== null && typeof maxBathrooms.value !== 'number' || maxBathrooms.value < 0) {
        errors.value.push('maxBathroomsVal')
    }

    if (minBathrooms.value > maxBathrooms.value) {
        errors.value.push('minBathroomsVal')
        errors.value.push('maxBathroomsVal')
    }

    if (minBedrooms.value !== null && typeof minBedrooms.value !== 'number' || minBedrooms.value < 0) {
        errors.value.push('minRoomsVal')
    }

    if (maxBedrooms.value !== null && typeof maxBedrooms.value !== 'number' || maxBedrooms.value < 0) {
        errors.value.push('maxRoomsVal')
    }

    if (minBedrooms.value > maxBedrooms.value) {
        errors.value.push('minRoomsVal')
        errors.value.push('maxRoomsVal')
    }

}



</script>
<template>
    <navbar />
        <div class="row">
            <div class="col-md-2 filter-column p-4">
                <form class="needs-validation" novalidate>
                    <!-- Precio -->

                    <div class="d-flex justify-content-between" style="width: 100%; height: 30px;">
                        <div>
                            <p>Max. Precio</p>
                        </div>
                        <button class="btn btn-danger btn-sm rounded-circle " @click.prevent="price = 0">
                            <i class="bi bi-x-lg"></i>
                        </button>
                    </div>
                    <input type="range" class="form-range" min="0" max="5000" step="50" v-model="price" id="priceVal" :class="{'is-invalid': errors.includes('priceVal')}">
                    <b>{{ price == 0 ? '-' : price == 5000 ? '+ ' + price.toString() + '€/mes' : '<= ' + price.toString() + '€/mes' }}</b>

                    <!-- Metros cuadrados -->

                    <div class="mt-3 d-flex justify-content-between" style="width: 100%; height: 30px;">
                        <p>Min. Espacio</p>
                        <button class="btn btn-danger btn-sm rounded-circle " @click.prevent="meters = 0">
                            <i class="bi bi-x-lg"></i>
                        </button>
                    </div>
                    <input type="range" class="form-range" min="0" max="350" step="5" v-model="meters" id="metersVal" :class="{'is-invalid': errors.includes('metersVal')}">
                    <b>{{ meters == 0 ? '-' : '>= ' + meters.toString() + 'm²' }}</b>

                    <!-- Vivienda vacía -->

                    <div class="mt-3">
                        <p>Vivienda vacía</p>
                        <input type="checkbox" class="form-check-input" id="empty" v-model="empty" :class="{'is-invalid': errors.includes('empty')}">
                        <b style="margin-left: 10px;">{{ empty ? 'Sí' : 'No' }}</b>
                    </div>

                    <!-- Inquilinos -->

                    <div v-if="!empty">
                        <div class="mt-3 d-flex justify-content-between" :invalid="true" style="width: 100%; height: 30px;">
                            <p>Max. Inquilinos</p>
                            <button class="btn btn-danger btn-sm rounded-circle " @click.prevent="tenants = 0">
                                <i class="bi bi-x-lg"></i>
                            </button>
                        </div>
                        <input type="range" class="form-range" min="0" max="10" step="1" v-model="tenants" id="tenantsVal" :class="{'is-invalid': errors.includes('tenantsVal')}">
                        <b>{{ tenants == 0 ? '-' : tenants == 10 ? '+ ' + tenants.toString() : '<= ' + tenants.toString() }}</b>
                    </div>

                    <!-- Baños -->

                    <div class="mt-3 d-flex justify-content-between" style="width: 100%; height: 30px;">
                        <p>Baños</p>
                        <button class="btn btn-danger btn-sm rounded-circle " @click.prevent="minBathrooms = null; maxBathrooms = null">
                            <i class="bi bi-x-lg"></i>
                        </button>
                    </div>
                    <div class="d-flex justify-content-between mt-2">
                        <input type="number" class="form-control" v-model="minBathrooms" min="0" id="minBathroomsVal" :class="{'is-invalid': errors.includes('minBathroomsVal')}">
                        <b style="margin: 0 10px;"> - </b>
                        <input type="number" class="form-control" v-model="maxBathrooms" id="maxBathroomsVal" :class="{'is-invalid': errors.includes('maxBathroomsVal')}">
                    </div>

                    <!-- Habitaciones -->

                    <div class="mt-3 d-flex justify-content-between" style="width: 100%; height: 30px;">
                        <p>Habitaciones</p>
                        <button class="btn btn-danger btn-sm rounded-circle " @click.prevent="minBedrooms = null; maxBedrooms = null">
                            <i class="bi bi-x-lg"></i>
                        </button>
                    </div>
                    <div class="d-flex justify-content-between mt-2">
                        <input type="number" class="form-control" v-model="minBedrooms" min="0" id="minRoomsVal" :class="{'is-invalid': errors.includes('minRoomsVal')}">
                        <b style="margin: 0 10px;"> - </b>
                        <input type="number" class="form-control" v-model="maxBedrooms" id="maxRoomsVal" :class="{'is-invalid': errors.includes('maxRoomsVal')}">
                    </div>
                </form>
                <hr>
                <div class="d-flex justify-content-between mb-2">
                    <button class="btn btn-primary" @click="errors=[]; applyFilters()">Aplicar</button>
                    <button class="btn btn-danger" @click="errors=[]; price = 0; meters = 0; empty = false; tenants = 0; minBathrooms = null; maxBathrooms = null; minBedrooms = null; maxBedrooms = null">Borrar</button>
                </div>
            </div>
            <div class="col">
                <div v-if="isLoading" class="spinner-border" role="status">
                    <span class="visually-hidden">Loading...</span>
                </div>
                <div v-else-if="fetchError" class="alert alert-danger" role="alert">
                    {{ fetchError }}
                </div>
                <div class="list-container mt-4" v-else>
                    <div class="list-item mt-2" v-for="advertisement in advertisements" :key="advertisement.id" @click="$router.push(`/advertisements/houses/${advertisement.id}`)">
                        <img :src="advertisement.images[0]" alt="house" class="list-item-image">
                        <div class="list-item-content">
                            <div class="d-flex justify-content-between w-100" style="margin-right: 2vw;">
                                <h3>{{ advertisement.title }}</h3>
                                <h3><b>{{ advertisement.price }}€/mes</b></h3>
                            </div>
                            <b>{{ advertisement.house.location }}</b>
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
                        </div>
                    </div>
                </div>
            </div>
        </div>
</template>