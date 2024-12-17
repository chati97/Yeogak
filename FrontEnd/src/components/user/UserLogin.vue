<template>
  <div>
    <b-card bg-white>
      <b-form class="text-left">
        <template #aside>
          <h3 class="mt-0">로그인 페이지</h3>
        </template>
        <b-alert show variant="danger" v-if="isLoginError"
          >아이디 또는 비밀번호를 확인하세요.</b-alert
        >
        <b-row>
          <b-col cols="4" />
          <b-col cols="4">
            <b-form-group label="아이디:" label-for="userId">
              <b-input id="userId" v-model="user.userId" /><br />
            </b-form-group>
          </b-col>
          <b-col cols="4" />
        </b-row>
        <b-row>
          <b-col cols="4" />
          <b-col cols="4">
            <b-form-group label="비밀번호:" label-for="userPw">
              <b-input id="userPw" type="password" v-model="user.userPw" @keyup.enter="userLogin" />
            </b-form-group>
          </b-col>
          <b-col cols="4" />
        </b-row>
        <b-row>
          <b-col cols="5" />
          <b-col cols="5">
            <b-button type="button" variant="primary" class="m-1" @click="userLogin"
              >로그인</b-button
            >
            <b-button type="button" variant="success" class="m-1" @click="userJoin"
              >회원가입</b-button
            >
          </b-col>
        </b-row>
      </b-form>
    </b-card>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";

const userStore = "userStore";

export default {
  name: "UserLogin",
  data() {
    return {
      user: {
        userId: "",
        userPw: "",
      },
    };
  },
  created() {
    this.$emit("getTitle", "로그인");
  },
  computed: {
    ...mapState(userStore, ["isLogin", "isLoginError", "userInfo"]),
  },
  methods: {
    ...mapActions(userStore, ["userConfirm", "getUserInfo"]),
    async userLogin() {
      await this.userConfirm(this.user);
      let token = sessionStorage.getItem("access-token");
      if (this.isLogin) {
        await this.getUserInfo(token);
        this.$router.push({ name: "MainPage" });
      } else {
        console.log("test");
        alert("회원정보가 일치하지 않습니다");
        this.$router.push({ name: "MainPage" });
      }
    },
    userJoin() {
      this.$router.push({ name: "UserRegist" });
    },
  },
};
</script>

<style scoped></style>
