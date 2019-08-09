package com.example.caitamobile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

public class Agenda extends AppCompatActivity implements View.OnClickListener {

    EditText etFechaInicio;
    EditText etFechaFinal;
    Button btnBuscarFecha;
    Button btnRegresar;
    ListView lvCitas;

    //Variables necesarias para pick date
    private int dia, mes, ano;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        etFechaInicio = (EditText) findViewById(R.id.etFechaInicio);
        etFechaFinal = (EditText) findViewById(R.id.etFechaFinal);
        btnBuscarFecha = (Button) findViewById(R.id.btnBuscarFecha);
        btnRegresar = (Button) findViewById(R.id.btnLimpiar);

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

        //Aqui comienza el codigo para el pick date requerido
        etFechaInicio.setOnClickListener(this);
        etFechaFinal.setOnClickListener(this);
        //Se vuelven escuchadores para al momento de ser cliqueados se haga la funcion de abajo (onClick)

    }//Fin del metodo onCreate

    @Override
    public void onClick(View view) {
        if(view==etFechaInicio){

            final Calendar c= Calendar.getInstance();
            dia=c.get(Calendar.DAY_OF_MONTH);
            mes=c.get(Calendar.MONTH);
            ano=c.get(Calendar.YEAR);

            DatePickerDialog dpd=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    etFechaInicio.setText(i2+"-"+(i1+1)+"-"+i);//Se le agrega 1 al mes porque se cuenta como arreglo
                }
            },dia,mes,ano);

            dpd.show();

        }else if(view==etFechaFinal){

        }
    }//Fin del metodo on click
}//Fin de la clase
