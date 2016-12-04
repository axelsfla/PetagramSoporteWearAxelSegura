package com.coursera.sacbe.petagramaxelsegura.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.coursera.sacbe.petagramaxelsegura.db.ConstructorMascotas;
import com.coursera.sacbe.petagramaxelsegura.pojo.FotoMascota;
import com.coursera.sacbe.petagramaxelsegura.pojo.Mascota;
import com.coursera.sacbe.petagramaxelsegura.restApi.ConstantesRestApi;
import com.coursera.sacbe.petagramaxelsegura.restApi.IEndPointsApi;
import com.coursera.sacbe.petagramaxelsegura.restApi.adapter.RestApiAdapter;
import com.coursera.sacbe.petagramaxelsegura.restApi.model.MascotaResponse;
import com.coursera.sacbe.petagramaxelsegura.restApi.model.UsuarioInstagram;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sacbe on 03/09/2016.
 */
public class MascotaAdaptador extends  RecyclerView.Adapter<MascotaAdaptador.MascotaViewHolder> {

    private ArrayList<FotoMascota> mascotas;
    private Activity activity;
    private String nombrePerfilActual;
    private String idInstagramFrom;

    public MascotaAdaptador(ArrayList<FotoMascota> mascotas, Activity activity){
        this.mascotas=mascotas;
        this.activity=activity;
    }

    //Inflar el layout y lo pasará al view holder para obtener los views
    @Override
    public MascotaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_mascota, parent, false);
        return new MascotaViewHolder(v);
    }

    //asocia cada elemento de la lista con cada view
    @Override
    public void onBindViewHolder(final MascotaViewHolder mascotaViewHolder, int position) {
        final FotoMascota fotoMascota = mascotas.get(position);
        //mascotaViewHolder.imgFoto.setImageResource(mascota.getFoto());

        Picasso.with(activity)
                .load(fotoMascota.getUrlFoto())
                .placeholder(R.drawable.unam_pumas)
                .into(mascotaViewHolder.imgFoto);

        mascotaViewHolder.tvNombre.setText(fotoMascota.getNombreCompleto());
        mascotaViewHolder.tvRaza.setText(fotoMascota.getNombreUsuario());

        String raiting = "";

        //if(fotoMascota.getLikesFoto()>0)
        raiting = String.valueOf(fotoMascota.getLikesFoto());

        mascotaViewHolder.tvRaiting.setText(String.valueOf(raiting)); //mascota.getRaiting())+" Likes"

        mascotaViewHolder.imgFoto.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){

                Toast.makeText(activity, fotoMascota.getNombreCompleto(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity, DetalleMascotaFoto.class);
                intent.putExtra(activity.getResources().getString(R.string.pUrl), fotoMascota.getUrlFoto());
                intent.putExtra(activity.getResources().getString(R.string.pLikes), fotoMascota.getLikesFoto());
//                intent.putExtra(activity.getResources().getString(R.string.pRaza), mascota.getRaza());

                //String raiting = "";

                /*if(mascota.getRaiting()>0)
                    raiting = String.valueOf(mascota.getRaiting());*/

                //intent.putExtra(activity.getResources().getString(R.string.pRating), raiting);
                activity.startActivity(intent);
                //activity.finish();

            }
        });

        mascotaViewHolder.btnLike.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
               /* ConstructorMascotas constructorMascotas = new ConstructorMascotas(activity);
                constructorMascotas.darLikeMascota(mascota);
                mascotaViewHolder.tvRaiting.setText(String.valueOf(constructorMascotas.obtenerLikesMascota(mascota)) + " Likes");
                */
                //Se envía el like a Instagram
                Log.d("LIKE_FOTO", fotoMascota.getIdFoto());
                RestApiAdapter restApiAdapter = new RestApiAdapter();
                //Gson gsonMediaLike = restApiAdapter.construyeGsonDeserializadorMediaLike();
                IEndPointsApi iEndPointsApi = restApiAdapter.establecerConexionRestApiInstagram();

                //Call<MascotaResponse> mascotaResponseCall = iEndPointsApi.getRecentMedia();

                Call<MascotaResponse> mascotaResponseCall = iEndPointsApi.setMediaLike(ConstantesRestApi.ACCESS_TOKEN,
                        fotoMascota.getIdFoto());

                mascotaResponseCall.enqueue(new Callback<MascotaResponse>() {
                    @Override
                    public void onResponse(Call<MascotaResponse> call, Response<MascotaResponse> response) {
                        //Se registra el like en la base de datos de firebase
                        String idDispositivoFrom = FirebaseInstanceId.getInstance().getToken();
                        String idUsuarioInstagramTo = fotoMascota.getNombreUsuario(); //obtenerCuentaConfigurada();
                        String idUsuarioInstagramFrom = obtenerCuentaConfigurada();
                        Log.d("ID_DISPOSITIVO", idDispositivoFrom);
                        Log.d("ID_USR_INSTAGRAM_TO", idUsuarioInstagramTo);
                        Log.d("ID_USR_INSTAGRAM_FROM", idUsuarioInstagramFrom);

                        RestApiAdapter restApiAdapter = new RestApiAdapter();

                        IEndPointsApi endpoints = restApiAdapter.establecerConexionRestApiHeroku();
                        Call<UsuarioInstagram> usuarioResponseCall = endpoints.registrarLikeInstagram(idDispositivoFrom,
                                idUsuarioInstagramFrom,
                                idUsuarioInstagramTo,
                                fotoMascota.getIdFoto(),
                                idInstagramFrom); //fotoMascota.getId()

                        usuarioResponseCall.enqueue(new Callback<UsuarioInstagram>() {
                            @Override
                            public void onResponse(Call<UsuarioInstagram> call, Response<UsuarioInstagram> response) {
                                UsuarioInstagram usuarioResponse = response.body();
                                Log.d("ID", usuarioResponse.getId());
                                Log.d("ID_DISPOSITIVO_TO", usuarioResponse.getIdDispositivoTo());
                                Log.d("ID_USR_INSTAGRAM_TO", usuarioResponse.getIdUsuarioInstagramTo());
                                Log.d("ID_DISPOSITIVO_FROM", usuarioResponse.getIdDispositivoFrom());
                                Log.d("ID_USR_INSTAGRAM_FROM", usuarioResponse.getIdUsuarioInstagramFrom());
                                Log.d("ID_FOTO_INSTAGRAM", usuarioResponse.getIdFotoInstagram());
                                Log.d("ID_FROM_INSTAGRAM", usuarioResponse.getIdInstagramFrom());

                                //Se envía la notificación
                                RestApiAdapter restApiAdapter = new RestApiAdapter();
                                IEndPointsApi endpoints = restApiAdapter.establecerConexionRestApiHeroku();
                                Call<UsuarioInstagram> usuarioResponseCall = endpoints.getLikeInstagram(usuarioResponse.getId());

                                usuarioResponseCall.enqueue(new Callback<UsuarioInstagram>() {
                                    @Override
                                    public void onResponse(Call<UsuarioInstagram> call, Response<UsuarioInstagram> response) {
                                        UsuarioInstagram usuarioResponse1 = response.body();
                                        Log.d("ID_FIREBASE", usuarioResponse1.getId());
                                        Log.d("ID_DISPOSITIVO_FB_TO", usuarioResponse1.getIdDispositivoTo());
                                        Log.d("ID_USR_INS_FB_TO", usuarioResponse1.getIdUsuarioInstagramTo());
                                        Log.d("ID_DISPOSITIVO_FB_FROM", usuarioResponse1.getIdDispositivoFrom());
                                        Log.d("ID_USR_INS_FB_FROM", usuarioResponse1.getIdUsuarioInstagramFrom());
                                        Log.d("ID_FOTO_INS_FIREBASE", usuarioResponse1.getIdFotoInstagram());
                                        Log.d("ID_INS_FROM_FIREBASE", usuarioResponse1.getIdInstagramFrom());

                                        Toast.makeText(activity, "Diste like a " + fotoMascota.getNombreUsuario(), Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<UsuarioInstagram> call, Throwable t) {
                                        Log.d("ERROR_HEROKU", "getLikeInstagram");
                                    }
                                });


                            }

                            @Override
                            public void onFailure(Call<UsuarioInstagram> call, Throwable t) {
                                Log.d("ERROR_HEROKU", "registrarLikeInstagram");
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<MascotaResponse> call, Throwable t) {
                        Toast.makeText(activity.getBaseContext(), "Algo pasó en la conexión, intentalo de nuevo", Toast.LENGTH_SHORT).show();
                        Log.e("Falló la conexión" , t.toString());
                    }
                });

            }
        });
    }

    private String obtenerCuentaConfigurada(){

        SharedPreferences miPreferenciaCompartida = activity.getSharedPreferences("MisDatosPersonales", Context.MODE_PRIVATE);

        nombrePerfilActual = miPreferenciaCompartida.getString(activity.getResources().getString(R.string.pNombrePerfil), "");
        idInstagramFrom = miPreferenciaCompartida.getString(activity.getResources().getString(R.string.pIdInstagramFrom), "");

        return nombrePerfilActual;
    }

    @Override
    public int getItemCount() {//Cantidad de elementos que contiene la lista
        return ((mascotas==null)? 0 : mascotas.size());
    }

    public static class MascotaViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgFoto;
        private TextView tvNombre;
        private TextView tvRaza;
        private TextView tvRaiting;
        private ImageButton btnLike;

        public MascotaViewHolder(View itemView) {
            super(itemView);
            imgFoto = (ImageView) itemView.findViewById(R.id.imgFotoCV);
            tvNombre = (TextView) itemView.findViewById(R.id.tvNombreCV);
            tvRaza = (TextView) itemView.findViewById(R.id.tvRazaCV);
            tvRaiting = (TextView) itemView.findViewById(R.id.tvRatingCV);
            btnLike = (ImageButton) itemView.findViewById(R.id.btnLike);
        }
    }
}
