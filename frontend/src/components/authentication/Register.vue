<template>
    <div class="mx-auto p-2" style="width: 50%;">
      <img src="/images/LogoCohabify.png" class="img-fluid rounded-start" alt="..." style="max-width: 400px; padding-top: 20px;padding-bottom: 2%;">
      <h1 style="padding-bottom: 30px;">Registro</h1>
      <div class="card">
        <form class="row justify-content-center" @submit.prevent="register">
          <div class="col-md-6" style="padding-inline: 20px;">
            <div class="form-group" style="padding: 20px;">
              <label for="name" class="form-label text-white fw-bold">Nombre completo</label>
              <input type="text" required class="form-control" id="name" v-model="name" placeholder="Nombre completo">
            </div>
            <div class="form-group" style="padding: 20px;">
              <label for="genre" class="form-label text-white fw-bold">Género</label>
              <select required class="form-select" id="genre" v-model="genre">
                <option value="MASCULINO">Masculino</option>
                <option value="FEMENINO">Femenino</option>
                <option value="OTRO">Otro</option>
              </select>
            </div>
            <div class="form-group" style="padding: 20px;">
              <label for="phone" class="form-label text-white fw-bold">Teléfono</label>
              <input type="tel"  required pattern="[0-9]{9}" class="form-control" id="phone" v-model="phone" placeholder="XXXXXXXXX">
            </div>
            <div class="form-group" style="padding: 20px;">
              <label for="password" class="form-label text-white fw-bold">Contraseña</label>
              <input type="password" required class="form-control" id="password" v-model="password" placeholder="contraseña">
            </div>
          </div>
          <div class="col-md-6" style="padding-inline: 20px;">
            <div class="form-group" style="padding: 20px;">
              <label for="username" class="form-label text-white fw-bold">Nombre de usuario</label>
              <input type="text" required class="form-control" id="username" v-model="username" placeholder="Nombre de usuario">
            </div>
            <div class="form-group" style="padding: 20px;">
              <label for="formFile" class="form-label text-white fw-bold">Imagen de perfil</label>
              <input class="form-control" type="file" required accept="image/*" @change="onFileChanged($event)" id="formFile">
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
  
          <div style="padding-top: 20px;">
            <button type="submit" class="btn-primary">Registrarse</button>
          </div>
        </form>
      </div>
      <div>
        <h3 style="color: rgb(0, 0, 0); padding-top: 20px;">¿Ya tienes cuenta?</h3>
        <router-link to="/login">Inicia sesión</router-link>
      </div>
    </div>
  </template>


<script>
import { ref } from 'vue'

export default {
    setup() {
        const name = ref('')
        const username = ref('')
        const email = ref('')
        const genre = ref('')
        const phone = ref('')
        const password = ref('')
        const confirmPassword = ref('')
        const img = ref('')

        const onFileChanged = (event) => {
            img.value = event.target.files[0];
        }

        const register = () => {
            const data = {
                name: name.value,
                username: username.value,
                genre: genre.value,
                email: email.value,
                phone: phone.value,
                password: password.value,
                confirmPassword: confirmPassword.value,
                img: img.value
            }
            console.log(data);
            if (password.value !== confirmPassword.value) {
                alert('Las contraseñas no coinciden')
            } else {
                const formData = new FormData();
                formData.append("string-data", new Blob([JSON.stringify({
                          name: data.name,
                          username: data.username,
                          genre: data.genre,
                          email: data.email,
                          phone: data.phone,
                          password: data.password
                        })], { type: "application/json" }))
                formData.append("profile-pic", data.img)
                
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
                    .then(jsonData => {window.location.href = '/login'})
                    .catch(error => console.error(error));
            }
        };

        return {
            name,
            username,
            genre,
            email,
            phone,
            password,
            confirmPassword,
            register,
            onFileChanged
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
    background-color: #28426b9d;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

button {
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

</style>


