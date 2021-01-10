package com.example.listadetareas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditWork extends AppCompatActivity {

    //VARIABLES
    TextView tvNombre;
    EditText nombre, etFecha, etHora, descrip;
    DataBaseWorks dataBaseWorks;
    String id;

    Spinner SpImportancia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_work);

        //CASTEO
        dataBaseWorks = new DataBaseWorks(this);

        //RELACION PARTE GRAFICA
        tvNombre = (TextView) findViewById(R.id.tvNombre);
        nombre = (EditText) findViewById(R.id.etTarea);
        etFecha = (EditText) findViewById(R.id.etFecha);
        etHora = (EditText) findViewById(R.id.etHora);
        descrip = (EditText) findViewById(R.id.etDescripcion);

        //PRUEBA
        SpImportancia = (Spinner) findViewById(R.id.SpImportancia);

        //RECIBE EL ID DEL INTENT
        id = getIntent().getExtras().getString("ID");

        ObtenDatosyMuestra();
    }

    //LLENA LOS CAMPOS CON LA INFORMACION DE LA BASE DE DATOS
    public void ObtenDatosyMuestra(){
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
            nombre.setText(name);
            etFecha.setText(date);
            etHora.setText(hora);
            descrip.setText(desc);

            //PRUEBA
            if(impor.equals("NORMAL")){
                SpImportancia.setSelection(0);
            }
            if(impor.equals("IMPORTANTE")){
                SpImportancia.setSelection(1);
            }
            if(impor.equals("NINGUNA")){
                SpImportancia.setSelection(2);
            }
        }
    }

    //METODO PARA ACTUALIZAR EN BD
    public void ACTUALIZAR(View view){
        String Fech = etFecha.getText().toString(); String hora = etHora.getText().toString();
        dataBaseWorks.UpdateWork(id, nombre.getText().toString(), Fech, hora, descrip.getText().toString(), SpImportancia.getSelectedItem().toString());
        Toast.makeText(this, "Tarea Actualizada correctamente", Toast.LENGTH_LONG).show();
        CLEARALL();
    }

    //METODO PARA ELIMINAR DATO DE LA BD
    public void ELIMINAR(View view){
        dataBaseWorks.DeleteWork(id);
        Toast.makeText(this, "Tarea Eliminada correctamente", Toast.LENGTH_LONG).show();
        CLEARALL();
    }

    //FINALIZAR PILA DE ACTIVIDADES Y VOLVER AL MAIN
    public void CLEARALL(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }

    //METODO DE REGRESAR
    public void BACK(View view){
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
                etHora.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };

        new TimePickerDialog(EditWork.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();



        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                //DIA Y MES CON CERO
                if (dayOfMonth <= 9 && month <= 9) {
                    etFecha.setText("0" + dayOfMonth + "/0" + month + "/" + year);
                }
                if (dayOfMonth <= 9 && month > 9) {
                    etFecha.setText("0" + dayOfMonth + "/" + month + "/" + year);
                }
                if (dayOfMonth > 9 && month <= 9) {
                    etFecha.setText(dayOfMonth + "/0" + month + "/" + year);
                }
                if (dayOfMonth > 9 && month > 9) {
                    etFecha.setText(dayOfMonth + "/" + month + "/" + year);
                }
            }
        };

        new DatePickerDialog(EditWork.this, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}