package com.example.caitamobile;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    String ip="192.168.43.125:1433";  /*LA CONEXION SIEMPRE SE PIDE DE MANERA REMOTA*/ //LA IP SE ASIGNA A LA QUE LA RED SE ASIGNA CON EL SERVIDOR
    String classs="net.sourceforge.jtds.jdbc.Driver";
    String dbname="Caitab";
    String usuario="sa";
    String password="pepito17";

    public Connection CONN(){
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn=null;
        String ConnURL=null;
        try{
            Class.forName(classs);
            ConnURL="jdbc:jtds:sqlserver://"+ip+";"+"databaseName="+dbname+";user="+usuario+";password="+password;
            conn = DriverManager.getConnection(ConnURL);
        }catch(SQLException se)
        {
            conn=null;
            Log.e("ERROR",se.getMessage());
        }catch (ClassNotFoundException e)
        {
            Log.e("ERROR",e.getMessage());
        }catch (Exception e)
        {
            Log.e("ERROR",e.getMessage());
        }
        return conn;
    }
}
