package com.coursera.sacbe.petagramaxelsegura.restApi.adapter;

import com.coursera.sacbe.petagramaxelsegura.restApi.ConstantesRestApi;
import com.coursera.sacbe.petagramaxelsegura.restApi.IEndPointsApi;
import com.coursera.sacbe.petagramaxelsegura.restApi.deserializador.MascotaDeserializador;
import com.coursera.sacbe.petagramaxelsegura.restApi.deserializador.RelationshipDeserializador;
import com.coursera.sacbe.petagramaxelsegura.restApi.deserializador.SeguidorDeserializador;
import com.coursera.sacbe.petagramaxelsegura.restApi.deserializador.UsuarioDeserializador;
import com.coursera.sacbe.petagramaxelsegura.restApi.model.MascotaResponse;
import com.coursera.sacbe.petagramaxelsegura.restApi.model.RelationshipResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by axel on 30/09/2016.
 */
public class RestApiAdapter {

    public IEndPointsApi establecerConexionRestApiInstagram(Gson gson){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantesRestApi.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(IEndPointsApi.class);

    }

    public IEndPointsApi establecerConexionRestApiInstagram(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantesRestApi.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(IEndPointsApi.class);

    }

    public IEndPointsApi establecerConexionRestApiHeroku(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantesRestApi.HEROKU_ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                ;
        return retrofit.create(IEndPointsApi.class);

    }

    public Gson construyeGsonDeserializadorMediaRecent(){

        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(MascotaResponse.class, new MascotaDeserializador());

        return gsonBuilder.create();
    }

    public Gson construyeGsonDeserializadorUserInformation(){

        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(MascotaResponse.class, new UsuarioDeserializador());

        return gsonBuilder.create();

    }

    public Gson construyeGsonDeserializadorSeguidores(){

        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(MascotaResponse.class, new SeguidorDeserializador());

        return gsonBuilder.create();

    }

    public Gson construyeGsonDeserializadorRelationship(){

        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(RelationshipResponse.class, new RelationshipDeserializador());

        return gsonBuilder.create();
    }
}
