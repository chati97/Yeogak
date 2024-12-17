<template>
  <div id="list">
    <b-carousel
      id="hotplace-carousel"
      controls
      indicators
      background="#ababab"
      class="text-center"
      style="text-shadow: 1px 1px 2px #333"
      @sliding-start="onSlideStart"
      @sliding-end="onSlideEnd"
    >
      <b-carousel-slide
        class="slide"
        v-for="place in top5List"
        :key="place.contentId"
        :caption="place.title"
        :img-src="place.firstImage"
      >
      </b-carousel-slide>
    </b-carousel>
  </div>
</template>

<script>
export default {
  name: "PlaceHotPlaceCarousel",
  data() {
    return {
      top5List: [],
      slide: 0,
      sliding: null,
    };
  },
  props: {
    hotPlaceList: [],
  },
  created() {
    this.top5List = this.hotPlaceList.slice(0, 5);
  },
  watch: {
    hotPlaceList() {
      this.top5List = this.hotPlaceList.slice(0, 5);
    },
  },
  methods: {
    onSlideStart() {
      this.sliding = true;
    },
    onSlideEnd() {
      this.sliding = false;
    },
  },
};
</script>

<style scoped>
#list {
  margin: auto;
  height: auto;
  width: auto;
  display: table;
}
.slide {
  height: 30vh;
  width: 25vw;
  object-fit: scale-down;
}
</style>
