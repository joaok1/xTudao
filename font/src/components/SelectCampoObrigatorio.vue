<template lang="pug">
#select-obrigatorio
  validation-provider(:rules='rules', v-slot='{ errors }', :name='name')
    label(:class='{ required: requiredLabel }') {{ label }}
    el-select.input(
      v-model='values',
      clearable,
      @clear='clearSelect',
      filterable,
      style='width: 100%',
      :class='{ error: errors.length }',
      :disabled='disabled',
      @change='change'
    )
      el-option(
        v-for='item in options',
        :key='item[optionKey]',
        :label='item[optionLabel]',
        :value='item[optionValueKey]'
      )
    span.error {{ errors[0] }}
</template>

<script>
export default {
  name: "SelectCampoObrigatorio",
  props: {
    initialValues: {
      required: false,
      default: () => null,
    },
    disabled: {
      type: Boolean,
      required: false,
      default: () => false,
    },
    options: {
      type: Array,
      required: true,
      default: () => [],
    },
    optionKey: {
      type: String,
      required: false,
      default: () => null,
    },
    optionLabel: {
      type: String,
      required: false,
      default: () => null,
    },
    optionValueKey: {
      type: String,
      required: false,
      default: () => null,
    },
    rules: {
      type: String,
      required: false,
    },
    name: {
      type: String,
      required: false,
    },
    label: {
      type: String,
      required: true,
      default: () => null,
    },
    requiredLabel: {
      type: Boolean,
      required: false,
      default: () => true,
    },
    isMultiple: {
      type: Boolean,
      required: false,
      default: () => false,
    },
  },

  data() {
    return {
      values: null,
    };
  },

  methods: {
    clearSelect() {
      this.values = null;
    },
    change(value) {
      this.$emit("change", value);
    },
  },

  mounted() {
    if (this.initialValues) {
      this.values = this.initialValues;
    }
  },

  watch: {
    immediate: true,
    values: {
      handler() {
        this.$emit("input", this.values);
      },
    },
    initialValues() {
      this.values = this.initialValues;
    },
  },
};
</script>
