<template>
  <div>
    <b-card>
      <b-row>
        <b-col cols="2">
          <h3 class="mt-0">제목 :</h3>
        </b-col>
        <b-col cols="9" style="text-align: left">
          <h3>{{ article.subject }}</h3>
        </b-col>
      </b-row>
      <h6>
        작성자 : {{ article.nickname }}<br />
        조회수 : {{ article.hit }}
      </h6>
      <div class="text-left">
        <p id="content" style="border-radius: 5px">
          {{ article.content }}
        </p>
      </div>
      <h6>작성일 : {{ article.registerTime }}</h6>
      <b-button v-if="userInfo && userInfo.type == 1" @click="modifyArticle">수정</b-button>
      <b-button @click="moveList">목록으로</b-button>
    </b-card>
  </div>
</template>

<script>
import axios from "@/api/http-common";
import { mapState, mapActions } from "vuex";

const userStore = "userStore";
export default {
  name: "NoticeView",
  components: {},
  data() {
    return {
      article: Object,
    };
  },
  created() {
    axios.get(`/notice/view/${this.$route.params.id}`).then(({ data }) => {
      this.article = data;
      console.log(data);
    });
  },
  computed: {
    ...mapState(userStore, ["userInfo"]),
  },
  methods: {
    ...mapActions(userStore, ["getUserInfo"]),
    modifyArticle() {
      if (this.userInfo.userId == this.article.userId) {
        this.$router.push({ name: "NoticeModify", params: { id: this.article.id } });
      } else {
        alert("작성자가 아닙니다");
        this.$router.push({ name: "NoticeList" });
      }
    },
    moveList() {
      this.$router.push({ name: "NoticeList" });
    },
  },
};
</script>

<style scoped>
h6 {
  text-align: right;
}
</style>
