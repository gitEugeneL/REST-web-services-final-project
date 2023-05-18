<template>
    <nav class="navbar navbar-expand-md navbar-light fixed-top">
        <div class="container">
            <router-link v-if="user" to="/" class="navbar-brand">Auctions</router-link>
            <button
                    class="navbar-toggler"
                    type="button"
                    data-toggle="collapse"
                    data-target="#navbarSupportedContent"
                    @click="toggleNavbar"
                    :aria-expanded="navbarExpanded ? 'true' : 'false'"
                    :class="{ collapsed: !navbarExpanded }"
            >
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" :class="{ show: navbarExpanded }">
                <ul v-if="user" class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <router-link to="/my-auctions" class="nav-link" @click="closeNavbar">My auctions</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link to="/im-participant" class="nav-link" @click="closeNavbar">I'm participate</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link to="/im-winner" class="nav-link" @click="closeNavbar">I'm winner</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link to="/my-purchased-auctions" class="nav-link" @click="closeNavbar">Purchased auctions</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link to="/my-sold-products" class="nav-link" @click="closeNavbar">My sold products</router-link>
                    </li>
                </ul>

                <ul v-if="!user" class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <router-link to="/login" class="nav-link" @click="closeNavbar">Login</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link to="/register" class="nav-link" @click="closeNavbar">Sign up</router-link>
                    </li>
                </ul>
                <ul v-if="user" class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <router-link to="/create-auction" class="nav-link text-success" @click="closeNavbar">New auction</router-link>
                    </li>
                    <li class="nav-item">
                        <router-link to="/user" class="nav-link text-primary" @click="closeNavbar">{{ user.firstName }}</router-link>
                    </li>
                    <Logout @click="closeNavbar" />
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
        },

        data() {
            return {
                navbarExpanded: false,
            }
        },

        methods: {
            toggleNavbar() {
                this.navbarExpanded = !this.navbarExpanded;
            },

            closeNavbar() {
                this.navbarExpanded = false;
            }
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

