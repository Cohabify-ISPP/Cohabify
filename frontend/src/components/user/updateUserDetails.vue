<script>
import { ref, onBeforeMount, computed, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useStore } from 'vuex';

export default {
    setup() {
        const store = useStore();
        const userId = ref("");
        const successfulAlert = ref(false);
        const user = ref("");
        const originalUser = ref(""); // To store the original user data for cancel functionality
        const route = useRoute();
        const router = useRouter();
        const tags = ref([]);
        const selectedTags = ref([]);
        const password = ref('');
        const confirmPassword = ref('');
        const passwordError = ref('');
        const isPasswordSafe = ref('true');
        const img = ref('');
        const fileInput = ref(null);
        const isDragging = ref(false);
        let userTags = [];

        const updateMeta = (title, description) => {
            document.querySelector('meta[name="description"]').setAttribute('content', description);
            document.querySelector('meta[property="og:title"]').setAttribute('content', title);
            document.querySelector('meta[property="og:description"]').setAttribute('content', description);
            };

        const imgUrl = computed(() => {
            if (img.value) {
                return URL.createObjectURL(img.value);
            } 
            return user.value.imageUri;
        });

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
                    user.value = { ...data }; // Create a copy of the user data
                    originalUser.value = { ...data }; // Save the original user data for cancel functionality
                    userTags = user.value.tag.map((awa) => awa.id);
                    fetchTags();
                } else {
                    window.location.href = "/404";
                }

            } catch (error) {
                console.error("Error:", error);
            }
        };

        const toggleTag = (tag) => {
            const index = selectedTags.value.indexOf(tag);
            if (index !== -1) {
                selectedTags.value.splice(index, 1);
            } else {
                selectedTags.value.push(tag);
            }
        };

        const updateUserProfile = async () => {
            try {
                if(password.value.length != 0) {
                    user.value.password = password.value;
                }

                document.getElementById("form").reportValidity();

                if(password.value === confirmPassword.value && user.value.phone.length === 9 && !isNaN(user.value.phone) && user.value.email.includes('@') && !passwordError.value) {
                    const formData = new FormData();
                    formData.append("string-data", new Blob([JSON.stringify({
                        name: "TODO",
                        username: user.value.username,
                        password: user.value.password,
                        tag: selectedTags.value,
                        description: user.value.description,
                        email: user.value.email,
                        phone: user.value.phone,
                        gender: user.value.gender,
                        changedImage: img.value != ""
                    })], { type: "application/json" }));

                    if(img.value) {
                        formData.append("profile-pic", img.value);
                    }
            
                    await fetch(import.meta.env.VITE_BACKEND_URL + "/api/user/update/" + user.value.id,
                        {
                            method: "PUT",
                            headers: {
                                "Authentication": "Bearer " + localStorage.getItem("authentication"),
                            },
                            credentials: 'include',
                            body: formData
                        })
                    .then(response => {
                        if (response.status === 200) {
                            return response.json();
                        } else {
                            throw new Error('Error al actualizar usuario');
                        }
                    })
                    .then(jsonData => {
                        localStorage.setItem("authentication", jsonData.token)
                        store.dispatch('cargarUser');
                        successfulAlert.value = true;
                        setTimeout(() => {
                        router.push("/user/" + user.value.id);
                        }, 800);
                    })
                    .catch(error => console.error(error));
                }
    
            } catch (error) {
                console.error("Error:", error);
            }
        };

        const filteredTags = computed(() => {
            for (const tag of tags.value) {
                if (userTags.contains(tag.id)) {
                    selectedTags.value.push(tag);
                }
            }
        });

        const cancelChanges = () => {
            user.value = { ...originalUser.value };
            password.value = "";
            confirmPassword.value = "";
            validatePassword();
            selectedTags.value = [];
            for(const tag of tags.value) {
                if(userTags.includes(tag.id)) {
                    toggleTag(tag);
                }
            }
            deleteFile();
            router.push("/user/" + user.value.id);
        };

        const onDragover = (event) => {
        event.preventDefault();
        isDragging.value = true;
      };

      const onDragLeave = (event) => {
        event.preventDefault();
        isDragging.value = false;
      };

      const onDrop = (event) => {
        event.preventDefault();
        isDragging.value = false;
        const file = event.dataTransfer.files[0];

        if (!file.type.includes('image')){
          alert('El archivo no es una imagen');
        }else if (file.size > 1000000  ) {
          alert('La imagen debe pesar menos de 1MB');
        } 
        else {
          img.value = file;
        }
      };

      const selectFile = () => {
        fileInput.value.value = null;
        fileInput.value.click();
      };

      const deleteFile = () => {
        img.value = '';
        imgUrl.value = '';
      };

      const onFileChanged = (event) => {
        if (event.target.files[0].size > 1000000 && event.target.files[0].type.includes('image')) {
          alert('La imagen debe pesar menos de 1MB');
          event.target.value = '';
        } else if (event.target.files[0].type.includes('image')){
          img.value = event.target.files[0];
        }
      };

        const validatePassword = () => {
            const passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}$/;
            if(password.value.length != 0 && !passwordRegex.test(password.value)) {
                passwordError.value = 'Contraseña no segura: la contraseña debe contener al menos 8 caracteres, una letra mayúscula, una minúscula, un número y un carácter especial  (!@#$%^&*).';
                isPasswordSafe.value = false;
            } else {
                passwordError.value = '';
                isPasswordSafe.value = true;
            }
        };

        const fetchTags = async () => {
            try {
                const response = await fetch(import.meta.env.VITE_BACKEND_URL + `/api/tag/types/USER_TAG`,
                {
                    method: "GET",
                    headers: {
                        'Authentication': 'Bearer ' + localStorage.getItem("authentication"),
                    },
                    credentials: "include",
                });
                const data = await response.json();
                tags.value = data;
                for(const tag of tags.value) {
                    if(userTags.includes(tag.id)) {
                        toggleTag(tag);
                    }
                }
            } catch (error) {
                console.error("Error:", error);
            }
        };

        onBeforeMount(() => {
            userId.value = route.params.id;
            fetchUser();
        });

        onMounted(() => {
            updateMeta('Editar Perfil de Usuario en Cohabify.', 'Actualiza tu perfil de usuario, cambia tu imagen, ajusta tu información personal y configura tu seguridad en Cohabify.');
            fileInput.value = "";
        })

        return {
            user,
            toggleTag,
            tags,
            selectedTags,
            updateUserProfile,
            password,
            confirmPassword,
            validatePassword,
            cancelChanges,
            passwordError,
            isPasswordSafe,
            successfulAlert,
            selectFile,
            deleteFile,
            onDragover,
            onDragLeave,
            onDrop,
            onFileChanged,
            isDragging,
            img,
            imgUrl,
            fileInput
        }
    }
}
</script>

<template>

    <Navbar />

    <div class="container d-flex align-items-center justify-content-center text-center mt-5">
        <div class="panel">
            <div class="columna">
                <div class="subseccion" style="flex: 0">
                    <div class="cimg mt-3">
                        <h4 style=" text-align: center;">Imagen de perfil</h4>
                        <div class="image imagen-circulo">
                            <img :src="imgUrl"/>
                        </div>
                        <div class="d-flex justify-content-center align-items-center" style="margin-top:10px;">
                            <button @click.prevent="deleteFile" type="button" class="button boton"
                                    style="text-wrap: wrap;height: 5vh;width:5vh; border-radius:100%; margin-left:0 "><strong>&times;</strong>
                            </button>
                        </div>       
                            <div class="drag-area" @dragover.prevent="onDragover($event)" @dragleave.prevent="onDragLeave($event)" @drop.prevent="onDrop($event)">
                                <span v-if="!isDragging">
                                    Arrastra aquí tu imagen o
                                    <span class="select" role="button" @click="selectFile">
                                        Elige
                                    </span>
                                </span>
                                <div v-else class="select">Deja la imagen aquí</div>
                                <input  type="file" accept="image/*" class="file"  ref="fileInput"  @change="onFileChanged" id="formFile" required/>
                            </div>
                    </div>

                    <div class="botones" style="margin-top: 5%;">
                        <div class="d-flex justify-content-center align-items-center">
                            <button @click.prevent="updateUserProfile" type="button" class="button boton"
                                style="text-wrap: nowrap; width:100%; margin-right: 45px;"><strong>Guardar cambios <i class="bi bi-check"
                                        style="margin-left: 5px;"></i></strong></button>
                            <button @click.prevent="cancelChanges" type="button" class="button boton-cancelar"
                                style="text-wrap: nowrap; width:100%;"><strong>Cancelar cambios <i
                                        class="bi bi-x" style="margin-left: 5px;"></i></strong></button>
                        </div>
                    </div>

                    <div
                        class="alert alert-dark mt-2"
                        role="alert"
                        v-if="successfulAlert"
                    >
                        ¡Perfil actualizado correctamente!
                    </div>
                </div>

                <div class="subseccion">
                    <div class="card mb-2 shadow" style="padding: 10px;">
                        <div class="card-body">
                            <h4 style="text-align: left;" class="card-title">Descripción</h4>
                            <hr>
                            <textarea v-model="user.description" style="text-align: justify; word-wrap: break-word; height:15vh; resize: none;" 
                                maxlength="255" required class="form-control" id="description" placeholder="Descripción."></textarea>
                        </div>
                    </div>
                </div>

            </div>

            <div class="columna">
                <div class="subseccion">

                    <div style="text-align: left;">
                        <h4>Detalles</h4>
                        <hr>
                        <form id="form">
                            <!-- Bind user details for editing -->
                            <h5>Nombre de usuario <i style="font-size: large;" class="bi bi-person-fill"></i></h5>
                            <input type="text" maxlength="14" required class="form-control" id="username" v-model="user.username" placeholder="Nombre de usuario">
                
                            <h5>Teléfono <i style="font-size: large;" class="bi bi-telephone-fill"></i></h5>
                            <input type="tel" pattern="(\+34|0034|34)?[6789]\d{8}" required class="form-control" id="phone" v-model="user.phone" placeholder="XXXXXXXXX">
                
                            <h5>Correo electrónico <i style="font-size: large;" class="bi bi-envelope"></i></h5>
                            <input type="email" maxlength="255" required class="form-control" id="email" v-model="user.email" placeholder="email">
                
                                <h5>Género <i style="font-size: large;"
                                        :class="{ 'bi': true, 'bi-gender-male': user?.gender == 'MASCULINO', 'bi-gender-female': user?.gender == 'FEMENINO', 'bi-gender-ambiguous': user?.gender == 'OTRO' }"></i>
                                </h5>
                                <select required class="form-select" id="gender" v-model="user.gender">
                                    <option value="MASCULINO">Masculino</option>
                                    <option value="FEMENINO">Femenino</option>
                                    <option value="OTRO">Otro</option>
                                </select>
            
                            <!-- Display existing tags -->
                            <h4 style=" text-align: left; margin-bottom: 1%; margin-top: 5%;">Etiquetas</h4>
                            <hr>
                            <div class="btn-group" role="group" aria-label="Basic toggle button">
                                <div class="tags-container">
                                    <span class="badge tag shadow" v-for="tag in tags" :key="tag.tag" @click="toggleTag(tag)"
                                        :class="{ 'selected': selectedTags.includes(tag), 'unselected': !selectedTags.includes(tag) }">
                                        {{ tag.tag }}
                                    </span>
                                </div>
                            </div>
                        
                            <h4 style=" text-align: left; margin-bottom: 1%; margin-top: 5%;">Autenticación</h4>
                            <hr>
                            <div class="form-group" style="padding: 20px;">
                                <h5>Contraseña <i style="font-size: large;"></i></h5>
                                <input type="password" maxlength="255" class="form-control" id="password" v-model="password"
                                placeholder="Contraseña" @input="validatePassword" :class="{ 'is-invalid': !isPasswordSafe }">
                                <div class="invalid-feedback text-danger" v-if="!isPasswordSafe">{{ passwordError }}</div>
                            </div>

                            <div class="form-group" style="padding: 20px;">
                                <h5>Repetir contraseña <i style="font-size: large;"></i></h5>
                                <input type="password" class="form-control" id="confirmPassword" v-model="confirmPassword" placeholder="Repetir contraseña"
                                :class="{'is-invalid': password !== confirmPassword}">
                                <div class="invalid-feedback text-danger" v-if="password !== confirmPassword">Las contraseñas no coinciden</div>
                            </div>
                        </form>
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
    padding: 20px;
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
    background-color: #179112;
    border-radius: 10px;
    width: 27%;
    height: 5vh;
    color:white;
}

.boton-cancelar {
    display: flex;
    justify-content: center;
    align-items: center;
    margin-left: 1%;
    background-color:#b32432;
    color: #FFFFFF;
    border-radius: 10px;
    width: 27%;
    height: 5vh;
}

.boton:hover{
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
    transition: box-shadow 0.2s ease;
}

.boton-cancelar:hover{
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
    transition: box-shadow 0.2s ease;
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

.fade-enter-active,
.fade-leave-active {
    transition: opacity 0.5s;
}

.fade-enter,
.fade-leave-to {
    opacity: 0;
}

.card-user {
    border-top: 1vh solid #28426B;
}

.tags-container {
  display: flex;
  flex-wrap: wrap;
  background-color: rgba(182, 205, 239, 0);
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

.tag {
  display: inline-block;
  padding: 5px 10px;
  margin: 5px;
  border-radius: 20px;
  cursor: pointer;
  border: 1px solid #28426B;
  background-color: #FFFFFF;
  color: #28426B;
}

.selected {
  background-color: #28426B;
  color: #FFFFFF;
}

.cimg .top {
  text-align: center;
}

.cimg p {
  font-weight: bold;
  color: #142e50
}

.cimg button {
  outline: 0;
  border: 0;
  color: rgb(241, 249, 255);
  border-radius: 4px;
  font-weight: 400;
  padding: 8px 13px;
  width: 100%;
  background: #142e50;

}

.cimg .drag-area {
  height: 150px;
  border-radius: 5px;
  border: 2px dashed #142e50;
  background: #f3f8ff;
  color: #142e50;
  display: flex;
  justify-content: center;
  align-items: center;
  user-select: center;
  -webkit-user-select: none;
  margin-top: 10px;
}

.cimg .drag-area .visible {
  font-size: 18px;
}

.cimg .select {
  color: #3f1db8;
  cursor: pointer;
  margin-left: 5px;
  cursor: pointer;
  transition: 0.4s;
}

.cimg .select:hover {
  opacity: 0.6;
}

.cimg .container {
  width: 100%;
  height: auto;
  display: flex;
  justify-content: flex-start;
  align-items: flex-start;
  flex-wrap: wrap;
  max-height: 200px;
  position: relative;
  margin-bottom: 8px;
  padding-top: 15px;
}

.cimg .container .image {
  width: 75px;
  margin-right: 5px;
  height: 75px;
  position: relative;
  margin-bottom: 8px;
}

.cimg .container .image img {
  width: 100%;
  height: 100%;
  border-radius: 5px;
}

.cimg .container .image span {
  position: absolute;
  top: -2px;
  right: 9px;
  font-size: 20px;
  cursor: pointer;
}

.cimg input,
.cimg .drag-area .on-drop,

.cimg .drag-area.dragover .visible {
  display: none;
}

.delete {
  z-index: 999;
  color: #ff0000;
  font-weight: bold;
  font-size: 30px;
}
</style>
