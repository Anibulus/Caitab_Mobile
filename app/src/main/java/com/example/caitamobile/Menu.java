package com.example.caitamobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Menu extends AppCompatActivity {

    ImageButton btnAgenda;
    ImageButton btnPacientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnAgenda = (ImageButton)  findViewById(R.id.btnAgenda);
        btnPacientes = (ImageButton) findViewById(R.id.btnPacientes);

        btnAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent agenda = new Intent(Menu.this, Agenda.class);
                startActivity(agenda);

            }
        });
        btnPacientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}
