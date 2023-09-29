package com.example.Authentication.repository;

import com.example.Authentication.model.ArquivosUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArquivosUploadRepository extends JpaRepository<ArquivosUpload,Short> {
    @Query(nativeQuery = true, value = "select * FROM financeiro.arquivos_upload WHERE id= :id")
    Optional<ArquivosUpload> find(Short id);
}
