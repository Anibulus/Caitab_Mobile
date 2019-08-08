package com.example.caitamobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DatosGenerales extends AppCompatActivity {

    Button btnVolverMenuExp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_generales);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        btnVolverMenuExp = (Button) findViewById(R.id.btnVolverMenuExp);

        btnVolverMenuExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuexp = new Intent(DatosGenerales.this, Paciente.class);
                startActivity(menuexp);
            }
        });
    }
}


