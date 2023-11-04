package com.example.Authentication.DTO;

import com.example.Authentication.model.FileName;
import com.example.Authentication.model.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    private Short id;

    private String nome;

    private String sobrenome;

    private String numero;

    private String sexo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone="GMT-3")
    private Date data_nascimento;

    private String cpf;

    private String rg;

    private String cep;

    private String endereco;

    private String logradouro;

    private String cidade;

    private String bairro;

    private String estado;

    private String telefone;

    private String email;

    private Usuario usuario;

    private FileName file;

    private String role;

}
