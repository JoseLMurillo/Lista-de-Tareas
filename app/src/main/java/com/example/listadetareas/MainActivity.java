package com.example.listadetareas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.listadetareas.Notificacion.AlarmaPrueba;

import com.example.listadetareas.Notificacion.TareasNotificacion;

public class MainActivity extends AppCompatActivity {


    TareasNotificacion tareasNotificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService(new Intent(this, TareasNotificacion.class));

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

    //BOTON ALARMA PRUEBA
    public void ALARMA(View view){
        Intent intent = new Intent(this, Pomodoro.class);
        startActivity(intent);
    }
}
