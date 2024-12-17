<template>
  <div>
    <b-card style="width: 70%; margin: auto">
      <b-media>
        <div>
          <label for="id">아이디</label>
          <b-input id="id" v-model="user.userId" readonly></b-input>
          <label for="name">이름</label>
          <b-input id="name" v-model="user.name"></b-input>
          <label for="nickname">별명</label>
          <b-input id="nickname" v-model="user.nickname"></b-input>
          <label for="email">이메일</label>
          <b-row>
            <b-col cols="4">
              <b-input id="email" v-model="user.emailId"></b-input>
            </b-col>
            <b-col cols="1"> @ </b-col>
            <b-col cols="4">
              <b-input id="" v-model="user.emailDomain"></b-input>
            </b-col>
            <b-col cols="3">
              <b-button @click="getCode">인증번호 받기</b-button>
            </b-col>
          </b-row>
          <label for="authorize">인증번호</label>
          <b-row>
            <b-col cols="9">
              <b-input id="authorize" v-model="authorize"></b-input>
            </b-col>
            <b-col cols="3">
              <b-button @click="chkCode" id="chkCode">인증</b-button>
            </b-col>
          </b-row>
          <label for="sido">시도</label>
          <sido-list @select-sido="selectSido"></sido-list>
        </div>
        <b-button @click="modify">수정</b-button>
      </b-media>
    </b-card>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
import axios from "@/api/http-common";
import SidoList from "@/components/item/SidoList.vue";

const userStore = "userStore";
export default {
  name: "UserMyPage",
  components: {
    SidoList,
  },
  data() {
    return {
      user: Object,
      sidos: [],
      emailChk: false,
      emailAuthorize: false,
      code: 0,
      authorize: 0,
    };
  },
  created() {
    this.user = this.userInfo;
    axios.get("/tour/sido").then((res) => {
      this.sidos = res.data;
    });
    this.$emit("getTitle", "마이페이지");
  },
  computed: {
    ...mapState(userStore, ["isUpdate", "userInfo"]),
  },
  methods: {
    ...mapActions(userStore, ["userModifyInfo"]),
    async modify() {
      if (this.emailAuthorize) {
        let updateInfo = this.user;
        await this.userModifyInfo(updateInfo);
        if (this.isUpdate) {
          alert("성공");
          this.$router.back();
        }
      } else {
        alert("이메일 인증을 진행해주세요");
      }
    },
    selectSido(sido) {
      this.userInfo.sido = sido;
    },
    getCode() {
      let email = this.user.emailId + "@" + this.user.emailDomain;
      this.domainChk();
      if (this.emailChk) {
        axios
          .get(`/user/getcode`, {
            params: {
              email: email,
            },
          })
          .then(({ data }) => {
            this.code = data;
            alert("인증코드가 발급되었습니다");
          });
      } else {
        alert("이메일을 확인해주세요");
      }
    },
    domainChk() {
      let dot = ".";
      let count = this.user.emailDomain.split(dot).length - 1;
      if (this.user.emailId == "") {
        this.emailChk = false;
      } else {
        if (count === 1) {
          if (this.user.emailDomain.indexOf(dot) != 0) {
            this.emailChk = true;
          } else {
            this.emailChk = false;
          }
        } else {
          this.emailChk = false;
        }
      }
    },
    chkCode() {
      if (this.authorize == this.code) {
        this.emailAuthorize = true;
        let btn = document.getElementById("chkCode");
        btn.innerText = "인증완료";
        btn.className = "btn btn-primary";
        btn.disabled = true;
      } else {
        alert("인증코드가 일치하지 않습니다");
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
