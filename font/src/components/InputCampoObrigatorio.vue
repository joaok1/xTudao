<template lang="pug">
#input-obrigatorio
  validation-provider(:rules='rules', v-slot='{ errors }', :name='name')
    label(:class='{ required: requiredLabel }') {{ label }}
    el-input.input(
      v-model='value',
      clearable,
      :type='type',
      :class='{ error: errors.length }',
      :disabled='disabled',
      :placeholder='placeholder',
      :maxlength='maxlength',
      @blur='blur',
      @clear='clearInput'
    )
    span.error {{ errors[0] }}
</template>

<script>
export default {
  name: "InputCampoObrigatorio",
  props: {
    rules: {
      type: String,
      required: false,
      default: () => null,
    },
    name: {
      type: String,
      required: false,
      default: () => null,
    },
    label: {
      type: String,
      required: true,
      default: () => null,
    },
    valorInicial: {
      required: false,
      default: () => null,
    },
    requiredLabel: {
      type: Boolean,
      required: false,
      default: () => true,
    },
    disabled: {
      type: Boolean,
      required: false,
      default: () => false,
    },
    type: {
      type: String,
      required: false,
      default: () => "text",
    },
    placeholder: {
      type: String,
      required: false,
      default: () => "",
    },
    blur: {
      required: false,
      default: () => null,
    },
    maxlength: {
      required: false,
      type: String,
      default: null,
    },
  },

  data() {
    return {
      value: null,
    };
  },

  methods: {
    clearInput() {
      this.value = null;
    },
  },

  mounted() {
    if (this.valorInicial) {
      this.value = this.valorInicial;
    }
  },

  watch: {
    value: {
      immediate: true,
      handler() {
        this.$emit("input", this.value);
      },
    },
    valorInicial() {
      this.value = this.valorInicial;
    },
  },
};
</script>
