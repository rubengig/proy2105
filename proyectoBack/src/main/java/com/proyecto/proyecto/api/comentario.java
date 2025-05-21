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

import com.proyecto.proyecto.entity.comentarioEntity;
import com.proyecto.proyecto.services.comentarioService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/comentario")
public class comentario {

    @Autowired
    comentarioService oComentarioService;

    @GetMapping("")
    public ResponseEntity<Page<comentarioEntity>> getPage(
            Pageable oPageable,
            @RequestParam Optional<String> filter) {
        return new ResponseEntity<Page<comentarioEntity>>(oComentarioService.getPage(oPageable, filter), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<comentarioEntity> getComentario(@PathVariable Long id) {
        return new ResponseEntity<comentarioEntity>(oComentarioService.get(id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return new ResponseEntity<Long>(oComentarioService.delete(id), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<comentarioEntity> create(@RequestBody comentarioEntity oComentarioEntity) {
        return new ResponseEntity<comentarioEntity>(oComentarioService.create(oComentarioEntity), HttpStatus.OK);
    }

    @GetMapping("/articulo/{idArticulo}")
    public ResponseEntity<Page<comentarioEntity>> getComentariosPorArticulo(
            @PathVariable Long idArticulo, Pageable pageable) {
        return new ResponseEntity<>(oComentarioService.getComentariosPorArticulo(idArticulo, pageable), HttpStatus.OK);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<Page<comentarioEntity>> getComentariosPorUsuario(
            @PathVariable Long idUsuario, Pageable pageable) {
        return new ResponseEntity<>(oComentarioService.getComentariosPorUsuario(idUsuario, pageable), HttpStatus.OK);
    }

    @PostMapping("/random/{cantidad}")
    public ResponseEntity<Long> createRandomComentarios(@PathVariable Long cantidad) {
        return new ResponseEntity<Long>(oComentarioService.randomCreate(cantidad), HttpStatus.CREATED);
    }

}
