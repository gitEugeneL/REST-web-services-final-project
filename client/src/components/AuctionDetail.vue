<template>
    <div class="wrapper">
        <div v-if="auction">
            <div>
                <img class="card-img-top" src="https://via.placeholder.com/300x200" alt="Card image cap">
            </div>
            <div class="card-body">
                <div class="title">
                    <h2 class="card-title mb-3">{{ auction.name }}</h2>
                    <p class="card-text mb-4">{{ auction.description }}</p>
                </div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item border-0 py-1">
                        <span class="fw-bold me-2">Start date:</span> 10 jun 2023 year, 10:00
                    </li>
                    <li class="list-group-item border-0 py-1">
                        <span class="fw-bold me-2">End date:</span> 15 jun 2023 year, 18:00
                    </li>
                    <li class="list-group-item border-0 py-1">
                        <span class="fw-bold me-2">Initial price:</span> €10
                    </li>
                    <li class="list-group-item border-0 py-1">
                        <span class="fw-bold me-2">Bids:</span> €25, €30, €58, €99
                    </li>
                    <li class="list-group-item border-0 py-1">
                        <span class="fw-bold me-2 text-danger">Current price:</span> €99
                    </li>
                    <li class="list-group-item border-0 py-1">
                        <form>
                            <div class="form-group mb-0 mt-2">
                                <label for="bid" class="fw-bold me-2">Your bid:</label>
                                <div class="input-group">
                                    <input type="number" class="form-control" placeholder="Enter your bid">
                                    <button type="submit" class="btn btn-danger">Place a bet</button>
                                </div>
                            </div>
                        </form>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</template>


<script>
    import {mapGetters} from "vuex";
    import axios from "axios";
    import {APPLICATION_SERVER} from "@/config";

    export default {
        name: 'AuctionDetail',

        computed: {
            ...mapGetters(['auctionId'])
        },

        data() {
            return {
                auction: null
            }
        },

        async created() {
            const token = localStorage.getItem('token');
            if (token) {
                const response = await axios.get(`${APPLICATION_SERVER}/api/auction/${this.auctionId}`, {
                    headers: { Authorization: 'Bearer ' + token }
                });
                this.auction = response.data;
            }
        }
    }

</script>

<style scoped>
    img {
        height: 200px;
    }

    .title {
        text-align: center;
    }
</style>
