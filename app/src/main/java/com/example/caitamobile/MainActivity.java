package com.example.caitamobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
        EditText etUsuarioEmp;
        EditText etPassEmp;
        Button btnIngresar;
        Button btnCerrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Se enlazan los componentes
        etUsuarioEmp = (EditText) findViewById(R.id.etUsuarioEmp);
        etPassEmp = (EditText) findViewById(R.id.etPassEmp);
        btnIngresar = (Button) findViewById(R.id.btnIngresar);
        btnCerrar = (Button) findViewById(R.id.btnCerrar);



        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etUsuarioEmp.getText().toString().equals("")&&!etPassEmp.getText().toString().equals("")) {
                    Intent ventana = new Intent(MainActivity.this, Main2Activity.class);
                    startActivity(ventana);
                }else{
                    Toast.makeText(getApplicationContext(),"Asegurese de rellenar ambos campos", Toast.LENGTH_LONG).show();
                }
            }//fin de la funcion on click
        });//Fin del boton ingresar

    }//Fin de on create
}//Fin de la clase
