const tourStore = {
  namespaced: true,
  state: {
    storedTourPlan: [],
  },
  getters: {
    getStoredTourPlan: function (state) {
      return state.storedTourPlan;
    },
  },
  mutations: {
    SET_STORED_TOUR_PLAN: (state, tourPlan) => {
      state.storedTourPlan = tourPlan;
    },
    REASSIGN_ID: (state) => {
      let id = 0;
      state.storedTourPlan.forEach((item) => {
        item.id = id++;
      });
    },
  },
  actions: {
    async setStoredTourPlan({ commit }, tourPlan) {
      commit("SET_STORED_TOUR_PLAN", tourPlan);
      commit("REASSIGN_ID");
    },
  },
};

export default tourStore;
