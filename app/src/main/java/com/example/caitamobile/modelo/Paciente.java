package com.example.caitamobile.modelo;

import android.os.Parcel;
import android.os.Parcelable;

public class Paciente implements Parcelable {
    private int id_paciente;
    private String nombre;
    private String apellidos;
    private int telefono;
    private String correo;

    public Paciente(int id_paciente, String nombre, String apellidos, int telefono, String correo) {
        this.id_paciente = id_paciente;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.correo = correo;
    }

    public int getId_paciente() {
        return id_paciente;
    }

    public void setId_paciente(int id_paciente) {
        this.id_paciente = id_paciente;
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

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    protected Paciente(Parcel in) {
        id_paciente = in.readInt();
        nombre = in.readString();
        apellidos = in.readString();
        telefono = in.readInt();
        correo = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_paciente);
        dest.writeString(nombre);
        dest.writeString(apellidos);
        dest.writeInt(telefono);
        dest.writeString(correo);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Paciente> CREATOR = new Parcelable.Creator<Paciente>() {
        @Override
        public Paciente createFromParcel(Parcel in) {
            return new Paciente(in);
        }

        @Override
        public Paciente[] newArray(int size) {
            return new Paciente[size];
        }
    };
}