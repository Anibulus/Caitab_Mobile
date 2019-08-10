package com.example.caitamobile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.example.caitamobile.Constantes.IntentExtras;
import com.example.caitamobile.Constantes.ListaActividades;
import com.example.caitamobile.modelo.Cita;
import com.example.caitamobile.modelo.Usuario;

import java.util.ArrayList;
import java.util.Calendar;

public class Agenda extends AppCompatActivity implements View.OnClickListener {

    private EditText etFechaInicio;
    private EditText etFechaFinal;

    private Button btnBuscarFecha;
    private Button btnLimpiar;

    private ListView lvCitas;

    private Usuario usuario;
    private String desde;

    //Para las fechas
    private int dia, mes, ano;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        Intent intent = getIntent();
        usuario = intent.getParcelableExtra(IntentExtras.USUARIO.llave);
        desde = intent.getStringExtra(IntentExtras.DESDE.llave);

        if(usuario == null)
            expulsar();

        etFechaInicio = findViewById(R.id.etFechaInicio);
        etFechaFinal = findViewById(R.id.etFechaFinal);
        btnBuscarFecha = findViewById(R.id.btnBuscarFecha);
        btnLimpiar = findViewById(R.id.btnLimpiar);
        lvCitas = findViewById(R.id.lvCitas);


        final ArrayList<Cita> citas = consultarCitas();

        // TODO - SE NECESITA CREAR UN ADAPTER PERSONALIZADO PARA LOS OBJETOS QUE VA A MOSTRAR EL LIST VIEW
        // AdapterPersonalizado adapterPersonalizado = new AdapterPersonalizado(getApplicationContext(), android.R.layout.simple_list_item_1, citas);
        ArrayAdapter adaptador = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, citas);
        lvCitas.setAdapter(adaptador);

        lvCitas.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /* INDICA desde QUE VISTA SE ESTA MANDANDO A LLAMAR EL MENU
                 * HACIENDO USO DE ENUMS PARA EVITAR ERRORES POR TYPOS
                 * SE PUEDEN AGREGAR MAS 'EXTRAS' CONFORME SEA NECESARIO
                 *
                 */
                Intent intent = new Intent(Agenda.this, datosCita.class);
                intent.putExtra(IntentExtras.USUARIO.llave, usuario);
                intent.putExtra(IntentExtras.CITA.llave, citas.get(position));
                intent.putExtra(IntentExtras.DESDE.llave, ListaActividades.MENU.nombre);
                startActivity(intent);
            }
        });

        btnBuscarFecha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO - BUSCAR DATOS EN BASE A LA INFORMACIÓN PROPORCIONADA POR EL USUARIO
            }
        });

        btnLimpiar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO - LIMPIAR TODOS LOS CAMPOS
            }
        });

        /**
         * Este es el codigo para los pickDate
         */
        etFechaFinal.setOnClickListener(this);
        etFechaInicio.setOnClickListener(this);
    }//Fin del metodo onCreate

    /**
     * Esto es parte del codigo para pickDate
     */
    @Override
    public void onClick(View view) {
        if(view==etFechaFinal){
            final Calendar c= Calendar.getInstance();
            dia=c.get(Calendar.DAY_OF_MONTH);
            mes=c.get(Calendar.MONTH);
            ano=c.get(Calendar.YEAR);

            DatePickerDialog dpd=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {//ano, mes y dia
                    etFechaFinal.setText(i2+"/"+(i1+1)+"/"+i);
                }
            },ano,mes,dia);
            dpd.show();
        }else if(view==etFechaInicio){
            final Calendar c= Calendar.getInstance();
            dia=c.get(Calendar.DAY_OF_MONTH);
            mes=c.get(Calendar.MONTH);
            ano=c.get(Calendar.YEAR);

            DatePickerDialog dpd=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {//ano, mes y dia
                    etFechaInicio.setText(i2+"/"+(i1+1)+"/"+i);
                }
            },ano,mes,dia);
            dpd.show();
        }

    }//Fin del metodo onClick

    private ArrayList<Cita> consultarCitas() {
        ArrayList<Cita> citas = new ArrayList<>();

        citas.add(new Cita(
                1,
                "NOM1",
                "APES1",
                "TEL1",
                "CORR1",
                "FECH1",
                "HOR1"));

        citas.add(new Cita(
                1,
                "NOM2",
                "APES2",
                "TEL2",
                "CORR2",
                "FECH2",
                "HOR2"));

        citas.add(new Cita(
                1,
                "NOM3",
                "APES3",
                "TEL3",
                "CORR3",
                "FECH3",
                "HOR3"));

        return citas;
    }

    private void expulsar() {
        usuario = null;
        Intent intent = new Intent(Agenda.this, MainActivity.class);
        startActivity(intent);
    }


}//Fin de la clase
