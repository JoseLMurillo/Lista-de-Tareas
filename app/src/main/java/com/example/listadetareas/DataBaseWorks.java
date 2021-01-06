package com.example.listadetareas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseWorks extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Works.db";

    public DataBaseWorks(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Agregar codigo SQL para crwd (crear, leer, escribir, eliminar)

        //CREA UNA TABLA DE LA BASE DE DATOS
        db.execSQL("CREATE TABLE Tarea ("+
                "id_tarea INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "nombre VARCHAR NOT NULL, "+
                "fecha VARCHAR NOT NULL, "+
                "hora VARCHAR NOT NULL, "+
                "descripcion VARCHAR NOT NULL, " +
                "importancia VARCHAR NOT NULL);");

        /*db.execSQL("CREATE TABLE Realizadas ("+
                "id_tarea INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "nombre VARCHAR NOT NULL, "+
                "fecha VARCHAR NOT NULL, "+
                "hora VARCHAR NOT NULL, "+
                "descripcion VARCHAR NOT NULL, " +
                "importancia VARCHAR NOT NULL);");

        db.execSQL("CREATE TABLE Pendientes ("+
                "id_tarea INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "nombre VARCHAR NOT NULL, "+
                "fecha VARCHAR NOT NULL, "+
                "hora VARCHAR NOT NULL, "+
                "descripcion VARCHAR NOT NULL, " +
                "importancia VARCHAR NOT NULL);");

        db.execSQL("CREATE TABLE Todos ("+
                "id_tarea INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "nombre VARCHAR NOT NULL, "+
                "fecha VARCHAR NOT NULL, "+
                "hora VARCHAR NOT NULL, "+
                "descripcion VARCHAR NOT NULL, " +
                "importancia VARCHAR NOT NULL);");*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Actualizar la estructura del base de datos o el modelo de datos de la base.

    }

    //METODO PARA GUARDAR LOS DATOS
    public void GuardarDatos(String Work, String date, String hora, String desc, String impor){
        getReadableDatabase().execSQL("INSERT INTO Tarea VALUES ("+null+",'"+Work+"','"+date+"','"+hora+"','"+desc+"','"+impor+"');");
    }

    //METODO PARA ACTUALIZAR LOS DATOS
    public void UpdateWork(String id, String nombre, String fecha, String hora, String descripcion, String importancia){
        getReadableDatabase().execSQL("UPDATE Tarea SET nombre = '"+nombre+"', fecha = '"+fecha+"', hora = '"+hora+"', descripcion = '"+descripcion+"', importancia = '"+importancia+"' WHERE id_tarea = "+id+";");
    }

    //METODO PARA ELIMIRA UN DATO
    public void DeleteWork(String id){
        getReadableDatabase().execSQL("DELETE FROM Tarea WHERE id_tarea = "+id+";");
    }

    public Cursor GetTareas(){
        return getReadableDatabase().query("Tarea", null, null, null, null, null, null);
    }

    public Cursor GetWorkById(String id){
        return getReadableDatabase().rawQuery("SELECT *" +
                "FROM Tarea " +
                "WHERE id_tarea = " + id + ";", null);
    }
}
