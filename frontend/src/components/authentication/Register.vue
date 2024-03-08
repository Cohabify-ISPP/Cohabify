<template>
    <div class="mx-auto p-2" style="width: 50%;">
      <img src="/images/LogoCohabify.png" class="img-fluid rounded-start" alt="..." style="max-width: 400px; padding-top: 20px;padding-bottom: 2%;">
      <h1 style="padding-bottom: 30px;">Registro</h1>
      <div class="card">
        <div v-show="success" class="alert alert-success alert-dismissible fade show" role="alert">
          Registro completado con éxito.
        </div>
        <form id="form1"class="row justify-content-center" @submit.prevent="[changePage]" v-if="!secondPage">
            <div class="col-md-6" style="padding-inline: 20px;" v-if="!secondPage">
              <div class="form-group" style="padding: 20px;">
                <label for="name" class="form-label text-white fw-bold">Nombre completo</label>
                <input type="text" required class="form-control" id="name" v-model="name" placeholder="Nombre completo">
              </div>
              <div class="form-group" style="padding: 20px;">
                <label for="phone" class="form-label text-white fw-bold">Teléfono</label>
                <input type="tel" pattern="(\+34|0034|34)?[6789]\d{8}" required class="form-control" id="phone" v-model="phone" placeholder="XXXXXXXXX">
              </div>
              <div class="form-group" style="padding: 20px;">
                <label for="password" class="form-label text-white fw-bold">Contraseña</label>
                <input type="password" required class="form-control" id="password" v-model="password" placeholder="contraseña">
              </div>
            </div>
            <div class="col-md-6" style="padding-inline: 20px;" v-if="!secondPage">
              <div class="form-group" style="padding: 20px;">
                <label for="username" class="form-label text-white fw-bold">Nombre de usuario</label>
                <input type="text" required class="form-control" id="username" v-model="username" placeholder="Nombre de usuario">
              </div>
              <div class="form-group" style="padding: 20px;">
                <label for="email" class="form-label text-white fw-bold">Email</label>
                <input type="email" required class="form-control" id="email" v-model="email" placeholder="email">
              </div>
              <div class="form-group" style="padding: 20px;">
                <label for="confirmPassword" class="form-label text-white fw-bold">Repetir contraseña</label>
                <input type="password" required class="form-control" id="confirmPassword" v-model="confirmPassword" placeholder="repetir contraseña"
                :class="{'is-invalid': password !== confirmPassword}">
                <div class="invalid-feedback text-danger" v-if="password !== confirmPassword">Las contraseñas no coinciden</div>
              </div>

            </div>
          <div style="padding-top: 20px;" >
            <button type="submit" class="btn-primary" @click="changePage">Siguiente</button>
          </div>
        </form>

        <form class="row justify-content-center" @submit.prevent="[register,changePage]"  v-if="secondPage">
          <div class="col-md-6" style="padding-inline: 20px;">
              <div class="form-group" style="padding: 20px;">
                <label for="gender" class="form-label text-white fw-bold">Género</label>
                <select required class="form-select" id="gender" v-model="gender">
                  <option value="MASCULINO">Masculino</option>
                  <option value="FEMENINO">Femenino</option>
                  <option value="OTRO">Otro</option>
                </select>
              </div>
              <label for="tags" class="form-label text-white fw-bold">¿Cómo te describirías?</label>
              <div class="btn-group" role="group" aria-label="Basic checkbox toggle button group">
                <div class="tags-container">
                <span class="tag" v-for="tag in tags" :key="tag.tag" @click="toggleTag(tag)"
                  :class="{ 'selected': selectedTags.includes(tag), 'unselected': !selectedTags.includes(tag) }">
                  {{ tag.tag }}
                </span>
            </div>
              </div>
                <div class="cimg mt-3">
                    <div class="top">
                      <label for="formFile" class="form-label text-white fw-bold">Imagen de perfil</label>
                    </div>
                    <div class="drag-area" @dragover.prevent="onDragover($event)" @dragleave.prevent="onDragLeave($event)" @drop.prevent="onDrop($event)">
                        <span v-if="!isDragging">
                            Arrastra aquí tu imagen o
                            <span class="select" role="button" @click="selectFile">
                                Elige
                            </span>
                        </span>
                        <div v-else class="select">Deja la imagen aquí</div>
                        <input  type="file" accept="image/*" class="file"  ref="fileInput"  @change="onFileChanged" id="formFile" required/>
                    </div>
                    <div class="container">
                        <div class="image" v-if="imgUrl">
                            <span class="delete" @click="deleteFile">&times;</span>
                            <img :src="imgUrl" />
                        </div>
                    </div>
                </div>
            </div>
            <div class="mt-3" style="padding-top: 20px;" v-if="secondPage">
              <button type="submit" class="btn-primary" @click="changePage" style="margin-right: 20px;">Anterior</button>
              <button type="submit" class="btn-green" @click="register">Registrarse</button>
          </div>
        </form>
        
      </div>
      <div class="row justify-content-center">
        <div class="col-md-7" >
          <h3 style="color: rgb(0, 0, 0); padding-top: 10px; padding-right: 0px; padding-left: 150px;">¿Ya tienes cuenta?</h3>
        </div>
        <div class="col-md-5" style="padding-top: 15px; padding-left: 0px; padding-right: 200px;" >
          <router-link to="/login" style="padding-top: 30px;">Inicia sesión</router-link>
        </div>
      </div>
    </div>
  </template>


<script>
import { ref, onMounted, computed } from 'vue'

export default {
    setup() {
        const name = ref('')
        const username = ref('')
        const email = ref('')
        const gender = ref('')
        const phone = ref('')
        const password = ref('')
        const confirmPassword = ref('')
        const img = ref('')
        const secondPage = ref(false)
        const success = ref(false)
        const tags = ref([])
        const selectedTags = ref([])
        const isDragging = ref(false);
        const fileInput = ref(null);
    
        
        const imgUrl = computed(() => {
          if (img.value) {
            return URL.createObjectURL(img.value);
          }
          return '';
        });


        onMounted(() => {
            fileInput.value = ref('fileInput');
            fetch(import.meta.env.VITE_BACKEND_URL + '/api/tag/types/USER_TAG')
                .then(response => {
                    if (response.status === 200) {
                        return response.json();
                    } else {
                        throw new Error('Error al cargar los tags');
                    }
                })
                .then(jsonData => {
                    tags.value = jsonData;
                })
                .catch(error => console.error(error));
        });

        const onDragover = (event) => {
          event.preventDefault();
          isDragging.value = true;
        };

        const onDragLeave = (event) => {
          event.preventDefault();
          isDragging.value = false;
        };

        const onDrop = (event) => {
          event.preventDefault();
          isDragging.value = false;
          const file = event.dataTransfer.files[0];

          if (!file.type.includes('image')){
            alert('El archivo no es una imagen');
          }else if (file.size > 1000000  ) {
            alert('La imagen debe pesar menos de 1MB');
          } 
          else {
            img.value = file;
          }
        };

        const selectFile = () => {
          fileInput.value.value = null;
          fileInput.value.click();
        };

        const deleteFile = () => {
          img.value = '';
          imgUrl.value = '';
        };

        const onFileChanged = (event) => {
          if (event.target.files[0].size > 1000000 && event.target.files[0].type.includes('image')) {
            alert('La imagen debe pesar menos de 1MB');
            event.target.value = '';
          } else {
            img.value = event.target.files[0];
          }
        };

      const toggleTag = (tag) => {
        const index = selectedTags.value.indexOf(tag);
        if (index !== -1) {
          selectedTags.value.splice(index, 1);
        } else {
          selectedTags.value.push(tag);
        }
      };

      const changePage = () => {
        if (name.value && username.value && email.value && phone.value && password.value && confirmPassword.value 
        && password.value === confirmPassword.value && phone.value.length === 9 && !isNaN(phone.value) && email.value.includes('@')) {
            secondPage.value = !secondPage.value;
        }
      };

      

        const register = () => {
            const data = {
                name: name.value,
                username: username.value,
                gender: gender.value,
                email: email.value,
                phone: phone.value,
                password: password.value,
                confirmPassword: confirmPassword.value,
                img: img.value,
                tags: selectedTags.value
            }
            if (password.value !== confirmPassword.value) {
                alert('Las contraseñas no coinciden')
            } else {
                const formData = new FormData();
                formData.append("string-data", new Blob([JSON.stringify({
                          name: data.name,
                          username: data.username,
                          genre: data.gender,
                          email: data.email,
                          phone: data.phone,
                          password: data.password,
                          tag: data.tags
                        })], { type: "application/json" }))
                formData.append("profile-pic", data.img);

                fetch(import.meta.env.VITE_BACKEND_URL + '/auth/register', {
                    method: 'POST',
                    credentials: 'include',
                    body: formData,
                })
                    .then(response => {
                        console.log(response.status);
                        if (response.status === 201) {
                            return response.json();
                        } else {
                            throw new Error('Error al registrar usuario');
                        }
                    })
                    .then(jsonData => {
                        success.value = true;
                        setTimeout(() => {
                            window.location.href = '/login'
                        }, 1000);
                      })
                    .catch(error => console.error(error));
            }
        };

        return {
            name,
            username,
            gender,
            email,
            phone,
            password,
            confirmPassword,
            register,
            onFileChanged,
            changePage,
            secondPage,
            tags,
            selectedTags,
            toggleTag,
            success,
            selectFile,
            deleteFile,
            onDragover,
            onDragLeave,
            onDrop,
            isDragging,
            imgUrl,
            fileInput
    }
  }
}
</script>

<style scoped>

.card {
    padding-top: 40px;
    padding-bottom: 40px;
    padding-left: 40px;
    padding-right: 40px;
    border: 1px  #28426b30;
    border-radius: 4px;
    background-color: #28426bae;
    box-shadow: 10px 10px 4px rgba(0, 0, 0, 0.1);
}

button {
    border: none;
    border-radius: 4px;
    cursor: pointer;
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

.img{
  width: 100%;
  padding: 10%;
  box-shadow: 0 0 3px #142e50;
  border-radius: 10px;
  overflow: hidden;
}

.cimg .top{
  text-align: center;
}

.cimg p{
  font-weight: bold;
  color:#142e50
}

.cimg button{
  outline:0;
  border:0;
  color:rgb(241, 249, 255);
  border-radius: 4px;
  font-weight: 400;
  padding: 8px 13px;
  width: 100%;
  background: #142e50;

}

.cimg .drag-area{
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

.cimg .drag-area .visible{
  font-size: 18px;
}

.cimg .select{
  color: #3f1db8;
  cursor: pointer;
  margin-left: 5px;
  cursor: pointer;
  transition: 0.4s;
}

.cimg .select:hover{
  opacity: 0.6;
}

.cimg .container{
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
.cimg .container .image{
  width:75px;
  margin-right: 5px;
  height: 75px;
  position: relative;
  margin-bottom: 8px;
}

.cimg .container .image img{
  width:100%;
  height: 100%;
  border-radius: 5px;
}

.cimg .container .image span{
  position: absolute;
  top: -2px;
  right: 9px;
  font-size: 20px;
  cursor: pointer;
}

.cimg input, 
.cimg .drag-area .on-drop,

.cimg .drag-area.dragover .visible{
  display: none;
}

.delete{
  z-index: 999;
  color:#ff0000;
  font-weight: bold;
  font-size: 30px;
}
</style>


