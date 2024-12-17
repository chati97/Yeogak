<template>
  <b-sidebar id="plan-side-bar" right shadow>
    <b-button type="button" @click="registPlan">여행계획 등록하기</b-button>
    <plan-side-bar-item
      v-for="att in tourPlan"
      :key="att.id"
      :att="att"
      class="tour-plan-item"
      @deleteItem="deleteItem"
    ></plan-side-bar-item>
  </b-sidebar>
</template>

<script>
import PlanSideBarItem from "./PlanSideBarItem";

export default {
  name: "PlanSideBar",
  components: {
    PlanSideBarItem,
  },
  data() {
    return {};
  },
  props: {
    tourPlan: [],
  },
  methods: {
    deleteItem(idx) {
      this.$emit("deleteItem", idx);
    },
    registPlan() {
      this.$emit("registPlan");
    },
  },
};
</script>

<style></style>
