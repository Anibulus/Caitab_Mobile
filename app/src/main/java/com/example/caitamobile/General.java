package com.example.caitamobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class General extends AppCompatActivity {

    Button btnDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_cita);//Supuestamente esta es la que debe de quedar
        //setContentView(R.layout.activity_general);

        btnDesc = (Button)  findViewById(R.id.btnDesc);

        btnDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent desc = new Intent(General.this, Descripcion.class);
                startActivity(desc);
            }
        });
    }
}
