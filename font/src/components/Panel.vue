<!-- eslint-disable vue/multi-word-component-names -->
<template lang="pug">
el-card.card
  .panel-header
    .title
      span.title-font(v-if='title') {{ title }}
      el-tooltip.tooltip(
        v-if='haveTooltip',
        :content='textTooltip',
        placement='bottom',
        effect='dark'
      )
        font-awesome-icon.question-circle(:icon='["fas", "question-circle"]')
    div.margin
      el-dropdown.width(
        size='medium',
        split-button,
        type='secondary',
        v-if='dropdown && hasPermissionAcao(permissaoTela, routeGroup)'
      ) Exportar
        el-dropdown-menu(slot='dropdown')
          el-dropdown-item(@click.native='exportarPdf')
            font-awesome-icon.icon(:icon='["fas", "file-pdf"]')
            label &nbsp PDF
          el-dropdown-item(@click.native='exportarCsv')
            font-awesome-icon.icon(:icon='["fas", "file-excel"]')
            label &nbsp CSV
    div
      el-button.button(
        type='secondary',
        v-if='(buttonText || buttonIcon) && hasPermissionAcao(permissaoTela, routeGroup)',
        @click.native='buttonAction'
      )
        font-awesome-icon.icon(:icon='["fas", buttonIcon]')
        span.buttonText {{ buttonText }}
  .panel-body
    slot
</template>
<script>
import { mapGetters } from "vuex";

export default {
  computed: {
    ...mapGetters({
      hasPermissionAcao: "permissions/hasPermissionAcao",
    }),
  },

  props: {
    title: {
      required: false,
      type: String,
    },
    buttonText: {
      required: false,
      type: String,
    },
    buttonIcon: {
      required: false,
      type: String,
    },
    haveTooltip: {
      default: false,
      type: Boolean,
    },
    textTooltip: {
      required: false,
      type: String,
    },
    permissaoTela: {
      required: false,
      type: String,
    },
    routeGroup: {
      required: false,
      type: String,
    },
    exportar: {
      required: false,
      type: Boolean,
    },
    dropdown: {
      required: false,
      type: Boolean,
    },
  },
  methods: {
    exportarCsv() {
      this.$emit("downloadCsv");
    },
    exportarPdf() {
      this.$emit("downloadPdf");
    },
    buttonAction() {
      this.$emit("goToCadastro");
    },
  },
};
</script>

<style scoped>
.card {
  margin-bottom: 25px;
  padding: 10px;
  border-radius: 8px;
}
.title {
  display: flex;
  width: 80%;
  font-weight: bold;
  color: #555555;
}
.title-font {
  font-size: 17px;
}
.tooltip {
  margin-left: 2%;
}
.panel-body {
  padding-top: 15px;
}
.panel-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
}
.dropdown {
  padding: 10px;
}
.panel {
  justify-content: flex-end;
}
.margin {
  display: flex;
  justify-content: flex-end;
  width: 200px;
  margin-right: 10px;
}
</style>
