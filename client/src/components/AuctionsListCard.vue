<template>
    <div class="row">
        <div v-for="auction in auctions" :key="auction.id" class="col-md-4">
            <div class="card">
                <img v-if="auction.status !== 'PAID'" class="card-img-top" src="https://via.placeholder.com/300x200" alt="Card image cap">
                <div class="card-body text-center">
                    <h5 class="card-title">{{ auction.name }}
                        <span class="your-card" v-if="auction.sellerEmail === user.email"> (Your)</span></h5>
                    <p class="card-text">End date: <span class="font-weight-bold">{{ formatDateTime(auction.end_time) }}</span></p>
                    <div class="d-flex justify-content-center">
                        <div class="card-text">
                            <p v-if="auction.status !== 'PAID'" class="mb-1">Initial price</p>
                            <p v-if="auction.status !== 'PAID' && auction.status !== 'FAIlLED'"
                               class="mb-0 font-weight-success">{{ auction.starting_price }}zł
                            </p>
                        </div>
                        <div class="card-text ml-3">
                            <p v-if="auction.status === 'ACTIVE'" class="mb-1">Current price</p>
                            <p v-if="auction.status === 'FINISHED'" class="mb-1">Final price</p>
                            <p v-if="auction.status === 'PAID' && auction.sellerEmail !== user.email"
                               class="mb-1 text-success">
                                    You paid: {{ auction.current_price }}zł
                            </p>
                            <p class="mb-1 text-success"
                               v-if="auction.status === 'PAID' && auction.sellerEmail === user.email">
                                    Bought for {{ auction.current_price }}zł
                            </p>

                            <p v-if="auction.status !== 'PAID'" class="mb-0 font-weight-bold text-danger">
                                {{ auction.current_price }}zł
                            </p>
                        </div>
                    </div>
                    <div v-if="auction.status === 'FAIlLED'" class="text-danger">No one participated :(</div>
                    <router-link
                            @click="saveAuctionId(auction.id)"
                            :to="{ path: '/auction-detail'}" class="btn btn-primary mt-3">
                        View details
                    </router-link>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import store from "@/vuex";
import {mapGetters} from "vuex";

export default {
    name: "AuctionsListCard",

    computed: {
        ...mapGetters(['user'])
    },

    props: {
        auctions: {type: Array, required: true},
        lead: {type: String}
    },

    methods: {
        formatDateTime(dateTimeStr) {
            const date = new Date(dateTimeStr);
            const options = {
                year: 'numeric', month: 'short', day: 'numeric',
                hour: 'numeric', minute: 'numeric', hour12: false
            };
            return date.toLocaleString('en-EN', options);
        },

        async saveAuctionId(auctionId) {
            await store.dispatch('auctionId', auctionId);
        }
    }
}
</script>

<style scoped>
    .card {
        box-shadow: 0 14px 80px rgba(34, 35, 58, .2);
        padding: 20px;
        border-radius: 15px;
        transition: all .3s;
        margin-bottom: 30px;
    }
    .your-card {
        font-style: italic;
        color: #ff0000;
    }
</style>