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
    cargarUser({ commit }) {
        try {
            fetch(
                import.meta.env.VITE_BACKEND_URL + "/auth/getUser",
                {
                  method: "POST",
                  headers: {
                    "Authentication":
                      "Bearer " + localStorage.getItem("authentication"),
                  },
                }
            ).then(response => response.json().then(data =>commit('cargarUser', data)));
            
        } catch (error) {
            console.errror(error);
        }
    },
  },
});

export default store;