<template lang="pug">
.flex
  .topo
    span.title {{ this.$route.meta.routeTitle }}
    .user
      .usuario
        font-awesome-icon.icon(:icon='["fas", "user-circle"]')
        .descricao
          span &nbsp &nbsp {{ nomeUsuario }}
      .logout(@click='sair')
        font-awesome-icon.icon(:icon='["fas", "sign-out-alt"]')
</template>

<script>
import { mapActions } from 'vuex';
import AppActionButton from '@/components/AppActionButton';

export default {
  components: {
    AppActionButton
  },

  data () {
    return {
      nomeUsuario: ''
    };
  },

  async updated () {
    let usuario = await this.getUsuarioObject();
    if (usuario !== 'null') {
      let usuarioJson = JSON.parse(usuario);
      this.nomeUsuario = usuarioJson.nome;
    }
  },

  methods: {
    ...mapActions({
      logout: 'auth/logout',
      getUsuarioObject: 'auth/getUsuarioObject'
    }),
    async sair () {
      if (process.env.NODE_ENV === 'development') {
        window.location.href = 'http://' + window.location.hostname + ':8081/#/logout';
      } else if (process.env.NODE_ENV === 'prodAgedoceGestaoAdministrativa') {
        window.location.href = 'http://' + window.location.hostname + '/agedoce/#/logout';
      } else {
        window.location.href = 'http://' + window.location.hostname + '/#/logout';
      }
      await this.logout();
    },
    goHome () {
      this.$router.push({ name: 'home' });
    }
  }
};
</script>

<style lang="scss" scoped>
@import '@/style/variables.scss';
.flex {
  display: flex;
}
.topo {
  width: 100%;
  height: $height-cabecalho;
  display: flex;
  align-items: center;
  padding: 0 33px 0 0;
  justify-content: space-between;
  background-color: #f0f2f5;
  font-size: xx-large;

  .title {
    font-size: 30px;
    font-weight: bold;
    color: #4b4b4b;
  }
  span {
    font-size: 16px;
    font-weight: bolder;
    color: #424242;
    display: flex;
  }
  .user {
    display: flex;
    align-items: center;
    color: #a7b2bb;
    justify-content: space-between;

    .usuario {
      display: flex;
      align-items: center;
      .descricao {
        text-align: left;
        display: flex;
        flex-direction: column;
        margin-left: 5px;
        .nomePerfil {
          opacity: 0.8;
        }
      }
    }
    .logout {
      margin-left: 1vw;
      display: flex;
      align-items: center;
      font-size: 25px;
      cursor: pointer;
      .icon {
        font-size: inherit !important;
      }
    }
  }
}
</style>
