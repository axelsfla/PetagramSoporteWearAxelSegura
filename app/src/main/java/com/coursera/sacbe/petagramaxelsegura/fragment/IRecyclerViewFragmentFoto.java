package com.coursera.sacbe.petagramaxelsegura.fragment;

import com.coursera.sacbe.petagramaxelsegura.adapter.FotoMascotaAdaptador;
import com.coursera.sacbe.petagramaxelsegura.adapter.MascotaAdaptador;
import com.coursera.sacbe.petagramaxelsegura.pojo.FotoMascota;

import java.util.ArrayList;

/**
 * Created by axel on 30/09/2016.
 */
public interface IRecyclerViewFragmentFoto {

    public FotoMascotaAdaptador crearAdaptador(ArrayList<FotoMascota> mascotas);

    public void inicializarAdaptador(FotoMascotaAdaptador adaptador);

    public void generarGridLyout();

    //public void generarLinearLyout();

}
