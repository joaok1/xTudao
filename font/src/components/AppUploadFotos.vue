<template lang="pug">
div
  input(
    type='file',
    ref='fileInput',
    @change='handleFileChange',
    style='display: none',
    :acceptedExtensions='acceptedExtensions'
  )
  .upload-agevap(v-if='!fileAdded', @click='callEventClick')
    .upload-content
      i.icon-upload.el-icon-plus
      p.tip-upload {{ descricao }}
  div(v-if='fileAdded')
    img.imagem(:src='fileAdded', alt='Fotos da validação')
    .icones-upload
      span.preview.el-icon-zoom-in(@click='handlePictureCardPreview')
      span.download.el-icon-download(@click='handleDownload')
      span.remove.el-icon-delete(@click='handleRemove')
    el-dialog(:visible.sync='dialogVisible')
      img(width='100%', :src='fileAdded', alt='Fotos da validação')
</template>

<script>
import { mapActions } from 'vuex';
import FileSaver from 'file-saver';
import Notification from '@/components/mixins/Notifications.vue';

export default {
  name: 'AppUploadFotos',

  props: {
    descricao: {
      type: String,
      required: true,
      default: () => ''
    },
    acceptedExtensions: {
      type: String,
      required: false
    }
  },

  data () {
    return {
      value: null,
      fileAdded: null,
      fileName: null,
      dialogVisible: false,
      keyFileUpload: null
    };
  },
  mixins: [Notification],

  methods: {
    ...mapActions({
      uploadFile: 'files/upload',
      getFileByKey: 'files/getFileByKey'
    }),

    callEventClick () {
      this.$refs.fileInput.click();
    },

    async handleFileChange (files) {
      const file = files.target.files[0];
      this.fileName = file.name;
      const acceptedExtension = this.validateFile(file);
      if (!acceptedExtension) {
        return this.alertWarning(`Apenas extensões ${this.acceptedExtensions} são aceitas.`);
      }
      this.fileAdded = URL.createObjectURL(file);
      this.keyFileUpload = await this.uploadFile(file);
      this.$emit('input', this.keyFileUpload);
    },

    validateFile (file) {
      const indexStartExtension = file.name.lastIndexOf('.');
      const fileType = file.name.substring(indexStartExtension);
      const arrayAcceptedExtensions = this.acceptedExtensions.split(',');
      return arrayAcceptedExtensions.some(
        acceptedExtensions => acceptedExtensions.trim() === fileType
      );
    },

    async setFoto (pathFoto) {
      const response = await this.getFileByKey({
        path: pathFoto,
        isTemp: false
      });

      this.fileAdded = URL.createObjectURL(response.data);
      this.keyFileUpload = pathFoto;
      this.$emit('input', pathFoto);
    },

    handlePictureCardPreview () {
      this.dialogVisible = true;
    },

    handleDownload () {
      FileSaver.saveAs(
        this.fileAdded,
        this.fileName
      );
    },

    async handleRemove () {
      this.$emit('remove', this.keyFileUpload);
      this.$emit('input', null);
      this.$refs.fileInput.value = null;
      this.fileAdded = null;
      this.fileName = null;
      this.keyFileUpload = null;
    }
  }
};
</script>

<style lang="scss" scoped>
.upload-agevap {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  text-align: center;
  display: flex;
  justify-content: center;
  height: 148px;
  width: 148px;

  &:hover {
    border-color: #366efa;
    color: #366efa;
  }
}

.tip-upload {
  margin-top: 16px;
}

.error.upload-agevap {
  border: 1px dashed red !important;
}

.icon-upload {
  font-size: 30px;
}

.upload-content {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.imagem {
  display: flex;
  width: 148px;
  height: 148px;
  border-radius: 8px;
  transition: 0.1s;
}

.icones-upload {
  color: #366efa;
  position: relative;
  padding: 0 15px;
  top: 10px;
  cursor: pointer;

  .preview {
    position: absolute;
    font-size: 20px;
  }

  .download {
    position: absolute;
    left: 63px;
    font-size: 20px;
  }

  .remove {
    position: absolute;
    left: 110px;
    font-size: 20px;
  }

  .preview:hover,
  .download:hover,
  .remove:hover {
    filter: brightness(0.8);
    font-size: 23px;
    transition: 0.2s;
  }
}
</style>
