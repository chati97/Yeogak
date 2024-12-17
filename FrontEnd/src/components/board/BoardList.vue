<template>
  <div class="text-right bg-white">
    <b-button @click="boardWrite()" style="text-align: right">게시글 작성</b-button>
    <b-form-group v-slot="{ ariaDescribedby }" style="align-items: inline" id="tableBack">
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
import axios from "@/api/http-common";
export default {
  name: "BoardList",
  components: {},
  data() {
    return {
      perPage: 10,
      currentPage: 1,
      fields: [
        "id",
        { key: "subject", label: "제목" },
        { key: "nickname", label: "작성자" },
        { key: "hit", label: "조회수", sortable: true },
        { key: "likes", label: "좋아요", sortable: true },
        { key: "registerTime", label: "작성일시", sortable: true },
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
  computed: {
    rows() {
      return this.articles.length;
    },
  },
  created() {
    axios.get(`/board/list`).then(({ data }) => {
      this.articles = data;
    });
  },
  methods: {
    rowClick(record) {
      this.$router.push({ name: "BoardView", params: { id: record.id } });
    },
    boardWrite() {
      this.$router.push({ name: "BoardWrite" });
    },
    search() {
      let param = {
        key: this.selected,
        word: this.word,
      };
      axios
        .get(`/board/search`, {
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
#my-table {
  background-color: white;
}
</style>
