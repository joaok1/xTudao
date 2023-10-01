package com.example.Authentication.DTO;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ProdutosDTO {

    private Short id;
    private String nome;
    private String fator;
    private String tipo;
    private Boolean nivelStoque;
    private Double porcao;
    private Double valor;
    private Double quantidade;
}
