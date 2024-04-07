
<script setup>

	import { ref } from 'vue';
    import { useRoute, useRouter } from 'vue-router';

    const route = useRoute();
	const router = useRouter();
    const verificationCode = route.params.verificationCode;
	const verified = ref(false);
	const errorMsg = ref("");

	fetch(import.meta.env.VITE_BACKEND_URL + '/auth/register/verify/' + verificationCode, {
		method: 'GET',
        headers: {
          'Authentication': 'Bearer ' + localStorage.getItem("authentication"),
        },
		credentials: 'include'
	})
	.then(response => {
		if (response.status === 200) {
			verified.value = true;
		} else {
			errorMsg.value = "Ha ocurrido un error en la activación.";
		}
	})
	.catch(error => errorMsg.value = "Ha ocurrido un error en la activación.");

</script>

<template>

	<div class="container d-flex flex-column justify-content-center align-items-center vh-100">
		<img src="/images/LogoCohabify.png" class="img-fluid rounded-start pb-0" alt="..."
                style="max-width: 400px; padding-top: 30px; padding-bottom: 2%;">
		<h1 class="mb-5">Activación de cuenta</h1>
		<div class="card">
			<div v-if="errorMsg === ''">
				<div v-if="!verified">
					<p class="text-white fw-bold">Estamos activando su cuenta.</p>
					<p class="text-white fw-bold">Por favor, no abandone la página...</p>
				</div>
				<div v-else>
					<p class="text-white fw-bold">Su cuenta se ha activado correctamente, se le redireccionará a la página de inicio de sesión.</p>
					<button type="button" class="btn-primary" @click="router.push('/login')">Iniciar sesión</button>
				</div>
			</div>
			<div v-else>
				<p class="text-white fw-bold">{{ errorMsg }}</p>
			</div>
		</div>
	</div>
	
  
</template>

<style scoped>
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

button {
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
</style>