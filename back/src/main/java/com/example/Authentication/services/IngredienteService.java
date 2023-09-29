package com.example.Authentication.services;

import com.example.Authentication.DTO.ProdutosDTO;
import com.example.Authentication.Interfaces.TiposInterface;
import com.example.Authentication.Interfaces.UnidadePeso;
import com.example.Authentication.model.Produtos;
import com.example.Authentication.repository.ProdutosRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class IngredienteService {

    @Autowired
    private ProdutosRepository produtosRepository;
    @Autowired
    private HistoricoIngredienteService historicoIngredienteService;

    //Metodo de cadastro de ingrediente
    public HttpStatus criar(ProdutosDTO produtosDTO) throws Exception {
        if (Objects.isNull(produtosDTO.getNome()) && Objects.isNull(produtosDTO.getValor())) {
            throw new RuntimeException("Erro ao salvar no banco");
        }
        Produtos resultName = produtosRepository.findByNome(produtosDTO.getNome());
        if (Objects.nonNull(resultName)) {
            throw new Exception("Já existe um ingrediente com o nome: " + produtosDTO.getNome());
        }
        Produtos produtos = new Produtos();
        if (produtosDTO.getFator().equals(UnidadePeso.Kg) && produtosDTO.getTipo().equals(TiposInterface.INGREDIENTES)) {
            produtosDTO.setQuantidade(produtosDTO.getQuantidade() * UnidadePeso.KG);
            produtos.setNome(produtosDTO.getNome());
            produtos.setFator(produtosDTO.getFator());
            produtos.setPorcao(produtosDTO.getPorcao());
            produtos.setValor(produtosDTO.getValor() / produtosDTO.getPorcao());
            produtos.setQuantidade(produtosDTO.getQuantidade() / produtosDTO.getPorcao());
        } else {
            produtos.setNome(produtosDTO.getNome());
            produtos.setFator(produtosDTO.getFator());
            produtos.setPorcao(produtosDTO.getPorcao());
            produtos.setValor(produtosDTO.getValor() / produtosDTO.getPorcao());
            produtos.setQuantidade(produtosDTO.getQuantidade());
        }
        produtosRepository.save(produtos);
        historicoIngredienteService.historicoIngrediente(produtos);
        return HttpStatus.CREATED;
    }

    public HttpStatus deleteById(Short id) throws Exception {
        try{
             produtosRepository.deleteById(id);

        } catch (Exception e) {
            throw new Exception("Erro ao deletar ingrediente");
        }
        return HttpStatus.OK;
    }

    public HttpStatus editById(Produtos produtos) throws Exception {
        try {
            Produtos produtosOptional = produtosRepository.findById(produtos.getId()).get();
            if (Objects.nonNull(produtosOptional)) {
                produtosOptional.setNome(produtos.getNome());
                produtosOptional.setPorcao(produtos.getPorcao());
                produtosOptional.setFator(produtos.getFator());
                produtosOptional.setValor(produtos.getValor());
                produtosRepository.save(produtosOptional) ;
            }
        } catch (Exception e) {
            throw new Exception("Erro ao editar ingrediente");
        }

        return HttpStatus.OK;
    }

    public Page<Produtos> findByAll(Pageable pageable) {
        return produtosRepository.findAll(pageable);
    }

    public Optional<Produtos> findById(Short id) {
        return produtosRepository.findById(id);
    }

    public HttpStatus insertSaldo(ProdutosDTO produtosDTO) throws Exception {
        Produtos produtos = produtosRepository.findById(produtosDTO.getId()).get();
        produtos.setValor(produtosDTO.getValor());
        if (produtosDTO.getFator().equals(UnidadePeso.Kg) && produtosDTO.getTipo().equals(TiposInterface.INGREDIENTES)) {
            if (produtos.getQuantidade() > 0) {
//                ingrediente.setQuantidade(ingrediente.getQuantidade() + ingredienteDTO.getQuantidade());
                Double media = (produtosDTO.getValor() + produtos.getValor()) / 2;
                produtosDTO.setQuantidade((produtosDTO.getQuantidade() * UnidadePeso.KG));
                produtos.setNome(produtosDTO.getNome());
                produtos.setFator(produtosDTO.getFator());
                produtos.setPorcao(produtosDTO.getPorcao());
                produtos.setValor((produtosDTO.getValor() / produtosDTO.getPorcao()) + produtos.getValor() / 2);
                produtos.setQuantidade(produtosDTO.getQuantidade() / produtosDTO.getPorcao() + produtos.getQuantidade());
            }
        } else {
            produtos.setNome(produtosDTO.getNome());
            produtos.setFator(produtosDTO.getFator());
            produtos.setPorcao(produtosDTO.getPorcao());
            produtos.setValor(produtosDTO.getValor() / produtosDTO.getPorcao());
            produtos.setQuantidade(produtosDTO.getQuantidade() + produtos.getQuantidade());
        }
        historicoIngredienteService.historicoIngrediente(produtos);
        produtosRepository.save(produtos);
        return HttpStatus.OK;
    }
}
