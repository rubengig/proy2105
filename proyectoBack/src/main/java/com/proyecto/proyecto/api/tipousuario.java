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

import com.proyecto.proyecto.entity.tipousuarioEntity;
import com.proyecto.proyecto.services.tipousuarioService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/tipousuario")
public class tipousuario {
    
    @Autowired
    tipousuarioService oTipousuarioService;

    @GetMapping("")
    public ResponseEntity<Page<tipousuarioEntity>> getPage(
            Pageable oPageable,
            @RequestParam  Optional<String> filter) {
        return new ResponseEntity<Page<tipousuarioEntity>>(oTipousuarioService.getPage(oPageable, filter), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<tipousuarioEntity> getTipousuario(@PathVariable Long id) {
        return new ResponseEntity<tipousuarioEntity>(oTipousuarioService.get(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oTipousuarioService.count(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return new ResponseEntity<Long>(oTipousuarioService.delete(id), HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<tipousuarioEntity> create(@RequestBody tipousuarioEntity oTipousuarioEntity) {
        return new ResponseEntity<tipousuarioEntity>(oTipousuarioService.create(oTipousuarioEntity), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<tipousuarioEntity> update(@RequestBody tipousuarioEntity oTipousuarioEntity) {
        return new ResponseEntity<tipousuarioEntity>(oTipousuarioService.update(oTipousuarioEntity), HttpStatus.OK);
    }

    @DeleteMapping("/all")
    public ResponseEntity<Long> deleteAll() {
        return new ResponseEntity<Long>(oTipousuarioService.deleteAll(), HttpStatus.OK);
    }

}
