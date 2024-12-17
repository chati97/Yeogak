<template>
  <div>
    <b-card style="width: 70%; margin: auto">
      <b-media>
        <div>
          <label for="password">기존 비밀번호</label>
          <b-input type="password" id="password" v-model="password"></b-input>
          <label for="changePw">변경할 비밀번호</label>
          <b-input type="password" id="changePw" v-model="changePw"></b-input>
          <label for="chkPw">비밀번호 확인</label>
          <b-input type="password" id="chkPw" v-model="chkPw"></b-input>
        </div>
        <b-button @click="modify">수정</b-button>
      </b-media>
    </b-card>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";

const userStore = "userStore";
export default {
  name: "UserMyPage",
  components: {},
  data() {
    return {
      password: "",
      changePw: "",
      chkPw: "",
    };
  },
  created() {
    this.$emit("getTitle", "마이페이지");
  },
  computed: {
    ...mapState(userStore, ["isUpdate", "userInfo"]),
  },
  methods: {
    ...mapActions(userStore, ["userModifyPw"]),
    async modify() {
      if (this.changePw == this.chkPw) {
        let updatePw = {
          userId: this.userInfo.userId,
          userPw: this.password,
          changePw: this.changePw,
        };
        await this.userModifyPw(updatePw);
        if (this.isUpdate) {
          alert("성공");
          this.isUpdate = false;
          this.$router.push({ name: "MainPage" });
        } else {
          alert("기존 비밀번호가 일치하지 않습니다");
          this.$router.back();
        }
      } else {
        alert("비밀번호가 일치하지 않습니다");
        this.$router.back();
      }
    },
  },
};
</script>

<style scoped>
h6 {
  text-align: right;
}
</style>
