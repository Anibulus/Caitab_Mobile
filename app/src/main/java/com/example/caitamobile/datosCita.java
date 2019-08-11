package com.example.caitamobile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.caitamobile.Constantes.IntentExtras;
import com.example.caitamobile.modelo.Cita;
import com.example.caitamobile.modelo.Paciente;
import com.example.caitamobile.modelo.Usuario;

import java.util.Calendar;

public class datosCita extends AppCompatActivity implements View.OnClickListener {

    private Usuario usuario;
    private String desde;
    private Cita cita;
    private Paciente paciente;
    private EditText fecha;
    private int ano, mes, dia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_cita);
        /**
         * Aqui se enlazan los componentes con el intent pasado
         */
        Intent intent = getIntent();
        usuario = intent.getParcelableExtra(IntentExtras.USUARIO.llave);
        paciente = intent.getParcelableExtra(IntentExtras.PACIENTE.llave);
        cita = intent.getParcelableExtra(IntentExtras.CITA.llave);
        desde = intent.getStringExtra(IntentExtras.DESDE.llave);
        /**
         * Aqui se enlazan los componentes
         */
        fecha = findViewById(R.id.editText2);
        //Todo Enlazar del de tiempo
        /**
         * Escuchadores para  el pickDate
         */
        fecha.setOnClickListener(this);
        /**
         * Si el usuario es nulo, se le expulsara
         */
        if(usuario == null)
            expulsar();
    }//Fin del metodo OnCreate

    private void expulsar() {
        usuario = null;
        Intent intent = new Intent(datosCita.this, MainActivity.class);
        startActivity(intent);
    }//Aqui ntermina la metodo de de expulsar

    /**
     * Este codigo permite que al  tapear la fecha, muestre el calendario
     * @param view
     */
    @Override
    public void onClick(View view) {
        if(view==fecha){
            final Calendar c= Calendar.getInstance();
            dia=c.get(Calendar.DAY_OF_MONTH);
            mes=c.get(Calendar.MONTH);
            ano=c.get(Calendar.YEAR);

            DatePickerDialog dpd=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {//ano, mes y dia
                    fecha.setText(i2+"/"+(i1+1)+"/"+i);
                }
            },ano,mes,dia);
            dpd.show();
        }

        //TODO Agregar el pick de Tiempo
    }//Aqui termina el OnClick
}//Aqui termina la clase
