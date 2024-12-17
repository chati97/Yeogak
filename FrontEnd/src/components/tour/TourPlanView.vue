<template>
  <b-container>
    <b-row>
      <!-- 여기는 여행지 부분-->
      <b-col cols="6">
        <b-row class="map-div my-2">
          <kakao-map
            :selected="null"
            :attList="null"
            :planList="plan.planList"
            :mainMarker="null"
          ></kakao-map>
        </b-row>
        <b-card class="plan-view-card">
          <plan-view-item
            v-for="placeData in plan.planList"
            :key="placeData.contentId"
            :placeData="placeData"
          >
          </plan-view-item>
        </b-card>
      </b-col>
      <!-- 여기는 내용 부분-->
      <b-col>
        <b-row>
          <b-col> <span>제목: </span>{{ this.plan.title }} </b-col>
        </b-row>
        <b-row>
          <b-col> <span>작성자: </span>{{ this.plan.nickname }} </b-col>
          <b-col> <span>작성시간: </span>{{ this.plan.registerTime }} </b-col>
        </b-row>
        <b-row>
          <b-col> <span>시작일: </span>{{ this.plan.startTime }} </b-col>
          <b-col> <span>종료일: </span>{{ this.plan.endTime }} </b-col>
          <b-col> <span>조회수: </span>{{ this.plan.hit }} </b-col>
        </b-row>
        <b-row>
          <b-col>
            <b-form-textarea
              id="textarea"
              v-model="plan.content"
              rows="3"
              max-rows="6"
              readonly
            ></b-form-textarea>
          </b-col>
        </b-row>
        <b-row>
          <h3>후기</h3>
          <b-col v-if="plan.reviewContent == null">
            <b-form-textarea
              id="tour-plan-review-textarea"
              v-model="reviewContent"
              rows="3"
              max-rows="6"
            ></b-form-textarea>
          </b-col>
          <b-col v-else>
            <b-form-textarea
              id="tour-plan-review-textarea"
              v-model="plan.reviewContent"
              rows="3"
              max-rows="6"
              readonly
            ></b-form-textarea>
          </b-col>
        </b-row>
        <b-button-group>
          <b-button
            v-if="plan.reviewContent == null && userInfo.userId == plan.userId"
            @click="sendReview"
          >
            리뷰 작성</b-button
          >
          <b-button v-if="userInfo.userId == plan.userId" @click="modifyPlan">수정</b-button>
          <b-button v-if="userInfo.userId == plan.userId" @click="deletePlan">삭제</b-button>
          <b-button @click="backToList">목록으로</b-button>
        </b-button-group>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
import axios from "@/api/http-common";
import PlanViewItem from "./item/PlanViewItem.vue";
import KakaoMap from "../item/KakaoMap.vue";
import { mapState, mapGetters } from "vuex";

const userStore = "userStore";

export default {
  name: "TourPlanView",
  components: {
    PlanViewItem,
    KakaoMap,
  },
  data() {
    return {
      id: 0,
      reviewContent: "",
      plan: Object,
    };
  },
  created() {
    this.id = this.$route.params.id;
    axios.get(`/tour/plan/${this.id}`).then(({ data }) => {
      this.plan = data;
      let planList = [];
      this.plan.infoDtoList.forEach((element) => {
        let temp = {
          addr: element.addr1 + " " + element.addr2,
          contentId: element.contentId,
          imgSrc: element.firstImage,
          homepage: element.contentId,
          latitude: element.latitude,
          longitude: element.longitude,
          overview: element.overview,
          ratingAvg: element.ratingAvg,
          tel: element.tel,
          title: element.title,
        };
        planList.push(temp);
      });
      delete this.plan.infoDtoList;
      this.plan.planList = planList;
    });
  },
  computed: {
    ...mapState(userStore, ["userInfo"]),
    ...mapGetters(["checkUserInfo"]),
  },
  methods: {
    sendReview() {
      let reviewContent = {
        userId: this.userInfo.userId,
        reviewContent: this.reviewContent,
      };
      axios.post(`/tour/plan/review/${this.plan.id}`, reviewContent).then(() => {
        this.plan.reviewContent = this.reviewContent;
      });
    },
    modifyPlan() {
      this.$router.push({ name: "TourPlanModify", params: { id: this.plan.id } });
    },
    deletePlan() {
      axios.delete(`/tour/plan/${this.plan.id}/${this.userInfo.userId}`).then((res) => {
        if (res.data == "success") {
          alert("삭제 성공!");
          this.backToList();
        } else {
          alert("삭제 실패! 잠시 후 다시 시도해주세요");
        }
      });
    },
    backToList() {
      this.$router.push({ name: "TourPlanList" });
    },
  },
};
</script>

<style>
.map-div {
  height: 33vh;
}

.plan-view-card {
  height: 33vh;
  overflow: auto;
}
</style>
