package com.example.hackhugo.myapplication.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by hackhugo on 09/10/2016.
 */

public class Contactos extends RealmObject {
    @PrimaryKey
    public Integer id;
    public String nombre;
    public String email;
    public String telefono;
    public String mensaje;
    public Integer idContactos;

    public Integer getIdContactos() {
        return idContactos;
    }

    public void setIdContactos(Integer idContactos) {
        this.idContactos = idContactos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
