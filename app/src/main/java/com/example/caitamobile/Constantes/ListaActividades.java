package com.example.caitamobile.Constantes;

public enum ListaActividades {
    MAIN_ACTIVITY("MAIN"),
    AGENDA("AGENDA"),
    PACIENTES("PACIENTES"),
    MENU("MENU");

    public String nombre;
    ListaActividades(String nombre) {
        this.nombre = nombre;
    }
}
