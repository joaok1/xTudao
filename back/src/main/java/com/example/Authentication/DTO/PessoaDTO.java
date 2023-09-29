package com.example.Authentication.DTO;

import com.example.Authentication.model.FileName;
import com.example.Authentication.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class PessoaDTO {
    private String nome;

    private String sobrenome;

    private Date data_nascimento;

    private String cpf;

    private String rg;

    private String cep;

    private String endereco;

    private String Bairro;

    private String cidade;

    private String estado;

    private String telefone;

    private String email;

    private Usuario usuario;

    private FileName file;

    private Short role;

}
