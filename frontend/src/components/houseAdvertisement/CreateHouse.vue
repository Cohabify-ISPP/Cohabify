
<template>
  <Navbar />
  <div class="container">
      <div class="row mt-5">
        <div class="d-flex align-items-center justify-content-center text-center w-75 p-3 ">
          <div class="container text-center">
            <form class="row justify-content-center">
              <div class="col-md-10">
                <div class="form-group" style="padding: 20px;">
                  <label for="roomsNumber" class="form-label text-white fw-bold">roomsNumber</label>
                  <input type="number" class="form-control" id="roomsNumber" v-model="roomsNumber" placeholder="Nombre de usuario">
                </div>
                <div class="form-group" style="padding: 20px;">
                  <label for="bathroomsNumber" class="form-label text-white fw-bold">bathroomsNumber</label>
                  <input type="number" class="form-control" id="bathroomsNumber" v-model="bathroomsNumber" placeholder="Contraseña">
                </div>
                <div class="form-group" style="padding: 20px;">
                  <label for="area" class="form-label text-white fw-bold">area</label>
                  <input type="number" class="form-control" id="area" v-model="area" placeholder="Contraseña">
                </div>
                <div class="form-group" style="padding: 20px;">
                  <label for="floor" class="form-label text-white fw-bold">floor</label>
                  <input type="number" class="form-control" id="floor" v-model="floor" placeholder="Contraseña">
                </div>
                <div class="form-group" style="padding: 20px;">
                  <label for="location" class="form-label text-white fw-bold">location</label>
                  <input type="text" class="form-control" id="location" v-model="location" placeholder="Contraseña">
                </div>
                <div class="form-group" style="padding: 20px;">
                  <label for="cadastre" class="form-label text-white fw-bold">cadastre</label>
                  <input type="text" class="form-control" id="cadastre" v-model="cadastre" placeholder="Contraseña">
                </div>
                <div class="form-group" style="padding: 20px;">
                  <select id="heating" name="heating" v-model="heating">
                      <option value="NATURAL_GAS">Gas Natural</option>
                      <option value="RADIATOR">Radiador</option>
                      <option value="CENTRAL_HEATING">Calefacción central</option>
                      <option value="AIR_CONDITIONING">Aire acondicionado</option>
                  </select>
                </div>
                <div class="form-group" style="padding: 20px;">
                  <select id="tags" name="tags" v-model="tags" class="form-select" size="3" multiple aria-label="Size 3 select example">
                    <option value="TAG_HOLA">One</option>
                    <option value="TAG_ADIOS">Two</option>
                    <option value="TAG_QUE_TAL">Three</option>
                  </select>
                </div>
                <div class="form-group" style="padding: 20px;">
                  <label for="x" class="form-label text-white fw-bold">x</label>
                  <input type="number" class="form-control" id="x" v-model="x" placeholder="Contraseña">
                </div>
                <div class="form-group" style="padding: 20px;">
                  <label for="y" class="form-label text-white fw-bold">y</label>
                  <input type="number" class="form-control" id="y" v-model="y" placeholder="Contraseña">
                </div>
                <div class="form-group" style="padding: 20px;">
                    <button type="button" class="btn-primary" @click="register">Publicar</button>
                </div>
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
        const tags = ref([])
        

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
              tags: tags.value
          }
          fetch(import.meta.env.VITE_BACKEND_URL + '/api/houses', {
                  method: 'POST',
                  headers: {
                      'Content-Type': 'application/json',
                  },
                  body: JSON.stringify({
                      roomsNumber: data.roomsNumber,
                      bathroomsNumber: data.bathroomsNumber,
                      floor: data.floor,
                      area: data.area,
                      location: data.location,
                      x: data.x,
                      y: data.y,
                      cadastre: data.cadastre,
                      heating: data.heating,
                      tags: data.tags,
                  }),
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
            register
        }
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
