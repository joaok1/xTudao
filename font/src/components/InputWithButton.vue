<template lang="pug">
  div
    validation-provider(:name="inputId" :rules="rules" v-slot="{ errors }" ref="InputWithButton")
      el-input(v-if="mask", :id="inputId", v-model="model", v-mask="mask" @input="onChange")
        el-button(v-if="buttonText" type="primary" :disabled="getDisabled(errors)" slot="append", @click="actionButton")
          font-awesome-icon(:icon="['fas', buttonIcon]")
          span {{ ' ' + buttonText}}
      el-input(v-else :id="inputId", v-model="model" :maxlength="wordLimit" :show-word-limit="showWordLimit" @input="onChange")
        el-button(v-if="buttonText" type="primary" :disabled="getDisabled(errors)" slot="append", @click="actionButton")
          font-awesome-icon(:icon="['fas', buttonIcon]")
          span {{ ' ' + buttonText}}
      span.error {{ errors[0] }}
    el-table(v-if="tableData && columnProp" :data="tableData")
      template(slot="empty")
        span {{ emptyText }}
      el-table-column(
        :prop="columnProp"
      )
      el-table-column(
        width="120"
        align="center"
      )
        template(slot-scope="scope")
          el-tooltip(v-if="tableIcon" class="item" effect="dark" :content="iconPlaceholder" placement="bottom")
            font-awesome-icon.trashIcon(:icon="['far', tableIcon]", @click="actionIcon(scope.$index, tableData)")

</template>

<script>
import Panel from '@/components/Panel';
import messages from '@/constants/messages';
export default {
  components: {
    Panel
  },

  data () {
    return {
      model: ''
    };
  },

  mounted () {
    this.model = this.inputValue;
  },

  props: {
    buttonText: {
      type: String,
      required: false,
      default: 'Adicionar'
    },
    buttonIcon: {
      type: String,
      required: false,
      default: ''
    },
    rules: {
      type: Object,
      required: false,
      default: () => {}
    },
    tableData: {
      type: Array,
      required: false,
      default: () => []
    },
    columnProp: {
      type: String,
      required: false,
      default: undefined
    },
    iconPlaceholder: {
      type: String,
      required: false,
      default: 'Excluir'
    },
    tableIcon: {
      type: String,
      required: false,
      default: undefined
    },
    mask: {
      required: false,
      default: undefined
    },
    inputId: {
      type: String,
      required: false,
      default: ''
    },
    wordLimit: {
      type: Number,
      required: false
    },
    showWordLimit: {
      type: Boolean,
      required: false,
      default: false
    },
    disabledRule: {
      type: Boolean,
      required: false,
      default: false
    },
    emptyText: {
      type: String,
      required: false,
      default: messages.RESULTADOS_NAO_ENCONTRADOS
    },
    inputValue: {
      type: String,
      required: false,
      default: ''
    }
  },

  methods: {
    actionButton () {
      this.$emit('buttonAction', this.model);
    },
    actionIcon (index, rows) {
      this.$emit('iconAction', index, rows);
    },
    getDisabled (errors) {
      if (!this.disabledRule && this.rules.isEmpty && errors[0] !== this.rules.isEmpty[1]) {
        return errors.length > 0;
      } else {
        return false;
      }
    },
    onChange () {
      this.$emit('onChange', this.model);
    }
  }
};
</script>

<style>
.trashIcon {
  color: red;
  cursor: pointer;
}
</style>
