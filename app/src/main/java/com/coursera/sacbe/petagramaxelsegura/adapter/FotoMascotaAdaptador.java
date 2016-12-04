package com.coursera.sacbe.petagramaxelsegura.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.coursera.sacbe.petagramaxelsegura.DetalleMascota;
import com.coursera.sacbe.petagramaxelsegura.DetalleMascotaFoto;
import com.coursera.sacbe.petagramaxelsegura.R;
import com.coursera.sacbe.petagramaxelsegura.pojo.FotoMascota;
import com.coursera.sacbe.petagramaxelsegura.pojo.Mascota;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Sacbe on 10/09/2016.
 */
public class FotoMascotaAdaptador extends  RecyclerView.Adapter<FotoMascotaAdaptador.FotoMascotaViewHolder> {

    private ArrayList<FotoMascota> fotosMascota;
    private Activity activity;

    public FotoMascotaAdaptador(ArrayList<FotoMascota> fotosMascota, Activity activity){
        this.fotosMascota=fotosMascota;
        this.activity=activity;
    }

    //Inflar el layout y lo pasará al view holder para obtener los views
    @Override
    public FotoMascotaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_foto_mascota, parent, false);
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_foto_mascota, parent, false);
        return new FotoMascotaViewHolder(v);
    }

    //asocia cada elemento de la lista con cada view
    @Override
    public void onBindViewHolder(final FotoMascotaViewHolder mascotaViewHolder, int position) {
        final FotoMascota fotoMascota = fotosMascota.get(position);
        //mascotaViewHolder.imgFotoMascotaCVFrg.setImageResource(fotoMascota.getFoto());

        Picasso.with(activity)
                .load(fotoMascota.getUrlFoto())
                .placeholder(R.drawable.unam_pumas)
                .into(mascotaViewHolder.imgFotoMascotaCVFrg);


        mascotaViewHolder.tvRatingCVFrg.setText(String.valueOf(fotoMascota.getLikesFoto()));

        mascotaViewHolder.imgFotoMascotaCVFrg.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                //Toast.makeText(activity, contacto.getNombre(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity, DetalleMascotaFoto.class);
                intent.putExtra(activity.getResources().getString(R.string.pUrl), fotoMascota.getUrlFoto());
                intent.putExtra(activity.getResources().getString(R.string.pLikes), fotoMascota.getLikesFoto());
                activity.startActivity(intent);
                //activity.finish();
            }
        });

    }

    public int getItemCount() {//Cantidad de elementos que contiene la lista
        return fotosMascota.size();
    }

    public static class FotoMascotaViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgFotoMascotaCVFrg;
        private TextView tvRatingCVFrg;

        public FotoMascotaViewHolder(View itemView) {
            super(itemView);
            imgFotoMascotaCVFrg = (ImageView) itemView.findViewById(R.id.imgFotoMascotaCVFrg);
            tvRatingCVFrg = (TextView) itemView.findViewById(R.id.tvRatingCVFrg);
        }
    }

}
