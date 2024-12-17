<template>
  <div class="content">
    <b-card>
      <b-form>
        <b-row class="mb-3 text-center">
          <b-col>
            <b-input id="subject" v-model="subject" placeholder="제목" />
          </b-col>
        </b-row>
        <b-row class="mb-5">
          <b-col>
            <b-textarea id="content" v-model="content" placeholder="내용을 입력해주세요" />
          </b-col>
        </b-row>
        <b-button type="button" variant="success" class="m-1" @click="write" right>등록</b-button>
      </b-form>
    </b-card>
  </div>
</template>

<script>
import axios from "@/api/http-common";
import { mapState, mapActions } from "vuex";
const userStore = "userStore";

export default {
  name: "BoardWrite",
  components: {},
  data() {
    return {
      subject: "",
      content: "",
    };
  },
  created() {},
  computed: {
    ...mapState(userStore, ["userInfo"]),
  },
  methods: {
    ...mapActions(userStore, ["getUserInfo"]),
    write() {
      let board = {
        userId: this.userInfo.userId,
        subject: this.subject,
        content: this.content,
      };
      axios.post(`/board/write`, board).then(() => {
        this.$router.push({ name: "BoardList" });
      });
    },
  },
};
</script>

<style scoped></style>
