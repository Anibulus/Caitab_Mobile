package com.example.caitamobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.caitamobile.Constantes.ListaActividades;
import com.example.caitamobile.Constantes.IntentExtras;
import com.example.caitamobile.modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MainActivity extends AppCompatActivity {
        private EditText etUsuario;
        private EditText etContrasenia;
        private Button btnIngresar;
        private Button btnCerrar;
        private Conexion conexionMySQL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**
         * Aqui se enlazan los componentes que tiene el Activity (Inicio de sesion)
         */
        etUsuario = findViewById(R.id.etUsuario);
        etContrasenia = findViewById(R.id.etContrasenia);
        btnIngresar = findViewById(R.id.btnIngresar);
        btnCerrar = findViewById(R.id.btnCerrar);
        conexionMySQL=new Conexion();

        /**
         * Al pulsar el Boton Ingresar, se llama una funcion que validara el inicio de Sesion, similar a un DAO (Data access object)
         */
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Usuario usuario = null;
                try {
                    usuario = validarCuenta(etUsuario.getText().toString(), etContrasenia.getText().toString());
                } catch (SQLException e) {
                    Toast.makeText(getApplicationContext(), "Error en la conexion a la base de Datos", Toast.LENGTH_SHORT).show();
                }
                /**
                 * Despues de realizar la funcion y conseguir un resultado, valida lo que la variable 'usuario' contiene
                 */
                if(usuario != null) {
                    /**
                     * En el caso de no haber encontrado un usuario, se notifica la persona que lo intente otra vez y borrara lo que contenga la contrasena
                     */
                    mostrarSiguienteVista(usuario);
                }//Fin de la validacion del usuario\
            }//Fin del metodo onClick

            private Usuario validarCuenta(String usuario, String contrasenia) throws SQLException {
                /**
                 * Se crea un objeto usuario nulo al principio y se llenara en
                 * el caso de que el empleado se encuentre para coincidir
                 * donde se llama la funcion
                 */
                Usuario usu=null;
                if(!usuario.equals("")&&!contrasenia.equals("")) {
                    Connection conn = conexionMySQL.CONN();
                    if (conn != null) {
                        String query = "select * from Usuario where Usuario=? and Contrasenia=?";

                        PreparedStatement ps = null;
                        ps = conn.prepareStatement(query);
                        ps.setString(1, usuario);
                        ps.setString(2, contrasenia);
                        ResultSet rs = ps.executeQuery();
                        if (rs.next()) {
                            /**
                             * Se llena el objeto USU cuando se encuentra lo que hace que permita llevarlo a la siguiente ventana
                             */
                            usu = new Usuario(rs.getInt("ID_Usu"), rs.getString("Usuario"), rs.getString("Contrasenia"));//id,usuario,contrasena
                        } else {
                            Toast.makeText(getApplicationContext(), "Usuario o Contrasena incorrectos", Toast.LENGTH_SHORT).show();
                            etContrasenia.setText("");//Se vacia el campo contrasena en caso de equivocarse
                        }
                        conn.close();
                    } else {
                        Toast.makeText(getApplicationContext(), "No se ha podido conectar a la base de Datos", Toast.LENGTH_SHORT).show();
                    }//Fin de SI la conexion a la base de datos es diferente de nula
                }
                else{
                    Toast.makeText(getApplicationContext(), "No se permiten campos vacios", Toast.LENGTH_SHORT).show();
                }
                /**
                 * Retorna al usuario
                 */
                return usu;//Retorna un objeto funcional
            }//Fin de la funcion que valida el inicio de sesion

            private void mostrarSiguienteVista(Usuario usuario) {

                /**
                 * INDICA DESDE QUE VISTA SE ESTA MANDANDO A LLAMAR EL MENU
                 * HACIENDO USO DE ENUMS PARA EVITAR ERRORES POR TYPOS
                 * SE PUEDEN AGREGAR MAS 'EXTRAS' CONFORME SEA NECESARIO
                 */
                Intent intent = new Intent(MainActivity.this, Menu.class);
                intent.putExtra(IntentExtras.DESDE.llave, ListaActividades.MAIN_ACTIVITY.nombre);
                intent.putExtra(IntentExtras.USUARIO.llave, usuario);
                startActivity(intent);
            }
        });//Fin del metodo que escucha el boton de ingresar

        /**
         * Este metodo cierra la aplicacion
         */
        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });

    }//Aqui termina el ONCreate
}//Aqui termina la clase
