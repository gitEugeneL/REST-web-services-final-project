<template>
    <div class="container">
        <h4>List of auctions won but not yet paid for</h4>
        <AuctionsListCard :auctions="auctions" />
    </div>
</template>

<script>
import AuctionsListCard from "@/components/auction/AuctionsListCard.vue";
import axios from "axios";
import {APPLICATION_SERVER} from "@/config";

export default {
    name: "UserWinnerAuctionList",

    components: { AuctionsListCard },

    data() {
        return {
            auctions: [],
        }
    },

    async created() {
        const token = localStorage.getItem('token');
        const response = await axios.get(`${APPLICATION_SERVER}/api/auction/auth-winner-auctions`, {
            headers: { Authorization: 'Bearer ' + token }
        });
        this.auctions = response.data;
    }


}
</script>

<style scoped>
    h4 {
        width: fit-content;
        margin: auto auto 30px;
    }
</style>