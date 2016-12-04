package com.coursera.sacbe.petagramaxelsegura.restApi.deserializador;

import com.coursera.sacbe.petagramaxelsegura.restApi.JsonKeys;
import com.coursera.sacbe.petagramaxelsegura.restApi.model.RelationshipResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by Sacbe on 04/12/2016.
 */
public class RelationshipDeserializador implements JsonDeserializer<RelationshipResponse> {

    @Override
    public RelationshipResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        Gson gson = new Gson();

        RelationshipResponse relationshipResponse = gson.fromJson(json, RelationshipResponse.class);

        JsonObject relationshipResponseData = json.getAsJsonObject().getAsJsonObject(JsonKeys.MEDIA_RESPONSE_ARRAY);

        JsonObject relationshipResponseDataObject = relationshipResponseData.getAsJsonObject();
        String outgoing_status = relationshipResponseDataObject.get(JsonKeys.OUTGOING_STATUS).getAsString();
        //String incoming_status = relationshipResponseDataObject.get(JsonKeys.INCOMING_STATUS).getAsString();

        relationshipResponse.setOutgoing_status(outgoing_status);
        //relationshipResponse.setIncoming_status(incoming_status);

        return relationshipResponse;
    }
}
