<script setup>

</script>

<template>
  <div class="container d-flex align-items-center justify-content-center text-center my-5">
    <h1 color="#282828">Hora de buscar...</h1>
  </div>
  <div class="d-flex flex-row justify-content-between align-items-center" style="max-width: 30%; margin-left: auto; margin-right: auto;">
    <div class="d-flex flex-column align-items-center">
      <button class="btn btn-primary btn-circle btn-xl">
        <i class="bi bi-houses h1"></i>
      </button>
      <b>Viviendas</b>
    </div>
    <div class="d-flex flex-column align-items-center">
      <button class="btn btn-primary btn-circle btn-xl">
        <i class="bi bi-people h1"></i>
      </button>
      <b>Compañeros</b>
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
            <h3 class="card-title text-white">Plan Explorador</h3>
            <p class="card-text text-white">Sé el primero en descubrir a tu nuevo compañero con 
            el plan de explorador.</p>
            <button type="button" class="btn btn-success">Ver planes</button>
          </div>
        </div>
        <div class="col-md-4">
          <img src="/explorador.png" class="img-fluid rounded-start" alt="...">
        </div>
      </div>
    </div>
  </div>
  </div>
</template>

<style scoped>

</style>