package com.example.caitamobile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

public class Agenda extends AppCompatActivity   {

    TextView tvFecha1;
    Button btnFechaInicio;
    EditText etFechaFinal;
    Button btnBuscarFecha;
    Button btnRegresar;
    ListView lvCitas;
    ArrayList citas = new ArrayList();
    Conexion conexionSQL;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);


        setContentView(R.layout.activity_agenda);
        conexionSQL = new Conexion();



        btnBuscarFecha = (Button) findViewById(R.id.btnBuscarFecha);
        btnRegresar = (Button) findViewById(R.id.btnRegresar);
        lvCitas = (ListView) findViewById(R.id.lvCitas);


        consultarTodo consultarTodo = new consultarTodo();
        consultarTodo.execute("Exitoso");




        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(Agenda.this, General.class);
                startActivity(intento);
            }
        });

    }


    public void LLenarlista(ArrayList lista) {
        ArrayAdapter adatador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lista);
        lvCitas.setAdapter(adatador);
    }




    public class consultarTodo extends AsyncTask<String, String, String> {

        String msj = "";
        boolean exito = false;


        protected void onPreExecute() {

        }

        protected void onPostExecute(String s) {


            if (exito) {
                LLenarlista(citas);
            } else {
                citas.clear();
                LLenarlista(citas);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected String doInBackground(String... strings) {

            Connection con = conexionSQL.CONN();
            if (con != null) {
                String query = "select * from Cita";
                try {
                    PreparedStatement ps = con.prepareStatement(query);


                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        msj = "No hay registros";
                    } else {
                        exito = true;
                        do {
                            citas.add("ID_Cita: " + rs.getString("ID_Cita") + "\nID_Emp: " + rs.getString("ID_Emp") + "\nID_Cli: " + rs.getString("ID_Cli") + "\nFecha y Hora: " + rs.getString("Fecha_Hora") + "\nConsultorio:" + rs.getString("Consultorio"));
                        } while (rs.next());
                    }
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                msj = "Error al conectar la base de datos.";
            }
            return null;
        }
    }
}
