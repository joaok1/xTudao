package com.example.Authentication.repository;


import com.example.Authentication.model.Pessoa;
import com.example.Authentication.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Short> {
    @Query(nativeQuery = true, value = "select * from pessoa p WHERE p.usuario_id = :usuario")
    Optional<Pessoa> findByIdUsuario(Short usuario);

    Optional<Pessoa> findByUsuario(Usuario usuario);
}
