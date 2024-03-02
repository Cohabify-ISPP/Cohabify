import { createRouter, createWebHistory } from 'vue-router';
import Main from './components/Main.vue';
import Login from './components/authentication/Login.vue';
import Register from './components/authentication/Register.vue';
import HouseAdvertisementList from './components/houseAdvertisement/HouseAdvertisementList.vue';
import UserAdvertisementDetails from './components/userAdvertisement/UserAdvertisementDetails.vue';
import createUserAdvertisement from './components/userAdvertisement/createUserAdvertisement.vue';

const routes = [
    { path: '/', component: Main },
    { path: '/login', component: Login },
    { path: '/register', component: Register },
    {path: '/userAdvertisement/1', component: UserAdvertisementDetails },
    {path: '/userAdvertisement/new', component: createUserAdvertisement },
    { path: '/houses', component: HouseAdvertisementList},
    { path: '/:pathMatch(.*)*', redirect: '/' }
];
  
const router = createRouter({
    routes,
    history: createWebHistory()
});
  
export default router;