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
import android.widget.Toast;

import com.example.caitamobile.Constantes.IntentExtras;
import com.example.caitamobile.Constantes.ListaActividades;
import com.example.caitamobile.modelo.Cita;
import com.example.caitamobile.modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private Conexion conexionMySQL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);

        Intent intent = getIntent();
        usuario = intent.getParcelableExtra(IntentExtras.USUARIO.llave);
        desde = intent.getStringExtra(IntentExtras.DESDE.llave);

        if(usuario == null)
            expulsar();

        /**
         * Se enlazan los componentes con el acticity
         */
        etFechaInicio = findViewById(R.id.etFechaInicio);
        etFechaFinal = findViewById(R.id.etFechaFinal);
        btnBuscarFecha = findViewById(R.id.btnBuscarFecha);
        btnLimpiar = findViewById(R.id.btnLimpiar);
        lvCitas = findViewById(R.id.lvCitas);
        conexionMySQL=new Conexion();

        /**
         * Se llena el array list con una funcion que hace consulta a base de datos
         * y con el se llena la tabla que esta en el activity validando si consulto o no
         * ---Aqui se llena cuando se acaba de creearel activity, pero tambien se hace al pulsar un boton
         */
        ArrayList<Cita> citas=null;
        try {
            citas = consultarCitas();
        } catch (SQLException e) {
            Toast.makeText(getApplicationContext(), "Ocurrio un error al consultar en la base de datos", Toast.LENGTH_SHORT).show();
        }//Fin del tryCatch (Si hubo un problema al consultar)
        if(citas!=null){
            // TODO - SE NECESITA CREAR UN ADAPTER PERSONALIZADO PARA LOS OBJETOS QUE VA A MOSTRAR EL LIST VIEW
            // AdapterPersonalizado adapterPersonalizado = new AdapterPersonalizado(getApplicationContext(), android.R.layout.simple_list_item_1, citas);
            ArrayAdapter adaptador = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, citas);
            lvCitas.setAdapter(adaptador);
        }//Fin del Si citas es nulo (Si no hubo conexion o no hay registros)


        /**
         * A partitr de AQUI estan los metodos que hacen acciones al ser presionados
         */
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
                //TODO incluir citas.get(position));
                //Inhabilitado mientras se arregla esta parte del codigo
                //intent.putExtra(IntentExtras.CITA.llave, citas.get(position));
                intent.putExtra(IntentExtras.DESDE.llave, ListaActividades.MENU.nombre);
                startActivity(intent);
            }
        });//Fin de si pulta la lista

        btnBuscarFecha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /**
                 * Una vez se desee hacer una consulta manual, se necesitan llenar ambos campos de las fechas
                 */
                if(!etFechaInicio.equals("")&&!etFechaFinal.equals("")){
                    // TODO - BUSCAR DATOS EN BASE A LA INFORMACIÓN PROPORCIONADA POR EL USUARIO
                }//Fin del if de fechas
                else{
                    Toast.makeText(getApplicationContext(), "No se ha permitesn campos vacios", Toast.LENGTH_SHORT).show();
                }//Fin de la validacion de campos
            }
        });//Fin de si quiere  buscar citas por fecha

        btnLimpiar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO - LIMPIAR TODOS LOS CAMPOS
                // todo - Quitar el boton y hacerlo una funcion automatica al consultar
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

    private ArrayList<Cita> consultarCitas() throws SQLException {
        ArrayList<Cita> citas= new ArrayList<>();
        Connection conn=conexionMySQL.CONN();
        if(conn!=null){
            PreparedStatement ps;
            String query="";
            if(!etFechaInicio.equals("")&&!etFechaFinal.equals("")){
                //TODO termiar la consulta para que sea completa segun los valores de las fechas
                query="select * from citas between ? and ?";//Este es un ejemplo, no es la consulta real
                ps=conn.prepareCall(query);
                //Todo - las siguientes lineas estan inhabilitadas esperando a se termine con las fechas correctas
                /*
                ps.setString();
                ps.setString();
                ps.setInt();
                */
            }else{
                query="select * from citas";
                ps=conn.prepareCall(query);
            }//Fin de la validacion que comprueba las fechas

            ResultSet rs=ps.executeQuery();
            while (rs.next()) {//TODO verificar qure el nombre de los campos sea el correcto
                /**
                 * Crea un objeto cita llenandolo con todos los valores
                 * y poniendolos en el arraylist llenandolo independiente al
                 */
                Cita c=new Cita(rs.getInt("ID_Cli"),rs.getString("nombreCli"), rs.getString("apellidoCli"), rs.getString("telefonoCli"), rs.getString("correo"), rs.getString("fecha"), rs.getString("hora"));
                citas.add(c);

            }//Aqui termina el while
           conn.close();
        }//Fin de si la conexion es diferente de nulo
        else{
            Toast.makeText(getApplicationContext(), "No se ha podido conectar con la base de datos", Toast.LENGTH_SHORT).show();
        }
        return citas;
    }//Fin de la funcion de buscar citas para llenar la tabla

    private void expulsar() {
        usuario = null;
        Intent intent = new Intent(Agenda.this, MainActivity.class);
        startActivity(intent);
    }


}//Fin de la clase
