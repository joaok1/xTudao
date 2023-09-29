package com.example.Authentication.services;

import com.example.Authentication.model.HistoricoIngrediente;
import com.example.Authentication.model.Produtos;
import com.example.Authentication.repository.HistoricoIngredienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
@AllArgsConstructor
public class HistoricoIngredienteService {

    @Autowired
    private HistoricoIngredienteRepository historicoIngredienteRepository;


    public void historicoIngrediente(Produtos produtos) throws Exception {
        HistoricoIngrediente historicoIngrediente = new HistoricoIngrediente();
        if (Objects.isNull(produtos)) {
            throw new Exception("Ingrediente invalido");
        }
        historicoIngrediente.setProdutos(produtos);
        historicoIngrediente.setValor(produtos.getValor());
        historicoIngrediente.setDate(new Date());
        historicoIngredienteRepository.save(historicoIngrediente);
    }
}
