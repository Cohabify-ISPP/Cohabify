<template>
    <Navbar />
    <div class="container d-flex justify-content-center align-items-center  vh-100">
        <div class="text-center">
            <div id="loginModal" class="modal">
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
            <img src="/images/LogoCohabify.png" class="img-fluid rounded-start" alt="..."
                style="max-width: 400px; padding-top: 30px; padding-bottom: 2%;">
            <h1>Iniciar sesión</h1>
            <div class="card">
                <form class="row justify-content-center">
                    <div class="col-md-10">
                        <div class="form-group" style="padding: 20px;">
                            <label for="username" class="form-label text-white fw-bold">Nombre de usuario</label>
                            <input type="text" class="form-control" id="username" v-model="username"
                                placeholder="Nombre de usuario">
                        </div>
                        <div class="form-group" style="padding: 20px;">
                            <label for="password" class="form-label text-white fw-bold">Contraseña</label>

                            <div class="input-with-toggle">
                                <input :type="visiblePassword ? 'text':'password'" class="form-control" id="password" v-model="password" placeholder="Contraseña">
                                <button @click.prevent="togglePasswordVisibility" class="toggle-password-button">
                                    <span v-if="visiblePassword"><i class="bi bi-eye-slash-fill"></i></span>
                                    <span v-else><i class="bi bi-eye-fill"></i></span>
                                </button>
                            </div>
                            
                        </div>
                        <div class="form-group" style="padding: 20px;">
                            <button type="button" class="btn-primary" @click.prevent="login">Iniciar sesión</button>
                        </div>
                        <div v-if="fetchError" class="alert alert-danger" role="alert">
                            {{ fetchError }}
                        </div>
                        <div id="g_id_onload"
                            :data-client_id="clientId"
                            data-callback="handleGoogleOauth">
                        </div>
                        <div class="g_id_signin d-flex justify-content-center" data-type="standard"></div>
                        <button v-if="test" id="bypass" type="button" @click.prevent="bypassOauth">Bypass</button>
                    </div>
                </form>
            </div>
            <div>
                <h3 style="color: rgb(0, 0, 0); padding-top: 2%;">¿No tienes cuenta? <button type="button" class="text-clickable" @click="moveToRegister">Regístrate</button></h3>
                <h3 style="color: rgb(0, 0, 0); padding-top: 2%;">¿Has olvidado tu contraseña?</h3>
                <h3 style="color: rgb(0, 0, 0); padding-top: 2%;"><button type="button" class="text-clickable" @click.prevent="router.push('/reset-password')">Restablece tu contraseña</button></h3>
            </div>
        </div>
    </div>
</template>

<script>
import { inject, ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';

export default {
    setup() {
        const username = ref('');
        const password = ref('');
        const fetchError = ref(null);
        const store = useStore();
        const clientId = import.meta.env.VITE_GOOGLE_CLIENT_ID;
        const router = useRouter();
        const test = ref(false);
        const visiblePassword = ref(false);

        const updateMeta = (title, description) => {
            document.querySelector('meta[name="description"]').setAttribute('content', description);
            document.querySelector('meta[property="og:title"]').setAttribute('content', title);
            document.querySelector('meta[property="og:description"]').setAttribute('content', description);
            };
        
        const login = async () => {
            const data = {
                username: username.value,
                password: password.value,
            };
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
                .then(response => {
                    if (response.status === 200) {
                        return response.json();
                    } else if (response.status === 400) {
                        throw new Error('Usuario o contraseña incorrectos');
                    } else {
                        throw new Error('Error al iniciar sesión');
                    }
                })
                .then(data => {
                    localStorage.setItem("authentication", data.token);
                    store.dispatch('cargarUser');
                    let modal = document.getElementById('loginModal');
                    modal.style.display = "block";
                    setTimeout(function () {
                        window.location.href = '/';
                    }, 1000);
                })
                .catch(error => fetchError.value = error.message);
        };
        const moveToRegister = async () => {
            await store.commit('cargarGoogleUser', "");
            window.location.href='/register';
        };
        
        const togglePasswordVisibility = () => {
            event.stopPropagation();
            visiblePassword.value = !visiblePassword.value;
        };

        const handleGoogleOauth = async (googleData) => {
            await fetch (import.meta.env.VITE_BACKEND_URL + '/auth/login/google', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include',
                body: JSON.stringify({
                    token: googleData
                })
            })
            .then(response => {
                if (response.status === 200) {
                    return response.json();
                } else if (response.status === 404) {
                    store.commit("cargarGoogleUser", JSON.stringify(googleData));
                    router.push('/register');
                    throw new Error('No hay ninguna cuenta con este usuario de Google');
                } else {
                    throw new Error('Error al iniciar sesión con Google');
                }
            })
            .then(data => {
                localStorage.setItem("authentication", data.token);
                store.dispatch('cargarUser');
                let modal = document.getElementById('loginModal');
                modal.style.display = "block";
                setTimeout(function () {
                    window.location.href = '/';
                }, 1000);
            })
        };

        const bypassOauth = async () => {
            const googleData = import.meta.env.VITE_GOOGLE_TEST_TOKEN;
            handleGoogleOauth(JSON.parse(googleData));
        };

        onMounted(() => {
            const script = document.createElement('script');
            script.src = 'https://accounts.google.com/gsi/client';
            script.async = true;
            script.defer = true;
            updateMeta('Iniciar Sesión en Cohabify', 'Accede a tu cuenta para explorar viviendas y encontrar compañeros de piso ideales. Utiliza nuestro servicio para mejorar tu experiencia.');
            document.body.appendChild(script);
            if (process.env.NODE_ENV === "test") {
                test.value = true;
            }
        });

        window.handleGoogleOauth = handleGoogleOauth;

        return {
            username,
            password,
            fetchError,
            clientId,
            test,
            handleGoogleOauth,
            bypassOauth,
            login,
            moveToRegister,
            visiblePassword,
            togglePasswordVisibility,
            router
        };

    }
};
</script>

<style scoped>
.modal {
    display: none;
    position: fixed;
    z-index: 1;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    overflow: auto;
    background-color: rgba(0, 0, 0, 0.4);
}

.modal-content {
    background-color: #fefefe;
    position: relative;
    top: 50vh;
    left: 50vw;
    transform: translate(-50%, -50%);
    padding: 20px;
    border: 1px solid #888;
    width: 20%;
    border-radius: 15px;
    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
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
    border: 1px #28426b30;
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

.text-clickable {
    padding: 0;
    color: #0056b3;
    cursor: pointer;
    background-color: transparent;
}
.text-clickable:hover {
    text-decoration: underline;
    background-color: transparent;
}

.input-with-toggle {
    position: relative;
}

.input-with-toggle input {
    padding-right: 40px;
}

.input-with-toggle .toggle-password-button {
    color: black;
    position: absolute;
    top: 50%;
    right: 0.5%;
    transform: translateY(-50%);
    border: none;
    background: transparent;
    cursor: pointer;
}

</style>
