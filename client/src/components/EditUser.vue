<template>
    <div class="wrapper">
        <form @submit.prevent="handleSubmit">
            <Error v-if="error" :error="error"/>
            <h3>Edit your account</h3>
            <div class="form-group">
                <label>First name</label>
                <input v-model="firstName"
                       type="text"
                       class="form-control"
                       placeholder="Your first name">
            </div>
            <div class="form-group">
                <label>Last name</label>
                <input v-model="lastName"
                       type="text"
                       class="form-control"
                       placeholder="Your last name">
            </div>
            <button class="btn btn-primary btn-block">Update your account</button>
        </form>
    </div>
</template>


<script>
import Error from "@/components/Error.vue";
import {mapGetters} from "vuex";
import axios from "axios";
import {AUTH_SERVER} from "@/config";
import router from "@/router";
import store from "@/vuex";

export default {
    name: "EditUser",

    computed: {
        ...mapGetters(['user']),
    },

    components: { Error },

    data() {
        return {
            id: '',
            firstName: '',
            lastName: '',
            error: ''
        }
    },

    mounted() {
        this.id = this.user.id;
        this.firstName = this.user.firstName;
        this.lastName = this.user.lastName;
    },

    methods: {
        async handleSubmit() {
            try {
                const data = {
                    firstName: this.firstName,
                    lastName: this.lastName
                }
                const response = await axios.put(`${AUTH_SERVER}/api/user/${this.id}`, data, {
                    headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
                });
                if (response.status === 200) {
                    await store.dispatch('user',
                        { id: this.id, email: this.user.email, firstName: data.firstName, lastName: data.lastName })
                    await router.push('/user');
                }
            } catch (e) {
                this.error = e.response.data.message;
            }
        }
    }
}
</script>


<style scoped>
    .wrapper {
        padding: 45px 55px 45px 55px;
    }

    button {
        margin: 30px auto auto;
}
</style>