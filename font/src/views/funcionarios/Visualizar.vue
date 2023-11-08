<!-- eslint-disable vue/multi-word-component-names -->
<template lang="pug">
div
    panel(
      title="Dados Pessoais"
    )
      el-row
          el-col(:span="8")
            span Nome:
            p {{ pessoa.nome }}
          el-col(:span="8")
            span Sobrenome:
            p {{ pessoa.sobrenome }}
          el-col(:span="4")
            span Sexo:
            p {{ pessoa.sexo }}
          el-col(:span="4")
            span Data de nascimento:
            el-date-picker(
              v-model='pessoa.data_nascimento',
              clearable
              disabled
              name='dataNascimento',
              style="width:100%"
              placeholder='Selecione uma data',
              format='dd/MM/yyyy',
              type='date',
              value-format='dd/MM/yyyy',
              align='center'
            )
      el-row
        el-col(:span="6")
          span CPF:
          p {{ this.sendFormattedCPF(pessoa.cpf) }}
        el-col(:span="4")
          span Cargo:
          p {{ pessoa.usuario.role.name }}
    panel(
        title="Endereço"
    )
      el-row
        el-col(:span="4")
          span CEP:
          p {{ pessoa.cep }}
        el-col(:span="6")
          span Município:
          p {{ pessoa.cidade }}
        el-col(:span="6", style="padding:10px")
          span Logradouro:
          p {{ pessoa.endereco }}
        el-col(:span="6")
          span Número:
          p {{ pessoa.numero }}
          el-row
        el-col(:span="4")
          span Bairro:
          p {{ pessoa.bairro }}
        el-col(:span="4")
          span Estado:
          p {{ pessoa.estado }}
    panel(
      title="Contatos"
    )
      el-row
        el-col(:span="8")
          span Email:
          p {{ pessoa.email }}
        el-col(:span="4")
          span Telefone:
          p {{ pessoa.telefone }}
    .footer
      el-button(type='info' @click='voltarListagemFuncionario()' icon="el-icon-back")
        span Voltar a listagem

</template>
<script>
import funcoes from "@/methods/funções";
import Panel from "@/components/Panel.vue";
export default {
  components: {
    Panel,
  },
  data() {
    return {
      idFuncionario: null,
      pessoa: {
        id: null,
        nome: null,
        sobrenome: null,
        data_nascimento: null,
        cpf: null,
        rg: null,
        cep: null,
        endereco: null,
        logradouro: null,
        estado: null,
        email: null,
        sexo: null,
        perfil: null,
        usuario: {
          id: null,
          login: null,
          senha: null,
          role: {
            id: null,
            name: null,
          },
          admin: false,
          documento: null,
          namePessoa: null,
        },
      },
    };
  },
  async mounted() {
    await this.findByIdPessoa(this.$route.params.idFuncionario);
  },
  methods: {
    sendFormattedCPF(dados) {
      const cpfWithoutFormat = dados.replace(
        /(\d{3})(\d{3})(\d{3})(\d{2})/,
        "$1.$2.$3-$4"
      );
      return cpfWithoutFormat;
    },
    voltarListagemFuncionario() {
      this.$router.push({
        name: "listFuncionarios",
      });
    },
    async findByIdPessoa(id) {
      const dados = await funcoes.findByIdPessoa(id);
      this.pessoa = dados.data;
      console.log(dados);
    },
  },
};
</script>

<style lang="scss" scoped>
* {
  padding: 10px;
}

span {
  font-weight: bold;
}

.warning {
  color: #ff0000;
}

.data {
  display: flex;
  margin-left: 5px;
  justify-content: center;
  align-content: center;
  height: 100%;
}

.footer {
  display: flex;
  justify-content: space-between;
}

.btnSalvar,
.btnCancelar {
  display: flex;
}

.icon {
  margin-right: 5px;
}

@media (max-width: 1385px) {
  .rowFoto {
    display: grid;
    grid-template-areas:
      "foto nameArea nameArea nameArea nameArea"
      "dataNascimentoArea dataNascimentoArea dataNascimentoArea dataNascimentoArea dataNascimentoArea";
  }

  .fotoArea {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 50%;
  }

  .nameArea {
    width: 100%;
  }

  .panel {
    background-color: #fff;
  }

  .titulo {
    font-weight: bolder;
  }

  .title-font {
    font-size: 17px;
  }

  .info {
    background-color: #dfcece;
    padding: 5px;
    margin: 3px;
  }
  .dataNascimentoArea,
  .rowpis,
  .cpf,
  .rg {
    width: 100%;
  }
}
span {
  font-weight: bold;
  font-size: 16px;
}

p {
  font-size: 14px;
}
</style>
