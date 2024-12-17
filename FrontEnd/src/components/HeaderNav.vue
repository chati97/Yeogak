<template>
  <div class="main">
    <b-navbar togglable="sm" type="dark">
      <b-navbar-brand :to="{ name: 'MainPage' }" class="ml-5" style="height: 15vh; object-fit: cover;">
        <img src="@/assets/여각_logo_green.png" width="100%" height="100%">
      </b-navbar-brand>

      <b-navbar-toggle target="navbar" style="margin-right: 5%" right></b-navbar-toggle>
      <b-collapse class="mt-5" id="navbar" is-nav>
        <b-navbar-nav class="ml-auto mr-5">
          <b-nav-item :to="{ name: 'PlacePage' }" right> 관광지 </b-nav-item>
          <b-nav-item :to="{ name: 'TourPage' }" right> 여행계획 </b-nav-item>
          <b-nav-item :to="{ name: 'NoticeList' }" right> 공지사항 </b-nav-item>
          <b-nav-item :to="{ name: 'BoardList' }" right> 게시판 </b-nav-item>
          <b-nav-item :to="{ name: 'QnAView' }" right> Q&A </b-nav-item>
          <b-nav-item v-if="userInfo" @click.prevent="userLogoutOnClick" right> 로그아웃 </b-nav-item>
          <b-nav-item v-if="userInfo" :to="{ name: 'UserMyPage' }" right> 마이페이지 </b-nav-item>
          <b-nav-item v-if="!userInfo" :to="{ name: 'UserLogin' }" right> 로그인 </b-nav-item>
          <b-nav-item v-if="!userInfo" :to="{ name: 'UserRegist' }" right> 회원가입 </b-nav-item>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
import { mapState, mapGetters, mapActions } from "vuex";

const userStore = "userStore";

export default {
  components: {},
  data() {
    return {};
  },
  computed: {
    ...mapState(userStore, ["isLogin", "userInfo"]),
    ...mapGetters(["checkUserInfo"]),
  },
  methods: {
    ...mapActions(userStore, ["userLogout"]),
    userLogoutOnClick() {
      this.userLogout(this.userInfo.userid);
      //저장된 토큰 없애기
      sessionStorage.removeItem("access-token");
      sessionStorage.removeItem("refresh-token");
      if (this.$route.path != "/") this.$router.push({ name: "MainPage" });
    },
  },
};
</script>

<style scoped>
.nav-item {
  list-style: none;
}
.nav-link,
.navbar-brand {
  font-weight: bold;
  font-size: 14px;
  text-transform: uppercase;
  text-decoration: none;
  padding: 20px 0px;
  margin: 0px 20px;
  display: inline-block;
  position: relative;
  opacity: 1;
}
.navbar-brand {
  font-size: 32px;
}
.nav-link:hover,
.navbar-brand:hover {
  opacity: 1;
}

.nav-link::before,
.navbar-brand::before {
  transition: 300ms;
  height: 5px;
  content: "";
  position: absolute;
  background-color: rgba(255, 255, 0, 0.887);
}

.nav-link::before,
.navbar-brand::before {
  width: 0%;
  bottom: 10px;
}
.nav-link:hover::before,
.navbar-brand:hover::before {
  width: 100%;
}
a.nav-link {
  color: white;
  font-weight: bold;
  font-size: 12pt;
}
.navbar-collapse.collapse.show {
  background-color: rgb(148, 148, 148);
  border-radius: 10px;
}
</style>
