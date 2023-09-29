package com.example.Authentication.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;
    @Column
    @NotEmpty(message = "{campo.login.obrigatorio}")
    private String login;
    @Column
    @NotEmpty(message = "{campo.senha.obrigatorio}")
    private String senha;
    @OneToOne
    @JoinColumn(name = "role", referencedColumnName = "id")
    private Role role;
    @Transient
    private boolean admin;
    @Column(name = "documento_image")
    private String documento;
    @Transient
    private Pessoa pessoa;

}
