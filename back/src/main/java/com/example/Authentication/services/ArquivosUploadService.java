package com.example.Authentication.services;

import com.example.Authentication.Interfaces.Routes;
import com.example.Authentication.model.ArquivosUpload;
import com.example.Authentication.model.FileName;
import com.example.Authentication.repository.ArquivosUploadRepository;
import com.example.Authentication.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@EnableWebMvc
public class ArquivosUploadService {

    private static ArquivosUploadRepository arquivosUploadRepository;


    private static UsuarioRepository usuarioRepository;
    @Autowired
    public ArquivosUploadService(ArquivosUploadRepository arquivosUploadRepository, FileName file, UsuarioRepository usuarioRepository) {
        ArquivosUploadService.arquivosUploadRepository = arquivosUploadRepository;
        ArquivosUploadService.usuarioRepository = usuarioRepository;
    }

//    public ResponseEntity<ArquivosUpload> save(String descricao, Short usuario , MultipartFile multipartFile) throws IOException {
//        try {
//            ArquivosUpload upload = new ArquivosUpload();
//            Optional<Usuario> findUsuario = usuarioRepository.findById(usuario);
//            upload.setUsuario(findUsuario.get());
//            upload.setDescricao(descricao);
//            upload.setNome(multipartFile.getOriginalFilename());
//            upload.setArquivo(multipartFile.getBytes());
//            arquivosUploadRepository.save(upload);
//            return ResponseEntity.ok(upload);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }

    public Optional<ArquivosUpload> findAll(Short id) {
        List<ArquivosUpload> arquivosUploads = arquivosUploadRepository.findAll();
        return arquivosUploads.stream()
                .filter(arquivosUpload -> arquivosUpload.getId().equals(id))
                .findFirst();
    }

    public Optional<ArquivosUpload> findByShortId(Short id) {
        Optional<ArquivosUpload> arquivosUploadOptional = arquivosUploadRepository.find(id);
        return arquivosUploadOptional;
    }

//    public ResponseEntity<Object> getArquivo(Short id) {
//        try {
//            Optional<ArquivosUpload> optionalUpload = findByShortId(id);
//            if (!optionalUpload.isPresent()) {
//                return ResponseEntity.notFound().build();
//            }
//
//            ArquivosUpload upload = optionalUpload.get();
//            byte[] conteudo = upload.getArquivo();
//            ByteArrayResource resource = new ByteArrayResource(conteudo);
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_PDF);
//            headers.setContentDisposition(ContentDisposition.builder("attachment").filename(upload.getNome()).build());
//
//            return ResponseEntity.ok().headers(headers).contentLength(conteudo.length).body(resource);
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().build();
//
//        }
//    }

    //Salva a imagem na pasta definitiva e retorna o caminho cripto
    public String save(FileName file) throws Exception {
        try {
            // Caminho da pasta de destino
            String destino = Routes.PASTA_DEFINITIVA;

            // Gere uma chave única para o arquivo
            String chave = UUID.randomUUID().toString();

            // Obtém o nome do arquivo da URL
            String nomeArquivo = file.getKey();

            // Crie o arquivo de destino usando a chave no nome do arquivo
            File arquivoDestino = new File(destino + chave);

            // Baixa o arquivo da URL e salva no destino
            saveUrlToFile(file.getKey(), arquivoDestino);

            return destino + chave ;

        } catch (Exception e) {
            throw new Exception("Não foi possivel resgatar a foto");
        }

    }

    private void saveUrlToFile(String url, File destino) throws Exception {
            Files.copy(Path.of(url), destino.toPath());

    }

    //Salva o arquivo na pasta temporaria
    public String saveTemp(MultipartFile multipartFile) throws Exception {

                // Caminho pasta temp
                String destino = Routes.PASTA_TEMP;

                // Gere uma chave única para o arquivo
                String chave = UUID.randomUUID().toString();

                String name = multipartFile.getOriginalFilename();

                if (!name.endsWith(".png") ) {
                    throw new Exception("Insira uma imagem PNG");
                }

                FileName file = new FileName();

                file.setName(name);

                file.setKey(chave);

                // Crie o arquivo de destino usando a chave no nome do arquivo
                File arquivoDestino = new File(destino + file.getKey()) ;

                // Salva o arquivo
                multipartFile.transferTo(arquivoDestino);

                return destino + file.getKey();
    }

    //Metodo para pegar a foto de perfil
    public ResponseEntity<Resource> getImagem(String nomeImagem) {
        Path caminhoImagem = Paths.get(nomeImagem);
        try {
            Resource resource = new UrlResource(caminhoImagem.toUri());
            if (resource.exists() && resource.isReadable()) {
                return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(resource);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.notFound().build();
    }

}

