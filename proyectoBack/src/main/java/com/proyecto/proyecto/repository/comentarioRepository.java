package com.proyecto.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.proyecto.proyecto.entity.comentarioEntity;

@Repository
public interface comentarioRepository extends JpaRepository<comentarioEntity, Long>{

     Page<comentarioEntity> findByTextoContaining(String filter, Pageable oPageable);

@Query(value = "SELECT * FROM comentarios WHERE articulo = :idArticulo", nativeQuery = true)
Page<comentarioEntity> findByComentarioxArticulo(@Param("idArticulo") Long idArticulo, Pageable pageable);

@Query(value = "SELECT * FROM comentarios WHERE usuario = :idUsuario", nativeQuery = true)
Page<comentarioEntity> findByComentarioxUsuario(@Param("idUsuario") Long idUsuario, Pageable pageable); 
   
}
