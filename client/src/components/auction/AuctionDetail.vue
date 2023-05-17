<template>
    <div class="wrapper">
        <div v-if="auction">
            <div class="img-block">
                <img class="card-img-top" v-if="!image" src="https://via.placeholder.com/300x200" alt="">
                <img class="card-img" v-if="image" :src="image" alt="">
            </div>
            <div class="card-body">
                <div class="timer text-danger">{{ this.timerText }}</div>

                <div class="title">
                    <h2 class="card-title mb-3">{{ auction.name }}</h2>
                    <p class="card-text mb-4">{{ auction.description }}</p>
                </div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item border-0 py-1">
                        <span class="fw-bold me-2">End date: </span>{{ formatDateTime(auction.end_time) }}
                    </li>
                    <li class="list-group-item border-0 py-1">
                        <span class="fw-bold me-2">Initial price:</span> {{ auction.starting_price }}zł
                    </li>
                    <li class="list-group-item border-0 py-1" v-if="auction.status !== 'FAIlLED'">
                        <span class="fw-bold me-2">Bets: </span>
                        <span class="bid" v-for="bid in bids"> {{ bid }}zł</span>
                    </li>
                    <li class="list-group-item border-0 py-1">
                        <span class="fw-bold me-2"
                              v-if="auction.status !== 'FAIlLED' && auction.status !== 'PAID' && timerText !== null">
                            Current price: </span>
                        <span class="fw-bold me-2"
                              v-if="auction.status !== 'FAIlLED' && auction.status !== 'PAID' && timerText === null">
                            Final price: </span>
                        <span v-if="auction.status !== 'FAIlLED' && auction.status !== 'PAID'" class="text-danger">
                            {{ auction.current_price }}zł
                        </span>
                    </li>
                    <li class="list-group-item border-0 py-1">
                        <h5 class="text-danger" v-if="lead">{{ lead }}</h5>
                        <div v-if="auction.status !== 'ACTIVE'">
                            <h5 v-if="auction.winnerId !== user.id">This auction is over</h5>
                        </div>
                        <div v-if="auction.sellerEmail !== user.email && auction.status === 'ACTIVE'">
                            <Error v-if="error" :error="error"/>
                            <form @submit.prevent="handleBetSubmit"
                                  class="d-flex justify-content-center align-items-center">
                                <label for="bid" class="fw-bold">Your bet: </label>
                                <input v-model="enteredBet" type="number" class="form-control" placeholder="Enter your bet">
                                <button type="submit" class="btn btn-danger">Place a bet</button>
                            </form>
                        </div>
                        <div v-if="auction.sellerEmail === user.email && auction.status === 'ACTIVE'">
                            <h5>This is your auction</h5>
                            <div class="d-flex justify-content-center">
                                <button @click="editAuctionHandler" class="btn btn-outline-primary edit-btn">Edit auction</button>
                                <button class="btn btn-outline-danger">Delete auction</button>
                            </div>
                        </div>
                        <div v-if="auction.sellerEmail !== user.email && auction.status === 'FINISHED' && auction.winnerId === user.id">
                            <h5 class="text-success">Congratulations you are the winner !!!</h5>
                            <div class="d-flex justify-content-center">
                                <Pay :auctionId="auction.id"/>
                            </div>
                        </div>
                        <div v-if="auction.status === 'FAILED'">
                            <h5 class="text-success">The auction ended. There are no participants :(</h5>
                        </div>
                        <div v-if="auction.status === 'FINISHED' && auction.sellerEmail === user.email">
                            <h5 class="text-danger">The winner hasn't paid yet</h5>
                        </div>
                        <div v-if="auction.status === 'PAID' && auction.sellerEmail === user.email">
                            <h5 class="text-success">The winner paid {{ auction.current_price }}zł</h5>
                        </div>
                        <div v-if="auction.status === 'FAIlLED' && auction.sellerEmail === user.email">
                            <h5 class="text-danger">No one participated :(</h5>
                        </div>
                        <div v-if="auction.status === 'PAID' && auction.winnerId === user.id">
                            <h5 class="text-success">You paid {{ auction.current_price }}zł</h5>
                        </div>
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
    import Error from "@/components/Error.vue";
    import store from "@/vuex";
    import router from "@/router";
    import Pay from "@/components/auction/Pay.vue";

    export default {
        name: 'AuctionDetail',
        components: {Pay, Error},

        computed: {
            ...mapGetters(['auctionId', 'user']),
        },

        data() {
            return {
                auction: null,
                bids: null,

                countdown: '',
                timerText: '',

                error: null,
                enteredBet: '',

                lead: '',

                image: null
            }
        },

        async created() {
            await this.getAuctionData();
            this.countdown = setInterval(() => {
                this.getAuctionData();
                this.timerLogic();
            }, 1000);

            await this.getImage(this.auction.id);
        },


        methods: {
            async getAuctionData() {
                const token = localStorage.getItem('token');
                if (token) {
                    const response = await axios.get(`${APPLICATION_SERVER}/api/auction/${this.auctionId}`, {
                        headers: {Authorization: 'Bearer ' + token}
                    });
                    this.auction = response.data;
                    if(this.auction.participation !== null) {
                        this.bids = Object.values(this.auction.participation).sort((a, b) => a - b);

                        const entries = Object.entries(this.auction.participation);
                        const maxEntry = entries.reduce((prev, current) => prev[1] > current[1] ? prev : current);
                        if (this.user) {
                            if (maxEntry[0] === this.user.id && this.auction.status === 'ACTIVE') {
                                this.lead = `Your bet is in the lead: ${maxEntry[1]}zł`
                            } else {
                                this.lead = '';
                            }
                        }
                    }
                }
            },

            async getImage(auctionId) {
                const token = localStorage.getItem('token');
                const response = await axios.get(`${APPLICATION_SERVER}/api/image/download/${auctionId}`, {
                    headers: { Authorization: 'Bearer ' + token },
                    responseType: 'arraybuffer'
                })
                if (response.status === 200) {
                    const blob = new Blob([response.data], { type: response.headers['content-type'] });
                    this.image = URL.createObjectURL(blob);
                }
            },

            async handleBetSubmit() {
                try {
                    const data = {
                        auctionId: this.auction.id,
                        bet: this.enteredBet
                    }
                    const response = await axios.post(`${APPLICATION_SERVER}/api/auction/bet`, data, {
                        headers: {Authorization: `Bearer ${localStorage.getItem('token')}`}
                    })

                    if (response.status === 200) {
                        this.error = null;
                        this.enteredBet = '';
                        await this.getAuctionData();
                    }
                } catch (e) {
                    this.error = e.response.data.message;
                }
            },

            formatDateTime(dateTimeStr) {
                const date = new Date(dateTimeStr);
                const options = {
                    year: 'numeric', month: 'short', day: 'numeric',
                    hour: 'numeric', minute: 'numeric', hour12: false
                };
                return date.toLocaleString('en-EN', options);
            },

            timerLogic() {
                const distance = new Date(this.auction.end_time) - new Date();
                const days = Math.floor(distance / (1000 * 60 * 60 * 24));
                const hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
                const minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
                const seconds = Math.floor((distance % (1000 * 60)) / 1000);

                if (distance < 0) {
                    clearInterval(this.countdown);
                    this.countdown = null;
                    this.timerText = null;
                    this.getAuctionData();
                } else {
                    this.timerText = `${days}d ${hours}h ${minutes}m ${seconds}s`;
                }
            },

            async editAuctionHandler() {
                await store.dispatch('auction', this.auction);
                await router.push('/auction-edit');
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

    .bid {
        margin-right: 4px;
        font-weight: normal;
    }

    .bid span {
        margin-left: 15px;
        color: grey;
    }

    .timer {
        height: 25px;
        text-align: right;
        font-size: 18px;
        margin-bottom: 8px;
    }

    .form-control {
        margin-right: 20px;
        width: 200px;
    }

    label {
        margin: 0 20px 0 0;
    }

    form {
        padding-top: 10px;
    }

    .edit-btn {
        margin-right: 20px;
    }

    h5 {
        text-align: center;
        margin-bottom: 10px;
        margin-top: 10px;
    }

    .card-img {
        height: auto;
        width: 200px;
    }

    .img-block {
        text-align: center;
    }
</style>
