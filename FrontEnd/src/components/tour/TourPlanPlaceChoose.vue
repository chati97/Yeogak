<template>
  <b-container class="tour-plan-place-choose-container">
    <b-row class="tour-plan-place-choose-main-row">
      <b-col class="tour-plan-place-choose-main-col" cols="4">
        <b-container>
          <form id="searchForm" role="search" @submit.prevent="">
            <b-row class="m-2">
              <b-col cols="6">
                <sido-list @select-sido="selectSido"></sido-list>
              </b-col>
              <b-col cols="6">
                <b-form-select
                  id="gugunCode"
                  name="gugunCode"
                  class="form-select me-2"
                  v-model="gugunCode"
                >
                  <option value="0" selected>구/군 선택</option>
                  <option v-for="gugun in guguns" :key="gugun.gugunCode" :value="gugun.gugunCode">
                    {{ gugun.gugunName }}
                  </option>
                </b-form-select>
              </b-col>
            </b-row>
            <b-row class="m-2">
              <b-col cols="12">
                <input
                  id="search-keyword"
                  name="title"
                  class="form-control"
                  type="search"
                  placeholder="검색어"
                  aria-label="검색어"
                  v-model="word"
                  @keyup.enter="getlistPlace"
                />
              </b-col>
            </b-row>
            <b-row class="m-2 d-flex justify-content-center">
              <b-button-group>
                <button id="btn-search" class="btn btn-success" type="button" @click="getlistPlace">
                  검색
                </button>
                <b-button v-b-modal.attration-type-modal class="btn btn-info" type="button">
                  추가조건
                </b-button>
                <b-button v-b-toggle.plan-side-bar class="btn btn-info" type="button">
                  여행계획보기
                </b-button>
              </b-button-group>
            </b-row>

            <place-search-attraction-type-modal
              :props-types="selectedAttTypes"
              @send-selected="sendSelected"
            ></place-search-attraction-type-modal>
          </form>
        </b-container>
        <img id="no-content" src="@/assets/noContent.png" hidden />
        <div id="placeList" class="place-search-list" v-if="afterSearch">
          <b-card class="place-list-card" v-if="placeList.length" style="max-height: 50vh">
            <place-search-item
              v-for="attData in placeList"
              :attData="attData"
              :key="attData.contentId"
              class="place-search-item"
              @show-modal="showModal"
              @move-map="moveMap"
              @add-to-plan="addToPlan"
            ></place-search-item>
          </b-card>
          <b-card
            class="place-list-card p-5"
            v-else
            title="검색 결과 없음"
            :img-src="noContentPath"
            img-top
          >
            <b-card-text> 검색조건을 바꿔 다시 검색해주세요. </b-card-text>
          </b-card>
        </div>
      </b-col>
      <b-col cols="8">
        <div
          class="container position-relative algin-items-center"
          style="height: 100%; max-height: 70vh"
        >
          <img id="noImage" src="@/assets/noImage.png" hidden />
          <kakao-map
            :attList="placeList"
            :selected="attData"
            :planList="tourPlan"
            :mainMarker="null"
          ></kakao-map>
        </div>
      </b-col>
    </b-row>
    <login-confirm-modal></login-confirm-modal>
    <place-detail-modal :attData="attData" @add-to-plan="addToPlan"></place-detail-modal>
    <b-modal
      id="plan-regist-confirm-modal"
      title="여행 계획 등록하기"
      ok-title="예"
      cancel-title="아니오"
      @ok="goToRegistPlan"
    >
      여행계획 등록 페이지로 이동하시겠습니까?
    </b-modal>
    <plan-side-bar
      :tourPlan="tourPlan"
      @deleteItem="deletePlanItem"
      @registPlan="$bvModal.show('plan-regist-confirm-modal')"
    ></plan-side-bar>
  </b-container>
</template>

<script>
import axios from "@/api/http-common";
import deepCopy from "@/api/deepCopy";
import KakaoMap from "@/components/item/KakaoMap";
import SidoList from "@/components/item/SidoList";
import LoginConfirmModal from "@/components/item/LoginConfirmModal";
import PlaceDetailModal from "./item/PlaceDetailModal";
import PlanSideBar from "./item/PlanSideBar";
import PlaceSearchAttractionTypeModal from "@/components/item/PlaceSearchAttractionTypeModal";
import PlaceSearchItem from "./item/PlaceSearchItem";
import { mapState, mapGetters, mapActions } from "vuex";

const userStore = "userStore";
const tourStore = "tourStore";

export default {
  name: "TourPlanPlaceChoose",
  components: {
    KakaoMap,
    SidoList,
    LoginConfirmModal,
    PlaceDetailModal,
    PlanSideBar,
    PlaceSearchAttractionTypeModal,
    PlaceSearchItem,
  },
  data() {
    return {
      pg: 1,
      spp: 10,
      start: 1,
      sidoCode: "",
      gugunCode: 0,
      selectedAttTypes: [],
      word: "",
      sidos: [],
      guguns: [],
      placeList: [],
      tourPlanCount: 0,
      tourPlan: [],
      afterSearch: false,
      attData: {},
      noImagePath: "",
      noContentPath: "",
    };
  },
  created() {
    this.tourPlan = this.storedTourPlan;
    let len = this.storedTourPlan.length;
    if (len == 0) {
      this.tourPlanCount = 0;
    } else {
      this.tourPlanCount = this.storedTourPlan[len - 1].id + 1;
    }
  },
  computed: {
    ...mapState(userStore, ["isLogin", "userInfo"]),
    ...mapState(tourStore, ["storedTourPlan"]),
    ...mapGetters(["checkUserInfo"]),
    ...mapGetters(["getStoredTourPlan"]),
  },
  mounted() {
    this.noImagePath = document.getElementById("noImage").src;
    this.noContentPath = document.getElementById("no-content").src;
  },
  watch: {
    sidoCode() {
      axios.get(`/tour/gugun/${this.sidoCode}`).then((res) => {
        this.guguns = res.data;
      });
    },
  },
  methods: {
    ...mapActions(userStore, ["userLogout"]),
    ...mapActions(tourStore, ["setStoredTourPlan"]),
    selectSido(sidoCode) {
      this.sidoCode = sidoCode;
    },
    getlistPlace() {
      let param = {
        sidoCode: this.sidoCode,
      };
      if (this.gugunCode) {
        param.gugunCode = this.gugunCode;
      }
      if (this.pg != 1) {
        param.pg = this.pg;
      }
      if (this.spp != 30) {
        param.spp = this.spp;
      }
      if (this.start != 1) {
        param.start = this.start;
      }
      if (this.selectedAttTypes.length) {
        let selected = "";
        this.selectedAttTypes.forEach((type) => {
          selected += type + ",";
        });
        param.selected = selected;
      }
      if (this.word) {
        param.word = this.word;
      }

      axios
        .get(`/tour`, {
          params: param,
        })
        .then((res) => {
          this.afterSearch = true;
          let data = res.data;
          if (data) {
            let idx = 0;
            this.placeList = data;
            this.placeList.forEach((data) => {
              data.idx = idx++;
              data.imgSrc = data.firstImage ? data.firstImage : this.noImagePath;
              data.addr = data.addr1 + " " + data.addr2;
            });
          } else {
            this.placeList = [];
          }
        });
    },
    sendSelected(selected) {
      this.selectedAttTypes = selected;
    },
    showModal(idx) {
      this.attData = this.placeList[idx];
      this.moveMap(idx);
      this.$bvModal.show("attr-detail");
    },
    moveMap(idx) {
      this.attData = this.placeList[idx];
    },
    addToPlan(idx) {
      if (!this.isLogin) {
        this.$bvModal.show("login-confirm-modal");
      }
      let flag = true;
      this.tourPlan.forEach((item) => {
        if (item.contentId == this.placeList[idx].contentId) {
          console.log("이미 계획에 포함되어 있습니다");
          flag = false;
        }
      });
      if (flag) {
        let planItem = deepCopy(this.placeList[idx]);
        planItem.id = this.tourPlanCount++;
        this.tourPlan.push(planItem);
        this.setStoredTourPlan(this.tourPlan);
      }
    },
    deletePlanItem(idx) {
      this.tourPlan.splice(idx, 1);
      let i = 0;
      this.tourPlan.forEach((plan) => {
        plan.idx = i++;
      });
      this.setStoredTourPlan(this.tourPlan);
    },
    goToRegistPlan() {
      this.$router.push({ name: "TourPlanWrite" });
    },
  },
};
</script>

<style scoped>
.tour-plan-place-choose-container,
.tour-plan-place-choose-main-row {
  /* height: 100%; */
  height: 75vh;
  padding: 1%;
}

.tour-plan-place-choose-main-col {
  height: 100%;
}

.place-search-list {
  max-height: 80%;
  height: auto;
  overflow: auto;
}

.place-list-card > .card-body {
  margin: 1px;
  padding: 2px;
}

#no-content {
  width: 100px;
}
</style>
