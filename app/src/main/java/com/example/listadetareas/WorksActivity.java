package com.example.listadetareas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class WorksActivity extends AppCompatActivity {

    //VARIABLES
    TextView tvNombre, tvFecha, tvDesc;
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
        tvDesc = findViewById(R.id.tvDesc);

        id = getIntent().getExtras().getString("ID_TAREA");
        Toast.makeText(this, id, Toast.LENGTH_LONG).show();

        ObtenDatosyMuestra();
    }

    //METODO OBTENER DATOS DE UNA TAREA Y ENVIARLOS AL LADO GRAFICO
    public void ObtenDatosyMuestra(){
        Cursor Datos = dataBaseWorks.GetWorkById(id);

        int id;
        String name, date, desc;

        while (Datos.moveToNext()){
            id = Datos.getInt(Datos.getColumnIndex("id_tarea"));
            name = Datos.getString(Datos.getColumnIndex("nombre"));
            date = Datos.getString(Datos.getColumnIndex("fecha"));
            desc = Datos.getString(Datos.getColumnIndex("descripcion"));

            tvNombre.setText(name);
            tvFecha.setText(date);
            tvDesc.setText(desc);
        }
    }

    //METODO PARA EL FAB
    public void EditWork(View view){
        Intent intent = new Intent(this, EditWork.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }

    //METODO DE REGRESAR
    public void Back(View view){
        onBackPressed();
    }
}