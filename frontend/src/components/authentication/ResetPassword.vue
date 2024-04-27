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
                        <h2>Restablecimiento exitoso</h2>
                    </div>
                    <div class="modal-body">
                        <p>Se ha restablecido su contraseña satisfactoriamente.</p>
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
                            <label for="password" class="form-label text-white fw-bold">Contraseña</label>
                            <input type="password" class="form-control" id="password" v-model="password"
                                placeholder="Contraseña" :class="{ 'is-invalid': !isPasswordSafe}">
                        </div>
                        <div class="mt-3 alert alert-danger" role="alert" style="padding-top: 20px; max-width: 300px;" v-if="!isPasswordSafe">
                            <p><i class="fas fa-exclamation-triangle"></i> {{ passwordError }}</p>
                        </div>
						<div class="form-group" style="padding: 20px;">
                            <label for="confirmPassword" class="form-label text-white fw-bold">Confirmar contraseña</label>
                            <input type="password" class="form-control" id="confirmPassword" v-model="confirmPassword"
                                placeholder="Confirmar contraseña" :class="{ 'is-invalid': password !== confirmPassword }">
                        </div>
                        <div class="mt-3 alert alert-danger" role="alert" style="padding-top: 20px; max-width: 300px;" v-if="confirmPasswordError != ''">
                            <p><i class="fas fa-exclamation-triangle"></i> {{ confirmPasswordError }}</p>
                        </div>
                        <div class="form-group" style="padding: 20px;">
                            <button type="button" class="btn-primary" @click="postResetPassword">Enviar restablecimiento de contraseña</button>
                        </div>
                        <div v-if="error" class="alert alert-danger" role="alert">
                            {{ error }}
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
import { ref, watch } from 'vue';
import { useRouter, useRoute } from 'vue-router';

export default {
    setup() {
        const password = ref('');
		const confirmPassword = ref('');
        const error = ref(null);
        const router = useRouter();
		const route = useRoute();
		const verificationCode = route.params.verificationCode;
        const isPasswordSafe = ref(true);
        const passwordError = ref("");
        const confirmPasswordError = ref("");
        const passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}$/;
		
        watch(password, () => {
            console.log(passwordRegex.test(password.value))
            if(!passwordRegex.test(password.value)) {
                isPasswordSafe.value = false;
				passwordError.value = 'Contraseña no segura: la contraseña debe contener al menos 8 caracteres, una letra mayúscula, una minúscula, un número y un carácter especial  (!@#$%^&*).';
			} else {
                isPasswordSafe.value = true;
            }
        })

        watch(confirmPassword, () => {
            if(password.value != confirmPassword.value) {
				confirmPasswordError.value = "Las contraseñas no coinciden.";
			} else {
                confirmPasswordError.value = "";
            }
        })

		const postResetPassword = async () => {
			error.value = "";
			if(password.value == null || password.value == undefined || password.value == "") {
                isPasswordSafe.value = false;
				passwordError.value = "Escriba su contraseña en ambos campos.";
				return;
			}

			if(confirmPassword.value == null || confirmPassword.value == undefined || confirmPassword.value == "") {
                isPasswordSafe.value = false;
				passwordError.value = "Escriba su contraseña en ambos campos.";
				return;
			}

			if(password.value != confirmPassword.value) {
                isPasswordSafe.value = false;
				passwordError.value = "Las contraseñas no coinciden.";
				return;
			}
			
			if(!passwordRegex.test(password.value)) {
                isPasswordSafe.value = false;
				passwordError.value = 'Contraseña no segura: la contraseña debe contener al menos 8 caracteres, una letra mayúscula, una minúscula, un número y un carácter especial  (!@#$%^&*).';
				return
			}

            isPasswordSafe.value = true;

			fetch(import.meta.env.VITE_BACKEND_URL + '/auth/reset-password', {
				method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include',
                body: JSON.stringify({
                    verificationCode: verificationCode,
                    password: password.value,
                }),
			})
			.then(response => {
				if (response.status === 200) {
					let modal = document.getElementById('loginModal');
                    modal.style.display = "block";
                    setTimeout(function () {
                        router.push('/login');
                    }, 2000);
				} else {
					response.json().then(body => error.value = body.message);
				}
			})
			.catch(err => error.value = "Ha ocurrido un error en la activación.");
		}

        return {
            password,
			confirmPassword,
            error,
			postResetPassword,
            isPasswordSafe,
            passwordError,
            confirmPasswordError
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
