package com.example.Authentication.DTO;

import com.example.Authentication.model.ListLancheIngrediente;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class LancheDTO {
    private Short id;
    private String nome;
    private Double valor;
    private List<ListLancheIngrediente> listagemIngrediente;
}
