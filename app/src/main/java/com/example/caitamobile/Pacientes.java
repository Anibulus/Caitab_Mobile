package com.example.caitamobile;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.caitamobile.Constantes.IntentExtras;
import com.example.caitamobile.Constantes.ListaActividades;
import com.example.caitamobile.modelo.Paciente;
import com.example.caitamobile.modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Pacientes extends AppCompatActivity {

    private Usuario usuario;
    private String desde;

    private Button btnDatosGenerales;
    private Button btnAgendarCita;
    private Button btnVerExpediente;
    private Spinner spPacientes;
    private Conexion conexionMySQL;

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

        ArrayList<Paciente> paciente=null;

        if (paciente!=null) {
            ArrayAdapter adaptor = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item);
            spPacientes.setAdapter(adaptor);
        }else {
            try {
                consultaPacientes();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Ocurrio un error al consultar en la base de datos", Toast.LENGTH_SHORT).show();
            }
        }


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

    private ArrayList<Paciente> consultaPacientes() throws SQLException {
        ArrayList<Paciente> paciente= new ArrayList<>();
        Connection conn = conexionMySQL.CONN();
        if (conn!=null) {
            String query="select Nombre_C from cliente";
            try{
                PreparedStatement ps =conn.prepareStatement(query);
                ResultSet rs = ps.executeQuery();
                if (rs.next()){
                    while (rs.next()){
                        Paciente p = new Paciente(rs.getString("Nombre_C"));
                        paciente.add(p);
                    }
                    conn.close();
                }else {
                    Toast.makeText(getApplicationContext(), "No se cargaron registros", Toast.LENGTH_LONG).show();
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            Toast.makeText(getApplicationContext(), "No se conecto a la base datos", Toast.LENGTH_LONG).show();
        }

        return paciente;
    }

    private void expulsar() {
        usuario = null;
        Intent intent = new Intent(Pacientes.this, MainActivity.class);
        startActivity(intent);
    }
}
