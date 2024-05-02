<script setup>
import Navbar from './Navbar.vue'
import { computed, ref, onMounted } from 'vue'
import { useStore } from "vuex";

const store = useStore()
const currentUser = computed(() => store.state.user);

const showMap = ref(false);
const locations = ref([]);
const auth = ref({});

function onClickShowMap() {
  showMap.value = !showMap.value;
  if (showMap.value) {
    fetchLocations();
  }
}

const updateMeta = (title, description) => {
  document.querySelector('meta[name="description"]').setAttribute('content', description);
  document.querySelector('meta[property="og:title"]').setAttribute('content', title);
  document.querySelector('meta[property="og:description"]').setAttribute('content', description);
};

onMounted(() => {
  updateMeta('Buscar Viviendas y Compañeros', 'Explora viviendas y encuentra compañeros de piso ideales en tu área. Utiliza nuestro mapa interactivo para descubrir opciones.');
});

const fetchLocations = async () => {
  try {
    const userFetch = await fetch(
      import.meta.env.VITE_BACKEND_URL + "/auth/getUser",
      {
        method: "POST",
        headers: {
          "Authentication":
            "Bearer " + localStorage.getItem("authentication"),
        },
      }
    );

    const userData = await userFetch.json();
    auth.value = userData;

    const response = await fetch(import.meta.env.VITE_BACKEND_URL + `/api/advertisements/houses`,
      {
        method: "GET",
        headers: {
          'Authentication': 'Bearer ' + localStorage.getItem("authentication"),
        },
        credentials: "include",
      });

    if (response.ok) {
      const data = await response.json();
      locations.value = data;

    } else {
      window.location.href = "/404";
    }

  } catch (error) {
    console.error("Error:", error);
  }
};

const mapCenter = ref({ lat: 40.416775, lng: -3.703790 });

const setPlace = (place) => {
  mapCenter.value = {
    lat: place.geometry.location.lat(),
    lng: place.geometry.location.lng(),
  };
}
</script>

<template>
  <Navbar />
  <div class="d-flex flex-column" style="min-height: 90vh;">
    <div class="container d-flex align-items-center justify-content-center text-center my-5">
      <h1 color="#282828">Hora de <span class="gradient-text">buscar...</span></h1>
    </div>
    <div class="d-flex flex-row justify-content-center align-items-center">
      <div class="d-flex flex-column align-items-center" style="padding-right: 2rem;">
        <button class="btn-primary btn-circle btn-xl"
          style="width: 200px; height: 200px; font-size: 36px; border-radius: 50%;"
          @click="$router.push('/advertisements/houses')">
          <i class="bi bi-houses h1" style="font-size: 2em"></i>
        </button>
        <b class="mt-3" style="font-size: 1.5em">Viviendas</b>
      </div>
      <div class="d-flex flex-column align-items-center" style="padding-left: 2rem;">
        <button class="btn-primary btn-circle btn-xl"
          style="width: 200px; height: 200px; font-size: 36px; border-radius: 50%;"
          @click="$router.push('/advertisements/users')">
          <i class="bi bi-people h1" style="font-size: 2em"></i>
        </button>
        <b class="mt-3" style="font-size: 1.5em">Compañeros</b>
      </div>
    </div>
    <div class="mb-1 mt-3">
      <!-- meter aquí relleno para que no quede tan vacío-->
      <a class="btn btn-primary" @click="onClickShowMap" v-if="!showMap"
        style="background: radial-gradient(circle, rgb(168, 126, 237), rgb(64, 105, 255));margin-top:15px; margin-bottom:15px"><b>Mostrar
          pisos en el mapa</b></a>
      <a class="btn btn-primary" @click="onClickShowMap" v-else
        style="background: radial-gradient(circle, rgb(168, 126, 237), rgb(64, 105, 255));margin-top:15px; margin-bottom:15px"><b>Ocultar
          mapa</b></a>

      <div class="d-flex align-items-end justify-content-center">
        <GMapAutocomplete placeholder="Busca cualquier lugar" @place_changed="setPlace"
          style="margin-right: 20px; padding: 10px; margin-top: 15px;width: 40vh;background-color: white; color: black"
          v-if="showMap"></GMapAutocomplete>
      </div>
      <div class="d-flex align-items-end justify-content-center">
        <GMapMap :center="mapCenter" :zoom="12" map-type-id="roadmap"
          style="height: 60vh; width: 90vh; padding: 10px; margin-top: 10px; border-radius: 10px;" v-if="showMap">
          <GMapMarker v-for="(a, index) in locations" :key="a.id"
            :position="{ lat: a.house.locationPoint.x, lng: a.house.locationPoint.y }"
            @click="$router.push(`/advertisements/houses/${a.id}`)" />
        </GMapMap>
      </div>
    </div>

    <div class="justify-content-start mt-6 mb-3">
      <h3 style="font-size: 2em">¡No te pierdas esto!</h3>
    </div>
    <div class="d-flex justify-content-center align-items-end">
    <div id="adCarousel" class="carousel slide " data-bs-ride="carousel" style="max-width: 685px;">
      <div class="carousel-inner">
        <div class="carousel-item active">
          <div class="d-block card mb-3 bg-dark " style="max-width: 680px;">
            <div class="row g-0">
              <div class="col-md-8 ">
                <div class="card-body">
                  <h3 class="card-title text-white" style="font-size: 1.8em">Plan <span class="gradient-text-basic">Básico</span></h3>
                  <br>
                  <p class="card-text text-white" style="font-size: 1.1em;">Encuenta a tu compañero gemelo, entra y descubre los privilegios con el plan básico.</p>
                  <br>
                  <a class="btn btn-success" href="/plan" v-if="currentUser.username" style="font-size: 1.1em">Ver
                    planes</a>
                  <a class="btn btn-success" href="/login" v-else>Ver planes</a>
                </div>
              </div>
              <div class="col-md-4">
                <img src="/images/basico.png" class="img-fluid rounded-start" alt="...">
              </div>
            </div>
          </div>
        </div>
        <div class="carousel-item">
          <div class="d-block card mb-3 bg-dark " style="max-width: 680px;">
            <div class="row g-0">
              <div class="col-md-8 ">
                <div class="card-body">
                  <h3 class="card-title text-white" style="font-size: 1.8em">Plan <span
                      class="gradient-text">Explorador</span></h3>
                  <br>
                  <p class="card-text text-white" style="font-size: 1.1em;">Sé el primero en descubrir a tu nuevo compañero
                    con el plan de explorador.</p>
                  <br>
                  <a class="btn btn-success" href="/plan" v-if="currentUser.username" style="font-size: 1.1em">Ver
                    planes</a>
                  <a class="btn btn-success" href="/login" v-else>Ver planes</a>
                </div>
              </div>
              <div class="col-md-4">
                <img src="/images/explorador.png" class="img-fluid rounded-start" alt="...">
              </div>
            </div>
          </div>
        </div>
        <div class="carousel-item">
          <div class="d-block card mb-3 bg-dark" style="max-width: 680px;">
            <div class="row g-0">
              <div class="col-md-8 ">
                <div class="card-body">
                  <h3 class="card-title text-white" style="font-size: 1.8em">Plan <span
                      class="gradient-text">Propietario</span></h3>
                  <br>
                  <p class="card-text text-white" style="font-size: 1.1em;">Sé el primero en descubrir a tu nuevo compañero
                    con el plan de explorador.</p>
                  <br>
                  <a class="btn btn-success" href="/plan" v-if="currentUser.username" style="font-size: 1.1em">Ver
                    planes</a>
                  <a class="btn btn-success" href="/login" v-else>Ver planes</a>
                </div>
              </div>
              <div class="col-md-4">
                <img src="/images/explorador.png" class="img-fluid rounded-start" alt="...">
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  </div>
  <div class="mt-16"></div>
  <div class="relative container-fluid text-start"
    style="background: rgba(71, 94, 132, 0.2); padding-left: 30%; padding-top: 1%;">
    <div class="row pt-2">
      <div class="col">
        <div class="text ">
          <a class="text-dark text-decoration-none fs-3">
            Cohabify
          </a>
        </div>
      </div>
      <div class="col">
        <div>
          <p class=" font-weight text-dark text-decoration-none fs-3">
            <a>
              Soporte
            </a>
          </p>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="mt-2 col">
        <div class="mt-8 fs-5">
          Porque es más fácil vivir con quien conectas.
        </div>
      </div>
      <div class="mt-2 col">
        <div class="mt-8 fs-5 ">
          <a href="https://cohabify.github.io/" class="text-dark text-decoration-none">
            Landing Page
          </a>
        </div>
      </div>
    </div>
    <div class="row">
      <div class="col mt-2">
        <div class="row" style="padding-left:2%; padding-top: 2%;">
          <!-- social links -->

          <div class="social-card">
            <button class="Btn github" onclick="location.href='https://github.com/Cohabify/Cohabify'">
              <span class="svgContainer">
                <svg height="1.6em" viewBox="0 0 496 512" fill="white">
                  <path
                    d="M165.9 397.4c0 2-2.3 3.6-5.2 3.6-3.3.3-5.6-1.3-5.6-3.6 0-2 2.3-3.6 5.2-3.6 3-.3 5.6 1.3 5.6 3.6zm-31.1-4.5c-.7 2 1.3 4.3 4.3 4.9 2.6 1 5.6 0 6.2-2s-1.3-4.3-4.3-5.2c-2.6-.7-5.5.3-6.2 2.3zm44.2-1.7c-2.9.7-4.9 2.6-4.6 4.9.3 2 2.9 3.3 5.9 2.6 2.9-.7 4.9-2.6 4.6-4.6-.3-1.9-3-3.2-5.9-2.9zM244.8 8C106.1 8 0 113.3 0 252c0 110.9 69.8 205.8 169.5 239.2 12.8 2.3 17.3-5.6 17.3-12.1 0-6.2-.3-40.4-.3-61.4 0 0-70 15-84.7-29.8 0 0-11.4-29.1-27.8-36.6 0 0-22.9-15.7 1.6-15.4 0 0 24.9 2 38.6 25.8 21.9 38.6 58.6 27.5 72.9 20.9 2.3-16 8.8-27.1 16-33.7-55.9-6.2-112.3-14.3-112.3-110.5 0-27.5 7.6-41.3 23.6-58.9-2.6-6.5-11.1-33.3 2.6-67.9 20.9-6.5 69 27 69 27 20-5.6 41.5-8.5 62.8-8.5s42.8 2.9 62.8 8.5c0 0 48.1-33.6 69-27 13.7 34.7 5.2 61.4 2.6 67.9 16 17.7 25.8 31.5 25.8 58.9 0 96.5-58.9 104.2-114.8 110.5 9.2 7.9 17 22.9 17 46.4 0 33.7-.3 75.4-.3 83.6 0 6.5 4.6 14.4 17.3 12.1C428.2 457.8 496 362.9 496 252 496 113.3 383.5 8 244.8 8zM97.2 352.9c-1.3 1-1 3.3.7 5.2 1.6 1.6 3.9 2.3 5.2 1 1.3-1 1-3.3-.7-5.2-1.6-1.6-3.9-2.3-5.2-1zm-10.8-8.1c-.7 1.3.3 2.9 2.3 3.9 1.6 1 3.6.7 4.3-.7.7-1.3-.3-2.9-2.3-3.9-2-.6-3.6-.3-4.3.7zm32.4 35.6c-1.6 1.3-1 4.3 1.3 6.2 2.3 2.3 5.2 2.6 6.5 1 1.3-1.3.7-4.3-1.3-6.2-2.2-2.3-5.2-2.6-6.5-1zm-11.4-14.7c-1.6 1-1.6 3.6 0 5.9 1.6 2.3 4.3 3.3 5.6 2.3 1.6-1.3 1.6-3.9 0-6.2-1.4-2.3-4-3.3-5.6-2z">
                  </path>
                </svg>
              </span>
              <span class="BG"></span>
            </button>

            <button class="Btn youtube" onclick="location.href='https://www.youtube.com/@Cohabify'">
              <span class="svgContainer">
                <svg viewBox="0 0 576 512" fill="white" height="1.6em" xmlns="http://www.w3.org/2000/svg">
                  <path
                    d="M549.7 124.1c-6.3-23.7-24.8-42.3-48.3-48.6C458.8 64 288 64 288 64S117.2 64 74.6 75.5c-23.5 6.3-42 24.9-48.3 48.6-11.4 42.9-11.4 132.3-11.4 132.3s0 89.4 11.4 132.3c6.3 23.7 24.8 41.5 48.3 47.8C117.2 448 288 448 288 448s170.8 0 213.4-11.5c23.5-6.3 42-24.2 48.3-47.8 11.4-42.9 11.4-132.3 11.4-132.3s0-89.4-11.4-132.3zm-317.5 213.5V175.2l142.7 81.2-142.7 81.2z">
                  </path>
                </svg>
              </span>
              <span class="BG"></span>
            </button>

            <button class="Btn twitter" onclick="location.href='https://twitter.com/Cohabify'">
              <span class="svgContainer">
                <svg xmlns="http://www.w3.org/2000/svg" height="1.6em" fill="currentColor" class="bi bi-twitter" viewBox="0 0 16 16">
                  <path d="M5.026 15c6.038 0 9.341-5.003 9.341-9.334q.002-.211-.006-.422A6.7 6.7 0 0 0 16 3.542a6.7 6.7 0 0 1-1.889.518 3.3 3.3 0 0 0 1.447-1.817 6.5 6.5 0 0 1-2.087.793A3.286 3.286 0 0 0 7.875 6.03a9.32 9.32 0 0 1-6.767-3.429 3.29 3.29 0 0 0 1.018 4.382A3.3 3.3 0 0 1 .64 6.575v.045a3.29 3.29 0 0 0 2.632 3.218 3.2 3.2 0 0 1-.865.115 3 3 0 0 1-.614-.057 3.28 3.28 0 0 0 3.067 2.277A6.6 6.6 0 0 1 .78 13.58a6 6 0 0 1-.78-.045A9.34 9.34 0 0 0 5.026 15"/>
                </svg>
              </span>
              <span class="BG"></span>
            </button>

            <button class="Btn instagram" onclick="location.href='https://www.instagram.com/cohabify.es?utm_source=ig_web_button_share_sheet&igsh=ZDNlZDc0MzIxNw=='">
              <span class="svgContainer">
                <svg xmlns="http://www.w3.org/2000/svg" height="1.6em" fill="currentColor" class="bi bi-instagram" viewBox="0 0 16 16">
                  <path d="M8 0C5.829 0 5.556.01 4.703.048 3.85.088 3.269.222 2.76.42a3.9 3.9 0 0 0-1.417.923A3.9 3.9 0 0 0 .42 2.76C.222 3.268.087 3.85.048 4.7.01 5.555 0 5.827 0 8.001c0 2.172.01 2.444.048 3.297.04.852.174 1.433.372 1.942.205.526.478.972.923 1.417.444.445.89.719 1.416.923.51.198 1.09.333 1.942.372C5.555 15.99 5.827 16 8 16s2.444-.01 3.298-.048c.851-.04 1.434-.174 1.943-.372a3.9 3.9 0 0 0 1.416-.923c.445-.445.718-.891.923-1.417.197-.509.332-1.09.372-1.942C15.99 10.445 16 10.173 16 8s-.01-2.445-.048-3.299c-.04-.851-.175-1.433-.372-1.941a3.9 3.9 0 0 0-.923-1.417A3.9 3.9 0 0 0 13.24.42c-.51-.198-1.092-.333-1.943-.372C10.443.01 10.172 0 7.998 0zm-.717 1.442h.718c2.136 0 2.389.007 3.232.046.78.035 1.204.166 1.486.275.373.145.64.319.92.599s.453.546.598.92c.11.281.24.705.275 1.485.039.843.047 1.096.047 3.231s-.008 2.389-.047 3.232c-.035.78-.166 1.203-.275 1.485a2.5 2.5 0 0 1-.599.919c-.28.28-.546.453-.92.598-.28.11-.704.24-1.485.276-.843.038-1.096.047-3.232.047s-2.39-.009-3.233-.047c-.78-.036-1.203-.166-1.485-.276a2.5 2.5 0 0 1-.92-.598 2.5 2.5 0 0 1-.6-.92c-.109-.281-.24-.705-.275-1.485-.038-.843-.046-1.096-.046-3.233s.008-2.388.046-3.231c.036-.78.166-1.204.276-1.486.145-.373.319-.64.599-.92s.546-.453.92-.598c.282-.11.705-.24 1.485-.276.738-.034 1.024-.044 2.515-.045zm4.988 1.328a.96.96 0 1 0 0 1.92.96.96 0 0 0 0-1.92m-4.27 1.122a4.109 4.109 0 1 0 0 8.217 4.109 4.109 0 0 0 0-8.217m0 1.441a2.667 2.667 0 1 1 0 5.334 2.667 2.667 0 0 1 0-5.334"/>
                </svg>
              </span>
              <span class="BG"></span>
            </button>

            <button class="Btn tiktok" onclick="location.href='https://www.tiktok.com/@cohabify'">
              <span class="svgContainer">
                <svg xmlns="http://www.w3.org/2000/svg" height="1.6em" fill="currentColor" class="bi bi-tiktok" viewBox="0 0 16 16">
                  <path d="M9 0h1.98c.144.715.54 1.617 1.235 2.512C12.895 3.389 13.797 4 15 4v2c-1.753 0-3.07-.814-4-1.829V11a5 5 0 1 1-5-5v2a3 3 0 1 0 3 3z"/>
                </svg>
              </span>
              <span class="BG"></span>
            </button>
            
          </div>

        </div>
      </div>
      <div class=" mt-2 col">
        <div class="mt-8 fs-5 ">
          <a href="mailto: cohabify.soporte@gmail.com" class="text-dark text-decoration-none">
            Contacto
          </a>

        </div>
      </div>
    </div>
    <div class="row" style="padding-top: 1%; padding-bottom: 1%;">
      <div class=" bg-gradient-to-tl from-box-bg py-2 relative col">
        <div>
          <div class="flex justify-between items-center gap-6 md:text-lg text-heading-3">
            <div>
              &copy; <span id="year"></span> Cohabify.
            </div>
          </div>
        </div>
      </div>
      <div class="col">

      </div>
    </div>
  </div>
</template>

<style scoped>
h1>.gradient-text {
  background: radial-gradient(circle, rgb(168, 126, 237), rgb(64, 105, 255));
  -webkit-background-clip: text;
  background-clip: text;
  color: transparent;
}

h3>.gradient-text {
  background: radial-gradient(circle, gold, orange);
  -webkit-background-clip: text;
  background-clip: text;
  color: transparent;
}

.enlaces {
  color: black;
  text-decoration: none;
}

.enlaces:hover {
  color: rgb(37, 37, 233);
}

.social-card {
  height: fit-content;
  width: fit-content;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 20px;
  background: rgb(255, 255, 255);
  padding: 2% 2%;
  border-radius: 14px;
}

.Btn {
  width: 40%;
  height: 40%;
  display: flex;
  align-items: center;
  justify-content: center;
  border: none;
  background-color: transparent;
  position: relative;
  border-radius: 35%;
  cursor: pointer;
  transition: all 0.3s;
}

.svgContainer {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: transparent;
  backdrop-filter: blur(1%);
  letter-spacing: 1%;
  border-radius: 40%;
  transition: all 0.3s;
  border: 1% solid rgba(156, 156, 156, 0.466);
  z-index: 2;
}

.BG {
  position: absolute;
  width: 100%;
  height: 100%;
  background: #181818;
  z-index: 1;
  border-radius: 10%;
  pointer-events: none;
  transition: all 0.3s;
  font-size: 40%;
}

.github .BG {
  background: #2d2c2c;
}

.youtube .BG {
  background: #bb0000;
}

.twitter .BG {
  background: #1da1f2;
}

.instagram .BG {
  background: #e4405f;
}

.tiktok .BG {
  background: #010101;
}

.Btn:hover .BG {
  transform: scale(1.2);
  font-size: 5%;
  color: white;
}

.gradient-text-basic {
  background: radial-gradient(circle, rgb(168, 126, 237), rgb(64, 105, 255));
  -webkit-background-clip: text;
  background-clip: text;
  color: transparent;
}
</style>