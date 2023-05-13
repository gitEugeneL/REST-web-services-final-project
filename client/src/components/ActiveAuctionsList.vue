<template>
    <div class="container">
        <h4>List of active auctions</h4>
        <AuctionsListCard :auctions="auctions" />
    </div>
</template>


<script>
import axios from "axios";
import {APPLICATION_SERVER} from "@/config";
import AuctionsListCard from "@/components/AuctionsListCard.vue";
import {mapGetters} from "vuex";

export default {
    name: "ActiveAuctionsList",

    components: { AuctionsListCard },

    data() {
        return {
            auctions: [],
        }
    },

    async created() {
        const token = localStorage.getItem('token');
        const response = await axios.get(`${APPLICATION_SERVER}/api/auction`, {
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
