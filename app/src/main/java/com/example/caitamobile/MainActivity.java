package com.example.caitamobile;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.caitamobile.Constantes.ListaActividades;
import com.example.caitamobile.Constantes.IntentExtras;
import com.example.caitamobile.modelo.Usuario;


public class MainActivity extends AppCompatActivity {
        private EditText etUsuario;
        private EditText etContrasenia;
        private Button btnIngresar;
        private Button btnCerrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsuario = findViewById(R.id.etUsuario);
        etContrasenia = findViewById(R.id.etContrasenia);
        btnIngresar = findViewById(R.id.btnIngresar);
        btnCerrar = findViewById(R.id.btnCerrar);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Usuario usuario = validarCuenta(etUsuario.getText().toString(), etContrasenia.getText().toString());

                if(usuario == null) {

                    String texto = "Usuario o contraseña incorrectos";
                    Toast mensaje = Toast.makeText(getApplicationContext(), texto, Toast.LENGTH_SHORT);
                    mensaje.show();

                } else {

                    mostrarSiguienteVista(usuario);

                }

            }

            private Usuario validarCuenta(String usuario, String contrasenia) {
                try {

                    // TODO - USUARIO = ... CONSULTA SQL
                    return new Usuario(1, usuario, contrasenia); // ACTUALMENTE RETORNANDO OBJETO DUMMY

                } catch (SQLException ex) {
                    return null;
                }
            }

            private void mostrarSiguienteVista(Usuario usuario) {

                /* INDICA DESDE QUE VISTA SE ESTA MANDANDO A LLAMAR EL MENU
                 * HACIENDO USO DE ENUMS PARA EVITAR ERRORES POR TYPOS
                 * SE PUEDEN AGREGAR MAS 'EXTRAS' CONFORME SEA NECESARIO
                 *
                 */
                Intent intent = new Intent(MainActivity.this, Menu.class);
                intent.putExtra(IntentExtras.DESDE.llave, ListaActividades.MAIN_ACTIVITY.nombre);
                intent.putExtra(IntentExtras.USUARIO.llave, usuario);
                startActivity(intent);
            }
        });

        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });

    }//Aqui termina el ONCreate
}//Aqui termina la clase
