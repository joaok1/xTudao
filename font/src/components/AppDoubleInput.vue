<template lang="pug">
  //- See reference: https://dm4t2.github.io/vue-currency-input/config/#component
  currency-input(
    class="el-input el-input__inner"
    :key="key"
    v-model="formattedValue"
    :currency="{ prefix: prefix ? prefix + ' ' : '', suffix: suffix ? ' ' + suffix : '' }"
    :precision="precision"
    :value-range="{ min: minValue, max: maxValue}"
    :allow-negative="allowNegative",
    :auto-decimal-mode="autoDecimalMode"
    @blur="$emit('blur')"
    @change.native="change"
    :disabled="disabled",
    :class="{'disabled': disabled}"
  )
</template>
<script>
import utils from '@/utils/utils';

export default {
  name: 'AppDoubleInput',
  props: {
    disabled: {
      type: Boolean,
      required: false,
      default: false
    },
    value: {
      required: true,
      default: 0
    },
    hasValidationError: {
      type: Boolean,
      default: false
    },
    precision: {
      type: Number,
      default: () => 2
    },
    autoDecimalMode: {
      type: Boolean,
      default: true
    },
    allowNegative: {
      type: Boolean,
      default: false
    },
    minValue: {
      type: Number,
      default: 0
    },
    maxValue: {
      type: Number
    },
    prefix: {
      type: String,
      default: () => ''
    },
    suffix: {
      type: String,
      default: () => ''
    }
  },

  data () {
    return {
      formattedValue: '',
      key: 1
    };
  },

  watch: {
    value: {
      immediate: true,
      handler (newValue) {
        this.key++;
        this.formattedValue = newValue;
      }
    }
  },

  methods: {
    change (evt) {
      const value = utils.unformat(evt.target.value, this.precision);
      if (this.value !== value) {
        this.$emit(
          'input',
          this.maxValue && this.maxValue < value ? this.maxValue : value
        );
      }
    }
  }
};
</script>
<style lang="scss" scoped>
.disabled {
  background-color: #f5f7fa;
  border-color: #e4e7ed;
  color: #c0c4cc;
  cursor: not-allowed;
}
</style>
