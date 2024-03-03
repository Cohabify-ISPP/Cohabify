
<template>
  <Navbar />
  <div class="container">
      <div class="row mt-5 mt-5 align-items-center justify-content-center text-center">
        <div class="d-flex w-75 p-3 ">
          <div class="container text-center">
            <form @submit.prevent="register">
              <div class="mb-3">
                  <label for="title" class="form-label">Titulo</label>
                  <input type="text" class="form-control" id="title" v-model="title" required>
                  <div class="invalid-feedback">
                      Por favor, introduce un nombre.
                  </div>
              </div>
              <div class="mt-5">
                  <label for="description" class="form-label">Descripción</label>
                  <textarea class="form-control" id="description" rows="3" v-model="description" required></textarea>
              </div>
              <div class="row mt-5">
                  <div class="col">
                      <label for="price" class="form-label">Precio</label>
                      <input type="number" class="form-control" min="0" id="price" v-model="price" required>
                      <div class="invalid-feedback">
                          Por favor, introduce un precio.
                      </div>
                  </div>
                  <div class="col">
                      <label for="location" class="form-label">Ubicación</label>
                      <input type="text" class="form-control" id="location" v-model="location" required>
                  </div>
              </div>
              <div class="row mt-5">
                  <div class="col">
                    <div class="align-middle">
                      <label for="heating" class="form-label">Tipo de calefacción</label>
                    </div>
                    <select id="heating" name="heating" v-model="heating" required>
                        <option value="NATURAL_GAS">Gas Natural</option>
                        <option value="RADIATOR">Radiador</option>
                        <option value="CENTRAL_HEATING">Calefacción central</option>
                        <option value="AIR_CONDITIONING">Aire acondicionado</option>
                    </select>
                    
                  </div>
                  <div class="col">
                      <label for="tags" class="form-label">Etiquetas</label>
                      <select id="tags" name="tags" v-model="tags" class="form-select" size="3" multiple aria-label="Size 3 select example" required>
                        <option v-for="(tag, index) in tagsSelect" :key="index" :value="tag">{{ tag.tag }}</option>
                      </select>
                  </div>
                  <div class="col">
                      <label for="tenants" class="form-label">Inquilinos</label>
                      <select id="tenants" name="tenants" v-model="tenants" class="form-select" size="3" multiple aria-label="Size 3 select example" required>
                        <option v-for="(tenant, index) in tenantsSelect" :key="index" :value="tenant">{{ tenant.username }}</option>
                      </select>
                  </div>
              </div>
              <div class="row mt-5">
                  <div class="d-flex justify-content-between">
                      <div style="padding-right: 10%;">
                          <label for="roomsNumber" class="form-label text-center">Habitaciones</label>
                          <input type="number" class="form-control text-center" id="bedrooms" v-model="roomsNumber" required>
                      </div>
                      <div style="padding-right: 10%;">
                          <label for="bathroomsNumber" class="form-label text-center">Baños</label>
                          <input type="number" class="form-control text-center" id="bathroomsNumber" v-model="bathroomsNumber" required>
                      </div>
                      <div style="padding-right: 10%;">
                          <label for="area" class="form-label text-center">m²</label>
                          <input type="number" class="form-control text-center" id="area" v-model="area" required>
                      </div>
                      <div>
                          <label for="floor" class="form-label text-center">Plantas</label>
                          <input type="number" class="form-control text-center" id="floor" v-model="floor" required>
                      </div>
                  </div>
              </div>
              <div class="row mt-5">
                  <div class="col">
                      <label for="cadastre" class="form-label">Catastro</label>
                      <input type="text" class="form-control" id="cadastre" v-model="cadastre" required>
                  </div>
                  <div class="col">
                      <label for="x" class="form-label">Coordenada X</label>
                      <input type="text" class="form-control" id="x" v-model="x" required>
                  </div>
                  <div class="col">
                      <label for="y" class="form-label">Coordenada Y</label>
                      <input type="text" class="form-control" id="y" v-model="y" required>
                  </div>
              </div>
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
                      <input name="file" type="file" accept="image/*" class="file" ref="fileInput" multiple @change="onFileSelect" required/>
                  </div>
                  <div class="container">
                      <div class="image" v-for="(image, index) in imagesUrl" :key="index">
                          <span class="delete" @click="deleteImage(index)">&times;</span>
                          <img :src="image.url" />
                      </div>
                  </div>
              </div>
              <div class="mt-3">
                  <button style="margin-right: 10px;" type="submit" class="btn btn-success">Publicar</button>
                  <button type="submit" class="btn btn-danger" @click="onCancel">Cancelar</button>
              </div>
          </form>


          </div>
        </div> 
      </div>
  </div>
</template>

<script>
import { ref } from 'vue'

export default {
    setup() {
        const roomsNumber = ref()
        const bathroomsNumber = ref()
        const floor = ref()
        const area = ref()
        const location = ref('')
        const cadastre = ref('')
        const heating = ref('')
        const x = ref()
        const y = ref()
        const tagsSelect = ref([])
        const tags = ref([])

        //ADVERTISEMENT

        const title = ref('')
        const description = ref('')
        const price = ref()
        const tenants = ref([])
        const tenantsSelect =ref([])
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
                        await fetchValorations()
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
                        await fetchValorations()
                    } else {
                        window.location.href = "/404";
                    }

                } catch (error) {
                console.error("Error:", error);
            }
        };
        
        fetchUsers();
        fetchTags();

        const register = () => {
          const data = {
              roomsNumber: roomsNumber.value,
              bathroomsNumber: bathroomsNumber.value,
              floor: floor.value,
              area: area.value,
              location: location.value,
              x: x.value,
              y: y.value,
              cadastre: cadastre.value,
              heating: heating.value,
              tags: tags.value,
              title: title.value,//advertisement
              description: description.value,
              price: price.value,
              tenants: tenants.value,
              images: images.value

          }
            const formData = new FormData();
            formData.append("string-data", new Blob([JSON.stringify({
                        title: data.title,
                        description: data.description,
                        price: data.price,
                        tenants: data.tenants,
                        x: data.x,
                        y: data.y,
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
                    })], { type: "application/json" }))
            formData.append("profile-pic1", data.images[0])
            fetch(import.meta.env.VITE_BACKEND_URL + '/api/advertisements/houses/advertisements', {
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
            x,
            y,
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
            register
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
          this.$refs.fileInput.click();
        },
        
        onFileSelect(event){
          const files = event.target.files;
          if(files.length>10){
            alert("Selecciona 10 imágenes máximo");
            return;
          }
          
            for(let i = 0; i<files.length; i++){
              if(files[i].type.split('/')[0] !== 'image') continue;
              if(!this.images.some((e)=> e.name === files[i].name)){
                this.images.push(files[i]);
                this.imagesUrl.push({name: files[i].name, url: URL.createObjectURL(files[i])});
              }
            }
          
        },
        deleteImage(index){
          this.images.splice(index, 1);
        },
        onDragover(event){
          event.preventDefault();
          this.isDragging = true;
          event.dataTransfer.dropEffect = 'copy';
        },
        onDragLeave(event){
          event.preventDefault();
          this.isDragging = false;
        },
        onDrop(event){
          event.preventDefault();
          this.isDragging = false;
          const files = event.dataTransfer.files;
          for(let i = 0; i<files.length; i++){
              if(files[i].type.split('/')[0] !== 'image') continue;
              if(!this.images.some((e)=> e.name === files[i].name)){
                this.images.push(files[i]);
                this.imagesUrl.push({name: files[i].name, url: URL.createObjectURL(files[i])});
              }
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
