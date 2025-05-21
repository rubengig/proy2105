package com.proyecto.proyecto.repository;

import org.springframework.stereotype.Repository;
import com.proyecto.proyecto.entity.favoritoEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface favoritoRepository extends JpaRepository<favoritoEntity, Long> {

    @Query(value = "SELECT * FROM favoritos WHERE usuario = :idUsuario", nativeQuery = true)
    Page<favoritoEntity> findByFavoritoxUsuario(@Param("idUsuario") Long idUsuario, Pageable pageable); 

}
