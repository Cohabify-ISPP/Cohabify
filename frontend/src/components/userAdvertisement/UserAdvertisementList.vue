<script>
import { ref, onMounted, computed, inject } from 'vue';
import Navbar from '../Navbar.vue'; // Import the Navbar component correctly
import { useRoute } from 'vue-router';

export default {

  setup() {

    /*
    const tags = ref([{ tag: 'Ordenado', tagtype: "USER_TAG" }, { tag: 'Limpio', tagtype: "USER_TAG" },
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
    */
    const tagsSeleccionadas = ref([])

    const userAdas = [
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
        author: {
          name: "User 1",
          gender: "Male",
          description: "User 1 description",
          imageUri: "https://via.placeholder.com/200",
          tags: [
            { tag: "Ordenado", tagtype: "USER_TAG" },
            { tag: "Limpio", tagtype: "USER_TAG" }
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
        author: {
          name: "User 2",
          gender: "Female",
          description: "User 2 description ashgdasjgdkalsjhjfgajshdkldsnh,bva",
          imageUri: "https://via.placeholder.com/200",
          tags: [
            { tag: "Amueblado", tagtype: "USER_TAG" },
            { tag: "Céntrico", tagtype: "USER_TAG" }
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
        author: {
          name: "User 3",
          gender: "Non-binary",
          description: "User 3 description",
          imageUri: "https://via.placeholder.com/200",
          tags: [
            { tag: "Exterior", tagtype: "USER_TAG" },
            { tag: "Interior", tagtype: "USER_TAG" }
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
        author: {
          name: "User 4",
          gender: "Male",
          description: "User 4 description",
          imageUri: "https://via.placeholder.com/200",
          tags: [
            { tag: "Luminoso", tagtype: "USER_TAG" },
            { tag: "Tranquilo", tagtype: "USER_TAG" },
            { tag: 'Parking', tagtype: "USER_TAG" }, { tag: 'Piscina', tagtype: "USER_TAG" },
            { tag: 'Jardín', tagtype: "USER_TAG" }, { tag: 'Calefacción', tagtype: "USER_TAG" },
            { tag: 'Aire acondicionado', tagtype: "USER_TAG" }, { tag: 'Vistas', tagtype: "USER_TAG" },
            { tag: 'Amueblado', tagtype: "USER_TAG" }, { tag: 'Céntrico', tagtype: "USER_TAG" },
            { tag: 'Exterior', tagtype: "USER_TAG" }, { tag: 'Interior', tagtype: "USER_TAG" },
            { tag: "Tranquilo", tagtype: "USER_TAG" },
            { tag: 'Parking', tagtype: "USER_TAG" }, { tag: 'Piscina', tagtype: "USER_TAG" },
            { tag: 'Jardín', tagtype: "USER_TAG" }, { tag: 'Calefacción', tagtype: "USER_TAG" },
            { tag: 'Aire acondicionado', tagtype: "USER_TAG" }, { tag: 'Vistas', tagtype: "USER_TAG" }
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
        author: {
          name: "User 5",
          gender: "Female",
          description: "User 5 description",
          imageUri: "https://via.placeholder.com/200",
          tags: [
            { tag: "Reformado", tagtype: "USER_TAG" },
            { tag: "Terraza", tagtype: "USER_TAG" }
          ]
        }
      }
    ];

    const userAds = ref([]);
    const tags = ref([]);
    const route = useRoute();

    const fetchAdvertisements = async () => {
      try {
        const response = await fetch(import.meta.env.VITE_BACKEND_URL + `/api/advertisements/users`,
          {
            method: "GET",
            credentials: "include",
          });

        if (response.ok) {
          const data = await response.json();
          userAds.value = data;
        } else {
          window.location.href = "/404";
        }

      } catch (error) {
        console.error("Error:", error);
      }
    };

    const fetchTags = async () => {
      try {
        const response = await fetch(import.meta.env.VITE_BACKEND_URL + `/api/tag/types/USER_TAG`,
          {
            method: "GET",
            credentials: "include",
          });
        const data = await response.json();
        tags.value = data;
      } catch (error) {
        console.error("Error:", error);
      }
    };

    /*
    const searchQuery = ref('');
    const isSubmitted = ref(false);

    function searchFunction() {
      isSubmitted.value = true;
    }
    */

    const toggleTag = (tag) => {
      const index = tagsSeleccionadas.value.indexOf(tag);
      if (index !== -1) {
        tagsSeleccionadas.value.splice(index, 1);
      } else {
        tagsSeleccionadas.value.push(tag);
      }
    }

    function toggleDivVisibility() {
      var div17 = document.getElementById('div-17');
      div17.classList.toggle('hidden');
    }

    const filteredUserAdsByTags = computed(() => {
      if (tagsSeleccionadas.value.length === 0) {
        return userAds.value;
      } else {
        return userAds.value.filter(ad => {
          return tagsSeleccionadas.value.every(selectedTag => {
            return ad.author.tag.some(adTag => adTag.tag === selectedTag.tag);
          });
        });
      }
    });

    /*
    const filteredUserAdsByNameAndDescription = computed(() => {
      if (!isSubmitted.value) {
        return userAds;
      }

      const query = searchQuery.value.toLowerCase().trim();

      return userAds.filter(ad => {
        const nameMatch = ad.author.name.toLowerCase().includes(query);
        const descriptionMatch = ad.author.description.toLowerCase().includes(query);
        return nameMatch || descriptionMatch;
      });
    });
    */

    onMounted(() => {
      fetchAdvertisements();
      fetchTags();
      toggleDivVisibility();
    });

    return {
      userAds: filteredUserAdsByTags,
      tags,
      tagsSeleccionadas,
      toggleDivVisibility,
      //searchFunction,
      toggleTag
    }
  },
}
</script>

<template>
  <Navbar />
  <div class="div-2">
    <div class="div-13">
      <div class="column-4">
        <div class="div-14">
          <div class="search-bar">
            <form class="d-flex w-100 justify-content-between" onsubmit="searchFunction();">
              <div id="searchForm" style="width:95%">
                <input class="searchInput" type="text" style="color:black" id="searchInput" placeholder="Busco..." />
              </div>
              <button class="searchButton d-flex align-items-center" style="padding: 0" type="submit">
                <img src="/images/search.png" alt="Buscar" />
              </button>
              <button @click.prevent="toggleDivVisibility" class="searchButton d-flex align-items-center">
                <img src="/images/filter.png" alt="Filter" />
              </button>
            </form>
          </div>

          <div class="div-17" id="div-17">
            <div class="tags-container">
              <span class="tag" v-for="tag in tags" :key="tag.tag" @click="toggleTag(tag)"
                :class="{ 'selected': tagsSeleccionadas.includes(tag), 'unselected': !tagsSeleccionadas.includes(tag) }">
                {{ tag.tag }}
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="box" style="width:80vw" v-for="anuncio in userAds" :key="anuncio">
      <div style="color: inherit; text-decoration: none; width:100%" @click="$router.push(`/advertisements/users/${anuncio.id}`)">
        <div class="inside-box" style="width:100%">
          <img class="imagen-circulo" :src="anuncio?.author?.imageUri" alt="Imagen del usuario">
          <div class="columna-informacion" style="width:100%">
            <div class="user-name" style="text-align: left;">{{ anuncio?.author?.username }}</div>
            <div class="text-truncate"
              style="display:flex; width:100%; justify-content: space-between; align-content: left; margin-right: 20px;">
              {{ anuncio?.author?.description }}
            </div>
          </div>
          <div class="tags-container" style="width:40%; display:flex; align-items: center">
            <span v-for="(tag, index) in anuncio?.author?.tag.slice(0, 8)" :key="index" class="tag"
              @click="toggleTag(tag)"
              :class="{ 'selected': tagsSeleccionadas.includes(tag), 'selected': !tagsSeleccionadas.includes(tag) }">
              {{ tag.tag }}
            </span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>



<style scoped>
.div-2 {
  display: flex;
  flex-direction: column;
  overflow: hidden;
  display: flex;
  width: 100%;
  padding-bottom: 50px;
  justify-content: center;
}

@media (max-width: 991px) {
  .div-2 {
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
  margin: -10px 28px 0;
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
    margin: 2vw 2vh;
    padding: 0;
  }
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

.user-name {
  color: #232323;
  align-self: start;
  margin-top: 15px;
  font: 700 30px Karla, sans-serif;
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
  font: 400 20px Karla, sans-serif;
}

.searchInput {
  background-color: #ffff;
  border: none;
  border-radius: 5px;
  padding: 10px;
  width: 90%;
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

.hidden {
  display: none;
}
</style>