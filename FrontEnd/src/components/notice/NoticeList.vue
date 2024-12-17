<template>
  <div style="text-align: right" id="tableBack">
    <b-button @click="writeNotice">공지사항 작성</b-button><br />
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
    <b-table
      striped
      hover
      type="dark"
      id="my-table"
      :items="articles"
      :fields="fields"
      @row-clicked="rowClick"
      :per-page="perPage"
      :current-page="currentPage"
    >
    </b-table>
    <b-pagination
      v-model="currentPage"
      :total-rows="rows"
      :per-page="perPage"
      aria-controls="my-table"
      align="center"
    ></b-pagination>
  </div>
</template>

<script>
import { mapState } from "vuex";
import axios from "@/api/http-common";
const userStore = "userStore";
export default {
  name: "NoticeList",
  components: {},
  data() {
    return {
      perPage: 10,
      currentPage: 1,
      fields: [
        "id",
        { key: "subject", label: "제목" },
        { key: "nickname", label: "작성자" },
        { key: "registerTime", label: "작성시간" },
        "hit",
        "likes",
      ],
      options: [
        { text: "제목", value: "subject" },
        { text: "작성자", value: "nickname" },
        { text: "내용", value: "content" },
      ],
      selected: "",
      word: "",
      articles: [],
    };
  },
  computed: {
    ...mapState(userStore, ["userInfo"]),
    rows() {
      return this.articles.length;
    },
  },
  created() {
    axios.get(`/notice/list`).then(({ data }) => {
      this.articles = data;
    });
  },
  methods: {
    rowClick(record) {
      this.$router.push({ name: "NoticeView", params: { id: record.id } });
    },
    writeNotice() {
      if (this.userInfo) {
        if (this.userInfo.type == 1) {
          this.$router.push({ name: "NoticeWrite" });
        } else {
          alert("권한이 없습니다");
        }
      } else {
        alert("로그인이 필요한 서비스입니다");
        this.$router.push({ name: "UserLogin" });
      }
    },
    search() {
      let param = {
        key: this.selected,
        word: this.word,
      };
      axios
        .get(`/notice/search`, {
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
#tableBack,
#checkbox,
#my-table {
  background-color: white;
}
</style>
