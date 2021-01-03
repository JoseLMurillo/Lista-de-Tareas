package com.example.listadetareas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AllWorksActivity extends AppCompatActivity {

    //VARIABLES
    ListView ListWorks;
    ArrayAdapter<String> adapter;
    ArrayList<String> nombre_Tareas;
    DataBaseWorks dataBaseWorks;
    ArrayList<Work> tareasBD;
    Map<String, Integer> mapaTarea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_works);

//CASTEO
        dataBaseWorks = new DataBaseWorks(this);
        nombre_Tareas = new ArrayList<String>();
        mapaTarea = new HashMap<String, Integer>();
        tareasBD = new ArrayList<Work>();

        tareasBD = ObtenDatos();

        //LLAMADA A LOS METODOS DE LLENADO
        LlenarArreglo();
        LlenarMapa();

        //CASTEO
        ListWorks = findViewById(R.id.ListWorks);

        ListWorks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nombre = adapter.getItem(position);

                Intent intent = new Intent(getApplicationContext(), WorksActivity.class);
                intent.putExtra("ID_TAREA", mapaTarea.get(nombre).toString());
                startActivity(intent);

            }
        });

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nombre_Tareas);
        ListWorks.setAdapter(adapter);

    }

    //METODO PARA OBTER LOS DATOS DE LA BD
    public ArrayList<Work> ObtenDatos(){
        Cursor Datos = dataBaseWorks.GetTareas();
        ArrayList<Work> tareas = new ArrayList<Work>();

        int id;
        String name, date, desc;

        while (Datos.moveToNext()){
            id = Datos.getInt(Datos.getColumnIndex("id_tarea"));
            name = Datos.getString(Datos.getColumnIndex("nombre"));
            date = Datos.getString(Datos.getColumnIndex("fecha"));
            desc = Datos.getString(Datos.getColumnIndex("descripcion"));

            Work work = new Work(id, name, date, desc);

            tareas.add(work);
        }
        return tareas;
    }

    //LLENA UN ARREGLO PARA
    public void LlenarArreglo(){
        for(int i=0; i<tareasBD.size(); i++){
            nombre_Tareas.add(tareasBD.get(i).getNombre());
        }
    }

    //LLENA UN MAPA CON EL NOMBRE DE LA TAREA Y SU ID PARA ENVIARLO A WORKS ACTIVITY
    public void LlenarMapa(){
        String llave;
        int valor;

        for(int i=0; i<tareasBD.size(); i++){
            llave = tareasBD.get(i).getNombre();
            valor = tareasBD.get(i).getIdWork();
            mapaTarea.put(llave, valor);
        }
    }

    //METODO DE NUEVA ACTIVIDAD
    public void NewActivity(View view){
        Intent intent = new Intent(this, NewWorksActivity.class);
        startActivity(intent);
        finish();
    }

    //METODO DE REGRESAR
    public void Back(View view){
        onBackPressed();
    }
}