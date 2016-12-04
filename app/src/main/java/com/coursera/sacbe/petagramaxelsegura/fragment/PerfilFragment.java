package com.coursera.sacbe.petagramaxelsegura.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.coursera.sacbe.petagramaxelsegura.R;
import com.coursera.sacbe.petagramaxelsegura.adapter.FotoMascotaAdaptador;
import com.coursera.sacbe.petagramaxelsegura.pojo.FotoMascota;
import com.coursera.sacbe.petagramaxelsegura.presentador.IRecyclerViewFragmentFotoPresenter;
import com.coursera.sacbe.petagramaxelsegura.presentador.RecyclerViewFragmentFotoPresenter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment implements IRecyclerViewFragmentFoto{

    private ImageView imgvFotoFrg;
    private TextView tvNombreFrg;

    private ArrayList<FotoMascota> fotosMascota;
    private RecyclerView rvMascotaFrg;
    private IRecyclerViewFragmentFotoPresenter presenter;
    private long userId = Long.parseLong("3978956593") ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_perfil, container, false);

        imgvFotoFrg = (ImageView) v.findViewById(R.id.imgvFotoFrg);
        tvNombreFrg = (TextView) v.findViewById(R.id.tvNombreFrg);

        imgvFotoFrg.setImageResource(R.drawable.husky);
        tvNombreFrg.setText("Miky");

        SharedPreferences miPreferenciaCompartida = getActivity().getSharedPreferences("MisDatosPersonales", Context.MODE_PRIVATE);

        String nombrePerfilActual = miPreferenciaCompartida.getString(getResources().getString(R.string.pNombrePerfil), "");

        rvMascotaFrg = (RecyclerView) v.findViewById(R.id.rvMascotaFrg);
        //nombrePerfilActual = "";
        if(nombrePerfilActual != "") {
            presenter = new RecyclerViewFragmentFotoPresenter(this, getContext(),nombrePerfilActual);
        }else{
            presenter = new RecyclerViewFragmentFotoPresenter(this, getContext(),userId);
        }



        /*
        rvMascotaFrg = (RecyclerView) v.findViewById(R.id.rvMascotaFrg);
        GridLayoutManager glm = new GridLayoutManager(getActivity(),3);
        //glm.setOrientation(LinearLayoutManager.VERTICAL);

        rvMascotaFrg.setLayoutManager(glm);
        //inicializarListaMascotas();
        inicializarAdaptador();
        */

        return v;
    }

    /*public void inicializarAdaptador(){
        FotoMascotaAdaptador adaptador = new FotoMascotaAdaptador(fotosMascota, getActivity());
        rvMascotaFrg.setAdapter(adaptador);
    }*/

    @Override
    public FotoMascotaAdaptador crearAdaptador(ArrayList<FotoMascota> mascotas) {
        FotoMascotaAdaptador adaptador = new FotoMascotaAdaptador(mascotas, getActivity());
        return adaptador;
    }

    @Override
    public void inicializarAdaptador(FotoMascotaAdaptador adaptador) {
        rvMascotaFrg.setAdapter(adaptador);
    }

    @Override
    public void generarGridLyout() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rvMascotaFrg.setLayoutManager(gridLayoutManager);


        fotosMascota = presenter.getFotosMascotas();

        if(fotosMascota != null && fotosMascota.size()>0){
            String nombreCompleto = fotosMascota.get(0).getNombreCompleto();
            String urlFotoPerfil = fotosMascota.get(0).getUrlFotoPerfil();

            tvNombreFrg.setText(nombreCompleto);

            Picasso.with(this.getActivity())
                    .load(urlFotoPerfil)
                    .placeholder(R.drawable.unam_pumas)
                    .into(imgvFotoFrg);

        }
    }

    /*
    public void inicializarListaMascotas(){

        fotosMascota = new ArrayList<FotoMascota>();
        fotosMascota.add(new FotoMascota(R.drawable.husky1,10));
        fotosMascota.add(new FotoMascota(R.drawable.husky2,3));
        fotosMascota.add(new FotoMascota(R.drawable.husky3,7));
        fotosMascota.add(new FotoMascota(R.drawable.husky4,1));
        fotosMascota.add(new FotoMascota(R.drawable.husky5,6));
        fotosMascota.add(new FotoMascota(R.drawable.husky6,2));
        fotosMascota.add(new FotoMascota(R.drawable.husky7,9));
        fotosMascota.add(new FotoMascota(R.drawable.husky8,1));
        fotosMascota.add(new FotoMascota(R.drawable.husky9,8));

    }
    */
}
