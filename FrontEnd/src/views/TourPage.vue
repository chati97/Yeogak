<template>
  <div>
    <h1>여행계획</h1>
    <router-view class="content px-3 rounded bg-white"></router-view>
  </div>
</template>

<script>
export default {
  name: "TourPage",
  components: {},
  data() {
    return {
      message: "",
    };
  },
  created() {},
  methods: {},
};
</script>

<style scoped>
.tour-page-bg {
  box-sizing: border-box;
  height: 80%;
}
</style>
