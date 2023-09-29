package com.example.Authentication.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "lanche")
@JsonIdentityInfo( generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Lanche {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    private String nome;

    private Double valor;

    @JsonIgnoreProperties(value = {"lanche", "hibernateLazyInitializer"})
    @OneToMany(mappedBy = "lanche", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ListLancheIngrediente> listagemIngrediente;

    private Double gasto_confeccao;

    private Double lucro;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public List<ListLancheIngrediente> getListagemIngrediente() {
        return listagemIngrediente;
    }

    public void setListagemIngrediente(List<ListLancheIngrediente> listagemIngrediente) {
        this.listagemIngrediente = listagemIngrediente;
    }

    public Double getGasto_confeccao() {
        return gasto_confeccao;
    }

    public void setGasto_confeccao(Double gasto_confeccao) {
        this.gasto_confeccao = gasto_confeccao;
    }

    public Double getLucro() {
        return lucro;
    }

    public void setLucro(Double lucro) {
        this.lucro = lucro;
    }
}
