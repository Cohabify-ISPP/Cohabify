
<script setup>
import { ref } from 'vue'
import Navbar from '../Navbar.vue'

const selectedImage = ref(false)

const images = ref([])


const selectImage = (index) => {
  selectedImage.value = index
}

const handleFileChange = (event) => {
  const data = {
    images: ['/images/uploadImage.png',
  '/images/uploadImage.png',
  '/images/uploadImage.png',
  '/images/uploadImage.png',
  '/images/uploadImage.png']
  }
  const files = event.target.files
  const imageFiles = Array.from(files).filter(file => file.type.startsWith('image/'))

  if (files.length !== imageFiles.length) {
    alert("Sólo se permiten archivos de imagen, por favor seleccione archivos de imagen")
    event.target.value = ''
  }

  if (imageFiles.length > 5) {
    alert('Solo se permiten un máximo de 5 archivos.')
    event.target.value = ''
  } else {
    for (let i = 0; i < data.images.length; i++) {
      if (i < imageFiles.length) {
        const file = imageFiles[i]
        images.value.push(URL.createObjectURL(file)) 
      } else {
        images.value.push('/images/uploadImage.png')
      }
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
                            <div class="carousel-item active" v-for="image in images" :key="image">
                                <img style="object-fit: cover;" :src="image" class="img-fluid" alt="...">
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
                        <div class="col-md-4 mb-4" v-for="(image,index) in images" :key="image">
                            <img :src="image" class="d-block w-100" alt="..." data-bs-target="#imgCarousel" :data-bs-slide-to="index" :@click="selectImage(index)" :class="{'shadow': selectedImage === index }">
                        </div>
                    </div>
                    <div class="mb-3">
                      <label for="formFileMultiple" class="form-label">Seleccione o arrastre hasta 5 fotos</label>
                      <input class="form-control" type="file" id="formFileMultiple" v-on:change="handleFileChange" multiple>
                    </div>
                </div>
            </div>   
        </div>
        
    </div>
</template>


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
