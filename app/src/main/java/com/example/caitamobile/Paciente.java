package com.example.caitamobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Paciente extends AppCompatActivity {

    Button btnDatosGenerales, btnAgendarCita, btnVerExpediente, btnMisPacientes, btnMenuPrincipal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paciente);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        btnDatosGenerales = (Button) findViewById(R.id.btnDatosGenerales);
        btnAgendarCita = (Button) findViewById(R.id.btnAgendarCita);
        btnVerExpediente = (Button) findViewById(R.id.btnVerExpediente);
        btnMisPacientes = (Button) findViewById(R.id.btnMisPacientes);
        btnMenuPrincipal = (Button) findViewById(R.id.btnMenuPrincipal);

        btnDatosGenerales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent datos_genrales = new Intent(Paciente.this, DatosGenerales.class);
                startActivity(datos_genrales);
            }
        });

        btnAgendarCita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnVerExpediente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent expediente = new Intent(Paciente.this, Expediente.class);
                startActivity(expediente);
            }
        });

        btnMisPacientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnMenuPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent menu_principal = new Intent(Paciente.this, Menu.class);
                    startActivity(menu_principal);
            }
        });
    }
}
