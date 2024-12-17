<template>
  <b-card class="m-1" :title="att.title" :img-src="att.firstImage" img-left style="height: 8rem">
    <b-icon icon="x-square" @click="deleteItem" />
  </b-card>
</template>

<script>
import { mapState, mapGetters } from "vuex";

const userStore = "userStore";

export default {
  name: "TourPlanSideBarItem",
  props: {
    att: Object,
  },
  computed: {
    ...mapState(userStore, ["isLogin", "userInfo"]),
    ...mapGetters(["checkUserInfo"]),
  },
  methods: {
    deleteItem() {
      this.$emit("deleteItem", this.att.idx);
    },
  },
};
</script>

<style>
.tour-plan-item .card-img-left {
  width: 8rem;
}
</style>
