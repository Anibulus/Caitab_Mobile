package com.example.caitamobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;

import com.example.caitamobile.Constantes.IntentExtras;
import com.example.caitamobile.Constantes.ListaActividades;
import com.example.caitamobile.modelo.Cita;
import com.example.caitamobile.modelo.Paciente;
import com.example.caitamobile.modelo.Usuario;

public class DatosGenerales extends AppCompatActivity {

    private Usuario usuario;
    private String desde;
    private Cita cita;
    private Paciente paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_generales);


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
        Intent intent = new Intent(DatosGenerales.this, MainActivity.class);
        startActivity(intent);
    }
}

/* VOLVER A LA VISTA DESDE LA CUAL SE CREÓ LA ACTIVIDAD

        if(desde.equals(ListaActividades.MAIN_ACTIVITY.nombre))
            intent = new Intent(DatosGenerales.this, MainActivity.class);

        if(desde.equals(ListaActividades.MENU.nombre))
            intent = new Intent(DatosGenerales.this, Menu.class);

 */
