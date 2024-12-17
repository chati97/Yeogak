<template>
  <div class="place-view-main">
    <h2 class="my-2">{{ placeData.title }}</h2>
    <b-container class="place-view-content">
      <b-row>
        <b-col cols="6">
          <!-- 장소의 정보들 -->
          <b-table stacked :items="items" :fields="fields" style="text-align: left">
            <template #cell(addr)="data"> {{ data.item.addr1 }}{{ data.item.addr2 }} </template>
          </b-table>
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
        </b-col>
        <b-col cols="6">
          <!-- 지도랑 이미지-->
          <b-card class="map-container my-2">
            <kakao-map
              :mainMarker="placeData"
              :selected="null"
              :attList="[]"
              :planList="[]"
            ></kakao-map>
          </b-card>
          <b-card v-if="placeData.firstImage">
            <img :src="placeData.firstImage" class="place-first-image" />
          </b-card>
          <b-card v-else>
            <img :src="noContent" class="place-no-image" />
            <p>대표 이미지 없음</p>
          </b-card>
        </b-col>
      </b-row>
      <b-row>
        <!-- 리뷰들 -->
        <b-card
          v-if="placeData.reviewList == null || placeData.reviewList.length == 0"
          class="review-card"
        >
          <p>아직 작성된 리뷰가 없습니다!</p>
          <p>지금 첫 리뷰 작성자가 되어보세요!</p>
          <place-my-review
            :myReview="myReview"
            @writeReview="writeReview"
            @click="loginCheck"
          ></place-my-review>
        </b-card>
        <b-card v-else class="review-card">
          <place-my-review
            :myReview="myReview"
            @writeReview="writeReview"
            @click="loginCheck"
            @updateReview="updateReview"
            @deleteReview="deleteReview"
          ></place-my-review>
          <place-review-item
            v-for="(reviewData, index) in reviewWithContentList"
            :key="index"
            :reviewData="reviewData"
          ></place-review-item>
        </b-card>
      </b-row>
    </b-container>
    <login-confirm-modal></login-confirm-modal>
  </div>
</template>

<script>
import axios from "@/api/http-common";
import LoginConfirmModal from "@/components/item/LoginConfirmModal";
import KakaoMap from "@/components/item/KakaoMap";
import PlaceReviewItem from "./items/PlaceReviewItem";
import PlaceMyReview from "./items/PlaceMyReview";
import { mapState, mapGetters } from "vuex";

const userStore = "userStore";

export default {
  name: "PlaceView",
  components: {
    KakaoMap,
    PlaceReviewItem,
    LoginConfirmModal,
    PlaceMyReview,
  },
  data() {
    return {
      contentId: 0,
      placeData: {},
      noContent: "",
      rating: 0,
      title: "",
      content: "",
      myReview: null,
      items: [],
      fields: [
        { key: "title", label: "관광지명" },
        { key: "addr", label: "주소" },
        { key: "tel", label: "전화번호" },
        { key: "overview", label: "설명" },
        { key: "ratingAvg", label: "총 평균 평점" },
        { key: "homepage", label: "홈페이지" },
      ],
      idxList: [0, 1, 2, 3, 4, 5],
      labelList: ["10대 이하", "20대", "30대", "40대", "50대", "60대 이상"],
      maleRating: [],
      femaleRating: [],
    };
  },
  computed: {
    ...mapState(userStore, ["isLogin", "userInfo"]),
    ...mapGetters(["checkUserInfo"]),
    reviewWithContentList() {
      let list = [];
      console.log(this.placeData);
      this.placeData.reviewList.forEach((element) => {
        if (element.title != null && element.content != null && element.title.length && element.content.length) {
          if (element.userId != this.userInfo.userId) {
            list.push(element);
          }
        }
        if (element.userId != null && element.userId == this.userInfo.userId) {
          this.myReview = element;
        }
      });
      return list;
    },
  },
  watch: {
    placeData() {
      this.maleRating = [];
      this.femaleRating = [];
      for (let i = 0; i < 6; i++) {
        let temp = this.placeData.maleRating[1][i] / this.placeData.maleRating[0][i];
        if (isNaN(temp)) {
          temp = "평점 없음";
        } else {
          temp = temp.toFixed(2);
        }
        this.maleRating.push(temp);

        temp = this.placeData.femaleRating[1][i] / this.placeData.femaleRating[0][i];
        if (isNaN(temp)) {
          temp = "평점 없음";
        } else {
          temp = temp.toFixed(2);
        }
        this.femaleRating.push(temp);
      }
    },
  },
  created() {
    this.contentId = this.$route.params.contentId;
    axios.get(`/tour/place/${this.contentId}`).then(({ data }) => {
      this.placeData = data;
      if (this.placeData.tel == "") {
        this.placeData.tel = "정보 없음";
      }
      if (this.placeData.homepage == "") {
        this.placeData.homepage = "정보 없음";
      }
      this.items.push(this.placeData);
    });
    this.noContent = require("@/assets/noContent.png");
  },
  methods: {
    loginCheck() {
      if (!this.isLogin) {
        this.$bvModal.show("login-confirm-modal");
      }
    },
    reload() {
      axios.get(`/tour/place/${this.contentId}`).then(({ data }) => {
        this.placeData = data;
      });
    },
    writeReview(param) {
      param.userId = this.userInfo.userId;
      param.attractionId = this.contentId;
      axios.post(`/tour/review/write`, param).then(({ data }) => {
        if (data == "success") {
          this.reload();
        } else {
          alert("등록에 실패했습니다. 잠시 후 다시 시도해 주십시오.");
        }
      });
    },
    updateReview(param) {
      axios.put(`/tour/review/`, param).then(({ data }) => {
        if (data == "success") {
          this.reload();
        } else {
          alert("등록에 실패했습니다. 잠시 후 다시 시도해 주십시오.");
        }
      });
    },
    deleteReview(id) {
      axios.delete(`/tour/review/${id}`).then(() => {
        this.reload();
      });
    },
  },
};
</script>

<style>
/*.place-view-content {
  height: 65vh;
  overflow: auto;
  -ms-overflow-style: none;
  // IE and Edge
  scrollbar-width: none;
  // Firefox
}
.place-view-content::-webkit-scrollbar {
  display: none;
  // Chrome, Safari, Opera
}*/

.place-first-image {
  height: 300px;
}

.place-no-image {
  height: 250px;
}

.map-container {
  height: 300px;
}

.place-view-main {
  overflow: auto;
}

.review-card {
  width: 100%;
}
</style>
