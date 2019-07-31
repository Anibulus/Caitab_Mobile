package com.example.caitamobile;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MainActivity extends AppCompatActivity {
        EditText etUsuarioEmp;
        EditText etPassEmp;
        Button btnIngresar;
        Button btnCerrar;
        conexionMySQL con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Se enlazan los componentes
        etUsuarioEmp = (EditText) findViewById(R.id.etUsuarioEmp);
        etPassEmp = (EditText) findViewById(R.id.etPassEmp);
        btnIngresar = (Button) findViewById(R.id.btnIngresar);
        btnCerrar = (Button) findViewById(R.id.btnCerrar);
        con=new conexionMySQL();


        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inicioSesion is=new inicioSesion();
                is.execute("");
            }//fin de la funcion on click
        });//Fin del boton ingresar

    }//Fin de on create

    public class inicioSesion extends AsyncTask<String,String,String> {
        boolean entrar = false;
        String mensaje;
        String usuario = etUsuarioEmp.getText().toString();
        String contra = etPassEmp.getText().toString();
        @Override
        public void onPreExecute() {
            //Esta linea es para mostrar un circulo de proceso
           // pbInicio.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPostExecute(String r) {
            //Esta linea es para mostrar un circulo de proceso
            //pbInicio.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), r, Toast.LENGTH_LONG).show();
            if (entrar) {
                Intent ventana = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(ventana);
            }//Fin de el si Entrar es verdadero
        }//Termina onPostExecute(String r)

        @Override
        protected String doInBackground(String... strings) {
            if (usuario.trim().equals("") || contra.trim().equals("")) {
                mensaje = "No se permiten campos vacios";
            }
            else {
                System.out.println("-----------Entre a validar");
                //System.out.println(con + " ----Esto es la conexion");
                Connection conn = con.CONN();
                System.out.println(conn);
                if (conn!=null) {
                    System.out.println("-----------Entre a validar1");
                    String query = "select * from usuario where Usuario=? and Contrasenia=?;";
                    try {
                        PreparedStatement ps = conn.prepareStatement(query);
                        ps.setString(1, usuario);
                        ps.setString(2, contra);
                        ResultSet rs = ps.executeQuery();
                        if (rs.next()) {
                            System.out.println("-----------Entre a validar2");
                            mensaje = "Bienvenido al Sistema";
                            entrar = true;
                        } else {
                            mensaje = "Usuario o contrasena invalidos";
                        }
                    } catch (SQLException e) {
                        mensaje = "Error en la operacion de base de datos: " + e.getMessage();
                    }
                } else {
                    mensaje = "Error al conectar la base de datos";
                }/*
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }*/
            }//Fin del else que entra a iniciar sesion
            return mensaje;
        }//Fin de la funcion do in BackGorung
    }//Fin de la subClase inicio sesion

}//Fin de la clase
