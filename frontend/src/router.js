import { createRouter, createWebHistory } from 'vue-router';
import Main from './components/Main.vue';
import Login from './components/authentication/Login.vue';
import Register from './components/authentication/Register.vue';
import HouseAdvertisementList from './components/houseAdvertisement/HouseAdvertisementList.vue';

const routes = [
    { path: '/', component: Main },
    { path: '/login', component: Login },
    { path: '/register', component: Register },
    { path: '/houses', component: HouseAdvertisementList},
    { path: '/:pathMatch(.*)*', redirect: '/' }
];
  
const router = createRouter({
    routes,
    history: createWebHistory()
});
  
export default router;