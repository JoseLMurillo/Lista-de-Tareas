package com.example.listadetareas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    //BOTON TODAS LAS TAREAS
    public void AllWorks(View view){
        Intent intent = new Intent(this, AllWorksActivity.class);
        startActivity(intent);
    }

    //BOTON NUEVA TAREA
    public void NewWork(View view){
        Intent intent = new Intent(this, NewWorksActivity.class);
        startActivity(intent);
    }

}
