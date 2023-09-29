package com.example.Authentication.repository;

import com.example.Authentication.model.Lanche;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LancheRepository extends JpaRepository<Lanche,Short> {
    Optional<Lanche> findByNome(String nome);
}
