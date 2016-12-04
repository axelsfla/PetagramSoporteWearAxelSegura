package com.coursera.sacbe.petagramaxelsegura.presentador;

import com.coursera.sacbe.petagramaxelsegura.pojo.FotoMascota;

import java.util.ArrayList;

/**
 * Created by axel on 30/09/2016.
 */
public interface IRecyclerViewFragmentFotoPresenter {

    public void mostrarMascotasRV();

    public void obtenerMediosRecientes();

    public ArrayList<FotoMascota> getFotosMascotas();

    public void obtenerUserId(String userName);

    //public void obtenerSeguidores();

}
