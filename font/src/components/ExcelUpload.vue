<template lang="pug">
div
  el-row
    el-upload(
      drag,
      action='',
      :key='uploadKey',
      :file-list='archive.files',
      :auto-upload='true',
      :on-change='uploadDocument',
      :before-upload='prepareUpload',
      :on-remove='remove'
    )
      .upload(@click='enviar')
        font-awesome-icon.uploadIcon(icon='cloud-upload-alt', size='5x')
        span Selecione o arquivo para enviar <br/> ou arraste e solte aqui

    el-table.margin(:empty-text='getMessageNotFound', :data='tableData')
      el-table-column(prop='documento.nomeArquivo', label='Documento')
      el-table-column(label='Ações', width='250', align='center')
        template(slot-scope='scope')
          el-tooltip(
            effect='dark',
            content='Baixar arquivo',
            placement='bottom'
          )
            font-awesome-icon.download(
              :icon='["fas", "folder-download"]',
              @click='fileDownload(scope.$index, tableData)'
            )
          el-tooltip(
            effect='dark',
            content='Remover documento',
            placement='bottom'
          )
            font-awesome-icon.trash(
              :icon='["far", "trash-alt"]',
              @click='deleteRow($event, scope.$index, tableData)'
            )
</template>

<script>
import Notification from '@/components/mixins/Notifications.vue';
import messages from '@/constants/messages';
import { mapActions } from 'vuex';
import FileSaver from 'file-saver';

export default {
  data () {
    return {
      archive: {
        nomeArquivo: '',
        dataVencimento: '',
        pathArquivo: '',
        files: []
      },
      score: null,
      uploadKey: 0,
      tableData: [],
      previewName: ''
    };
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
    acceptedExtensions: {
      type: String,
      default: ''
    },
    data: {
      type: Array
    }
  },

  mixins: [Notification],
  async mounted () {
    this.tableData = this.data ? this.data : [];
  },
  computed: {
    getMessageNotFound () {
      return messages.RESULTADOS_NAO_ENCONTRADOS;
    }
  },
  methods: {
    ...mapActions({
      getFileByKey: 'files/getFileByKey',
      upload: 'gestaoUpload/upload',
      createFile: 'files/upload'
    }),

    async enviar () {
      this.$emit('enviar', this.score);
    },

    prepareUpload (file) {
      this.tableData.splice(0);
      if (file.size / 1000000 >= 20) {
        this.alertError(
          'Os arquivos inseridos ultrapassam o tamanho máximo permitido de 20 MB'
        );
        return false;
      } else {
        this.previewName = file.name;
      }
    },

    async uploadDocument (file) {
      this.tableData.splice(0);
      this.score = file.raw;
      this.$emit('enviar', file.raw);
      this.archive.nomeArquivo = this.previewName.split('.')[0].toUpperCase() +
        '.' + this.previewName.split('.')[1];
      const arrayFileName = file.name.split('.');
      if (this.previewName !== '' && arrayFileName[arrayFileName.length - 1] === 'xlsx') {
        let result = await this.createFile(file.raw);
        if (result) {
          this.archive.pathArquivo = result.data;
          let archiveCopy = { ...this.archive };
          if (archiveCopy.nomeArquivo !== '') {
            this.files.push({
              documento: archiveCopy
            });
          }
          this.resetObject(this.archive);
          this.archive.files = [];
          this.previewName = '';
        }
      } else if (this.previewName !== '' && arrayFileName[arrayFileName.length - 1] !== 'xlsx') {
        this.alertInfo('Apenas arquivos XLS são suportados!', 'Atenção!');
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

    deleteRow (event, index, rows) {
      event.preventDefault();
      this.tableData.splice(rows[index]);
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

.marginLeft {
  margin-left: 8px;
}
.marginTop {
  margin-top: 20px;
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
  color: red;
  cursor: pointer;
}

.padding {
  padding: 20px 0;
}
.margin {
  margin: 20px 0 20px 0;
}
</style>
