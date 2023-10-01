<template lang="pug">
div
    panel(
      title='Dados pessoais',
    )
      el-input(
        v-model='colaborador.nome',
        style="margin:1em"
        placeholder='Nome completo:',
      )
      el-input(
        v-model='colaborador.sobrenome',
        style="margin:1em"
        placeholder='Sobrenome:',
      )
      el-row
        el-col
          el-col(:span="4", style="padding:10px")
            el-select(
              v-model='colaborador.sexo',
              :initialValues='colaborador.sexo',
              name='sexo',
              style="margin:1em; width:100%"
              placeholder='Sexo:',
              rules='required',
              :options='listSexo',
              optionKey='id',
              optionplaceholder='genero',
              optionValueKey='genero'
                )
          el-col(:span="6", style="padding:10px")
            el-date-picker(
              v-model='colaborador.dataNascimento',
              name='dataNascimento',
              style="margin:1em; width:100%"
              placeholder='Selecione uma data',
              format='dd/MM/yyyy',
              type='date',
              value-format='dd/MM/yyyy',
              align='center'
            )
          el-col(:span="4", style="padding:10px")
            el-input(
              placeholder='CPF:',
              style="margin:1em"
              rules='required',
              v-model='colaborador.cpfCnpj',
              :valorInicial='colaborador.cpfCnpj',
              v-mask='\'###.###.###-##\'',
              :blur='validateCpf'
            )
          el-col(:span="6", style="padding:10px")
            el-input(
              placeholder='Senha:',
              style="margin:1em; width:100%"
              show-password="false"
              rules='required',
              type="password"
              v-model='colaborador.usuario.senha',
              :valorInicial='colaborador.cpfCnpj',
            )
      el-row
        el-col(:span="4", style="padding:10px")
          el-input(
            placeholder='Telefone:',
            style="margin:1em"
            rules='required',
            v-model='colaborador.telefone',
            :valorInicial='colaborador.telefone',
            v-mask='\'(##) #####-####\''
          )
        el-col(:span="4", style="padding:10px")
          el-input(
            placeholder='CEP:',
            style="margin:1em"
            rules='required',
            v-model='colaborador.cep',
            :valorInicial='colaborador.cep',
            v-mask='\'#####-###\'',
            :blur='searchCep',
            @change='searchCep'
          )
        el-col(:span="6", style="padding:10px")
          el-select(
            v-model='colaborador.municipioEndereco.id',
            :initialValues='colaborador.municipioEndereco.id',
            name='municipio',
            style="margin:1em; width:100%"
            :disabled='cepEncontrado',
            placeholder='Município:',
            rules='required',
            :options='listMunicipioEndereco',
            optionKey='id',
            optionplaceholder='nome',
            optionValueKey='id'
          )
      el-row(:md='12', :sm='24')
        el-col(:span="4", style="padding:10px")
          el-input(
            placeholder='Email:',
            rules='required',
            type='email',
            style="margin:1em"
            v-model='colaborador.email',
            :valorInicial='colaborador.email'
          )
        el-col(:span="4", style="padding:10px")
          el-input(
            placeholder='Logradouro:',
            rules='required',
            style="margin:1em"
            :disabled='logradouroEncontrado',
            v-model='colaborador.logradouro',
            :valorInicial='colaborador.logradouro',
            maxlength='300'
          )
        el-col(:span="6", style="padding:10px")
          el-input(
            placeholder='Número:',
            rules='required',
            style="margin:1em"
            v-model='colaborador.numero',
            :valorInicial='colaborador.numero'
          )
      el-row
        el-col(:span="4", style="padding:10px")
            el-input(
              placeholder='Bairro:',
              rules='required',
              style="margin:1em"
              :disabled='bairroEncontrado',
              v-model='colaborador.bairro',
              :valorInicial='colaborador.bairro'
            )
        el-col(:span="4", style="padding:10px")
          el-input(
            placeholder='Estado:',
            rules='required',
            style="margin:1em"
            :disabled='bairroEncontrado',
            v-model='colaborador.estado',
            :valorInicial='colaborador.estado'
          )
        el-col(:span="6", style="padding:10px")
          el-select(
            v-model='colaborador.dadosEmpresarialAtual && colaborador.dadosEmpresarialAtual.cargo.id',
            :initialValues='colaborador.dadosEmpresarialAtual && colaborador.dadosEmpresarialAtual.cargo.id',
            name='cargo',
            placeholder='Cargo:',
            style="margin:1em; width:100%;"
            rules='required',
            :options='listCargos',
            optionKey='id',
            optionplaceholder='nome',
            optionValueKey='id'
          )
    .footer
      el-button.btnCancelar(@click='this.$route.push("cadastrarFuncionarios")')
        font-awesome-icon.icon(:icon='["fas", "times"]')
        span Cancelar

      el-button.btnSalvar(@click='salvar', type='primary')
        font-awesome-icon.icon(:icon='["fas", "save"]')
        span {{ " " + "Salvar" }}
</template>

<script>
import Panel from "@/components/Panel.vue";
import configs from "@/constants/configs";
import SelectCampoObrigatorioVue from "@/components/SelectCampoObrigatorio.vue";
import InputCampo from "@/components/InputCampoObrigatorio.vue";

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
          senha: null,
        },
        sobrenome: null,
        nome: null,
        nomeDaMae: null,
        nomeDoPai: null,
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
        cpfCnpj: null,
        rg: null,
        cnhCategoria: [],
        dataNascimento: null,
        sexo: null,
        estadoCivil: null,
        pis: null,
        tituloEleitor: null,
        validade: null,
        municipioNaturalidade: {
          id: null,
        },
        municipioEndereco: {
          id: null,
        },
        bairro: null,
        logradouro: null,
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
    async searchCep() {
      this.cepEncontrado = false;
      if (!this.colaborador.cep) {
        return this.alertWarning("Digite um CEP!");
      } else if (this.colaborador.cep.length < 9) {
        return this.alertWarning("Formato do cep incorreto.");
      } else {
        const axios = require("axios").default;
        this.buscarCep = await axios.get(
          `https://viacep.com.br/ws/${this.colaborador.cep}/json/`
        );
        if (this.buscarCep.data.erro) {
          return this.alertWarning("Cep inexistente!");
        }
        this.cepEncontrado = true;
        this.buscarCep = this.buscarCep.data;
        this.buscarCep.bairro !== ""
          ? (this.colaborador.bairro = this.buscarCep.bairro)
          : (this.colaborador.bairro = null);
        this.buscarCep.logradouro !== ""
          ? (this.colaborador.logradouro = this.buscarCep.logradouro)
          : (this.colaborador.logradouro = null);
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
      }
    },
    goToSelecaoModulos() {
      window.location.href =
        "http://" +
        (process.env.NODE_ENV === "development"
          ? window.location.hostname + ":8081"
          : window.location.hostname) +
        "/#/selecionarModulo";
    },
    openDialog() {
      this.textDialog = this.$route.params.messageExitPage;
      this.showDialog = true;
    },
    confirmar() {
      this.redirectToListagem();
    },
    formatDeppendentData() {
      this.colaborador.dependentes.map((v) => {
        if (v.parentesco === "Mãe") {
          v.parentesco = "MAE";
        } else if (v.parentesco === "Avó") {
          v.parentesco = "AVO";
        } else if (v.parentesco === "Avô") {
          v.parentesco = "AVO_MASCULINO";
        } else if (v.parentesco === "Sobrinho(a)") {
          v.parentesco = "SOBRINHO";
        } else if (v.parentesco === "Filho(a)") {
          v.parentesco = "FILHO";
        } else {
          v.parentesco = "PAI";
        }
      });
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
