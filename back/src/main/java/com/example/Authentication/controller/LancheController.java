package com.example.Authentication.controller;

import com.example.Authentication.DTO.LancheDTO;
import com.example.Authentication.model.Lanche;
import com.example.Authentication.services.LancheService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@NoArgsConstructor
@RequestMapping("/api/lanche/")
@Slf4j
@Controller
@CrossOrigin(origins = "http://localhost:3000/")
public class LancheController {

    @Autowired
    private LancheService lancheService;

    public LancheController(LancheService lancheService) {
        this.lancheService = lancheService;
    }

    @PostMapping(value = "adicionar", produces = "application/json")
    public HttpStatus inserirDespesas(@RequestBody LancheDTO lancheDTO) throws Throwable {
           HttpStatus httpStatus = lancheService.addLanche(lancheDTO);
            return httpStatus;
    }

    @GetMapping(value = "findById/{id}", produces = "application/json")
    public Optional<Lanche> findById(@PathVariable Short id) {
        return lancheService.findById(id);
    }

    @PutMapping(value = "edit")
    public HttpStatus editById(@RequestBody Lanche lanche) throws Exception {
        lancheService.editById(lanche);
        return HttpStatus.OK;
    }

    @DeleteMapping(value = "deleteById/{id}", produces = "application/json")
    public HttpStatus deleteById(@PathVariable Short id) throws Exception {
        return lancheService.deleteById(id);
    }

    @GetMapping(value = "findAll", produces = "application/json")
    public List<Lanche> findAll() {
        return lancheService.findAll();
    }
}
