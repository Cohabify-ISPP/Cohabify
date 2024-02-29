<script setup>
import Navbar from './Navbar.vue'
</script>

<template>
  <Navbar />
  <div class="container d-flex align-items-center justify-content-center text-center my-5">
    <h1 color="#282828">Hora de <span class="gradient-text">buscar...</span></h1>
  </div>
  <div class="d-flex flex-row justify-content-center align-items-center">
    <div class="d-flex flex-column align-items-center" style="padding-right: 2rem;">
      <button class="btn-primary btn-circle btn-xl" @click="this.$router.push('/houses')">
        <i class="bi bi-houses h1"></i>
      </button>
      <b class="mt-3">Viviendas</b>
    </div>
    <div class="d-flex flex-column align-items-center" style="padding-left: 2rem;">
      <button class="btn-primary btn-circle btn-xl">
        <i class="bi bi-people h1"></i>
      </button>
      <b class="mt-3">Compañeros</b>
    </div>
  </div>
  <div style="margin-top: 10vh;">
    <div class="justify-content-start mt-6 mb-4">
      <h3>¡No te pierdas esto!</h3>
    </div>
    <div class="d-flex align-items-end justify-content-center">
    <div class="card mb-3 bg-dark" style="max-width: 540px;">
      <div class="row g-0">
        <div class="col-md-8">
          <div class="card-body">
            <h3 class="card-title text-white">Plan <span class="gradient-text">Explorador</span></h3>            
            <p class="card-text text-white">Sé el primero en descubrir a tu nuevo compañero con el plan de explorador.</p>
            <button type="button" class="btn btn-success">Ver planes</button>
          </div>
        </div>
        <div class="col-md-4">
          <img src="/images/explorador.png" class="img-fluid rounded-start" alt="...">
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
h3 > .gradient-text {
  background: radial-gradient(circle, gold, orange);
  -webkit-background-clip: text;
  background-clip: text;
  color: transparent;
}
</style>