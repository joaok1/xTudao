<template lang="pug">
  el-tooltip.item(
    v-if="text",
    effect='dark',
    :content='text',
    placement="bottom"
  )
    el-button.btn(
      :type="tipo",
      :disabled="disabled",
      @click="$emit('click')",
      @mouseover.native="hover",
      @mouseout.native="leave",
      :style="getColor + getFont",
      size="mini"
    )
      font-awesome-icon(:icon="icone")
</template>

<script>
export default {
  props: {
    tipo: {
      type: String,
      default: "text",
    },
    icone: {
      type: String,
      required: true,
    },
    nome: {
      type: String,
      default: undefined,
    },
    text: {
      type: String,
      required: true,
    },
    hoverColor: {
      type: String,
      default: "#366EFA",
    },
    fontSize: {
      type: String,
      default: undefined,
    },
    botonSig: {
      type: Boolean,
      default: false,
    },
    cor: String,
    disabled: Boolean,
  },

  data() {
    return {
      colorCopy: this.cor,
    };
  },

  computed: {
    getColor() {
      if (this.colorCopy) {
        return `color: ${this.colorCopy};`;
      }
      return "";
    },

    getFont() {
      if (this.fontSize) {
        return `font-size: ${this.fontSize}px;`;
      }
      return "";
    },
  },

  methods: {
    hover() {
      this.colorCopy = this.hoverColor;
    },

    leave() {
      this.colorCopy = this.cor;
    },
  },
};
</script>

<style scoped lang="scss">
.btn {
  padding: 5px;
  color: #666666;
  font-size: 25px;
}
</style>
