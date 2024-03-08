
<template>
  <Navbar />
  <div class="container">
    <h1 class="text-center mt-4">Anuncio de vivienda</h1>
    <form @submit.prevent="register">
      <div class="row mt-5 justify-content-center">

        <div class=" row justify-content-center">
              <div class="mb-3 w-75 text-start">
                  <label for="title" class="form-label"><strong>Título</strong></label>
                  <input type="text" class="form-control" id="title" v-model="title" required placeholder="Escribe un título para el anuncio...">
              </div>
              <div class="mb-3 w-75 text-start">
                  <label for="description" class="form-label"><strong>Descripción</strong></label>
                  <textarea class="form-control" id="description" rows="3" v-model="description" required placeholder="Escribe una descripción para el anuncio..."></textarea>
              </div>
              <div class="row w-75 mb-3">
              <div class="mb-3 w-75 text-start col">
                      <label for="cadastre" class="form-label"><strong>Catastro</strong></label>
                      <input type="text" class="form-control" id="cadastre" v-model="cadastre" required placeholder="12345678901234567890...">
              </div>
              <div class="mb-3 w-75 text-start col">
                      <label for="location" class="form-label"><strong>Ubicación</strong></label>
                      <input type="text" class="form-control" id="location" v-model="location" required placeholder="C/...">
              </div>
            </div>
              <div class="row w-75 mb-3">
                  <div class="col text-start">
                    <label for="area" class="form-label"><strong>Superficie</strong></label>
                    <div class="input-group">
                      <input type="number" class="form-control" id="area" v-model="area" required placeholder="0">
                      <span class="input-group-text" style="color: grey;">m²</span>
                    </div>
                      
                  </div>
                  <div class="col mb-3 text-start">
                      <label for="floor" class="form-label"><strong>Planta</strong></label>
                      <input type="number" class="form-control" id="floor" v-model="floor" required placeholder="0">
                  </div>
                  <div class="col text-start">
                    <div class="mb-3">
                      <label for="price" class="form-label"><strong>Precio</strong></label>
                      <div class="input-group">
                      <input type="number" class="form-control" id="area" v-model="price" required placeholder="0">
                      <span class="input-group-text" style="color: grey;">€</span>
                    </div>
                        </div>
                  </div>
              </div>
              <div class="row w-75 mb-3" >
                  <div class="col">
                      <div class="mb-3">
                          <label for="roomsNumber" class="form-label"><strong>Habitaciones</strong></label>
                          <div class="input-group d-flex align-items-center justify-content-center">
                            <button class=" btn-primary" type="button" @click="decreaseRoomsNumber()">-</button>
                            <p class="mx-3" style=" margin: auto;">{{ roomsNumber }}</p>
                            <button class="btn-primary" type="button" @click="increaseRoomsNumber()">+</button>
                          </div>
                      </div>
                  </div>
                  <div class="col">
                    <div class="mb-3">
                      <label for="bathroomsNumber" class="form-label"><strong>Baños</strong></label>
                          <div class="input-group d-flex align-items-center justify-content-center">
                            <button class="btn-primary" type="button" @click=" decreaseBathoomsNumber()">-</button>
                            <p class="mx-3" style=" margin: auto;">{{ bathroomsNumber }}</p>
                            <button class="btn-primary" type="button" @click="increaseBathroomsNumber()">+</button>
                          </div>
                        </div>
                  </div>
                  <div class="col">
                    <div class="mb-3">
                      <div>
                      <label for="heating" class="form-label"><strong>Calefacción</strong></label>
                      </div>
                        <select id="heating" name="heating" v-model="heating" required>
                          <option value="" disabled selected hidden>Selecciona una opción</option>
                          <option value="NATURAL_GAS">Gas Natural</option>
                          <option value="RADIATOR">Radiador</option>
                          <option value="CENTRAL_HEATING">Calefacción central</option>
                          <option value="AIR_CONDITIONING">Aire acondicionado</option>
                        </select>
                        </div>
                  </div>
              </div>
              <div class="row w-75 mb-3">
                <div class="col">
                    <div class ="text-start">
                      <label for="tenants" class="form-label"><strong>Inquilinos</strong></label>
                    </div>
                    <input type="text" class="form-control" v-model="search" placeholder="Buscar inquilinos">
                    <div style="max-height: 60px; overflow-y: auto;">
                    <div v-for="(tenant, index) in filteredTenants" :key="index">
                        <input type="checkbox" :id="tenant.username" :value="tenant" v-model="selectedTenants">
                        <label :for="tenant.username">{{ tenant.username }}</label>
                    </div>
                  </div>
                    <div class ="text-start mb-3" v-if="selectedTenants.length > 0">
                        <p><strong>Inquilinos seleccionados:</strong></p>
                         <div style="max-height: 60px; overflow-y: auto;">
                        <ul>
                            <li v-for="(tenant, index) in selectedTenants" :key="'selected' + index">{{ tenant.username }}</li>
                        </ul>
                      </div>
                    </div>
                </div>
                <div class="col">
                      <label for="tags" class="form-label">Etiquetas</label>
                      <select id="tags" name="tags" v-model="tags" class="form-select" size="3" multiple aria-label="Size 3 select example" required>
                        <option v-for="(tag, index) in tagsSelect" :key="index" :value="tag">{{ tag.tag }}</option>
                      </select>
                  </div>
              </div>
        </div>
              <!--IMAGES-->
        <div class=" row d-flex w-50 mb-5">
              <div class="card mt-3">
                  <div class="top">
                      <p>Arrastra aquí tus imágenes</p>
                  </div>
                  <div class="drag-area" @dragover.prevent="onDragover" @dragleave.prevent="onDragLeave" @drop.prevent="onDrop">
                      <span v-if="!isDragging">
                          Arrastra aquí tu imagen o
                          <span class="select" role="button" @click="selectFiles">
                              Elige
                          </span>
                      </span>
                      <div v-else class="select">Deja la imagen aquí</div>
                      <input name="file" type="file" accept="image/*" class="file" ref="fileInput" multiple @change="onFileSelect"/>
                  </div>
                  <div class="container">
                      <div class="image" v-for="(image, index) in imagesUrl" :key="index">
                          <span class="delete" @click="deleteImage(index)">&times;</span>
                          <img :src="image.url" />
                      </div>
                  </div>
              </div>
              <!--Success-->
              <div class="mt-3">
                  <button style="margin-right: 10px;" type="submit" class="btn btn-success">Publicar</button>
                  <button type="submit" class="btn btn-danger" @click="onCancel">Cancelar</button>
              </div>
        </div> 
      </div>
    </form>
  </div>
</template>

<script>
import { ref } from 'vue'

export default {
    setup() {
        var isDragging = ref(false)        
        const roomsNumber = ref(0)
        const bathroomsNumber = ref(0)
        const floor = ref()
        const area = ref()
        const location = ref('')
        const cadastre = ref('')
        const heating = ref('')
        const tagsSelect = ref([])
        const tags = ref([])

        //ADVERTISEMENT
        const search = ref('')
        const title = ref('')
        const description = ref('')
        const price = ref()
        const tenants = ref([])
        const tenantsSelect =ref([])
        const selectedTenants = ref([])
        const imagesUrl = ref([])
        const images= ref([])

        const fetchUsers = async () => {
            try {
                const response = await fetch(import.meta.env.VITE_BACKEND_URL + `/api/user/list`,
                    {
                        method: "GET",
                        credentials: "include",
                    });

                    if (response.ok) {
                        const data = await response.json();
                        tenantsSelect.value = data;
                        await fetchTags()
                    } else {
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
                        credentials: "include",
                    });

                    if (response.ok) {
                        const data = await response.json();
                        tagsSelect.value = data;
                        
                    } else {
                        window.location.href = "/404";
                    }

                } catch (error) {
                console.error("Error:", error);
            }
        };
        
        fetchUsers();
        

        const register = () => {
          const data = {
              roomsNumber: roomsNumber.value,
              bathroomsNumber: bathroomsNumber.value,
              floor: floor.value,
              area: area.value,
              location: location.value,
              cadastre: cadastre.value,
              heating: heating.value,
              tags: tags.value,
              title: title.value,//advertisement
              description: description.value,
              price: price.value,
              tenants: tenants.value,
              images: images.value

          }
          if(data.images.length === 0){
            alert("Selecciona al menos una imagen");
            return;
          }
            const formData = new FormData();
            formData.append("string-data", new Blob([JSON.stringify({
                        title: data.title,
                        description: data.description,
                        price: data.price,
                        tenants: data.tenants,
                        
                        house: {
                          roomsNumber: data.roomsNumber,
                          bathroomsNumber: data.bathroomsNumber,
                          floor: data.floor,
                          area: data.area,
                          location: data.location,
                          cadastre: data.cadastre,
                          heating: data.heating,
                          tags: data.tags
                        }
                    })], { type: "application/json" }));
                    for (let i = 0; i < data.images.length; i++) {
                        formData.append("images", data.images[i]);
                    }
            fetch(import.meta.env.VITE_BACKEND_URL + '/api/advertisements/houses', {
                    method: 'POST',
                    body: formData,
                })
                    .then(response => response.json())
                    .then(jsonData => window.location.href = '/')
                    .catch(error => console.error('Error al enviar datos al backend:', error));

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
            tagsSelect,
            title,//Advertisement
            description,
            price,
            tenants,
            tenantsSelect,
            images,
            imagesUrl,
            register,
            isDragging,
            search,
            selectedTenants,
            
        }
    },
    computed: {
        filteredTenants() {
            return this.tenantsSelect.filter(tenant => tenant.username.includes(this.search));
        }
    },
    watch: {
        selectedTenants(newVal) {
            this.tenants = newVal;
        }
    },
    methods:{
        onCancel() {
          const confirmExit = window.confirm('¿Estás seguro de que deseas salir?');
          if (confirmExit) {
            this.$router.push({ name: 'main' });
          }
        },
          selectFiles(){
            this.$refs.fileInput.value = null
            this.$refs.fileInput.click()
        },
        
        onFileSelect(event){
          const files = event.target.files
          if(this.imagesUrl.length+files.length > 10){
            alert("Selecciona 10 imágenes máximo")
            return
          }
          
            for(let i = 0; i<files.length; i++){
              if (files[i].size > 1000000) {
                alert('La imagen debe pesar menos de 1MB')
                return
              } else {
              if(files[i].type.split('/')[0] !== 'image') continue
              if(!this.images.some((e)=> e.name === files[i].name)){
                this.images.push(files[i])
                this.imagesUrl.push({name: files[i].name, url: URL.createObjectURL(files[i])})
              }
            }
            }
          
        },
        deleteImage(index){
          this.images.splice(index, 1)
          this.imagesUrl.splice(index, 1)
        },
        onDragover(event){
          event.preventDefault()
          this.isDragging = true
          event.dataTransfer.dropEffect = 'copy'
        },
        onDragLeave(event){
          event.preventDefault()
          this.isDragging = false
        },
        onDrop(event){
          event.preventDefault()
          this.isDragging = false
          const files = event.dataTransfer.files
          if(this.imagesUrl.length+files.length > 10){
            alert("Selecciona 10 imágenes máximo")
            return
          }
          for(let i = 0; i<files.length; i++){
            if (files[i].size > 1000000) {
              alert('La imagen debe pesar menos de 1MB')
              return
            } else {
              if(files[i].type.split('/')[0] !== 'image') continue
              if(!this.images.some((e)=> e.name === files[i].name)){
                this.images.push(files[i])
                this.imagesUrl.push({name: files[i].name, url: URL.createObjectURL(files[i])})
              }
            }
            }
        },
        increaseRoomsNumber(){
          if(this.roomsNumber < 6){
            this.roomsNumber++
          }
          
        },
        decreaseRoomsNumber(){
          if(this.roomsNumber > 0){
            this.roomsNumber--
          }
          
        },
        increaseBathroomsNumber(){
          if(this.bathroomsNumber < 6){
            this.bathroomsNumber++
          }
          
        },
        decreaseBathoomsNumber(){
          if(this.bathroomsNumber > 0){
            this.bathroomsNumber--
          }
        },
    }
}
</script>

<style scoped>
.card{
  width: 100%;
  padding: 10%;
  box-shadow: 0 0 3px #142e50;
  border-radius: 10px;
  overflow: hidden;
}

.card .top{
  text-align: center;
}

.card p{
  font-weight: bold;
  color:#142e50
}

.card button{
  outline:0;
  border:0;
  color:rgb(241, 249, 255);
  border-radius: 4px;
  font-weight: 400;
  padding: 8px 13px;
  width: 100%;
  background: #142e50;

}

.card .drag-area{
  height:150px;
  border-radius:5px;
  border: 2px dashed #142e50;
  background: #f3f8ff;
  color:#142e50;
  display:flex;
  justify-content: center;
  align-items: center;
  user-select: center;
  -webkit-user-select: none;
  margin-top: 10px;
}

.card .drag-area .visible{
  font-size: 18px;
}

.card .select{
  color: #3f1db8;
  cursor: pointer;
  margin-left: 5px;
  cursor: pointer;
  transition: 0.4s;
}

.card .select:hover{
  opacity: 0.6;
}

.card .container{
  width: 100%;
  height: auto;
  display: flex;
  justify-content: flex-start;
  align-items: flex-start;
  flex-wrap: wrap;
  max-height: 200px;
  position: relative;
  margin-bottom:8px;
  padding-top: 15px;
}
.card .container .image{
  width:75px;
  margin-right: 5px;
  height: 75px;
  position: relative;
  margin-bottom: 8px;
}

.card .container .image img{
  width:100%;
  height: 100%;
  border-radius: 5px;
}

.card .container .image span{
  position: absolute;
  top: -2px;
  right: 9px;
  font-size: 20px;
  cursor: pointer;
}

.card input, 
.card .drag-area .on-drop,

.card .drag-area.dragover .visible{
  display: none;
}

.delete{
  z-index: 999;
  color:#ff0000;
  font-weight: bold;
  font-size: 30px;
}
</style>