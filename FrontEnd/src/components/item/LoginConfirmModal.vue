<template>
  <b-modal
    id="login-confirm-modal"
    title="로그인이 필요한 서비스입니다"
    ok-title="예"
    cancel-title="아니오"
    @ok="goToLogin"
  >
    로그인 페이지로 이동하시겠습니까?
  </b-modal>
</template>

<script>
export default {
  name: "LoginConfirmModal",
  methods: {
    goToLogin() {
      this.$router.push({ name: "UserLogin" });
    },
  },
};
</script>

<style></style>
