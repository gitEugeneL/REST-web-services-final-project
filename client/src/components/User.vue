<template>
    <h3 v-if="user">
        Hi, {{ user.firstName }} {{ user.lastName }}
    </h3>
    <h3 v-else>
        You are not login !
    </h3>
</template>


<script>
import { mapGetters } from "vuex";

export default {
    name: 'User',

    computed: {
        ...mapGetters(['user'])
    }
}
</script>