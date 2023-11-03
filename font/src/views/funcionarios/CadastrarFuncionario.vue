<template lang="pug">
div
    panel(
      title="Dados Pessoais"
    )
      el-row
          el-col(:span="8")
            span Nome:
            el-input(
              v-model='colaborador.nome',
              clearable
              placeholder='Nome completo:',
            )
          el-col(:span="8")
            span Sobrenome:
            el-input(
              v-model='colaborador.sobrenome',
              placeholder='Sobrenome:',
              clearable
            )
          el-col(:span="4")
            span Sexo:
            el-select(
              v-model='colaborador.sexo',
              :initialValues='colaborador.sexo',
              name='sexo',
              placeholder='Sexo:',
              clearable
            )
              el-option(
                v-for="item in sexo"
                :key="item.value"
                :label="item.value"
                :value="item.value"
              )
          el-col(:span="4")
            span Data de nascimento:
            el-date-picker(
              v-model='colaborador.data_nascimento',
              clearable
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
            placeholder='CPF:',
            clearable
            rules='required',
            v-model='colaborador.cpf',
            :valorInicial='colaborador.cpf',
            v-mask='\'###.###.###-##\'',
            :blur='validateCpf'
          )
        el-col(:span="4")
          span Cargo:
          el-select(
            placeholder="Selecione o cargo",
            clearable
            v-model='colaborador.role'
          )
            el-option(
              v-for="item in role"
              :key="item.value"
              :label="item.value"
              :value="item.value"
            )
    panel(
        title="Endereço"
    )
      el-row
        el-col(:span="4")
          span CEP:
          el-input(
            placeholder='CEP:',
            rules='required',
            clearable
            v-model='colaborador.cep',
            :valorInicial='colaborador.cep',
            v-mask='\'#####-###\'',
            :blur='searchCep',
            @change='searchCep'
          )
        el-col(:span="6")
          span Município:
          el-input(
            v-model='colaborador.cidade',
            name='municipio',
            clearable
            :disabled='cepEncontrado',
            placeholder='Município:',
          )
        el-col(:span="6", style="padding:10px")
          span Logradouro:
          el-input(
            placeholder='Logradouro:',
            rules='required',
            clearable
            :disabled='logradouroEncontrado',
            v-model='colaborador.endereco',
            :valorInicial='colaborador.endereco',
            maxlength='300'
          )
        el-col(:span="6")
          span Número:
          el-input(
            placeholder='Número:',
            rules='required',
            clearable
            v-model='colaborador.numero',
            :valorInicial='colaborador.numero'
          )
          el-row
        el-col(:span="4")
          span Bairro:
          el-input(
            placeholder='Bairro:',
            rules='required',
            clearable
            :disabled='bairroEncontrado',
            v-model='colaborador.bairro',
            :valorInicial='colaborador.bairro'
          )
        el-col(:span="4")
          span Estado:
          el-input(
            placeholder='Estado:',
            rules='required',
            clearable
            :disabled='bairroEncontrado',
            v-model='colaborador.estado',
            :valorInicial='colaborador.estado'
          )
    panel(
      title="Contatos"
    )
      el-row
        el-col(:span="8")
          span Email:
          el-input(
            placeholder='Email:',
            rules='required',
            type='email',
            clearable
            v-model='colaborador.email',
            :valorInicial='colaborador.email'
          )
        el-col(:span="4")
          span Telefone:
          el-input(
            placeholder='Telefone:',
            rules='required',
            clearable
            v-model='colaborador.telefone',
            :valorInicial='colaborador.telefone',
            v-mask='\'(##) #####-####\''
          )
    panel(
      title="Usuário"
    )
      el-row
        el-col(:span="8")
          span Senha:
          el-input(
            placeholder='Senha:',
            show-password
            rules='required',
            clearable
            type="password"
            v-model='colaborador.usuario.senha',
            :valorInicial='colaborador.cpfCnpj',
          )
      span.warning * O login de acesso será equivalente ao CPF


    .footer
      el-button(type='danger' @click='voltarListagemFuncionario()' icon="el-icon-close")
        span Cancelar

      el-button.btnSalvar(@click='salvar()', type='success' icon="el-icon-success")
        span {{ " " + "Salvar" }}
</template>

<script>
import Panel from "@/components/Panel.vue";
import configs from "@/constants/configs";
import SelectCampoObrigatorioVue from "@/components/SelectCampoObrigatorio.vue";
import InputCampo from "@/components/InputCampoObrigatorio.vue";
import funcoes from "@/methods/funções";

export default {
  name: "CadastroColaboradores",
  components: {
    Panel,
    InputCampo,
    SelectCampoObrigatorioVue,
  },
  data() {
    return {
      contador: 0,
      sexo: [
        {
          value: "MASCULINO",
        },
        {
          value: "FEMININO",
        },
      ],
      role: [
        {
          value: "ADMIN",
        },
        {
          value: "USER",
        },
        {
          value: "GERENTE",
        },
        {
          value: "CAIXA",
        },
        {
          value: "VENDEDOR",
        },
      ],
      money: {
        decimal: ",",
        thousands: ".",
        prefix: "R$ ",
        suffix: "",
        precision: 2,
        masked: false,
      },
      boolAddBanco: false,
      cep: null,
      possuiDependentes: false,
      date: null,
      dataAdmissao: null,
      textDialog: "",
      showDialog: false,
      listCategoria: configs.TIPOS_HABILITACAO,
      listParentesco: configs.PARENTESCO,
      listNacionalidade: configs.PAISES,
      cpfInvalido: true,
      dependente: {
        nome: null,
        cpf: null,
        rg: null,
        parentesco: null,
      },
      agencia: null,
      conta: null,
      banco: null,
      novoCadastro: false,
      colaborador: {
        usuario: {
          login: null,
          senha: null,
        },
        sobrenome: null,
        nome: null,
        nomeDaMae: null,
        nomeDoPai: null,
        role: null,
        uf: {
          id: null,
        },
        reservista: null,
        rgDataExpedicao: null,
        dependentes: [],
        funcionario: {
          supervisorImediato: {
            id: null,
            pessoa: {
              id: null,
            },
          },
          dadosBancarios: [],
        },
        cpf: null,
        rg: null,
        cnhCategoria: [],
        data_nascimento: null,
        sexo: null,
        estadoCivil: null,
        pis: null,
        tituloEleitor: null,
        validade: null,
        municipioNaturalidade: {
          id: null,
        },
        cidade: null,
        endereco: null,
        numero: null,
        cep: null,
        email: null,
        telefone: null,
        escolaridade: null,
        quantidadeDependentes: 0,
        nacionalidade: "Brasil",
        keysDocumentos: [],
        salario: null,
        fotos: [
          {
            id: null,
            pathArquivo: null,
            nomeArquivo: null,
          },
        ],
        dadosEmpresarialAtual: {
          idCargo: null,
          cargo: {
            id: null,
          },
          idLocalAtuacao: null,
          localAtuacao: {
            id: null,
          },
          nacionalidade: null,
          matricula: null,
          areaAtuacao: null,
          dataAdmissao: null,
          valeAlimentacao: false,
          assistenciaMedica: false,
          valeTransporte: false,
          salario: null,
          valorValeAlimentacao: null,
          valorAssistenciaMedica: null,
          valorValeTransporte: null,
        },
      },
      listSexo: [],
      block: 0,
      listEstado: [],
      listBancos: [],
      listEstadoCivil: [],
      listLocalAtuacao: [],
      listSetor: [],
      url: null,
      listCargos: [],
      listEscolaridade: [],
      listMunicipio: [],
      listMunicipioEndereco: [],
      listFuncionarios: [],
      valeAlimentacao: false,
      assistenciaMedica: false,
      valeTransporte: false,
      idColaborador: null,
      bairroEncontrado: false,
      logradouroEncontrado: false,
      dependentes: [],
      cepEncontrado: false,
      dataReady: false,
      columns: [
        {
          placeholder: "Nome",
          prop: "nome",
        },
        {
          placeholder: "CPF",
          prop: "cpf",
        },
        {
          placeholder: "Rg",
          prop: "rg",
        },
      ],
      documentos: [],
      valorValeAlimentacao: null,
      valorValeTransporte: null,
    };
  },
  async mounted() {},

  methods: {
    sendFormattedCPF(dados) {
      const cpfWithoutFormat = dados.replace(/[^\d]/g, "");
      return (this.colaborador.cpf = cpfWithoutFormat);
    },
    async salvar() {
      try {
        this.colaborador.cpf = this.sendFormattedCPF(this.colaborador.cpf);
        this.colaborador.usuario.login = this.colaborador.cpf;
        const value = await funcoes.cadastroFuncionario(this.colaborador);
        if (value) {
          this.$notify({
            title: "Success",
            message: "Usuário cadastrado com sucesso",
            type: "success",
          });
          this.$router.push({
            name: "listagemFuncionarios",
          });
        }
      } catch (error) {
        this.$notify.error({
          title: "Error",
          message: "Erro ao cadastar usuário, verifique todos os campos",
        });
      }
    },
    voltarListagemFuncionario() {
      this.$router.push({
        name: "listagemFuncionarios",
      });
    },
    async searchCep() {
      this.cepEncontrado = false;
      const axios = require("axios").default;
      this.buscarCep = await axios.get(
        `https://viacep.com.br/ws/${this.colaborador.cep}/json/`
      );
      console.log(this.buscarCep);
      this.cepEncontrado = true;
      this.buscarCep = this.buscarCep.data;
      this.colaborador.cidade = this.buscarCep.localidade;
      this.colaborador.estado = this.buscarCep.uf;
      this.buscarCep.bairro !== ""
        ? (this.colaborador.bairro = this.buscarCep.bairro)
        : (this.colaborador.bairro = null);
      this.buscarCep.logradouro !== ""
        ? (this.colaborador.endereco = this.buscarCep.logradouro)
        : (this.colaborador.endereco = null);
      this.colaborador.municipioEndereco.id = Number(this.buscarCep.ibge);
      if (this.buscarCep.logradouro) {
        this.logradouroEncontrado = true;
        this.block = 0;
      } else {
        this.block = 1;
      }
      if (this.buscarCep.bairro) {
        this.block = 0;
        this.buscarCep.bairro = true;
      } else {
        this.block = 1;
      }
    },
    redirectToListagem() {
      this.$router.push({
        name: "gestaoColaboradores/listagem",
      });
    },
    validateCep() {
      if (!this.colaborador.cep) {
        return this.alertWarning("Este CEP é inválido!");
      }
    },
    validateCpfDependente() {
      if (!this.isValidCPF(this.dependente.cpf)) {
        this.cpfInvalido = true;
        return this.alertWarning("Este CPF é inválido!");
      } else {
        this.cpfInvalido = false;
      }
    },
    validateCpf() {
      if (!this.isValidCPF(this.colaborador.cpfCnpj)) {
        return this.alertWarning("Este CPF é inválido!");
      }
    },
    isValidCPF(cpf) {
      if (cpf === undefined || cpf === null) return false;

      if ((cpf = cpf.replace(/[^\d]/g, "")).length !== 11) return false;

      const cpfInvalidos = [
        "00000000000",
        "11111111111",
        "22222222222",
        "33333333333",
        "44444444444",
        "55555555555",
        "66666666666",
        "77777777777",
        "88888888888",
        "99999999999",
      ];

      if (cpfInvalidos.indexOf(cpf) > -1) return false;

      let r, i;
      let s = 0;
      for (i = 1; i <= 9; i++) s += parseInt(cpf[i - 1]) * (11 - i);
      r = (s * 10) % 11;
      if (r === 10 || r === 11) r = 0;
      if (r !== parseInt(cpf[9])) return false;
      s = 0;
      for (i = 1; i <= 10; i++) s += parseInt(cpf[i - 1]) * (12 - i);
      r = (s * 10) % 11;

      if (r === 10 || r === 11) r = 0;

      if (r !== parseInt(cpf[10])) return false;

      return true;
    },
  },
  watch: {
    async "colaborador.uf.id"(val) {
      if (val) {
        const municipios = await this.findMunicipioByIdEstado(val);
        this.listMunicipio = this.setListMunicipio(municipios);
      }
    },
    "colaborador.cep"(val) {
      if (!val) {
        this.cepEncontrado = false;
        this.bairroEncontrado = false;
        this.logradouroEncontrado = false;
      }
    },
    "colaborador.bairro"(val) {
      if (val && this.block < 1) {
        this.bairroEncontrado = true;
      } else {
        this.bairroEncontrado = false;
      }
    },
    "colaborador.logradouro"(val) {
      if (val && this.block < 1) {
        this.logradouroEncontrado = true;
      } else {
        this.logradouroEncontrado = false;
      }
    },
    "colaborador.municipioEndereco"(val) {
      if (val) {
        this.cepEncontrado = true;
      } else {
        this.cepEncontrado = false;
      }
    },
    "colaborador.funcionario.dadosBancarios"(val) {
      if (val.length > 0) {
        this.boolAddBanco = true;
      } else {
        this.boolAddBanco = false;
      }
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
