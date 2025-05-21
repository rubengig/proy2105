package com.proyecto.proyecto.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.proyecto.proyecto.entity.tipousuarioEntity;
import com.proyecto.proyecto.exception.ResourceNotFoundException;
import com.proyecto.proyecto.repository.tipousuarioRepository;

@Service
public class tipousuarioService implements serviceInterface<tipousuarioEntity> {

    @Autowired
    tipousuarioRepository oTipousuarioRepository;

    @Autowired
    private randomService oRandomService;

    public Page<tipousuarioEntity> getPage(Pageable oPageable, Optional<String> filter) {

        if (filter.isPresent()) {
            return oTipousuarioRepository
                    .findByDescripcionContaining(filter.get(), oPageable);
        } else {
            return oTipousuarioRepository.findAll(oPageable);
        }
    }

     public tipousuarioEntity get(Long id) {
        return oTipousuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }

    public Long count() {
        return oTipousuarioRepository.count();
    }

    public Long delete(Long id) {
        oTipousuarioRepository.deleteById(id);
        return 1L;
    }

    public tipousuarioEntity create(tipousuarioEntity oTipousuarioEntity) {
        return oTipousuarioRepository.save(oTipousuarioEntity);
    }

    public tipousuarioEntity update(tipousuarioEntity oTipousuarioEntity) {
        tipousuarioEntity oTipousuarioEntityFromDatabase = oTipousuarioRepository.findById(oTipousuarioEntity.getId())
                .get();
        if (oTipousuarioEntity.getDescripcion() != null) {
            oTipousuarioEntityFromDatabase.setDescripcion(oTipousuarioEntity.getDescripcion());
        }
        return oTipousuarioRepository.save(oTipousuarioEntityFromDatabase);
    }

    public Long deleteAll() {
        oTipousuarioRepository.deleteAll();
        return this.count();
    }

    public tipousuarioEntity randomSelection() {
        return oTipousuarioRepository.findAll()
                .get(oRandomService.getRandomInt(0, (int) (oTipousuarioRepository.count() - 1)));
    }
    
}
