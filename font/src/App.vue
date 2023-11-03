<template lang="pug">
div
  div(v-if="this.$route.name === 'Login'")
    div(v-if="this.$route.name !== 'Login'")
      SideMenu
    div
      <router-view />
  div.layout(v-if="this.$route.name !== 'Login'")
    div.sideMenu
      SideMenu
    div.content
      <router-view />
</template>
<script>
import SideMenu from "./views/SideMenu.vue";

export default {
  components: {
    SideMenu,
  },
  data() {
    return {
      isFixed: false,
    };
  },
  methods: {
    handleScroll() {
      const sidebar = this.$refs.sidebar;
      if (sidebar) {
        const sidebarOffset = sidebar.offsetTop;
        const scrollY = window.scrollY || window.pageYOffset;

        if (scrollY > sidebarOffset) {
          this.isFixed = true;
        } else {
          this.isFixed = false;
        }
      }
    },
  },
  mounted() {
    window.addEventListener("scroll", this.handleScroll);
  },
  beforeDestroy() {
    window.removeEventListener("scroll", this.handleScroll);
  },
};
</script>

<style scoped>
.layout {
  display: flex;
}
.sideMenu {
  position: fixed;
}
.content {
  margin-left: 200px;
  flex: 1;
}
</style>
