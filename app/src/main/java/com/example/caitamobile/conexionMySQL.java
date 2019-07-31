package com.example.caitamobile;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexionMySQL {
        String ip = "192.168.100.151";//La ip cambia
        String db = "caitab";
        String un = "root";
        String password = "";
        String url = "jdbc:mysql://" + ip + "/" + db;

        @SuppressLint("NewApi")
        public Connection CONN(){
            //Politicas o reglas de como se debe de conectar
            //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Build();
            //StrictMode.setTreadPolicy(policy);
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Connection conn=null;
            String ConnURL=null;
            try{
                Class.forName("con.mysql.jdbc.Driver").newInstance();
                conn= DriverManager.getConnection(url,un,password);
            }catch(SQLException se){
                Log.e("Error", se.getMessage());
            }catch(ClassNotFoundException e){
                Log.e("Error", e.getMessage());
            }catch(Exception e){
                Log.e("Error", e.getMessage());
            }
            return conn;
        }//Fin del metodo conexion
}//fin de la clase conexion