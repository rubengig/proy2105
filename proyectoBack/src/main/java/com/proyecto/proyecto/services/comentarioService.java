package com.proyecto.proyecto.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.proyecto.proyecto.entity.articuloEntity;
import com.proyecto.proyecto.entity.comentarioEntity;
import com.proyecto.proyecto.entity.usuarioEntity;
import com.proyecto.proyecto.exception.ResourceNotFoundException;
import com.proyecto.proyecto.exception.UnauthorizedAccessException;
import com.proyecto.proyecto.repository.comentarioRepository;

@Service
public class comentarioService implements serviceInterface<comentarioEntity> {

    @Autowired
    comentarioRepository oComentarioRepository;

    @Autowired
    usuarioService oUsuarioService;

    @Autowired
    articuloService oArticuloService;

    @Autowired
    private AuthService oAuthService;

    public Long randomCreate(Long cantidad) {
    List<usuarioEntity> usuarios = oUsuarioService.getAll();
    List<articuloEntity> articulos = oArticuloService.getAll();
    
    String[] comentariosTexto = {
        "¡Excelente artículo!",
        "Me ha gustado mucho este tema, gracias por compartir.",
        "Este artículo es muy informativo.",
        "No estoy de acuerdo con algunos puntos, pero interesante.",
        "Muy buen análisis, me ha ayudado a entender más sobre el tema.",
        "Creo que podría mejorarse, pero está bien en general.",
        "Este artículo es muy útil para empezar en este campo.",
        "La información está bien estructurada y clara.",
        "No encontré lo que esperaba, pero aún así interesante.",
        "Excelente investigación, sigue así."
    };
    
    Random rand = new Random();

    for (int i = 0; i < cantidad; i++) {
        comentarioEntity comentario = new comentarioEntity();
        
        // Asignar un usuario y un artículo aleatorio
        comentario.setUsuario(usuarios.get(rand.nextInt(usuarios.size())));
        comentario.setArticulo(articulos.get(rand.nextInt(articulos.size())));
        
        // Asignar un texto aleatorio de los posibles
        comentario.setTexto(comentariosTexto[rand.nextInt(comentariosTexto.length)]);
        
        // Guardar el comentario
        oComentarioRepository.save(comentario);
    }
    
    return oComentarioRepository.count();
}

    public Page<comentarioEntity> getPage(Pageable oPageable, Optional<String> filter) {

        if (filter.isPresent()) {
            return oComentarioRepository
                    .findByTextoContaining(filter.get(), oPageable);
        } else {
            return oComentarioRepository.findAll(oPageable);
        }
    }

    public comentarioEntity get(Long id) {
        return oComentarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario no encontrado"));
    }

    public Long count() {
        return oComentarioRepository.count();
    }

    public Long delete(Long id) {
        if(oAuthService.isAdmin()) {
            oComentarioRepository.deleteById(id);
            return 1L;
        }else {
            throw new UnauthorizedAccessException("No tienes permisos para borrar el comentario");
        }

    }

    public comentarioEntity create(comentarioEntity ocomentarioEntity) {
        if(oAuthService.isSessionActive()) {
            return oComentarioRepository.save(ocomentarioEntity);
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para hacer comentarios");
        }
        
    }

    public comentarioEntity update(comentarioEntity ocomentarioEntity) {
        return oComentarioRepository.save(ocomentarioEntity);
    }

    public Long deleteAll() {
        oComentarioRepository.deleteAll();
        return this.count();
    }

    public Page<comentarioEntity> getComentariosPorArticulo(Long idArticulo, Pageable pageable) {
        return oComentarioRepository.findByComentarioxArticulo(idArticulo, pageable);
    }

    public Page<comentarioEntity> getComentariosPorUsuario(Long idUsuario, Pageable pageable) {
        return oComentarioRepository.findByComentarioxUsuario(idUsuario, pageable);
    }
}
