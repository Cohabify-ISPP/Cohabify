import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router.js';
import "bootstrap/dist/css/bootstrap.min.css"
import "bootstrap"
import "bootstrap-icons/font/bootstrap-icons.css"
import Navbar from './components/Navbar.vue'

const app = createApp(App);
app.use(router);
app.component("Navbar", Navbar);
app.mount('#app');
