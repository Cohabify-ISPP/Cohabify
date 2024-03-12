<script>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";

export default {
  setup() {
    const successfulAlert = ref(false);
    const router = useRouter();
    const userAdvertisementId = ref();

    const userAd = ref({
      id: "",
      title: "",
      description: "",
      maxBudget: "",
      desiredLocation: "",
      entranceDate: "",
      exitDate: "",
      maxCohabitants: "",
    });
    
    const fetchUserAd = async () => {
            try {
                const response = await fetch(import.meta.env.VITE_BACKEND_URL + `/api/advertisements/users/${userAdvertisementId.value}`,
                    {
                        method: "GET",
                        headers: {
                            'Authentication': 'Bearer ' + sessionStorage.getItem("authentication"),
                        },
                        credentials: "include",
                    });

                    if (response.ok) {
                        const data = await response.json();
                        userAd.value = data;
                    } else {
                        window.location.href = "/404";
                    }

                } catch (error) {
                console.error("Error:", error);
            }
        };

    const saveUserAd = async () => {
      if (!validateForm()) {
        return;
      }

      try {

        const userFetch = await fetch(
          import.meta.env.VITE_BACKEND_URL + "/auth/getUser",
          {
            method: "POST",
            headers: {
              "Authentication":
                "Bearer " + sessionStorage.getItem("authentication"),
            },
          }
        );

        const userData = await userFetch.json();
        userAd.value.author = userData;

        const response = await fetch(
          import.meta.env.VITE_BACKEND_URL + "/api/advertisements/users",
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
              "Authentication":
                "Bearer " + sessionStorage.getItem("authentication"),
            },
            body: JSON.stringify(userAd.value),
          }
        );
        const data = await response.json();
        successfulAlert.value = true;
        setTimeout(() => {
          router.push("/");
        }, 2000);
      } catch (error) {
        console.error("Error:", error);
      }
    };

    const errorMessages = ref({
      title: "",
      maxBudget: "",
      desiredLocation: "",
      entranceDate: "",
      maxCohabitants: "",
    });

    const validateForm = () => {
      let isValid = true;
      if (document.getElementById("form").reportValidity()) {
        if (userAd.value.title.trim() === "") {
          isValid = false;
          errorMessages.value.title = "Este campo no puede estar vacío";
        } else {
          errorMessages.value.title = "";
        }

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
        if (new Date(userAd.value.entranceDate) > new Date(userAd.value.exitDate)) {
          isValid = false;
          errorMessages.value.exitDate = "La fecha de salida no puede ser anterior a la entrada";
        } else {
          errorMessages.value.exitDate = "";
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

    onMounted(() => {
      userAdvertisementId.value = router.currentRoute.value.params.id;
      if (userAdvertisementId !== null && userAdvertisementId !== undefined) {
        fetchUserAd();
      }
      if (sessionStorage.getItem("authentication") === null) {
        router.push("/login");
      }
    });

    return {
      userAd,
      saveUserAd,
      errorMessages,
      successfulAlert,
    };
  },
};
</script>

<template>
  <Navbar />
  <div
    class="contenedor d-flex align-items-center justify-content-center text-center"
  >
    <div class="panel">
      <div class="columna" style="flex-grow: 2">
        <form id="form">
          <div class="form-group" style="text-align: left; margin-top: 3vh">
            <h2 class="mb-4">Crear anuncio de búsqueda de piso</h2>
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
            ¡Anuncio creado correctamente!
          </div>
          <div class="mt-3">
            <button
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
</style>
