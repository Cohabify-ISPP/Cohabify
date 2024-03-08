<script setup>
import { ref, onMounted } from 'vue'
import { jwtDecode } from 'jwt-decode'

const isLoggedIn = ref(false)

onMounted(() => {
  const token = sessionStorage.getItem("authentication")

  if (token) {
    const decoded = jwtDecode(token)
    const now = Date.now() / 1000
    isLoggedIn.value = decoded.exp > now
  }

})

const logout = () => {
  sessionStorage.setItem("authentication", "")
  isLoggedIn.value = false
}

</script>

<template>
<nav class="navbar navbar-expand navbar-custom sticky-top">
  <div class="container-fluid">

    <a class="navbar-brand mt-2 mt-lg-0" href="/">
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
            <li><a class="dropdown-item" href="/advertisements/houses/new">Vivienda</a></li>
            <li><a class="dropdown-item" href="/advertisements/users/new">Búsqueda</a></li>
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
          <img
            src="/images/user.png"
            class="rounded-circle"
            height="40"
            alt="Black and White Portrait of a Man"
            loading="lazy"
          />
        </a>
        <ul
          class="dropdown-menu dropdown-menu-end"
          aria-labelledby="navbarDropdownMenuAvatar"
        >
          <li>
            <a class="dropdown-item " href="#" >Perfil</a>
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
            <a class="dropdown-item" @click="logout()">Cerrar sesión</a>
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
 .nav-link {
   color: white;
   transition: 0.1s;
 }

 .nav-link:hover {
   color: #a4c7ff;
   transition: 0.2s;
 }
 .dropdown-toggle::after {
  border-top-color: white; /* Cambia esto al color que prefieras */
  border-width: 0.3em; /* Cambia esto al tamaño que prefieras */
}

.dropdown-toggle:hover::after {
  border-top-color: #a4c7ff; /* Cambia esto al color que prefieras */
  transition: 0.2s;
}
</style>
