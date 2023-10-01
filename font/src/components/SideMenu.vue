<template lang="pug">
div
  app-dialog(
    :isVisible='showDialog',
    icone='exclamation-triangle',
    :text='textDialog',
    iconeBtnConfirmar='check',
    tituloBtnConfirmar='Sim',
    tituloBtnCancelar='Não',
    iconeBtnCancelar='times',
    @confirmar='confirmar',
    @cancelar='cancelar'
  )
  .component-body(
    :style='{ "background-color": colorMenu }',
    :class='{ submenu: isSubmenuActive }'
  )
    nav
      ul
        el-tooltip(
          effect='dark',
          content='Acesso a tela de módulos',
          placement='right'
        )
          img.logo(@click='goToSelecaoModulos', src='@/assets/logo.png')
        div(v-for='item in menu')
          template(
            v-if='item.isItemMenu && !isSubmenuActive && !isEmprestimoSubMenuActive'
          )
            el-tooltip(
              effect='dark',
              :content='item.nome',
              placement='right',
              v-can:tela='item.permissaoTela'
            )
              li.menu-item(
                :class='{ active: $route.name == item.routeName }',
                @click='goTo(item)'
              )
                .menu-group
                  img(:src='item.icon')
                  .menu-text(v-if='item.nome.length > 34')
                    span {{ item.nome.slice(0, 34) }}...
                  .menu-text(v-if='item.nome.length <= 34')
                    span {{ item.nome.slice(0, 34) }}
          template(
            v-if='item.isItemSubMenu && isSubmenuActive && !isEmprestimoSubMenuActive'
          )
            el-tooltip(
              effect='dark',
              :content='item.nome',
              placement='right',
              v-can:tela='item.permissaoTela'
            )
              li.sub-menu-item(
                :class='{ "active-sub-menu": $route.name == item.routeName }',
                @click='goTo(item)'
              )
                .menu-group
                  img(:src='item.icon')
                  .menu-text(v-if='item.nome.length > 34')
                    span {{ item.nome.slice(0, 34) }}...
                  .menu-text(v-if='item.nome.length <= 34')
                    span {{ item.nome.slice(0, 34) }}
          template(
            v-if='item.isEmprestimoSubMenu && !isSubmenuActive && isEmprestimoSubMenuActive'
          )
            el-tooltip(
              effect='dark',
              :content='item.nome',
              placement='right',
              v-can:tela='item.permissaoTela'
            )
              li.sub-menu-item(
                :class='{ "active-sub-menu": $route.name == item.routeName }',
                @click='goTo(item)'
              )
                .menu-group
                  img(:src='item.icon')
                  .menu-text(v-if='item.nome.length > 34')
                    span {{ item.nome.slice(0, 34) }}...
                  .menu-text(v-if='item.nome.length <= 34')
                    span {{ item.nome.slice(0, 34) }}
</template>

<script>
import { mapGetters } from 'vuex';
import AppDialog from '@/components/AppDialog';
import messages from '@/constants/messages';

export default {
  name: 'SideMenu',

  components: {
    AppDialog
  },

  data () {
    return {
      isSubmenuActive: false,
      isEmprestimoSubMenuActive: false,
      textDialog: '',
      showDialog: false,
      routeName: null,
      colorMenu: '#5181FB'
    };
  },

  computed: {
    ...mapGetters({
      menu: 'menu/items'
    })
  },

  methods: {
    goTo ({ nome, routeName, isItemSubMenu, isEmprestimoSubMenu }) {
      if (nome === 'Movimentação') {
        this.isSubmenuActive = true;
        this.colorMenu = '#23B5D3';
      }

      if (nome === 'Empréstimo') {
        this.isSubmenuActive = false;
        this.isEmprestimoSubMenuActive = true;
        this.colorMenu = '#23B5D3';
      }

      if (nome === 'Voltar') {
        if (isItemSubMenu) {
          this.isSubmenuActive = false;
          this.isEmprestimoSubMenuActive = false;
        }

        if (isEmprestimoSubMenu) {
          this.isSubmenuActive = true;
          this.isEmprestimoSubMenuActive = false;
        } else {
          this.colorMenu = '#5181FB';
          this.isSubmenuActive = false;
          this.isEmprestimoSubMenuActive = false;
        }
      }

      if (routeName === this.$route.name || routeName.length === 0) return;

      if (
        nome !== 'Movimentação' ||
        nome !== 'Empréstimo' ||
        nome !== 'Voltar'
      ) {
        this.routeName = routeName;
        if (this.$route.params.messageExitPage) {
          this.showDialog = true;
          this.textDialog = messages.CANCELAR_CADASTRO;
          return;
        }
        this.$router.push({
          name: routeName
        });
      }
    },
    cancelar () {
      this.showDialog = false;
    },

    confirmar () {
      this.showDialog = false;
      this.$router.push({
        name: this.routeName
      });
    },

    goToSelecaoModulos () {
      window.location.href =
        'http://' +
        (process.env.NODE_ENV === 'development'
          ? window.location.hostname + ':8081'
          : window.location.hostname) +
        '/#/selecionarModulo';
    }
  }
};
</script>

<style lang="scss" scoped>
@import '@/style/variables.scss';

.component-body {
  width: 145px;
  display: flex;
  position: relative;
  height: 100vh;
  overflow-y: scroll;

  nav {
    width: 100%;
    color: #ffffff;
    -webkit-box-shadow: 0px 0px 7px -5px #eaeef1;
    box-shadow: 8px 0px 7px -5px #eaeef1;

    ul {
      padding: 0;
      margin: 0;
    }

    .menu-item,
    .sub-menu-item {
      padding: 5px;
      height: 90px;
      display: flex;
      align-items: center;
      transition: hover 1s;
      cursor: pointer;
      margin-top: 20px;

      .menu-group {
        width: 100%;
        height: 100%;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;

        i {
          font-size: 20px;
        }
      }
    }
  }
}

.component-body::-webkit-scrollbar {
  width: 5px;
}

.component-body::-webkit-scrollbar-track {
  background: $cor-padrao;
}

.component-body::-webkit-scrollbar-thumb {
  background-color: $cor-principal;
  border-radius: 20px;
}

.component-body.submenu::-webkit-scrollbar-thumb {
  background-color: $cor-secundaria-dark;
}

.active,
.menu-item:hover {
  background: transparent linear-gradient(180deg, #366efa 0%, #2556ce 100%) 0%
    0% no-repeat padding-box;
}

.active-sub-menu,
.sub-menu-item:hover {
  background: $cor-secundaria-dark;
}

.menu-text {
  margin-top: 5px;
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  font-size: 14px;
}

.logo {
  width: 40px;
  display: block;
  margin: 30px auto;
  cursor: pointer;
}

@media (max-width: 1019px) {
  .component-body {
    width: 100px;
    nav {
      .menu-item {
        height: 70px;
      }
    }
  }
  .menu-icon {
    font-size: 20px;
  }
  .menu-text {
    display: none;
  }
}
</style>
