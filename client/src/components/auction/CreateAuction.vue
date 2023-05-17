<template>
    <div class="wrapper">
        <form @submit.prevent="handleSubmit">
            <Error v-if="error" :error="error"/>
            <h3>Create new auction</h3>
            <div class="form-group">
                <label>Name</label>
                <input v-model="name"
                       type="text"
                       class="form-control"
                       placeholder="Auction name">
            </div>
            <div class="form-group">
                <label>Description</label>
                <textarea v-model="description"
                       class="form-control"
                       placeholder="Auction description" />
            </div>
            <div class="form-group">
                <label>Initial price</label>
                <input v-model="startingPrice"
                       type="number"
                       class="form-control"
                       placeholder="Auction initial price">
            </div>
            <h5>Auction time</h5>
            <div class="form-group">
                <div class="radio-wrapper">
                    <input v-model="lifeTime" value="test" type="radio" name="select" id="option-1">
                    <input v-model="lifeTime" value="one-day" type="radio" name="select" id="option-2">
                    <input v-model="lifeTime" value="three-days" type="radio" name="select" id="option-3">
                    <input v-model="lifeTime" value="one-week" type="radio" name="select" id="option-4">
                    <label for="option-1" class="option option-1">
                        <span>Test-5min</span>
                    </label>
                    <label for="option-2" class="option option-2">
                        <span>1 day</span>
                    </label>
                    <label for="option-3" class="option option-3">
                        <span>3 days</span>
                    </label>
                    <label for="option-4" class="option option-4">
                        <span>1 week</span>
                    </label>
                </div>
            </div>
            <button class="btn btn-primary btn-block">Create Auction</button>
        </form>
    </div>
</template>


<script>
import Error from "@/components/Error.vue";
import axios from "axios";
import {APPLICATION_SERVER} from "@/config";
import router from "@/router";

export default {
    name: "CreateAuction",

    components: { Error },

    data() {
        return {
            name: '',
            description: '',
            startingPrice: '',
            lifeTime: '',
            error: ''
        }
    },

    computed: {
        selectedLifeTime() {
            return this.lifeTime;
        }
    },

    methods: {
        async handleSubmit() {
            try {
                const data = {
                    name: this.name,
                    description: this.description,
                    startingPrice: parseInt(this.startingPrice),
                    lifeTime: this.selectedLifeTime
                }
                const token = localStorage.getItem('token');
                const response = await axios.post(`${APPLICATION_SERVER}/api/auction/create`, data, {
                    headers: { Authorization: `Bearer ${localStorage.getItem('token')}` }
                });
                if (response.status === 200) {
                    await router.push('/');
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

    h5 {
        margin-top: 30px;
        text-align: center;
    }

    .radio-wrapper {
        display: inline-flex;
        background: #fff;
        height:50px;
        width: 100%;
        align-items: center;
        justify-content: space-evenly;
        border-radius: 5px;
        margin-top: 10px;
    }

    .radio-wrapper .option {
        background: #fff;
        height: 100%;
        width: 100%;
        display: flex;
        align-items: center;
        justify-content: space-evenly;
        margin: 0 10px;
        border-radius: 5px;
        cursor: pointer;
        padding: 0 10px;
        border: 1px solid grey;
        transition: all 0.3s ease;
    }

    input[type="radio"] {
        display: none;
    }

    #option-1:checked:checked ~ .option-1,
    #option-2:checked:checked ~ .option-2,
    #option-3:checked:checked ~ .option-3,
    #option-4:checked:checked ~ .option-4 {
        border-color: #167bff;
        background: #167bff;
    }

    #option-1:checked:checked ~ .option-1 span,
    #option-2:checked:checked ~ .option-2 span,
    #option-3:checked:checked ~ .option-3 span,
    #option-4:checked:checked ~ .option-4 span {
        color: #fff;
    }
</style>