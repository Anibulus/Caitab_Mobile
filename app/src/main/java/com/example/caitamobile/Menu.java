package com.example.caitamobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.caitamobile.Constantes.ListaActividades;
import com.example.caitamobile.Constantes.IntentExtras;
import com.example.caitamobile.modelo.Usuario;

public class Menu extends AppCompatActivity {

    private ImageButton btnAgenda;
    private ImageButton btnPacientes;
    private Button btnCerrarSesion;

    private Usuario usuario;
    private String desde;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        usuario = intent.getParcelableExtra(IntentExtras.USUARIO.llave);
        desde = intent.getStringExtra(IntentExtras.DESDE.llave);

        // TODO - ELIMINAR CUANDO SE REQUIERA : CÓDIGO PARA FINES INFORMATIVOS ÚNICAMENTE
        /**/ Toast mensaje = Toast.makeText(getApplicationContext(), (usuario.getUsuario() + " : " + usuario.getContrasenia() + " - " + desde), Toast.LENGTH_SHORT);
        /**/ mensaje.show();

        if(usuario == null)
            expulsar();

        btnAgenda = findViewById(R.id.btnAgenda);
        btnPacientes = findViewById(R.id.btnPacientes);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        btnAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* INDICA desde QUE VISTA SE ESTA MANDANDO A LLAMAR EL MENU
                 * HACIENDO USO DE ENUMS PARA EVITAR ERRORES POR TYPOS
                 * SE PUEDEN AGREGAR MAS 'EXTRAS' CONFORME SEA NECESARIO
                 *
                 */
                Intent intent = new Intent(Menu.this, Agenda.class);
                intent.putExtra(IntentExtras.USUARIO.llave, usuario);
                intent.putExtra(IntentExtras.DESDE.llave, ListaActividades.MENU.nombre);
                startActivity(intent);

            }
        });

        btnPacientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* INDICA desde QUE VISTA SE ESTA MANDANDO A LLAMAR EL MENU
                 * HACIENDO USO DE ENUMS PARA EVITAR ERRORES POR TYPOS
                 * SE PUEDEN AGREGAR MAS 'EXTRAS' CONFORME SEA NECESARIO
                 *
                 */
                Intent intent = new Intent(Menu.this, Pacientes.class);
                intent.putExtra(IntentExtras.USUARIO.llave, usuario);
                intent.putExtra(IntentExtras.DESDE.llave, ListaActividades.MENU.nombre);
                startActivity(intent);

            }
        });
    }

    private void expulsar() {
        usuario = null;
        Intent intent = new Intent(Menu.this, MainActivity.class);
        startActivity(intent);
    }
}
