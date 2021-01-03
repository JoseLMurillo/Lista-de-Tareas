package com.example.listadetareas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class NewWorksActivity extends AppCompatActivity {

    //VARIABLES
    DataBaseWorks dataBaseWorks;
    ArrayList<String> Tarea, Fecha, Descripcion;
    EditText etTarea, etDia, etMes, etAnio, etDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_works);

        //CASTEO
        dataBaseWorks = new DataBaseWorks(this);
        Tarea = new ArrayList<String>();
        Fecha = new ArrayList<String>();
        Descripcion = new ArrayList<String>();

        //RELACION PARTE GRAFICA
        etTarea = findViewById(R.id.etTarea);
        etDia = findViewById(R.id.etDia);
        etMes = findViewById(R.id.etMes);
        etAnio = findViewById(R.id.etAnio);
        etDescripcion = findViewById(R.id.etDescripcion);

    }

    //METODO GUARDAR DATOS EN LOS ARRAY
    public void GuardarDatos(View view){
        String tarea, fecha, descripcion;

        tarea = etTarea.getText().toString();
        fecha = etDia.getText().toString()+"/"+etMes.getText().toString()+"/"+etAnio.getText().toString();
        descripcion = etDescripcion.getText().toString();

        dataBaseWorks.GuardarDatos(tarea,fecha,descripcion);

        Toast.makeText(this, "La tarea se ha agregado correctamente", Toast.LENGTH_LONG).show();
        ViewAllWorks();
    }

    //METODO PARA IR ALLWORKSACTIVITY
    public void ViewAllWorks(){
        Intent intent = new Intent(this, AllWorksActivity.class);
        startActivity(intent);
        finish();
    }

    //METODO DE REGRESAR
    public void Back(View view){
        onBackPressed();
    }
}