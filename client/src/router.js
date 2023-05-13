import { createRouter, createWebHistory } from "vue-router";
import Login from "@/components/Login.vue";
import Register from "@/components/Register.vue";
import User from "@/components/User.vue";
import AuctionDetail from "@/components/AuctionDetail.vue"
import CreateAuction from "@/components/CreateAuction.vue";
import EditUser from "@/components/EditUser.vue"
import EditAuction from "@/components/EditAuction.vue";
import ActiveAuctionsList from "@/components/ActiveAuctionsList.vue";
import UserAuctionsList from "@/components/UserAuctionsList.vue";
import UserParticipateAuctionList from "@/components/UserParticipateAuctionList.vue";
import UserWinnerAuctionList from "@/components/UserWinnerAuctionList.vue";
import UserPurchasedAuctions from "@/components/UserPurchasedAuctions.vue";

const routes = [
    { path: '/login', component: Login },
    { path: '/register', component: Register },
    { path: '/', component: ActiveAuctionsList },
    { path: '/my-auctions', component: UserAuctionsList },
    { path: '/im-participant', component: UserParticipateAuctionList },
    { path: '/im-winner', component: UserWinnerAuctionList },
    { path: '/my-purchased-auctions', component: UserPurchasedAuctions },
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