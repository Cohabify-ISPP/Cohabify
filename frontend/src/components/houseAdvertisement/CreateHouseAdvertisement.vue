<template>
  <Navbar />
  <h1 class="text-center mt-4">Anuncio de vivienda</h1>
  <div class="container card2" style="margin-top: 20px;margin-bottom: 20px;">
    <div v-show="success" class="alert alert-success alert-dismissible fade show" role="alert">
        Anuncio creado con éxito.
      </div>
    <form v-if="user.numAdvertisements > 0" @submit.prevent="register">
      <div class="row mt-2 justify-content-center">

        <div class=" col  justify-content-center" style="padding-right:35px;">
          <div class="mb-3  text-start">
            <label for="title" class="form-label text-white"><strong>Título<span style="color: red;"> *</span></strong></label>
            <input type="text" class="form-control" id="title" v-model="title" required
              placeholder="Escribe un título para el anuncio..." maxlength="100">
          </div>
          <div class="mb-3 text-start">
            <label for="description" class="form-label text-white"><strong>Descripción<span style="color: red;"> *</span></strong></label>
            <textarea class="form-control" id="description" rows="3" v-model="description" required
              placeholder="Escribe una descripción para el anuncio..."maxlength="1500"></textarea>
          </div>
          <div class="row  mb-3">
            <div class="mb-3  text-start col">
              <div class="d-flex ">
              <label for="cadastre" class="form-label text-white" style="padding-right: 5px;">
                <strong>Catastro
                  <span style="color: red;"> *</span>
                </strong>
              </label>
              <div class="wrapper" style="padding-top:2px">
                <div class="icon">
                  <span class="tooltip">Al buscar un número de catastro se rellenará la <strong>ubicación</strong> y la <strong>superficie</strong> automáticamente</span>
                  <span class="bi bi-info-lg"></span>
                </div>
              </div> 
              </div>
              <input type="text" class="form-control" id="cadastre" v-model="cadastre" required
                placeholder="12345678901234567890..." minlength="20" maxlength="20">
            </div>
            <div class="mb-3  text-start col">
              <label for="location" class="form-label text-white"><strong>Ubicación</strong></label>
              <input type="text"  class="form-control" id="location"  maxlength="100" v-model="location" required placeholder="C/...">
            </div>
          </div>
          <div class="row  mb-3">
            <div class="mb-3  text-start col" style="display: flex; justify-content: center;">
              <button
              style="margin-right: 10px; max-height: 36px;"
              type="button"
              class="btn btn-success"
              @click.prevent="fetchCadastre"
            >
              Buscar
            </button>
            </div>
            <div class="mb-3 text-start col">
              <label for="location" class="form-label text-white"><strong>Seleccionado: {{ selectedCadastre }}</strong></label>
            </div>
          </div>
          <div class="row  mb-3">
            <div class="col text-start">
              <label for="area" class="form-label text-white"><strong>Superficie</strong></label>
              <div class="input-group">
                <input type="number" class="form-control" id="area" v-model="area" required placeholder="0" min="1" max="10000">
                <span class="input-group-text" style="color: grey;">m²</span>
              </div>

            </div>
            <div class="col mb-3 text-start">
              <label for="floor" class="form-label text-white"><strong>Planta<span style="color: red;"> *</span></strong></label>
              <input type="number" class="form-control" id="floor" v-model="floor" required placeholder="0" min="-5" max="100">
            </div>
            <div class="col text-start">
              <div class="mb-3">
                <label for="price" class="form-label text-white"><strong>Precio<span style="color: red;"> *</span></strong></label>
                <div class="input-group">
                  <input type="number" class="form-control" id="area" v-model="price" required placeholder="0" min="0" max="10000000">
                  <span class="input-group-text" style="color: grey;">€</span>
                </div>
              </div>
            </div>
          </div>
          <div class="row  mb-3">
            <div class="col">
              <div class="mb-3">
                <label for="roomsNumber" class="form-label text-white"><strong>Habitaciones<span style="color: red;"> *</span></strong></label>
                <div class="input-group d-flex align-items-center justify-content-center " style="background-color: aliceblue; border-radius: 4px;">
                  <button class=" btn-primary" type="button" @click="decreaseRoomsNumber()">-</button>
                  <p  style="margin: auto;color:black">{{ roomsNumber }}</p>
                  <button class="btn-primary" type="button" @click="increaseRoomsNumber()">+</button>
                </div>
              </div>
            </div>
            <div class="col">
              <div class="mb-3">
                <label for="bathroomsNumber" class="form-label text-white"><strong>Baños<span style="color: red;"> *</span></strong></label>
                <div class="input-group d-flex align-items-center justify-content-center" style="background-color: aliceblue; border-radius: 4px;">
                  <button class="btn-primary" type="button" @click=" decreaseBathoomsNumber()">-</button>
                  <p  style=" margin: auto;color:black">{{ bathroomsNumber }}</p>
                  <button class="btn-primary" type="button" @click="increaseBathroomsNumber()">+</button>
                </div>
              </div>
            </div>
            <div class="col">
              <div class="mb-3">
                <div>
                  <label for="heating" class="form-label text-white"><strong>Calefacción<span style="color: red;"> *</span></strong></label>
                </div>
                <select  class="form-select" id="heating" name="heating" v-model="heating" required>
                  <option value="" disabled selected hidden>Selecciona una opción</option>
                  <option v-for="(option, index) in heatingSelect" :value="option" :key="index">
                    {{ option }}
                  </option>
                </select>
              </div>
            </div>
          </div>
          <div class="row  mb-3">
            
              <div class="text-start">
                <h5>¿Hay inquilinos ya en la vivienda utilizando Cohabify? Añádelos</h5>
                <div class="d-flex">
                  
                  <p class="form-label text-white" style="padding-right: 5px;"><strong>Inquilinos</strong></p>
                  <div class="wrapper" style="padding-top:2px">
                    <div class="icon">
                      <span class="tooltip">Para añadir un inquilino deberás introducir su <strong>nombre de usuario</strong> y su <strong>número de teléfono</strong></span>
                      <span class="bi bi-info-lg"></span>
                    </div>
                  </div>
                </div>
              </div>
                <div class="row d-flex justify-content-center mb-2" style="border-radius:4px">
                  <div class="col">
                  <input type="text" class="form-control" v-model="userSearch" placeholder="Nombre de usuario a buscar..." maxlength="14">
                  </div>
                  <div class="col">
                  <input type="tel" pattern="(\+34|0034|34)?[6789]\d{8}" class="form-control" v-model="phoneSearch" placeholder="Teléfono a buscar...">
                  </div>
                </div>
                <div class="row justify-content-center" >  
                  <button type="button" class="btn-primary col-md-3" @click.prevent="fetchUser">Buscar</button>
                </div>
              <p class="text-white text-start"><strong>Inquilinos seleccionados:</strong></p>
              <div class="text-start mb-3" v-if="selectedTenants.length > 0">
                <div style="max-height: 120px; overflow-y: auto;">
                  <table>
                    <tr v-for="(tenant, index) in selectedTenants" :key="'selected' + index">
                      <td>{{ tenant.username }}</td>
                      <td class="d-flex justify-content-center align-items-center" style="margin:0.5vh 1vw"><span style="color:#E84743; cursor: pointer" @click.prevent="selectedTenants = selectedTenants.filter(x => x !== tenant)" class="material-symbols-outlined">cancel</span></td>
                    </tr>
                  </table>
                </div>
              </div>
            
            <div class=" row d-flex mb-2">
            <label for="tags" class="form-label text-white fw-bold">¿Cómo describirías tu vivienda?<span style="color: red;"> *</span></label>
          </div>   
          <div class="row d-flex mb-2">
            <div class="btn-group" role="group"  style="max-height:  180px; overflow-y: auto;" aria-label="Basic checkbox toggle button group">
              <div class="tags-container">
              <span class="tag" v-for="tag in tags" :key="tag.tag" @click="toggleTag(tag)"
                :class="{ 'selected': selectedTags.includes(tag), 'unselected': !selectedTags.includes(tag) }">
                {{ tag.tag }}
              </span>
              </div>
            </div>
          </div>
          </div>
        </div>
        <div class="col  justify-content-center">
            <!--IMAGES-->
            <div class="row d-flex mb-2">
            <div class="card mt-2 mb-3">
              <div class="top">
                <p>Arrastra aquí tus imágenes <span style="color: red;"> *</span></p>
              </div>
              <div class="drag-area" @dragover.prevent="onDragover" @dragleave.prevent="onDragLeave"
                @drop.prevent="onDrop">
                <span v-if="!isDragging">
                  Arrastra aquí tu imagen o
                  <span class="select" role="button" @click="selectFiles">
                    Elige
                  </span>
                </span>
                <div v-else class="select">Deja la imagen aquí</div>
                <input name="file" type="file" accept="image/*" class="file" ref="fileInput" multiple
                  @change="onFileSelect" />
              </div>
              <div class="container">
                <div class="image" v-for="(image, index) in imagesUrl" :key="index">
                  <span class="delete" @click="deleteImage(index)">&times;</span>
                  <img :src="image.url" />
                </div>
              </div>
            </div>
            <div class="d-flex flex-column mt-4 ">
            <label for="map" class="form-label text-white text-start" style="justify-content: start;"><strong>Localidad<span style="color: red;"> *</span></strong></label>
            <GMapAutocomplete
                placeholder="Busca cualquier lugar"
                @place_changed="setPlace"
                style="margin-right: 20px; padding: 10px;" class="form-control col"
                ></GMapAutocomplete>
            <GMapMap
            :center="mapCenter"
            :zoom="12"
            map-type-id="roadmap"
            style="height: 50vh; width: 100%; padding: 10px; margin-top: 10px; border-radius: 10px;"
            @click="handleMapClick"
            >
            <GMapMarker
              v-for="(m, index) in marker"
              :key="index"
              :position="m.position"/>
          </GMapMap>
          </div>
            <!--Success-->
            <div class="mt-3">
              <button id="btnPublicar" style="margin-right: 10px;" type="submit" class="btn btn-success">Publicar</button>
              <button type="submit" class="btn btn-danger" @click="onCancel">Cancelar</button>
            </div>
          </div>
          </div>
      </div>
    </form>
  </div>
</template>

<script>
import { ref, onBeforeMount, onMounted, computed,watch } from 'vue'
import { useStore } from 'vuex'
import { loadStripe } from '@stripe/stripe-js';

export default {
  setup() {

    var isDragging = ref(false)
    const roomsNumber = ref(1)
    const bathroomsNumber = ref(1)
    const floor = ref()
    const area = ref()
    const location = ref('')
    const cadastre = ref('')
    const heating = ref('')
    const heatingSelect = ref([])
    const selectedTags = ref([])
    const tags = ref([])
    const success = ref(false)
    const filteredTenants = ref([])
    const selectedCadastre = ref('')
    

    //ADVERTISEMENT
    const userSearch = ref('')
    const phoneSearch = ref('')
    const title = ref('')
    const description = ref('')
    const price = ref()
    const tenants = ref([])
    const tenantsSelect = ref([])
    const selectedTenants = ref([])
    const imagesUrl = ref([])
    const images = ref([])
    const x = ref(null)
    const y = ref(null)
    const auth = ref();
    const authorAdvertisementsNumber = ref([]);

    const stripePromise = loadStripe('' + import.meta.env.VITE_STRIPE_PUBLISHABLE_KEY);
    const loading = ref(false);
    const lineItems = ref(null);

    const updateMeta = (title, description) => {
            document.querySelector('meta[name="description"]').setAttribute('content', description);
            document.querySelector('meta[property="og:title"]').setAttribute('content', title);
            document.querySelector('meta[property="og:description"]').setAttribute('content', description);
            };

    onMounted(() => {
      updateMeta('Publica tu Anuncio de Vivienda en Cohabify: Rápido y Efectivo', 'Publica anuncios de vivienda en Cohabify y alcanza a potenciales inquilinos rápidamente. Aprovecha una plataforma intuitiva para listar las características, ubicación y precio de tu propiedad.');
    });


    const store = useStore()
    const user = computed(() => store.state.user);

    const marker = ref([]);

    onBeforeMount(() => {
      fetchTags();
      const urlParams = new URLSearchParams(window.location.search);
      const sessionId = urlParams.get('session_id');
      if (sessionId) {
        fetchHouseAdvertisement(sessionId);
      }

      });
    
    const mapCenter = ref({ lat: 40.416775, lng: -3.703790 });
    
    const handleMapClick = (event) => {
      const newMarker = {
          position: {
            lat: event.latLng.lat(),
            lng: event.latLng.lng(),
          },
      };
      marker.value = [];
      marker.value.push(newMarker);
      x.value=  event.latLng.lng();
      y.value =  event.latLng.lat();
      console.log(x.value, y.value)
    }

    const setPlace= (place) =>{
          mapCenter.value = {
          lat: place.geometry.location.lat(),
          lng: place.geometry.location.lng(),
          };
        }
      const fetchHouseAdvertisement = (sessionId) => {
      const response = fetch(import.meta.env.VITE_BACKEND_URL + '/api/stripe/session', {
          method: 'POST',
          headers: {
              'Authentication': 'Bearer ' + localStorage.getItem("authentication"),
              'Content-Type': 'application/json'
          },
          body: JSON.stringify({ sessionId: sessionId })
      }).then(response => {
          if (response.status === 200) {
              return response.json();
          } else { 
              throw new Error('Error al cargar la sesión de stripe');
          }
          }) 
          .then(jsonData => {
            user.value.numAdvertisements = 1;
          })
          .catch(error => {
              console.error('Error al cargar la sesión de stripe:', error);
          })
      }

    const fetchAuthorAdvertisements = async () => {
      fetch(import.meta.env.VITE_BACKEND_URL + '/api/advertisements/houses/owner/' + user.value.id, {
        method: "GET",
        headers: {
          'Authentication': 'Bearer ' + localStorage.getItem("authentication"),
        },
        credentials: "include",
      })
      .then((response) => {
        return response.json();
      })
      .then((json) => {
        authorAdvertisementsNumber.value = json.length;
      })
      .catch((error) => {
        console.error(error);
      });
    }

    const toggleTag = (tag) => {
      const index = selectedTags.value.indexOf(tag);
      if (index !== -1) {
        selectedTags.value.splice(index, 1);
      } else {
        selectedTags.value.push(tag);
      }
    };

    const fetchUser = async () => {
      try {
        
        if (userSearch.value === '' || phoneSearch.value === '') {
          return
        }
        for (let i = 0; i < selectedTenants.value.length; i++) {
          if (selectedTenants.value[i].username === userSearch.value || selectedTenants.value[i].phone === phoneSearch.value) {
           alert("Usuario ya seleccionado");
            return;
          }
        }
       let data = {
          username: userSearch.value,
          phone: phoneSearch.value,
        }
        const response = await fetch(import.meta.env.VITE_BACKEND_URL + `/api/user/secureSearch`,
          {
            method: "POST",
            headers: {
                        'Authentication': 'Bearer ' + localStorage.getItem("authentication"),
                        'Content-Type': 'application/json'
                    },
            credentials: "include",
            body: JSON.stringify(data),
          });

        if (response.ok) {
          const data = await response.json();
          selectedTenants.value.push(data);
          userSearch.value = '';
          phoneSearch.value = '';
        } else if (response.status === 404){
          alert("Usuario no encontrado");
        }else{
          window.location.href = "/404";
        }

      } catch (error) {
        console.error("Error:", error);
      }
    };

    const fetchTags = async () => {
      try {
        const response = await fetch(import.meta.env.VITE_BACKEND_URL + `/api/tag/types/FLAT_TAG`,
          {
            method: "GET",
            headers: {
                        'Authentication': 'Bearer ' + localStorage.getItem("authentication"),
                    },
            credentials: "include",
          });

        if (response.ok) {
          const data = await response.json();
          tags.value = data;
          await fetchHeating()
          fetchAuthorAdvertisements();

        } else {
          window.location.href = "/404";
        }

      } catch (error) {
        console.error("Error:", error);
      }
    };

    const fetchHeating = async () => {
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

        const response = await fetch(import.meta.env.VITE_BACKEND_URL + `/api/advertisements/houses/heating`,
          {
            method: "GET",
            headers: {
                        'Authentication': 'Bearer ' + localStorage.getItem("authentication"),
                    },
            credentials: "include",
          });

        if (response.ok) {
          const data = await response.json();
          heatingSelect.value = data;

        } else {
          window.location.href = "/404";
        }

      } catch (error) {
        console.error("Error:", error);
      }
    };

    const fetchCadastre = async () => {
      try{
        const response = await fetch(`https://ovc.catastro.meh.es/OVCServWeb/OVCWcfCallejero/COVCCallejeroCodigos.svc/json/Consulta_DNPRC_Codigos?RefCat=${cadastre.value}`)
        if (response.ok) {
          const data = await response.json();
          if (data.consulta_dnprcResult.control.cuerr){
           console.error(data.consulta_dnprcResult.lerr[0].des);
          }
          location.value = data.consulta_dnprcResult.bico.bi.ldt;
          area.value = data.consulta_dnprcResult.bico.bi.debi.sfc;
          selectedCadastre.value = cadastre.value;

        } else {
          window.location.href = "/404";
        }
      }catch (error){
        console.error("Error:", error);
        alert("Catastro no encontrado");
      }
    }
    const send = (formData) =>{
      fetch(import.meta.env.VITE_BACKEND_URL + '/api/advertisements/houses', {
        method: 'POST',
        headers: {
                        'Authentication': 'Bearer ' + localStorage.getItem("authentication"),
                    },
        body: formData,
      })
        .then(response => response.json())
        .then(jsonData => {
          success.value = true;
          user.numAdvertisements -= 1;
          setTimeout(() => {
            window.location.href = "/";
          }, 1000);
        }
        
        
        )
        .catch(error => console.error('Error al enviar datos al backend:', error));

    }

    const register = () => {
      document.getElementById("btnPublicar").disabled = true;

      if (selectedTags.value.length === 0) {
        alert("Selecciona al menos una etiqueta");
        document.getElementById("btnPublicar").disabled = false;
        return;
      }

      if (images.value.length === 0) {
        alert("Selecciona al menos una imagen");
        document.getElementById("btnPublicar").disabled = false;
        return;
      }

      if(x.value === null || y.value === null){
        alert("Selecciona una ubicación en el mapa");
        document.getElementById("btnPublicar").disabled = false;
        return;
        
      }
      if(area.value === '' || area.value === 0){
        alert("Introduce una superficie válida");
        return;
      }
      
      const formData = new FormData();
      formData.append("string-data", new Blob([JSON.stringify({
        title:title.value,
        description: description.value,
        price: price.value,
        tenants: selectedTenants.value,
        author: auth.value,
        x: x.value,
        y: y.value,
        house: {
          roomsNumber: roomsNumber.value,
          bathroomsNumber: bathroomsNumber.value,
          floor: floor.value,
          area: area.value,
          location: location.value,
          cadastre: selectedCadastre.value,
          heating: heating.value,
          tags: selectedTags.value,
        }
      })], { type: "application/json" }));
      for (let i = 0; i < images.value.length; i++) {
        formData.append("images", images.value[i]);
      }
      send(formData);
    };
    return {
      roomsNumber,
      bathroomsNumber,
      floor,
      area,
      location,
      cadastre,
      heating,
      tags,
      selectedTags,
      title,//Advertisement
      description,
      price,
      tenants,
      tenantsSelect,
      images,
      imagesUrl,
      register,
      isDragging,
      userSearch,
      phoneSearch,
      selectedTenants,
      heatingSelect,
      fetchUser,
      toggleTag,
      success,
      filteredTenants,
      fetchCadastre,
      selectedCadastre,
      auth,
      authorAdvertisementsNumber,
      user,
      mapCenter,
      setPlace,
      handleMapClick,
      marker,
    }
  },
  watch: {
    selectedTenants(newVal) {
      this.tenants = newVal;
    }
  },
  methods: {
    onCancel() {
      const confirmExit = window.confirm('¿Estás seguro de que deseas salir?');
      if (confirmExit) {
        this.$router.push({ name: 'main' });
      }
    },
    selectFiles() {
      this.$refs.fileInput.value = null
      this.$refs.fileInput.click()
    },

    onFileSelect(event) {
      const files = event.target.files
      if (this.imagesUrl.length + files.length > 10) {
        alert("Selecciona 10 imágenes máximo")
        return
      }

      for (let i = 0; i < files.length; i++) {
        if (files[i].size > 1000000) {
          alert('La imagen debe pesar menos de 1MB')
          return
        } else {
          if (files[i].type.split('/')[0] !== 'image') continue
          if (!this.images.some((e) => e.name === files[i].name)) {
            this.images.push(files[i])
            this.imagesUrl.push({ name: files[i].name, url: URL.createObjectURL(files[i]) })
          }
        }
      }

    },
    deleteImage(index) {
      this.images.splice(index, 1)
      this.imagesUrl.splice(index, 1)
    },
    onDragover(event) {
      event.preventDefault()
      this.isDragging = true
      event.dataTransfer.dropEffect = 'copy'
    },
    onDragLeave(event) {
      event.preventDefault()
      this.isDragging = false
    },
    onDrop(event) {
      event.preventDefault()
      this.isDragging = false
      const files = event.dataTransfer.files
      if (this.imagesUrl.length + files.length > 10) {
        alert("Selecciona 10 imágenes máximo")
        return
      }
      for (let i = 0; i < files.length; i++) {
        if (files[i].size > 1000000) {
          alert('La imagen debe pesar menos de 1MB')
          return
        } else {
          if (files[i].type.split('/')[0] !== 'image') continue
          if (!this.images.some((e) => e.name === files[i].name)) {
            this.images.push(files[i])
            this.imagesUrl.push({ name: files[i].name, url: URL.createObjectURL(files[i]) })
          }
        }
      }
    },
    increaseRoomsNumber() {
      if (this.roomsNumber < 6) {
        this.roomsNumber++
      }

    },
    decreaseRoomsNumber() {
      if (this.roomsNumber > 1) {
        this.roomsNumber--
      }

    },
    increaseBathroomsNumber() {
      if (this.bathroomsNumber < 6) {
        this.bathroomsNumber++
      }

    },
    decreaseBathoomsNumber() {
      if (this.bathroomsNumber > 1) {
        this.bathroomsNumber--
      }
    },
  }
}
</script>

<style scoped>
.card {
  width: 100%;
  padding: 10%;
  box-shadow: 0 0 3px #142e50;
  border-radius: 10px;
  overflow: hidden;
}

.card .top {
  text-align: center;
}

.card p {
  font-weight: bold;
  color: #142e50
}

.card button {
  outline: 0;
  border: 0;
  color: rgb(241, 249, 255);
  border-radius: 4px;
  font-weight: 400;
  padding: 8px 13px;
  width: 100%;
  background: #142e50;

}

.card .drag-area {
  height: 150px;
  border-radius: 5px;
  border: 2px dashed #142e50;
  background: #f3f8ff;
  color: #142e50;
  display: flex;
  justify-content: center;
  align-items: center;
  user-select: center;
  -webkit-user-select: none;
  margin-top: 10px;
}

.card .drag-area .visible {
  font-size: 18px;
}

.card .select {
  color: #3f1db8;
  cursor: pointer;
  margin-left: 5px;
  cursor: pointer;
  transition: 0.4s;
}

.card .select:hover {
  opacity: 0.6;
}

.card .container {
  width: 100%;
  height: auto;
  display: flex;
  justify-content: flex-start;
  align-items: flex-start;
  flex-wrap: wrap;
  max-height: 200px;
  position: relative;
  margin-bottom: 3px;
  padding-top: 8px;
}

.card .container .image {
  width: 75px;
  margin-right: 5px;
  height: 75px;
  position: relative;
  margin-bottom: 8px;
}

.card .container .image img {
  width: 100%;
  height: 100%;
  border-radius: 5px;
}

.card .container .image span {
  position: absolute;
  top: -2px;
  right: 9px;
  font-size: 20px;
  cursor: pointer;
}

.card input,
.card .drag-area .on-drop,

.card .drag-area.dragover .visible {
  display: none;
}

.delete {
  z-index: 999;
  color: #ff0000;
  font-weight: bold;
  font-size: 30px;
}

button {
    border: none;
    border-radius: 4px;
    cursor: pointer;
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
}

.tags-container {
display: flex;
flex-wrap: wrap;
background-color: rgba(182, 205, 239, 0);
}

.tag {
display: inline-block;
padding: 5px 10px;
margin: 5px;
border-radius: 20px;
cursor: pointer;
background-color: rgb(156, 182, 221);
color: #28426B;
}

.selected {
background-color: #28426B;
color: #ffff;
}

.wrapper .icon {
  position: relative;
  background: #fff;
  color: black;
  border-radius: 50%;
  width: 20px;
  height: 20px;
  font-size: 18px;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  box-shadow: 0 10px 10px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: all 0.2s cubic-bezier(0.68, -0.55, 0.265, 1.55);
}

.wrapper .tooltip {
  position: absolute;
  top: 0;
  font-size: 16px;
  background: #fff;
  color: black;
  padding: 5px 8px;
  border-radius: 5px;
  box-shadow: 0 10px 10px rgba(0, 0, 0, 0.1);
  opacity: 0;
  pointer-events: none;
  width: 20em;
  transition: all 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);
}

.wrapper .tooltip::before {
  position: absolute;
  content: "";
  height: 10px;
  width: 10px;
  background: #fff;
  bottom: -3px;
  left: 50%;
  transform: translate(-50%) rotate(45deg);
  transition: all 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);
}


.wrapper .icon:hover .tooltip {
  top: -90px;
  width: 20em;
  opacity: 1;
  visibility: visible;
  pointer-events: auto;
  background: #1da1f2;
  color: #fff;
}

.wrapper .icon:hover .tooltip::before {
  background: #1da1f2;
}

.wrapper .icon:hover {
  background: #1da1f2;
  color: #fff
}


</style>