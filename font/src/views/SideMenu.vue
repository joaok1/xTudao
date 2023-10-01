<!-- eslint-disable prettier/prettier -->
<!-- eslint-disable vue/no-parsing-error -->
<template>
  <el-menu
    class="el-menu-vertical-demo"
    :collapse="isCollapse"
    background-color="#000000"
    text-color="#fff"
    style="height: 100vh"
    active-text-color="#ffd04b"
    :default-active="$route.name"
    :router="true"
  >
    <img class="logo" src="@/assets/logo.png" />
    <el-menu-item index="home" @click="home()">
      <i class="el-icon-s-home"></i>
      <span slot="title">Home</span>
    </el-menu-item>
    <el-menu-item index="listagemprodutos" @click="ingredientes()">
      <i class="el-icon-shopping-cart-full"></i>
      <span slot="title">Produtos</span>
    </el-menu-item>
    <el-menu-item index="listagemLanches" @click="lanches()">
      <i class="el-icon-burger"></i>
      <span slot="title">Lanches</span>
    </el-menu-item>
    <el-menu-item index="listagemFuncionarios" @click="funcionarios()">
      <i class="el-icon-user"></i>
      <span slot="title">Funcionários</span>
    </el-menu-item>
    <!-- <div
      style="
        bottom: 0em;
        margin-top: 33em;
        position: relative;
        text-align: center;
      "
    >
      <el-tag index="6" effect="dark" type="info">
        <span>{{ this.name }}</span>
      </el-tag>
    </div> -->
    <el-menu-item
      index="7"
      style="
        bottom: 0;
        margin-bottom: 20px;
        position: absolute;
        justify-content: center;
        align-items: center;
      "
      class="logout"
      @click="logout()"
    >
      <el-button
        class="bx bx-log-out"
        type="text"
        style="font-size: 16px; color: #ff0000; font-weight: bold"
      >
        Sair</el-button
      >
    </el-menu-item>
  </el-menu>
</template>

<script>
import funcoes from "@/methods/funções";

export default {
  data() {
    return {
      name: null,
      isCollapse: false,
    };
  },
  async mounted() {
    await this.getUserName();
  },
  methods: {
    async getUserName() {
      const userName = await funcoes.Login();
      this.name = userName.data.namePessoa;
    },
    async logout() {
      await this.$store.dispatch("logout");
    },
    home() {
      if (this.$route.name !== "home") {
        this.$router.push("/");
      }
    },
    ingredientes() {
      if (this.$route.name !== "listagemprodutos") {
        this.$router.push({
          name: "listagemprodutos",
          // params: { dadosProduto, messageExitPage },
        });
      }
    },
    lanches() {
      if (this.$route.name !== "listagemLanches") {
        this.$router.push({
          name: "listagemLanches",
          // params: { dadosProduto, messageExitPage },
        });
      }
    },
    funcionarios() {
      if (this.$route.name !== "listagemFuncionarios") {
        this.$router.push({
          name: "listagemFuncionarios",
          // params: { dadosProduto, messageExitPage },
        });
      }
    },
  },
};
</script>
<style lang="scss" scoped>
.logout:hover,
.logout:active {
  color: #ff0000;
}

.logo {
  width: 200px;
  margin-bottom: 5px;
}

.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}
</style>
