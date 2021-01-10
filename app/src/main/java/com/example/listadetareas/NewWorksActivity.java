package com.example.listadetareas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class NewWorksActivity extends AppCompatActivity {

    //VARIABLES
    DataBaseWorks dataBaseWorks;
    ArrayList<String> Tarea, Fecha, Descripcion;
    EditText etTarea, etFecha, etMes, etDescripcion;
    Spinner SpImportancia;


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
        etFecha = findViewById(R.id.etFecha);
        etMes = findViewById(R.id.etHora);
        etDescripcion = findViewById(R.id.etDescripcion);
        SpImportancia = (Spinner) findViewById(R.id.SpImportancia); //Que la base de datos se actualice segun el orden de importancia


        //PRUEBA
    }

    //METODO GUARDAR DATOS EN LOS ARRAY
    public void GUARDARDATOS(View view) {
        String tarea, fecha, hora, descripcion, importancia;

        tarea = etTarea.getText().toString();
        fecha = etFecha.getText().toString();
        hora = etMes.getText().toString();
        descripcion = etDescripcion.getText().toString();
        importancia = SpImportancia.getSelectedItem().toString();

        dataBaseWorks.GuardarDatos(tarea, fecha, hora, descripcion, importancia);

        Toast.makeText(this, "La tarea se ha agregado correctamente", Toast.LENGTH_LONG).show();
        VIEWALLWORKS();
    }

    //METODO PARA IR ALLWORKSACTIVITY
    public void VIEWALLWORKS() {
        Intent intent = new Intent(this, AllWorksActivity.class);
        startActivity(intent);
        finish();
    }

    //METODO DE REGRESAR
    public void BACK(View view) {
        onBackPressed();
    }

    //METODO MUESTRA UN DATEPIKER DE FECHA
    public void DATEPICKERFECHAHORA(View view) {
        final Calendar calendar = Calendar.getInstance();

        TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                calendar.set(Calendar.MINUTE,minute);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
                etMes.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };

        new TimePickerDialog(NewWorksActivity.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();



        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                //DIA Y MES CON CERO
                if (dayOfMonth < 9 && month < 9) {
                    etFecha.setText("0" + dayOfMonth + "/0" + month + "/" + year);
                }
                if (dayOfMonth < 9 && month > 9) {
                    etFecha.setText("0" + dayOfMonth + "/" + month + "/" + year);
                }
                if (dayOfMonth > 9 && month < 9) {
                    etFecha.setText(dayOfMonth + "/0" + month + "/" + year);
                }
                if (dayOfMonth > 9 && month > 9) {
                    etFecha.setText(dayOfMonth + "/" + month + "/" + year);
                }
            }
        };

        new DatePickerDialog(NewWorksActivity.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}