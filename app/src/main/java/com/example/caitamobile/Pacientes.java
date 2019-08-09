package com.example.caitamobile;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.example.caitamobile.Constantes.IntentExtras;
import com.example.caitamobile.Constantes.ListaActividades;
import com.example.caitamobile.modelo.Paciente;
import com.example.caitamobile.modelo.Usuario;

public class Pacientes extends AppCompatActivity {

    private Usuario usuario;
    private String desde;

    private Button btnDatosGenerales;
    private Button btnAgendarCita;
    private Button btnVerExpediente;
    private Spinner spPacientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente);

        Intent intent = getIntent();
        usuario = intent.getParcelableExtra(IntentExtras.USUARIO.llave);
        desde = intent.getStringExtra(IntentExtras.DESDE.llave);

        if(usuario == null)
            expulsar();

        btnDatosGenerales = findViewById(R.id.btnDatosGenerales);
        btnAgendarCita = findViewById(R.id.btnAgendarCita);
        btnVerExpediente = findViewById(R.id.btnVerExpediente);

        btnDatosGenerales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* INDICA desde QUE VISTA SE ESTA MANDANDO A LLAMAR EL MENU
                 * HACIENDO USO DE ENUMS PARA EVITAR ERRORES POR TYPOS
                 * SE PUEDEN AGREGAR MAS 'EXTRAS' CONFORME SEA NECESARIO
                 *
                 */
                Paciente paciente = (Paciente) spPacientes.getSelectedItem();

                Intent intent = new Intent(Pacientes.this, DatosGenerales.class);
                intent.putExtra(IntentExtras.USUARIO.llave, usuario);
                intent.putExtra(IntentExtras.PACIENTE.llave, paciente);
                intent.putExtra(IntentExtras.DESDE.llave, ListaActividades.PACIENTES.nombre);
                startActivity(intent);
            }
        });

        btnAgendarCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* INDICA desde QUE VISTA SE ESTA MANDANDO A LLAMAR EL MENU
                 * HACIENDO USO DE ENUMS PARA EVITAR ERRORES POR TYPOS
                 * SE PUEDEN AGREGAR MAS 'EXTRAS' CONFORME SEA NECESARIO
                 *
                 */
                Paciente paciente = (Paciente) spPacientes.getSelectedItem();

                Intent intent = new Intent(Pacientes.this, datosCita.class);
                intent.putExtra(IntentExtras.USUARIO.llave, usuario);
                intent.putExtra(IntentExtras.PACIENTE.llave, paciente);
                intent.putExtra(IntentExtras.DESDE.llave, ListaActividades.PACIENTES.nombre);
                startActivity(intent);
            }
        });

        btnVerExpediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* INDICA desde QUE VISTA SE ESTA MANDANDO A LLAMAR EL MENU
                 * HACIENDO USO DE ENUMS PARA EVITAR ERRORES POR TYPOS
                 * SE PUEDEN AGREGAR MAS 'EXTRAS' CONFORME SEA NECESARIO
                 *
                 */
                Paciente paciente = (Paciente) spPacientes.getSelectedItem();

                Intent intent = new Intent(Pacientes.this, Expediente.class);
                intent.putExtra(IntentExtras.USUARIO.llave, usuario);
                intent.putExtra(IntentExtras.PACIENTE.llave, paciente);
                intent.putExtra(IntentExtras.DESDE.llave, ListaActividades.PACIENTES.nombre);
                startActivity(intent);
            }
        });
    }

    private void expulsar() {
        usuario = null;
        Intent intent = new Intent(Pacientes.this, MainActivity.class);
        startActivity(intent);
    }
}
