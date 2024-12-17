<template>
  <div>
    <b-card class="content">
      <b-media>
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
          조회수 : {{ article.hit }}<br />
          좋아요 : {{ article.likes }}
        </h6>
        <div class="text-left">
          <p id="content" style="border-radius: 5px">
            {{ article.content }}
          </p>
        </div>
        <div style="text-align: right">
          <b-button @click="upLike" variant="primary">좋아요</b-button>
        </div>
        <h6>작성일 : {{ article.registerTime }}</h6>
        <p id="comment" style="text-align: left" v-if="article.commentList">
          <b-card v-for="(comment, index) in article.commentList" :key="index">
            <b-row class="mb-2">
              <b-col>{{ comment.nickname }}</b-col>
              <b-col>{{ comment.content }}</b-col>
            </b-row>

            <b-row class="mb-2" align="right">
              <b-col>{{ comment.registerTime }}</b-col>
            </b-row>
            <b-row class="mb-2" alig="right">
              <b-col>
                <b-button @click="deleteComment(comment)">삭제</b-button>
              </b-col>
            </b-row>
          </b-card>
        </p>
        <p id="comment" style="text-align: left">
          <b-card>
            <b-row class="mb-2">
              <b-col><b-textarea v-model="inputComment" /></b-col>
            </b-row>
            <div style="text-align: right">
              <b-button @click="writeComment()">작성</b-button>
            </div>
          </b-card>
        </p>
        <b-button @click="modifyArticle">수정</b-button>
        <b-button @click="moveList">목록으로</b-button>
      </b-media>
    </b-card>
  </div>
</template>

<script>
import axios from "@/api/http-common";
import { mapState, mapActions } from "vuex";

const userStore = "userStore";
export default {
  name: "BoardView",
  components: {},
  data() {
    return {
      article: Object,
      inputComment: "",
    };
  },
  created() {
    axios.get(`/board/view/${this.$route.params.id}`).then(({ data }) => {
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
        this.$router.push({ name: "BoardModify", params: { id: this.article.id } });
      } else {
        alert("작성자가 아닙니다");
        this.$router.push({ name: "BoardList" });
      }
    },
    moveList() {
      this.$router.push({ name: "BoardList" });
    },
    writeComment() {
      if (this.userInfo) {
        let comment = {
          userId: this.userInfo.userId,
          boardId: this.article.id,
          content: this.inputComment,
        };
        axios.post(`/board/comment/write`, comment).then(() => {
          axios.get(`/board/view/${this.$route.params.id}`).then(({ data }) => {
            this.article = data;
          });
          this.inputComment = "";
        });
      } else {
        alert("로그인이 필요합니다");
        this.$router.push({ name: "UserLogin" });
      }
    },
    deleteComment(comment) {
      if (this.userInfo) {
        if (this.userInfo.userId == comment.userId) {
          axios.delete(`/board/comment/delete/${comment.id}`).then(
            axios.get(`/board/view/${this.$route.params.id}`).then(({ data }) => {
              this.article = data;
            })
          );
        } else {
          alert("삭제할 권한이 없습니다");
          this.$router.push({ name: "BoardList" });
        }
      } else {
        alert("로그인이 필요합니다");
        this.$router.push({ name: "UserLogin" });
      }
    },
    upLike() {
      let map = {
        id: this.article.id,
        userId: this.userInfo.userId,
      };
      axios.post(`/board/up`, map).then(({ data }) => {
        if (data == "success") {
          axios.get(`/board/view/${this.$route.params.id}`).then(({ data }) => {
            this.article = data;
          });
        } else {
          alert("이미 좋아요를 누른 게시물입니다");
        }
      });
    },
  },
};
</script>

<style scoped>
h6 {
  text-align: right;
}
</style>
