<template lang="pug">
div.data
    el-card.data
      div(style="display:flex; position:relative; align-items:center; justify-content:space-between;")
        div
          span(style="font-weight:bold; font-size:20px") Listagem de produtos
        div
          el-button(type="info" class="el-icon-plus")  Cadastrar produto
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
          width: "370",
        },
        {
          label: "Tipo",
          prop: "tipo",
          width: "200",
          formatter: ({ tipo }) =>
            tipo === "BEBIDA" ? (
              <el-tooltip
                class="item"
                effect="dark"
                placement="right"
                content="Bebidas"
              >
                (<i class={"bx bx-beer"}></i>)
              </el-tooltip>
            ) : (
              <el-tooltip
                class="item"
                effect="dark"
                placement="right"
                content="Ingredientes"
              >
                (<i class={"el-icon-burger"}></i>)
              </el-tooltip>
            ),
        },
        {
          label: "Preço",
          prop: "valor",
          width: "200",
          formatter: (row, column) => {
            const value = row[column.property];
            return `R$ ${parseFloat(value).toFixed(2)}`;
          },
        },
        {
          label: "Quantidade",
          prop: "quantidade",
          width: "200",
          formatter: (row, column) => {
            const value = row[column.property];
            return parseFloat(value).toFixed(2);
          },
        },
        {
          label: "Fator",
          prop: "fator",
          width: "200",
        },
        {
          label: "Porçao",
          prop: "porcao",
          width: "200",
        },
      ],
    };
  },
  async mounted() {
    await this.listAllProdutosPage();
  },
  methods: {
    atualizarTabela(newPage) {
      this.page = this.listaData.pageable.pageNumber = newPage - 1;
      this.listAllProdutosPage();
    },
    async listAllProdutosPage() {
      const listaData = await funcoes.getListagemProdutos(this.page);
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
  margin-left: 5px;
  justify-content: center;
  align-content: center;
  height: 100%;
}
* {
  padding: 10px;
}
</style>
