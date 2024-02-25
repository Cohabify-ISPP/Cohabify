<template>


    <div class="login-container">

        <img src="/images/LogoCohabify.png" class="img-fluid rounded-start" alt="..." style="max-width: 400px; padding-bottom: 2%;">
        <h1>Iniciar sesión</h1>

        <div class="card mt-5">
            <h3 style="color: white; padding-bottom: 10px;">Usuario</h3>
            <input style="width: 300px; align-self: center;" type="text" v-model="username" placeholder="Usuario" />
            <h3 style="color: white; padding-top: 10px; padding-bottom: 10px;">Contraseña</h3>
            <input style="width: 300px; align-self: center;" type="password" v-model="password" placeholder="Contraseña" />
            <div style="padding-top: 30px; padding-bottom: 10px;">
                <button class="btn-primary" @click="login">Iniciar sesión</button>
            </div>
        </div>

        <div>
            <h3 style="color: rgb(0, 0, 0); padding-top: 10%;">¿No tienes cuenta?</h3>
            <router-link to="/register">Regístrate</router-link>
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
                fetch('/auth/login', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        username: data.username,
                        password: data.password,
                    }),
                })
                    .then(response => response.json())
                    .then(jsonData => console.log('Respuesta del backend:', jsonData))
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
