<template>
    <button @click="handleClickPay" class="btn btn-outline-danger">Pay for the auction</button>
</template>

<script>
import axios from "axios";
import {PAYMENT_SERVER} from "@/config";

export default {
    name: "Pay",

    props: {
        auctionId: {type: String, required: true}
    },

    methods: {
        async handleClickPay() {
            const response = await axios.get(`${PAYMENT_SERVER}/api/payment/${this.auctionId}`, {
                headers: { Authorization: 'Bearer ' + localStorage.getItem('token') }
            })
            if (response.status === 200) {
                window.location.href = response.data;
            }
        }
    }
}
</script>

<style scoped>

</style>