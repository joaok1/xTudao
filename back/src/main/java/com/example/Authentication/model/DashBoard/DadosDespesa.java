package com.example.Authentication.model.DashBoard;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DadosDespesa {
    private Integer ano;
    private String mes;
    private Double entrada;
    private Double saida;
    private String name;
}
