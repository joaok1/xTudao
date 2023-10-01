<!-- eslint-disable vue/multi-word-component-names -->
<template lang="pug">
div.class
    div.container
      div
        img.logo(src="@/assets/logo.png")
      div
        el-card.box
          h2 Bem-Vindo
          div(style="margin-top:10%")
            el-input(
              style="width:100%; margin-bottom:2vh;"
                placeholder="Digite o cpf"
                v-model="formLogin.login"
                v-mask="'###.###.###-##'"
            )
            el-input(
              style="width:100%; margin-bottom:2vh;"
              v-model="formLogin.senha"
              type="password"
              placeholder="Digite a senha"
            )
            el-button.button(
              @click="logar()" 
              type="info"
              style="color:#fff; padding:2vh; align-items:center"
            ) Entrar
</template>
<script>
import store from "../store/index.js";
import actions from "@/methods/funções";
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
    async handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    sendFormattedCPF(dados) {
      const cpfWithoutFormat = dados.replace(/[^\d]/g, "");
      return (this.formLogin.login = cpfWithoutFormat);
    },
    handleUploadSuccess(response, fileList) {
      this.fileList = fileList;
      this.pessoa.file.key = response;
    },
    async logar() {
      this.formLogin.login = this.sendFormattedCPF(this.formLogin.login);
      await store.dispatch("login", this.formLogin);
    },
    async cadastrar() {
      this.pessoa.cpf = this.sendFormattedCPF(this.pessoa.cpf);
      if (this.pessoa.file.key === null) {
        this.$notify.error({
          title: "Erro!",
          message: "Insira uma foto!",
        });
      }
      actions
        .cadastrar(this.pessoa)
        .then((response) => {
          (this.pessoa = {
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
          }),
            (this.step = 1);
          this.$refs.upload.clearFiles();
          this.$notify({
            title: "Sucesso!",
            message: response,
            type: "success",
          });
        })
        .catch((error) => {
          this.$refs.upload.clearFiles();
          this.$notify.error({
            title: "Erro!",
            message: error.response.data.message,
          });
        });
    },
  },
};
</script>

<style scoped>
.container {
  width: 100vw;
  height: 100vh;
  background: #000000;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.button {
  width: 100%;
  height: 100%;
  display: flex;
  padding: 1vh;
  flex-direction: row;
  justify-content: center;
  align-items: center;
}
.logo {
  width: 300px;
  margin-bottom: 10px;
}
.class {
  background: #000000;
}
.box {
  width: 300px;
  height: 300px;
  margin-bottom: 10em;
  background: #fff;
}
</style>
