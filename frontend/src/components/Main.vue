<script setup>
import Navbar from './Navbar.vue'
import { computed } from 'vue'
import { useStore } from "vuex";

const store = useStore()
const currentUser = computed(() => store.state.user);
</script>

<template>
  <Navbar />
  <div class="d-flex flex-column" style="min-height: 90vh;">
    <div class="container d-flex align-items-center justify-content-center text-center my-5">
      <h1 color="#282828">Hora de <span class="gradient-text">buscar...</span></h1>
    </div>
    <div class="d-flex flex-row justify-content-center align-items-center">
      <div class="d-flex flex-column align-items-center" style="padding-right: 2rem;">
        <button class="btn-primary btn-circle btn-xl" @click="$router.push('/advertisements/houses')">
          <i class="bi bi-houses h1"></i>
        </button>
        <b class="mt-3">Viviendas</b>
      </div>
      <div class="d-flex flex-column align-items-center" style="padding-left: 2rem;">
        <button class="btn-primary btn-circle btn-xl" @click="$router.push('/advertisements/users')">
          <i class="bi bi-people h1"></i>
        </button>
        <b class="mt-3">Compañeros</b>
      </div>
    </div>
    <div class="mb-auto">
      <!-- meter aquí relleno para que no quede tan vacío-->
    </div>
    <div class="justify-content-start mt-6 mb-3">
      <h3>¡No te pierdas esto!</h3>
    </div>
    <div class="d-flex align-items-end justify-content-center">
      <div class="card mb-3 bg-dark" style="max-width: 580px;">
        <div class="row g-0">
          <div class="col-md-8">
            <div class="card-body">
              <h3 class="card-title text-white">Plan <span class="gradient-text">Explorador</span></h3>            
              <p class="card-text text-white">Sé el primero en descubrir a tu nuevo compañero con el plan de explorador.</p>
              
              <a class="btn btn-success" href="/plan" v-if="currentUser.username">Ver planes</a>
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
  <div class="mt-16"></div>
  <div class="relative container-fluid text-start" style="background: rgba(71, 94, 132, 0.2); padding-left: 30%; padding-top: 1%;">
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
                    
                      <div class="social-card" >
                          <button class="Btn github" onclick="location.href='https://github.com/Cohabify/Cohabify'">
                            <span class="svgContainer">
                              <svg height="1.6em" viewBox="0 0 496 512" fill="white">
                                <path
                                  d="M165.9 397.4c0 2-2.3 3.6-5.2 3.6-3.3.3-5.6-1.3-5.6-3.6 0-2 2.3-3.6 5.2-3.6 3-.3 5.6 1.3 5.6 3.6zm-31.1-4.5c-.7 2 1.3 4.3 4.3 4.9 2.6 1 5.6 0 6.2-2s-1.3-4.3-4.3-5.2c-2.6-.7-5.5.3-6.2 2.3zm44.2-1.7c-2.9.7-4.9 2.6-4.6 4.9.3 2 2.9 3.3 5.9 2.6 2.9-.7 4.9-2.6 4.6-4.6-.3-1.9-3-3.2-5.9-2.9zM244.8 8C106.1 8 0 113.3 0 252c0 110.9 69.8 205.8 169.5 239.2 12.8 2.3 17.3-5.6 17.3-12.1 0-6.2-.3-40.4-.3-61.4 0 0-70 15-84.7-29.8 0 0-11.4-29.1-27.8-36.6 0 0-22.9-15.7 1.6-15.4 0 0 24.9 2 38.6 25.8 21.9 38.6 58.6 27.5 72.9 20.9 2.3-16 8.8-27.1 16-33.7-55.9-6.2-112.3-14.3-112.3-110.5 0-27.5 7.6-41.3 23.6-58.9-2.6-6.5-11.1-33.3 2.6-67.9 20.9-6.5 69 27 69 27 20-5.6 41.5-8.5 62.8-8.5s42.8 2.9 62.8 8.5c0 0 48.1-33.6 69-27 13.7 34.7 5.2 61.4 2.6 67.9 16 17.7 25.8 31.5 25.8 58.9 0 96.5-58.9 104.2-114.8 110.5 9.2 7.9 17 22.9 17 46.4 0 33.7-.3 75.4-.3 83.6 0 6.5 4.6 14.4 17.3 12.1C428.2 457.8 496 362.9 496 252 496 113.3 383.5 8 244.8 8zM97.2 352.9c-1.3 1-1 3.3.7 5.2 1.6 1.6 3.9 2.3 5.2 1 1.3-1 1-3.3-.7-5.2-1.6-1.6-3.9-2.3-5.2-1zm-10.8-8.1c-.7 1.3.3 2.9 2.3 3.9 1.6 1 3.6.7 4.3-.7.7-1.3-.3-2.9-2.3-3.9-2-.6-3.6-.3-4.3.7zm32.4 35.6c-1.6 1.3-1 4.3 1.3 6.2 2.3 2.3 5.2 2.6 6.5 1 1.3-1.3.7-4.3-1.3-6.2-2.2-2.3-5.2-2.6-6.5-1zm-11.4-14.7c-1.6 1-1.6 3.6 0 5.9 1.6 2.3 4.3 3.3 5.6 2.3 1.6-1.3 1.6-3.9 0-6.2-1.4-2.3-4-3.3-5.6-2z"
                                ></path>
                              </svg>
                            </span>
                            <span class="BG"></span>
                          </button>

                      <button class="Btn youtube" onclick="location.href='https://www.youtube.com/@Cohabify'">
                        <span class="svgContainer">
                          <svg
                            viewBox="0 0 576 512"
                            fill="white"
                            height="1.6em"
                            xmlns="http://www.w3.org/2000/svg"
                          >
                            <path
                              d="M549.7 124.1c-6.3-23.7-24.8-42.3-48.3-48.6C458.8 64 288 64 288 64S117.2 64 74.6 75.5c-23.5 6.3-42 24.9-48.3 48.6-11.4 42.9-11.4 132.3-11.4 132.3s0 89.4 11.4 132.3c6.3 23.7 24.8 41.5 48.3 47.8C117.2 448 288 448 288 448s170.8 0 213.4-11.5c23.5-6.3 42-24.2 48.3-47.8 11.4-42.9 11.4-132.3 11.4-132.3s0-89.4-11.4-132.3zm-317.5 213.5V175.2l142.7 81.2-142.7 81.2z"
                            ></path>
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
.enlaces {
  color:black;
  text-decoration:none;
}
.enlaces:hover {
  color:rgb(37, 37, 233);
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
  background: #181818;
}
.youtube .BG {
  background: #bb0000;
}

.Btn:hover .BG {
  transform: scale(1.2);
  /* transform-origin: bottom; */
  font-size: 5%;
  color: white;
}



</style>