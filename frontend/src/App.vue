<script setup>
import HelloWorld from './components/HelloWorld.vue'
import { ref, onMounted } from 'vue'

const VITE_BACKEND_URL = import.meta.env.VITE_BACKEND_URL
console.log('VITE_BACKEND_URL:', VITE_BACKEND_URL)


</script>

<template>
  <div>
    <a href="https://vitejs.dev" target="_blank">
      <img src="/vite.svg" class="logo" alt="Vite logo" />
    </a>
    <a href="https://vuejs.org/" target="_blank">
      <img src="./assets/vue.svg" class="logo vue" alt="Vue logo" />
    </a>
    <a href="https://getbootstrap.com/" target="_blank">
      <i class="bi bi-bootstrap-fill h1"></i>
    </a>
  </div>
  <div class="card">
    <h1>Vite + Vue + Bootstrap + Bootstrap Icons <i class="bi bi-heart-fill"></i></h1>
  </div>
  <div class="card mt-2">
    <h1>Backend URL: {{ VITE_BACKEND_URL }}</h1>
  </div>
  <HelloWorld />
</template>

<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style>