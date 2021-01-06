package com.example.listadetareas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class WorksActivity extends AppCompatActivity {

    //VARIABLES
    TextView tvNombre, tvFecha, tvHora, tvDesc, tvImportancia;
    DataBaseWorks dataBaseWorks;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_works);

        //CASTEO
        dataBaseWorks = new DataBaseWorks(this);

        //RELACION PARTE GRAFICA
        tvNombre = findViewById(R.id.tvNombre);
        tvFecha = findViewById(R.id.tvFecha);
        tvHora = findViewById(R.id.tvHora);
        tvDesc = findViewById(R.id.tvDesc);
        tvImportancia = findViewById(R.id.tvImportancia);

        id = getIntent().getExtras().getString("ID_TAREA");

        OBTENDATOSYMUESTRA();
    }

    //METODO OBTENER DATOS DE UNA TAREA Y ENVIARLOS AL LADO GRAFICO
    public void OBTENDATOSYMUESTRA(){
        Cursor Datos = dataBaseWorks.GetWorkById(id);

        int id;
        String name, date, hora, desc, impor;

        while (Datos.moveToNext()){
            id = Datos.getInt(Datos.getColumnIndex("id_tarea"));
            name = Datos.getString(Datos.getColumnIndex("nombre"));
            date = Datos.getString(Datos.getColumnIndex("fecha"));
            hora = Datos.getString(Datos.getColumnIndex("hora"));
            desc = Datos.getString(Datos.getColumnIndex("descripcion"));
            impor = Datos.getString(Datos.getColumnIndex("importancia"));

            tvNombre.setText(name);
            tvFecha.setText(date);
            tvHora.setText(hora);
            tvDesc.setText(desc);
            tvImportancia.setText(impor);
        }
    }

    //METODO PARA EL FAB
    public void EDITWORK(View view){
        Intent intent = new Intent(this, EditWork.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }

    //METODO DE REGRESAR
    public void BACK(View view){
        onBackPressed();
    }
}