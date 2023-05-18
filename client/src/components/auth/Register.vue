<template>
    <div class="auth-inner">
        <form @submit.prevent="handleSubmit">
            <Error v-if="error" :error="error"/>
            <h3>Sign up</h3>
            <div class="form-group">
                <label>Email</label>
                <input v-model="email"
                       type="email"
                       class="form-control"
                       placeholder="Your email address">
            </div>
            <div class="form-group">
                <label>Password</label>
                <input v-model="password"
                       type="password"
                       class="form-control"
                       placeholder="Password">
            </div>

            <div class="form-group">
                <label>Confirm password</label>
                <input v-model="confirmPassword"
                       type="password"
                       class="form-control"
                       placeholder="Confirm password">
            </div>

            <div class="form-group">
                <label>First Name</label>
                <input v-model="fistName"
                       type="text"
                       class="form-control"
                       placeholder="Your first name">
            </div>

            <div class="form-group">
                <label>Last Name</label>
                <input v-model="lastName"
                       type="text"
                       class="form-control"
                       placeholder="Your first name">
            </div>
            <button class="btn btn-primary btn-block">Sign up</button>
        </form>
    </div>
</template>


<script>
    import axios from "axios";
    import { AUTH_SERVER } from "@/config";
    import router from "@/router";
    import Error from "@/components/Error.vue";

    export default {
        name: 'Register',
        components: {Error},

        data() {
            return {
                email: '',
                password: '',
                confirmPassword: '',
                fistName: '',
                lastName: '',
                error: ''
            }
        },

        methods: {
            async handleSubmit() {
                try {
                    if (this.password !== this.confirmPassword) {
                        this.error = 'Passwords do not match';
                        return;
                    }
                    this.error = '';

                    const data = {
                        login: this.email,
                        password: this.password,
                        firstName: this.fistName,
                        lastName: this.lastName,
                    };
                    await axios.post(`${AUTH_SERVER}/api/user/create`, data);

                    await router.push('/login');
                } catch (e) {
                    this.error = e.response.data.message;
                }
            }
      }
  }
</script>