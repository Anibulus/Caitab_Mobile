package com.example.caitamobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {
        EditText etUsuarioEmp;
        EditText etPassEmp;
        Button btnIngresar;
        Button btnCerrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsuarioEmp = (EditText) findViewById(R.id.etUsuarioEmp);
        etPassEmp = (EditText) findViewById(R.id.etPassEmp);
        btnIngresar = (Button) findViewById(R.id.btnIngresar);
        btnCerrar = (Button) findViewById(R.id.btnCerrar);




        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menu = new Intent(MainActivity.this, Menu.class);
                startActivity(menu);

            }
        });

        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });




    }
}
