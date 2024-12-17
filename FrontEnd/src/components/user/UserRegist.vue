<template>
  <div id="test">
    <b-card>
      <b-row>
        <b-col sm="12">
          <br />
        </b-col>
        <b-col sm="3">
          <label label-for="userId">아이디:</label>
        </b-col>
        <b-col sm="4">
          <b-form-input id="userId" v-model.lazy="userId" @change="resetIdCheck" />
        </b-col>
        <b-col sm="5" align="left">
          <b-button @click="idCheck">아이디 중복 확인</b-button>
        </b-col>
        <b-col sm="12">
          <br />
        </b-col>
        <b-col sm="3"></b-col>
        <b-col sm="9" align="left">
          <span id="idCheckSpan">
            {{ idCheckMsg }}
          </span>
        </b-col>
        <b-col sm="12">
          <br />
        </b-col>
        <b-col sm="3">
          <label label-for="userPw">비밀번호:</label>
        </b-col>
        <b-col sm="4">
          <b-form-input type="password" id="userPw" v-model="userPw" @change="pwCheck" />
        </b-col>
        <b-col sm="12">
          <br />
        </b-col>
        <b-col sm="3">
          <label label-for="userPw">비밀번호확인:</label>
        </b-col>
        <b-col sm="4">
          <b-form-input type="password" id="userPwCheck" v-model="userPwCheck" @change="pwCheck" />
        </b-col>
        <b-col sm="4" align="left">
          <span id="pwCheckSpan">
            {{ pwCheckMsg }}
          </span>
        </b-col>
        <b-col sm="12">
          <br />
        </b-col>
        <b-col sm="3">
          <label label-for="name">이름:</label>
        </b-col>
        <b-col sm="4">
          <b-form-input id="name" v-model="name" />
        </b-col>
        <b-col sm="12">
          <br />
        </b-col>
        <b-col sm="3">
          <label label-for="name">닉네임:</label>
        </b-col>
        <b-col sm="4">
          <b-form-input id="name" v-model="nickname" @change="nickCheck" />
        </b-col>
        <b-col sm="4">
          <span id="nickCheckSpan">
            {{ nickCheckMsg }}
          </span>
        </b-col>
        <b-col sm="12">
          <br />
        </b-col>
        <b-col sm="3">
          <label label-for="emailId">이메일:</label>
        </b-col>
        <b-col sm="2">
          <b-form-input id="emailId" v-model="emailId" />
        </b-col>
        @
        <b-col sm="2">
          <b-form-input id="emailDomain" v-model="emailDomain" @change="domainChk" />
        </b-col>
        <b-col sm="3">
          <b-button @click="getCode">인증번호 받기</b-button>
        </b-col>
        <b-col sm="12">
          <br />
        </b-col>
        <b-col sm="3">
          <label label-for="authorize">인증번호:</label>
        </b-col>
        <b-col sm="4">
          <b-form-input id="authorize" v-model="authorize" />
        </b-col>
        <b-col sm="3">
          <b-button @click="chkCode" id="chkCode">인증</b-button>
        </b-col>
        <b-col sm="12">
          <br />
        </b-col>
        <b-col sm="3">
          <label label-for="sido">시/도:</label>
        </b-col>
        <b-col sm="4">
          <sido-list @select-sido="selectSido"></sido-list>
        </b-col>
        <b-col sm="12">
          <br />
        </b-col>
        <b-col sm="3">
          <label label-for="birth-year">생년:</label>
        </b-col>
        <b-col sm="4">
          <b-form-input id="birth-year" v-model="birthYear" />
        </b-col>
        <b-col sm="12">
          <br />
        </b-col>
        <b-col sm="3">
          <label label-for="sex">성별 :</label>
        </b-col>
        <b-col sm="9">
          <b-form-group v-slot="{ ariaDescribedby }" style="align-items: inline; text-align: left">
            <b-form-radio-group
              id="sex"
              v-model="sex"
              :options="options"
              :aria-describedby="ariaDescribedby"
              name="plain-inline"
            ></b-form-radio-group>
          </b-form-group>
        </b-col>
      </b-row>
      <b-row class="m-2 mt-4">
        <b-col />
        <b-col>
          <b-alert v-model="showDismissibleAlert" variant="danger">
            <p>{{ alertMsg }}</p>
          </b-alert>
        </b-col>
        <b-col />
      </b-row>
      <b-button type="button" variant="success" class="m-1" @click="userJoin">등록</b-button>
    </b-card>
  </div>
</template>

<script>
import axios from "@/api/http-common";
import SidoList from "@/components/item/SidoList.vue";

export default {
  name: "UserRegist",
  components: {
    SidoList,
  },
  data() {
    return {
      userId: "",
      idChecked: false,
      idCheckMsg: "아이디 중복 확인을 눌러주십시오",

      userPw: "",
      userPwCheck: "",
      pwCheckMsg: "",

      name: "",
      nickname: "",
      nickCheckMsg: "",

      emailId: "",
      emailDomain: "",
      code: 0,
      authorize: 0,
      domainCheck: "",
      domainChkMsg: "",
      emailChk: false,
      emailAuthorize: false,

      sido: "",
      birthYear: 0,
      sex: "",
      showDismissibleAlert: false,
      alertMsg: "",
      options: [
        { text: "남성", value: "남성" },
        { text: "여성", value: "여성" },
      ],
    };
  },
  created() {
    this.$emit("getTitle", "회원가입");
  },
  methods: {
    selectSido(sido) {
      this.sido = sido;
    },
    idCheck() {
      let idCheckSpan = document.getElementById("idCheckSpan");
      axios.get(`/user/idCheck/${this.userId}`).then(({ data }) => {
        if (data == "duplicated") {
          this.idCheckMsg = "사용할 수 없는 아이디입니다";
          idCheckSpan.classList.add("text-danger");
        } else if (data == "ok") {
          this.idCheckMsg = "사용할 수 있는 아이디입니다!";
          this.idChecked = true;
          idCheckSpan.classList.add("text-success");
        }
      });
    },
    nickCheck() {
      let nickCheckSpan = document.getElementById("nickCheckSpan");
      axios.get(`/user/nickCheck/${this.nickname}`).then(({ data }) => {
        if (data == "duplicated") {
          this.nickCheckMsg = "사용할 수 없는 별명입니다";
          nickCheckSpan.classList.add("text-danger");
        } else {
          this.nickCheckMsg = "사용할 수 있는 별명입니다";
          nickCheckSpan.classList.add("text-success");
        }
      });
    },
    pwCheck() {
      if (this.userPwCheck == "") {
        return;
      }
      let pwCheckSpan = document.getElementById("pwCheckSpan");
      if (this.userPw == this.userPwCheck) {
        pwCheckSpan.className = "text-success";
        this.pwCheckMsg = "비밀번호가 일치합니다.";
      } else {
        pwCheckSpan.className = "text-danger";
        this.pwCheckMsg = "비밀번호가 일치하지 않습니다.";
      }
    },
    domainChk() {
      let dot = ".";
      let count = this.emailDomain.split(dot).length - 1;
      if (this.emailId == "") {
        this.emailChk = false;
      } else {
        if (count === 1) {
          if (this.emailDomain.indexOf(dot) != 0) {
            this.emailChk = true;
          } else {
            this.emailChk = false;
          }
        } else {
          this.emailChk = false;
        }
      }
    },
    resetIdCheck() {
      this.idChecked = false;
      this.idCheckMsg = "아이디 중복 확인을 눌러주십시오";
      document.getElementById("idCheckSpan").classList.remove("text-danger", "text-success");
    },
    userJoin() {
      if (!this.idChecked) {
        this.showAlert(this.idCheckMsg);
        return;
      }
      if (!this.emailAuthorize) {
        this.showAlert("이메일 인증이 완료되지 않았습니다.");
        return;
      }
      if (this.userPw != this.userPwCheck) {
        this.showAlert("비밀번호가 일치하지 않습니다.");
        return;
      }
      if (!this.userPw) {
        this.showAlert("비밀번호를 입력해주십시오");
        return;
      }
      if (!this.name) {
        this.showAlert("이름을 입력해주십시오");
        return;
      }
      if (!this.emailDomain || !this.emailId) {
        this.showAlert("이메일을 입력해주십시오");
        return;
      }
      if (!this.sido) {
        this.showAlert("사용자의 지역을 입력해주십시오");
        return;
      }
      let user = {
        userId: this.userId,
        userPw: this.userPw,
        name: this.name,
        nickname: this.nickname,
        emailId: this.emailId,
        emailDomain: this.emailDomain,
        sido: this.sido,
        birthYear: this.birthYear,
        sex: this.sex,
      };
      axios
        .post(`/user/join`, user)
        .then(() => {
          alert("등록 성공");
          this.$router.push({ name: "UserLogin" });
        })
        .catch(() => {
          this.showAlert("등록 실패");
        });
    },
    showAlert(msg) {
      this.showDismissibleAlert = true;
      this.alertMsg = msg;
    },
    getCode() {
      let email = this.emailId + "@" + this.emailDomain;
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

<style scoped></style>
