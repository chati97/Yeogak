<template>
  <div>
    <h1 class="underline">Q&A</h1>
    <router-view class="content px-3 rounded bg-white"></router-view>
  </div>
</template>

<script>

export default {
  name: "QnAView",
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
