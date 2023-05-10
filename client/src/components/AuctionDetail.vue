<template>
    <div class="wrapper">
<!--        hello {{ auctionId }}-->
        <div>
            <img class="card-img-top" src="https://via.placeholder.com/300x200" alt="Card image cap">
        </div>
        <div class="card-body">
            <div class="title">
                <h2 class="card-title mb-3">Auction Name</h2>
                <p class="card-text mb-4">Auction description</p>
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
                <li class="list-group-item border-0 py-3">
                    <form>
                        <div class="form-group mb-0">
                            <label for="bid" class="fw-bold me-2">Current price:</label>
                            <span class="fw-bold fs-3 text-danger"> €99</span>
                        </div>
                        <div class="form-group mb-0 mt-2">
                            <label for="bid" class="fw-bold me-2">Your bid:</label>
                            <div class="input-group">
                                <input type="text" class="form-control" placeholder="Enter your bid">
                                <button type="submit" class="btn btn-danger">Place a bet</button>
                            </div>
                        </div>
                    </form>
                </li>
            </ul>
        </div>


    </div>
</template>


<script>
    import {mapGetters} from "vuex";

    export default {
        name: 'AuctionDetail',

        computed: {
            ...mapGetters(['auctionId'])
        },

    }

</script>

<style scoped>
    .wrapper {
        width: 550px;
        margin: auto;
        box-shadow: 0 14px 80px rgba(34, 35, 58, .2);
        padding: 20px;
        border-radius: 15px;
        transition: all .3s;
    }

    img {
        height: 200px;
    }

    .title {
        text-align: center;
    }
</style>
