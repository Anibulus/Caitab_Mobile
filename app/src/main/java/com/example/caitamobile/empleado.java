package com.example.caitamobile;

public class empleado {
    private int idEmpleado;
    private String nombre;
    private String puesto;
    private Double sueldo;
    private String usuario;
    private String contrasena;

    public empleado (int id, String nombre, String puesto, Double sueldo, String usuario, String contrasena){
        this.idEmpleado=id;
        this.nombre=nombre;
        this.puesto=puesto;
        this.sueldo=sueldo;
        this.usuario=usuario;
        this.contrasena=contrasena;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public Double getSueldo() {
        return sueldo;
    }

    public void setSueldo(Double sueldo) {
        this.sueldo = sueldo;
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

}//fin de la clase empleado
