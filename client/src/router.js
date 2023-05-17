import { createRouter, createWebHistory } from "vue-router";
import Login from "@/components/auth/Login.vue";
import Register from "@/components/auth/Register.vue";
import User from "@/components/user/User.vue";
import AuctionDetail from "@/components/auction/AuctionDetail.vue"
import CreateAuction from "@/components/auction/CreateAuction.vue";
import EditUser from "@/components/user/EditUser.vue"
import EditAuction from "@/components/auction/EditAuction.vue";
import ActiveAuctionsList from "@/components/auction/ActiveAuctionsList.vue";
import UserAuctionsList from "@/components/user/UserAuctionsList.vue";
import UserParticipateAuctionList from "@/components/user/UserParticipateAuctionList.vue";
import UserWinnerAuctionList from "@/components/user/UserWinnerAuctionList.vue";
import UserPurchasedAuctions from "@/components/user/UserPurchasedAuctions.vue";
import UserSoldProducts from "@/components/user/UserSoldProducts.vue";

const routes = [
    { path: '/login', component: Login },
    { path: '/register', component: Register },
    { path: '/', component: ActiveAuctionsList },
    { path: '/my-auctions', component: UserAuctionsList },
    { path: '/im-participant', component: UserParticipateAuctionList },
    { path: '/im-winner', component: UserWinnerAuctionList },
    { path: '/my-purchased-auctions', component: UserPurchasedAuctions },
    { path: '/my-sold-products', component: UserSoldProducts },
    { path: '/auction-detail', component: AuctionDetail },
    { path: '/create-auction', component: CreateAuction },
    { path: '/auction-edit', component: EditAuction },
    { path: '/user', component: User },
    { path: '/user-edit', component: EditUser },
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