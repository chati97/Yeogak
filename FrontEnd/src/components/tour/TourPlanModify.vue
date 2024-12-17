<template>
  <b-container id="tour-plan-modify-container" class="p-2">
    <b-row>
      <b-col>
        <b-form @submit.prevent="parameterCheck">
          <b-form-group
            id="tour-plan-modify-title-group"
            label="제목:"
            label-for="tour-plan-modify-title"
          >
            <b-form-input
              id="tour-plan-modify-title"
              type="text"
              v-model="planData.title"
              required
            ></b-form-input>
          </b-form-group>

          <b-row>
            <b-col>
              <b-form-group
                id="tour-plan-modify-start-time-group"
                label="시작일"
                label-for="tour-plan-modify-start_time"
              >
                <b-form-datepicker
                  id="tour-plan-modify-start-time"
                  v-model="planData.startTime"
                  class="mb-2"
                  label-help="시작일을 골라주세요"
                ></b-form-datepicker>
              </b-form-group>
            </b-col>
            <b-col>
              <b-form-group
                id="tour-plan-modify-end-time-group"
                label="종료일"
                label-for="tour-plan-modify-end_time"
              >
                <b-form-datepicker
                  id="tour-plan-modify-end-time"
                  v-model="planData.endTime"
                  class="mb-2"
                  label-help="종료일을 골라주세요"
                ></b-form-datepicker>
              </b-form-group>
            </b-col>
          </b-row>

          <b-form-group
            id="tour-plan-modify-content-group"
            label="설명:"
            label-for="tour-plan-modify-content"
          >
            <b-form-textarea
              id="tour-plan-modify-content"
              rows="3"
              max-rows="15"
              v-model="planData.content"
              no-auto-shrink
              required
            ></b-form-textarea>
          </b-form-group>

          <b-form-group
            v-if="planData.reviewContent != null"
            id="tour-plan-modify-review-content-group"
            label="후기:"
            label-for="tour-plan-modify-review-content"
          >
            <b-form-textarea
              id="tour-plan-modify-riview-content"
              rows="3"
              max-rows="8"
              v-model="planData.reviewContent"
              no-auto-shrink
              required
            ></b-form-textarea>
          </b-form-group>

          <b-button-group>
            <b-button type="submit">수정 완료</b-button>
            <b-button type="button" @click="backToPlan">취소</b-button>
          </b-button-group>
        </b-form>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import axios from "@/api/http-common";
import { mapState, mapGetters } from "vuex";

const userStore = "userStore";

export default {
  name: "TourPlanModify",
  data() {
    return {
      id: 0,
      planData: Object,
    };
  },
  created() {
    if (!this.isLogin) {
      alert("로그인이 필요한 서비스 입니다!");
      this.$router.push({ name: "userLogin" });
    }
    this.id = this.$route.params.id;
    axios.get(`/tour/plan/${this.id}`).then(({ data }) => {
      this.planData = data;
    });
  },
  computed: {
    ...mapState(userStore, ["isLogin", "userInfo"]),
    ...mapGetters(["checkUserInfo"]),
  },
  methods: {
    parameterCheck() {
      let msg = "";
      if (!this.planData.title) {
        msg = "제목을 입력 하십시오!";
      } else if (!msg && !this.planData.content) {
        msg = "내용을 입력 하십시오!";
      } else if (!msg && !this.planData.startTime) {
        msg = "시작일을 입력 하십시오!";
      } else if (!msg && !this.planData.endTime) {
        msg = "종료일을 입력 하십시오!";
      } else if (this.planData.startTime > this.planData.endTime) {
        msg = "시작일은 종료일보다 빨라야 합니다";
      }

      if (!msg) {
        this.modify();
      } else {
        alert(msg);
      }
    },
    modify() {
      // 유저 아이디 넣어야 함
      let plan = this.planData;
      plan.userId = this.userInfo.userId;
      axios.put(`/tour/plan/${plan.id}`, plan).then(() => {
        this.$router.push({ name: "TourPlanView", params: { id: plan.id } });
      });
    },
    backToPlan() {
      this.$router.push({ name: "TourPlanView", params: { id: this.planData.id } });
    },
  },
};
</script>

<style></style>
