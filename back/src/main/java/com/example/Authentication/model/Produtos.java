package com.example.Authentication.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Data
@RequiredArgsConstructor
@Component
@Table(name = "produtos")
public class Produtos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;
    private String nome;
    private String tipo;
    @Column(name = "fator_medida_peso")
    private String fator;
    private Double porcao;
    private Double valor;
    private Double quantidade;

}
