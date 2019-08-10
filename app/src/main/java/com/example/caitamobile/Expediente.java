package com.example.caitamobile;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.caitamobile.Constantes.IntentExtras;
import com.example.caitamobile.modelo.Cita;
import com.example.caitamobile.modelo.Paciente;
import com.example.caitamobile.modelo.Usuario;

public class Expediente extends AppCompatActivity {

    private Usuario usuario;
    private Cita cita;
    private String desde;
    private Paciente paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expediente);

        Intent intent = getIntent();
        usuario = intent.getParcelableExtra(IntentExtras.USUARIO.llave);
        paciente = intent.getParcelableExtra(IntentExtras.PACIENTE.llave);
        cita = intent.getParcelableExtra(IntentExtras.CITA.llave);
        desde = intent.getStringExtra(IntentExtras.DESDE.llave);

        if(usuario == null)
            expulsar();
    }

    private void expulsar() {
        usuario = null;
        Intent intent = new Intent(Expediente.this, MainActivity.class);
        startActivity(intent);
    }
}

