package com.example.Authentication.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "pessoa")
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

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

    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private Usuario usuario;

}
