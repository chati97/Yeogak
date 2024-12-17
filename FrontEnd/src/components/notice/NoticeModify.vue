<template>
  <div>
    <b-card>
      <b-media>
        <b-row>
          <b-col cols="2">
            <h3 class="mt-0">제목 :</h3>
          </b-col>
          <b-col cols="9" style="text-align: left">
            <b-input v-model="article.subject" />
          </b-col>
        </b-row>
        <h6>
          작성자 : {{ article.userId }}<br />
          조회수 : {{ article.hit }}
        </h6>
        <div class="text-left">
          <b-textarea v-model="article.content"></b-textarea>
        </div>
        <h6>작성일 : {{ article.registerTime }}</h6>
        <b-button @click="modifyArticle">수정</b-button>
        <b-button @click="deleteArticle">삭제</b-button>
      </b-media>
    </b-card>
  </div>
</template>

<script>
import axios from "@/api/http-common";
import { mapState, mapActions } from "vuex";

const userStore = "userStore";
export default {
  name: "NoticeModify",
  components: {},
  data() {
    return {
      article: Object,
    };
  },
  created() {
    axios.get(`/notice/view/${this.$route.params.id}`).then(({ data }) => {
      this.article = data;
    });
  },
  computed: {
    ...mapState(userStore, ["userInfo"]),
  },
  methods: {
    ...mapActions(userStore, ["getUserInfo"]),
    modifyArticle() {
      if (this.userInfo.userId == this.article.userId) {
        axios.put(`/notice/modify/${this.article.id}`, this.article).then(() => {
          this.$router.push({ name: "NoticeView", params: { id: this.article.id } });
        });
      } else {
        alert("작성자가 아닙니다");
        this.$router.push({ name: "NoticeList" });
      }
    },
    deleteArticle() {
      if (this.userInfo.userId == this.article.userId) {
        axios.delete(`/notice/delete/${this.article.id}`).then(() => {
          this.$router.push({ name: "NoticeList" });
        });
      } else {
        alert("작성자가 아닙니다");
        this.$router.push({ name: "NoticeList" });
      }
    },
  },
};
</script>

<style scoped>
h6 {
  text-align: right;
}
</style>
