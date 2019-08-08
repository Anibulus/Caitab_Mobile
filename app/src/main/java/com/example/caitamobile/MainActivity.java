package com.example.caitamobile;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MainActivity extends AppCompatActivity {
        EditText etUsuarioEmp;
        EditText etPassEmp;
        ProgressBar pbarInicio;
        Button btnIngresar;

        Conexion conexionSQL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsuarioEmp = (EditText) findViewById(R.id.etUsuarioEmp);
        etPassEmp = (EditText) findViewById(R.id.etPassEmp);
        btnIngresar = (Button) findViewById(R.id.btnIngresar);
        pbarInicio = (ProgressBar) findViewById(R.id.pbarInicio);
        pbarInicio.setVisibility(View.GONE);

        conexionSQL= new Conexion();


        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InicioSesion  in = new InicioSesion();
                in.execute("");
            }
        });


    }

    public class InicioSesion extends AsyncTask<String,String,String>
    {
        boolean exito = false;
        String msj;
        String usu=etUsuarioEmp.getText().toString();
        String pass=etPassEmp.getText().toString();
        Conexion con;

        @Override
        public void onPreExecute()
        {
            pbarInicio.setVisibility(View.VISIBLE);
            /*al momento de dar click, activa el Progress Bar*/
        }


        @Override
        protected String doInBackground(String... strings) {
            if(usu.trim().equals("")&&pass.trim().equals(""))
            {
                msj="No se permiten campos vacíos";
            }
            else
            {
                Connection con=conexionSQL.CONN();
                if(con!=null)
                {
                    String query="select * from Usuario where Usuario=? and Contrasenia=?";
                    try {
                        PreparedStatement pa=con.prepareStatement(query);
                        pa.setString(1,usu);
                        pa.setString(2,pass);
                        ResultSet rs=pa.executeQuery();
                        if (rs.next())
                        {
                            msj="Bienvenido al sistema "+usu;
                            exito=true;
                        }
                        else
                        {
                            msj="Usuario o Contraseña incorrectos";
                        }
                    } catch (SQLException e) {
                        //e.printStackTrace();
                        msj="Error de base de datos";
                    }
                }
                else
                {
                    msj="ERROR AL CONECTAR LA BASE DE DATOS";
                }
                try{
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return msj;
        }

        public void onPostExecute(String m)
        {
            pbarInicio.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(),m,Toast.LENGTH_LONG).show();
            if (exito){
                Intent ventana = new Intent(MainActivity.this, Menu.class);
                startActivity(ventana);
            }
        }
    }
}
