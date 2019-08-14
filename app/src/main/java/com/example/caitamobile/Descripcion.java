package com.example.caitamobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.caitamobile.Constantes.IntentExtras;
import com.example.caitamobile.modelo.Cita;
import com.example.caitamobile.modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class Descripcion extends AppCompatActivity {
    private Button btnGuardarSesion;
    private EditText txtDiagnostico, txtDescripcion,txtConclusion;
    private Conexion conexionMySQL;
    private Usuario usuario;
    private Cita cita;
    private int idCita;
    private int idCliente;
    private Calendar c;
    private String horaIni, horaFin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion);
        /**
         * Aqui se enlazan los componentes
         */
        btnGuardarSesion = (Button) findViewById(R.id.btnGuardar);
        txtDiagnostico = (EditText) findViewById(R.id.txtDiagnostico);
        txtDescripcion = (EditText) findViewById(R.id.etDescripcionSesion);
        txtConclusion = (EditText) findViewById(R.id.txtConclusion);
        conexionMySQL = new Conexion();
        //TODO - Poner los llave de usuario y metodo expulsar con el
        Intent inte=getIntent();
        usuario=inte.getParcelableExtra(IntentExtras.USUARIO.llave);
        Bundle b=getIntent().getExtras();
        idCita = b.getInt("idCita");
        idCliente = b.getInt("idCliente");
        if(b.getString("anterior").equals("Cita")) {
            /**
             * Este codigo se ejecuta en el caso de que provengan de datosCita
             */

            c = Calendar.getInstance();
            horaIni = String.valueOf(c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND));
            System.out.println("Aqui estoy " + horaIni);
            /**
             * Acciones
             */
            btnGuardarSesion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /**
                     * Al momento de presionar el boton terminaste la sesion y se toma como hora fin
                     */
                    c = Calendar.getInstance();
                    horaFin = String.valueOf(c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND));
                    try {
                        if (guardarSesion()) {
                            Toast.makeText(getApplicationContext(), "Sesion guardada correctamente", Toast.LENGTH_SHORT).show();
                            //Intent regreso = new Intent(Descripcion.this, Agenda.class);
                            //regreso.putExtra(IntentExtras.USUARIO.llave, usuario);
                            //startActivity(regreso);
                        } else {
                            Toast.makeText(getApplicationContext(), "No se ha podido guardar la sesion", Toast.LENGTH_SHORT).show();
                        }
                    } catch (SQLException e) {
                        Toast.makeText(getApplicationContext(), "Ocurrio un error mientras se guardaba", Toast.LENGTH_SHORT).show();
                    }//Fin de try-catch
                }
            });//Fin del metodo del boton
        }else{
            /**
             * Surgen modificaciones para que la ventana sea diferente
             */
            try {
                llenarCampos();
            } catch (SQLException e) {
                Toast.makeText(getApplicationContext(), "Ocurrio un error al consultar la informacion", Toast.LENGTH_SHORT).show();
            }
            btnGuardarSesion.setText("Modificar");
            btnGuardarSesion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!txtDiagnostico.getText().toString().equals("")&&!txtDescripcion.getText().toString().equals("")){
                        //TODO corregir como se envian los datos porque se envian bien pero las nonvbres de variables pueden no entenderse
                        try {
                            if(guardar(txtDescripcion.getText().toString(),txtDiagnostico.getText().toString())){
                                Toast.makeText(getApplicationContext(), "Se ha modificado correctamente", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(), "No ha sido posible guardar", Toast.LENGTH_SHORT).show();
                            }
                        } catch (SQLException e) {
                            Toast.makeText(getApplicationContext(), "Ocurrio un error al modificar", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "No puedes dejar vacias ningunpo de los dos campos", Toast.LENGTH_SHORT).show();
                    }//if-else para validar que no se envien campos vacios
                }
            });//Fin de la accion del boton

        }//Fin del IF-ELSE que busca lo que hay que hacer dependiendo de donde se proviene
    }//Fin del metodo onCreate

    private boolean guardarSesion() throws SQLException {
        boolean guardar=false;
        Connection conn=conexionMySQL.CONN();
        if(conn!=null){
            String query="insert into expediente (ID_Emp,ID_Cli,ID_Cita,Hora_Inicio, Hora_Fin,Descripcion,Conclusion) values (?,?,?,?,?,?,?);";
            PreparedStatement ps=conn.prepareCall(query);
            ps.setInt(1,conexionMySQL.getEmpleadoActivo());
            ps.setInt(2,idCliente);
            ps.setInt(3,idCita);
            ps.setString(4,horaIni);
            ps.setString(5,horaFin);
            ps.setString(6,txtDescripcion.getText().toString());
            ps.setString(7,txtDiagnostico.getText().toString());
            if(ps.executeUpdate()>0){
                guardar=true;
            }
            conn.close();
        }else{
            Toast.makeText(getApplicationContext(), "No se pudo conectar a la base de datos", Toast.LENGTH_SHORT).show();
        }
        return guardar;
    }//Fin de metodo guardar sesion conectada a la base de datos
    /**
     * A partir de aqui son las funciones para llenar los campos y el metodo de update
     */
    private void llenarCampos() throws SQLException {
        Connection conn=conexionMySQL.CONN();
        if(conn!=null){
            String query="select * from expediente where ID_Cita=?";
            PreparedStatement ps=conn.prepareCall(query);
            ps.setInt(1,idCita);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                txtDiagnostico.setText(rs.getString("Conclusion"));
                txtDescripcion.setText(rs.getString("Descripcion"));
            }
            conn.close();
        }//Fin de si la conexion es nula
    }//Fin del metodo que llena los cmapos

    private boolean guardar(String diagnostico, String conclusion) throws SQLException {
        boolean guardar=false;
        Connection conn=conexionMySQL.CONN();
        if(conn!=null){
            String query="update expediente set Descripcion=?, Conclusion=? where ID_Cita=?";
            PreparedStatement ps=conn.prepareCall(query);
            ps.setString(1,diagnostico);
            ps.setString(2,conclusion);
            ps.setInt(3,idCita);
            if(ps.executeUpdate()>0){
                guardar=true;
            }
            conn.close();
        }//Fin de si la conexion es nula
        return guardar;
    }
}//Fin de la clase
