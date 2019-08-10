package com.example.caitamobile.Constantes;

public enum IntentExtras {
    DESDE("DESDE"),
    USUARIO("USUARIO"),
    PACIENTE("PACIENTE"),
    CITA("CITA");

    public String llave;
    IntentExtras(String llave) {
        this.llave = llave;
    }
}
