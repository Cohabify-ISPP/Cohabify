<script>
import { ref, onBeforeMount } from 'vue';
import { useRoute, useRouter } from 'vue-router';

export default {
    
    setup() {

        const userId = ref("");
        const user = ref(""); 
        const route = useRoute();
        const router = useRouter();

        const fetchUser = async () => {
            try {
                const response = await fetch(import.meta.env.VITE_BACKEND_URL + "/auth/getUser", 
                {
                    method: "POST",
                    headers: {
                        "Authentication": "Bearer " + localStorage.getItem("authentication"),
                    },
                });

                if (response.ok) {
                    const data = await response.json();
                    user.value = data;
                } else {
                    window.location.href = "/404";
                }

            } catch (error) {
                console.error("Error:", error);
            }
        };

        const navigateToUser = () => {
            router.push('/user/edit/' + userId.value);
        };

        onBeforeMount(() => {
            userId.value = route.params.id;
            fetchUser();
        });
        
        return {
            user,
            navigateToUser
        }
    }
}
</script>

<template>

    <Navbar />

    <div class="container d-flex align-items-center justify-content-center text-center mt-5">            
        <div class="panel">
            
            <div class="columna">
                <div class="subseccion">
                    <h4 style=" text-align: center;">Imagen de perfil</h4>
                    <div class="imagen-circulo">
                        <img :src="user.imageUri" alt = "Imagen de perfil">
                    </div>

                    <div class= "botones" style="margin-top: 3%;">
                        <div class="d-flex justify-content-center align-items-center">
                            <button @click.prevent="navigateToUser" type="button" class="button boton" style="text-wrap: nowrap; width:100%;"><strong>Editar perfil <i class="bi bi-pencil-square" style="margin-left: 5px;"></i></strong></button>
                        </div>
                    </div>
                </div>

                <div class="subseccion">
                    <div class="card mb-2 shadow" style="padding: 10px;">   
                        <div class="card-body">
                            <h4 style="text-align: left;" class="card-title">Descripción</h4>
                            <hr>
                            <p style="text-align: justify; word-wrap: break-word" class="card-text">{{ user.description }}</p>
                        </div>
                    </div> 
                </div>

            </div>

            <div class="columna">
                <div class="subseccion">

                    <div style="text-align: left;">
                        <h4>Detalles</h4>
                        <hr>

                        <h5>Nombre de usuario <i style="font-size: large;" class="bi bi-person-fill"></i></h5>
                        <h6 style="color: #5D5E60">{{ user.username }}</h6>

                        <h5>Teléfono <i style="font-size: large;" class="bi bi-telephone-fill"></i></h5>
                        <h6 style="color: #5D5E60"> ******{{ user.phone?.slice(-3) }}</h6>

                        <h5>Correo electrónico <i style="font-size: large;" class="bi bi-envelope"></i></h5>
                        <h6 style="color: #5D5E60">{{ user.email }}</h6>

                        <h5>Género <i style="font-size: large;" :class="{'bi':true,'bi-gender-male': user?.gender == 'MASCULINO', 'bi-gender-female':user?.gender == 'FEMENINO', 'bi-gender-ambiguous': user?.gender == 'OTRO'}"></i></h5>
                        <h6 style="color: #5D5E60">{{ user.gender }}</h6>
                    </div>

                    <div v-if="user?.tag?.length === 0" style="text-align: left; margin-top: 5%;">
                        <h5 style="color: #5D5E60">Este usuario no tiene etiquetas establecidas</h5>
                    </div>

                    <div v-else style="margin-bottom: 1%; margin-top: 5%;">
                        <h4 style=" text-align: left;">Etiquetas</h4>
                        <hr>
                        <div style=" display: flex; flex-wrap:wrap; margin-right: 10%;">      
                            <div style="display: inline-flex; align-items: start; justify-content: start; align-content: start; justify-items:start;" v-for="etiqueta in user?.tag" :key="etiqueta">
                                <span class="badge etiqueta shadow" style="font-size: 105%; margin: 0.25vh 0.25vw;"> {{ etiqueta.tag }} </span>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
                
</template>

<style scoped>

.panel {
    max-width: 100%;
    display: flex;
    flex: 1;
}

.columna {
    display: flex;
    height: auto;
    flex-direction: column;
    flex: 1;
    overflow-y: auto;
    overflow-x: hidden;
    margin-bottom: 1%;
}

.subseccion {
    flex: 1;
    align-self: center;
    padding: 10px;
    width: 95%;
    height: auto;
    align-self: center;
}

.imagen-circulo {
    position: relative;
    width: 200px; 
    height: 200px;
    overflow: hidden;
    border-radius: 50%;
    display: flex;
    justify-content: center;
    align-items: center;
    margin: auto;
}

.imagen-circulo img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 50%;
    display: block; 
}

.botones {
    display: flex;
    justify-content: space-around;
    align-items: center;
}

.boton {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-left: 1%; 
    background-color:#28426B;
    border-radius: 10px;
    width: 27%;
    height: 5vh;
}

.boton strong {
    display: flex;
    align-items: center;
}

.btn-share {
    border-radius: 50%;
    width: 40px;
    height: 40px;
    display: flex;
    line-height: 40px;
    justify-content: center;
    align-items: center;
    background-color: #28426B;
    color: #FFFFFF; 
    border: none;
}

.btn-share:hover {
    background-color: #28426B;
    color: #FFFFFF; 
    border: none;
}

.fade-enter-active, .fade-leave-active {
  transition: opacity 0.5s;
}

.fade-enter, .fade-leave-to {
  opacity: 0;
}

.card-user {
    border-top: 1vh solid #28426B;
}

.etiqueta {
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 15px;
  border: 1px solid #28426B;
  background-color: #FFFFFF;
  color: #28426B;
}

</style>
