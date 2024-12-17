<template>
  <b-card
    :title="plan.title"
    :img-src="plan.imgSrc"
    img-left
    style="max-height: 8rem"
    class="m-1"
    tag="article"
    @click="viewPlan"
  >
    <b-card-text> </b-card-text>
  </b-card>
</template>

<script>
export default {
  name: "TourPlanListItem",
  data() {
    return {};
  },
  props: {
    plan: Object,
  },
  created() {},
  methods: {
    viewPlan() {
      this.$router.push({ name: "TourPlanView", params: { id: this.plan.id } });
    },
  },
};
</script>

<style></style>
