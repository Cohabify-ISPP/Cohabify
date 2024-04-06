import { createRouter, createWebHistory } from 'vue-router';
import Main from './components/Main.vue';
import Login from './components/authentication/Login.vue';
import Register from './components/authentication/Register.vue';
import RegisterVerification from './components/authentication/RegisterVerification.vue';
import UserAdvertisementList from './components/userAdvertisement/UserAdvertisementList.vue';
import CreateHouseAdvertisement from './components/houseAdvertisement/CreateHouseAdvertisement.vue';
import HouseAdvertisementList from './components/houseAdvertisement/HouseAdvertisementList.vue';
import UserAdvertisementDetails from './components/userAdvertisement/UserAdvertisementDetails.vue';
import EditUserAdvertisement from './components/userAdvertisement/EditUserAdvertisement.vue';
import HouseAdvertisementDetails from './components/houseAdvertisement/HouseAdvertisementDetails.vue';
import UpdateHouseAdvertisement from './components/houseAdvertisement/UpdateHouseAdvertisement.vue';
import PlanSelection from './components/PlanSelection.vue';
import userDetails from './components/user/userDetails.vue';
import updateUserDetails from './components/user/updateUserDetails.vue';
import Error from './components/Error.vue';
import MyHouseAdvertisementList from './components/houseAdvertisement/MyHouseAdvertisementList.vue';


const routes = [

    { name: "main", path: '/', component: Main },
    { path: '/login', component: Login },
    { path: '/register', component: Register },
    { path: '/register/verify/:verificationCode', component: RegisterVerification },
    
    { path: '/advertisements/users', component: UserAdvertisementList },
    { path: '/advertisements/houses', component: HouseAdvertisementList},

    { path: '/myAdvertisements/house', component: MyHouseAdvertisementList},
    { path: '/advertisements/users/:id', component: UserAdvertisementDetails },
    { path: '/advertisements/houses/:id', component: HouseAdvertisementDetails },
    
    { path: "/user/:id", component: userDetails },
    { path: '/user/edit/:id', component: updateUserDetails },

    { path: '/advertisements/houses/new', component: CreateHouseAdvertisement },

    { path: '/plan', component: PlanSelection },

    { path: '/advertisements/users/myAdvertisement', component: EditUserAdvertisement },
    { path: '/advertisements/houses/edit/:id', component: UpdateHouseAdvertisement},

    
    { path: '/advertisements/users/edit/:id', component: EditUserAdvertisement },
    { path: '/:pathMatch(.*)*', component: Error }

];
  
const router = createRouter({
    routes,
    history: createWebHistory()
});
  
export default router;