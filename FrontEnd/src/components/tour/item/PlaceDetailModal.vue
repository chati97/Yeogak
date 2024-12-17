<template>
  <b-modal
    id="attr-detail"
    title="관광지 정보 상세 조회"
    centered
    scrollable
    ok-title="여행계획에 등록"
    cancel-title="닫기"
    @ok="addToPlan"
  >
    <b-card :img-src="attData.imgSrc" img-top no-body>
      <b-card-body>
        <b-card-title>{{ attData.title }}</b-card-title>
        <b-card-sub-title class="mb-2">{{ attData.addr }}</b-card-sub-title>
        <b-card-text v-if="attData.tel">전화번호: {{ attData.tel }}</b-card-text>
        <a v-if="attData.homepage" :href="attData.homepage" class="card-link">홈페이지로</a>

        <!-- 평점 테이블 -->
        <table class="table table-sm">
          <thead>
            <tr>
              <th scope="col">남성</th>
              <th scope="col">연령대별 평균 평점</th>
              <th scope="col">여성</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="idx in this.idxList" :key="idx">
              <td>
                <b-icon icon="star" aria-hidden="true" variant="warning"></b-icon>
                {{ maleRating[idx] }} / 5
              </td>
              <th scope="row">{{ labelList[idx] }}</th>
              <td>
                <b-icon icon="star" aria-hidden="true" variant="warning"></b-icon>
                {{ femaleRating[idx] }} / 5
              </td>
            </tr>
          </tbody>
        </table>

        <b-button @click="goToDetail">상세보기 페이지로 이동</b-button>
      </b-card-body>
    </b-card>
  </b-modal>
</template>

<script>
import axios from "@/api/http-common";

export default {
  name: "TourDetailModal",
  data() {
    return {
      placeData: Object,
      idxList: [0, 1, 2, 3, 4, 5],
      labelList: ["10대 이하", "20대", "30대", "40대", "50대", "60대 이상"],
      maleRating: [],
      femaleRating: [],
    };
  },
  props: {
    attData: Object,
  },
  watch: {
    attData() {
      axios.get(`/tour/place/${this.attData.contentId}`).then(({ data }) => {
        this.placeData = data;
      });
    },
    placeData() {
      this.maleRating = [];
      this.femaleRating = [];
      for (let i = 0; i < 6; i++) {
        let temp = this.placeData.maleRating[1][i] / this.placeData.maleRating[0][i];
        if (isNaN(temp)) {
          temp = "평점 없음";
        }
        this.maleRating.push(temp);

        temp = this.placeData.femaleRating[1][i] / this.placeData.femaleRating[0][i];
        if (isNaN(temp)) {
          temp = "평점 없음";
        }
        this.femaleRating.push(temp);
      }
    },
  },
  methods: {
    addToPlan() {
      this.$emit("add-to-plan", this.attData.idx);
    },
    goToDetail() {
      this.$router.push({ name: "PlaceView", params: { contentId: this.attData.contentId } });
    },
  },
};
</script>

<style>
.table.table-sm {
  text-align: center;
}
</style>
