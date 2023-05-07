<template>
    <nav class="navbar navbar-expand navbar-light fixed-top">
        <div class="container">
            <router-link to="/" class="navbar-brand">Home</router-link>
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
