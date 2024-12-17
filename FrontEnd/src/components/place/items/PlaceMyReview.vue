<template>
  <b-card @click="sendClick">
    <b-form v-if="myReview == null" @submit.prevent="onSubmit">
      <b-form-group description="점수만으로도 리뷰를 작성하실 수 있습니다.">
        <b-form-rating
          v-model="rating"
          variant="warning"
          class="mb-2"
        ></b-form-rating>
      </b-form-group>
      <b-form-group id="title">
        <b-form-input
          v-model="title"
          type="text"
          placeholder="제목"
        ></b-form-input>
      </b-form-group>
      <b-form-group id="title">
        <b-form-textarea
          v-model="content"
          placeholder="내용"
          rows="3"
          max-rows="6"
        ></b-form-textarea>
      </b-form-group>

      <b-button v-if="isLogin" type="submit" variant="primary"
        >리뷰 올리기</b-button
      >
      <b-button v-else type="submit" variant="primary" disabled
        >리뷰 올리기</b-button
      >
    </b-form>
    <b-form v-else>
      <b-form-rating
        v-model="rating"
        variant="warning"
        class="mb-2"
      ></b-form-rating>
      <b-row v-if="myReview.title != null">
        <h3>{{ myReview.title }}</h3>
      </b-row>
      <b-row v-if="myReview.content != null">
        {{ myReview.content }}
      </b-row>
      <b-button-group>
        <b-button v-if="!updateMode" @click="updateMode = !updateMode"
          >수정</b-button
        >
        <b-button v-else @click="updateReview">수정완료</b-button>
        <b-button v-if="!updateMode" @click="deleteReview">삭제</b-button>
        <b-button v-else @click="backToOriginal">취소</b-button>
      </b-button-group>
    </b-form>
  </b-card>
</template>

<script>
import { mapState, mapGetters } from "vuex";

const userStore = "userStore";

export default {
  name: "PlaceMyReview",
  data() {
    return {
      rating: 0,
      title: "",
      content: "",
      updateMode: false,
    };
  },
  props: {
    myReview: Object,
  },
  watch: {
    myReview() {
      if (this.myReview != null) {
        this.rating = this.myReview.rating;
      }
    },
  },
  created() {
    if (this.myReview != null) {
      this.rating = this.myReview.rating;
    }
  },
  computed: {
    ...mapState(userStore, ["isLogin", "userInfo"]),
    ...mapGetters(["checkUserInfo"]),
  },
  methods: {
    onSubmit() {
      let param = {
        rating: this.rating,
        title: this.title,
        content: this.content,
      };
      this.$emit("writeReview", param);
    },
    sendClick() {
      this.$emit("click");
    },
    updateReview() {
      let param = {
        id: this.myReview.id,
        rating: this.rating,
        title: this.title,
        content: this.content,
      };
      this.$emit("updateReview", param);
    },
    deleteReview(){
      this.$emit("deleteReview", this.myReview.id);
    },
    backToOriginal() {
      this.rating = this.myReview.rating;
      this.title = this.myReview.title;
      this.content = this.myReview.content;
      this.updateMode = !this.updateMode;
    },
  },
};
</script>

<style>
</style>