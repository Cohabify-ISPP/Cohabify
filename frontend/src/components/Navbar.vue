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
    if(decoded.exp < now) {
      logout()
    } else{
      isLoggedIn.value = true
    }

  }
})

const logout = () => {
  localStorage.removeItem("authentication")
  isLoggedIn.value = false
  store.commit('limpiarUser')
  router.push('/')
}

const findMyUserAd = async () => {
  try {
    const response = await fetch(import.meta.env.VITE_BACKEND_URL + `/api/advertisements/users/myAdvertisement/${user.value.id}`,
        {
            method: "GET",
            headers: {
                'Authentication': 'Bearer ' + localStorage.getItem("authentication"),
            },
            credentials: "include",
        });

        if (response.ok) {
          const data = await response.json();
          router.push(`/advertisements/users/${data.id}`);
        } else{
          if (response.status === 404){
          router.push("/advertisements/users/myAdvertisement")
          } else {
            console.error("Error:", response);
          }
        }

    } catch (error) {
    if (error.response.status === 404) {
      router.push("/advertisements/users/myAdvertisement")
    } else {
        console.error("Error:", error);
    }
  }
}


watch(user, (newValue) => {
  if (newValue !== null && newValue !== undefined && Object.keys(newValue).length !== 0) { 
    image.value = newValue.imageUri.startsWith('/') ? `${BACKEND_URL}${newValue.imageUri}` : newValue.imageUri;
  }
});
</script> 

<template>
<nav class="navbar navbar-expand navbar-dark navbar-custom sticky-top">
  <div class="container-fluid" >
    <a class="navbar-brand mt-2 mt-lg-0" href="/" @click.prevent="$router.push('/')">
      <img style="max-height: 35px;"
        src="/images/LogoMonoColor.png"
        alt="Cohabify"
        loading="lazy"
      />
    </a>
    <div class="d-flex"  style="margin-right: 0px;">
    <div class="d-flex align-items-center">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0 d-none d-lg-flex">
        <li class="nav-item" v-if="isLoggedIn" style="padding-right:1%">
          <a class="nav-link" href="/chat">
              <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-chat-left-text " viewBox="0 0 16 16">
                <path d="M14 1a1 1 0 0 1 1 1v8a1 1 0 0 1-1 1H4.414A2 2 0 0 0 3 11.586l-2 2V2a1 1 0 0 1 1-1zM2 0a2 2 0 0 0-2 2v12.793a.5.5 0 0 0 .854.353l2.853-2.853A1 1 0 0 1 4.414 12H14a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2z"/>
                <path d="M3 3.5a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5M3 6a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9A.5.5 0 0 1 3 6m0 2.5a.5.5 0 0 1 .5-.5h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1-.5-.5"/>
              </svg>
          </a>
        </li>
        <li class="nav-item dropdown pr-4" style="padding-right: 1%;">
          <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
            <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0"/>
          </svg>
          </a>
          <ul class="dropdown-menu " aria-labelledby="navbarDropdown">
            <li><a class="dropdown-item" href="/advertisements/houses" @click.prevent="$router.push('/advertisements/houses')">Viviendas</a></li>
            <li><a class="dropdown-item" href="/advertisements/users" @click.prevent="$router.push('/advertisements/users')">Compañeros</a></li>
          </ul>
        </li>
       
        <li class="nav-item dropdown" v-if="isLoggedIn">
          <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Anuncios de vivienda
          </a>
          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
            <li><a class="dropdown-item" @click.prevent="$router.push('/myAdvertisements/house')">Ver publicados</a></li>
            <li v-if="user?.plan === 'owner'"><a class="dropdown-item" href="/advertisements/houses/new" @click.prevent="$router.push('/advertisements/houses/new')">Publicar</a></li>           
          </ul>
        </li>

        <li class="nav-item dropdown" v-if="isLoggedIn">
          <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Anuncios de compañero
          </a>
          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
            <li><a class="dropdown-item" @click.prevent="findMyUserAd">Ver publicado</a></li>
            <li><a class="dropdown-item" href="/advertisements/users/new" @click.prevent="$router.push('/advertisements/users/myAdvertisement')">Publicar/Editar</a></li>
          </ul>
        </li>
        <li class="nav-item" v-if="isLoggedIn">
          <a class="nav-link" href="/plan">Planes</a>
        </li>
        
      </ul>
      
      <div class="dropdown navbar-nav" style="margin-left: 1vw; margin-right: 0px" v-if="isLoggedIn">
        <a
          class="nav-link dropdown-toggle d-flex align-items-center hidden-arrow"
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
            style="object-fit: cover; margin-right: 0.6vw;"
          />
          <img v-else
            src="https://st4.depositphotos.com/14903220/22197/v/450/depositphotos_221970610-stock-illustration-abstract-sign-avatar-icon-profile.jpg"
            class="rounded-circle"
            height="40"
            width="40"
            alt="avatar"
            loading="lazy"
            style="object-fit: cover; margin-right: 0.6vw;"
          />
          {{ user?.username }}
          <img v-if="user?.plan === 'explorer'" 
            style="max-height: 35px; margin-left: 0.3vw;"
            src="/images/verificadoBlanco.png"
            loading="lazy"
          />
        </a>
        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdownMenuAvatar">
          <li>
            <a class="dropdown-item" href="/user/" @click.prevent="$router.push('/user/' + user?.id)">Perfil</a>
          </li>
          <li class="d-lg-none">
            
            <a class="nav-link" href="/chat">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chat-left-text" viewBox="0 0 16 16">
                <path d="M14 1a1 1 0 0 1 1 1v8a1 1 0 0 1-1 1H4.414A2 2 0 0 0 3 11.586l-2 2V2a1 1 0 0 1 1-1zM2 0a2 2 0 0 0-2 2v12.793a.5.5 0 0 0 .854.353l2.853-2.853A1 1 0 0 1 4.414 12H14a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2z"/>
                <path d="M3 3.5a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5M3 6a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9A.5.5 0 0 1 3 6m0 2.5a.5.5 0 0 1 .5-.5h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1-.5-.5"/>
              </svg></a>


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
    <div class="d-flex align-items-center" v-if="!isLoggedIn">
      <a href="/login" class="text-decoration-none">
          <button style="width:100%; height: 10%;">Iniciar sesión
              <div class="arrow-wrapper">
                  <div class="arrow"></div>
              </div>
          </button>
       </a>
    </div>
  </div>
  </div>
</nav>
</template>

<style scoped>

 .dropdown-toggle::after {
  border-top-color: white;
  border-width: 0.3em;
}

.dropdown-toggle:hover::after {
  border-top-color: #a4c7ff;
  transition: 0.2s;
}

.dropdown::after {
  border-top-color: white;
  border-width: 0.3em;
}

.dropdown:hover::after {
  border-top-color: #a4c7ff;
  transition: 0.2s;
}

button {
  --primary-color: #a4c7ff;
  --secondary-color: #28426b;
  --hover-color: #ffffff;
  --arrow-width: 10px; 
  --arrow-stroke: 2px; 
  border: 0;
  border-radius: 20px; 
  color: var(--secondary-color);
  padding: 0.5em 1.2em; 
  background: var(--primary-color);
  display: flex;
  transition: 0.2s background;
  align-items: center;
  gap: 0.3em; 
  font-weight: bold;
}

button .arrow-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
}

button .arrow {
  margin-top: 1px;
  width: var(--arrow-width);
  background: var(--primary-color);
  height: var(--arrow-stroke);
  position: relative;
  transition: 0.2s;
}

button .arrow::before {
  content: "";
  box-sizing: border-box;
  position: absolute;
  border: solid var(--secondary-color);
  border-width: 0 var(--arrow-stroke) var(--arrow-stroke) 0;
  display: inline-block;
  top: -3px; 
  right: 3px; 
  transition: 0.2s;
  padding: 3px; 
  transform: rotate(-45deg);
  font-weight: bold;
    
}

button:hover {
  background-color: var(--hover-color);
}

button:hover .arrow {
  background: var(--secondary-color);
}

button:hover .arrow:before {
  right: 0;
}

.dropdown-item:active {
  background-color: #28426b;
}
</style>
