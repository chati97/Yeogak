<template>
  <div id="map" style="width: 100%; height: 100%"></div>
</template>

<script>
export default {
  name: "KakaoMap",
  components: {},
  data() {
    return {
      map: null,
      line: null,
      markers: [],
      planMarkers: [],
      redMarkerImgSrc: "",
    };
  },
  props: {
    selected: Object,
    attList: [],
    planList: [],
    mainMarker: Object,
  },
  watch: {
    attList() {
      this.loadMarker();
    },
    planList() {
      this.loadMarker();
    },
    selected() {
      this.moveCenter(this.selected.latitude, this.selected.longitude);
    },
    map() {
      if (this.mainMarker != null) {
        this.simpleMapInit();
      }
    },
    mainMarker() {
      if (this.map != null && this.mainMarker != null) {
        this.simpleMapInit();
      }
    },
  },
  computed: {
    positions() {
      let list = [];
      if (this.attList == null || this.attList.length == 0 || this.map == null) {
        return [];
      } else {
        this.attList.forEach((att) => {
          let obj = {};
          obj.title = att.title;
          obj.latlng = new kakao.maps.LatLng(att.latitude, att.longitude);
          list.push(obj);
        });
        return list;
      }
    },
    planPositions() {
      let list = [];
      if (this.planList == null || this.planList.length == 0 || this.map == null) {
        return [];
      } else {
        this.planList.forEach((plan) => {
          let obj = {};
          obj.title = plan.title;
          obj.latlng = new kakao.maps.LatLng(plan.latitude, plan.longitude);
          list.push(obj);
        });
        return list;
      }
    },
    planPositionsLatLng() {
      let list = [];
      this.planPositions.forEach((plan) => {
        list.push(plan.latlng);
      });
      return list;
    },
  },
  created() {},
  async mounted() {
    // api 스크립트 소스 불러오기 및 지도 출력
    if (window.kakao && window.kakao.maps) {
      await this.loadMap();
    } else {
      await this.loadScript();
    }
  },
  methods: {
    // api 불러오기
    loadScript() {
      const script = document.createElement("script");
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?appkey=" +
        process.env.VUE_APP_KAKAO_MAP_API_KEY +
        "&autoload=false";
      /* global kakao */
      script.onload = () => window.kakao.maps.load(this.loadMap);

      document.head.appendChild(script);
    },
    // 맵 출력하기
    loadMap() {
      const container = document.getElementById("map");
      const options = {
        center: new window.kakao.maps.LatLng(33.450701, 126.570667),
        level: 3,
      };

      this.map = new window.kakao.maps.Map(container, options);
      this.loadMarker();
    },
    // 지도의 중심 옮기기
    moveCenter(lat, lng) {
      this.map.setCenter(new kakao.maps.LatLng(lat, lng));
      this.map.setLevel(3);
    },
    // 지정한 위치에 마커 불러오기
    loadMarker() {
      // 현재 표시되어있는 marker들이 있다면 marker에 등록된 map을 없애준다.
      this.deleteMarker();

      // 마커를 생성합니다
      this.markers = [];
      if (this.positions != null) {
        this.positions.forEach((position) => {
          const marker = new kakao.maps.Marker({
            map: this.map, // 마커를 표시할 지도
            position: position.latlng, // 마커를 표시할 위치
            title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
            // image: markerImage, // 마커의 이미지
          });
          this.markers.push(marker);
        });
        // console.log("마커수 ::: " + this.markers.length);

        // 4. 지도를 이동시켜주기
        // 배열.reduce( (누적값, 현재값, 인덱스, 요소)=>{ return 결과값}, 초기값);
        if (this.positions.length > 0) {
          const bounds = this.positions.reduce(
            (bounds, position) => bounds.extend(position.latlng),
            new kakao.maps.LatLngBounds()
          );
          this.map.setBounds(bounds);
        }
      }
      if (this.planPositions.length > 0) {
        this.addPlanMarker();
      }
    },
    addPlanMarker() {
      this.deleteMarker();
      // 마커 이미지를 생성합니다
      const imgSrc = require("@/assets/red-marker.png");
      // 마커 이미지의 이미지 크기 입니다
      const imgSize = new kakao.maps.Size(24, 35);
      const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize);
      // 마커를 생성합니다
      this.planMarkers = [];
      this.planPositions.forEach((position) => {
        const marker = new kakao.maps.Marker({
          map: this.map, // 마커를 표시할 지도
          position: position.latlng, // 마커를 표시할 위치
          title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
          image: markerImage, // 마커의 이미지
        });
        this.planMarkers.push(marker);
      });
      // console.log("추가된 마커수 ::: " + this.planMarkers.length);

      // 4. 지도를 이동시켜주기
      // 배열.reduce( (누적값, 현재값, 인덱스, 요소)=>{ return 결과값}, 초기값);
      if (this.planPositions.length > 0) {
        const bounds = this.planPositions.reduce(
          (bounds, position) => bounds.extend(position.latlng),
          new kakao.maps.LatLngBounds()
        );

        this.map.setBounds(bounds);
        this.drawLine();
      }
    },
    drawLine() {
      this.deleteClickLine();

      this.line = new kakao.maps.Polyline({
        map: this.map, // 선을 표시할 지도입니다
        // path: this.planPositions, // 선을 구성하는 좌표 배열입니다
        strokeWeight: 3, // 선의 두께입니다
        strokeColor: "#db4040", // 선의 색깔입니다
        strokeOpacity: 1, // 선의 불투명도입니다 0에서 1 사이값이며 0에 가까울수록 투명합니다
        strokeStyle: "solid", // 선의 스타일입니다
      });
      this.line.setPath(this.planPositionsLatLng);
    },
    // 관광지 정보 조회때 한곳의 정보면 보여주면 될때 사용
    simpleMapInit() {
      this.deleteMarker();
      // 마커를 생성합니다
      new kakao.maps.Marker({
        map: this.map, // 마커를 표시할 지도
        position: new kakao.maps.LatLng(this.mainMarker.latitude, this.mainMarker.longitude), // 마커를 표시할 위치
        title: this.mainMarker.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
      });
      this.moveCenter(this.mainMarker.latitude, this.mainMarker.longitude);
    },
    deleteMarker() {
      if (this.markers.length > 0) {
        this.markers.forEach((item) => {
          // console.log(item);
          item.setMap(null);
        });
      }
      if (this.planMarkers.length > 0) {
        this.planMarkers.forEach((item) => {
          item.setMap(null);
        });
      }
    },
    deleteClickLine() {
      if (this.line) {
        this.line.setMap(null);
        this.line = null;
      }
    },
  },
};
</script>

<style></style>
