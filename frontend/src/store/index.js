import { createStore } from 'vuex';

const store = createStore({
  state: {
    user: {},
    googleUser: {},
    houseAdvertisement: {},
  },
  mutations: {
    cargarUser(state, user) {
      state.user = user;
    },
    limpiarUser(state) {
      state.user = {};
    },
    cargarGoogleUser(state, googleUser) {
      state.googleUser = googleUser;
    },
    cargarHouseAdvertisement(state, houseAdvertisement) {
      state.houseAdvertisement = houseAdvertisement;
    },
    limpiarHouseAdvertisement(state) {
      state.houseAdvertisement = {};
    },
  },
  actions: {
    async cargarUser({ commit }) {
      try {
        const response = await fetch(
            import.meta.env.VITE_BACKEND_URL + "/auth/getUser",
            {
              method: "POST",
              headers: {
                "Authentication":
                  "Bearer " + localStorage.getItem("authentication"),
              },
            }
        )
        
        if (response.status == 500){
          commit('cargarUser', undefined);
          return { error: true };
        }else{
          response.json().then(data =>commit('cargarUser', data))
          return {error: false};
        }
      } catch (error) {
        console.error(error);
      }
    },
  },
});

export default store;