package com.coursera.sacbe.petagramaxelsegura.restApi.model;

import com.coursera.sacbe.petagramaxelsegura.pojo.FotoMascota;
import com.coursera.sacbe.petagramaxelsegura.pojo.Mascota;

import java.util.ArrayList;

/**
 * Created by axel on 30/09/2016.
 */
public class MascotaResponse {

    private String id;
    private String token;
    private String animal;

    private ArrayList<FotoMascota> mascotas;
    //private ArrayList<>
    private FotoMascota userInformation;

    public MascotaResponse(String id, String token, String animal) {
        this.id = id;
        this.token = token;
        this.animal = animal;

    }

    public ArrayList<FotoMascota> getMascotas() {
        return mascotas;
    }

    public void setMascotas(ArrayList<FotoMascota> mascotas) {
        this.mascotas = mascotas;
    }

    public FotoMascota getUserInformation() {
        return userInformation;
    }

    public void setUserInformation(FotoMascota userInformation) {
        this.userInformation = userInformation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }
}
