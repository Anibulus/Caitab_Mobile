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
        Conexion con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsuarioEmp = (EditText) findViewById(R.id.etUsuarioEmp);
        etPassEmp = (EditText) findViewById(R.id.etPassEmp);
        btnIngresar = (Button) findViewById(R.id.btnIngresar);
        btnCerrar = (Button) findViewById(R.id.btnCerrar);
        con=new Conexion();

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inicioSesion is=new inicioSesion();
                is.execute("");
            }
        });

        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }//Fin de la funcion on create


    public class inicioSesion extends AsyncTask<String,String,String> {
        boolean entrar = false;
        String mensaje;
        String usuario = etUsuarioEmp.getText().toString();
        String contra = etPassEmp.getText().toString();

        @Override
        public void onPreExecute() {
            //pbInicio.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPostExecute(String r) {
            //pbInicio.setVisibility(View.GONE);
            Toast.makeText(getApplicationContext(), r, Toast.LENGTH_LONG).show();
            if (entrar) {
                Intent ventana = new Intent(MainActivity.this, Menu.class);
                startActivity(ventana);
            }//Fin de el si Entrar es verdadero
        }//Termina onPostExecute(String r)

        @Override
        protected String doInBackground(String... strings) {
            if (usuario.trim().equals("") || contra.trim().equals("")) {
                mensaje = "No se permiten campos vacios";
            } else {
                System.out.println("----Entro a validar");
                Connection conn = con.CONN();
                System.out.println("----Entro a validar2");
                if (conn!=null) {
                    System.out.println("----Entro a validar3");
                    String query = "select * from usuario where Usuario=? and Contrasenia=?";
                    try {
                        PreparedStatement ps = conn.prepareStatement(query);
                        ps.setString(1, usuario);
                        ps.setString(2, contra);
                        ResultSet rs = ps.executeQuery();
                        if (rs.next()) {
                            mensaje = "Bienvenido al Sistema";
                            entrar = true;
                        } else {
                            mensaje = "Usuario o contrasena invalidos";
                        }
                    } catch (SQLException e) {
                        //e.printStackTrace();//el usuario final no debe ver el error que esta sucediendo
                        mensaje = "Error en la operacion de base de datos: " + e.getMessage();
                    }
                } else {
                    mensaje = "Error al conectar la base de datos";
                }
                /*try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }*/
            }
            return mensaje;
        }//Fin del metodo do in background
    }//Fin de la clase secundaria

}//fin de la clase
