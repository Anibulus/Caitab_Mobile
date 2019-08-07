package com.example.caitamobile;
import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion{
    String ip = "192.168.15.8:3306";//La ip puede cambiar
    String db = "caitab";
    String un = "caitab";
    String password = "1234";
    String url = "jdbc:mysql://" + ip + "/" + db;

    @SuppressLint("NewApi")
    public Connection CONN(){
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();//Politicas o reglas de como se gdebe de conectar
        StrictMode.setThreadPolicy(policy);
        Connection conn=null;
        String ConnURL=null;
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn=DriverManager.getConnection(url,un,password);
        }catch(SQLException se){
            Log.e("Error", se.getMessage());
        }catch(ClassNotFoundException e){
            Log.e("Error", e.getMessage());
        }catch(Exception e){
            Log.e("Error", e.getMessage());
        }
        return conn;
    }//Fin del metodo conexion
}//Fin de la clase conexion

