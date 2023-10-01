<template lang="pug">
  div.buttons
    el-button.button(
      @click="$emit('actionCancelar')",
      :style="buttonCancelarStyle",
    )
      font-awesome-icon(:icon="iconeCancelar")
      span.buttonText &nbsp {{ textButtonCancelar }}
    el-button.button(
      @click="$emit('actionConfirmar')",
      :style="buttonConfirmarStyle",
      :type="buttonConfirmarTipo"
    )
      font-awesome-icon(:icon="iconeConfirmar")
      span.buttonText(:style="buttonCancelarTextStyle") &nbsp {{ textButtonConfirmar }}

</template>

<script>
export default {
  props: {
    iconeCancelar: {
      type: String,
      required: true
    },
    iconeConfirmar: {
      type: String,
      required: true
    },
    textButtonConfirmar: {
      type: String,
      required: true
    },
    textButtonCancelar: {
      type: String,
      required: true
    },
    buttonConfirmarStyle: {
      type: String
    },
    buttonConfirmarTipo: {
      type: String,
      default: 'primary'
    },
    buttonCancelarStyle: {
      type: String
    },
    buttonCancelarTextStyle: {
      type: String
    }
  }
};
</script>

<style scoped lang="scss">
.buttons {
  display: flex;
  justify-content: space-between;
}

.el-button.el-button--default {
  &:hover {
    border-color: #c3d4fe !important;
  }
  border-color: #707070;
}
</style>
