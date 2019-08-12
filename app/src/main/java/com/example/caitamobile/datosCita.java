package com.example.caitamobile;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.caitamobile.Constantes.IntentExtras;
import com.example.caitamobile.Constantes.ListaActividades;
import com.example.caitamobile.modelo.Cita;
import com.example.caitamobile.modelo.Paciente;
import com.example.caitamobile.modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;

public class datosCita extends AppCompatActivity implements View.OnClickListener {

    private Usuario usuario;
    private String desde;
    private Cita cita;
    private Paciente paciente;
    private TextView nombre, apellido, telefono, correo;
    private EditText  hora,fecha;
    private Button btnModificar, btnSesion;
    private int ano, mes, dia;
    private Conexion conexionMySQL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_cita);
        /**
         * Aqui Se obtiene la informacion del activity pasado
         */
        Intent intent = getIntent();
        usuario = intent.getParcelableExtra(IntentExtras.USUARIO.llave);//Objeto Usuario
        paciente = intent.getParcelableExtra(IntentExtras.PACIENTE.llave);//Objeto Paciente
        cita = intent.getParcelableExtra(IntentExtras.CITA.llave);//Objeto Cita
        desde = intent.getStringExtra(IntentExtras.DESDE.llave);//De donde viene y a donde va
        /**
         * Aqui se enlazan los componentes
         */
        nombre= (TextView) findViewById(R.id.textView12);
        apellido= (TextView) findViewById(R.id.textView12);
        correo= (TextView) findViewById(R.id.textView12);
        telefono= (TextView) findViewById(R.id.textView12);
        fecha = (EditText) findViewById(R.id.editText2);
        hora=(EditText)findViewById(R.id.editText3);
        btnSesion=(Button)findViewById(R.id.button2);
        btnModificar=(Button)findViewById(R.id.btnDesc);
        conexionMySQL=new  Conexion();
        /**
         * En este punto se llena de informacion los textView de los intentExtra
         */
        nombre.setText(cita.getNombre_paciente());
        apellido.setText(cita.getApellidos_paciente());
        telefono.setText(cita.getTelefono());
        correo.setText(cita.getCorreo());
        fecha.setText(cita.getFecha());
        hora.setText("8:00");//Dato dummy
        /**
         * Escuchadores para  el pickDate
         */
        fecha.setOnClickListener(this);
        hora.setOnClickListener(this);
        /**
         * Si el usuario es nulo, se le expulsara
         */
        if(usuario == null)
            expulsar();

        /**
         * Acciones que ejecutan los botones al momento de ser presionados
         */
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO - Verificar si no hacen falta validaciones
                try {
                    if(modificarCita()){
                        Toast.makeText(getApplicationContext(), "De ha corregico correctamente", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "No se ha podido modificar", Toast.LENGTH_SHORT).show();
                    }
                } catch (SQLException e) {
                    Toast.makeText(getApplicationContext(), "Ocurrio un error mientras se modificaba", Toast.LENGTH_SHORT).show();
                }
            }
        });//Fin del boton de Modificar

        btnSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO llevarlo a la siguiente ventana
                Intent intent = new Intent(datosCita.this, Descripcion.class);
                intent.putExtra(IntentExtras.USUARIO.llave, usuario);
                //TODO - Corregir la siguiente linea
                intent.putExtra(IntentExtras.DESDE.llave, ListaActividades.MENU.nombre);
                startActivity(intent);
            }
        });//Fin del boton de sesion
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
        //TODO Agregar el pick de Tiempo y preguntar como separar la fecha de la hora
    }//Aqui termina el OnClick

    private boolean modificarCita() throws SQLException {
        boolean modificar=false;
        Connection conn=conexionMySQL.CONN();
        if(conn!=null){
            //TODO - Hacer que en objeto cita contenga IDCIta para el update y modificar la consulta del activity agenda
            String query="update cita set Fecha_Hora=? where ID_Cita=?";
            PreparedStatement ps=conn.prepareCall(query);
            ps.setString(1, fecha.getText().toString());//Nueva fecha
            ps.setInt(2, cita.getIdCita());//Que cita es
            if(ps.executeUpdate()>0){//Si se modifico el registro, es se c=modifico correctamente
                modificar=true;
            }
            conn.close();
        }//Fin de validacion de conexion diferente de nulo
        else{
            //TODO Toast que indique que no se creo la conexion a la base
        }
        return modificar;
    }//Fin de la funcion de modificar
}//Aqui termina la clase
