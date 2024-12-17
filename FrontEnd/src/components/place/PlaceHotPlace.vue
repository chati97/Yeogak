<template>
  <div id="tableBack">
    <b-container>
      <b-row>
        <b-col cols="6">
          <h2 class="my-2">최근 평점 높은 장소</h2>
          <place-hot-place-carousel
            :hotPlaceList="placeList"
            class="my-2"
          ></place-hot-place-carousel>
          <place-recent-review-list mx-2></place-recent-review-list>
        </b-col>
        <b-col cols="6">
          <place-search></place-search>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import axios from "@/api/http-common";
import PlaceHotPlaceCarousel from "./items/PlaceHotPlaceCarousel";
import PlaceRecentReviewList from "./items/PlaceRecentReviewList";
import PlaceSearch from "./items/PlaceSearch";

export default {
  name: "PlaceHotPlace",
  components: {
    PlaceHotPlaceCarousel,
    PlaceRecentReviewList,
    PlaceSearch,
  },
  data() {
    return {
      placeList: [],
    };
  },
  created() {
    this.getPlaceList();
  },
  methods: {
    getPlaceList() {
      let param = {};
      axios.get(`/tour`, { params: param }).then((res) => {
        console.log(res);
        this.placeList = res.data;
      });
    },
  },
};
</script>

<style>
#tableBack {
  background-color: white;
}
</style>
