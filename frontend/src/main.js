import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router.js';
import store from './store/index.js';
import Navbar from './components/Navbar.vue';
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap"
import "bootstrap-icons/font/bootstrap-icons.css"

const app = createApp(App);
app.component("Navbar", Navbar);
app.use(router);
app.use(store);
app.mount('#app');
