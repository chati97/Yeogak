<template>
  <b-card class="place-recent-review-list">
    <h4>최근 리뷰</h4>
    <ul class="list-group">
      <li v-for="reviewData in reviewList" :key="reviewData.id" class="list-group-item py-1">
        <b-row class="d-flex justify-content-between">
          <b-col cols="7">
            <router-link :to="`view/` + reviewData.attractionId">{{
              reviewData.title
            }}</router-link>
          </b-col>
          <b-col cols="5">
            <b-form-rating
              class="recent-review-list-rating"
              variant="warning"
              :value="reviewData.rating"
              show-value
              readonly
              inline
            ></b-form-rating>
          </b-col>
        </b-row>
      </li>
    </ul>

    <b-card-text v-if="reviewList.length == 0">아직 작성된 리뷰가 없습니다.</b-card-text>
  </b-card>
</template>

<script>
import axios from "@/api/http-common";

export default {
  name: "PlaceRecentReviewList",
  data() {
    return {
      reviewList: [],
    };
  },
  created() {
    axios.get(`/tour/review/recent`).then(({ data }) => {
      this.reviewList = data;
    });
  },
};
</script>

<style>
.place-recent-review-list {
  overflow: auto;
  height: 30vh;
}
</style>
