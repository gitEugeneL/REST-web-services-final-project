<template>
    <div class="wrapper" v-if="user">
        <div class="card shadow">
            <div class="card-body">
                <h3 class="card-title mb-4">Your account</h3>
                <div class="mb-3">
                    <h5>First name: <span class="font-italic text-secondary">{{ user.firstName }}</span></h5>
                    <h5>Last name: <span class="font-italic text-secondary">{{ user.lastName }}</span></h5>
                    <h5>Email: <span class="font-italic text-secondary">{{ user.email }}</span></h5>
                </div>
                <div class="d-flex justify-content-end mb-4">
                    <button  @click="editHandler" class="btn btn-outline-primary">Edit your account</button>
                </div>
            </div>
        </div>
        <div class="card shadow auction-block">
            <h3 class="mb-3">Auction actions</h3>
            <div class="d-flex justify-content-between">
                <router-link to="/my-auctions" class="btn btn-outline-primary">My auctions</router-link>
                <router-link to="/im-participant" class="btn btn-outline-primary">I'm participate</router-link>
                <router-link to="/im-winner" class="btn btn-outline-danger">I'm winner</router-link>
                <router-link to="/my-purchased-auctions" class="btn btn-outline-success">Purchased auctions</router-link>
                <router-link to="/my-sold-products" class="btn btn-outline-success">My sold products</router-link>
            </div>
        </div>
    </div>
</template>


<script>
import { mapGetters } from "vuex";
import router from "@/router";


export default {
    name: 'User',

    computed: {
        ...mapGetters(['user'])
    },

    methods: {
        async editHandler() {
            await router.push('/user-edit');
        }
    }
}
</script>


<style scoped>
    .wrapper {
        padding: 40px;
        width: 800px;
    }

    .auction-block {
        padding: 20px;
        margin-top: 30px;
    }

    .logout {
        margin-left: 15px;
    }

</style>