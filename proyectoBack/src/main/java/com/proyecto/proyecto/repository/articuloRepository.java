package com.proyecto.proyecto.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.proyecto.entity.articuloEntity;

@Repository
public interface articuloRepository extends JpaRepository<articuloEntity, Long> {

    Page<articuloEntity> findByNombreContainingOrDescripcionContaining(
            String filter, String filter2, Pageable oPageable);

}
