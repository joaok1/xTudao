<template lang="pug">
  .main
    el-row(:gutter="20")
      el-col(:md="12" :sm="24")
        label(:class= "!archive.nomeArquivo && archive.files.length !== 0 ? 'required' : ''") Documento:
        el-input(
          autocomplete="off"
          v-model="archive.nomeArquivo"
          maxlength="100"
          show-word-limit
        )
        span.error(v-if="!archive.nomeArquivo.trim() && archive.files.length !== 0") O campo é obrigatório
      el-col(v-if="haveDate" :md="6" :sm="12")
        label Data de vencimento:
        el-date-picker(
          v-model="archive.dataVencimento",
          type="date",
          placeholder="Selecione uma data",
          format="dd/MM/yyyy"
          value-format="dd/MM/yyyy"
        )
    el-upload(
      drag
      :key="uploadKey"
      action="https://jsonplaceholder.typicode.com/posts/"
      :show-file-list="true"
      :file-list="archive.files"
      :on-success="onSuccess"
      :on-error="onError"
      :auto-upload="false"
      :on-change="change"
      :on-remove="remove"
    )
      div.upload
        font-awesome-icon.uploadIcon(icon="cloud-upload-alt", size="5x")
        span Selecione o arquivo para enviar <br/> ou arraste e solte aqui

    .spacingButton
      el-button.button(@click="addArchive", type="secondary", :disabled="!archive.nomeArquivo.trim() || archive.files.length === 0 || loading")
        font-awesome-icon(:icon="['fas', 'plus']")
        span.buttonText Adicionar

    el-table(
      :empty-text="emptyText"
      :data="tableData"
    )
      el-table-column(
        prop="documento.nomeArquivo",
        label="Documento"
      )
      el-table-column(
        v-if="haveDate"
        prop="dataVencimento",
        label="Data de vencimento"
      )
        template( slot-scope="scope")
          span {{scope.row.dataVencimento ? scope.row.dataVencimento : 'Não há data de vencimento'}}
      el-table-column(
        label="Ações"
        width="250"
        align="center"
      )
        template(slot-scope='scope')
          el-tooltip( effect='dark', :content='downloadText', placement="bottom")
            font-awesome-icon.download(:icon="['fas', 'folder-download']", @click="fileDownload(scope.$index, tableData)")
          el-tooltip( effect='dark', :content="removeText", placement="bottom")
            font-awesome-icon.trash(:icon="['far', 'trash-alt']", @click="deleteRow(scope.$index, tableData)")

</template>

<script>
import panel from '@/components/Panel';
import Notification from '@/components/mixins/Notifications.vue';
import { mapActions } from 'vuex';
import FileSaver from 'file-saver';
import JsZip from 'jszip';
import messages from '@/constants/messages';

export default {
  data () {
    return {
      archive: {
        nomeArquivo: '',
        dataVencimento: '',
        pathArquivo: '',
        files: []
      },
      tableData: [],
      uploadKey: 0,
      loading: false
    };
  },
  mounted () {
    this.tableData = this.data ? this.data : [];
  },
  watch: {
    files (value) {
      this.$emit('input', value);
    },
    data: {
      handler (value) {
        this.files = value;
        this.tableData = value;
      },
      deep: true
    }
  },

  props: {
    files: {
      type: Array,
      required: false,
      default: () => []
    },
    haveDate: {
      type: Boolean,
      required: false,
      default: false
    },
    acceptedExtensions: {
      type: String,
      default: ''
    },
    data: {
      type: Array
    },
    emptyText: {
      type: String,
      required: false,
      default: messages.RESULTADOS_NAO_ENCONTRADOS
    },
    removeText: {
      type: String,
      required: false,
      default: 'Remover documento'
    },
    downloadText: {
      type: String,
      required: false,
      default: 'Baixar arquivo'
    }
  },

  components: {
    panel
  },

  mixins: [Notification],

  methods: {
    ...mapActions({
      getFileByKey: 'files/getFileByKey',
      upload: 'files/upload'
    }),

    onSuccess () {
      this.$message.success('Arquivo importado com succeso');
    },

    onError () {
      this.$message.error('Ocorreu um erro ao importar o arquivo');
    },

    async addArchive () {
      this.loading = true;
      let arquivo = null;
      if (this.archive.files.length > 1) {
        var zip = new JsZip();
        this.archive.files.forEach(file => {
          zip.file(file.name, file.raw);
        });
        arquivo = await zip.generateAsync({ type: 'blob' });
        this.archive.nomeArquivo = this.archive.nomeArquivo.trim() + '.zip';
      } else {
        arquivo = this.archive.files[0].raw;
        this.archive.nomeArquivo = this.archive.nomeArquivo.trim() + '.' + arquivo.name.split('.').pop();
      }
      if (arquivo.size / 1000000 >= 20) {
        this.alertError('Os arquivos inseridos ultrapassam o tamanho máximo permitido de 20 MB');
      } else {
        const result = await this.upload(arquivo);
        this.archive.pathArquivo = result.data;
        let archiveCopy = { ...this.archive };
        this.files.push({
          documento: archiveCopy,
          dataVencimento: archiveCopy.dataVencimento
        });
      }
      this.loading = false;
      this.resetObject(this.archive);
      this.archive.files = [];
    },

    change (file) {
      const arrayFileName = file.name.split('.');
      if (arrayFileName[arrayFileName.length - 1] === 'pdf') {
        this.archive.files.push(file);
      } else {
        this.alertInfo('Apenas arquivos PDF são suportados!', 'Atenção!');
      }
      this.uploadKey++;
    },

    remove (file, fileList) {
      this.archive.files = fileList;
    },

    resetObject (object) {
      for (let property in object) {
        object[property] = '';
      }
    },

    deleteRow (index, rows) {
      rows.splice(index, 1);
    },
    async fileDownload (index, rows) {
      const temp = !rows[index].id;
      const response = await this.getFileByKey({
        path: rows[index].documento.pathArquivo,
        isTemp: temp
      });
      const blob = new Blob([response.data]);
      FileSaver.saveAs(blob, rows[index].documento.nomeArquivo);
    }
  }
};
</script>

<style lang="scss" scoped>
@import '@/style/variables';
.uploadIcon {
  color: #555555;
}

.upload {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
}

.download {
  margin-right: 10px;
  color: $cor-principal;
  cursor: pointer;
}

.trash {
  cursor: pointer;
  color: red
}

.spacingButton {
  display: flex;
  justify-content: flex-end;
  padding-bottom: 10px;
  padding-top: 10px;
}

</style>
