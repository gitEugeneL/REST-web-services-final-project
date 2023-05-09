<template>
    <div class="auth-inner">
        <form @submit.prevent="handleSubmit">
            <Error v-if="error" :error="error"/>
            <h3>Login</h3>
            <div class="form-group">
                <label>Email</label>
                <input v-model="email"
                     type="email"
                     class="form-control"
                     placeholder="Email">
            </div>
            <div class="form-group">
                <label>Password</label>
                <input v-model="password"
                     type="password"
                     class="form-control"
                     placeholder="Password">
            </div>
            <button class="btn btn-primary btn-block">Login</button>
        </form>
    </div>
</template>


<script>
    import axios from "axios";
    import { AUTH_SERVER } from "@/config";
    import router from "@/router";
    import store from "@/vuex";
    import Error from "@/components/Error.vue";

    export default {
        name: 'Login',

        components: { Error },

        data() {
            return {
                email: '',
                password: '',
                error: ''
            }
        },

        methods: {
            async handleSubmit() {
                try {
                    const data = {
                        login: this.email,
                        password: this.password
                    }
                    const response = await axios.post(`${AUTH_SERVER}/api/user/login`, data);

                    if (response.status === 200) {
                        localStorage.setItem('token', response.data.accessToken);

                        const getAuthUser = await axios.get(`${AUTH_SERVER}/api/user/auth/info`, {
                            headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
                        })

                        await store.dispatch('user', getAuthUser.data);
                        await router.push('/user');
                    }
                } catch (e) {
                    this.error = e.response.data.message;
                }
            }
        }
    }
</script>