package com.coursera.sacbe.petagramaxelsegura.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.coursera.sacbe.petagramaxelsegura.R;
import com.coursera.sacbe.petagramaxelsegura.adapter.FotoMascotaAdaptador;
import com.coursera.sacbe.petagramaxelsegura.adapter.MascotaAdaptador;
import com.coursera.sacbe.petagramaxelsegura.pojo.FotoMascota;
import com.coursera.sacbe.petagramaxelsegura.presentador.IRecyclerViewFragmentFotoPresenter;
import com.coursera.sacbe.petagramaxelsegura.presentador.RecyclerViewFragmentFotoPresenter;

import java.util.ArrayList;

/**
 * Created by axel on 30/09/2016.
 */
public class RecyclerViewFragmentFoto extends Fragment implements IRecyclerViewFragmentFoto {
    private ArrayList<FotoMascota> mascotas;
    private RecyclerView listaMascotas;
    private IRecyclerViewFragmentFotoPresenter presenter;
    private long userId = Long.parseLong("3978956593") ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        listaMascotas = (RecyclerView) v.findViewById(R.id.rvMascota);

        SharedPreferences miPreferenciaCompartida = getActivity().getSharedPreferences("MisDatosPersonales", Context.MODE_PRIVATE);

        String nombrePerfilActual = miPreferenciaCompartida.getString(getResources().getString(R.string.pNombrePerfil), "");
        //nombrePerfilActual = "";

        if(nombrePerfilActual != "") {
            presenter = new RecyclerViewFragmentFotoPresenter(this, getContext(), nombrePerfilActual);
        }
        else{
            presenter = new RecyclerViewFragmentFotoPresenter(this, getContext(), userId);
        }


        return v;
    }

    /*
    public void inicializarListaMascotas(){

        mascotas = new ArrayList<Mascota>();

        mascotas.add(new Mascota(R.drawable.husky, "Miky", "Husky"));
        mascotas.add(new Mascota(R.drawable.bostonterrier, "Terry", "Boston Terrier"));
        mascotas.add(new Mascota(R.drawable.braco, "Toth", "Braco"));
        mascotas.add(new Mascota(R.drawable.chowchow, "Oso", "Chow Chow"));
        mascotas.add(new Mascota(R.drawable.bullterrier, "Seth", "Bull Terrier"));
        mascotas.add(new Mascota(R.drawable.foxhound, "Lasha", "Foxhound"));
        mascotas.add(new Mascota(R.drawable.goldenretriever, "Anubis", "Golden Retriever"));
        mascotas.add(new Mascota(R.drawable.pastoraustraliano, "Boby", "Pastor Australiano"));
        mascotas.add(new Mascota(R.drawable.perrocrestado, "Pelos", "Crestado"));

    }
    */

    @Override
    public FotoMascotaAdaptador crearAdaptador(ArrayList<FotoMascota> mascotas) {
        FotoMascotaAdaptador adaptador = new FotoMascotaAdaptador(mascotas, getActivity());
        return adaptador;
    }

    @Override
    public void inicializarAdaptador(FotoMascotaAdaptador adaptador) {
        listaMascotas.setAdapter(adaptador);
    }


    @Override
    public void generarGridLyout() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        listaMascotas.setLayoutManager(gridLayoutManager);
    }

}
