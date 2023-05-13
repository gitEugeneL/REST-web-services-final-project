<template>
    <div class="wrapper">
        <form @submit.prevent="handleSubmit">
            <Error v-if="error" :error="error"/>
            <h3>Edit your auction</h3>




        </form>
    </div>
</template>


<script>
import {mapGetters} from "vuex";
import Error from "@/components/Error.vue";

export default {
    name: 'EditAuction',

    components: {Error},

    computed: {
        ...mapGetters(['user', "auction"])
    },

    data() {
        return {
            name: '',
            description: ''
        }
    },


    created() {
        console.log(this.auction);
    }
}

</script>


<style scoped>
</style>