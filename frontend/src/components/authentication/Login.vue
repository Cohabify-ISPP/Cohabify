<template>
    <div class="container d-flex justify-content-center align-items-center  vh-100">
      <div class="text-center">
        <img src="/images/LogoCohabify.png" class="img-fluid rounded-start" alt="..." style="max-width: 400px; padding-top: 30px; padding-bottom: 2%;">
        <h1>Iniciar sesión</h1>
        <div class="card">
        <form class="row justify-content-center">
          <div class="col-md-10">
            <div class="form-group" style="padding: 20px;">
              <label for="username" class="form-label text-white fw-bold">Nombre de usuario</label>
              <input type="text" class="form-control" id="username" v-model="username" placeholder="Nombre de usuario">
            </div>
            <div class="form-group" style="padding: 20px;">
              <label for="password" class="form-label text-white fw-bold">Contraseña</label>
              <input type="password" class="form-control" id="password" v-model="password" placeholder="Contraseña">
            </div>
            <div class="form-group" style="padding: 20px;">
                <button type="button" class="btn-primary" @click="login">Iniciar sesión</button>
            </div>
            </div>
        </form>
        </div>
        <div>
          <h3 style="color: rgb(0, 0, 0); padding-top: 10%;">¿No tienes cuenta?</h3>
          <router-link to="/register">Regístrate</router-link>
        </div>
      </div>
    </div>
  </template>
  


<script>
    import { ref } from 'vue'
    export default {
        setup() {
            const username = ref('')
            const password = ref('')
            const login = () => {
                const data = {
                    username: username.value,
                    password: password.value,
                }
                fetch(import.meta.env.VITE_BACKEND_URL + '/auth/login', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    credentials: 'include',
                    body: JSON.stringify({
                        username: data.username,
                        password: data.password,
                    }),
                })
                    .then(response => response.json())
                    .then(jsonData => window.location.href = '/')
                    .catch(error => console.error('Error al enviar datos al backend:', error));
            };

            return {
                username,
                password,
                login,
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

input {
    margin-bottom: 10px;
    padding: 8px;
    border: 1px solid #ccc;
    border-radius: 4px;
}

button {
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

button:hover {
    background-color: #0056b3;
}
</style>
