<template>
    <div class="container">
        <h4>List of your active auctions</h4>
        <AuctionsListCard :auctions="auctions" />
    </div>
</template>

<script>
import AuctionsListCard from "@/components/AuctionsListCard.vue";
import {APPLICATION_SERVER} from "@/config";
import axios from "axios";

export default {
    name: "UserSoldProducts",

    components: { AuctionsListCard },

    data() {
        return {
            auctions: [],
        }
    },

    async created() {
        const token = localStorage.getItem('token');
        const response = await axios.get(`${APPLICATION_SERVER}/api/auction/auth-user-auctions`, {
            headers: { Authorization: 'Bearer ' + token }
        });
        this.auctions = response.data.filter(auction => auction.status === 'PAID');
    }

}
</script>


<style scoped>
h4 {
    width: fit-content;
    margin: auto auto 30px;
}
</style>
