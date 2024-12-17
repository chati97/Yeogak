<template>
  <b-form-select
    id="search-area"
    name="sidoCode"
    class="form-select me-2"
    v-model="sidoCode"
    @change="selectSido"
  >
    <option value="0">시/도 선택</option>
    <option v-for="sido in sidos" :key="sido.sidoCode" :value="sido.sidoCode">
      {{ sido.sidoName }}
    </option>
  </b-form-select>
</template>

<script>
import axios from "@/api/http-common";

export default {
  name: "SidoList",
  data() {
    return {
      sidoCode: 0,
      sidos: [],
    };
  },
  created() {
    axios.get("/tour/sido").then((res) => {
      this.sidos = res.data;
    });
  },
  methods: {
    selectSido() {
      this.$emit("select-sido", this.sidoCode);
    },
  },
};
</script>

<style></style>
