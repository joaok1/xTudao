<!-- eslint-disable vue/multi-word-component-names -->
<template lang="pug">
div
    panel(
      title="Dados Pessoais"
    )
      el-row
          el-col(:span="8")
            span Nome:
            el-input(
                disabled
                v-model='pessoa.nome',
                placeholder='Sobrenome:',
                clearable
            )
          el-col(:span="8")
            span Sobrenome:
            el-input(
                disabled
              v-model='pessoa.sobrenome',
              placeholder='Sobrenome:',
              clearable
            )
          el-col(:span="4")
            span Sexo:
            el-input(
                disabled
                v-model='pessoa.sexo',
                :initialValues='pessoa.sexo',
                name='sexo',
                placeholder='Sexo:',
                clearable
            )
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
          el-input(
            disabled
            placeholder='CPF:',
            clearable
            rules='required',
            v-model='pessoa.cpf',
            v-mask='\'###.###.###-##\'',
            :valorInicial='pessoa.cpf',
          )
        el-col(:span="4")
          span Cargo:
          el-input(
            disabled
            placeholder="Selecione o cargo",
            clearable
            v-model='pessoa.usuario.role.name'
          )
    panel(
        title="Endereço"
    )
      el-row
        el-col(:span="4")
          span CEP:
          el-input(
            disabled
            placeholder='CEP:',
            rules='required',
            clearable
            v-model='pessoa.cep',
            :valorInicial='pessoa.cep',
            v-mask='\'#####-###\'',
          )
        el-col(:span="6")
          span Município:
          el-input(
            disabled
            v-model='pessoa.cidade',
            name='municipio',
            clearable
            placeholder='Município:',
          )
        el-col(:span="6", style="padding:10px")
          span Logradouro:
          el-input(
            disabled
            placeholder='Logradouro:',
            rules='required',
            clearable
            v-model='pessoa.endereco',
            :valorInicial='pessoa.endereco',
            maxlength='300'
          )
        el-col(:span="6")
          span Número:
          el-input(
            disabled
            placeholder='Número:',
            rules='required',
            clearable
            v-model='pessoa.numero',
            :valorInicial='pessoa.numero'
          )
          el-row
        el-col(:span="4")
          span Bairro:
          el-input(
            disabled
            placeholder='Bairro:',
            rules='required',
            clearable
            v-model='pessoa.bairro',
            :valorInicial='pessoa.bairro'
          )
        el-col(:span="4")
          span Estado:
          el-input(
            disabled
            placeholder='Estado:',
            rules='required',
            clearable
            v-model='pessoa.estado',
            :valorInicial='pessoa.estado'
          )
    panel(
      title="Contatos"
    )
      el-row
        el-col(:span="8")
          span Email:
          el-input(
            disabled
            placeholder='Email:',
            rules='required',
            type='email',
            clearable
            v-model='pessoa.email',
            :valorInicial='pessoa.email'
          )
        el-col(:span="4")
          span Telefone:
          el-input(
            disabled
            placeholder='Telefone:',
            rules='required',
            clearable
            v-model='pessoa.telefone',
            :valorInicial='pessoa.telefone',
            v-mask='\'(##) #####-####\''
          )
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
</style>
