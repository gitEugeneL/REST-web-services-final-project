import { createRouter, createWebHistory } from "vue-router";
import Home from "@/components/Home.vue";
import Login from "@/components/Login.vue";
import Register from "@/components/Register.vue";
import User from "@/components/User.vue";

const routes = [
    { path: '/', component: Home },
    { path: '/login', component: Login },
    { path: '/register', component: Register },
    { path: '/user', component: User }

]

const router = createRouter({
    history: createWebHistory(),
    routes
})

router.beforeEach((to, from, next) => {
    const token = localStorage.getItem('token');
    if (!token && to.path !== '/login' && to.path !== '/register') {
        next('/login');
    } else {
        next();
    }
});

export default router;