package com.example.caitamobile;

public class Terapeuta {
    private int id_terapeuta;
    private String nombre;
    private String apellidos;
    private String usuario;
    private String contrasena;

    public Terapeuta(int id_terapeuta, String nombre, String apellidos, String usuario, String contrasena) {
        this.id_terapeuta = id_terapeuta;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public int getId_terapeuta() {
        return id_terapeuta;
    }

    public void setId_terapeuta(int id_terapeuta) {
        this.id_terapeuta = id_terapeuta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}
