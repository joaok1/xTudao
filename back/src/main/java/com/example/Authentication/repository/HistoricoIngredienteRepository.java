package com.example.Authentication.repository;

import com.example.Authentication.model.HistoricoIngrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoIngredienteRepository extends JpaRepository<HistoricoIngrediente,Short> {

}
