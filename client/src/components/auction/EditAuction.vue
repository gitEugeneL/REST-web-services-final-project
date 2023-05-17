<template>
    <div class="wrapper">
        <form @submit.prevent="handleSubmit">
            <Error v-if="error" :error="error"/>
            <h3>Edit your auction</h3>
            <div class="form-group">
                <label>Description</label>
                <textarea v-model="description" class="form-control" placeholder="Your description" />
            </div>
            <button class="btn btn-primary btn-block">Update your account</button>
        </form>
    </div>
</template>


<script>
import {mapGetters} from "vuex";
import Error from "@/components/Error.vue";
import axios from "axios";
import {APPLICATION_SERVER} from "@/config";
import store from "@/vuex";
import router from "@/router";

export default {
    name: 'EditAuction',

    components: { Error },

    computed: {
        ...mapGetters(['auction'])
    },

    data() {
        return {
            description: '',
            error: '',
        }
    },

    mounted() {
      this.description = this.auction.description;
    },

    methods: {
        async handleSubmit() {
            try {
                const data = {
                    description: this.description
                }
                const response = await axios.put(`${APPLICATION_SERVER}/api/auction/${this.auction.id}`, data, {
                    headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
                });
                if (response.status === 200) {
                    await store.dispatch('auction', this.auction.description = this.description)
                    await router.push('/my-auctions');
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