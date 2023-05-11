<template>
    <nav class="navbar navbar-expand navbar-light fixed-top">
        <div class="container">
            <router-link v-if="user" to="/" class="navbar-brand">Auctions</router-link>
                <div class="collapse navbar-collapse">

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
                            <router-link to="/create-auction" class="nav-link">Create Auction</router-link>
                        </li>
                        <li class="nav-item">
                            <router-link to="/user" class="nav-link">Account</router-link>
                        </li>

                        <Logout />

                    </ul>

                </div>
        </div>
    </nav>
</template>

<script>
    import Logout from "@/components/Logout.vue";
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

