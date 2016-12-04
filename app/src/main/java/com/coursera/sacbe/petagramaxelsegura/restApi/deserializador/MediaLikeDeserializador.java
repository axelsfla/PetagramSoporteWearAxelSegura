package com.coursera.sacbe.petagramaxelsegura.restApi.deserializador;

import com.coursera.sacbe.petagramaxelsegura.restApi.JsonKeys;
import com.coursera.sacbe.petagramaxelsegura.restApi.model.MascotaResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by Sacbe on 29/10/2016.
 */

public class MediaLikeDeserializador implements JsonDeserializer<MascotaResponse> {

    @Override
    public MascotaResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        Gson gson = new Gson();

        MascotaResponse mascotaResponse = gson.fromJson(json, MascotaResponse.class);

        //JsonArray mascotaResponseData = json.getAsJsonObject().getAsJsonArray(JsonKeys.MEDIA_RESPONSE_ARRAY);

        //mascotaResponse.setMascotas(deserializarMascotaDeJson(mascotaResponseData));


        return mascotaResponse;
    }


}
