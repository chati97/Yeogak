<template>
  <div>
    <b-row>
      <b-col>
        <kakao-map :planList="tourPlan" :selected="null" :attList="[]"></kakao-map>
      </b-col>
      <b-col>
        <b-form @submit.prevent="parameterCheck">
          <b-form-group id="title-group" label="제목:" label-for="title">
            <b-form-input
              id="title"
              type="text"
              placeholder="나만의 제목"
              v-model="title"
              required
            ></b-form-input>
          </b-form-group>

          <b-form-group id="context-group" label="설명:" label-for="input-2">
            <b-form-textarea
              id="context"
              rows="3"
              max-rows="8"
              placeholder="나만의 여행 계획 설명"
              v-model="content"
              no-auto-shrink
              required
              >{{ content }}</b-form-textarea
            >
          </b-form-group>

          <b-form-group id="start-time-group" label="시작일" label-for="start_time">
            <b-form-datepicker
              id="start-time"
              v-model="startTime"
              class="mb-2"
              label-help="시작일을 골라주세요"
            ></b-form-datepicker>
          </b-form-group>
          <b-form-group id="end-time-group" label="종료일" label-for="end_time">
            <b-form-datepicker
              id="end-time"
              v-model="endTime"
              class="mb-2"
              label-help="종료일을 골라주세요"
            ></b-form-datepicker>
          </b-form-group>

          <b-button type="submit" variant="primary">Submit</b-button>
        </b-form>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import axios from "@/api/http-common";
import KakaoMap from "@/components/item/KakaoMap";
import { mapState, mapGetters, mapActions } from "vuex";

const userStore = "userStore";
const tourStore = "tourStore";

export default {
  name: "TourPlanWrite",
  components: {
    KakaoMap,
  },
  data() {
    return {
      title: "",
      content: "",
      startTime: "",
      endTime: "",
      tourPlan: [],
    };
  },
  created() {
    if (!this.isLogin) {
      alert("로그인이 필요한 서비스 입니다!");
      this.$router.push({ name: "userLogin" });
    }
    if (!this.storedTourPlan || !this.storedTourPlan.length) {
      alert("여행계획지가 없습니다!!");
      this.$router.push({ name: "TourSearch" });
    }
    this.tourPlan = this.storedTourPlan;
  },
  computed: {
    ...mapState(userStore, ["isLogin", "userInfo"]),
    ...mapState(tourStore, ["storedTourPlan"]),
    ...mapGetters(["getStoredTourPlan"]),
  },
  methods: {
    ...mapActions(userStore, ["userLogout"]),
    ...mapActions(tourStore, ["setStoredTourPlan"]),
    parameterCheck() {
      let msg = "";
      if (!this.title) {
        msg = "제목을 입력 하십시오!";
      } else if (!msg && !this.content) {
        msg = "내용을 입력 하십시오!";
      } else if (!msg && !this.startTime) {
        msg = "시작일을 입력 하십시오!";
      } else if (!msg && !this.endTime) {
        msg = "종료일을 입력 하십시오!";
      } else if (this.startTime > this.endTime) {
        msg = "시작일은 종료일보다 빨라야 합니다";
      }

      if (!msg) {
        this.regist();
      } else {
        alert(msg);
      }
    },
    regist() {
      // 유저 아이디 넣어야 함

      let tourList = [];
      this.storedTourPlan.forEach((place) => {
        tourList.push(place.contentId);
      });
      let plan = {
        userId: this.userInfo.userId,
        title: this.title,
        content: this.content,
        tourList,
        startTime: this.startTime,
        endTime: this.endTime,
      };
      axios.post("/tour", plan).then(() => {
        this.setStoredTourPlan([]);
        this.$router.push({ name: "TourPlanList" });
      });
    },
  },
};
</script>

<style></style>
