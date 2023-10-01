<template lang="pug">
el-dialog(
  :visible.sync="isVisible",
  :title="titulo",
  :close-on-click-modal="true",
  :close-on-press-escape="false",
  :show-close="showClose",
  :width = "widthDialog",
)
  .bodyDialog
    font-awesome-icon.iconeDialog(:icon="['fas', icone]" :style="iconColor")
    span.text {{text}}
    slot
  div.footerDialog(slot="footer")
    el-button.dialogButton.confirmButton(@click="$emit('confirmar')")
      font-awesome-icon(:icon="['fas', iconeBtnConfirmar]")
      span.dialogBtnText {{tituloBtnConfirmar}}
</template>
<script>
export default {
  props: {
    titulo: {
      type: String,
      required: false
    },
    icone: {
      type: String,
      required: false
    },
    iconeColor: {
      type: String,
      required: false
    },
    text: {
      type: String,
      required: true
    },
    tituloBtnConfirmar: {
      type: String,
      default: 'Confirmar'
    },
    iconeBtnConfirmar: {
      type: String
    },
    widthDialog: {
      type: String,
      default: '30%'
    },
    showClose: {
      type: Boolean,
      default: false
    },
    isVisible: {
      type: Boolean
    }
  },
  computed: {
    iconColor () {
      return {
        '--iconColor': this.iconeColor
      };
    }
  }
};
</script>
<style lang="scss" scoped>
.iconeDialog {
  font-size: 60px;
  justify-self: center;
  display: inline-block;
  border-radius: 50%;
  padding: 0.45em 0.7em;
  border: 2.5px solid var(--iconColor);
  opacity: 1;
  color: var(--iconColor);
}
.subtitle {
  font-weight: 1000;
  font-size: 17px;
  margin-top: 20px;
  justify-self: center;
}
.text {
  margin-top: 20px;
  font-size: 18px;
  font-weight: 500;
  text-align: center;
}
.dialogBtnText {
  margin-left: 10px;
  font-size: 14px;
}
.dialogButton {
  border: 1px solid;
  width: 125px;
}
.confirmButton {
  background-color: #366efa;
  color: white;
}
.bodyDialog {
  display: grid;
  justify-content: center;
  word-break: break-word;
}
.footerDialog {
  text-align: center;
  justify-content: space-between;
  margin-top: 25px;
  border-top: solid;
  border-top-width: thin;
  padding: 20px;
}
/deep/.el-dialog__footer {
  padding: 0px;
}
</style>
