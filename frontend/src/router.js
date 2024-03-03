import { createRouter, createWebHistory } from 'vue-router';
import Main from './components/Main.vue';
import Login from './components/authentication/Login.vue';
import Register from './components/authentication/Register.vue';
import CreateHouse from './components/houseAdvertisement/CreateHouse.vue';
import HouseAdvertisementList from './components/houseAdvertisement/HouseAdvertisementList.vue';
import UserAdvertisementDetails from './components/userAdvertisement/UserAdvertisementDetails.vue';
import createUserAdvertisement from './components/userAdvertisement/createUserAdvertisement.vue';
import HouseAdvertisementDetails from './components/houseAdvertisement/HouseAdvertisementDetails.vue';
import Error from './components/Error.vue';


const routes = [
    { name:"main", path: '/', component: Main },
    { path: '/login', component: Login },
    { path: '/register', component: Register },
    { path: '/createHouse', component: CreateHouse },
    {path: '/userAdvertisement/1', component: UserAdvertisementDetails },
    { path: '/houses', component: HouseAdvertisementList},
    { path: '/advertisements/houses/:id', component: HouseAdvertisementDetails },
    { path: '/advertisements/houses', component: HouseAdvertisementList},
    { path: '/userAdvertisement/:id', component: UserAdvertisementDetails },
    {path: '/userAdvertisement/new', component: createUserAdvertisement },
    { path: '/404', component: Error },
    { path: '/:pathMatch(.*)*', redirect: '/' }

];
  
const router = createRouter({
    routes,
    history: createWebHistory()
});
  
export default router;