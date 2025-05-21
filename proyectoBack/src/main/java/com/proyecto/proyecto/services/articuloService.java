package com.proyecto.proyecto.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.proyecto.proyecto.entity.articuloEntity;
import com.proyecto.proyecto.exception.ResourceNotFoundException;
import com.proyecto.proyecto.exception.UnauthorizedAccessException;
import com.proyecto.proyecto.repository.articuloRepository;

@Service
public class articuloService implements serviceInterface<articuloEntity>{

    @Autowired
    articuloRepository oArticuloRepository;

    @Autowired
    private randomService oRandomService;

    @Autowired
    private AuthService oAuthService;

    private String[] arrNombres = {
        "Camiseta", "Zapatillas", "Mochila", "Auriculares", "Libro", "Sudadera", "Pantalón", "Reloj", "Botella", "Teclado"
    };

    private String[] arrDescripciones = {
        "deportiva", "moderna", "clásica", "con estilo", "de alta calidad", "ergonómica", "resistente al agua", "para gamers", "eco-friendly", "compacta"
    };

    public Long randomCreate(Long cantidad) {
        for (int i = 0; i < cantidad; i++) {
            articuloEntity oArticuloEntity = new articuloEntity();

            String nombre = arrNombres[oRandomService.getRandomInt(0, arrNombres.length - 1)];
            String descripcion = "Artículo " + arrDescripciones[oRandomService.getRandomInt(0, arrDescripciones.length - 1)];
            double precio = oRandomService.getRandomInt(5, 200) + oRandomService.getRandomInt(0, 99) / 100.0; // Ej: 79.99

            oArticuloEntity.setNombre(nombre);
            oArticuloEntity.setDescripcion(descripcion);
            oArticuloEntity.setPrecio(precio);

            oArticuloRepository.save(oArticuloEntity);
        }

        return oArticuloRepository.count();
    }

    public Page<articuloEntity> getPage(Pageable oPageable, Optional<String> filter) {
        if (filter.isPresent()) {
            return oArticuloRepository.findByNombreContainingOrDescripcionContaining(filter.get(), filter.get(), oPageable);
        } else {
            return oArticuloRepository.findAll(oPageable);
        }
    }

    public articuloEntity get(Long id) {
        return oArticuloRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Articulo no encontrado"));
    }

    public Long count() {
        return oArticuloRepository.count();
    }

    public Long delete(Long id) {
        if(oAuthService.isAdmin()) {
            oArticuloRepository.deleteById(id);
            return 1L;
        } else {
             throw new UnauthorizedAccessException("No tienes permisos para borrar el articulo");
        }
        
    }

    public articuloEntity create(articuloEntity oArticuloEntity) {
        if(oAuthService.isAdmin()) {
            return oArticuloRepository.save(oArticuloEntity);
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para crear el articulo");
        }
        
    }

    public articuloEntity update(articuloEntity oArticuloEntity) {
         if(oAuthService.isAdmin()) {

        articuloEntity oArticuloEntityFromDatabase = oArticuloRepository.findById(oArticuloEntity.getId()).get();
        if (oArticuloEntity.getNombre() != null) {
            oArticuloEntityFromDatabase.setNombre(oArticuloEntity.getNombre());
        }
        if (Double.compare(oArticuloEntity.getPrecio(), Double.NaN) != 0) {
            oArticuloEntityFromDatabase.setPrecio(oArticuloEntity.getPrecio());
        }
        return oArticuloRepository.save(oArticuloEntityFromDatabase);

         }else {
             throw new UnauthorizedAccessException("No tienes permisos para actualizar el articulo");
         }
       
    }

    public Long deleteAll() {
        oArticuloRepository.deleteAll();
        return this.count();
    }

    public List<articuloEntity> getAll() {
        return oArticuloRepository.findAll();
    }
    
    
}
