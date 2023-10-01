<template lang="pug">
    div.data
        el-card.data
          div(style="display:flex; position:relative; align-items:center; justify-content:space-between;")
            div
              span(style="font-weight:bold; font-size:20px") Listagem de funcionários
            div
              el-button(type="info" class="el-icon-plus" @click="cadastrarFuncionario()")  Cadastrar funcionario
          data-table(
              :pageable='pageable',
              :data="listaData.content",
              :columns='columns',
              stripe,
              @atualizarTabela='atualizarTabela',
              :acoes='acoes',
              )
    </template>
<script>
import DataTable from "@/components/DataTable.vue";
import Panel from "@/components/Panel.vue";
import funcoes from "@/methods/funções";
export default {
  components: {
    DataTable,
    Panel,
  },
  data() {
    return {
      page: null,
      listaData: {
        content: [],
      },
      pageable: {},
      acoes: [
        {
          text: "Editar",
          codigo: "EDITAR",
          icon: "el-icon-edit",
        },
        {
          text: "Excluir",
          codigo: "EXCLUIR",
          icon: "el-icon-delete",
        },
      ],
      columns: [
        {
          label: "Nome",
          prop: "nome",
          width: "325",
        },
        {
          label: "Sobrenome",
          prop: "sobrenome",
          width: "200",
        },
        {
          label: "Data de nascimento",
          prop: "data_nascimento",
          width: "250",
        },
        {
          label: "Perfil",
          prop: "usuario.role.name",
          width: "200",
        },
        {
          label: "Contato",
          prop: "telefone",
          width: "200",
        },
        {
          label: "Sexo",
          prop: "sexo",
          width: "200",
        },
      ],
    };
  },
  async mounted() {
    await this.listAllFuncionarioPage();
  },
  methods: {
    cadastrarFuncionario() {
      this.$router.push({
        name: "cadastrarFuncionarios",
        // params: { dadosProduto, messageExitPage },
      });
    },
    atualizarTabela(newPage) {
      this.page = this.listaData.pageable.pageNumber = newPage - 1;
      this.listAllFuncionarioPage();
    },
    async listAllFuncionarioPage() {
      const listaData = await funcoes.listAllFuncionarioPage(this.page);
      this.listaData = listaData.data;
      console.log(this.listaData);
      const { empty, number, numberOfElements, pageable, totalElements } =
        this.listaData;
      this.pageable = {
        empty,
        number,
        numberOfElements,
        pageable,
        totalElements,
      };
    },
  },
};
</script>
<style scoped>
.data {
  display: flex;
  margin-left: 5px;
  justify-content: center;
  align-content: center;
  height: 100%;
}
* {
  padding: 10px;
}
</style>
