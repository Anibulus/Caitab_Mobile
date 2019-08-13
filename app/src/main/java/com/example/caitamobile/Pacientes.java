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
    private Conexion conexionMySQL;
    private Spinner spPacientes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente);

        /**
         * aqui se obtirene informacion del activity pasado
         */
        Intent intent = getIntent();
        usuario = intent.getParcelableExtra(IntentExtras.USUARIO.llave);
        desde = intent.getStringExtra(IntentExtras.DESDE.llave);
        /**
         * Esta funcion permite expulsar si no se haq iniciado sesion
         */
        if(usuario == null)
            expulsar();
        /**
         * Aqui se enlazan los componentes
         */
        btnDatosGenerales =(Button) findViewById(R.id.btnDatosGenerales);
        btnAgendarCita = (Button)findViewById(R.id.btnAgendarCita);
        btnVerExpediente =(Button) findViewById(R.id.btnVerExpediente);
        spPacientes=(Spinner)findViewById(R.id.spPacientes);
        conexionMySQL=new Conexion();
        llenarSpinner();

        /**
         * Estoas son los botones que permiten que se lleven a cabo acciones
         */
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
    }//Fin del metodo OnCreate

    /**
     * A partir de aqui empiezan las acdiones que se dan en el activity
     * @return
     * @throws SQLException
     */
    private ArrayList<Paciente> consultaPacientes() throws SQLException {
        ArrayList<Paciente> paciente= new ArrayList<>();
        Connection conn = conexionMySQL.CONN();
        if (conn!=null) {
            String query="select Nombre_C from cliente";
            PreparedStatement ps =conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Paciente p = new Paciente(rs.getString("Nombre_C"));
                paciente.add(p);
            }
                conn.close();
        }else {
            Toast.makeText(getApplicationContext(), "No se conecto a la base datos", Toast.LENGTH_LONG).show();
        }

        return paciente;
    }//Fin del metodo consultar acientes

    private void llenarSpinner(){
        try {
            ArrayList<Paciente> paciente=consultaPacientes();
            if (paciente.size()>0) {
                ArrayAdapter adaptor = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,paciente);
                spPacientes.setAdapter(adaptor);
            }else {

            }//Fin de if-else
        } catch (SQLException e) {
            Toast.makeText(getApplicationContext(), "Ocurrio un error al consultar", Toast.LENGTH_LONG).show();
        }


    }//Fin de llenarSpinner

    private void expulsar() {
        usuario = null;
        Intent intent = new Intent(Pacientes.this, MainActivity.class);
        startActivity(intent);
    }
}//Fin de la clase
