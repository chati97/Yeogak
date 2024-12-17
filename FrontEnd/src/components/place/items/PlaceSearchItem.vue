<template>
  <b-card
    :title="attData.title"
    :img-src="attData.imgSrc"
    img-left
    style="max-height: 8rem"
    class="m-1"
    tag="article"
    @click="goToDetail"
  >
    <b-card-text>
      {{ attData.addr }}
    </b-card-text>
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
    goToDetail(){
      this.$router.push({ name: "PlaceView", params: { contentId: this.attData.contentId } });
    }
  },
};
</script>

<style>
.place-search-item .card-img-left {
  width: 8rem;
}

.place-search-item .card-title {
  font-size: 1.2rem;
  /* display: block; */
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
