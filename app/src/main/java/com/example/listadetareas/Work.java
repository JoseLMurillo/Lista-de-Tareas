package com.example.listadetareas;

public class Work {

    private int idWork;
    private String nombre,  fecha, desc;

    public Work(){}

    public Work(int id, String nom, String fec, String des){
        this.idWork = id;
        this.nombre = nom;
        this.fecha = fec;
        this.desc = des;
    }

    public int getIdWork() {
        return idWork;
    }

    public void setIdWork(int idWork) {
        this.idWork = idWork;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
