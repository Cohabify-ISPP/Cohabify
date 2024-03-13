<script setup>
import { ref, onMounted, computed, watch } from 'vue'
import { jwtDecode } from 'jwt-decode'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'

const isLoggedIn = ref(false)
const image = ref(null)
const store = useStore()
const user = computed(() => store.state.user);
const BACKEND_URL= import.meta.env.VITE_BACKEND_URL
const router = useRouter()



onMounted(async() => {
  const token = localStorage.getItem("authentication")

  if (token) {
    if (user.value ===null || user.value === undefined || Object.keys(user.value).length === 0) {
      await store.dispatch('cargarUser')
    }
     
    const decoded = jwtDecode(token)
    const now = Date.now() / 1000
    image.value = user.value?.imageUri?.startsWith('/') ? `${BACKEND_URL}${user.value?.imageUri}` : user.value?.imageUri
    isLoggedIn.value = decoded.exp > now

    
    
  }
})

const logout = () => {
  localStorage.removeItem("authentication")
  isLoggedIn.value = false
  store.commit('limpiarUser')
  router.push('/')
}

watch(user, (newValue) => {
  if (newValue !== null && newValue !== undefined && Object.keys(newValue).length !== 0) { 
    image.value = newValue.imageUri.startsWith('/') ? `${BACKEND_URL}${newValue.imageUri}` : newValue.imageUri;
  }
});
</script> 

<template>
<nav class="navbar navbar-expand navbar-dark navbar-custom sticky-top">
  <div class="container-fluid">
    <a class="navbar-brand mt-2 mt-lg-0" href="/" @click.prevent="$router.push('/')">
      <img style="max-height: 35px;"
        src="/images/LogoMonoColor.png"
        alt="Cohabify"
        loading="lazy"
      />
    </a>

    <div class="d-flex align-items-center" v-if="isLoggedIn">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0 d-none d-lg-flex">
        <li class="nav-item">
          <a class="nav-link" href="#"><span class="badge rounded-pill badge-notification bg-danger">1</span> Chat</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">Planes</a>
        </li>
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Publicar Anuncio
          </a>
          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
            <li><a class="dropdown-item" href="/advertisements/houses/new" @click.prevent="$router.push('/advertisements/houses/new')">Vivienda</a></li>
            <li><a class="dropdown-item" href="/advertisements/users/new" @click.prevent="$router.push('/advertisements/users/new')">Perfil</a></li>
          </ul>
        </li>
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Buscar
          </a>
          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
            <li><a class="dropdown-item" href="/advertisements/houses" @click.prevent="$router.push('/advertisements/houses')">Viviendas</a></li>
            <li><a class="dropdown-item" href="/advertisements/users" @click.prevent="$router.push('/advertisements/users')">Compañeros</a></li>
          </ul>
        </li>
      </ul>
      
      <div class="dropdown" style="margin-left: 1vw; margin-right: 1vw;">
        <a
          class="dropdown-toggle d-flex align-items-center hidden-arrow"
          href="#"
          id="navbarDropdownMenuAvatar"
          role="button"
          data-bs-toggle="dropdown"
          aria-expanded="false"
        >
          <img v-if="image"
            :src="image"
            class="rounded-circle"
            height="40"
            width="40"
            alt="avatar"
            loading="lazy"
            style="object-fit: cover;"
          />
          <img v-else
            src="https://st4.depositphotos.com/14903220/22197/v/450/depositphotos_221970610-stock-illustration-abstract-sign-avatar-icon-profile.jpg"
            class="rounded-circle"
            height="40"
            width="40"
            alt="avatar"
            loading="lazy"
            style="object-fit: cover;"
          />
        </a>
        <ul
          class="dropdown-menu dropdown-menu-end"
          aria-labelledby="navbarDropdownMenuAvatar"
        >
          <li>
            <a class="dropdown-item " href="/user/" @click.prevent="$router.push('/user/' + user?.id)">Perfil</a>
          </li>
          <li class="d-lg-none">
            <a class="dropdown-item" href="#">Chat <span class="badge rounded-pill badge-notification bg-danger">1</span></a>
          </li>
          <li class="d-lg-none">
            <a class="dropdown-item" href="#">Publicar Anuncio</a>
          </li>
          <li class="d-lg-none">
            <a class="dropdown-item" href="#">Planes</a>
          </li>
          <li>
            <a class="dropdown-item" @click="logout()" style="cursor:pointer">Cerrar sesión</a>
          </li>
        </ul>
      </div>
    </div>
    <div class="d-flex align-items-center" v-else>
      <a href="/login" class="btn btn-primary">Iniciar sesión</a>
    </div>
  </div>
</nav>
</template>

<style scoped>

 .dropdown-toggle::after {
  border-top-color: white; /* Cambia esto al color que prefieras */
  border-width: 0.3em; /* Cambia esto al tamaño que prefieras */
}

.dropdown-toggle:hover::after {
  border-top-color: #a4c7ff; /* Cambia esto al color que prefieras */
  transition: 0.2s;
}
</style>
