package com.coursera.sacbe.petagramaxelsegura.presentador;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.coursera.sacbe.petagramaxelsegura.fragment.IRecyclerViewFavoritas;
import com.coursera.sacbe.petagramaxelsegura.pojo.FotoMascota;
import com.coursera.sacbe.petagramaxelsegura.restApi.IEndPointsApi;
import com.coursera.sacbe.petagramaxelsegura.restApi.adapter.RestApiAdapter;
import com.coursera.sacbe.petagramaxelsegura.restApi.model.MascotaResponse;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sacbe on 18/09/2016.
 */
public class RecyclerViewFavoritasPresenter implements IRecyclerViewFavoritasPresenter {

    private IRecyclerViewFavoritas iRecyclerViewFavoritas;
    private Context context;
    //private ConstructorMascotas constructorMascotas;
    private ArrayList<FotoMascota> seguidores;
    private int iSeguidores;
    private ArrayList<FotoMascota> fotosMascotas;

    public RecyclerViewFavoritasPresenter(IRecyclerViewFavoritas iRecyclerViewFavoritas, Context context) {
        this.iRecyclerViewFavoritas = iRecyclerViewFavoritas;
        this.context = context;
        obtenerMascotasFavoritas();
        //obtenerSeguidores();
    }

    @Override
    public void obtenerMascotasFavoritas() {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonSeguidores = restApiAdapter.construyeGsonDeserializadorSeguidores();
        IEndPointsApi iEndPointsApi = restApiAdapter.establecerConexionRestApiInstagram(gsonSeguidores);

        //Call<MascotaResponse> mascotaResponseCall = iEndPointsApi.getRecentMedia();

        Call<MascotaResponse> mascotaResponseCall = iEndPointsApi.getFollowedBy();

        mascotaResponseCall.enqueue(new Callback<MascotaResponse>() {
            @Override
            public void onResponse(Call<MascotaResponse> call, Response<MascotaResponse> response) {
                MascotaResponse mascotaResponse = response.body();
                seguidores = mascotaResponse.getMascotas();
                obtenerFotosSeguidores();
                //mostrarMascotasRV();
            }

            @Override
            public void onFailure(Call<MascotaResponse> call, Throwable t) {
                Toast.makeText(context, "Algo pasó en la conexión, intentalo de nuevo", Toast.LENGTH_SHORT).show();
                Log.e("Falló la conexión" , t.toString());
            }
        });
    }

    @Override
    public void mostrarMascotasRV() {
        iRecyclerViewFavoritas.inicializarAdaptador(iRecyclerViewFavoritas.crearAdaptador(fotosMascotas));
        iRecyclerViewFavoritas.generarLinearLyout();
    }

    @Override
    public void obtenerFotosSeguidores() {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonFotosSeguidores = restApiAdapter.construyeGsonDeserializadorMediaRecent();
        IEndPointsApi iEndPointsApi = restApiAdapter.establecerConexionRestApiInstagram(gsonFotosSeguidores);

        //Call<MascotaResponse> mascotaResponseCall = iEndPointsApi.getRecentMedia();

        fotosMascotas = new ArrayList<>();

        iSeguidores = 0;
        for (final FotoMascota fotoMascota : seguidores) {
            Call<MascotaResponse> mascotaResponseCall = iEndPointsApi.getRecentMedia(Long.parseLong(fotoMascota.getId()));

            mascotaResponseCall.enqueue(new Callback<MascotaResponse>() {
                @Override
                public void onResponse(Call<MascotaResponse> call, Response<MascotaResponse> response) {
                    iSeguidores++;
                    MascotaResponse mascotaResponse = response.body();
                    fotosMascotas.addAll(mascotaResponse.getMascotas());

                    if (iSeguidores == seguidores.size()) {
                        //Se obtienen las fotos con más likes

                        Collections.sort(fotosMascotas,Collections.<FotoMascota>reverseOrder());
                        ArrayList<FotoMascota> favoritas = new ArrayList<FotoMascota>();
                        for(int i = 0; i<5; i++){
                            favoritas.add(fotosMascotas.get(i));
                        }
                        fotosMascotas = favoritas;

                        //fotosMascotas.sort((o1, o2) -> o1.getLikesFoto().compareTo(o2.getLikesFoto()));

                        //fotosMascotas.sort(FotoMascota);
                        mostrarMascotasRV();
                    }
                    //mascotas = mascotaResponse.getMascotas();
                    //obtenerFotosDeSeguidores()

                }

                @Override
                public void onFailure(Call<MascotaResponse> call, Throwable t) {
                    Toast.makeText(context, "Algo pasó en la conexión, intentalo de nuevo", Toast.LENGTH_SHORT).show();
                    Log.e("Falló la conexión", t.toString());
                }
            });
        }

    }

}
