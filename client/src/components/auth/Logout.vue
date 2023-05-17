<template>
    <li class="nav-item">
        <a href="javascript:void(0)" @click="handleClickLogout" data-toggle="modal"  data-target="#deleteModal" class="nav-link text-danger">Logout</a>
    </li>
</template>

<script>
import axios from "axios";
import {AUTH_SERVER} from "@/config";
import router from "@/router";
import store from "@/vuex";

export default {
    name: "Logout",

    methods: {
        async handleClickLogout() {
            const response = await axios.get(`${AUTH_SERVER}/api/user/logout`, {
                headers: { Authorization: 'Bearer ' + localStorage.getItem('token') }
            });

            if (response.status === 200) {
                localStorage.removeItem('token')
                await store.dispatch('user', null)
                await router.push('/login');
            }
        }
    }
}
</script>

<style scoped>

</style>