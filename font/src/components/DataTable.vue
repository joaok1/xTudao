<template lang="pug">
div(class="fornecedores")
    el-table.table(
      :data="data"
      :default-sort="defaultSort"
      :header-cell-style="headerStyle"
      @select="select"
      @select-all="selectAll"
      :row-key="getRowKey"
      @sort-change="sort"
    )
      template(slot="empty") {{getMessageNotFound}}
      el-table-column(
        
        v-if="selectionColumn"
        type="selection",
        :reserve-selection="true"
      )
      el-table-column(
        v-if="checkWarningAlmoxarifado"
        width="35"
      )
        template(slot-scope="scope")
          el-tooltip(
            v-if="scope.row.icone === 'ZERADO'"
            content="Estoque Zerado"
          )

          el-tooltip(
            v-if="scope.row.icone === 'ALERTA'"
            placement="top" effect="light"
          )

      el-table-column(
        v-for="col in columns"
        :prop="col.prop"
        :width="col.width"
        :label="col.label"
        :key="col.prop"
        :sortable="col.order"
        :formatter="typeof col.formatter === 'function' ? col.formatter : null"
      )
      el-table-column(
        fixed="right"
        v-if="acoes && acoes.length > 1",
        label="Ações",
        width="220",
        header-align="center",
        align="center"
      )
        template(slot-scope="scope")
          el-button(v-for="acao in acoes", :key="acao.text", @click.native="buttonAction(scope.row, acao.codigo)" type="text" size="small")
            div(style="display:flex;align-items:center")
              i.textButton(:class="acao.icon") {{acao.text}}
      //- APPEND DA TABELA
      template(slot="append")
        slot(name="appendTable")

    //- COMPONENT FOOTER
    div.footer(v-if="pageable && pageable.pageable && !pageable.empty")
      span.bold {{getStringResultados()}}
      el-pagination(
        :page-size="pageable.pageable.pageSize",
        :current-page="pageable.pageNumber + 1"
        :total="pageable.totalElements",
        layout="prev, pager, next",
        small,
        align="right"
        @current-change="atualizarTabela"
      )
</template>

<script>
import configs from "@/constants/configs";
import messages from "@/constants/messages";
import formatter from "@/utils/formatter";

export default {
  data() {
    return {
      pageSize: configs.PAGE_SIZE,
      headerStyle: {
        verticalAlign: "top",
        lineHeight: "22px",
      },
    };
  },
  props: {
    columns: {
      required: true,
      type: Array,
    },
    acoes: {
      required: false,
      type: Array,
    },
    pageable: {
      required: false,
      type: Object,
    },
    defaultSort: {
      required: false,
      type: Object,
    },
    icon: {
      type: Boolean,
      default: false,
    },
    selectionColumn: {
      type: Boolean,
      default: false,
    },
    showStatusDataEsperada: {
      type: Boolean,
      default: false,
    },
    checkWarningAlmoxarifado: {
      type: Boolean,
      default: false,
    },
    data: {
      required: true,
      type: Array,
      default: () => [],
    },
  },
  computed: {
    getMessageNotFound() {
      return messages.RESULTADOS_NAO_ENCONTRADOS;
    },
  },
  methods: {
    sort(data) {
      const ordenacaoFiltro = {
        ordem: data.prop,
        ordemDirecao: null,
      };

      if (data.order === "ascending") {
        ordenacaoFiltro.ordemDirecao = "ASC";
      } else {
        ordenacaoFiltro.ordemDirecao = "DESC";
      }

      this.$emit("sort", { ...ordenacaoFiltro });
    },
    showIcon(row) {
      const arrayProps = this.acoes[0].propShowAcao.split(".");
      if (this.acoes[0].propShowAcao) {
        for (let index = 0; index < arrayProps.length; index++) {
          return !!row[arrayProps[0]][arrayProps[1]];
        }
      }
      return false;
    },
    showColumnByEstoque(row = false) {
      const isColumnEstoque = row.estoque.quantidade < row.estoque.minimo;
      return isColumnEstoque;
    },
    buttonAction(row, action, index) {
      this.$emit(action.toLowerCase(), row, index);
    },
    atualizarTabela(newPage) {
      this.$emit("atualizarTabela", newPage);
    },
    getStringResultados() {
      if (this.pageable.pageable) {
        return `Exibindo de ${this.pageable.pageable.offset + 1}
          até ${this.pageable.pageable.offset + this.pageable.numberOfElements}
          de ${this.pageable.totalElements} registros`;
      }
    },
    showActionByStatus(row, statusShow) {
      let showAction = true;
      if (statusShow) {
        showAction = statusShow.includes(row.situacao.codigo || row.status);
      }
      return showAction;
    },
    showAtivadoDesativado(row, acao) {
      if (row.ativo != null) {
        if (row.ativo) {
          switch (acao.codigo) {
            case "ATIVAR":
              return false;
            default:
              return true;
          }
        } else {
          switch (acao.codigo) {
            case "ATIVAR":
              return true;
            case "VISUALIZAR":
              return true;
            default:
              return false;
          }
        }
      }
      return true;
    },

    select(selection, row) {
      this.$emit("setRow", row, !row.ativo, selection);
    },

    selectAll(selection) {
      this.$emit("selectAll", selection);
    },

    getRowKey(row) {
      return row.id;
    },

    getClass(date) {
      if (date) {
        const now = new Date();
        const dataToCompare = formatter.stringToDate(date);
        dataToCompare.setDate(dataToCompare.getDate() + 1);
        const nowToCompareWithFiveLessDays = formatter.stringToDate(date);
        nowToCompareWithFiveLessDays.setDate(
          nowToCompareWithFiveLessDays.getDate() + 1
        );
        nowToCompareWithFiveLessDays.setDate(
          nowToCompareWithFiveLessDays.getDate() - 5
        );

        if (dataToCompare.getTime() < now.getTime()) {
          return "red";
        }
        if (now.getTime() <= nowToCompareWithFiveLessDays.getTime()) {
          return "green";
        }

        return "yellow";
      }
    },
    getToolTip(date) {
      return date
        ? `Prazo previsto: ${date}`
        : "Prazo previsto ainda não definido";
    },
  },
};
</script>

<style scoped lang="scss">
.icon {
  margin-right: 12px;
  color: #366efa;
}
* {
  color: black;
  font-weight: bold;
}

.iconExclamation {
  margin-right: 12px;
  color: #ffb703;
}

.dropdown {
  font-weight: bold;
  border-color: #707070;
}

.footer {
  display: flex;
  justify-content: space-between;
  margin-top: 1.5em;
}

.soloIcon {
  color: #366efa;
  font-size: 15px;
  cursor: pointer;
}

.table {
  width: 100%;
}

.el-table .cell {
  word-break: break-word;
}

.fa-trash-alt {
  color: red;
}

.red {
  color: red;
}

.green {
  color: green;
}

.yellow {
  color: yellow;
}

.bold {
  font-weight: bold;
  color: #555555;
}

.textButton {
  color: #343e59;
  font-weight: bolder;
  font-size: 13px;
}
.textButton:hover {
  color: #1313d3;
  font-weight: bolder;
  text-decoration: underline;
}
</style>
