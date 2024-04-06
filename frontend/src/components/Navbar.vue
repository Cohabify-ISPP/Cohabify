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
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Buscar
          </a>
          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
            <li><a class="dropdown-item" href="/advertisements/houses" @click.prevent="$router.push('/advertisements/houses')">Viviendas</a></li>
            <li><a class="dropdown-item" href="/advertisements/users" @click.prevent="$router.push('/advertisements/users')">Compañeros</a></li>
          </ul>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="/plan">Planes</a>
        </li>
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Anuncios de vivienda
          </a>
          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
            <li><a class="dropdown-item" @click.prevent="$router.push('/myAdvertisements/house')">Ver publicados</a></li>
            <li v-if="user?.plan === 'owner'"><a class="dropdown-item" href="/advertisements/houses/new" @click.prevent="$router.push('/advertisements/houses/new')">Publicar</a></li>           
          </ul>
        </li>

        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            Anuncios de compañero
          </a>
          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
            <li><a class="dropdown-item" @click.prevent="findMyUserAd">Ver publicado</a></li>
            <li><a class="dropdown-item" href="/advertisements/users/new" @click.prevent="$router.push('/advertisements/users/myAdvertisement')">Publicar/Editar</a></li>
          </ul>
        </li>
        
      </ul>
      
      <div class="dropdown navbar-nav" style="margin-left: 1vw; margin-right: 1vw">
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
            style="max-height: 35px;"
            src="/images/verificado.png"
            loading="lazy"
          />
        </a>
        <ul
          class="dropdown-menu dropdown-menu-end"
          aria-labelledby="navbarDropdownMenuAvatar"
        >
          <li>
            <a class="dropdown-item" href="/user/" @click.prevent="$router.push('/user/' + user?.id)">Perfil</a>
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
    <div class="d-flex align-items-center" v-else style="padding-right: 1%;">
      <a href="/login" class="text-decoration-none">  
        <button class="login">
    
          <div class="sign"><svg viewBox="0 0 512 512"><path d="M217.9 105.9L340.7 228.7c7.2 7.2 11.3 17.1 11.3 27.3s-4.1 20.1-11.3 27.3L217.9 406.1c-6.4 6.4-15 9.9-24 9.9c-18.7 0-33.9-15.2-33.9-33.9l0-62.1L32 320c-17.7 0-32-14.3-32-32l0-64c0-17.7 14.3-32 32-32l128 0 0-62.1c0-18.7 15.2-33.9 33.9-33.9c9 0 17.6 3.6 24 9.9zM352 416l64 0c17.7 0 32-14.3 32-32l0-256c0-17.7-14.3-32-32-32l-64 0c-17.7 0-32-14.3-32-32s14.3-32 32-32l64 0c53 0 96 43 96 96l0 256c0 53-43 96-96 96l-64 0c-17.7 0-32-14.3-32-32s14.3-32 32-32z"></path></svg></div>
          
          <div class="text">Iniciar sesión</div>
        </button>
      </a>
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

.login {
  display: flex;
  align-items: center;
  justify-content: flex-start;
  width: 50px;
  height: 50px;
  border: none;
  border-radius: 50%;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition-duration: .3s;
  box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.199);
  background-color: rgb(154, 171, 216);
}

/* plus sign */
.sign {
  width: 100%;
  transition-duration: .3s;
  display: flex;
  align-items: center;
  justify-content: center;
}

.sign svg {
  width: 17px;
}

.sign svg path {
  fill: white;
}
/* text */
.text {
  position: relative;
  right: 0%;
  width: 0%;
  opacity: 0;
  color: white;
  font-size: 1.2em;
  font-weight: 600;
  transition-duration: .3s;
}
/* hover effect on button  width */
.login:hover {
  width: 200px;
  border-radius: 40px;
  transition-duration: .3s;
}

.login:hover .sign {
  width: 30%;
  transition-duration: .3s;
  padding-left: 20px;
}
/* hover effect button's text */
.login:hover .text {
  opacity: 1;
  width: 70%;
  transition-duration: .3s;
  padding-right: 10px;
}
/* button click effect*/
.login:active {
  transform: translate(2px ,2px);
}

</style>
