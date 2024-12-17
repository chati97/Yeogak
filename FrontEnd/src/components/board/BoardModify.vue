<template>
  <div>
    <b-card class="content">
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
  name: "BoardModify",
  components: {},
  data() {
    return {
      article: Object,
    };
  },
  created() {
    axios.get(`/board/view/${this.$route.params.id}`).then(({ data }) => {
      this.article = data;
      // console.log(this.article);
    });
  },
  computed: {
    ...mapState(userStore, ["userInfo"]),
  },
  methods: {
    ...mapActions(userStore, ["getUserInfo"]),
    modifyArticle() {
      if (this.userInfo.userId == this.article.userId) {
        console.log(this.article);
        axios.put(`/board/modify/${this.article.id}`, this.article).then(() => {
          console.log(this.article);
          this.$router.push({ name: "BoardView", params: { id: this.article.id } });
        });
      } else {
        alert("작성자가 아닙니다");
        this.$router.push({ name: "BoardList" });
      }
    },
    deleteArticle() {
      console.log("delete");
      if (this.userInfo.userId == this.article.userId) {
        axios.delete(`/board/delete/${this.article.id}`).then(() => {
          this.$router.push({ name: "BoardList" });
        });
      } else {
        alert("작성자가 아닙니다");
        this.$router.push({ name: "BoardList" });
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
