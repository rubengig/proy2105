package com.proyecto.proyecto.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "articulos")
public class articuloEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min = 3, max = 255)
    private String nombre;

    @NotNull
    @Size(min = 3, max = 255)
    private String descripcion;

    private double precio;

    @OneToMany(mappedBy = "articulo", fetch = FetchType.LAZY)
    private java.util.List<favoritoEntity> favoritos;

    @OneToMany(mappedBy = "articulo", fetch = FetchType.LAZY)
    private java.util.List<comentarioEntity> comentarios;

    public articuloEntity() {
        this.favoritos = new java.util.ArrayList<favoritoEntity>();
        this.comentarios = new java.util.ArrayList<comentarioEntity>();
    }

    public articuloEntity(@NotNull @Size(min = 3, max = 255) String nombre,
            @NotNull @Size(min = 3, max = 255) String descripcion, double precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public articuloEntity(long id, @NotNull @Size(min = 3, max = 255) String nombre,
            @NotNull @Size(min = 3, max = 255) String descripcion, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getFavoritos() {
        return favoritos.size();
    }

    public int getComentarios() {
        return comentarios.size();
    }

}
