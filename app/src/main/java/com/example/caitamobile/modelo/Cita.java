package com.example.caitamobile.modelo;

import android.os.Parcel;
import android.os.Parcelable;

public class Cita implements Parcelable {
    private int idCita;
    private int id_paciente;
    private String nombre_paciente;
    private String apellidos_paciente;

    private String telefono;
    private String correo;

    private String fecha;
    private int idConsultorio;

    public Cita(int idCita,int id_paciente, String nombre_paciente, String apellidos_paciente, String telefono, String correo, String fecha, int idConsultorio) {
        this.idCita=idCita;
        this.id_paciente = id_paciente;
        this.nombre_paciente = nombre_paciente;
        this.apellidos_paciente = apellidos_paciente;
        this.telefono = telefono;
        this.correo = correo;
        this.fecha = fecha;
        this.idConsultorio = idConsultorio;
    }

    public String getDescripcion() {
        fecha= (String) fecha.substring(0,16);
        return nombre_paciente + " " + apellidos_paciente + "\n Dia: " + fecha + "\n Consultorio: " + idConsultorio;
    }
    public int getIdCita() {
        return idCita;
    }

    public int getId_paciente() {
        return id_paciente;
    }

    public String getNombre_paciente() {
        return nombre_paciente;
    }

    public String getApellidos_paciente() {
        return apellidos_paciente;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public String getFecha() {
        return fecha;
    }

    public int getIdConsultorio() {
        return idConsultorio;
    }

    protected Cita(Parcel in) {
        id_paciente = in.readInt();
        nombre_paciente = in.readString();
        apellidos_paciente = in.readString();
        telefono = in.readString();
        correo = in.readString();
        fecha = in.readString();
        idConsultorio = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id_paciente);
        dest.writeString(nombre_paciente);
        dest.writeString(apellidos_paciente);
        dest.writeString(telefono);
        dest.writeString(correo);
        dest.writeString(fecha);
        dest.writeInt(idConsultorio);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Cita> CREATOR = new Parcelable.Creator<Cita>() {
        @Override
        public Cita createFromParcel(Parcel in) {
            return new Cita(in);
        }

        @Override
        public Cita[] newArray(int size) {
            return new Cita[size];
        }
    };
}