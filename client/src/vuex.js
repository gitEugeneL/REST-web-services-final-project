import Vuex from 'vuex'

const state = {
    user: null,
    actionId: null
};

const store = new Vuex.Store({
    state,

    getters: {
        user: (state) => {
            return state.user;
        },
        auctionId: (state) => {
            return state.actionId;
        }
    },

    actions: {
        user(context, user) {
            context.commit('user', user);
        },
        auctionId(context, auctionId) {
          context.commit('auctionId', auctionId);
        }
    },

    mutations: {
        user(state, user) {
            state.user = user;
        },
        auctionId(state, auctionId) {
            state.actionId = auctionId;
        }
    }
});

export default store;