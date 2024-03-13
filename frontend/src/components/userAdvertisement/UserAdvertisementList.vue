<script>
import { ref, onMounted, computed } from 'vue';
import Navbar from '../Navbar.vue';


export default {

  setup() {

    const tagsSeleccionadas = ref([])
    const budget = ref(0)
    const cohabitants = ref(0)
    const errors = ref([])
    const userAds = ref([]);
    const tags = ref([]);
    const entranceDate = ref(null);
    const currentDate = new Date();
    const filtersVisibility = ref(false);
    const filteredAdvertisements = ref([]);
    const filtered = ref(false);
    const empty = ref(false);

    const fetchAdvertisements = async () => {
      try {
        const response = await fetch(import.meta.env.VITE_BACKEND_URL + `/api/advertisements/users`,
          {
            method: "GET",
            headers: {
                'Authentication': 'Bearer ' + sessionStorage.getItem("authentication"),
            },
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
			headers: {
				'Authentication': 'Bearer ' + sessionStorage.getItem("authentication"),
			},
            credentials: "include",
          });
        const data = await response.json();
        tags.value = data;
      } catch (error) {
        console.error("Error:", error);
      }
    };

    const applyFilters = () => {
      const selectedDate = new Date(entranceDate.value);
      console.log(selectedDate)
      console.log(entranceDate)
      if (budget.value < 0 || budget.value > 5000) {
        errors.value.push('budgetVal')
      }

      if (cohabitants.value < 0 || cohabitants.value > 10) {
        errors.value.push('cohabitantsVal')
      }

      if (selectedDate < currentDate) {
        errors.value.push('entranceDateVal');
      }

      filteredAdvertisements.value = userAds.value.filter(a => {

        const selectedDate = entranceDate.value ? new Date(entranceDate.value) : null;
        const advertisementDate = a.entranceDate ? new Date(a.entranceDate) : null;
        console.log(selectedDate)
        console.log(advertisementDate)
        const isDateValid = selectedDate === null || (advertisementDate && selectedDate < advertisementDate);

        return (budget.value >= a.maxBudget || budget.value == 0) &&
            (cohabitants.value <= a.maxCohabitants || cohabitants.value == 0) &&
            isDateValid;
        })
    filtered.value = true
    }

    const currentAdvertisements = computed(() => {
      return filtered.value ? filteredAdvertisements.value : userAds.value
    })

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
      var filters = document.getElementById('filterContainer');
      filters.classList.toggle('hidden');
      

    }

    const filteredUserAdsByTags = computed(() => {
      if (tagsSeleccionadas.value.length === 0) {
        return currentAdvertisements.value;
      } else {
        return currentAdvertisements.value.filter(ad => {
          return tagsSeleccionadas.value.every(selectedTag => {
            return ad.author.tag.some(adTag => adTag.tag === selectedTag.tag);
          });
        });
      }
    });

    onMounted(() => {
      fetchAdvertisements();
      fetchTags();
      toggleDivVisibility();
    });

    return {
      currentAdvertisements: filteredUserAdsByTags,
      tags,
      errors,
      budget,
      cohabitants,
      tagsSeleccionadas,
      toggleDivVisibility,
      applyFilters,
      toggleTag,
      filtersVisibility,
      entranceDate,
      empty,
      filtered
    }
  },
}
</script>

<template>
  <Navbar />
  <div style="display: flex; flex-direction: row; height:100%">
    <div id="filterContainer" class="container-fluid" style="padding-left:0; flex-basis:20vw; flex-shrink:0; transition: opacity 0.3s ease, visibility 0.3s ease;">
      <div class="col filter-column align-items-center" id="filters" style="height:auto;  overflow: hidden; ">
        <form class="needs-validation mb-4" style="width:90%" novalidate>
          <div class="d-flex justify-content-between m-1 mt-4 mr-3" style="width: 100%; height: 30px; border-bottom-right-radius: 0px;">
            <div>
              <p class="m-1">Máx. Presupuesto</p>
            </div>
            <button class="btn btn-danger btn-sm rounded-circle d-flex align-items-center justify-content-center px-1 py-1" @click.prevent="budget = 0" style="width:2vw; height: 2vw;">
              <i class="bi bi-x-lg"></i>
            </button>
          </div>
          <input type="range" class="form-range" min="0" max="5000" step="50" v-model="budget" id="budgetVal"
            :class="{ 'is-invalid': errors.includes('budgetVal') }">
          <b>{{ budget == 0 ? '-' : budget == 5000 ? '+ ' + budget.toString() + '€/mes' : '<= ' + budget.toString() + '€/mes' }}</b>

              <div v-if="!empty">
                <div class="mt-3 d-flex justify-content-between m-1" :invalid="true" style="width: 100%; height: auto; text-wrap:nowrap">
                  <p class="m-1">Máx. Inquilinos</p>
                  <button class="btn btn-danger btn-sm rounded-circle d-flex align-items-center justify-content-center px-1 py-1" @click.prevent="cohabitants = 0" style="width:2vw; height: 2vw;">
                    <i class="bi bi-x-lg"></i>
                  </button>
                </div>
                <input type="range" class="form-range" min="0" max="10" step="1" v-model="cohabitants"
                  id="cohabitantsVal" :class="{ 'is-invalid': errors.includes('cohabitantsVal') }">
                <b>{{ cohabitants == 0 ? '-' : cohabitants == 10 ? '+ ' + cohabitants.toString() : '<= ' + cohabitants.toString() }}</b>
          </div>

          <div v-if="!empty">
            <div class="mt-3 d-flex justify-content-between m-1" :invalid="true" style="width: 100%; height: 30px;">
              <p class="m-1">Fecha de entrada</p>
              <button class="btn btn-danger btn-sm rounded-circle d-flex align-items-center justify-content-center px-1 py-1" @click.prevent="cohabitants = null" style="width:2vw; height: 2vw;">
                <i class="bi bi-x-lg"></i>
              </button>
            </div>
            <input type="date" v-model="entranceDate" id="entranceDate"  class="form-control" :class="{
              ' is-invalid':
                errors.includes('entranceDateVal')
            }">
                    <b>{{ entranceDate ? entranceDate : '-' }}</b>
              </div>
              <hr>
              <div class="d-flex justify-content-center mb-2" >
                <button type="button" class="btn btn-primary" style="margin-right:4px" @click.prevent="errors = []; applyFilters()">Aplicar</button>
                <button class="btn btn-danger ml-2" style="margin-left:4px" @click.prevent="errors = [];filtered=false; budget = 0; cohabitants = 0; entranceDate = null;">Borrar</button>
              </div>
        </form>
      </div>
    </div>
    <div class="div-2" style="flex-basis:3">
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
                <span class="tag" v-for="tag in tags" :key="tag.tag" @click="toggleTag(tag)" :class="{
              ' selected': tagsSeleccionadas.includes(tag), 'unselected': !tagsSeleccionadas.includes(tag)
            }">
                  <b>{{ tag.tag }}</b>
                </span>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="box list-item" style="width:90%; align-items:center" v-for="anuncio in currentAdvertisements" :key="anuncio">
        <a style="color: inherit; text-decoration: none; width:100%" :href="'/advertisements/users/' + anuncio?.id">
          <div class="inside-box" style="width: 100%; display: flex; align-items: center;">
            <img class="imagen-circulo" :src="anuncio?.author?.imageUri" alt="Imagen del usuario"
              style="margin-right: 10px;">

            <div class="columna-informacion" style="flex: 1;">
              <div class="user-name" style="text-align: left;">{{ anuncio?.author?.username }}</div>
              <div class="text-truncate"
                style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 10px; margin-right: 4vh;">
                <span>{{ anuncio?.desiredLocation }}</span>
                <h3><b>{{ anuncio?.maxBudget }}€/mes</b></h3>
                <h3><b>Máximo {{ anuncio?.maxCohabitants }} inquilinos</b></h3>
              </div>
              <div class="tags-container" style="display: flex; align-items: center;">
                <span v-for="(tag, index) in anuncio?.author?.tag.slice(0, 8)" :key="index" class="tag selected">
                  <b>{{ tag.tag }}</b>
                </span>
              </div>
            </div>
          </div>

        </a>
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
  width: 1000px;
  max-width: 100%;
  gap: 10px;
  padding-top: 6vh;
  position: relative;
  display: flex;
  flex-grow: 1;
  flex-direction: column;
}

@media (max-width: 991px) {
  .div-14 {
    max-width: 100%;
    margin-top: 20px;
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
  white-space: initial;
  padding: 10px 50px;
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
  transition: opacity 0.2s ease, visibility 0.2s ease;
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
  background-color: #FFFFFF;
  align-self: center;
  display: flex;
  margin-top: 17px;
  max-width: 100%;
  justify-content: space-between;
  gap: 20px;
  padding: 1vw 1vh;
  box-shadow: 0 0 15px rgba(0, 0, 0, 0.4);
}

.box:hover {
  box-shadow: 0 0 30px rgba(0, 0, 0, 0.5);
  transition: box-shadow 0.2s ease;
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

.user-name {
  color: #232323;
  align-self: start;
  margin-top: 15px;
  font: 700 30px Karla, sans-serif;
}

.imagen-circulo {
  position: relative;
  width: 15vh;
  height: 15vh;
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
  opacity: 0;
  max-height: 0;
  max-width: 0;
  transition: opacity 0.2s ease, visibility 0.2s ease;
}


</style>