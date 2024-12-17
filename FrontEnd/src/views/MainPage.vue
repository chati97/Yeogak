<template>
  <div class="mt-5">
    <div class="row justify-content-center">
      <div class="col-lg-6 text-center">
        <img src="@/assets/여각_logo_green.png" height="60%;" />
        <p data-aos="fade-up" style="color: white; font-size: 15pt">
          전국에 존재하는 다양한 관광지를 알려드립니다
        </p>
        <b-button size="lg" pill variant="light" :to="{ name: 'PlacePage' }">Get start</b-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "MainPage",
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
img {
  filter: drop-shadow(6px 4px 4px #8a8a8a);
}
</style>
