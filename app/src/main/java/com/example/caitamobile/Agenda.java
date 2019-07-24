package com.example.caitamobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class Agenda extends AppCompatActivity {

    EditText etFechaInicio;
    EditText etFechaFinal;
    Button btnBuscarFecha;
    Button btnRegresar;
    ListView lvCitas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        etFechaInicio = (EditText) findViewById(R.id.etFechaInicio);
        etFechaFinal = (EditText) findViewById(R.id.etFechaFinal);
        btnBuscarFecha = (Button) findViewById(R.id.btnBuscarFecha);
        btnRegresar = (Button) findViewById(R.id.btnRegresar);
        lvCitas = (ListView) findViewById(R.id.lvCitas);

        ArrayList datosLista = new ArrayList();
        datosLista.add("29/05/2019 - Juan - 3:30");
        datosLista.add("29/05/2019 - Pedro - 4:30");
        datosLista.add("30/05/2019 - Alfredo - 12:00");
        datosLista.add("30/05/2019 - Rafael - 2:45");

        ArrayAdapter adaptadorLista = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,datosLista);
        lvCitas.setAdapter(adaptadorLista);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(Agenda.this, General.class);
                startActivity(intento);
            }
        });

    }
}
