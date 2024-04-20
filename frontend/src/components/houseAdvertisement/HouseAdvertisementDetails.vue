<script setup>
import { ref, onMounted, nextTick, computed } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";

const route = useRoute();
const router = useRouter();
const id = route.params.id;
const houseAdvertisement = ref(null);
const selectedImage = ref(0);
const clipboardMessage = ref(false);
const store = useStore();
const currentUser = computed(() => store.state.user);
const valorations = ref([]);
const isLoading = ref(true);
const erroresComentario = ref([]);
const fetchError = ref(null);
const text = ref("");
const currentUserHouseAdvertisementRating = ref({});
const rating = ref(0);
const chatError = ref("");
function setRating(value) {
  rating.value = value;
}

const truncateDescription = (description) => {
  const words = description.split(" ");
  if (words.length > 10) {
    return words.slice(0, 10).join(" ") + "...";
  } else {
    return description;
  }
};

function parseHeating(heating) {
  if (heating === "CENTRAL_HEATING") {
    return "Calefacción central";
  } else if (heating === "AIR_CONDITIONING") {
    return "Aire acondicionado";
  } else if (heating == "NATURAL_GAS") {
    return "Gas natural";
  } else {
    return "Radiador";
  }
}

function copyToClipboard() {
  navigator.clipboard
    .writeText(window.location.href)
    .then(function () {
      clipboardMessage.value = true;
      setTimeout(() => {
        clipboardMessage.value = false;
      }, 1500);
    })
    .catch(function (error) {
      console.error("Error al copiar al portapapeles: ", error);
    });
}

const openModal = () => {
  let modal = document.getElementById("loginModal");
  modal.style.display = "block";
};
const closeModal = () => {
  let modal = document.getElementById("loginModal");
  modal.style.display = "none";
  erroresComentario.value = [];
};

const deleteComment = () => {
  fetch(
    import.meta.env.VITE_BACKEND_URL +
      "/api/houseRating/" +
      currentUserHouseAdvertisementRating.value.id,
    {
      method: "DELETE",
      headers: {
        Authentication: "Bearer " + localStorage.getItem("authentication"),
      },
    }
  )
    .then((response) => {
      if (response.ok) {
        currentUserHouseAdvertisementRating.value = {};
        fetchValorations();
      } else {
        console.error("Error al eliminar el comentario");
      }
    })
    .catch((error) =>
      console.error("Error al enviar datos al backend:", error)
    );
};

const createHouseAdvertisementRating = () => {
    fetch(import.meta.env.VITE_BACKEND_URL + "/api/houseRating", {
    method: "POST",
    headers: {
      Authentication: "Bearer " + localStorage.getItem("authentication"),
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      houseAdvertisement: houseAdvertisement.value,
      comment: text.value,
      rating: rating.value,
    }),
  })
    .then((response) => {
      if(response.status == 200) {
          fetchValorations();
          closeModal();
      } else { 
        response.json()
          .then((body) => {
            erroresComentario.value = body ? body : [{"message": "Ha ocurrido un error inesperado"}];
          });
      }
    })
    .catch((error) => {
      console.error("Error al enviar datos al backend:", error);
      erroresComentario.value = error ? error : "Ha ocurrido un error inesperado";
    });
};

const fetchValorations = async () => {
  try {
    const response = await fetch(
      import.meta.env.VITE_BACKEND_URL +
        `/api/houseRating/houseAdvertisements/${houseAdvertisement.value.id}`,
      {
        method: "GET",
        credentials: "include",
      }
    );
    valorations.value = await response.json();
    for(const rating of valorations.value){
      if(rating.user.username === currentUser.value.username){
        currentUserHouseAdvertisementRating.value = rating;
        break;
      }
    }
  } catch (error) {
    console.error("Error:", error);
  }
};

const toggleLike = async () => {
  try {
    const response = await fetch(
      import.meta.env.VITE_BACKEND_URL +
        `/api/houses/like/${houseAdvertisement.value.house.id}`,
      {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authentication: "Bearer " + localStorage.getItem("authentication"),
        },
      }
    );

    if (response.ok) {
      if (
        houseAdvertisement.value.house.likes.some(
          (like) => like.id === currentUser.value.id
        )
      ) {
        houseAdvertisement.value.house.likes =
          houseAdvertisement.value.house.likes.filter(
            (like) => like.id !== currentUser.value.id
          );
      } else {
        houseAdvertisement.value.house.likes.push(currentUser.value);
      }
    }
  } catch (error) {
    console.error("Error:", error);
  }
};

const deleteHouseAd = (id) => {
  fetch(import.meta.env.VITE_BACKEND_URL + "/api/advertisements/houses/" + id, {
    method: "DELETE",
    headers: {
      Authentication: "Bearer " + localStorage.getItem("authentication"),
    },
    credentials: "include",
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("No se ha podido eliminar el anuncio de vivienda");
      }
    })
    .then((data) => {
      router.push(`/advertisements/houses`);
    })
    .catch((error) => {
      fetchError.value = error;
    });
};

const fetchHouseAdvertisement = () => {
  fetch(import.meta.env.VITE_BACKEND_URL + "/api/advertisements/houses/" + id, {
    method: "GET",
    headers: {
      Authentication: "Bearer " + localStorage.getItem("authentication"),
    },
    credentials: "include",
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("No se ha podido cargar la vivienda");
      }

      return response.json();
    })
    .then((data) => {
      isLoading.value = false;
      houseAdvertisement.value = data;
      fetchValorations();
      nextTick(() => {
        const carousel = document.getElementById("imgCarousel");

        if (carousel) {
          carousel.addEventListener("slid.bs.carousel", function (e) {
            const activeSlideIndex = Array.from(
              e.target.querySelectorAll(".carousel-item")
            ).findIndex((item) => item.classList.contains("active"));
            selectedImage.value = activeSlideIndex;
          });
        }
      });
    })
    .catch((error) => {
      isLoading.value = false;
      fetchError.value = error.message;
    });
};

const openChat = () => {
    fetch(import.meta.env.VITE_BACKEND_URL + '/api/chat/', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authentication': 'Bearer ' + localStorage.getItem("authentication"),
        },
        credentials: 'include',
        body: JSON.stringify({
            users: houseAdvertisement.value.tenants,
        }),
    })
    .then(async response => {
        if (!response.ok) {
            if(response.status == 409) {
                chatError.value = "Ya posee un chat con esta persona";
            } else {
                throw new Error('No se ha podido crear el chat, código: ' + response.status);
            }
        } else {
            router.push("/chat");
        }
        
    })
    .catch(error => {
        console.error(error);
        chatError.value = "Ha ocurrido un error creando el chat.";
    })
}

onMounted(() => {
  fetchHouseAdvertisement();
});
</script>

<template>
  <Navbar />
  <link
    rel="stylesheet"
    href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.2/css/all.min.css"
  />
  <div class="container">
    <div id="loginModal" class="modal">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <div class="col">
              <h5 class="modal-title">Nuevo comentario</h5>
            </div>
            <div class="col">
              <span @click="closeModal" class="success-checkmark" style="position: relative; align-items: center; margin: 1vh; padding: 1vh; float: right; cursor: pointer;">X</span>
            </div>
          </div>
          <div class="modal-body">
            <form @submit.prevent="createHouseAdvertisementRating" class="d-flex flex-column align-items-center">
              <div>
                  <div class="rating stars-input">
                    <input value="5" name="rate" id="star5" type="radio" @click="setRating(5)">
                    <label title="text" for="star5"></label>
                    <input value="4" name="rate" id="star4" type="radio" @click="setRating(4)">
                    <label title="text" for="star4"></label>
                    <input value="3" name="rate" id="star3" type="radio" @click="setRating(3)">
                    <label title="text" for="star3"></label>
                    <input value="2" name="rate" id="star2" type="radio" @click="setRating(2)">
                    <label title="text" for="star2"></label>
                    <input value="1" name="rate" id="star1" type="radio" @click="setRating(1)">
                    <label title="text" for="star1"></label>
                  </div>
              </div>
              <div class="form-group container-fluid">
                <label for="commentText">Comentario</label>
                <textarea class="form-control" id="text" v-model="text"
                ></textarea>
              </div>
              <div class="mt-3 alert alert-danger" role="alert" style="padding-top: 20px;" v-if="erroresComentario.length > 0" v-for="error in erroresComentario" :key="error.message">
                <span v-if="error.field !== undefined && error.field !== '*'">
                  <i class="fas fa-exclamation-triangle"></i> El campo {{ error.field }} {{ error.message }}
                </span>
                <span v-if="error.field === undefined || error.field === '*'">
                  <i class="fas fa-exclamation-triangle"></i> {{ error.message }}
                </span>
              </div>
              <button type="submit" class="button boton" style="position: relative; align-items: center; margin-top: 1vh; padding: 1vh; float: right;">
                <strong style="color: white">Enviar</strong>
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
    <div v-if="isLoading" class="spinner-border" role="status">
      <span class="visually-hidden">Loading...</span>
    </div>
    <div v-else-if="fetchError" class="alert alert-danger" role="alert">
      {{ fetchError }}
    </div>
    <div v-else>
      <div class="row mt-5">
        <div class="col col-6 justify-content-center align-items-center">
          <div class="container">
            <div id="imgCarousel" class="carousel slide mb-4">
              <div class="carousel-inner mx-auto p-3">
                <div class="carousel-item" v-for="(image, index) in houseAdvertisement.images" :key="image" :class="{ active: index === 0 }">
                  <img :src="image" class="img-fluid carousel-image" alt="..."/>
                </div>
              </div>
              <button class="carousel-control-prev" type="button" data-bs-target="#imgCarousel" data-bs-slide="prev">
                <span class="material-symbols-outlined" style="color: black">arrow_back_ios</span>
                <span class="visually-hidden">Previous</span>
              </button>
              <button class="carousel-control-next" type="button" data-bs-target="#imgCarousel" data-bs-slide="next">
                <span class="material-symbols-outlined" style="color: black">arrow_forward_ios</span>
                <span class="visually-hidden">Next</span>
              </button>
            </div>
          </div>

          <div class="container w-75">
            <div class="row align-items-center">
              <div class="col-md-4 mb-4" v-for="(image, index) in houseAdvertisement.images" :key="image">
                <img :src="image" class="d-block w-100 rounded" alt="..." data-bs-target="#imgCarousel" :data-bs-slide-to="index" :class="{ 'image-selected': selectedImage === index }"/>
              </div>
            </div>
          </div>
        </div>

        <div class="col px-6" style="padding-left: 5%; padding-right: 5%">
          <div style="display: flex; align-items: center; justify-content: space-between;">
            <h1 style="text-align: left">{{ houseAdvertisement.title }}</h1>
            <button class="btn btn-share" @click="copyToClipboard()">
              <i class="bi bi-share-fill"></i>
            </button>
          </div>
          <h2 style="text-align: left">{{ houseAdvertisement.price }}€</h2>
          <transition name="fade">
            <div v-show="clipboardMessage" class="alert alert-success alert-dismissible fade show" role="alert">
              Enlace copiado al portapapeles.
            </div>
          </transition>
          <br />
          <div>
            <h4 style="text-align: left">Detalles</h4>
            <hr />
            <div
              style="color: black; display: flex; align-items: left"
              class="justify-content-between">
              <div style="display: grid">
                <span class="material-icons" style="font-size: xx-large"
                  >bed</span>
                <h5 style="color: #5d5e60; text-align: center">
                  {{ houseAdvertisement.house.roomsNumber }}
                </h5>
              </div>
              <div style="display: grid">
                <span class="material-icons" style="font-size: xx-large">shower</span>
                <h5 style="color: #5d5e60; text-align: center">
                  {{ houseAdvertisement.house.bathroomsNumber }}
                </h5>
              </div>
              <div style="display: grid">
                <span class="material-icons" style="font-size: xx-large">square_foot</span>
                <h5 style="color: #5d5e60; text-align: center">
                  {{ houseAdvertisement.house.area }} m<sup>2</sup>
                </h5>
              </div>
              <div style="display: grid">
                <span class="material-symbols-outlined" style="font-size: xx-large">mode_fan</span>
                <h5 style="color: #5d5e60; text-align: center">
                  {{ parseHeating(houseAdvertisement.house.heating) }}
                </h5>
              </div>
              <div style="display: grid">
                <span class="material-symbols-outlined" style="font-size: xx-large">floor</span>
                <h5 style="color: #5d5e60; text-align: center">
                  {{ houseAdvertisement.house.floor }}
                </h5>
              </div>
            </div>
          </div>
          <br />
          <div>
            <h4 style="text-align: left">Inquilinos</h4>
            <hr />
            <div style="display: flex" class="mb-1">
              <div style="display: grid; margin-right: 10%">
                <h5 style="color: #5d5e60">Número</h5>
                <h5 style="color: black; text-align: center">
                  {{ houseAdvertisement.tenants.length }}
                </h5>
              </div>
              <div style="display: grid; margin-right: 10%">
                <h5 style="color: #5d5e60">Etiquetas</h5>
                <div style="display: inline-flex" v-for="etiqueta in houseAdvertisement.house.tags" :key="etiqueta">
                  <span class="badge etiqueta shadow" style="font-size: 105%">
                    {{ etiqueta.tag }}
                  </span>
                </div>
              </div>
            </div>
            <div v-if="houseAdvertisement.tenants.length > 0" class="py-3 px-3 overflow-auto" style="height: 20vh">
              <div v-for="tenant in houseAdvertisement.tenants" :key="tenant">
                <div class="card card-user mb-3 mt-3 shadow-sm" style="padding: 10px">
                  <div class="card-body">
                    <div class="d-flex">
                      <div class="mx-2">
                        <img :src="tenant.imageUri" style="border-radius: 50%; width: 10vh; height: 10vh"/>
                      </div>
                      <div class="flex-column overflow-auto" style="height: 10vh; padding-right: 5px">
                        <div class="d-flex" style="margin-bottom: 5px; align-items: center">
                          <h5 style="text-align: left" class="card-title">
                            {{ tenant.username }}
                          </h5>
                          <img v-if="tenant.plan === 'explorer'" style="margin-left: 5px; max-height: 35px" src="/images/verificado.png" loading="lazy"/>
                          <i class="bi bi-gender-female" style="margin-left: 5px" v-if="tenant.gender == 'FEMENINO'"></i>
                          <i class="bi bi-gender-male" style="margin-left: 5px" v-if="tenant.gender == 'MASCULINO'"></i>
                          <i class="bi bi-gender-ambiguous" style="margin-left: 5px" v-if="tenant.gender == 'OTRO'"></i>
                        </div>
                        <p v-if="houseAdvertisement.description === ''" style="text-align: justify; word-wrap: break-word" class="card-text">
                          Esta vivienda tiene establecida descripción
                        </p>
                        <p v-else style="text-align: justify; word-wrap: break-word" class="card-text">
                          {{ truncateDescription(tenant.description) }}
                        </p>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="row mt-1">
        <div class="col mb-4 mt-1" style="padding-left: 5%; padding-right: 5%">
          <div class="d-flex flex-column align-items-left">
            <div style="color: #5d5e60; display: flex; align-items: center">
              <h5>{{ houseAdvertisement.house.location }}</h5>
              <i class="bi bi-geo-alt" style="margin-left: 1%"></i>
            </div>
            <img src="https://motor.elpais.com/wp-content/uploads/2022/01/google-maps-22-1046x616.jpg" class="rounded-4" style="max-width: 100%; max-height: 100%"/>
          </div>
        </div>

        <div class="col-6 mb-4 mt-5" style="padding-left: 5%; padding-right: 5%">
          <div class="card mb-3 shadow" style="padding: 10px">
            <div class="card-body">
              <h4 style="text-align: left" class="card-title">Descripción</h4>
              <hr />
              <p v-if="houseAdvertisement.description === ''" style="text-align: justify; word-wrap: break-word" class="card-text">
                Este usuario no ha establecido una descripción
              </p>
              <p v-else style="text-align: justify; word-wrap: break-word" class="card-text">
                {{ houseAdvertisement.description }}
              </p>
            </div>
          </div>

          <div class="d-flex justify-content-center">
            <div class="d-flex justify-content-center align-items-center">
              <div v-if="currentUser?.id === houseAdvertisement?.author?.id" class="d-flex align-items-center" style="margin-right: 1vh;">
                <span style="font-weight: bold; font-size: large; color: #28426b">{{ houseAdvertisement.views }}</span>
                <span class="material-symbols-outlined" style="color: #28426b; margin-left: 2px;">visibility</span>
              </div>
              <div class="likes" style="margin-right: 1vw">
                <button :class="{ 'like-button': true, 'no-clickable' : Object.keys(currentUser).length === 0 || houseAdvertisement.author?.id == currentUser?.id }" :disabled="Object.keys(currentUser).length === 0 || houseAdvertisement.author?.id == currentUser?.id" @click="toggleLike">
                  <i :class="{ 'bi bi-heart-fill': houseAdvertisement.house?.likes.some((like) => like.id === currentUser.id), 'bi bi-heart': !houseAdvertisement.house?.likes.some((like) => like.id === currentUser.id) }" :style="{ color: houseAdvertisement.house?.likes.some((like) => like.id === currentUser.id) ? '#e87878' : '#28426b' }" class="heart-transition" style="margin-top: 2px; margin-right: 5px;"></i>
                </button>   

                <span style="font-weight: bold; font-size: large; color: #28426b">
                  {{ houseAdvertisement.house?.likes.length }}
                </span>
              </div>
              
              <button @click.prevent="openChat()" v-if="currentUser.id !== houseAdvertisement.author?.id" type="button" class="button boton" style="text-wrap: nowrap; width: 100%; margin-left: 1vw">
                <strong style="color: white"
                  >Iniciar chat
                  <i class="bi bi-chat" style="margin-left: 5px"></i
                ></strong>
              </button>

              <div class="d-flex col" v-else>
                <button type="button" class="btn btn-success" @click="$router.push(`/advertisements/houses/edit/${houseAdvertisement.id}`)
                  "style="display: flex; align-items: center; justify-content: center; width: 100%; margin-left: 1vw;">
                  <strong>Editar</strong>
                  <span class="material-symbols-outlined" style="margin-left: 0.5rem">edit</span>
                </button>

                <button type="button" class="btn btn-danger" @click="deleteHouseAd(houseAdvertisement.id)" 
                  style="display: flex; align-items: center; justify-content: center; width: 100%; margin-left: 1vw;">
                  <strong>Eliminar</strong>
                  <span class="material-symbols-outlined" style="margin-left: 0.5rem">delete</span>
                </button>
              </div>
              <div class="mt-3 alert alert-danger" role="alert" style="padding-top: 20px;" v-if="currentUser.id !== houseAdvertisement.author?.id && chatError != ''">
                  <i class="fas fa-exclamation-triangle"></i> {{ chatError }}
              </div>
            </div>
          </div>
          
          <div style="margin-top: 5%">
            <div class="d-flex justify-content-between">
              <h4 style="text-align: left">Comentarios</h4>
              <div v-if="currentUser.username && houseAdvertisement.author?.username !== currentUser.username">
              <i class="fas fa-trash-alt" @click="deleteComment" style="cursor: pointer;width: 38px;height: 38px;border: 0.2em solid black;border-radius: 50%;padding: 0.5em;background-color: #f2f2f2;"
                 v-if="Object.keys(currentUserHouseAdvertisementRating).length !== 0">
              </i>
              <button type="button" @click="openModal" class="button boton" style="padding: 0 1.3vw; width: 100%"
                v-if="Object.keys(currentUserHouseAdvertisementRating).length === 0">
                <strong style="color: white">Comentar</strong>
              </button>  
            </div>
            </div>
            <hr />
            <div v-if="valorations.length == 0" style="text-align: left">
              Aún no hay comentarios...
            </div>
            <div v-else style="overflow-y: auto; max-height: 50vh">
              <div class="card card-user mb-3 mt-3 shadow-sm" style="padding: 10px" v-if="Object.keys(currentUserHouseAdvertisementRating).length !== 0">
                <div class="card-body">
                    <div class="d-flex justify-content-between">
                      <p style="font-weight: bold; text-align: left" class="card-title">
                        <img class="rounded-circle" :src="currentUserHouseAdvertisementRating.user.imageUri" style="width: 3vw; height: 3vw; margin-right: 1vw"/>
                        {{ currentUserHouseAdvertisementRating.user.username }}
                      </p>
                      <div class="stars" style="text-align: right;">
                        <span
                          v-for="star in 5"
                          :key="star"
                          :class="{ active: star <= currentUserHouseAdvertisementRating.rating }"
                          style="font-size: 1.5em;"
                          >★</span
                        > 
                      </div>
                    </div>
                    <p  style="text-align: justify; word-wrap: break-word" class="card-text">
                      {{ currentUserHouseAdvertisementRating.comment }}
                    </p>
                  </div>  
              </div>
              <div v-for="comentario in valorations" :key="comentario">
                <div class="card card-user mb-3 mt-3 shadow-sm" style="padding: 10px" v-if="comentario.user.username !== currentUser.username">
                  <div class="card-body">
                    <div class="d-flex justify-content-between">
                      <p style="font-weight: bold; text-align: left" class="card-title">
                        <img class="rounded-circle" :src="comentario.user.imageUri" style="width: 3vw; height: 3vw; margin-right: 1vw"/>
                        {{ comentario.user.username }}
                      </p>
                      <div class="stars" style="text-align: right;">
                        <span
                          v-for="star in 5"
                          :key="star"
                          :class="{ active: star <= comentario.rating }"
                          style="font-size: 1.5em;"
                          >★</span
                        >
                      </div>
                    </div>
                    <p  style="text-align: justify; word-wrap: break-word" class="card-text">
                      {{ comentario.comment }}
                    </p>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.boton {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-left: 1%;
  background-color: #28426b;
  color: #ffffff;
  border-radius: 15px;
  width: 30%;
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
  border-radius: 15px;
  border: 1px solid #28426b;
  background-color: #ffffff;
  color: #28426b;
}

.btn-share {
  border-radius: 50%;
  width: 40px;
  height: 40px;
  display: flex;
  line-height: 40px;
  justify-content: center;
  align-items: center;
  background-color: #28426b;
  color: #ffffff;
  border: none;
}

.btn-share:hover {
  background-color: #28426b;
  color: #ffffff;
  border: none;
}
.carousel-item {
  height: 45vh;
  object-fit: contain;
}

.carousel-item img {
  height: 45vh;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s;
}

.fade-enter,
.fade-leave-to {
  opacity: 0;
}

.card-user {
  border-top: 1vh solid #28426b;
}

.likes {
  display: inline-flex;
}

.boton {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-left: 1%;
  background-color: #28426b;
  border-radius: 10px;
  width: 27%;
  height: 5vh;
}

.boton strong {
  display: flex;
  align-items: center;
}

.stars-input {
  cursor: pointer;
}

.stars-input span {
  color: #ccc;
}

.stars-input span.active {
  color: #ffa723;
}

.stars span {
  color: #ccc;
}

.stars span.active {
  color: #ffa723;
}

.like-button {
    cursor: pointer; 
    background-color: rgba(0,0,0,0); 
    border-color: rgba(0,0,0,0); 
    padding: 0%
}

.no-clickable {
    cursor: not-allowed;
}

.rating:not(:checked) > input {
  position: absolute;
  appearance: none;
}

.rating:not(:checked) > label {
  float: right;
  cursor: pointer;
  font-size: 30px;
  color: #666;
}

.rating:not(:checked) > label:before {
  content: '★';
}

.rating > input:checked + label:hover,
.rating > input:checked + label:hover ~ label,
.rating > input:checked ~ label:hover,
.rating > input:checked ~ label:hover ~ label,
.rating > label:hover ~ input:checked ~ label {
  color: #e58e09;
}

.rating:not(:checked) > label:hover,
.rating:not(:checked) > label:hover ~ label {
  color: #ff9e0b;
}

.rating > input:checked ~ label {
  color: #ffa723;
}

.heart-transition {
  transition: color 0.3s ease-in-out;
}


</style>
