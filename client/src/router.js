import { createRouter, createWebHistory } from "vue-router";
import Login from "@/components/Login.vue";
import Register from "@/components/Register.vue";
import User from "@/components/User.vue";
import AuctionList from "@/components/AuctionList.vue";
import AuctionDetail from "@/components/AuctionDetail.vue"

const routes = [
    { path: '/login', component: Login },
    { path: '/register', component: Register },
    { path: '/', component: AuctionList },
    { path: '/auction-detail', component: AuctionDetail },
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