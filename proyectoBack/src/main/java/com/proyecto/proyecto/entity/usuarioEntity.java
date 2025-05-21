package com.proyecto.proyecto.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuarios")
public class usuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(min = 3, max = 255)
    private String nombre;

    @NotNull
    @Size(min = 3, max = 255)
    private String apellido1;

    @Size(min = 0, max = 255)
    private String apellido2;

    @Size(min = 0, max = 255)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @ManyToOne(fetch = jakarta.persistence.FetchType.EAGER)
    @JoinColumn(name = "tipousuario")
    private tipousuarioEntity tipousuario;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private java.util.List<favoritoEntity> favoritos;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private java.util.List<comentarioEntity> comentarios;

    public usuarioEntity() {
        this.favoritos = new java.util.ArrayList<favoritoEntity>();
        this.comentarios = new java.util.ArrayList<comentarioEntity>();
    }

    public usuarioEntity(@NotNull @Size(min = 3, max = 255) String nombre,
            @NotNull @Size(min = 3, max = 255) String apellido1, @Size(min = 0, max = 255) String apellido2,
            @Email String email, String password) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.email = email;
        this.password = password;
    }

    public usuarioEntity(long id, @NotNull @Size(min = 3, max = 255) String nombre,
            @NotNull @Size(min = 3, max = 255) String apellido1, @Size(min = 0, max = 255) String apellido2,
            @Email String email, String password) {
        this.id = id;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.email = email;
        this.password = password;
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

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public tipousuarioEntity getTipousuario() {
        return tipousuario;
    }

    public void setTipousuario(tipousuarioEntity tipousuario) {
        this.tipousuario = tipousuario;
    }

    public int getFavoritos() {
        return favoritos.size();
    }

    public int getComentarios() {
        return comentarios.size();
    }

}
