<template>
    <div class="mx-auto p-2" style="width: 50%;">
      <img src="/images/LogoCohabify.png" class="img-fluid rounded-start" alt="..." style="max-width: 400px; padding-top: 20px;padding-bottom: 2%;">
      <h1 style="padding-bottom: 30px;">Registro</h1>
      <div class="card">
        <form class="row justify-content-center">
          <div class="col-md-6" style="padding-inline: 20px;">
            <div class="form-group" style="padding: 20px;">
              <label for="name" class="form-label text-white fw-bold">Nombre completo</label>
              <input type="text" class="form-control" id="name" v-model="name" placeholder="Nombre completo">
            </div>
            <div class="form-group" style="padding: 20px;">
              <label for="username" class="form-label text-white fw-bold">Nombre de usuario</label>
              <input type="text" class="form-control" id="username" v-model="username" placeholder="Nombre de usuario">
            </div>
            <div class="form-group" style="padding: 20px;">
              <label for="phone" class="form-label text-white fw-bold">Teléfono</label>
              <input type="text" class="form-control" id="phone" v-model="phone" placeholder="XXXXXXXXX">
            </div>
          </div>
          <div class="col-md-6" style="padding-inline: 20px;">
            <div class="form-group" style="padding: 20px;">
              <label for="email" class="form-label text-white fw-bold">Email</label>
              <input type="text" class="form-control" id="email" v-model="email" placeholder="email">
            </div>
            <div class="form-group" style="padding: 20px;">
              <label for="password" class="form-label text-white fw-bold">Contraseña</label>
              <input type="password" class="form-control" id="password" v-model="password" placeholder="contraseña">
            </div>
            <div class="form-group" style="padding: 20px;">
              <label for="confirmPassword" class="form-label text-white fw-bold">Repetir contraseña</label>
              <input type="password" class="form-control" id="confirmPassword" v-model="confirmPassword" placeholder="repetir contraseña">
            </div>
          </div>
  
          <div style="padding-top: 20px;">
            <button class="btn-primary" @click="register">Registrarse</button>
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
        const username = ref('')
        const email = ref('')
        const password = ref('')

        const register = () => {
            // TODO: Lógica de registro
        }

        return {
            username,
            email,
            password,
            register
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


