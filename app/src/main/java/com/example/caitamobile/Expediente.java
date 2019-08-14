package com.example.caitamobile;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.caitamobile.Constantes.IntentExtras;
import com.example.caitamobile.modelo.Cita;
import com.example.caitamobile.modelo.Paciente;
import com.example.caitamobile.modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Expediente extends AppCompatActivity {

    private Usuario usuario;
    private Cita cita;
    private String desde;
    private Paciente paciente;
    private TextView nombre, apellido;
    private ListView expediente;
    private Conexion conexionMySQL;
    private ArrayList<Cita> llenado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expediente);
        /**
         * Aqui se obtienen datos del activity pasado
         */
        Intent intent = getIntent();
        usuario = intent.getParcelableExtra(IntentExtras.USUARIO.llave);
        paciente = intent.getParcelableExtra(IntentExtras.PACIENTE.llave);
        cita = intent.getParcelableExtra(IntentExtras.CITA.llave);
        desde = intent.getStringExtra(IntentExtras.DESDE.llave);
        /**
         * Aqui se enlazan los componentes
         */
        nombre=(TextView)findViewById(R.id.tvNombrePaciente2);
        apellido=(TextView)findViewById(R.id.tvNombrePaciente);
        expediente=(ListView)findViewById(R.id.lbSesiones);
        nombre.setText(paciente.getNombre());
        apellido.setText(paciente.getApellidos());
        conexionMySQL=new Conexion();
        System.out.println("id==="+paciente.getId_paciente());
        llenarExpediente();

        if(usuario == null)
            expulsar();

        expediente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent act=new Intent(Expediente.this,Descripcion.class);
                act.putExtra("anterior","Expediente");
                act.putExtra("idCita",llenado.get(i).getIdCita());
                act.putExtra("idCliente",paciente.getId_paciente());
                startActivity(act);
            }
        });//Fin del listView
    }//Fin de onCreate

    private void expulsar() {
        usuario = null;
        Intent intent = new Intent(Expediente.this, MainActivity.class);
        startActivity(intent);
    }//Fin de expulsar

    private ArrayList<Cita> consultarExpediente() throws SQLException {
        ArrayList<Cita> citas= new ArrayList<>();
        Connection conn=conexionMySQL.CONN();
        if(conn!=null){
            System.out.println("llego aqui");
            String query="SELECT c.ID_Cita,c.ID_Cli,cli.Nombre_C,cli.Apellidos_C,cli.Tel_C,cli.Email_C,c.Fecha_Hora,c.Consultorio \n" +
                    "FROM cita c JOIN cliente cli  ON c.ID_Cli = cli.ID_Cli \n" +
                    "WHERE c.ID_Cita IN (SELECT ID_Cita FROM expediente) AND c.ID_Cli=? AND c.ID_Emp =? \n" +
                    "and c.Fecha_Hora  ORDER BY c.Fecha_Hora DESC";
            PreparedStatement ps=conn.prepareCall(query);
            ps.setInt(1, paciente.getId_paciente());
            ps.setInt(2,conexionMySQL.getEmpleadoActivo());
            //ps.setInt(2,paciente.getID));
            ResultSet rs=ps.executeQuery();
            while (rs.next()){
                System.out.println("llego aqui");
                Cita c=new Cita(rs.getInt("ID_Cita"),rs.getInt("ID_Cli"),rs.getString("Nombre_C"), rs.getString("Apellidos_C"), rs.getString("Tel_C"), rs.getString("Email_C"), rs.getString("Fecha_Hora"), rs.getInt("Consultorio"));
                System.out.println(rs.getInt("ID_Cita"));
                citas.add(c);
            }
            conn.close();
        }//Fin del if conexion no
        return citas;
    }//Fin de consulta

    private void llenarExpediente(){
        try {System.out.println("llego aqui");
            llenado=consultarExpediente();
            ArrayList<String> bonito=new ArrayList<>();
            if(llenado.size()>0){
                for (int i=0;i<llenado.size();i++){
                    bonito.add(llenado.get(i).getDescripcion());
                }
                ArrayAdapter adaptador = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, bonito);
                expediente.setEnabled(true);
                expediente.setAdapter(adaptador);
            }else{
                bonito.add("No hay expedientes del paciente todavia");
                ArrayAdapter adaptador = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, bonito);
                expediente.setEnabled(false);
                expediente.setAdapter(adaptador);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//Fin del llenado
}//Fin de la clase

