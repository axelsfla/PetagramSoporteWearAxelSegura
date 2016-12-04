package com.coursera.sacbe.petagramaxelsegura.presentador;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.coursera.sacbe.petagramaxelsegura.db.ConstructorMascotas;
import com.coursera.sacbe.petagramaxelsegura.fragment.IRecyclerViewFragmentFoto;
import com.coursera.sacbe.petagramaxelsegura.fragment.IRecyclerViewFragmentView;
import com.coursera.sacbe.petagramaxelsegura.pojo.FotoMascota;
import com.coursera.sacbe.petagramaxelsegura.pojo.Mascota;
import com.coursera.sacbe.petagramaxelsegura.restApi.IEndPointsApi;
import com.coursera.sacbe.petagramaxelsegura.restApi.adapter.RestApiAdapter;
import com.coursera.sacbe.petagramaxelsegura.restApi.model.MascotaResponse;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sacbe on 18/09/2016.
 */
public class RecyclerViewFragmentPresenter implements IRecyclerViewFragmentPresenter  {

    private IRecyclerViewFragmentView iRecyclerViewFragmentView;
    //private IRecyclerViewFragmentFoto iRecyclerViewFragmentFoto;
    private Context context;
    private ConstructorMascotas constructorMascotas;
    private ArrayList<FotoMascota> seguidores;
    private int iSeguidores;
    private ArrayList<FotoMascota> fotosMascotas;

    public RecyclerViewFragmentPresenter(IRecyclerViewFragmentView iRecyclerViewFragmentView, Context context) {
        this.iRecyclerViewFragmentView = iRecyclerViewFragmentView;
        this.context = context;
        //obtenerMascotasBaseDatos();
        obtenerSeguidores();
    }

    public RecyclerViewFragmentPresenter(IRecyclerViewFragmentView iRecyclerViewFragmentView, Context context, String idInstagramfrom) {
        this.iRecyclerViewFragmentView = iRecyclerViewFragmentView;
        this.context = context;

        FotoMascota fotoMascota = new FotoMascota();
        fotoMascota.setId(idInstagramfrom);
        seguidores = new ArrayList<>();
        seguidores.add(fotoMascota);

        obtenerFotosSeguidores();
    }

    @Override
    public void obtenerMascotasBaseDatos() {
        //constructorMascotas = new ConstructorMascotas(context);
        //mascotas = constructorMascotas.obtenerDatos();
        //mostrarMascotasRV();

    }

    @Override
    public void mostrarMascotasRV() {
        this.iRecyclerViewFragmentView.inicializarAdaptador(this.iRecyclerViewFragmentView.crearAdaptador(fotosMascotas));
        this.iRecyclerViewFragmentView.generarLinearLyout();
    }

    @Override
    public void obtenerSeguidores() {
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
    public void obtenerFotosSeguidores() {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonFotosSeguidores = restApiAdapter.construyeGsonDeserializadorMediaRecent();
        IEndPointsApi iEndPointsApi = restApiAdapter.establecerConexionRestApiInstagram(gsonFotosSeguidores);

        //Call<MascotaResponse> mascotaResponseCall = iEndPointsApi.getRecentMedia();

        fotosMascotas = new ArrayList<>();

        iSeguidores = 0;
        for (FotoMascota fotoMascota:seguidores) {
            Call<MascotaResponse> mascotaResponseCall = iEndPointsApi.getRecentMedia(Long.parseLong(fotoMascota.getId()));

            mascotaResponseCall.enqueue(new Callback<MascotaResponse>() {
                @Override
                public void onResponse(Call<MascotaResponse> call, Response<MascotaResponse> response) {
                    iSeguidores++;
                    MascotaResponse mascotaResponse = response.body();
                    fotosMascotas.addAll(mascotaResponse.getMascotas());

                    if(iSeguidores==seguidores.size()){
                        mostrarMascotasRV();
                    }
                    //mascotas = mascotaResponse.getMascotas();
                    //obtenerFotosDeSeguidores()

                }

                @Override
                public void onFailure(Call<MascotaResponse> call, Throwable t) {
                    Toast.makeText(context, "Algo pasó en la conexión, intentalo de nuevo", Toast.LENGTH_SHORT).show();
                    Log.e("Falló la conexión" , t.toString());
                }
            });
        }




    }
}
