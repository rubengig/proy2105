package com.proyecto.proyecto.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.proyecto.proyecto.entity.usuarioEntity;
import com.proyecto.proyecto.exception.ResourceNotFoundException;
import com.proyecto.proyecto.exception.UnauthorizedAccessException;
import com.proyecto.proyecto.repository.usuarioRepository;

@Service
public class usuarioService implements serviceInterface<usuarioEntity>{

    @Autowired
    usuarioRepository oUsuarioRepository;

    @Autowired
    tipousuarioService oTipousuarioService;

    @Autowired
    private randomService oRandomService;

    @Autowired
    private AuthService oAuthService;

    private String[] arrNombres = {"Pepe", "Laura", "Ignacio", "Maria", "Lorenzo", "Carmen", "Rosa", "Paco", "Luis",
    "Ana", "Rafa", "Manolo", "Lucia", "Marta", "Sara", "Rocio"};

    private String[] arrApellidos = {"Sancho", "Gomez", "Pérez", "Rodriguez", "Garcia", "Fernandez", "Lopez",
    "Martinez", "Sanchez", "Gonzalez", "Gimenez", "Feliu", "Gonzalez", "Hermoso", "Vidal", "Escriche", "Moreno"};

    public Long randomCreate(Long cantidad) {
        for (int i = 0; i < cantidad; i++) {
            usuarioEntity oUsuarioEntity = new usuarioEntity();
            oUsuarioEntity.setNombre(arrNombres[oRandomService.getRandomInt(0, arrNombres.length - 1)]);
            oUsuarioEntity.setApellido1(arrApellidos[oRandomService.getRandomInt(0, arrApellidos.length - 1)]);
            oUsuarioEntity.setApellido2(arrApellidos[oRandomService.getRandomInt(0, arrApellidos.length - 1)]);
            oUsuarioEntity.setEmail("email" + oUsuarioEntity.getNombre() + oRandomService.getRandomInt(999, 9999) + "@gmail.com");
            oUsuarioEntity.setTipousuario(oTipousuarioService.randomSelection());
            oUsuarioRepository.save(oUsuarioEntity);
        }
        return oUsuarioRepository.count();
    }

     public Page<usuarioEntity> getPage(Pageable oPageable, Optional<String> filter) {
        if (filter.isPresent()) {
            return oUsuarioRepository
                    .findByNombreContainingOrApellido1ContainingOrApellido2ContainingOrEmailContaining(
                            filter.get(), filter.get(), filter.get(), filter.get(),
                            oPageable);
        } else {
            return oUsuarioRepository.findAll(oPageable);
        }
    }

        public usuarioEntity get(Long id) {
        return oUsuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }

    public Long count() {
        return oUsuarioRepository.count();
    }

    public Long delete(Long id) {
         if(oAuthService.isAdmin()) {

        oUsuarioRepository.deleteById(id);
        return 1L;

         } else {
             throw new UnauthorizedAccessException("No tienes permisos para borrar el usuario");
         }

    }

    public usuarioEntity create(usuarioEntity oUsuarioEntity) {

         if(oAuthService.isAdmin()) {
        return oUsuarioRepository.save(oUsuarioEntity);

         } else {
        throw new UnauthorizedAccessException("No tienes permisos para crear usuario");
         }


    }

    public usuarioEntity update(usuarioEntity oUsuarioEntity) {
        if(oAuthService.isAdmin()) {

        usuarioEntity oUsuarioEntityFromDatabase = oUsuarioRepository.findById(oUsuarioEntity.getId()).get();
        if (oUsuarioEntity.getNombre() != null) {
            oUsuarioEntityFromDatabase.setNombre(oUsuarioEntity.getNombre());
        }
        if (oUsuarioEntity.getApellido1() != null) {
            oUsuarioEntityFromDatabase.setApellido1(oUsuarioEntity.getApellido1());
        }
        if (oUsuarioEntity.getApellido2() != null) {
            oUsuarioEntityFromDatabase.setApellido2(oUsuarioEntity.getApellido2());
        }
        if (oUsuarioEntity.getEmail() != null) {
            oUsuarioEntityFromDatabase.setEmail(oUsuarioEntity.getEmail());
        }
        return oUsuarioRepository.save(oUsuarioEntityFromDatabase);

        }else {
            throw new UnauthorizedAccessException("No tienes permisos para actualizar el usuario");
        }
       
    }

    public Long deleteAll() {
        oUsuarioRepository.deleteAll();
        return this.count();
    }

    public List<usuarioEntity> getAll() {
        return oUsuarioRepository.findAll();
    }
    

}
