package com.proyecto.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.proyecto.proyecto.entity.tipousuarioEntity;

@Repository
public interface tipousuarioRepository extends JpaRepository<tipousuarioEntity, Long> {

    Page<tipousuarioEntity> findByDescripcionContaining(String filter, Pageable oPageable);
    
}
