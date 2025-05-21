package com.proyecto.proyecto.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.proyecto.proyecto.entity.articuloEntity;
import com.proyecto.proyecto.entity.favoritoEntity;
import com.proyecto.proyecto.entity.usuarioEntity;
import com.proyecto.proyecto.exception.ResourceNotFoundException;
import com.proyecto.proyecto.exception.UnauthorizedAccessException;
import com.proyecto.proyecto.repository.favoritoRepository;

@Service
public class favoritoService implements serviceInterface<favoritoEntity>{

    @Autowired
    favoritoRepository oFavoritoRepository;

    @Autowired
    usuarioService oUsuarioService;

    @Autowired
    articuloService oArticuloService;

    @Autowired
    private AuthService oAuthService;

public Long randomCreate(Long cantidad) {
    List<usuarioEntity> usuarios = oUsuarioService.getAll();
    List<articuloEntity> articulos = oArticuloService.getAll();

    Random rand = new Random();

    for (int i = 0; i < cantidad; i++) {
        favoritoEntity favorito = new favoritoEntity();
        favorito.setUsuario(usuarios.get(rand.nextInt(usuarios.size())));
        favorito.setArticulo(articulos.get(rand.nextInt(articulos.size())));
        oFavoritoRepository.save(favorito);
    }
    return oFavoritoRepository.count();
}

    public Page<favoritoEntity> getPage(Pageable oPageable, Optional<String> filter) {
            return oFavoritoRepository.findAll(oPageable);
    }

     public favoritoEntity get(Long id) {
        return oFavoritoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }

    public Long count() {
        return oFavoritoRepository.count();
    }

    public Long delete(Long id) {
        oFavoritoRepository.deleteById(id);
        return 1L;
    }

    public favoritoEntity create(favoritoEntity ofavoritoEntity) {
        if(oAuthService.isSessionActive()) {
            return oFavoritoRepository.save(ofavoritoEntity);
        }else {
            throw new UnauthorizedAccessException("No tienes permisos para crear favorito");
        }
        
    }

    public favoritoEntity update(favoritoEntity ofavoritoEntity) {
        return oFavoritoRepository.save(ofavoritoEntity);
    }

    public Long deleteAll() {
        oFavoritoRepository.deleteAll();
        return this.count();
    }

     public Page<favoritoEntity> getFavoritosPorUsuario(Long idUsuario, Pageable pageable) {
        return oFavoritoRepository.findByFavoritoxUsuario(idUsuario, pageable);
    }
    
}
