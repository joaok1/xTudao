package com.example.Authentication.controller;

import com.example.Authentication.DTO.ProdutosDTO;
import com.example.Authentication.model.Produtos;
import com.example.Authentication.services.IngredienteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ingrediente/")
@Slf4j
public class ProdutosController {
    @Autowired
    private IngredienteService ingredienteService;

    @CrossOrigin(origins = "http://localhost:8080/")
    @PostMapping(value = "adicionar", produces = "application/json")
    public HttpStatus salvarPessoa(@RequestBody ProdutosDTO produtosDTO) throws Exception {
            HttpStatus httpStatus = ingredienteService.criar(produtosDTO);
            return httpStatus;
    }

    @CrossOrigin(origins = "http://localhost:8080/")
    @GetMapping(value = "findById/{id}", produces = "application/json")
    public Optional<Produtos> findById(@PathVariable Short id) {
        return ingredienteService.findById(id);
    }

    @CrossOrigin(origins = "http://localhost:8080/")
    @PutMapping(value = "edit", produces = "application/json")
    public HttpStatus editById(@RequestBody Produtos produtos) throws Exception {
        return ingredienteService.editById(produtos);
    }

    @CrossOrigin(origins = "http://localhost:8080/")
    @DeleteMapping(value = "deleteById/{id}", produces = "application/json")
    public HttpStatus deleteById(@PathVariable Short id) throws Exception {
        return ingredienteService.deleteById(id);
    }

    @CrossOrigin(origins = "http://localhost:8080/")
    @PutMapping(value = "inset", produces = "application/json")
    public HttpStatus InsertById(@RequestBody ProdutosDTO ingrediente) throws Exception {
        return ingredienteService.insertSaldo(ingrediente);
    }

    @CrossOrigin(origins = "http://localhost:8080/")
    @GetMapping(value = "findAll", produces = "application/json")
    public Page<Produtos> findByPessoaByUser(Pageable pageable) {
        return ingredienteService.findByAll(pageable);
    }
}


