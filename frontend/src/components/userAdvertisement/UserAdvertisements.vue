<script>
import { ref, onMounted } from 'vue';
import Navbar from '../Navbar.vue'; // Import the Navbar component correctly

export default {

  setup() {

    //props = ['id'];

    const etiquetas = ref([{ tag: 'Ordenado', tagtype: "USER_TAG" }, { tag: 'Limpio', tagtype: "USER_TAG" },
    { tag: 'Amueblado', tagtype: "USER_TAG" }, { tag: 'Céntrico', tagtype: "USER_TAG" },
    { tag: 'Exterior', tagtype: "USER_TAG" }, { tag: 'Interior', tagtype: "USER_TAG" },
    { tag: 'Luminoso', tagtype: "USER_TAG" }, { tag: 'Tranquilo', tagtype: "USER_TAG" },
    { tag: 'Reformado', tagtype: "USER_TAG" }, { tag: 'Terraza', tagtype: "USER_TAG" },
    { tag: 'Ascensor', tagtype: "USER_TAG" }, { tag: 'Parking', tagtype: "USER_TAG" },
    { tag: 'Piscina', tagtype: "USER_TAG" }, { tag: 'Jardín', tagtype: "USER_TAG" },
    { tag: 'Calefacción', tagtype: "USER_TAG" }, { tag: 'Aire acondicionado', tagtype: "USER_TAG" },
    { tag: 'Vistas', tagtype: "USER_TAG" }, { tag: 'Amueblado', tagtype: "USER_TAG" },
    { tag: 'Céntrico', tagtype: "USER_TAG" }, { tag: 'Exterior', tagtype: "USER_TAG" },
    { tag: 'Interior', tagtype: "USER_TAG" }, { tag: 'Luminoso', tagtype: "USER_TAG" },
    { tag: 'Tranquilo', tagtype: "USER_TAG" }, { tag: 'Reformado', tagtype: "USER_TAG" },
    { tag: 'Terraza', tagtype: "USER_TAG" }, { tag: 'Ascensor', tagtype: "USER_TAG" },
    { tag: 'Parking', tagtype: "USER_TAG" }, { tag: 'Piscina', tagtype: "USER_TAG" },
    { tag: 'Jardín', tagtype: "USER_TAG" }, { tag: 'Calefacción', tagtype: "USER_TAG" },
    { tag: 'Aire acondicionado', tagtype: "USER_TAG" }, { tag: 'Vistas', tagtype: "USER_TAG" },
    { tag: 'Amueblado', tagtype: "USER_TAG" }, { tag: 'Céntrico', tagtype: "USER_TAG" },
    { tag: 'Exterior', tagtype: "USER_TAG" }, { tag: 'Interior', tagtype: "USER_TAG" }])

    const etiquetasSeleccionadas = ref([])

    const userAds = [
      {
        description: "Description 1",
        minBudget: 100,
        maxBudget: 200,
        desiredLocation: "Street 1",
        entranceDate: "2024-03-01",
        exitDate: "2024-03-31",
        maxCohabitants: 2,
        likes: 10,
        comments: [
          { user: "User1", comment: "Comment 1" },
          { user: "User2", comment: "Comment 2" },
          { user: "User3", comment: "Comment 3" }
        ],
        user: {
          name: "User 1",
          gender: "Male",
          description: "User 1 description",
          photo: "https://via.placeholder.com/200",
          etiquetas: [
            "tag1",
            "tag2",
            "tag3",
            "tag4",
            "tag5",
            "tag6",
            "tag7",
            "tag8",
            "tag9",
            "tag10"
          ]
        }
      },
      {
        description: "Description 2 adsftyguhijokpjiuhjmokijuh",
        minBudget: 150,
        maxBudget: 300,
        desiredLocation: "Street 2",
        entranceDate: "2024-04-01",
        exitDate: "2024-04-30",
        maxCohabitants: 3,
        likes: 15,
        comments: [
          { user: "User4", comment: "Comment 4" },
          { user: "User5", comment: "Comment 5" }
        ],
        user: {
          name: "User 2",
          gender: "Female",
          description: "User 2 description ashgdasjgdkalsjhjfgajshdkldsnh,bva",
          photo: "https://via.placeholder.com/200",
          etiquetas: [
            "tag11",
            "tag12",
            "tag13",
            "tag14",
            "tag15",
            "tag16",
            "tag17",
            "tag18",
            "tag19",
            "tag20"
          ]
        }
      },
      {
        description: "Description 3",
        minBudget: 200,
        maxBudget: 400,
        desiredLocation: "Street 3",
        entranceDate: "2024-05-01",
        exitDate: "2024-05-31",
        maxCohabitants: 4,
        likes: 20,
        comments: [
          { user: "User6", comment: "Comment 6" },
          { user: "User7", comment: "Comment 7" },
          { user: "User8", comment: "Comment 8" }
        ],
        user: {
          name: "User 3",
          gender: "Non-binary",
          description: "User 3 description",
          photo: "https://via.placeholder.com/200",
          etiquetas: [
            "tag21",
            "tag22",
            "tag23",
            "tag24",
            "tag25",
            "tag26",
            "tag27",
            "tag28",
            "tag29",
            "tag30"
          ]
        }
      },
      {
        description: "Description 4",
        minBudget: 250,
        maxBudget: 500,
        desiredLocation: "Street 4",
        entranceDate: "2024-06-01",
        exitDate: "2024-06-30",
        maxCohabitants: 5,
        likes: 25,
        comments: [
          { user: "User9", comment: "Comment 9" }
        ],
        user: {
          name: "User 4",
          gender: "Male",
          description: "User 4 description",
          photo: "https://via.placeholder.com/200",
          etiquetas: [
            "tag31",
            "tag32",
            "tag33",
            "tag34",
            "tag35",
            "tag36",
            "tag37",
            "tag38",
            "tag39",
            "tag40"
          ]
        }
      },
      {
        description: "Description 5",
        minBudget: 300,
        maxBudget: 600,
        desiredLocation: "Street 5",
        entranceDate: "2024-07-01",
        exitDate: "2024-07-31",
        maxCohabitants: 6,
        likes: 30,
        comments: [
          { user: "User10", comment: "Comment 10" },
          { user: "User11", comment: "Comment 11" }
        ],
        user: {
          name: "User 5",
          gender: "Female",
          description: "User 5 description",
          photo: "https://via.placeholder.com/200",
          etiquetas: [
            "tag41",
            "tag42",
            "tag43",
            "tag44",
            "tag45",
            "tag46",
            "tag47",
            "tag48",
            "tag49",
            "tag50"
          ]
        }
      }
    ];

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

    /*
    function searchFunction() {
        // Obtener el valor de búsqueda del campo de entrada
        var searchQuery = document.getElementById("searchInput").value;

        // Realizar alguna acción con el valor de búsqueda, como enviar una solicitud al servidor o filtrar los resultados en el lado del cliente

        // Aquí puedes agregar la lógica para manejar la búsqueda, como enviar la consulta a tu servidor, etc.
        console.log("Búsqueda realizada:", searchQuery);
        }
    */

    const toggleEtiqueta = (etiqueta) => {
      const index = etiquetasSeleccionadas.value.indexOf(etiqueta);
      if (index !== -1) {
        etiquetasSeleccionadas.value.splice(index, 1); // Si la etiqueta ya está seleccionada, la elimina
      } else {
        etiquetasSeleccionadas.value.push(etiqueta); // Si la etiqueta no está seleccionada, la agrega
      }
    }

    return {
      userAds,
      etiquetas,
      etiquetasSeleccionadas,
      toggleEtiqueta
    }
  },
}
</script>

<template>
    <div class="div-2">
      <Navbar />
      <div class="div-12">
        <div class="div-13">
          <div class="column-4">
            <div class="div-14">
              <div class="search-bar">
                <form class="d-flex w-100 justify-content-between" onsubmit="searchFunction(); return false;">
                  <div id="searchForm" style="width:95%">
                  <input class="searchInput" type="text" style="color:black" id="searchInput" placeholder="Busco..." />
                  </div>
                  <button class="searchButton d-flex align-items-center" style="padding: 0" type="submit"><img src="/images/search.png" alt="Buscar" /></button>
                </form>
              </div>
              <div class="div-17">
                <div class="etiquetas-container">
                  <span class="etiqueta" v-for="etiqueta in etiquetas" :key="etiqueta.tag"
                    @click="toggleEtiqueta(etiqueta)"
                    :class="{ 'selected': etiquetasSeleccionadas.includes(etiqueta), 'unselected': !etiquetasSeleccionadas.includes(etiqueta) }">
                    {{ etiqueta.tag }}
                  </span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="box" style="width:80vw" v-for="anuncio in userAds" :key="anuncio">
        <a style="color: inherit; text-decoration: none;" :href="'/userAdvertisement/'+ anuncio.id">
          <div class="inside-box">
            <img class="imagen-circulo" :src="anuncio.user.photo" alt="Imagen del usuario">

            <div class="columna-informacion">
              <div class="user-name" style="text-align: left;">{{ anuncio.user.name }}</div>

              <div style="display:flex; justify-content: space-between; align-content: left; margin-right: 20px;">
                <span>{{ anuncio.user.description }}</span>
                <span style="">{{ anuncio.floor }}</span>
              </div>
            </div>
          </div>
        </a>
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
  justify-content: center;
}

@media (max-width: 991px) {
  .div-2 {
    max-width: 100%;
  }
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
  position: relative;
  z-index: 10;
  margin-top: -20px;
  width: 100%;
  max-width: 100%;
  gap: 20px;
  display: flex;
  flex-direction: row;
  justify-content: center;
}

@media (max-width: 991px) {
  .div-13 {
    flex-direction: column;
    align-items: stretch;
    gap: 0px;
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
  align-self: center;
  width: 1292px;
  max-width: 100%;
  gap: 20px;
  padding: 31px 43px;
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

.search-bar {
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
  align-items: center;
  flex-wrap: wrap;
  white-space: nowrap;
  padding: 17px 67px;
}

@media (max-width: 991px) {
  .search-bar {
    max-width: 100%;
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex-wrap: wrap;
    white-space: initial;
    padding: 0 20px;
  }
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
.box {
  position: relative;
  border-radius: 15px;
  background-color: rgba(182, 205, 239, 1);
  align-self: center;
  display: flex;
  margin-top: 17px;
  max-width: 100%;
  justify-content: space-between;
  gap: 20px;
  padding: 1vw 1vh;
}
.inside-box {
  position: relative;
  border-radius: 15px;
  align-self: center;
  display: flex;
  margin-left: 1vw;
  max-width: 100%;
  justify-content: space-between;
  gap: 20px;
}

@media (max-width: 991px) {
  .box {
    flex-wrap: wrap;
    margin: 2vw 2vh ;
    padding: 0;
  }
}

.etiquetas-container {
  display: flex;
  flex-wrap: wrap;
}

.etiqueta {
  display: inline-block;
  padding: 5px 10px;
  margin: 5px;
  border-radius: 20px;
  cursor: pointer;
  background-color: rgba(182, 205, 239, 1);
  color: #28426B;
}

.selected {
  background-color: #28426B;
  color: #ffff;
}

.user-name {
  color: #232323;
  align-self: start;
  margin-top: 15px;
  font: 700 40px Karla, sans-serif;
}

.imagen-circulo {
  position: relative;
  width: 20vh;
  height: 20vh;
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

.columna-informacion {
  flex: 2;
  padding: 1%;
  max-height: 20vh;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  margin-top: 30px;
  font: 400 24px Karla, sans-serif;
}

.searchInput {
  background-color: #ffff;
  border: none;
  border-radius: 5px;
  padding: 10px;
  width: 100%;
  margin-right: 10px;
}

.searchButton {
  background-color: transparent;
  border: none;
  align-self: center;
  cursor: pointer;
  width: 30px;
  height: 30px;
}

.searchButton img {
  width: 24px;
  align-self: right;
}
</style>