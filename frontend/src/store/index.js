import { createStore } from 'vuex';

const store = createStore({
  state: {
    user: {},
  },
  mutations: {
    cargarUser(state, user) {
      state.user = user;
    },
    limpiarUser(state) {
      state.user = {};
    }
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
            console.log(error);
        }
    },
  },
});

export default store;