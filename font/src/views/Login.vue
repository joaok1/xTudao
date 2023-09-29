<!-- eslint-disable vue/multi-word-component-names -->
<template lang="pug">
div
    //-   img.logo(src="@/assets/logo.png")
    el-card 
        el-input(placeholder="Digite o CPF", v-mask="'###.###.###-##'", v-model="formLogin.login")
        el-input(type="password", placeholder="Digite a senha", v-model="formLogin.senha")
        el-button(type="info", @click="logar()") Entrar
    </template>

<script>
import store from "../store/index.js";
export default {
  data() {
    return {
      uploadComponent: null,
      fileList: {},
      dialogImageUrl: "",
      dialogVisible: false,
      step: 1,
      length: 0,
      formLogin: {
        login: null,
        senha: null,
      },
      pessoa: {
        nome: null,
        sobrenome: null,
        cpf: null,
        email: null,
        usuario: {
          senha: null,
        },
        file: {
          key: null,
        },
      },
      imageTemp: null,
    };
  },
  mounted() {
    this.uploadComponent = this.$refs.upload;
  },
  methods: {
    sendFormattedCPF(dados) {
      const cpfWithoutFormat = dados.replace(/[^\d]/g, "");
      return (this.formLogin.login = cpfWithoutFormat);
    },
    async logar() {
      this.formLogin.login = this.sendFormattedCPF(this.formLogin.login);
      await store.dispatch("login", this.formLogin);
    },
  },
};
</script>
<style scoped></style>
