package com.proyecto.proyecto.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "comentarios")
public class comentarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min = 3, max = 500)
    private String texto;

    @ManyToOne(fetch = jakarta.persistence.FetchType.EAGER)
    @JoinColumn(name = "articulo")
    private articuloEntity articulo;

    @ManyToOne(fetch = jakarta.persistence.FetchType.EAGER)
    @JoinColumn(name = "usuario")
    private usuarioEntity usuario;

    public comentarioEntity() {
    }

    public comentarioEntity(long id, @NotNull @Size(min = 3, max = 500) String texto) {
        this.id = id;
        this.texto = texto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public articuloEntity getArticulo() {
        return articulo;
    }

    public void setArticulo(articuloEntity articulo) {
        this.articulo = articulo;
    }

    public usuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(usuarioEntity usuario) {
        this.usuario = usuario;
    }
    
    

}
