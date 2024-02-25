import { createRouter, createWebHistory } from 'vue-router';
import Main from './components/Main.vue';
import Login from './components/authentication/Login.vue';

const routes = [
    { path: '/', component: Main },
    { path: '/login', component: Login }
];
  
const router = createRouter({
    routes,
    history: createWebHistory()
});
  
export default router;