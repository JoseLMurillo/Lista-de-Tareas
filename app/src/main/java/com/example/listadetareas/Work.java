package com.example.listadetareas;

public class Work {

    private int idWork;
    private String nombre, fecha, hora, desc, impor;

    public Work(){}

    public Work(int id, String nom, String fec, String ho, String des, String imp){
        this.idWork = id;
        this.nombre = nom;
        this.fecha = fec;
        this.desc = des;
        this.impor = imp;
        this.hora = ho;
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

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImpor() {
        return impor;
    }

    public void setImpor(String impor) {
        this.impor = impor;
    }
}
