<template>
  <Navbar />
  <div class="mx-auto p-2" style="width: 50%;">
    <img src="/images/LogoCohabify.png" class="img-fluid rounded-start" alt="..."
      style="max-width: 400px; padding-top: 20px;padding-bottom: 2%;">
    <h1 style="padding-bottom: 30px;">Registro</h1>
    <div class="card">
      <form id="form1" class="row justify-content-center" @submit.prevent="[changePage]" v-if="!secondPage">
        <div class="col-md-6" style="padding-inline: 20px;" v-if="!secondPage">
          <div class="form-group" style="padding: 20px;">
            <label for="name" class="form-label text-white fw-bold">Nombre completo <span style="color: red;"> *</span></label>
            <input name="name" type="text" maxlength="100" required class="form-control" id="name" v-model="name"
              placeholder="Nombre completo" @input="validateName">
            <div class="mt-3 alert alert-danger" role="alert" style="padding-top: 20px;" v-if="!nameError==''">
              <p><i class="fas fa-exclamation-triangle"></i> {{ nameError }}</p>  
            </div>
          </div>
          <div class="form-group text-center" style="padding: 20px;">
            <div class="text-center">
              <div class="d-flex justify-content-center">
                <label for="phone" class="form-label text-white fw-bold" style="padding-right: 5px;">Teléfono <span style="color: red;"> *</span></label>
                <div class="wrapper" style="padding-top:2px">
                  <div class="icon">
                    <span class="tooltip">Tenga en cuenta que los números de teléfono en España empiezan en <strong>6 o 7 en el caso de los teléfonos móviles</strong>, o por <strong>8 o 9 en los teléfonos fijos</strong></span>
                    <span class="bi bi-info-lg"></span>
                  </div>
                </div>
              </div>
            </div>
           
            <input name="phone" type="tel" pattern="(\+34|0034|34)?[6789]\d{8}" required class="form-control" id="phone"
              v-model="phone" placeholder="XXXXXXXXX" @input="validateTelephone" maxlength="9">
              <div class="mt-3 alert alert-danger" role="alert" style="padding-top: 20px;" v-if="!telephoneError==''">
                <p><i class="fas fa-exclamation-triangle"></i> {{ telephoneError }}</p>  
              </div>
          </div>
          <div v-if="googleOAuthToken === null || googleOAuthToken === undefined" class="form-group"
            style="padding: 20px;">
            <label for="password" class="form-label text-white fw-bold">Contraseña <span style="color: red;"> *</span></label>
            
            <div class="input-with-toggle">
              <input name="password" :type="visiblePassword ? 'text':'password'" maxlength="255" required class="form-control" id="password" v-model="password"
              placeholder="Contraseña" @input="validatePassword" :class="{ 'is-invalid': !isPasswordSafe}">
              <button @click.prevent="togglePasswordVisibility" class="toggle-password-button">
                  <span v-if="visiblePassword"><i class="bi bi-eye-slash-fill"></i></span>
                  <span v-else><i class="bi bi-eye-fill"></i></span>
              </button>
          </div>

            <div class="mt-3 alert alert-danger" role="alert" style="padding-top: 20px;" v-if="!isPasswordSafe">
              <p><i class="fas fa-exclamation-triangle"></i> {{ passwordError }}</p>
            </div>
          </div>
          <div class="form-check form-check-inline" style="padding: 20px;">
            <input class="form-check-input" type="checkbox" id="termsAndConditions" v-model="termsAccepted">
            <label class="form-check-label" for="termsAndConditions">
              <p style="color: white;">Acepto los <a style="color: darkblue;"  href="https://cohabify.github.io/ca" target="_blank">Términos y condiciones de uso</a></p>
            </label>
            
          </div>
        </div>
        <div class="col-md-6" style="padding-inline: 20px;" v-if="!secondPage">
          <div class="form-group" style="padding: 20px;">
            <label for="username" class="form-label text-white fw-bold">Nombre de usuario <span style="color: red;"> *</span></label>
            <input name="username" type="text" maxlength="50" required class="form-control" id="username" v-model="username"
              placeholder="Nombre de usuario"  @input="validateUsername" :class="{ 'is-invalid': !isUsernameValid}">
              <div class="mt-3 alert alert-danger" role="alert" style="padding-top: 20px;" v-if="!isUsernameValid">
                <p><i class="fas fa-exclamation-triangle"></i> {{ usernameError }}</p>  
              </div>
          </div>
          <div class="form-group" style="padding: 20px;">
            <label for="email" class="form-label text-white fw-bold">Email <span style="color: red;"> *</span></label>
            <input name="email" :readonly="googleOAuthToken !== null && googleOAuthToken !== undefined && googleOAuthToken !== ''"
              :class="{ 'form-control': true, 'readonly': googleOAuthToken !== null && googleOAuthToken !== undefined && googleOAuthToken !== '' }" type="email"
              maxlength="255" required id="email" v-model="email" placeholder="Email" @input="validateEmail">
              <div class="mt-3 alert alert-danger" role="alert" style="padding-top: 20px;" v-if="!emailError==''">
                <p><i class="fas fa-exclamation-triangle"></i> {{ emailError }}</p>  
              </div>
          </div>
          <div v-if="googleOAuthToken === null || googleOAuthToken === undefined" class="form-group"
            style="padding: 20px;">
            <label for="confirmPassword" class="form-label text-white fw-bold">Repetir contraseña <span style="color: red;"> *</span></label>

            <div class="input-with-toggle">
                <input name="confirmPassword" :type="visibleConfirmPassword ? 'text':'password'" required class="form-control" id="confirmPassword" v-model="confirmPassword" 
                placeholder="Repetir contraseña" :class="{ 'is-invalid': password !== confirmPassword }" maxlength="255">
                <button @click.prevent="toggleConfirmPasswordVisibility" class="toggle-password-button">
                    <span v-if="visibleConfirmPassword"><i class="bi bi-eye-slash-fill"></i></span>
                    <span v-else><i class="bi bi-eye-fill"></i></span>
                </button>
            </div>

            <div class="mt-3 alert alert-danger" role="alert" style="padding-top: 20px;" v-if="password !== confirmPassword && confirmPassword !== ''">
                <i class="fas fa-exclamation-triangle"></i> Las contraseñas no coinciden
            </div>
          </div>

        </div>
        <div style="padding-top: 20px;">
          <button type="button" class="btn-primary" @click="changePage">Siguiente</button>
        </div>
      </form>

      <form class="row justify-content-center" @submit.prevent="[register, changePage]" v-if="secondPage">
        <div class="col-md-6" style="padding-inline: 20px;">
          <div class="form-group" style="padding: 20px;">
            <label for="gender" class="form-label text-white fw-bold">Género <span style="color: red;"> *</span></label>
            <select required class="form-select" id="gender" v-model="gender">
              <option value="MASCULINO">Masculino</option>
              <option value="FEMENINO">Femenino</option>
              <option value="OTRO">Otro</option>
            </select>
            <div class="mt-3 alert alert-danger" role="alert" style="padding-top: 20px;" v-if="!genderError==''">
              <p><i class="fas fa-exclamation-triangle"></i> {{ genderError }}</p>  
            </div>
          </div>
          <label for="tags" class="form-label text-white fw-bold">¿Cómo te describirías?</label>
          <br>
          <div class="btn-group" role="group" aria-label="Basic checkbox toggle button group">
            <div class="tags-container">
              <span class="tag" v-for="tag in tags" :key="tag.tag" @click="toggleTag(tag)"
                :class="{ 'selected': selectedTags.includes(tag), 'unselected': !selectedTags.includes(tag) }">
                {{ tag.tag }}
              </span>
            </div>
          </div>
          <div class="cimg mt-3">
            <div class="top">
              <label for="formFile" class="form-label text-white fw-bold">Imagen de perfil</label>
            </div>
            <div class="drag-area" @dragover.prevent="onDragover($event)" @dragleave.prevent="onDragLeave($event)"
              @drop.prevent="onDrop($event)">
              <span v-if="!isDragging">
                Arrastra aquí tu imagen o
                <span class="select" role="button" @click="selectFile">
                  Elige
                </span>
              </span>
              <div v-else class="select">Deja la imagen aquí</div>
              <input name="formFile" type="file" accept="image/*" class="file" ref="fileInput" @change="onFileChanged" id="formFile" />
            </div>
            <div class="container">
              <div class="image" v-if="imgUrl">
                <span class="delete" @click="deleteFile">&times;</span>
                <img :src="imgUrl" />
              </div>
            </div>
          </div>
        </div>
        <div class="mt-3 alert alert-danger" role="alert" style="padding-top: 20px;" v-if="secondPage && validationErrors.length > 0" v-for="error in validationErrors" :key="error.message">
          <i class="fas fa-exclamation-triangle"></i> {{ error.message }} 
        </div>
        <div v-show="success && (googleOAuthToken === null || googleOAuthToken === undefined)" class="alert alert-success alert-dismissible fade show" role="alert">
          Registro completado con éxito. Se le ha enviado un correo para verificar su cuenta. Se le redireccionará en 5 segundos...
        </div>
        <div v-show="success && !(googleOAuthToken === null || googleOAuthToken === undefined)" class="alert alert-success alert-dismissible fade show" role="alert">
          Registro completado con éxito. Se le redireccionará en 5 segundos...
        </div>
        <div class="mt-3" style="padding-top: 20px;" v-if="secondPage">
          <button type="submit" class="btn-primary " @click="changePage" style="margin-right: 20px;">Anterior</button>
          <button type="submit" class="btn-green " :disabled="disableRegisterButton" @click="register">Registrarse</button>
        </div>
      </form>
    </div>
    <div>
      <h3 style="color: rgb(0, 0, 0); padding-top: 2%;">¿Ya tienes cuenta? <button type="button" class="text-clickable" @click="redirectToLogin">Inicia sesión</button></h3>
    </div>
  </div>
</template>

<script>

import { ref, onMounted, computed } from 'vue';
import { useStore } from 'vuex';
import { jwtDecode } from 'jwt-decode';

export default {
  setup() {
    const name = ref('');
    const username = ref('');
    const email = ref('');
    const gender = ref('');
    const phone = ref('');
    const password = ref('');
    const confirmPassword = ref('');
    const googleOAuthToken = ref('');
    const googleOAuthData = ref('');
    const img = ref('');
    const secondPage = ref(false);
    const success = ref(false);
    const tags = ref([]);
    const selectedTags = ref([]);
    const isDragging = ref(false);
    const fileInput = ref(null);
    const passwordError = ref('');
    const usernameError = ref('');
    const telephoneError = ref('');
    const nameError = ref('');
    const emailError = ref('');
    const genderError = ref('');
    const isPasswordSafe = ref('true');
    const isUsernameValid = ref('true');
    const store = useStore();
    const validationErrors = ref([])
    const termsAccepted = ref(false);
    const disableRegisterButton =ref(false);
    const visiblePassword = ref(false);
    const visibleConfirmPassword = ref(false);

    const updateMeta = (title, description) => {
            document.querySelector('meta[name="description"]').setAttribute('content', description);
            document.querySelector('meta[property="og:title"]').setAttribute('content', title);
            document.querySelector('meta[property="og:description"]').setAttribute('content', description);
            };

    const validatePassword = () => {
      const passwordRegex = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}$/;
 
      if (password.value.length == 0) {
        isPasswordSafe.value = false;
        passwordError.value = 'Introduzca una contraseña'
      } else if (!passwordRegex.test(password.value) && password.value.length > 0) {
        passwordError.value = 'Contraseña no segura: la contraseña debe contener al menos 8 caracteres, una letra mayúscula, una minúscula, un número y un carácter especial  (!@#$%^&*).';
        isPasswordSafe.value = false;
      } else {
        passwordError.value = '';
        isPasswordSafe.value = true;
      }

      if (googleOAuthToken.value !== null && googleOAuthToken.value !== undefined && googleOAuthToken.value !== "") {
        isPasswordSafe.value = true;
        passwordError.value = '';
        return '';
      }
    };
    
    const validateUsername = () => {
      const usernameRegex = /^.*\s.*$/;
      if (usernameRegex.test(username.value)) {
        usernameError.value = 'El nombre de usuario no puede contener espacios.';
        isUsernameValid.value = false;
      }else if (username.value.length == 0) {
        usernameError.value = 'Introduzca un nombre de usuario';
        isUsernameValid.value = false;
      }else {
        usernameError.value = '';
        isUsernameValid.value = true;
      }
    }

    const validateTelephone = () => {
      const pattern = /^(\+34|0034|34)?[6789]\d{8}$/;
      if(!pattern.test(phone.value)){
        telephoneError.value = 'El teléfono debe tener 9 dígitos y ser válido';
      }else{
        telephoneError.value = '';
      }
    }

    const validateGender = () => {
      if(gender.value==''){
        genderError.value = 'Introduzca un género';
      }else{
        genderError.value = '';
      }
    }
    
    const validateName = () => {
      if(name.value.length == 0){
        nameError.value = 'Introduzca un nombre';
      }else{
        nameError.value = '';}
    }

    const validateEmail = () => {
      const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
      if(email.value.length == 0){
        emailError.value = 'Introduzca un email';
      }else if(!emailRegex.test(email.value)){
        emailError.value = 'Introduzca un email válido';
      }else{
        emailError.value = '';
      }
    }

    const imgUrl = computed(() => {
      if (img.value) {
        return URL.createObjectURL(img.value);
      }
      return '';
    });

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

      if (!file.type.includes('image')) {
        alert('El archivo no es una imagen');
      } else if (file.size > 1000000) {
        alert('La imagen debe pesar menos de 1MB');
      } else {
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
      } else if (event.target.files[0].type.includes('image')) {
        img.value = event.target.files[0];
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

    const changePage = () => {
      validatePassword();
      validateUsername();
      validateTelephone();
      validateName();
      validateEmail();
      validateGender();
      if(passwordError.value !== '' || usernameError.value !== '' || telephoneError.value !== '' || nameError.value !== '' || emailError.value !== '') 
      return;
      if (!termsAccepted.value) {
        alert('Debes aceptar los términos y condiciones para registrarte.');
        return;
      }
  
      if (googleOAuthToken.value !== null && googleOAuthToken.value !== undefined && googleOAuthToken.value !== "") {
        if (name.value && username.value && email.value && phone.value && phone.value.length === 9 && !isNaN(phone.value)
          && email.value.includes('@') && !passwordError.value) {
          secondPage.value = !secondPage.value;
          validationErrors.value = [];
        }
      } else {
        if (name.value && username.value && email.value && phone.value && password.value && confirmPassword.value
          && password.value === confirmPassword.value && phone.value.length === 9 && !isNaN(phone.value) && email.value.includes('@') && !passwordError.value && !usernameError.value) {
          secondPage.value = !secondPage.value;
          validationErrors.value = [];
        }
      }

      
    };

    const togglePasswordVisibility = () => {
            event.stopPropagation();
            visiblePassword.value = !visiblePassword.value;
    };
    const toggleConfirmPasswordVisibility = () => {
            event.stopPropagation();
            visibleConfirmPassword.value = !visibleConfirmPassword.value;
    };

    const register = () => {
      disableRegisterButton.value = true;

      if ((googleOAuthToken.value === null || googleOAuthToken.value === undefined || googleOAuthToken.value === "") && password.value !== confirmPassword.value) {
        alert('Las contraseñas no coinciden');
        disableRegisterButton.value = false;
      } else {
        const formData = new FormData();  
        formData.append("string-data", new Blob([JSON.stringify({
          name: name.value,
          username: username.value,
          gender: gender.value,
          email: email.value,
          phone: phone.value,
          password: password.value,
          googleOAuthToken: googleOAuthToken.value,
          tag: selectedTags.value,
        })], { type: "application/json" }));
        formData.append("profile-pic", img.value);

        validationErrors.value = [];

        fetch(import.meta.env.VITE_BACKEND_URL + '/auth/register', {
          method: 'POST',
          credentials: 'include',
          body: formData,
        })
          .then(response => {
            if (response.status === 201) {
              success.value = true;

              setTimeout(() => {
                window.location.href = '/login';
              }, 5000);
            } else {
              response.json()
                .then((body) => {
                  if(body.length === undefined){
                    body = [body]
                }
                  validationErrors.value = body ? body : [{"message": "Ha ocurrido un error inesperado al procesar el registro"}];
                })
                .catch(error => console.error(error));
            }
          })
          .catch(error => console.error(error));
          disableRegisterButton.value = false;
      }
    };

    const redirectToLogin = () => {
      window.location.href = '/login';
    };

    onMounted(() => {
      updateMeta('Registro - Cohabify', 'Únete a Cohabify hoy para encontrar tu espacio ideal y compañeros de piso. Regístrate ahora para comenzar tu búsqueda.');
      fileInput.value = ref('fileInput');
      fetch(import.meta.env.VITE_BACKEND_URL + '/api/tag/types/USER_TAG')
        .then(response => {
          if (response.status === 200) {
            return response.json();
          } else {
            throw new Error('Error al cargar los tags');
          }
        })
        .then(jsonData => {
          tags.value = jsonData;
        })
        .catch(error => console.error(error));
        googleOAuthData.value = store.state.googleUser;
        store.commit('cargarGoogleUser', '');
        if (googleOAuthData.value !== null && JSON.stringify(googleOAuthData.value) !== '{}' && googleOAuthData.value !== undefined) {
          const decoded = jwtDecode(JSON.parse(googleOAuthData.value).credential);
          googleOAuthToken.value = decoded.sub;
          email.value = decoded.email;
          name.value = decoded.name;
      }else{
        googleOAuthToken.value = null;
      }
      });

    return {
      name,
      username,
      gender,
      email,
      phone,
      password,
      confirmPassword,
      register,
      onFileChanged,
      changePage,
      secondPage,
      tags,
      selectedTags,
      toggleTag,
      success,
      selectFile,
      deleteFile,
      onDragover,
      onDragLeave,
      onDrop,
      isDragging,
      imgUrl,
      fileInput,
      passwordError,
      usernameError,
      isPasswordSafe,
      isUsernameValid,
      validatePassword,
      googleOAuthToken,
      redirectToLogin,
      validationErrors,
      termsAccepted,
      usernameError,
      validateUsername,
      telephoneError,
      disableRegisterButton,
      validateTelephone,
      nameError,
      validateName,
      emailError,
      genderError,
      validateEmail,
      validateGender,
      visiblePassword,
      togglePasswordVisibility,
      visibleConfirmPassword,
      toggleConfirmPasswordVisibility,
    };
  }
}

</script>

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

.tags-container {
  display: flex;
  flex-wrap: wrap;
  background-color: rgba(182, 205, 239, 0);
}

.tag {
  display: inline-block;
  padding: 5px 10px;
  margin: 5px;
  border-radius: 20px;
  cursor: pointer;
  background-color: rgb(156, 182, 221);
  color: #28426B;
}

.selected {
  background-color: #28426B;
  color: #ffff;
}

.img {
  width: 100%;
  padding: 10%;
  box-shadow: 0 0 3px #142e50;
  border-radius: 10px;
  overflow: hidden;
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

.readonly {
  background-color: #e9ecef94;
  cursor: not-allowed;
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
.btn-green:disabled {
  background-color: darkgreen;
  color: rgb(175, 175, 175);
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
    right: 5%;
    transform: translateY(-50%);
    border: none;
    background: transparent;
    cursor: pointer;
}

.wrapper .icon {
  position: relative;
  background: #fff;
  color: black;
  border-radius: 50%;
  width: 20px;
  height: 20px;
  font-size: 18px;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  box-shadow: 0 10px 10px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: all 0.2s cubic-bezier(0.68, -0.55, 0.265, 1.55);
}

.wrapper .tooltip {
  position: absolute;
  top: 0;
  font-size: 16px;
  background: #fff;
  color: black;
  padding: 5px 8px;
  border-radius: 5px;
  box-shadow: 0 10px 10px rgba(0, 0, 0, 0.1);
  opacity: 0;
  pointer-events: none;
  width: 20em;
  transition: all 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);
}

.wrapper .tooltip::before {
  position: absolute;
  content: "";
  height: 10px;
  width: 10px;
  background: #fff;
  bottom: -3px;
  left: 50%;
  transform: translate(-50%) rotate(45deg);
  transition: all 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);
}


.wrapper .icon:hover .tooltip {
  top: -90px;
  width: 20em;
  opacity: 1;
  visibility: visible;
  pointer-events: auto;
  background: #1da1f2;
  color: #fff;
}

.wrapper .icon:hover .tooltip::before {
  background: #1da1f2;
}

.wrapper .icon:hover {
  background: #1da1f2;
  color: #fff
}
</style>
