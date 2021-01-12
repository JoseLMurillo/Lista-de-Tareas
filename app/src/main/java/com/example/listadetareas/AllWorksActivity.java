package com.example.listadetareas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.listadetareas.Notificacion.AlarmaPrueba;
import com.example.listadetareas.Notificacion.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AllWorksActivity extends AppCompatActivity {

    //VARIABLES
    ListView ListWorks;
    ArrayAdapter<String> adapter;
    ArrayList<String> nombre_Tareas, fecha_Tareas;
    DataBaseWorks dataBaseWorks;
    ArrayList<Work> tareasBD;
    Map<String, Integer> mapaTarea, mapaTarea2;
    Map<Integer, String>mapaHora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_works);

        //CASTEO
        dataBaseWorks = new DataBaseWorks(this);
        nombre_Tareas = new ArrayList<String>();
        fecha_Tareas = new ArrayList<String>();
        mapaTarea = new HashMap<String, Integer>();
        mapaTarea2 = new HashMap<String, Integer>();
        mapaHora = new HashMap<Integer, String>();

        tareasBD = new ArrayList<Work>();

        tareasBD = OBTENDATOS();

        //LLAMADA A LOS METODOS DE LLENADO
        LLENARARREGLO();
        LLENARMAPA();

        ///////
        LLENARARREGLO2();
        LLENARMAPA2();
        LLENARHORA();
        ///////


        //CASTEO
        ListWorks = findViewById(R.id.ListWorks);

        //CUANDO SE TOCA UN ITEM DE LA LISTA
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

    //OBTIENE LOS DATOS DE LA BD
    public ArrayList<Work> OBTENDATOS(){
        Cursor Datos = dataBaseWorks.GetTareas();
        ArrayList<Work> tareas = new ArrayList<Work>();

        //VARIABLES
        int id;
        String name, date, desc, impor, hor;

        while (Datos.moveToNext()){
            id = Datos.getInt(Datos.getColumnIndex("id_tarea"));
            name = Datos.getString(Datos.getColumnIndex("nombre"));
            date = Datos.getString(Datos.getColumnIndex("fecha"));
            hor = Datos.getString(Datos.getColumnIndex("hora"));
            desc = Datos.getString(Datos.getColumnIndex("descripcion"));
            impor = Datos.getString(Datos.getColumnIndex("importancia"));

            Work work = new Work(id, name, date, hor, desc, impor);
            tareas.add(work);
        }
        return tareas;
    }

    //LLENA UN ARREGLO PARA
    public void LLENARARREGLO(){
        for(int i=0; i<tareasBD.size(); i++){
            nombre_Tareas.add(tareasBD.get(i).getNombre());
        }
    }

    //LLENA UN MAPA CON EL NOMBRE DE LA TAREA Y SU ID PARA ENVIARLO A WORKS ACTIVITY
    public void LLENARMAPA(){
        String llave;
        int valor;

        for(int i=0; i<tareasBD.size(); i++){
            llave = tareasBD.get(i).getNombre();
            valor = tareasBD.get(i).getIdWork();
            mapaTarea.put(llave, valor);
        }
    }

    //LLENA UN ARREGLO PARA
    public void LLENARARREGLO2(){
        for(int i=0; i<tareasBD.size(); i++){
            fecha_Tareas.add(tareasBD.get(i).getFecha());
        }
    }

    //LLENA UN MAPA CON EL NOMBRE DE LA TAREA Y SU ID PARA ENVIARLO A WORKS ACTIVITY
    public void LLENARMAPA2(){
        String llave;
        int valor;

        for(int i=0; i<tareasBD.size(); i++){
            llave = tareasBD.get(i).getFecha();
            valor = tareasBD.get(i).getIdWork();
            mapaTarea2.put(llave, valor);
        }
    }

    //LLENA UN MAPA CON EL NOMBRE DE LA TAREA Y SU ID PARA ENVIARLO A WORKS ACTIVITY
    public void LLENARHORA(){
        int llave;
        String valor;

        for(int i=0; i<tareasBD.size(); i++){
            valor = tareasBD.get(i).getHora();
            llave = tareasBD.get(i).getIdWork();
            mapaHora.put(llave, valor);
        }
    }

    //PARA LA ACTIVIDAD NUEVA TAREA
    public void NEWORKACTIVITY(View view){
        Intent intent = new Intent(this, NewWorksActivity.class);
        startActivity(intent);
        finish();
    }

    //REGRESAR
    public void BACK(View view){
        onBackPressed();
    }
}