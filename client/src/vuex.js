import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate'


const state = {
    user: null,
    actionId: null,
    auction: null
};

const store = new Vuex.Store({
    state,
    plugins: [ createPersistedState() ],

    getters: {
        user: (state) => {
            return state.user;
        },
        auctionId: (state) => {
            return state.actionId;
        },
        auction: (state) => {
            return state.auction;
        }
    },

    actions: {
        user(context, user) {
            context.commit('user', user);
        },
        auctionId(context, auctionId) {
          context.commit('auctionId', auctionId);
        },
        auction(context, auction) {
            context.commit('auction', auction)
        }
    },

    mutations: {
        user(state, user) {
            state.user = user;
        },
        auctionId(state, auctionId) {
            state.actionId = auctionId;
        },
        auction(state, auction) {
            state.auction = auction;
        }
    }
});

export default store;