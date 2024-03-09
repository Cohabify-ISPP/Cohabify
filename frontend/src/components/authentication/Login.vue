<template>
    <div class="container d-flex justify-content-center align-items-center  vh-100">
      <div class="text-center">
        <!-- Modal de Bootstrap que se mostrará -->
        <div id="loginModal" class="modal">
            <!-- Modal content -->
            <div class="modal-content">
                <div class="modal-header">
                <span class="success-checkmark">✓</span>
                <h2>Inicio de sesión exitoso</h2>
                </div>
                <div class="modal-body">
                <p>¡Se ha iniciado sesión correctamente!</p>
                </div>
            </div>
        </div>
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
            <div v-if="fetchError" class="alert alert-danger" role="alert">
                    {{ fetchError }}
            </div>
            </div>
        </form>
        </div>
        <div>
          <h3 style="color: rgb(0, 0, 0); padding-top: 10%;">¿No tienes cuenta? <router-link to="/register">Regístrate</router-link></h3>
        </div>
      </div>
    </div>
  </template>

<script>
    import { inject, ref } from 'vue'
    export default {
        setup() {
            const username = ref('')
            const password = ref('')
            const fetchError = ref(null)
            const user = inject('user')
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
                .then(response =>{
                    if(response.status === 200){
                        return response.json();
                    }else if (response.status === 400) {
                        throw new Error('Usuario o contraseña incorrectos');
                    } else { 
                        throw new Error('Error al iniciar sesión');
                    }
                })
                .then(data => {
                    user.value = data.user;
                    sessionStorage.setItem("authentication", data.token)
                    // Muestra el modal de inicio de sesión
                    let modal = document.getElementById('loginModal');
                    modal.style.display = "block";
                    // Espera unas décimas de segundo antes de redirigir
                    setTimeout(function() {
                        window.location.href = '/';
                    }, 1000); // Retraso en milisegundos
                })
                .catch(error => fetchError.value = error.message);
            };

            return {
                username,
                password,
                fetchError,
                login
            }

        }
    }
</script>
<style scoped>
 /* Estilos para el modal */
 .modal {
    display: none;
    position: fixed;
    z-index: 1;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    overflow: auto;
    background-color: rgba(0,0,0,0.4);
}

.modal-content {
    background-color: #fefefe;
    position: relative; /* Posicionamiento relativo */
    top: 50vh; /* Centrado vertical */
    left: 50vw; /* Centrado horizontal */
    transform: translate(-50%, -50%); /* Ajuste para centrar */
    padding: 20px;
    border: 1px solid #888;
    width: 20%;
    border-radius: 15px;
    box-shadow: 0 4px 8px 0 rgba(0,0,0,0.2);
}



.success-checkmark {
    color: #4CAF50;
    font-size: 50px;
}

.modal-header {
    font-size: 24px;
    color: #333;
    text-align: center;
    padding-bottom: 15px;
    border-bottom: 1px solid #ddd;
}

.modal-body {
    padding: 30px 15px;
    text-align: justify;
}
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
