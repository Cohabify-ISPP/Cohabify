
<template>
  <Navbar />
  <div class="container">
      <div class="row mt-5">
        <div class="d-flex align-items-center justify-content-center text-center w-75 p-3 ">
          <div class="container text-center">
            <form class="row justify-content-center">
              <div class="col-md-10">
                <div class="form-group" style="padding: 20px;">
                  <label for="title" class="form-label text-white fw-bold">Nombre de usuario</label>
                  <input type="text" class="form-control" id="title" v-model="title" placeholder="Nombre de usuario">
                </div>
                <div class="form-group" style="padding: 20px;">
                  <label for="description" class="form-label text-white fw-bold">Contraseña</label>
                  <input type="text" class="form-control" id="description" v-model="description" placeholder="Contraseña">
                </div>
                <div class="form-group" style="padding: 20px;">
                  <label for="price" class="form-label text-white fw-bold">price</label>
                  <input type="number" step="0.01" class="form-control" id="price" v-model="price">
                </div>
                <div class="form-group" style="padding: 20px;">
                  <label for="tenants" class="form-label text-white fw-bold">tenants</label>
                  <input type="number" class="form-control" id="tenants" v-model="tenants">
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
        const title = ref('')
        const description = ref('')
        const price = ref()
        const tenants = ref()

        const register = () => {
            const data = {
                title: title.value,
                description: description.value,
                price: price.value,
                tenants: tenants.value
            }
            fetch(import.meta.env.VITE_BACKEND_URL + '/api/advertisements/houses/advertisements', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        title: data.title,
                        description: data.description,
                        price: data.price,
                        tenants: data.tenants
                       
                    }),
                })
                    .then(response => response.json())
                    .then(jsonData => window.location.href = '/')
                    .catch(error => console.error('Error al enviar datos al backend:', error));
        };

        return {
            title,
            description,
            price,
            tenants,
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
