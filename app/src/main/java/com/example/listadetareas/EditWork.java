package com.example.listadetareas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditWork extends AppCompatActivity {

    //VARIABLES
    TextView tvNombre;
    EditText nombre, dia, mes, anio, descrip;
    DataBaseWorks dataBaseWorks;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_work);

        //CASTEO
        dataBaseWorks = new DataBaseWorks(this);

        //RELACION PARTE GRAFICA
        tvNombre = (TextView) findViewById(R.id.tvNombre);
        nombre = (EditText) findViewById(R.id.etTarea);
        dia = (EditText) findViewById(R.id.etDia);
        mes = (EditText) findViewById(R.id.etMes);
        anio = (EditText) findViewById(R.id.etAnio);
        descrip = (EditText) findViewById(R.id.etDescripcion);

        //RECIBE EL ID DEL INTENT
        id = getIntent().getExtras().getString("ID");

        ObtenDatosyMuestra();
    }

    //LLENA LOS CAMPOS CON LA INFORMACION DE LA BASE DE DATOS
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
            nombre.setText(name);
            dia.setText(date.substring(0,2));
            mes.setText(date.substring(3,6));
            anio.setText(date.substring(7));
            descrip.setText(desc);
        }
    }

    //METODO PARA ACTUALIZAR EN BD
    public void Actualizar(View view){
        String Fech = dia.getText().toString()+"/"+mes.getText().toString()+"/"+anio.getText().toString();
        dataBaseWorks.UpdateWork(id, nombre.getText().toString(), Fech, descrip.getText().toString());
        ClearAll();
    }

    //METODO PARA ELIMINAR DATO DE LA BD
    public void Eliminar(View view){
        dataBaseWorks.DeleteWork(id);
        ClearAll();
    }

    //FINALIZAR PILA DE ACTIVIDADES Y VOLVER AL MAIN
    public void ClearAll(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }

    //METODO DE REGRESAR
    public void Back(View view){
        onBackPressed();
    }
}