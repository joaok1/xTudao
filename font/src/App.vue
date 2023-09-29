<template lang="pug">
div(style="display:flex")
  div(v-if="this.$route.name !== 'Login'")
    SideMenu
  div
    <router-view />
</template>
<script>
import SideMenu from "./views/SideMenu.vue";

export default {
  components: {
    SideMenu,
  },
  // data() {},
};
</script>

<style scoped></style>
