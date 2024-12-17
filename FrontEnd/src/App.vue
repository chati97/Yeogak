<template>
  <div id="app">
    <nav-bar style="background-color: rgba(0, 0, 0, 0.5)"></nav-bar>
    <div class="mt-4">
      <router-view class="main-view" />
    </div>
  </div>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  height: 100vh;
  width: 100vw;
  background-image: url("@/assets/img2.jpg");
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center;
  background-attachment: fixed;
}
h1 {
  color: white;
  text-align: left;
  text-decoration: underline;
  text-decoration-color: yellow;
}
.content {
  width: 80vw;
  height: 73vh;
  margin: auto;
}
#mask {
  filter: blur(4px);
}
.main-view {
  max-height: 80vh;
  max-width: 80vw;
  overflow-x: hidden;
  overflow-y: auto;
  margin: auto;
}
* {
  -ms-overflow-style: none; /* IE and Edge */
  scrollbar-width: none; /* Firefox */
}
*::-webkit-scrollbar {
  display: none; /* Chrome, Safari, Opera*/
}
</style>

<script>
import NavBar from "@/components/HeaderNav.vue";
export default {
  name: "App",
  components: {
    NavBar,
  },
};
</script>
