<template>
    <nav class="navbar navbar-expand-md navbar-light fixed-top">
        <div class="container">
            <router-link v-if="user" to="/" class="navbar-brand">Auctions</router-link>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul v-if="user" class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <router-link to="/my-auctions" class="nav-link">My auctions</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link to="/im-participant" class="nav-link">I'm participate</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link to="/im-winner" class="nav-link">I'm winner</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link to="/my-purchased-auctions" class="nav-link">Purchased auctions</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link to="/my-sold-products" class="nav-link">My sold products</router-link>
                    </li>
                </ul>
                <ul v-if="!user" class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <router-link to="/login" class="nav-link">Login</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link to="/register" class="nav-link">Sign up</router-link>
                    </li>
                </ul>
                <ul v-if="user" class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <router-link to="/create-auction" class="nav-link text-success">New auction</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link to="/user" class="nav-link text-primary">{{ user.firstName }}</router-link>
                    </li>
                    <Logout />
                </ul>

            </div>
        </div>
    </nav>
</template>

<script>
    import Logout from "@/components/auth/Logout.vue";
    import { mapGetters } from "vuex";

    export default {
        name: 'NavBar',

        components: { Logout },

        computed: {
            ...mapGetters(['user'])
        }
    }
</script>


<style scoped>
    .navbar-light {
        background-color: #fff;
        box-shadow: 0 14px 80px rgba(34, 35, 58, .2);
        margin-bottom: 20px;
    }
</style>

