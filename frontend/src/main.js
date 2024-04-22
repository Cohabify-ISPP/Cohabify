import { createApp } from 'vue';
import './style.css';
import App from './App.vue';
import router from './router.js';
import store from './store/index.js';
import Navbar from './components/Navbar.vue';
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap";
import "bootstrap-icons/font/bootstrap-icons.css";
import VueGoogleMaps from "@fawmi/vue-google-maps";

const google_maps_key = import.meta.env.VITE_GOOGLE_MAPS_KEY;

const app = createApp(App);
app.component("Navbar", Navbar);
app.use(router);
app.use(store);
app.use(VueGoogleMaps, {
    load: {
        key: google_maps_key,
        libraries: "places",
    },
}).mount('#app');
