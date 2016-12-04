package com.coursera.sacbe.petagramaxelsegura.restApi.deserializador;

import com.coursera.sacbe.petagramaxelsegura.pojo.FotoMascota;
import com.coursera.sacbe.petagramaxelsegura.restApi.JsonKeys;
import com.coursera.sacbe.petagramaxelsegura.restApi.model.MascotaResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Sacbe on 01/10/2016.
 */
public class UsuarioDeserializador implements JsonDeserializer<MascotaResponse> {

    @Override
    public MascotaResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();

        MascotaResponse mascotaResponse = gson.fromJson(json,MascotaResponse.class);

        JsonArray mascotaResponseData = json.getAsJsonObject().getAsJsonArray(JsonKeys.MEDIA_RESPONSE_ARRAY);

        mascotaResponse.setUserInformation(deserializarUsuarioDeJson(mascotaResponseData));

        return mascotaResponse;
    }

    private FotoMascota deserializarUsuarioDeJson(JsonArray mascotaResponseData){

        FotoMascota mascota = new FotoMascota();

        for (int i = 0; i< mascotaResponseData.size(); i++){

            JsonObject mascotaResponseDataObject = mascotaResponseData.get(i).getAsJsonObject();
            //JsonObject userJson = MascotaResponseDataObject.getAsJsonObject(JsonKeys.USER);
            String id = mascotaResponseDataObject.get(JsonKeys.USER_ID).getAsString();
            String nombreCompleto = mascotaResponseDataObject.get(JsonKeys.USER_FULLNAME).getAsString();
            String fotoPerfil = mascotaResponseDataObject.get(JsonKeys.USER_PROFILE_PICTURE).getAsString();

            mascota.setId(id);
            mascota.setNombreCompleto(nombreCompleto);
            mascota.setUrlFotoPerfil(fotoPerfil);

        }

        return mascota;

    }
}
