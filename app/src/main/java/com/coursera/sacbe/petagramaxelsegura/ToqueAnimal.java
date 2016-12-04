package com.coursera.sacbe.petagramaxelsegura;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import com.coursera.sacbe.petagramaxelsegura.restApi.ConstantesRestApi;
import com.coursera.sacbe.petagramaxelsegura.restApi.IEndPointsApi;
import com.coursera.sacbe.petagramaxelsegura.restApi.adapter.RestApiAdapter;
import com.coursera.sacbe.petagramaxelsegura.restApi.model.*;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by axel on 02/12/2016.
 */

public class ToqueAnimal extends BroadcastReceiver {

    private static final String ANIMAL_EMISOR = "Unicornio";
    private static final String ANIMAL_RECEPTOR = "Perro";
    private static final String ANIMAL_RECEPTOR_FOTOID = "1220369215222909308_1566128009";
    private static final String PERRO = "-KY2YwpMuOeD84OoR9rG";
    private static final String UNICORNIO = "-KVLFW110Qd9WnU5K_D1";
    private static final String ID_RECEPTOR = PERRO;
    private Context context;
    private ViewPager viewPager;
    private String idInstagramFrom = "";
    private String usuarioInstagramFrom = "";
    private static final String OUTGOING_STATUS_FOLLOWS = "follows";
    private static final String STATUS_UNFOLLOW = "unfollow";
    private static final String STATUS_FOLLOW = "follow";
    private static final String OUTGOING_STATUS_NONE = "none";

    @Override
    public void onReceive(Context context, Intent intent) {
        String ACTION_KEY = context.getString(R.string.toque_animal);
        String action = intent.getAction();
        this.context = context;

        Bundle parametros = intent.getExtras();

        //if(ACTION_KEY.equals(action)){
        if(action.equals(context.getString(R.string.toque_animal))){
            toqueAnimal();
            Toast.makeText(context, "Diste un toque a ", Toast.LENGTH_SHORT).show();
        }
        else if(action.equals(context.getString(R.string.ver_perfil))){
            Intent iPerfil = new Intent(context, MainActivity.class);
            iPerfil.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            iPerfil.putExtra(context.getResources().getString(R.string.pViewPage), 1);
            context.startActivity(iPerfil);
            Toast.makeText(context, "Ver el Perfil ", Toast.LENGTH_SHORT).show();
        }
        else if(action.equals(context.getString(R.string.follow_unfollow))){
            if(parametros != null){
                idInstagramFrom = parametros.getString(context.getResources().getString(R.string.pIdInstagramFrom));
                usuarioInstagramFrom = parametros.getString(context.getResources().getString(R.string.pUsuarioInstagramFrom));
                //Primero realizamos la búsqueda del estatus de la relacion con el usuario
                obtenerAccionRelacion();


                Log.d("ID_INSTAGRAM_FROM: ", idInstagramFrom);
            }else{
                Log.d("NO ID_INSTAGRAM_FROM: ", idInstagramFrom);
            }
            Toast.makeText(context, "Follow unfollow", Toast.LENGTH_SHORT).show();
        }
        else if(action.equals(context.getString(R.string.ver_usuario))){

            if(parametros != null){
                idInstagramFrom = parametros.getString(context.getResources().getString(R.string.pIdInstagramFrom));
                usuarioInstagramFrom = parametros.getString(context.getResources().getString(R.string.pUsuarioInstagramFrom));
                Intent iUsuario = new Intent(context, UsuarioInstagramFrom.class);
                iUsuario.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                iUsuario.putExtra(context.getResources().getString(R.string.pIdInstagramFrom), idInstagramFrom);
                context.startActivity(iUsuario);

                Toast.makeText(context, "Ver el Usuario ", Toast.LENGTH_SHORT).show();

                Log.d("ID_INSTAGRAM_FROM: ", idInstagramFrom);
            }else{
                Log.d("NO ID_INSTAGRAM_FROM: ", idInstagramFrom);
                Toast.makeText(context, "No se puede ver el Usuario ", Toast.LENGTH_SHORT).show();
            }




        }
    }

    public void toqueAnimal(){
        Log.d("TOQUE_ANIMAL", "true");
        final MascotaResponse mascotaResponse = new MascotaResponse(ID_RECEPTOR, "123", ANIMAL_RECEPTOR);
        RestApiAdapter restApiAdapter =  new RestApiAdapter();
        IEndPointsApi iEndPointsApi = restApiAdapter.establecerConexionRestApiInstagram();

        //Call<MascotaResponse> mascotaResponseCall = iEndPointsApi.getRecentMedia();

        Call<MascotaResponse> mascotaResponseCall = iEndPointsApi.setMediaLike(ConstantesRestApi.ACCESS_TOKEN, ANIMAL_RECEPTOR_FOTOID);
        mascotaResponseCall.enqueue(new Callback<MascotaResponse>() {
            @Override
            public void onResponse(Call<MascotaResponse> call, Response<MascotaResponse> response) {
                //MascotaResponse usuarioResponse1 =response.body();
                String idDispositivoFrom = FirebaseInstanceId.getInstance().getToken();
                String idUsuarioInstagramTo = ANIMAL_RECEPTOR;//usuarioResponse1.getUserInformation().getNombreUsuario(); //obtenerCuentaConfigurada();
                String idUsuarioInstagramFrom = obtenerCuentaConfigurada();
                Log.d("ID_DISPOSITIVO", idDispositivoFrom);
                Log.d("ID_USR_INSTAGRAM_TO", idUsuarioInstagramTo);
                Log.d("ID_USR_INSTAGRAM_FROM", idUsuarioInstagramFrom);
            }

            @Override
            public void onFailure(Call<MascotaResponse> call, Throwable t) {

            }
        });
    }

    private String obtenerCuentaConfigurada(){

        SharedPreferences miPreferenciaCompartida = context.getSharedPreferences("MisDatosPersonales", Context.MODE_PRIVATE);

        String nombrePerfilActual = miPreferenciaCompartida.getString(context.getResources().getString(R.string.pNombrePerfil), "");

        return nombrePerfilActual;
    }

    public void obtenerAccionRelacion() {

        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonRelationship = restApiAdapter.construyeGsonDeserializadorRelationship();
        IEndPointsApi iEndPointsApi = restApiAdapter.establecerConexionRestApiInstagram(gsonRelationship);

        Call<RelationshipResponse> relationshipResponseCall = iEndPointsApi.getRelationship(idInstagramFrom);

        relationshipResponseCall.enqueue(new Callback<RelationshipResponse>() {
            @Override
            public void onResponse(Call<RelationshipResponse> call, Response<RelationshipResponse> response) {
                RelationshipResponse relationshipResponse = response.body();
                String outgoing_status = relationshipResponse.getOutgoing_status();
                String accion = "";
                //Aqui validamos si se da FOLLOW o UNFOLLOW
                if(OUTGOING_STATUS_FOLLOWS.equals(outgoing_status)){
                    //Entonces se da unfollow
                    accion = STATUS_UNFOLLOW;
                }else if(OUTGOING_STATUS_NONE.equals(outgoing_status)){
                    //Entonces se da follow
                    accion = STATUS_FOLLOW;
                }

                //Se realiza el siguiente endpoint.
                if(!accion.equals("")){
                    setRelationship(accion);

                }else{
                    Toast.makeText(context, "No se puede modificar la relación", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RelationshipResponse> call, Throwable t) {
                Toast.makeText(context, "Algo pasó en la conexión, intentalo de nuevo", Toast.LENGTH_SHORT).show();
                Log.e("Falló la conexión" , t.toString());
            }
        });
    }

    public void setRelationship(String accion) {

        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonRelationship = restApiAdapter.construyeGsonDeserializadorRelationship();
        IEndPointsApi iEndPointsApi = restApiAdapter.establecerConexionRestApiInstagram(gsonRelationship);

        Call<RelationshipResponse> relationshipResponseCall = iEndPointsApi.setRelationship(accion, idInstagramFrom);

        relationshipResponseCall.enqueue(new Callback<RelationshipResponse>() {
            @Override
            public void onResponse(Call<RelationshipResponse> call, Response<RelationshipResponse> response) {
                RelationshipResponse relationshipResponse = response.body();
                String outgoing_status = relationshipResponse.getOutgoing_status();

                //Aqui validamos si se da FOLLOW o UNFOLLOW
                if(OUTGOING_STATUS_FOLLOWS.equals(outgoing_status)){
                    Toast.makeText(context, "Has seguido a " + usuarioInstagramFrom, Toast.LENGTH_SHORT).show();
                }else if(OUTGOING_STATUS_NONE.equals(outgoing_status)){
                    //Entonces se da follow
                    Toast.makeText(context, "Has dejado de seguir a " + usuarioInstagramFrom, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RelationshipResponse> call, Throwable t) {
                Toast.makeText(context, "Algo pasó en la conexión, intentalo de nuevo", Toast.LENGTH_SHORT).show();
                Log.e("Falló la conexión" , t.toString());
            }
        });

    }

}
