<script setup>
import Navbar from '../Navbar.vue'
const img = '/images/uploadImage.png';
</script>

<template>
  <Navbar />
  <div class="container text-center my-5">
    <div class="row">
      <div class="col">
        <div id="carouselExampleAutoplaying" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-inner">
              <div class="carousel-item active">
                <img :src="img" class="d-block w-100" alt="...">
              </div>
              <div class="carousel-item">
                <img :src="img" class="d-block w-100" alt="...">
              </div>
              <div class="carousel-item">
                <img :src="img" class="d-block w-100" alt="...">
              </div>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExample" data-bs-slide="prev">
              <span class="carousel-control-prev-icon" aria-hidden="true"></span>
              <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselExample" data-bs-slide="next">
              <span class="carousel-control-next-icon" aria-hidden="true"></span>
              <span class="visually-hidden">Next</span>
            </button>
          </div>
      </div>
      <div class="col">
        <div class="container text-center">
        <form @submit.prevent="submitForm">
            <div class="mb-3">
                <label for="title" class="form-label">Nombre</label>
                <input type="text" class="form-control" id="title" v-model="advertisement.title">
            </div>
            <div class="mb-3">
                <label for="price" class="form-label">Precio</label>
                <input type="number" class="form-control" id="price" v-model="advertisement.price">
            </div>
            <div class="mb-3">
                <label for="location" class="form-label">Ubicación</label>
                <input type="text" class="form-control" id="location" v-model="advertisement.location">
            </div>
            <div class="row align-items-start">
                    <div class="col">
                        <label for="bedrooms" class="form-label">Habitaciones</label>
                        <input type="number" class="form-control" id="bedrooms" v-model="advertisement.bedrooms">
                    </div>
                    <div class="col">
                        <label for="bathrooms" class="form-label">Baños</label>
                        <input type="number" class="form-control" id="bathrooms" v-model="advertisement.bathrooms">
                    </div>
                    <div class="col">
                        <label for="squareMeters" class="form-label">Metros cuadrados</label>
                        <input type="number" class="form-control" id="squareMeters" v-model="advertisement.squareMeters">
                    </div>
            </div>
            <div class="row align-items-start">
                    <div class="col">
                        <label for="heatingGas" class="form-label">Calefacción de gas</label>
                        <input type="checkbox" class="form-check-input" id="heatingGas" v-model="advertisement.heatingGas">
                    </div>
                    <div class="col">
                        <label for="floors" class="form-label">Plantas</label>
                        <input type="number" class="form-control" id="floors" v-model="advertisement.floors">
                    </div>
                    <div class="col">
                        <label for="elevator" class="form-label">Ascensor</label>
                        <input type="checkbox" class="form-check-input" id="elevator" v-model="advertisement.elevator">
                    </div>
            </div>
            <div class="row align-items-start">
                    <div class="col">
                        <label for="tenants" class="form-label">Inquilinos</label>
                        <input type="number" class="form-control" id="tenants" v-model="advertisement.tenants">
                    </div>
                    <div class="col">
                        <label for="tags" class="form-label">Etiquetas</label>
                        <input type="text" class="form-control" id="tags" v-model="advertisement.tags">
                    </div>
            </div>
            <div class="mb-3">
                <label for="description" class="form-label">Descripción</label>
                <textarea class="form-control" id="description" rows="3" v-model="advertisement.description"></textarea>
            </div>
            <button type="submit" class="btn btn-primary">Crear anuncio</button>
        </form>

      </div>
    </div>
  </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      advertisement: {
        title: '',
        price: 0,
        location: '',
        bedrooms: 0,
        bathrooms: 0,
        squareMeters: 0,
        heatingGas: false,
        floors: 0,
        elevator: false,
        tenants: 0,
        tags: '',
        description: ''
      }
    }
  },
  methods: {
    submitForm() {
      // Aquí puedes hacer la llamada a la API para crear el anuncio
      console.log(this.advertisement);
    }
  }
}
</script>

<style scoped>
h1 > .gradient-text {
  background: radial-gradient(circle, rgb(168, 126, 237), rgb(64, 105, 255));
  -webkit-background-clip: text;
  background-clip: text;
  color: transparent;
}
.mb-3 {
  /* Añade aquí los estilos que quieras aplicar a todos los divs */
  border: 1px solid #ccc;
  padding: 10px;
  border-radius: 5px;
}
</style>
<style scoped>
h1 > .gradient-text {
  background: radial-gradient(circle, rgb(168, 126, 237), rgb(64, 105, 255));
  -webkit-background-clip: text;
  background-clip: text;
  color: transparent;
}
</style>
