import { createRouter, createWebHistory } from 'vue-router';
import Main from './components/Main.vue';
import Login from './components/authentication/Login.vue';
import UserAdvertisements from './components/userAdvertisement/UserAdvertisements.vue';

const routes = [
    { path: '/', component: Main },
    { path: '/login', component: Login },
    { path: '/userAdvertisements', component: UserAdvertisements }
];
  
const router = createRouter({
    routes,
    history: createWebHistory()
});
  
export default router;