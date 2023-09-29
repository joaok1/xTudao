package com.example.Authentication.services;

import com.example.Authentication.DTO.PessoaDTO;
import com.example.Authentication.repository.RoleRepository;
import com.example.Authentication.model.Pessoa;
import com.example.Authentication.model.Role;
import com.example.Authentication.model.Usuario;
import com.example.Authentication.repository.PessoaRepository;
import com.example.Authentication.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PessoaService {
    @Autowired
    private static PessoaRepository pessoaRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private static RoleRepository roleRepository;
    private static ArquivosUploadService arquivosUploadService;

    @Autowired
    private static UsuarioRepository usuarioRepository;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository, PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository, ArquivosUploadService arquivosUploadService,RoleRepository roleRepository) {
        PessoaService.pessoaRepository = pessoaRepository;
        this.passwordEncoder = passwordEncoder;
        PessoaService.usuarioRepository = usuarioRepository;
        PessoaService.arquivosUploadService = arquivosUploadService;
        PessoaService.roleRepository = roleRepository;
    }


    @Transactional(rollbackFor = Exception.class)
    public void adicionarPessoa(PessoaDTO pessoaDTO) throws Exception {
        try {
            String senhaCripto = passwordEncoder.encode(pessoaDTO.getUsuario().getSenha());
            pessoaDTO.getUsuario().setSenha(senhaCripto);
            Pessoa pessoa = new Pessoa();
            pessoa.setNome(pessoaDTO.getNome());
            pessoa.setSobrenome(pessoaDTO.getSobrenome());
            pessoa.setCpf(pessoaDTO.getCpf());
            pessoa.setEmail(pessoaDTO.getEmail());
            pessoa.setCep(pessoa.getCep());
            pessoa.setEndereco(pessoaDTO.getEndereco());
            pessoa.setBairro(pessoaDTO.getBairro());
            pessoa.setCidade(pessoa.getCidade());
            pessoa.setEstado(pessoaDTO.getEstado());
            pessoa.setTelefone(pessoaDTO.getTelefone());
            Usuario usuario = new Usuario();
            usuario.setLogin(pessoaDTO.getCpf());
            Optional<Role> role = roleRepository.findById(pessoaDTO.getRole());
            usuario.setRole(role.get());
            usuario.setSenha(pessoaDTO.getUsuario().getSenha());
            usuario.setDocumento(arquivosUploadService.save(pessoaDTO.getFile()));
            usuarioRepository.save(usuario);
            pessoa.setUsuario(usuario);
            pessoaRepository.save(pessoa);
        } catch (DataAccessException e) {
            throw new Exception("Não foi possivel criar o usuario.",e);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possivel criar o usuario",e);
        }
    }

    public Optional<Pessoa> findById(Short id) {
        return pessoaRepository.findById(id);
    }


    public ResponseEntity<String> deleteById(Short id) {
        pessoaRepository.deleteById(id);
        return ResponseEntity.ok("Deletado com sucesso");
    }

    public Optional<Pessoa> findByPessoaByUser(String login) {
        Optional<Usuario> user = usuarioRepository.findByLogin(login);
        return pessoaRepository.findByUsuario(user.get());
    }
}
