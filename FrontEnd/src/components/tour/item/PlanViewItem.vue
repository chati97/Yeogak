<template>
  <b-card no-body class="overflow-hidden" style="max-width: 540px">
    <b-row no-gutters>
      <b-col md="3">
        <b-card-img :src="placeData.imgSrc" alt="Image" class="rounded-0"></b-card-img>
      </b-col>
      <b-col md="9">
        <b-card-body :title="placeData.title">
          <b-card-text class="plan-view-item-card-text">
            {{ placeData.addr }}
          </b-card-text>
        </b-card-body>
      </b-col>
    </b-row>
  </b-card>
</template>

<script>
export default {
  name: "PlanViewItem",
  props: {
    placeData: Object,
  },
};
</script>

<style>
.plan-view-item-card-text {
  font-size: small;
}
</style>
