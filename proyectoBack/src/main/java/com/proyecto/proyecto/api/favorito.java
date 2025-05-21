package com.proyecto.proyecto.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.proyecto.entity.favoritoEntity;
import com.proyecto.proyecto.services.favoritoService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/favorito")
public class favorito {

    @Autowired
    favoritoService oFavoritoService;

    @GetMapping("")
    public ResponseEntity<Page<favoritoEntity>> getPage(
            Pageable oPageable,
            @RequestParam  Optional<String> filter) {
        return new ResponseEntity<Page<favoritoEntity>>(oFavoritoService.getPage(oPageable, filter), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return new ResponseEntity<Long>(oFavoritoService.delete(id), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<favoritoEntity> create(@RequestBody favoritoEntity oFavoritoEntity) {
        return new ResponseEntity<favoritoEntity>(oFavoritoService.create(oFavoritoEntity), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<favoritoEntity> getFavorito(@PathVariable Long id) {
        return new ResponseEntity<favoritoEntity>(oFavoritoService.get(id), HttpStatus.OK);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<Page<favoritoEntity>> getFavoritosPorUsuario(
    @PathVariable Long idUsuario, Pageable pageable) {
    return new ResponseEntity<>(oFavoritoService.getFavoritosPorUsuario(idUsuario, pageable), HttpStatus.OK);
}

@PostMapping("/random/{cantidad}")
public ResponseEntity<Long> createRandomFavoritos(@PathVariable Long cantidad) {
    return new ResponseEntity<Long>(oFavoritoService.randomCreate(cantidad), HttpStatus.CREATED);
}

    
}
