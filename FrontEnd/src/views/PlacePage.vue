<template>
  <div>
    <h1>관광지</h1>
    <router-view  class="content px-3 rounded bg-white"></router-view>
  </div>
</template>

<script>
export default {
  name: "PlacePage",
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

<style scoped></style>
