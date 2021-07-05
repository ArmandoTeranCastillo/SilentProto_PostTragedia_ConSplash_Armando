package com.example.silentproto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ListaLugaresActivity extends AppCompatActivity {
FloatingActionButton fab;
    Lugares[] aLugares = {
            new Lugares("Casa","Casita del gerundio"),
            new Lugares("Escuela","Elvis tek "),
            new Lugares("Soriana","Un Soriana"),
            new Lugares("Fashion Mall","Joyería"),
            new Lugares("Cinépolis","AV Vallarta"),
            new Lugares("Dentista","Centro"),
            new Lugares("Clínica salud digna","aquí me internaron")
    };

    ListView lstVwLugares;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_lugares);
        fab=findViewById(R.id.fab);
    }

    @Override
    protected void onStart() {
        super.onStart();
        lstVwLugares = findViewById(R.id.lstVwLugares);
        lstVwLugares.setAdapter(new LugaresAdaptador(this, R.layout.mi_lista_lugares, aLugares));
    }

    @Override
    protected void onResume() {
        super.onResume();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentoMapa=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intentoMapa);
            }
        });
    }
}