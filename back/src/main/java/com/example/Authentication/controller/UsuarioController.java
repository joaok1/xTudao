package com.example.Authentication.controller;


import com.example.Authentication.DTO.CredenciaisDTO;
import com.example.Authentication.DTO.TokenDTO;
import com.example.Authentication.model.Usuario;
import com.example.Authentication.securityJwt.JwtService;
import com.example.Authentication.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:8080")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    private final JwtService jwtService;

    public UsuarioController(UsuarioService usuarioService, JwtService jwtService) {
        this.usuarioService = usuarioService;
        this.jwtService = jwtService;
    }

    @GetMapping("/findById/{id}")
    public Optional<Usuario> findById(@PathVariable Short id) {
        return usuarioService.findById(id);
    }

    @GetMapping("/findByLogin/{login}")
    public Usuario findByLogin(@PathVariable String login) {
        return usuarioService.findByLogin(login);
    }

    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais){
        try{
            Usuario usuario = Usuario.builder()
                    .login(credenciais.getLogin())
                    .senha(credenciais.getSenha()).build();
            usuarioService.autenticar(usuario);
            String token = jwtService.gerarToken(usuario);
            return new TokenDTO(usuario.getLogin(), token);
        } catch (Exception e ){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping("/validatorUser/{token}")
    public boolean userValidator(@PathVariable String token) {
        return  jwtService.tokenValido(token);
    }

}
