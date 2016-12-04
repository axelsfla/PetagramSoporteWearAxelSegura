package com.coursera.sacbe.petagramaxelsegura.presentador;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.coursera.sacbe.petagramaxelsegura.R;
import com.coursera.sacbe.petagramaxelsegura.fragment.IRecyclerViewFragmentFoto;
import com.coursera.sacbe.petagramaxelsegura.pojo.FotoMascota;
import com.coursera.sacbe.petagramaxelsegura.restApi.IEndPointsApi;
import com.coursera.sacbe.petagramaxelsegura.restApi.adapter.RestApiAdapter;
import com.coursera.sacbe.petagramaxelsegura.restApi.model.MascotaResponse;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by axel on 30/09/2016.
 */
public class RecyclerViewFragmentFotoPresenter implements IRecyclerViewFragmentFotoPresenter {

    private IRecyclerViewFragmentFoto iRecyclerViewFragmentFoto;
    private Context context;
    private ArrayList<FotoMascota> mascotas;
    private Long userId;

    public RecyclerViewFragmentFotoPresenter(IRecyclerViewFragmentFoto iRecyclerViewFragmentFoto, Context context, String userName) {
        this.iRecyclerViewFragmentFoto = iRecyclerViewFragmentFoto;
        this.context = context;
        obtenerUserId(userName);
    }

    public RecyclerViewFragmentFotoPresenter(IRecyclerViewFragmentFoto iRecyclerViewFragmentFoto, Context context, Long userId) {
        this.iRecyclerViewFragmentFoto = iRecyclerViewFragmentFoto;
        this.context = context;
        this.userId = userId;
        obtenerMediosRecientes();
    }

    @Override
    public void mostrarMascotasRV() {
        iRecyclerViewFragmentFoto.inicializarAdaptador(iRecyclerViewFragmentFoto.crearAdaptador(mascotas));
        iRecyclerViewFragmentFoto.generarGridLyout();
    }

    @Override
    public void obtenerMediosRecientes() {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonMediaRecent = restApiAdapter.construyeGsonDeserializadorMediaRecent();
        IEndPointsApi iEndPointsApi = restApiAdapter.establecerConexionRestApiInstagram(gsonMediaRecent);

        //Call<MascotaResponse> mascotaResponseCall = iEndPointsApi.getRecentMedia();

        Call<MascotaResponse> mascotaResponseCall = iEndPointsApi.getRecentMedia(userId);

        mascotaResponseCall.enqueue(new Callback<MascotaResponse>() {
            @Override
            public void onResponse(Call<MascotaResponse> call, Response<MascotaResponse> response) {
                MascotaResponse mascotaResponse = response.body();
                mascotas = mascotaResponse.getMascotas();
                mostrarMascotasRV();
            }

            @Override
            public void onFailure(Call<MascotaResponse> call, Throwable t) {
                Toast.makeText(context, "Algo pasó en la conexión, intentalo de nuevo", Toast.LENGTH_SHORT).show();
                Log.e("Falló la conexión" , t.toString());
            }
        });

    }

    @Override
    public ArrayList<FotoMascota> getFotosMascotas() {
        return mascotas;
    }

    @Override
    public void obtenerUserId(String userName) {
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonUsuarioInfo = restApiAdapter.construyeGsonDeserializadorUserInformation();
        IEndPointsApi iEndPointsApi = restApiAdapter.establecerConexionRestApiInstagram(gsonUsuarioInfo);


        //Call<MascotaResponse> mascotaResponseCall = iEndPointsApi.getRecentMedia();

        Call<MascotaResponse> mascotaResponseCall = iEndPointsApi.getUserByName(userName);

        mascotaResponseCall.enqueue(new Callback<MascotaResponse>() {
            @Override
            public void onResponse(Call<MascotaResponse> call, Response<MascotaResponse> response) {
                MascotaResponse mascotaResponse = response.body();
                if(mascotaResponse.getUserInformation().getId() != null) {
                    userId = Long.parseLong(mascotaResponse.getUserInformation().getId());
                    obtenerMediosRecientes();
                }
            }

            @Override
            public void onFailure(Call<MascotaResponse> call, Throwable t) {
                Toast.makeText(context, "Algo pasó en la conexión, intentalo de nuevo", Toast.LENGTH_SHORT).show();
                Log.e("Falló la conexión" , t.toString());
            }
        });
    }

}
