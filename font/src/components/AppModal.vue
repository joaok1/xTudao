<template lang="pug">
el-dialog(
  :visible.sync='isVisible',
  :title='titulo',
  :close-on-click-modal='closeOnClick',
  :close-on-press-escape='false',
  :show-close='showClose',
  :width='widthDialog',
  top='10vh',
  @close='$emit("close")'
)
  .bodyDialog
    slot
  .footerDialog(slot='footer', v-if='!hideFooter')
    .leftContainer(:md='12', v-if='showLeftBtn')
      .btnLeftHolder
        el-button.button(@click='$emit("cancelar")')
          font-awesome-icon(:icon='["fas", iconeBtnCancelar]')
          span.buttonText &nbsp {{ tituloBtnCancelar }}
    .rightContainer(:md='12', v-if='showRightBtn')
      el-button.button(type='primary', @click='$emit("confirmar")')
        font-awesome-icon(:icon='["fas", iconeBtnConfirmar]')
        span.buttonText &nbsp {{ tituloBtnConfirmar }}
</template>
<script>
export default {

  props: {
    titulo: {
      type: String,
      required: false
    },
    tituloBtnConfirmar: {
      type: String,
      default: 'Confirmar'
    },
    iconeBtnCancelar: {
      type: String,
      default: 'times'
    },
    iconeBtnConfirmar: {
      type: String,
      default: 'check'
    },
    tituloBtnCancelar: {
      type: String,
      default: 'Cancelar'
    },
    widthDialog: {
      type: String,
      default: '50%'
    },
    showClose: {
      type: Boolean,
      default: false
    },
    showLeftBtn: {
      type: Boolean,
      default: true
    },
    showRightBtn: {
      type: Boolean,
      default: false
    },
    isVisible: {
      type: Boolean
    },
    closeOnClick: {
      type: Boolean,
      default: false
    },
    hideFooter: {
      type: Boolean,
      default: false
    }
  }
};
</script>
<style lang="scss" scoped>
.iconeDialog {
  font-size: 60px;
  justify-self: center;
  display: inline-block;
  border-radius: 70px;
  padding: 0.5em 0.45em;
  border: 2px solid #e6a23d;
  opacity: 1;
  color: #e6a23d;
}
.dialogBtnText {
  margin-left: 10px;
  font-size: 14px;
}
.dialogButton {
  border: 1px solid;
  width: 100px;
}
.confirmButton {
  background-color: #27ae60;
  color: white;
}
.bodyDialog {
  display: grid;
  word-break: break-word;
  max-height: 600px;
  overflow-y: auto;
  overflow-x: hidden;
}
.footerDialog {
  border-top: solid;
  border-top-width: thin;
  padding: 10px;
  height: 60px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.leftContainer {
  justify-content: space-between;
  flex-direction: column;
  display: flex;
}
.rightContainer {
  float: right;
}
.btnLeftHolder {
  justify-content: flex-start;
  display: flex;
}
/deep/ .el-dialog__body {
  color: unset;
}
/deep/.el-dialog__footer {
  padding: 0px;
}
/deep/.el-dialog__header {
  background-color: #366efa;
}
/deep/.el-dialog__title {
  letter-spacing: 1px;
  font-weight: bold;
  font-size: 24px;
  color: #ffffff;
}

/deep/ i.el-dialog__close.el-icon.el-icon-close {
  color: #ffffff;
}
</style>
