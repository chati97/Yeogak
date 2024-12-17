<template>
  <div class="content" id="tableBack">
    <div style="text-align: right">
      <b-button
        @click="
          () => {
            this.showForm = !this.showForm;
          }
        "
      >
        문의 등록</b-button
      >
      <b-form-group v-slot="{ ariaDescribedby }" style="align-items: inline; text-align: right">
        <b-form-radio-group
          id="key"
          v-model="selected"
          :options="options"
          :aria-describedby="ariaDescribedby"
          name="plain-inline"
        ></b-form-radio-group>
        <input v-model="word" /><b-button @click="search">검색</b-button>
      </b-form-group>
    </div>
    <b-card v-if="showForm">
      <b-form @submit.prevent="registQuestion" @reset.prevent="resetForm">
        <b-form-group id="subject-group" label="글 제목:" label-for="subject">
          <b-form-input
            id="subject"
            v-model="form.subject"
            placeholder="제목..."
            required
          ></b-form-input>
        </b-form-group>
        <b-form-group id="content-group" label="글 내용:" label-for="content">
          <b-form-input
            id="content"
            v-model="form.content"
            placeholder="내용..."
            required
          ></b-form-input>
        </b-form-group>
        <b-button type="submit" variant="primary">Submit</b-button>
        <b-button type="reset" variant="danger">Reset</b-button>
      </b-form>
    </b-card>
    <b-table striped hover type="dark" :items="articles" :fields="fields">
      <template #cell(show_details)="row">
        <b-button size="sm" @click="row.toggleDetails" class="mr-2">
          {{ row.detailsShowing ? "Hide" : "Show" }} Details
        </b-button>
      </template>

      <template #row-details="row">
        <b-card>
          <b-row class="mb-2">
            <b-col sm="3" class="text-sm-right"><b>내용: </b></b-col>
            <b-col>{{ row.item.content }}</b-col>
          </b-row>
          <b-row class="mb-2" v-if="row.item.answer">
            <b-col sm="3" class="text-sm-right"><b>답변: </b></b-col>
            <b-col>{{ row.item.answer }}</b-col>
          </b-row>
          <b-row class="mb-2" v-else>
            <b-col sm="3" class="text-sm-right"><b>답변: </b></b-col>
            <b-col
              ><input v-model="answer" /><b-button @click="writeAnswer(row.item, answer)"
                >등록</b-button
              ></b-col
            >
          </b-row>
          <b-row class="mb-2" v-if="row.item.answerTime">
            <b-col sm="3" class="text-sm-right"><b>답변일시</b></b-col>
            <b-col>{{ row.item.answerTime }}</b-col>
          </b-row>
        </b-card>
      </template>
    </b-table>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
import axios from "@/api/http-common";

const userStore = "userStore";

export default {
  name: "BoardList",
  components: {},
  data() {
    return {
      showForm: false,
      form: {
        userId: "",
        subject: "",
        content: "",
      },
      answer: "",
      fields: [
        "id",
        { key: "subject", label: "제목" },
        { key: "nickname", label: "작성자" },
        { key: "registerTime", label: "작성일시" },
        { key: "show_details", label: "" },
      ],
      options: [
        { text: "제목", value: "subject" },
        { text: "작성자", value: "nickname" },
        { text: "내용", value: "content" },
      ],
      articles: [],
      selected: "",
      word: "",
    };
  },
  created() {
    axios.get(`/qna/list`).then(({ data }) => {
      this.articles = data;
    });
  },
  computed: {
    ...mapState(userStore, ["userInfo"]),
  },
  methods: {
    ...mapActions(userStore, ["getUserInfo"]),
    writeAnswer(item) {
      if (this.userInfo.type == 1) {
        axios
          .put(`/qna/writeanswer/${item.id}`, {
            answer: this.answer,
          })
          .then(() => {
            axios.get(`/qna/list`).then(({ data }) => (this.articles = data));
          });
      } else {
        alert("권한이 없습니다");
      }
    },
    registQuestion() {
      let input = this.form;
      input.userId = this.userInfo.userId;
      axios.post(`/qna/write`, input).then(() => {
        axios.get(`/qna/list`).then(({ data }) => {
          this.articles = data;
        });
      });
      this.showForm = false;
    },
    resetForm() {
      this.form.subject = "";
      this.form.content = "";
    },
    search() {
      let param = {
        key: this.selected,
        word: this.word,
      };
      axios
        .get(`/qna/search`, {
          params: param,
        })
        .then(({ data }) => {
          this.articles = data;
        });
    },
  },
};
</script>

<style scoped>
#tableBack {
  background-color: white;
}
</style>
