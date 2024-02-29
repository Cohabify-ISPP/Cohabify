<script setup>
import { ref, defineExpose } from 'vue'
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

defineExpose({
  applyFilters
})



</script>
<template>
    <navbar />
    <div class="col-2 filter-column justify-content-between">
        <form class="needs-validation" novalidate>
            <h3>Filtros</h3>
            <hr>

            <!-- Precio -->

            <div class="mt-4 d-flex justify-content-between" style="width: 100%;">
                <h5>Max. Precio</h5>
                <button class="btn btn-danger btn-sm rounded-circle " @click.prevent="price = 0">
                    <i class="bi bi-x-lg"></i>
                </button>
            </div>
            <input type="range" class="form-range" min="0" max="5000" step="50" v-model="price" id="priceVal" :class="{'is-invalid': errors.includes('priceVal')}">
            <b>{{ price == 0 ? '-' : price == 5000 ? '+ ' + price.toString() + '€' : '<= ' + price.toString() + '€' }}</b>

            <!-- Metros cuadrados -->

            <div class="mt-4 d-flex justify-content-between">
                <h5>Min. Metros cuadrados</h5>
                <button class="btn btn-danger btn-sm rounded-circle " @click.prevent="meters = 0">
                    <i class="bi bi-x-lg"></i>
                </button>
            </div>
            <input type="range" class="form-range" min="0" max="350" step="5" v-model="meters" id="metersVal" :class="{'is-invalid': errors.includes('metersVal')}">
            <b>{{ meters == 0 ? '-' : '>= ' + meters.toString() + 'm²' }}</b>

            <!-- Vivienda vacía -->

            <div class="mt-4">
                <h5>Vivienda vacía</h5>
                <input type="checkbox" class="form-check-input" id="empty" v-model="empty" :class="{'is-invalid': errors.includes('empty')}">
                <b style="margin-left: 10px;">{{ empty ? 'Sí' : 'No' }}</b>
            </div>

            <!-- Inquilinos -->

            <div v-if="!empty">
                <div class="mt-4 d-flex justify-content-between" :invalid="true">
                    <h5>Max. Inquilinos</h5>
                    <button class="btn btn-danger btn-sm rounded-circle " @click.prevent="tenants = 0">
                        <i class="bi bi-x-lg"></i>
                    </button>
                </div>
                <input type="range" class="form-range" min="0" max="10" step="1" v-model="tenants" id="tenantsVal" :class="{'is-invalid': errors.includes('tenantsVal')}">
                <b>{{ tenants == 0 ? '-' : tenants == 10 ? '+ ' + tenants.toString() : '<= ' + tenants.toString() }}</b>
            </div>

            <!-- Baños -->

            <div class="mt-4 d-flex justify-content-between">
                <h5>Baños</h5>
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

            <div class="mt-4 d-flex justify-content-between">
                <h5>Habitaciones</h5>
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
        <div class="d-flex justify-content-between mb-2 mt-5">
            <button class="btn btn-primary" @click="errors=[]; applyFilters()">Aplicar filtros</button>
            <button class="btn btn-danger" @click="price = 0; meters = 0; empty = false; tenants = 0; minBathrooms = null; maxBathrooms = null; minBedrooms = null; maxBedrooms = null">Limpiar filtros</button>
        </div>
    </div>
    <div class="col-10 list-container">
        
    </div>
</template>