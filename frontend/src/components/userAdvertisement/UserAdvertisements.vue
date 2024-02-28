<script>
import { ref, onMounted } from 'vue';
import Navbar from '../Navbar.vue'; // Import the Navbar component correctly

export default {
    
    
    setup() {

        //props = ['id'];
        const userAdvertisement = ref({ description: "Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga Desc de prueba muy larga ", 
                                        minBudget: 123.4, maxBudget: 567.8, desiredLocation: "Street 123", entranceDate: "2021-12-12", 
                                        exitDate: "2022-12-12", maxCohabitants: 3, likes: 19,
                                        comments: [{user: "Usuario1", comment: "Comentario1"}, {user: "Usuario2", comment: "Comentario2"}, {user: "Usuario3", comment: "Comentario3"}, {user: "Usuario4", comment: "Comentario4"}, {user: "Usuario5", comment: "Comentario5"}, {user: "Usuario6", comment: "Comentario6"}, {user: "Usuario7", comment: "Comentario7"}, {user: "Usuario8", comment: "Comentario8"}, {user: "Usuario9", comment: "Comentario9"}, {user: "Usuario10", comment: "Comentario10"}]});
        const user = ref({ name: "Nombre de prueba", gender: "Masculino", photo: "https://via.placeholder.com/200", 
                        etiquetas: ["etiqueta1", "etiqueta2", "etiqueta3", "etiqueta4", "etiqueta5", "etiqueta6", "etiqueta7", "etiqueta8", "etiqueta9", "etiqueta10, etiqueta1", "etiqueta2", "etiqueta3", "etiqueta4", "etiqueta5", "etiqueta6", "etiqueta7", "etiqueta8", "etiqueta9", "etiqueta10"]})

        const etiquetas =  ref(["etiqueta1", "etiqueta2", "etiqueta3", "etiqueta4", "etiqueta5", "etiqueta6", "etiqueta7", "etiqueta8", "etiqueta9", "etiqueta10, etiqueta1", "etiqueta2", "etiqueta3", "etiqueta4", "etiqueta5", "etiqueta6", "etiqueta7", "etiqueta8", "etiqueta9", "etiqueta10"])
        /*
        const fetchData = async () => {
            try {
                const response = await fetch(import.meta.env.VITE_API_URL + `/userAdvertisment/${id}`,
                    {
                        method: "GET",
                        credentials: "include",
                    });
                const data = await response.json();

                data.entranceDate = new Date(data.entranceDate);
                data.exitDate = new Date(data.exitDate);
                userAdvertisement.value = data;
            } catch (error) {
                console.error("Error:", error);
            }
        };
        */
        
        return {
            user,
            userAdvertisement,
        }
    }
}
</script>

<template>
    <div class="div">
      <div class="div-2">
        <Navbar />
        
        <div class="div-12">
          <div class="div-13">
            <div class="column-4">
              <div class="div-14">
                <div class="div-15">
                  <div class="div-16">Busco...</div>
                  <img
                    loading="lazy"
                    srcSet="..."
                    class="img-6"
                  />
                </div>
                <div class="div-17">
                  <img
                    loading="lazy"
                    src="https://cdn.builder.io/api/v1/image/assets/TEMP/79706e6dac8cb995ff0a4a16f2d12e9b64def822e3ca7d4d8354a0053723ae07?"
                    class="img-7"
                  />
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="div-18">
          <div class="div-19">
            <img
              loading="lazy"
              srcSet="..."
              class="img"
            /><img
              loading="lazy"
              srcSet="..."
              class="img-8"
            />
          </div>
          <div class="div-20">
            <div class="div-21">
              <div class="div-22">Luis</div>
              <div class="div-23">
                <img
                  loading="lazy"
                  src="https://cdn.builder.io/api/v1/image/assets/TEMP/3147d680df82357abda278bf1160cb1da1aa682199fdf7856fba4b9de219764f?"
                  class="img-9"
                /><img
                  loading="lazy"
                  src="https://cdn.builder.io/api/v1/image/assets/TEMP/6d04028d46e04b3e3a7c77b849032f19fb103d872bda7a59c0bee354a06b6cfa?"
                  class="img-10"
                /><img
                  loading="lazy"
                  srcSet="..."
                  class="img-11"
                />
              </div>
            </div>
            <div class="div-24">
              Me dan miedo los personajes ficticios, me gusta escuchar música.
              Tengo un perro <br />real que se llama Ectochucho y es muy amigable,
              es el único<br />que no me da miedo...
            </div>
          </div>
        </div>
        <div class="div-25">
          <img
            loading="lazy"
            srcSet="..."
            class="img-12"
          />
          <div class="div-26">
            <div class="div-27">
              <div class="div-28">Gabriela González</div>
              <div class="div-29">
                <img
                  loading="lazy"
                  src="https://cdn.builder.io/api/v1/image/assets/TEMP/3147d680df82357abda278bf1160cb1da1aa682199fdf7856fba4b9de219764f?"
                  class="img-13"
                /><img
                  loading="lazy"
                  src="https://cdn.builder.io/api/v1/image/assets/TEMP/40febe692e0bced19f3c4e79e0c22e3ceb0f0f9de4803052d0bbc7fc91381ab0?"
                  class="img-14"
                /><img
                  loading="lazy"
                  srcSet="..."
                  class="img-15"
                />
              </div>
            </div>
            <div class="div-30">
              Me gusta tocar el piano, leer, y soy desarrolladora de videojuegos
              Indie...
            </div>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  
  
  <style scoped>
  .div {
    background-color: #fff;
    display: flex;
    flex-direction: column;
    justify-content: center;
  }
  .div-2 {
    display: flex;
    flex-direction: column;
    overflow: hidden;
    position: relative;
    display: flex;
    min-height: 1080px;
    width: 100%;
    padding-bottom: 50px;
  }
  @media (max-width: 991px) {
    .div-2 {
      max-width: 100%;
    }
  }
  .img {
    position: absolute;
    inset: 0;
    height: 100%;
    width: 100%;
    object-fit: cover;
    object-position: center;
  }
  .div-3 {
    position: relative;
    background-color: #28426b;
    width: 100%;
    padding: 9px 80px 9px 20px;
  }
  @media (max-width: 991px) {
    .div-3 {
      max-width: 100%;
      padding-right: 20px;
    }
  }
  .div-4 {
    gap: 20px;
    display: flex;
  }
  @media (max-width: 991px) {
    .div-4 {
      flex-direction: column;
      align-items: stretch;
      gap: 0px;
    }
  }
  .column {
    display: flex;
    flex-direction: column;
    line-height: normal;
    width: 43%;
    margin-left: 0px;
  }
  @media (max-width: 991px) {
    .column {
      width: 100%;
    }
  }
  .div-5 {
    border-radius: 60px;
    box-shadow: 0px 2px 3px 2px rgba(0, 0, 0, 0.25);
    background-color: #fff;
    position: relative;
    display: flex;
    justify-content: space-between;
    gap: 20px;
    align-self: stretch;
    font-size: 24px;
    color: #939393;
    font-weight: 700;
    white-space: nowrap;
    width: 100%;
    margin: auto 0;
    padding: 5px 7px 5px 40px;
  }
  @media (max-width: 991px) {
    .div-5 {
      max-width: 100%;
      margin-top: 40px;
      flex-wrap: wrap;
      padding-left: 20px;
      white-space: initial;
    }
  }
  .div-6 {
    font-family: Karla, sans-serif;
    margin: auto 0;
  }
  .img-2 {
    aspect-ratio: 1.09;
    object-fit: auto;
    object-position: center;
    width: 51px;
    background-color: #28426b;
    border-radius: 50%;
  }
  .column-2 {
    display: flex;
    flex-direction: column;
    line-height: normal;
    width: 57%;
    margin-left: 20px;
  }
  @media (max-width: 991px) {
    .column-2 {
      width: 100%;
    }
  }
  .div-7 {
    position: relative;
    display: flex;
    flex-grow: 1;
    align-items: center;
    justify-content: space-between;
    gap: 14px;
    font-size: 32px;
    color: #fff;
    font-weight: 700;
  }
  @media (max-width: 991px) {
    .div-7 {
      max-width: 100%;
      margin-top: 40px;
      flex-wrap: wrap;
    }
  }
  .div-8 {
    border-radius: 30px;
    background-color: #b6cdef;
    align-self: stretch;
    flex-grow: 1;
    justify-content: center;
    color: #28426b;
    white-space: nowrap;
    margin: auto 0;
    padding: 9px 19px;
    font: 24px Karla, sans-serif;
  }
  @media (max-width: 991px) {
    .div-8 {
      white-space: initial;
      padding-right: 20px;
    }
  }
  .div-9 {
    font-family: Karla, sans-serif;
    align-self: stretch;
    margin: auto 0;
  }
  .img-3 {
    aspect-ratio: 1;
    object-fit: auto;
    object-position: center;
    width: 39px;
    align-self: stretch;
    margin: auto 0;
  }
  .div-10 {
    font-family: Karla, sans-serif;
    align-self: stretch;
    flex-grow: 1;
    flex-basis: auto;
    margin: auto 0;
  }
  .div-11 {
    font-family: Karla, sans-serif;
    align-self: stretch;
    margin: auto 0;
  }
  .img-4 {
    aspect-ratio: 1;
    object-fit: auto;
    object-position: center;
    width: 100px;
    border-radius: 50%;
    align-self: stretch;
    max-width: 100%;
  }
  .div-12 {
    position: relative;
    z-index: 10;
    margin-top: -20px;
    width: 100%;
  }
  @media (max-width: 991px) {
    .div-12 {
      max-width: 100%;
    }
  }
  .div-13 {
    gap: 20px;
    display: flex;
  }
  @media (max-width: 991px) {
    .div-13 {
      flex-direction: column;
      align-items: stretch;
      gap: 0px;
    }
  }
  .column-3 {
    display: flex;
    flex-direction: column;
    line-height: normal;
    width: 19%;
    margin-left: 0px;
  }
  @media (max-width: 991px) {
    .column-3 {
      width: 100%;
    }
  }
  .img-5 {
    aspect-ratio: 1.96;
    object-fit: auto;
    object-position: center;
    width: 344px;
    max-width: 100%;
  }
  @media (max-width: 991px) {
    .img-5 {
      margin-top: 40px;
    }
  }
  .column-4 {
    display: flex;
    flex-direction: column;
    line-height: normal;
    width: 81%;
    margin-left: 20px;
  }
  @media (max-width: 991px) {
    .column-4 {
      width: 100%;
    }
  }
  .div-14 {
    position: relative;
    display: flex;
    margin-top: 69px;
    flex-grow: 1;
    flex-direction: column;
  }
  @media (max-width: 991px) {
    .div-14 {
      max-width: 100%;
      margin-top: 40px;
    }
  }
  .div-15 {
    border-radius: 60px;
    box-shadow: 0px 2px 3px 2px rgba(0, 0, 0, 0.25);
    background-color: #fff;
    z-index: 10;
    display: flex;
    justify-content: space-between;
    gap: 20px;
    font-size: 24px;
    color: #939393;
    font-weight: 700;
    white-space: nowrap;
    padding: 17px 67px;
  }
  @media (max-width: 991px) {
    .div-15 {
      max-width: 100%;
      flex-wrap: wrap;
      white-space: initial;
      padding: 0 20px;
    }
  }
  .div-16 {
    font-family: Karla, sans-serif;
    margin: auto 0;
  }
  .img-6 {
    aspect-ratio: 1;
    object-fit: auto;
    object-position: center;
    width: 44px;
  }
  .div-17 {
    border-radius: 25px;
    background-color: #fff;
    display: flex;
    flex-direction: column;
    align-items: start;
    margin: -16px 28px 0;
    padding: 31px 60px 13px 13px;
  }
  @media (max-width: 991px) {
    .div-17 {
      max-width: 100%;
      margin-right: 10px;
      padding-right: 20px;
    }
  }
  .img-7 {
    aspect-ratio: 6.25;
    object-fit: auto;
    object-position: center;
    width: 1011px;
    max-width: 100%;
  }
  .div-18 {
    position: relative;
    border-radius: 15px;
    background-color: rgba(182, 205, 239, 1);
    align-self: center;
    display: flex;
    margin-top: 17px;
    width: 1292px;
    max-width: 100%;
    justify-content: space-between;
    gap: 20px;
    padding: 31px 43px;
  }
  @media (max-width: 991px) {
    .div-18 {
      flex-wrap: wrap;
      padding: 0 20px;
    }
  }
  .div-19 {
    display: flex;
    flex-direction: column;
    overflow: hidden;
    position: relative;
    display: flex;
    aspect-ratio: 1.02;
    width: 163px;
    justify-content: center;
    align-items: center;
  }
  .img-8 {
    aspect-ratio: 1.02;
    object-fit: auto;
    object-position: center;
    width: 100%;
    border-radius: 50%;
  }
  .div-20 {
    align-self: start;
    display: flex;
    flex-grow: 1;
    flex-basis: 0%;
    flex-direction: column;
  }
  @media (max-width: 991px) {
    .div-20 {
      max-width: 100%;
    }
  }
  .div-21 {
    display: flex;
    width: 100%;
    justify-content: space-between;
    gap: 20px;
    padding: 0 1px;
  }
  @media (max-width: 991px) {
    .div-21 {
      max-width: 100%;
      flex-wrap: wrap;
    }
  }
  .div-22 {
    color: #232323;
    align-self: start;
    margin-top: 15px;
    font: 700 40px Karla, sans-serif;
  }
  .div-23 {
    display: flex;
    justify-content: space-between;
    gap: 20px;
  }
  .img-9 {
    aspect-ratio: 1.04;
    object-fit: auto;
    object-position: center;
    width: 50px;
  }
  .img-10 {
    aspect-ratio: 1.04;
    object-fit: auto;
    object-position: center;
    width: 50px;
  }
  .img-11 {
    aspect-ratio: 1.56;
    object-fit: auto;
    object-position: center;
    width: 56px;
    margin: auto 0;
  }
  .div-24 {
    color: #404040;
    margin-top: 16px;
    font: 400 24px Karla, sans-serif;
  }
  @media (max-width: 991px) {
    .div-24 {
      max-width: 100%;
    }
  }
  .div-25 {
    position: relative;
    border-radius: 15px;
    background-color: #b6cdef;
    align-self: center;
    display: flex;
    width: 1292px;
    max-width: 100%;
    justify-content: space-between;
    gap: 20px;
    margin: 33px 0 77px;
    padding: 31px 43px;
  }
  @media (max-width: 991px) {
    .div-25 {
      flex-wrap: wrap;
      margin-bottom: 40px;
      padding: 0 20px;
    }
  }
  .img-12 {
    aspect-ratio: 1.02;
    object-fit: auto;
    object-position: center;
    width: 163px;
    border-radius: 50%;
    max-width: 100%;
  }
  .div-26 {
    align-self: start;
    display: flex;
    flex-grow: 1;
    flex-basis: 0%;
    flex-direction: column;
  }
  @media (max-width: 991px) {
    .div-26 {
      max-width: 100%;
    }
  }
  .div-27 {
    display: flex;
    width: 100%;
    justify-content: space-between;
    gap: 20px;
  }
  @media (max-width: 991px) {
    .div-27 {
      max-width: 100%;
      flex-wrap: wrap;
    }
  }
  .div-28 {
    color: #232323;
    align-self: start;
    margin-top: 11px;
    flex-grow: 1;
    flex-basis: auto;
    font: 700 40px Karla, sans-serif;
  }
  .div-29 {
    display: flex;
    justify-content: space-between;
    gap: 20px;
  }
  .img-13 {
    aspect-ratio: 1.04;
    object-fit: auto;
    object-position: center;
    width: 50px;
  }
  .img-14 {
    aspect-ratio: 1.04;
    object-fit: auto;
    object-position: center;
    width: 50px;
  }
  .img-15 {
    aspect-ratio: 1.56;
    object-fit: auto;
    object-position: center;
    width: 56px;
    margin: auto 0;
  }
  .div-30 {
    color: #404040;
    margin-top: 17px;
    font: 400 24px Karla, sans-serif;
  }
  @media (max-width: 991px) {
    .div-30 {
      max-width: 100%;
    }
  }
  </style>