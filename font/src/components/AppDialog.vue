<template lang="pug">
el-dialog(
  :visible.sync='isVisible',
  :title='titulo',
  :close-on-click-modal='false',
  :close-on-press-escape='false',
  :show-close='showClose',
  :width='widthDialog'
)
  .bodyDialog
    font-awesome-icon.iconeDialog(
      :class='{ colorConfirmation: uniqueButton, colorWarning: !uniqueButton }',
      :icon='["fas", icone]'
    )
    span.subtitle Atenção!
    span.text {{ text }}
    slot
  .footerDialog(v-if='!uniqueButton', slot='footer')
    action-buttons(
      :iconeCancelar='iconeBtnCancelar',
      :iconeConfirmar='iconeBtnConfirmar',
      :textButtonConfirmar='tituloBtnConfirmar',
      :textButtonCancelar='tituloBtnCancelar',
      @actionConfirmar='$emit("confirmar")',
      @actionCancelar='$emit("cancelar")',
      cor='#366efa'
    )
  .footerDialog.footerUniqueButton(v-if='uniqueButton', slot='footer')
    el-button.dialogButton(type='primary', @click='$emit("confirmar")')
      font-awesome-icon(:icon='["fas", iconeBtnConfirmar]')
      span.dialogBtnText {{ tituloBtnConfirmar }}
</template>
<script>
import ActionButtons from '@/components/ButtonsConfirmarCancelar';
export default {
  components: {
    ActionButtons
  },

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
      required: false,
      default: 'red'
    },
    text: {
      type: String,
      required: true
    },
    tituloBtnConfirmar: {
      type: String,
      default: 'Confirmar'
    },
    iconeBtnCancelar: {
      type: String
    },
    iconeBtnConfirmar: {
      type: String
    },
    tituloBtnCancelar: {
      type: String,
      default: 'Cancelar'
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
    },
    uniqueButton: {
      type: Boolean,
      required: false,
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
}
.colorConfirmation {
  color: #27ae60;
  border-color: #27ae60;
}
.colorWarning {
  color: #e6a23d;
  border-color: #e6a23d;
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
  justify-content: center;
  word-break: break-word;
}
.footerDialog {
  margin-top: 25px;
  border-top: solid #000;
  border-top-width: thin;
  padding: 20px;
}
.footerUniqueButton {
  text-align-last: center;
}
/deep/.el-dialog__footer {
  padding: 0px;
}
</style>
