<template>
  <div>
    <b-card class="content">
      <b-table stacked :items="items" :fields="fields">
        <template #cell(email)="data">
          {{ data.item.emailId }}@{{ data.item.emailDomain }}
        </template>
        <template #cell(sidoName)>
          {{ getName }}
        </template>
      </b-table>
      <b-button @click="updateInfo">정보 수정</b-button>
      <b-button @click="updatePw">비밀번호 변경</b-button>
      <b-button @click="deleteModal">탈퇴</b-button>
      <b-modal id="deleteForm" title="회원 탈퇴" @ok="deleteUser">
        <form ref="form" @submit.stop.prevent="handleSubmit">
          <b-form-group
            label="비밀번호를 입력하세요"
            label-for="password"
            invalid-feedback="password is required"
          >
            <b-form-input id="password" type="password" v-model="password" required></b-form-input>
          </b-form-group>
        </form>
      </b-modal>
    </b-card>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
import axios from "@/api/http-common";

const userStore = "userStore";
export default {
  name: "UserMyPage",
  components: {},
  data() {
    return {
      sidoList: [],
      password: "",
      items: [],
      fields: [
        { key: "userId", label: "아이디" },
        { key: "name", label: "이름" },
        { key: "nickname", label: "별명" },
        { key: "email", label: "이메일" },
        { key: "sidoName", label: "시도" },
        { key: "sex", label: "성별" },
      ],
    };
  },
  created() {
    axios.get(`/tour/sido`).then(({ data }) => {
      this.sidoList = data;
    });
    this.items.push(this.userInfo);
    this.$emit("getTitle", "마이페이지");
  },
  computed: {
    ...mapState(userStore, ["userInfo", "isDelete"]),
    getName: function () {
      for (let i = 0; i < this.sidoList.length; i++) {
        if (this.userInfo.sido == this.sidoList[i].sidoCode) {
          return this.sidoList[i].sidoName;
        }
      }
      return 0;
    },
  },
  methods: {
    ...mapActions(userStore, ["userDelete", "userLogout"]),
    updateInfo() {
      this.$router.push({ name: "UserModifyInfo" });
    },
    updatePw() {
      this.$router.push({ name: "UserModifyPw" });
    },
    deleteModal() {
      this.$bvModal.show("deleteForm");
    },
    async deleteUser() {
      let userInfo = {
        userId: this.userInfo.userId,
        userPw: this.password,
      };
      await this.userDelete(userInfo);
      if (this.isDelete) {
        alert("성공");
        this.userLogout(this.userInfo.userid);
        this.isDelete = false;
        //저장된 토큰 없애기
        sessionStorage.removeItem("access-token");
        sessionStorage.removeItem("refresh-token");
        this.$router.push({ name: "MainPage" });
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
