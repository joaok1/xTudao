package com.example.Authentication.services;

import com.example.Authentication.Interfaces.RolesInterface;
import com.example.Authentication.model.Usuario;
import com.example.Authentication.repository.PessoaRepository;
import com.example.Authentication.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PessoaRepository pessoaRepository;

    public Optional<Usuario> findById(Short id) {
        return usuarioRepository.findById(id);
    }

    public UserDetails autenticar( Usuario usuario ) throws Exception {
        UserDetails user = loadUserByUsername(usuario.getLogin());
        boolean senhasBatem = encoder.matches( usuario.getSenha(), user.getPassword() );
        if(senhasBatem){
            return user;
        }
        throw new Exception("Senha invalida");
    }
    @Override
    public UserDetails loadUserByUsername(String username) {
        Usuario usuario = usuarioRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado na base de dados."));

        String[] roles = new String[]{};

        if(usuario.getRole().getName().equals(RolesInterface.ADMIN)) {
            roles  = new String[]{RolesInterface.ADMIN};
        } else if(usuario.getRole().getName().equals(RolesInterface.USER)) {
            roles =  new String[]{RolesInterface.USER};
        } else if (usuario.getRole().getName().equals(RolesInterface.CAIXA)) {
            roles =  new String[]{RolesInterface.CAIXA};
        } else if (usuario.getRole().getName().equals(RolesInterface.VENDEDOR)) {
            roles =  new String[]{RolesInterface.VENDEDOR};
        } else if (usuario.getRole().getName().equals(RolesInterface.GERENTE)) {
            roles =  new String[]{RolesInterface.GERENTE};
        }

        return User
                .builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha())
                .roles(roles)
                .build();
    }


    public Usuario findByLogin(String login) {
        Optional<Usuario> user = usuarioRepository.findByLogin(login);
        return user.get();
    }
}
