<template>
  <b-card :title="reviewData.title">
    <b-card-text>작성자: {{ reviewData.nickname }}</b-card-text>
    <b-card-text>{{ reviewData.content }}</b-card-text>
    <b-row>
      <b-col cols="8"
        ><b-form-rating
          :value="reviewData.rating"
          variant="warning"
          class="mb-2"
          readonly
        ></b-form-rating
      ></b-col>
      <b-col cols="4">작성일: {{ reviewData.registerTime }}</b-col>
    </b-row>
  </b-card>
</template>

<script>
export default {
  name: "PlaceReviewItem",
  data() {
    return {
      rating: 0,
    };
  },
  props: {
    reviewData: Object,
  },
  created() {
    this.rating = this.reviewData.reting;
  },
};
</script>

<style></style>
