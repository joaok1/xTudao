<template lang="pug">
div.data
  el-card.data
    div(style="display:flex; position:relative; align-items:center; justify-content:space-between;")
      div
        span(style="font-weight:bold; font-size:20px") Listagem de funcionários
      div
        el-button(type="info" class="el-icon-plus" @click="cadastrarFuncionario()")  Cadastrar funcionário
    data-table(
      :pageable='pageable',
      :data="listaData.content",
      :columns='columns',
      stripe,
      @editar="editar"
      @excluir="modalExcluir"
      @visualizar="visualizarFuncionario"
      @atualizarTabela='atualizarTabela',
      :acoes='acoes',
    )
</template>
<script>
import DataTable from "@/components/DataTable.vue";
import Panel from "@/components/Panel.vue";
import funcoes from "@/methods/funções";
export default {
  name: "listagemFuncionarios",
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
        {
          text: "Visualizar",
          codigo: "VISUALIZAR",
          icon: "el-icon-view",
        },
      ],
      columns: [
        {
          label: "Nome",
          prop: "nome",
          width: "300",
        },
        {
          label: "Sobrenome",
          prop: "sobrenome",
          width: "200",
        },
        {
          label: "Data de nascimento",
          prop: "data_nascimento",
          width: "200",
        },
        {
          label: "Perfil",
          prop: "usuario.role.name",
          width: "150",
        },
        {
          label: "Cpf",
          prop: "cpf",
          width: "200",
          formatter: ({ cpf }) =>
            cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, "$1.$2.$3-$4"),
        },
        {
          label: "Contato",
          prop: "telefone",
          width: "200",
        },
        {
          label: "Sexo",
          prop: "sexo",
          width: "120",
        },
      ],
    };
  },
  async mounted() {
    await this.listAllFuncionarioPage();
  },
  methods: {
    modalExcluir(data) {
      this.$confirm("Deseja excluir este usuário?", {
        type: "warning",
        title: "Aviso",
        center: true,
        roundButton: true,
        confirmButtonText: "Sim",
        cancelButtonText: "Não",
      })
        .then((confirm) => {
          window.dispatchEvent(new Event("resize"));
          this.excluir(data);
          data(confirm);
        })
        .catch((cancel) => {
          cancel;
        });
    },
    async excluir(data) {
      await funcoes
        .excluirFuncionario(data.id)
        .then(async () => {
          await this.listAllFuncionarioPage();
          this.$notify({
            title: "Sucesso!",
            message: "Registro Deletado!",
            type: "success",
          });
        })
        .catch((erro) => {
          this.$notify.error({
            title: "Erro!",
            message: erro,
          });
        });
    },
    visualizarFuncionario(data) {
      this.$router.push({
        name: "visualizarFuncionario",
        params: {
          idFuncionario: data.id,
        },
      });
    },
    editar(data) {
      this.$router.push({
        name: "editarFuncionario",
        params: {
          idFuncionario: data.id,
        },
      });
    },
    cadastrarFuncionario() {
      this.$router.push({
        name: "cadastroFuncionarios",
      });
    },
    atualizarTabela(newPage) {
      this.page = this.listaData.pageable.pageNumber = newPage - 1;
      this.listAllFuncionarioPage();
    },
    async listAllFuncionarioPage() {
      const listaData = await funcoes.listAllFuncionarioPage(this.page);
      this.listaData = listaData.data;
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
  justify-content: center;
  align-content: center;
  height: 100%;
}
* {
  padding: 10px;
}
</style>
