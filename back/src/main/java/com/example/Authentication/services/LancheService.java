package com.example.Authentication.services;

import com.example.Authentication.DTO.LancheDTO;
import com.example.Authentication.model.Produtos;
import com.example.Authentication.model.Lanche;
import com.example.Authentication.model.ListLancheIngrediente;
import com.example.Authentication.repository.ProdutosRepository;
import com.example.Authentication.repository.LancheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LancheService {

    @Autowired
    private LancheRepository lancheRepository;
    @Autowired
    private ProdutosRepository produtosRepository;

    public LancheService(LancheRepository lancheRepository, ProdutosRepository produtosRepository) {
        this.lancheRepository = lancheRepository;
        this.produtosRepository = produtosRepository;
    }

    public HttpStatus addLanche(LancheDTO lancheDTO) throws Exception {
      Optional<Lanche> lancheResult =  lancheRepository.findByNome(lancheDTO.getNome().trim());
        if (!lancheResult.isEmpty()) {
            throw new Exception("Já exite um lanche cadastrado com este nome");
        }
        Lanche lanche = new Lanche();
        lanche.setNome(lancheDTO.getNome());
        lanche.setValor(lancheDTO.getValor());
        Double soma  = 0.0;
        List<ListLancheIngrediente> listagemLancheIngredientes = new ArrayList<>();
        for (ListLancheIngrediente listLanche :lancheDTO.getListagemIngrediente()) {
            ListLancheIngrediente listLancheIngrediente = new ListLancheIngrediente();
            Produtos ingrediente = produtosRepository.findById(listLanche.getProdutos().getId()).get();
            listLancheIngrediente.setProdutos(ingrediente);
            double valor = ingrediente.getValor();
            soma+=valor;
            listLancheIngrediente.setLanche(lanche);
            listagemLancheIngredientes.add(listLancheIngrediente);
        }
        lanche.setGasto_confeccao(soma);
        lanche.setLucro(lanche.getValor() - soma);
        lanche.setListagemIngrediente(listagemLancheIngredientes);
        lancheRepository.save(lanche);
        return HttpStatus.CREATED;
    }


    public List<Lanche> findAll() {
        return lancheRepository.findAll();

    }

    public HttpStatus deleteById(Short id) {
        lancheRepository.deleteById(id);
        return HttpStatus.OK;
    }

    public Optional<Lanche> findById(Short id) {
        return lancheRepository.findById(id);
    }
//    @Transactional(rollbackFor = Exception.class)
//    public void editById(Lanche lancheEdit) throws Exception {
//        try {
//            if (Objects.isNull(lancheEdit)) {
//                throw new Exception("Lanche inválido");
//            }
//            Lanche lanche = lancheRepository.findById(lancheEdit.getId()).orElse(null);
//
//            if (lanche == null) {
//                throw new Exception("Lanche não encontrado na base de dados");
//            }
//
//            lanche.setNome(lancheEdit.getNome());
//            lanche.setValor(lancheEdit.getValor());
//
//            double soma = 0.0;
//            List<ListLancheIngrediente> listIngredientes = new ArrayList<>();
//
//            for (ListLancheIngrediente listLancheIngrediente : lancheEdit.getListagemIngrediente()) {
//                Ingrediente ingrediente = ingredienteRepository.findById(listLancheIngrediente.getIngrediente().getId()).orElse(null);
//
//                if (ingrediente == null) {
//                    throw new Exception("Ingrediente não encontrado na base de dados");
//                }
//
//                ListLancheIngrediente lancheIngrediente = new ListLancheIngrediente();
//                lancheIngrediente.setIngrediente(ingrediente);
//                lancheIngrediente.setLanche(lanche);
//                double valor = ingrediente.getValor();
//                soma += valor;
//                listIngredientes.add(lancheIngrediente);
//            }
//
//            // Atualizar a lista de ingredientes existente no lanche
//            lanche.getListagemIngrediente().clear();
//            lanche.getListagemIngrediente().addAll(listIngredientes); // Adiciona os novos ingredientes
//
//            lanche.setGasto_confeccao(soma);
//            lanche.setLucro(lanche.getValor() - soma);
//
//            lancheRepository.save(lanche);
//        } catch (Exception e) {
//            throw e;
//        }
//    }

    @Transactional(rollbackFor = Exception.class)
    public void editById(Lanche lancheEdit) throws Exception {
        try {
            if (Objects.isNull(lancheEdit)) {
                throw new Exception("Lanche invalido");
            }
            Lanche lanche = lancheRepository.findById(lancheEdit.getId()).get();
            lanche.setNome(lancheEdit.getNome());
            lanche.setValor(lancheEdit.getValor());
            double soma = 0.0;

            List<ListLancheIngrediente> listIngredientes = lanche.getListagemIngrediente();
            listIngredientes.clear();

            for (ListLancheIngrediente listLancheIngrediente : lancheEdit.getListagemIngrediente()) {
                Produtos produtos = produtosRepository.findById(listLancheIngrediente.getProdutos().getId()).get();
                if (Objects.isNull(produtos)) {
                    throw new Exception("Ingrediente nao lacalizado na base de dados");
                }
                ListLancheIngrediente lancheIngrediente = new ListLancheIngrediente();

                lancheIngrediente.setProdutos(produtos);
                lancheIngrediente.setLanche(lanche);
                double valor = produtos.getValor();
                soma+=valor;
                listIngredientes.add(lancheIngrediente);
            }

            lanche.setListagemIngrediente(listIngredientes);
            lanche.setGasto_confeccao(soma);
            lanche.setLucro(lanche.getValor() - soma);
            lancheRepository.save(lanche);
        } catch (Exception e) {
            throw e;
        }

    }
}
