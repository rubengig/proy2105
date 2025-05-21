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

import com.proyecto.proyecto.entity.articuloEntity;
import com.proyecto.proyecto.services.articuloService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/articulo")
public class articulo {

    @Autowired
    articuloService oArticuloService;

     @GetMapping("")
    public ResponseEntity<Page<articuloEntity>> getPage(
            Pageable oPageable,
            @RequestParam  Optional<String> filter) {
        return new ResponseEntity<Page<articuloEntity>>(oArticuloService.getPage(oPageable, filter), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<articuloEntity> getArticulo(@PathVariable Long id) {
        return new ResponseEntity<articuloEntity>(oArticuloService.get(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oArticuloService.count(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return new ResponseEntity<Long>(oArticuloService.delete(id), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<articuloEntity> create(@RequestBody articuloEntity oArticuloEntity) {
        return new ResponseEntity<articuloEntity>(oArticuloService.create(oArticuloEntity), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<articuloEntity> update(@RequestBody articuloEntity oArticuloEntity) {
        return new ResponseEntity<articuloEntity>(oArticuloService.update(oArticuloEntity), HttpStatus.OK);
    }

    @DeleteMapping("/all")
    public ResponseEntity<Long> deleteAll() {
        return new ResponseEntity<Long>(oArticuloService.deleteAll(), HttpStatus.OK);
    }

    @PostMapping("/random/{cantidad}")
    public ResponseEntity<Long> createRandomArticulos(@PathVariable Long cantidad) {
    return new ResponseEntity<Long>(oArticuloService.randomCreate(cantidad), HttpStatus.CREATED);
}


}
