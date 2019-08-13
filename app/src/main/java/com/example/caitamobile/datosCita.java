package com.example.caitamobile;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.caitamobile.Constantes.IntentExtras;
import com.example.caitamobile.Constantes.ListaActividades;
import com.example.caitamobile.modelo.Cita;
import com.example.caitamobile.modelo.Paciente;
import com.example.caitamobile.modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Clock;
import java.util.Calendar;

public class datosCita extends AppCompatActivity implements View.OnClickListener {

    private Usuario usuario;
    private String desde;
    private Cita cita;
    private int idCita;
    private Paciente paciente;
    private TextView nombre, apellido, telefono, correo;
    private EditText  hora,fecha;
    private Button btnModificar, btnSesion;
    private int ano, mes, dia, horas, minutos;
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
        System.out.println(cita.getIdCita()+"Soy getCita");
        desde = intent.getStringExtra(IntentExtras.DESDE.llave);//De donde viene y a donde va
        Bundle b=getIntent().getExtras();
        idCita=b.getInt("idCita");
        /**
         * Aqui se enlazan los componentes
         */
        nombre= (TextView) findViewById(R.id.textView12);
        apellido= (TextView) findViewById(R.id.textView13);
        correo= (TextView) findViewById(R.id.textView15);
        telefono= (TextView) findViewById(R.id.textView14);
        fecha = (EditText) findViewById(R.id.editText2);
        hora=(EditText)findViewById(R.id.editText3);//Ahora consultorio
        btnSesion=(Button)findViewById(R.id.btnDesc);
        btnModificar=(Button)findViewById(R.id.button2);
        conexionMySQL=new  Conexion();
        /**
         * En este punto se llena de informacion los textView de los intentExtra
         */
        nombre.setText(cita.getNombre_paciente());
        apellido.setText(cita.getApellidos_paciente());
        telefono.setText(cita.getTelefono());
        correo.setText(cita.getCorreo());
        fecha.setText(cita.getFecha());
        hora.setText(String.valueOf(cita.getIdConsultorio()));
        fecha.setInputType(InputType.TYPE_NULL);
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
                if(validarFechas(fecha.getText().toString())) {
                    try {
                        if (modificarCita()) {
                            Toast.makeText(getApplicationContext(), "Se ha corregido correctamente", Toast.LENGTH_SHORT).show();
                            Intent regreso = new Intent(datosCita.this, Agenda.class);
                            regreso.putExtra(IntentExtras.USUARIO.llave, usuario);
                            startActivity(regreso);
                        } else {
                            Toast.makeText(getApplicationContext(), "No se ha podido modificar", Toast.LENGTH_SHORT).show();
                        }
                    } catch (SQLException e) {
                        Toast.makeText(getApplicationContext(), "Ocurrio un error mientras se modificaba", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "No puedes guardar una fecha antes de hoy", Toast.LENGTH_SHORT).show();
                }
            }
        });//Fin del boton de Modificar

        btnSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(validarDiaDeSesion(idCita)) {
                        Intent intent = new Intent(datosCita.this, Descripcion.class);
                        intent.putExtra(IntentExtras.USUARIO.llave, usuario);
                        intent.putExtra(IntentExtras.DESDE.llave, ListaActividades.MENU.nombre);
                        intent.putExtra("idCita", idCita);
                        intent.putExtra("idCliente", cita.getId_paciente());
                        intent.putExtra("Cita", cita);
                        //TODO -
                        intent.putExtra(IntentExtras.DESDE.llave, ListaActividades.MENU.nombre);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getApplicationContext(), "La sesion no es para el dia de hoy", Toast.LENGTH_SHORT).show();
                    }
                } catch (SQLException e) {
                    Toast.makeText(getApplicationContext(), "Necesitas conexion con el servido para esta accion", Toast.LENGTH_SHORT).show();
                }
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
            horas=c.get(Calendar.HOUR_OF_DAY);
            minutos=c.get(Calendar.MINUTE);

            TimePickerDialog tpd=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {//horas, minutos
                    String fec=fecha.getText().toString();
                    if(i>8&&i<21) {//Horario del lugar
                        btnModificar.setEnabled(true);
                        if (i1 < 10) {
                            String minutos = "0" + String.valueOf(i1);
                            fecha.setText(String.valueOf(fec + " " + i + ":" + minutos));
                        } else {
                            fecha.setText(String.valueOf(fec + " " + i + ":" + i1));
                        }
                    }//Fin del if para verificar la hora en la que se lleva a cabo la cita
                    else{
                        btnModificar.setEnabled(false);
                        Toast.makeText(getApplicationContext(), "No se puede aceptar ese horario", Toast.LENGTH_SHORT).show();
                    }
                }
            }, horas,minutos,false);//
            tpd.show();

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
                    fecha.setText(i + "/" + mes + "/" + dia);
                }
            },ano,mes,dia);
            dpd.show();
        }//Fin de si seleccionan la fecha
    }//Aqui termina el OnClick

    private boolean modificarCita() throws SQLException {
        boolean modificar=false;
        Connection conn=conexionMySQL.CONN();
        if(conn!=null){
            String query="select * from cita where Fecha_Hora=? and Consultorio=?";
            PreparedStatement ps=conn.prepareCall(query);
            ps.setString(1,fecha.getText().toString());
            ps.setInt(2,Integer.parseInt(hora.getText().toString()));
            ResultSet rs=ps.executeQuery();
            if(!rs.next()){//Si no encuentra registros se llevara la modificacion de la cita
                query="update cita set Fecha_Hora=?, Consultorio=? where ID_Cita=?";
                ps=conn.prepareCall(query);
                ps.setString(1, fecha.getText().toString());//Nueva fecha
                ps.setInt(2, Integer.parseInt(String.valueOf(hora.getText())));//Que consultorio
                ps.setInt(3, idCita);//Que cita es
                System.out.println(""+ fecha.getText().toString()+" "+Integer.parseInt(String.valueOf(hora.getText()))+" "+idCita);
                if(ps.executeUpdate()>0){//Si se modifico el registro, es se c=modifico correctamente
                    modificar=true;
                }
            }else{
                Toast.makeText(getApplicationContext(), "Ya existe una cita en el mismo consultorio a esa hora", Toast.LENGTH_SHORT).show();
            }//Fin del si encuentra mas citas iguales

            conn.close();
        }//Fin de validacion de conexion diferente de nulo
        else{
            Toast.makeText(getApplicationContext(), "No se ha podido conectar con la base de datos", Toast.LENGTH_SHORT).show();
        }
        return modificar;
    }//Fin de la funcion de modificar

    //TODO terminar validacion
    private boolean validarFechas(String fecha){
        boolean coincide=true;
        /**
         * Las siguientes lineas de codigo compararan la fecha que se quiere guardar antes de hacer el procedimiento
         * y evitar que se guarden fechas antes al dia que transcurre
         */
        int anoF=Integer.parseInt(fecha.substring(0,4));
        int mesF=Integer.parseInt(fecha.substring(5,7));
        int diaF=Integer.parseInt(fecha.substring(8,10));
        final Calendar c= Calendar.getInstance();
        dia=c.get(Calendar.DAY_OF_MONTH);
        mes=c.get(Calendar.MONTH)+1;
        ano=c.get(Calendar.YEAR);
        System.out.println(diaF+" "+dia);
        if(dia>diaF){
            coincide=false;
        }System.out.println(coincide);
        if(mes>mesF){
            coincide=false;
        }System.out.println(coincide);
        if(ano>anoF){
            coincide=false;
        }
        System.out.println(coincide);
        return coincide;
    }//Fin de validar fechas

    private boolean validarDiaDeSesion(int idCita) throws SQLException {
        boolean sesion=false;
        /**
         * Este codigo valida la fecha del dispositivo con la fecha que esta asignada en la base de datos
         * para llevar a cabo la sesion qque se guardara en el expediente
         */
        final Calendar c=Calendar.getInstance();
        //Se crean variables locales
        int dia=c.get(Calendar.DAY_OF_MONTH);
        int mes=c.get(Calendar.MONTH)+1;
        int ano=c.get(Calendar.YEAR);
        String diaS=String.valueOf(dia);
        String mesS=String.valueOf(mes);
        if(dia<10){
            diaS="0"+diaS;
        }
        if(mes<10){
            mesS="0"+mes;
        }
        String fechaDisp=String.valueOf(ano+"-"+mesS+"-"+diaS);
        Connection conn=conexionMySQL.CONN();
        if(conn!=null){
            PreparedStatement ps=conn.prepareCall("select Fecha_Hora from cita where ID_Cita=?");
            ps.setInt(1,idCita);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                String fechaBD=rs.getString("Fecha_Hora");
                System.out.println(fechaBD);
                System.out.println(fechaDisp);
                System.out.println(fechaBD.substring(0,10));
                if(fechaDisp.equals(fechaBD.substring(0,10))){
                    sesion=true;
                }
            }
            conn.close();
        }
        return sesion;
    }
}//Aqui termina la clase
