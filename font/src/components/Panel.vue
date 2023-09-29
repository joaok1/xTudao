<!-- eslint-disable vue/multi-word-component-names -->
<template lang="pug">
div
    el-card.card
      .panel-header
        .title
          span.title-font(v-if="title") {{title}}
          el-tooltip.tooltip(v-if="haveTooltip" :content="textTooltip" placement="bottom", effect="dark")
            font-awesome-icon.question-circle( :icon="['fas', 'question-circle']")
        el-button.button(type="secondary", v-if="(buttonText || buttonIcon) && hasPermissionAcao(permissaoTela, routeGroup)", @click="buttonAction")
          font-awesome-icon.icon(:icon="['fas', buttonIcon]")
          span {{buttonText}}
        el-button.button(
          type="secondary"
          v-if="(secondaryButton && (secondaryButtonText || secondaryButtonIcon)) && hasPermissionAcao(permissaoTela, routeGroup)"
          @click="secondaryButtonAction"
        )
          font-awesome-icon.icon(:icon="['fas', secondaryButtonIcon]")
          span {{secondaryButtonText}}
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
    secondaryButton: {
      required: false,
      default: false,
      type: Boolean,
    },
    secondaryButtonText: {
      required: false,
      type: String,
    },
    secondaryButtonIcon: {
      required: false,
      type: String,
    },
  },
  methods: {
    buttonAction() {
      this.$emit("goToCadastro");
    },

    secondaryButtonAction() {
      this.$emit("secondaryButtonAction");
    },
  },
};
</script>

<style scoped>
.icon {
  margin-right: 8px;
}
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
  padding-top: 0px;
}
.panel-header {
  display: flex;
  justify-content: space-between;
  align-items: baseline;
}
</style>
