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

    public Descripcion() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion);
        //TODO - Traer los datos con un bundle (IDCita, IDCliente) (Empleado activo esta en conexion)
        //TODO - Poner los llave de usuario y metodo expulsar con el
        Intent inte=getIntent();
        usuario=inte.getParcelableExtra(IntentExtras.USUARIO.llave);
        Bundle b=getIntent().getExtras();
        idCita=b.getInt("idCita");
        idCliente=b.getInt("idCliente");
        /**
         * Aqui se enlazan los componentes
         */
        btnGuardarSesion=(Button)findViewById(R.id.btnGuardar);
        txtDiagnostico=(EditText)findViewById(R.id.txtDiagnostico);
        txtDescripcion=(EditText)findViewById(R.id.etDescripcionSesion);
        txtConclusion=(EditText)findViewById(R.id.txtConclusion);
        conexionMySQL=new Conexion();
        txtDiagnostico.setInputType(InputType.TYPE_NULL);//Se inabilita el teclado para que la persona no pueda cambiarlo
        c=Calendar.getInstance();
        horaIni=String.valueOf(c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND));
        System.out.println("Aqui estoy "+horaIni);
        /**
         * Acciones
         */
        btnGuardarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * Al momento de presionar el boton terminaste la sesion y se toma como hora fin
                 */
                c=Calendar.getInstance();
                horaFin=String.valueOf(c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND));
                try {
                    if(guardarSesion())
                    {
                        Toast.makeText(getApplicationContext(), "Sesion guardada correctamente", Toast.LENGTH_SHORT).show();
                        Intent regreso=new Intent(Descripcion.this,Agenda.class);
                        regreso.putExtra(IntentExtras.USUARIO.llave, usuario);
                        startActivity(regreso);
                    }else{
                        Toast.makeText(getApplicationContext(), "No se ha podido guardar la sesion", Toast.LENGTH_SHORT).show();
                    }
                } catch (SQLException e) {
                    Toast.makeText(getApplicationContext(), "Ocurrio un error mientras se guardaba", Toast.LENGTH_SHORT).show();
                }//Fin de try-catch
            }
        });//Fin del metodo del boton
    }//Fin del metodo onCreate

    private boolean guardarSesion() throws SQLException {
        boolean guardar=false;
        Connection conn=conexionMySQL.CONN();
        if(conn!=null){
            //TODO agregar campor de hora inicio y hora fin
            String query="insert into expediente (ID_Emp,ID_Cli,ID_Cita,Hora_Inicio, Hora_Fin,Descripcion,Conclusion) values (?,?,?,?,?,?,?);";
            PreparedStatement ps=conn.prepareCall(query);
            ps.setInt(1,conexionMySQL.getEmpleadoActivo());
            ps.setInt(2,idCliente);
            ps.setInt(3,idCita);
            ps.setString(4,horaIni);
            ps.setString(5,horaFin);
            ps.setString(6,txtDescripcion.getText().toString());
            ps.setString(7,txtConclusion.getText().toString());
            if(ps.executeUpdate()>0){
                guardar=true;
            }
            conn.close();
        }else{
            Toast.makeText(getApplicationContext(), "No se pudo conectar a la base de datos", Toast.LENGTH_SHORT).show();
        }
        return guardar;
    }//Fin de metodo guardar sesion conectada a la base de datos

}//Fin de la clase
