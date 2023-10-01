<script>
export default {
  methods: {
    alertSuccess (message, title = 'Sucesso') {
      if (message === undefined) {
        throw new Error('Forneça uma mensagem para a notificação!');
      }
      this.$toastr('success', message, title);
    },

    alertError (message = 'Tente novamente.', title = 'Erro!') {
      this.$toastr('error', message, title);
    },

    alertWarning (message = 'Tente novamente.', title = 'Atenção!') {
      this.$toastr('warning', message, title);
    },

    alertInfo (message, title) {
      if (title === undefined || message === undefined) {
        throw new Error('Forneça um título e uma mensagem para a notificação!');
      }
      this.$toastr('info', message, title);
    }
  }
};
</script>
