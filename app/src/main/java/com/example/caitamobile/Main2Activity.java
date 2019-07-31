package com.example.caitamobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Main2Activity extends AppCompatActivity {
    Button btnCerrarSesion;
    ImageView imgAgenda, imgExpediente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        btnCerrarSesion=(Button)findViewById(R.id.btncerrarsesion);
        imgAgenda=(ImageView)findViewById(R.id.imageButton);
        imgExpediente=(ImageView)findViewById(R.id.imageButton3);

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ventana = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(ventana);
            }//Fin de la funcion on click
        });//Fin de la funcion de Boton cerrar sesion

        imgAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Redireccionar a la ventana que tenga que ir segun sea el caso
                Intent ventana = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(ventana);
            }
        });

        imgExpediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Redireccionar a la ventana que tenga que ir segun sea el caso
                Intent ventana = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(ventana);
            }
        });
    }//Fin del onCreate
}//Fin de la clase
