package com.example.Authentication.repository;

import com.example.Authentication.model.Produtos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutosRepository extends JpaRepository<Produtos,Short> {
    @Query(nativeQuery = true, value = "select * from ingrediente rc where UPPER(TRIM(rc.nome)) = UPPER(TRIM(:nome)) " +
            "and  LOWER(TRIM(rc.nome)) = LOWER(TRIM(:nome))")
    Produtos findByNome(String nome);
}
