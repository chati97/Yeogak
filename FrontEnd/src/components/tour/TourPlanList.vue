<template>
  <div class="text-center bg-white">
    <b-button :to="{ name: 'TourPlanPlaceChoose' }"> 여행계획 짜기 </b-button>

    <b-card v-if="planList.length">
      <tour-plan-list-item
        v-for="plan in planList"
        :key="plan.id"
        :plan="plan"
      ></tour-plan-list-item>
    </b-card>
    <b-card class="place-list-card p-5" v-else title="검색 결과 없음">
      <img id="no-content" src="@/assets/noContent.png" />
      <b-card-text> 아직 해당하는 조건의 여행 계획이 없습니다! </b-card-text>
    </b-card>
  </div>
</template>

<script>
import axios from "@/api/http-common";
import TourPlanListItem from "./item/TourPlanListItem";

export default {
  name: "TourPlanList",
  components: {
    TourPlanListItem,
  },
  data() {
    return {
      noContentPath: "",
      planList: [],
    };
  },
  created() {
    this.getPlanList();
  },
  mounted() {
    this.noContentPath = document.getElementById("no-content").src;
  },
  methods: {
    getPlanList() {
      let param = {};
      axios.get(`/tour/plan`, { params: param }).then((res) => {
        this.planList = res.data;
      });
    },
  },
};
</script>

<style>
#no-content {
  width: 100px;
}
</style>
