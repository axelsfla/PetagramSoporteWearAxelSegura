package com.coursera.sacbe.petagramaxelsegura.pojo;

import java.util.ArrayList;

/**
 * Created by Sacbe on 03/09/2016.
 */
public class Mascota {
    private String nombre;
    private String raza;
    private int raiting;
    private int foto;
    //private ArrayList<FotoMascota> fotosMascota;
    private int id;

    public Mascota(int foto, String nombre, String raza) {
        this.foto  = foto;
        this.nombre = nombre;
        this.raza = raza;
    }

    public Mascota() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public int getRaiting() {
        return raiting;
    }

    public void setRaiting(int raiting) {
        this.raiting = raiting;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    /*public ArrayList<FotoMascota> getFotosMascota() {
        return fotosMascota;
    }

    public void setFotosMascota(ArrayList<FotoMascota> fotosMascota) {
        this.fotosMascota = fotosMascota;
    }*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
