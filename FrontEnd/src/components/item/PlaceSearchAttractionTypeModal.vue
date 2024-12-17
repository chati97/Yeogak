<template>
  <b-modal
    id="attration-type-modal"
    title="추가조건 선택"
    ok-title="선택완료"
    cancel-title="취소"
    centered
    size="lg"
    @ok="sendSelected"
  >
    <p>검색할 관광지의 유형을 선택하세요</p>
    <p>선택하지 않으면 유형에 관계 없이 검색됩니다</p>
    <b-row cols-sm="1" cols-md="2" cols-lg="4" class="attType">
      <b-col v-for="attType in attractionTypes" :key="attType.value">
        <label class="mx-1" for="acttraction" @click="labelClicked(attType.value)">
          <input
            type="checkbox"
            :id="attType.id"
            name="attractionType"
            v-model="selectedAttTypes"
            :value="attType.value"
          />
          {{ attType.desc }}
        </label>
      </b-col>
    </b-row>
  </b-modal>
</template>

<script>
export default {
  name: "TourSearchAttracionTypeModal",
  data() {
    return {
      selectedAttTypes: [],
      attractionTypes: [
        {
          id: "attraction",
          value: 12,
          desc: "관광지",
        },
        {
          id: "culture",
          value: 14,
          desc: "문화시설",
        },
        {
          id: "festival",
          value: 15,
          desc: "축제공연행사",
        },
        {
          id: "course",
          value: 25,
          desc: "여행코스",
        },
        {
          id: "leiports",
          value: 28,
          desc: "레포츠",
        },
        {
          id: "lodgment",
          value: 32,
          desc: "숙박",
        },
        {
          id: "shopping",
          value: 38,
          desc: "쇼핑",
        },
        {
          id: "restaurant",
          value: 39,
          desc: "음식점",
        },
      ],
    };
  },
  props: {
    propsTypes: [],
  },
  created() {
    this.selectedAttTypes = this.propsTypes;
  },
  methods: {
    labelClicked(value) {
      if (this.selectedAttTypes.includes(value)) {
        this.selectedAttTypes.splice(this.selectedAttTypes.indexOf(value), 1);
      } else {
        this.selectedAttTypes.push(value);
      }
    },
    sendSelected() {
      this.$emit("send-selected", this.selectedAttTypes);
    },
  },
};
</script>

<style>
.attType {
  font-size: 1rem;
  padding: 5px;
}

.attType > * {
  padding: 5px;
}
</style>
