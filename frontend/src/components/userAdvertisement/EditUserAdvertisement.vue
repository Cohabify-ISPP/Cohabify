<script>
import { ref, onMounted, computed } from "vue";
import { useRouter, useRoute } from "vue-router";
import { useStore } from "vuex";

export default {
  setup() {
    const successfulAlert = ref(false);
    const router = useRouter();
    const store = useStore();
    const user = ref(store.state.user);
    const userAd = ref({
      id: "",
      description: "",
      maxBudget: "",
      desiredLocation: "",
      entranceDate: "",
      exitDate: "",
      maxCohabitants: "",
    });
    
    const fetchUserAd = async () => {
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
                  userAd.value = data;
              } else {
                if (response.status === 404) {
                  userAd.value = {
                    description: "",
                    maxBudget: "",
                    desiredLocation: "",
                    entranceDate: "",
                    exitDate: "",
                    maxCohabitants: "",
                  };
                } else {
                  router.push("/404");
                }
              }

          } catch (error) {
          if (error.response.status === 404) {
            userAd.value = {
              description: "",
              maxBudget: "",
              desiredLocation: "",
              entranceDate: "",
              exitDate: "",
              maxCohabitants: "",
            };
          }else {
            router.push("/404");
          }
      }
    };

    const saveUserAd = async () => {
      document.getElementById("btnPublicar").disabled = true;
      if (!validateForm()) {
        document.getElementById("btnPublicar").disabled = false;
        return;
      }
      try {
        userAd.value.author = user.value;
        const response = await fetch(
          import.meta.env.VITE_BACKEND_URL + "/api/advertisements/users",
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              "Authentication":
                "Bearer " + localStorage.getItem("authentication"),
            },
            body: JSON.stringify(userAd.value),
          }
        );
        const data = await response.json();
        successfulAlert.value = true;
        setTimeout(() => {
          router.push(`/advertisements/users/${data.id}`);
        }, 2000);
      } catch (error) {
        document.getElementById("btnPublicar").disabled = false;
        console.error("Error:", error);
      }
    };

    const errorMessages = ref({
      maxBudget: "",
      desiredLocation: "",
      entranceDate: "",
      maxCohabitants: "",
    });

    const validateForm = () => {
      let isValid = true;
      if (document.getElementById("form").reportValidity()) {

        if (!userAd.value.maxBudget || isNaN(userAd.value.maxBudget)) {
          isValid = false;
          errorMessages.value.maxBudget = "Este campo debe ser un número";
        } else {
          errorMessages.value.maxBudget = "";
        }

        if (!userAd.value.desiredLocation) {
          isValid = false;
          errorMessages.value.desiredLocation =
            "Este campo no puede estar vacío";
        } else {
          errorMessages.value.desiredLocation = "";
        }

        if (!userAd.value.entranceDate) {
          isValid = false;
          errorMessages.value.entranceDate = "Este campo no puede estar vacío";
        } else {
          errorMessages.value.entranceDate = "";
        }
        if (new Date(userAd.value.entranceDate) <= new Date()) {
          isValid = false;
          errorMessages.value.entranceDate = "La fecha debe ser en el futuro";
        } else {
          errorMessages.value.entranceDate = "";
        }
        if (userAd.value.exitDate){
          if (new Date(userAd.value.entranceDate) > new Date(userAd.value.exitDate)) {
            isValid = false;
            errorMessages.value.exitDate = "La fecha de salida no puede ser anterior a la entrada";
          } else {
            errorMessages.value.exitDate = "";
          }
        }
       

        if (
          !userAd.value.maxCohabitants ||
          isNaN(userAd.value.maxCohabitants)
        ) {
          isValid = false;
          errorMessages.value.maxCohabitants = "Este campo debe ser un número";
        } else {
          errorMessages.value.maxCohabitants = "";
        }

        return isValid;
      }
    };

    const onCancel = () => {
      router.push("/");
    };

    onMounted(() => {
      if (localStorage.getItem("authentication") === null) {
        router.push("/login");
      }

      if (user.value.id !== undefined) {
        fetchUserAd()
      } else {
          store.watch(
            () => store.state.user,
            (newValue, oldValue) => {
                user.value = newValue
                fetchUserAd()
            }
          )
      }
    });

    return {
      userAd,
      saveUserAd,
      errorMessages,
      successfulAlert,
      router,
      onCancel,
    };
  },
};
</script>

<template>
  <Navbar />
  <div class="container card2" style="margin-top: 40px;margin-bottom: 40px;">
    <div class="contenedor d-flex align-items-center justify-content-center text-center">
      <div class="panel">
        <div class="columna" style="flex-grow: 2">
          <form id="form">
            <div class="form-group" style="text-align: left; margin-top: 3vh">
              <h2 class="mb-4">Crear/Editar anuncio de búsqueda de piso</h2>
              <h5>Presupuesto</h5>
              <div class="input-group mb-3 d-flex w-50">
                <div class="d-flex align-items-center">
                  <input
                    type="number"
                    min="0"
                    class="form-control"
                    aria-label="Cantidad máxima"
                    v-model="userAd.maxBudget"
                    required
                    placeholder="Max..."
                  />
                  <div class="input-group-append">
                    <span class="input-group-text">€</span>
                  </div>
                </div>
                <span class="text-danger">{{ errorMessages.maxBudget }}</span>
              </div>
              <div class="input-group mb-3 d-flex w-50">
                <input
                  type="text"
                  class="form-control"
                  aria-label="Añadir ubicación"
                  v-model="userAd.desiredLocation"
                  required
                  placeholder="Añadir ubicación..."
                />
                <div class="input-group-append">
                  <span class="input-group-text"
                    ><i class="bi bi-geo-alt" style="font-size: inherit"></i
                  ></span>
                </div>
              </div>
              <span class="text-danger">{{ errorMessages.desiredLocation }}</span>
              <h5>Estancia</h5>
              <div class="input-group mb-3 d-flex w-75 align-items-center">
                <div class="input-up">
                  <label for="entrada">Entrada</label>
                  <div class="d-flex align-items-center">
                    <input
                      type="date"
                      id="entrada"
                      class="form-control"
                      style="margin-right: 1vw"
                      aria-label="Cantidad mínima"
                      v-model="userAd.entranceDate"
                      required
                      placeholder="Min..."
                    />
                    <h5 style="margin: 0 2vw">-</h5>
                  </div>
                </div>
                <div class="input-up">
                  <label for="salida">Salida</label>
                  <input
                    type="date"
                    id="salida"
                    class="form-control"
                    style="margin-left: 1vw"
                    aria-label="Cantidad máxima"
                    v-model="userAd.exitDate"
                    placeholder="Max..."
                  />
                </div>
              </div>
              <span class="text-danger">{{ errorMessages.entranceDate }}</span>
              <span class="text-danger">{{ errorMessages.exitDate }}</span>
              <h5>Inquilinos</h5>
              <div class="input-group mb-3 d-flex w-75 align-items-center">
                <div class="input-up">
                  <label for="entrada">Número</label>
                  <div class="d-flex align-items-center">
                    <input
                      type="number"
                      min="0"
                      id="entrada"
                      class="form-control"
                      aria-label="Cantidad máxima"
                      v-model="userAd.maxCohabitants"
                      required
                      placeholder="Max..."
                    />
                  </div>
                  <span class="text-danger">{{
                    errorMessages.maxCohabitants
                  }}</span>
                </div>
              </div>
              <textarea
                class="form-control"
                id="descripcion"
                rows="3"
                v-model="userAd.description"
                placeholder="Añadir descripción..."
              ></textarea>
            </div>
            <div
              class="alert alert-dark mt-2"
              role="alert"
              v-if="successfulAlert"
            >
              ¡Anuncio publicado correctamente!
            </div>
            <div class="mt-3">
              <button
                id="btnPublicar"
                style="margin-right: 10px"
                type="button"
                class="btn btn-success"
                @click.prevent="saveUserAd"
              >
                Publicar
              </button>
              <button
                type="button"
                class="btn btn-danger"
                @click.prevent="onCancel"
              >
                Cancelar
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<style>
.contenedor {
  position: relative;
  display: flex;
  height: auto;
  width: 97%;
  margin: 1%;
  justify-content: start;
  align-items: start;
}

.panel {
  width: 60vw;
  max-width: 100%;
}

.columna {
  display: flex;
  height: auto;
  flex-direction: column;
  flex: 1;
  overflow-y: auto;
  overflow-x: hidden;
  margin-bottom: 5px;
  align-items: left;
}

.input-up {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
h5 {
  margin: 1vh 0 !important;
}

.popover {
  --bs-popover-max-width: 80% !important;
}

input[type="number"]::-webkit-inner-spin-button,
input[type="number"]::-webkit-outer-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

input[type="number"] {
  -moz-appearance: textfield; /* Firefox */
  appearance: textfield;
}

.card2 {
  padding-top: 40px;
  padding-bottom: 40px;
  padding-left: 40px;
  padding-right: 40px;
  border: 1px  #28426b30;
  border-radius: 4px;
  background-color: #28426bae;
  box-shadow: 10px 10px 4px rgba(0, 0, 0, 0.1);
  color: white;
}

</style>
