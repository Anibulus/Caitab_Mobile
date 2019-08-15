package com.example.caitamobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;

import com.example.caitamobile.Constantes.IntentExtras;
import com.example.caitamobile.Constantes.ListaActividades;
import com.example.caitamobile.modelo.Cita;
import com.example.caitamobile.modelo.Paciente;
import com.example.caitamobile.modelo.Usuario;

public class DatosGenerales extends AppCompatActivity {

    private Usuario usuario;
    private String desde;
    //private Cita cita;
    private Paciente paciente;
    private TextView nombre,apellido, correo, telefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_generales);
        /**
         * Aqui se obtienen datos del activity pasado
         */
        Intent intent = getIntent();
        usuario = intent.getParcelableExtra(IntentExtras.USUARIO.llave);
        paciente = intent.getParcelableExtra(IntentExtras.PACIENTE.llave);
        //cita = intent.getParcelableExtra(IntentExtras.CITA.llave);
        desde = intent.getStringExtra(IntentExtras.DESDE.llave);
        /**
         * Aqui se enlazan los componentes
         */

        nombre=(TextView)findViewById(R.id.tvNombrePaciente);
        apellido=(TextView)findViewById(R.id.tvApellidosPaciente);
        correo=(TextView)findViewById(R.id.tvCorreoPaciente);
        telefono=(TextView)findViewById(R.id.tvTelefonoPaciente);
        /**
         * Se llena de informacion con el objetio seleccionado del spinner
         * del activity pasado
         */
        nombre.setText(paciente.getNombre());
        apellido.setText(paciente.getApellidos());
        correo.setText(paciente.getCorreo());
        telefono.setText(paciente.getTelefono());
        if(usuario == null)
            expulsar();
    }//Fin de la OnCreate

    private void expulsar() {
        usuario = null;
        Intent intent = new Intent(DatosGenerales.this, MainActivity.class);
        startActivity(intent);
    }
}//Fin de la clase

/* VOLVER A LA VISTA DESDE LA CUAL SE CREÓ LA ACTIVIDAD

        if(desde.equals(ListaActividades.MAIN_ACTIVITY.nombre))
            intent = new Intent(DatosGenerales.this, MainActivity.class);

        if(desde.equals(ListaActividades.MENU.nombre))
            intent = new Intent(DatosGenerales.this, Menu.class);

 */
