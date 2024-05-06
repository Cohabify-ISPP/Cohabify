<template>
    <Navbar />
    <div class="container d-flex justify-content-center align-items-center  vh-100">
        <div class="text-center">
            <!-- Modal de Bootstrap que se mostrará -->
            <div id="loginModal" class="modal">
                <!-- Modal content -->
                <div class="modal-content">
                    <div class="modal-header">
                        <span class="success-checkmark">✓</span>
                        <h2>Petición exitosa</h2>
                    </div>
                    <div class="modal-body">
                        <p>Se le ha enviado un correo para que restablezca su contraseña.</p>
                    </div>
                </div>
            </div>
            <img src="/images/LogoCohabify.png" class="img-fluid rounded-start" alt="..."
                style="max-width: 400px; padding-top: 30px; padding-bottom: 2%;">
            <h1>Restablecer contraseña</h1>
            <div class="card">
                <form class="row justify-content-center">
                    <div class="col-md-10">
                        <div class="form-group" style="padding: 20px;">
                            <label for="username" class="form-label text-white fw-bold">Nombre de usuario</label>
                            <input type="text" class="form-control" id="username" v-model="username"
                                placeholder="Nombre de usuario">
                        </div>
                        <div class="form-group" style="padding: 20px;">
                            <button type="button" class="btn-primary" @click="postResetPassword">Enviar restablecimiento de contraseña</button>
                        </div>
                        <div v-if="fetchError" class="alert alert-danger" role="alert">
                            {{ fetchError }}
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';

export default {
    setup() {
        const username = ref('');
        const fetchError = ref(null);
        const router = useRouter();
        const disableButton = ref(false);
		
		const postResetPassword = async () => {
            if(disableButton.value) {
                fetchError.value = "Ya estamos procesando su solicitud.";
                return;
            }

            fetchError.value = "";
            if(username.value == null || username.value == undefined || username.value == "") {
                fetchError.value = "Debe introducir su nombre de usuario.";
                return;
            }

            disableButton.value = true;
            fetch(import.meta.env.VITE_BACKEND_URL + '/auth/reset-password/' + username.value, {
				method: 'GET',
				credentials: 'include'
			})
			.then(response => {
				if (response.status === 200) {
					let modal = document.getElementById('loginModal');
                    modal.style.display = "block";
                    setTimeout(function () {
                        router.push('/');
                    }, 2000);
				} else {
					response.json().then(body => {
                        fetchError.value = body.message; 
                        disableButton.value = false;
                    });
                    
				}
			})
			.catch(error => {
                fetchError.value = "Ha ocurrido un error inesperado en el restablecimiento.";
                disableButton.value = false;
            });
        }

        return {
            username,
            fetchError,
            postResetPassword,
            disableButton
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

</style>
