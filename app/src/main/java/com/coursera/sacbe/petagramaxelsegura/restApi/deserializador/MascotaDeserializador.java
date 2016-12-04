package com.coursera.sacbe.petagramaxelsegura.restApi.deserializador;

import com.coursera.sacbe.petagramaxelsegura.pojo.FotoMascota;
import com.coursera.sacbe.petagramaxelsegura.pojo.Mascota;
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
 * Created by axel on 30/09/2016.
 */
public class MascotaDeserializador implements JsonDeserializer<MascotaResponse> {

    @Override
    public MascotaResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        Gson gson = new Gson();

        MascotaResponse mascotaResponse = gson.fromJson(json,MascotaResponse.class);

        JsonArray mascotaResponseData = json.getAsJsonObject().getAsJsonArray(JsonKeys.MEDIA_RESPONSE_ARRAY);

        mascotaResponse.setMascotas(deserializarMascotaDeJson(mascotaResponseData));


        return mascotaResponse;
    }

    private ArrayList<FotoMascota> deserializarMascotaDeJson(JsonArray mascotaResponseData){

        ArrayList<FotoMascota> mascotas = new ArrayList<>();

        for (int i = 0; i< mascotaResponseData.size(); i++){

            JsonObject MascotaResponseDataObject = mascotaResponseData.get(i).getAsJsonObject();
            JsonObject userJson = MascotaResponseDataObject.getAsJsonObject(JsonKeys.USER);
            String id = userJson.get(JsonKeys.USER_ID).getAsString();
            String nombreCompleto = userJson.get(JsonKeys.USER_FULLNAME).getAsString();
            String fotoPerfil = userJson.get(JsonKeys.USER_PROFILE_PICTURE).getAsString();
            String nombreUsuario = userJson.get(JsonKeys.USER_NAME).getAsString();

            JsonObject imageJson = MascotaResponseDataObject.get(JsonKeys.MEDIA_IMAGES).getAsJsonObject();
            JsonObject stdResolutionJson = imageJson.getAsJsonObject(JsonKeys.MEDIA_IMAGES_STANDARD_RESOLUTION);
            String urlFoto = stdResolutionJson.get(JsonKeys.MEDIA_URL).getAsString();

            String imageID = MascotaResponseDataObject.get(JsonKeys.MEDIA_ID).getAsString();

            JsonObject likesJson = MascotaResponseDataObject.getAsJsonObject(JsonKeys.MEDIA_LIKES);
            int likes = likesJson.get(JsonKeys.MEDIA_LIKES_COUNT).getAsInt();

            FotoMascota mascotaActual = new FotoMascota();
            mascotaActual.setId(id);
            mascotaActual.setNombreCompleto(nombreCompleto);
            mascotaActual.setNombreUsuario(nombreUsuario);
            mascotaActual.setUrlFotoPerfil(fotoPerfil);
            mascotaActual.setUrlFoto(urlFoto);
            mascotaActual.setLikesFoto(likes);
            mascotaActual.setIdFoto(imageID);

            mascotas.add(mascotaActual);

        }

        return mascotas;

    }


}
