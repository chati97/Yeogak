<template>
  <div>
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
        <b-row class="m-2 d-flex justify-content-center" cols="3">
          <b-button-group>
            <button id="btn-search" class="btn btn-success" type="button" @click="getlistPlace">
              검색
            </button>
            <b-button v-b-modal.attration-type-modal class="btn btn-info" type="button">
              추가조건
            </b-button>
          </b-button-group>
        </b-row>

        <place-search-attraction-type-modal
          :props-types="selectedAttTypes"
          @send-selected="sendSelected"
        ></place-search-attraction-type-modal>
      </form>
    </b-container>
    <div id="placeList" class="place-search-list" v-if="afterSearch">
      <b-card class="place-list-card" v-if="placeList.length" style="max-height: 50vh">
        <place-search-item
          v-for="attData in placeList"
          :attData="attData"
          :key="attData.contentId"
          class="place-search-item"
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
    <div v-else class="place-search-list">
      <b-card class="place-list-card p-5" title="관광지 정보 검색">
        <b-card-text>
          검색조건을 정해 검색을 하시면 원하시는 데이터를 더욱 정확하게 얻으실 수 있습니다.
        </b-card-text>
      </b-card>
    </div>
  </div>
</template>

<script>
import axios from "@/api/http-common";
import SidoList from "@/components/item/SidoList";
import PlaceSearchAttractionTypeModal from "@/components/item/PlaceSearchAttractionTypeModal";
import PlaceSearchItem from "@/components/place/items/PlaceSearchItem";

export default {
  name: "PlaceSearch",
  components: {
    SidoList,
    PlaceSearchAttractionTypeModal,
    PlaceSearchItem,
  },
  data() {
    return {
      sidoCode: "",
      gugunCode: 0,
      selectedAttTypes: [],
      word: "",
      sidos: [],
      guguns: [],
      placeList: [],
      afterSearch: false,
      noContentPath: "",
    };
  },
  created() {
    this.noContentPath = require("@/assets/noContent.png");
  },
  methods: {
    selectSido(sidoCode) {
      this.sidoCode = sidoCode;
      axios.get(`/tour/gugun/${this.sidoCode}`).then((res) => {
        this.guguns = res.data;
      });
    },
    getlistPlace() {
      let param = {
        sidoCode: this.sidoCode,
      };
      if (this.gugunCode) {
        param.gugunCode = this.gugunCode;
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
      this.afterSearch = true;
    },
    sendSelected(selected) {
      this.selectedAttTypes = selected;
    },
  },
};
</script>

<style>
.place-list-card {
  overflow: auto;
}
</style>
