package com.example.caitamobile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
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

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Agenda extends AppCompatActivity implements View.OnClickListener {

    private EditText etFechaInicio;
    private EditText etFechaFinal;

    private Button btnBuscarFecha;

    private ListView lvCitas;

    private Usuario usuario;
    private String desde;
    //Para las fechas
    private int dia, mes, ano;
    private Conexion conexionMySQL;

    ArrayList<Cita> citas=null;
    ArrayAdapter adaptador=null;
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
        lvCitas = findViewById(R.id.lvCitas);
        conexionMySQL=new Conexion();

        etFechaInicio.setInputType(InputType.TYPE_NULL);
        etFechaFinal.setInputType(InputType.TYPE_NULL);

        /**
         * Se llena el array list con una funcion que hace consulta a base de datos
         * y con el se llena la tabla que esta en el activity validando si consulto o no
         * ---Aqui se llena cuando se acaba de creearel activity, pero tambien se hace al pulsar un boton
         * Y son vacios para llenarlos en las dos ocasiones sin gastar regursos por tenerlos activos
         */
        llenarListView();

        /**
         * A partir de AQUI estan los metodos que hacen acciones al ser presionados
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
                if(citas.size()>0){
                    intent.putExtra(IntentExtras.CITA.llave, citas.get(position));
                }
                intent.putExtra(IntentExtras.DESDE.llave, ListaActividades.MENU.nombre);
                intent.putExtra("idCita", citas.get(position).getIdCita());
                startActivity(intent);
            }
        });//Fin de si pulta la lista

        btnBuscarFecha.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /**
                 * Una vez se desee hacer una consulta manual, se necesitan llenar ambos campos de las fechas
                 */
                if(!etFechaInicio.getText().toString().equals("")&&!etFechaFinal.getText().toString().equals("")){
                    if (validarFechas(etFechaInicio.getText().toString(),etFechaFinal.getText().toString())){
                        llenarListView();
                    }else{
                        Toast.makeText(getApplicationContext(), "La segunda fecha no sebe ser menor a la primera", Toast.LENGTH_SHORT).show();
                    }
                }//Fin del if de fechas
                else{
                    Toast.makeText(getApplicationContext(), "No se ha permiten campos vacios", Toast.LENGTH_SHORT).show();
                }//Fin de la validacion de campos
            }
        });//Fin de si quiere  buscar citas por fecha


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
                    String dia=String.valueOf(i2);//dia
                    String mes=String.valueOf(i1 + 1);//mes
                    if(i2<10) {
                        dia=String.valueOf("0"+i2);//dia
                    }
                    if(i1<10){
                        mes=String.valueOf("0"+mes);//mes
                    }
                    etFechaFinal.setText(i + "/" + mes + "/" + dia);
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
                    String dia=String.valueOf(i2);//dia
                    String mes=String.valueOf(i1 + 1);//mes
                    if(i2<10) {
                        dia=String.valueOf("0"+i2);//dia
                    }
                    if(i1<10){
                        mes=String.valueOf("0"+mes);//mes
                    }
                    etFechaInicio.setText(i + "/" + mes + "/" + dia);
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
            if(!etFechaInicio.getText().toString().equals("")&&!etFechaFinal.getText().toString().equals("")){
                String fechaInicio=etFechaInicio.getText().toString()+" 00:00:00:0";
                String fechaFin=etFechaFinal.getText().toString()+" 23:55:00:0";//Esto permite que se muestren las citas incluso de un solo dia
                query="SELECT c.ID_Cita,c.ID_Cli,cli.Nombre_C,cli.Apellidos_C,cli.Tel_C,cli.Email_C,c.Fecha_Hora, c.Consultorio FROM cliente cli \n" +
                        "JOIN cita c ON c.ID_Cli = cli.ID_Cli where ID_Emp=? and c.Fecha_Hora BETWEEN ? AND ? ORDER BY c.Fecha_Hora ASC;";//Este es un ejemplo, no es la consulta real
                ps=conn.prepareCall(query);
                ps.setInt(1, conexionMySQL.getEmpleadoActivo());
                System.out.println(conexionMySQL.getEmpleadoActivo()+" "+fechaInicio +"  "+fechaFin);
                ps.setString(2,fechaInicio);
                ps.setString(3,fechaFin);
            }else{
                query="SELECT c.ID_Cita,c.ID_Cli,cli.Nombre_C,cli.Apellidos_C,cli.Tel_C,cli.Email_C,c.Fecha_Hora,c.Consultorio FROM cliente cli\n" +
                        "JOIN cita c ON c.ID_Cli = cli.ID_Cli where ID_Emp=? ORDER BY c.Fecha_Hora ASC;";
                ps=conn.prepareCall(query);
                ps.setInt(1, conexionMySQL.getEmpleadoActivo());
            }//Fin de la validacion que comprueba las fechas


            ResultSet rs=ps.executeQuery();
            while (rs.next()) {
                /**
                 * Crea un objeto cita llenandolo con todos los valores
                 * y poniendolos en el arraylist llenandolo
                 */
                Cita c=new Cita(rs.getInt("ID_Cita"),rs.getInt("ID_Cli"),rs.getString("Nombre_C"), rs.getString("Apellidos_C"), rs.getString("Tel_C"), rs.getString("Email_C"), rs.getString("Fecha_Hora"), rs.getInt("Consultorio"));
                System.out.println(rs.getInt("ID_Cita"));
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

    private void llenarListView(){
        try {
            citas = consultarCitas();
        } catch (SQLException e) {
            Toast.makeText(getApplicationContext(), "Ocurrio un error al consultar en la base de datos", Toast.LENGTH_SHORT).show();
        }//Fin del tryCatch (Si hubo un problema al consultar)
        if(citas.size()>0){
            /**
             * El siguente siclo es para vaciar un array en otro y que sea visible la informacion
             */
            ArrayList<String> bonito=new ArrayList<>();
            for (int i=0;i<citas.size();i++) {
                bonito.add(citas.get(i).getDescripcion());
            }
            adaptador = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, bonito);
            System.out.println(citas.get(0).getIdCita());
            lvCitas.setEnabled(true);
            lvCitas.setAdapter(adaptador);
        }//Fin del Si citas es nulo (Si no hubo conexion o no hay registros)
        else{
            ArrayList<String> bonito=new ArrayList<>();
            bonito.add("No hay citas pendientes en estas fechas");
            adaptador = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, bonito);
            lvCitas.setEnabled(false);
            lvCitas.setAdapter(adaptador);
        }
        adaptador=null;
    }

    private boolean validarFechas(String fechaIni, String fechaFin){
        boolean coincide=true;
        /**
         * las siguientes dos variables van a comparar en secciones ano mes y dia verificando que
         * uno no sea menor a otro y comparandolos con un parseo a enteros
         * y debido a que estan estandarizados, mantienen siempre ese formato y espacios siempre
         */
        String comparador,comparador2;
        comparador=fechaIni.substring(0,4);
        comparador2=fechaFin.substring(0,4);
        System.out.println("-------Aqui"+comparador);
        int fec,fec2;
        fec=Integer.parseInt(comparador);//Fecha ini
        fec2=Integer.parseInt(comparador2);//Fecha fin
        if(fec>fec2){
            /**
             * Debido a que el ano de la primera fecha es mayor, no permite consultar
             */
            coincide=false;
        }//Fin del comparador de anos
        comparador=fechaIni.substring(5,7);
        comparador2=fechaFin.substring(5,7);
        fec=Integer.parseInt(comparador);//Fecha ini
        fec2=Integer.parseInt(comparador2);//Fecha fin
        System.out.println("-------Aqui"+comparador);
        System.out.println(fechaIni);
        if(fec>fec2){
            /**
             * Debido a que el mes de la primera fecha es mayor, no permite consultar
             */
            coincide=false;
        }//Fin del comparador de meses
        comparador=fechaIni.substring(8,10);
        comparador2=fechaFin.substring(8,10);
        fec=Integer.parseInt(comparador);//Fecha ini
        fec2=Integer.parseInt(comparador2);//Fecha fin
        if(fec>fec2){
            /**
             * Debido a que el dia de la primera fecha es mayor, no permite consultar
             */
            coincide=false;
        }//Fin del comparador de dias
        System.out.println("coincide es "+coincide);
        return coincide;
    }//Fin de validar fechas

}//Fin de la clase
