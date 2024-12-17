<template>
  <b-card
    :title="attData.title"
    :img-src="attData.imgSrc"
    img-left
    style="min-height: 10rem"
    class="m-1"
    tag="article"
    @click="moveMap"
  >
    <b-card-text>
      {{ attData.addr }}
    </b-card-text>
    <b-button @click.stop="showModal">간략 정보</b-button>
    <b-button :id="btnId" @click.stop="addToPlan">여행계획에 추가</b-button>
  </b-card>
</template>

<script>
export default {
  name: "TourSearchItem",
  data() {
    return {
      show: false,
      btnId: `add-button-${this.attData.idx}`,
    };
  },
  props: {
    attData: Object,
  },
  methods: {
    showModal() {
      this.$emit("show-modal", this.attData.idx);
    },
    moveMap() {
      this.$emit("move-map", this.attData.idx);
    },
    addToPlan() {
      this.$emit("add-to-plan", this.attData.idx);
      this.show = true;
      setTimeout(() => (this.show = false), 2000);
    },
  },
};
</script>

<style>
.place-search-item .card-img-left {
  width: 8rem;
}

.place-search-item .card-title {
  font-size: 1.2rem;
}

.place-search-item p {
  font-size: 0.6rem;
  margin-bottom: 10px;
}

.place-search-item .btn {
  font-size: 0.7px;
  padding: 4px;
  margin: 2px;
}
</style>
