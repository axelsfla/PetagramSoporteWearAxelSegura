package com.coursera.sacbe.petagramaxelsegura.restApi;

import com.coursera.sacbe.petagramaxelsegura.restApi.model.MascotaResponse;
import com.coursera.sacbe.petagramaxelsegura.restApi.model.RelationshipResponse;
import com.coursera.sacbe.petagramaxelsegura.restApi.model.UsuarioInstagram;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by axel on 30/09/2016.
 */
public interface IEndPointsApi {
    @GET(ConstantesRestApi.URL_GET_RECENT_MEDIA_USER)
    Call<MascotaResponse> getRecentMedia();

    @GET(ConstantesRestApi.URL_SEARCH_USER_BY_NAME)
    Call<MascotaResponse> getUserByName(@Query("q") String userName);

    @GET(ConstantesRestApi.URL_GET_RECENT_MEDIA_USER_ID)
    Call<MascotaResponse> getRecentMedia(@Path("user-id") Long userId);

    @GET(ConstantesRestApi.URL_GET_FOLLOWED_BY)
    Call<MascotaResponse> getFollowedBy();

    @FormUrlEncoded
    @POST(ConstantesRestApi.URL_POST_MEDIA_LIKE)
    Call<MascotaResponse> setMediaLike(@Field("access_token") String accessToken, @Path("media-id") String mediaId);

    //https://api.instagram.com/v1/users/{user-id}/relationship?access_token=ACCESS-TOKEN
    @FormUrlEncoded
    @POST(ConstantesRestApi.URL_POST_RELATIONSHIP)
    Call<RelationshipResponse> setRelationship(@Field("action") String action,
                                               @Path("user-id") String mediaId);

    @GET(ConstantesRestApi.URL_GET_RELATIONSHIP)
    Call<RelationshipResponse> getRelationship(@Path("user-id") String userId);

    //Endpoint para Heroku
    @FormUrlEncoded
    @POST(ConstantesRestApi.KEY_POST_ID_TOKEN)
    Call<UsuarioInstagram> registrarTokenID(@Field("token") String token);

    @FormUrlEncoded
    @POST(ConstantesRestApi.KEY_POST_REGISTRAR_USUARIO)
    Call<UsuarioInstagram> registrarUsuarioID(@Field("idDispositivoFrom") String idDispositivo,
                                              @Field("idUsuarioInstagramFrom") String idUsuarioInstagram,
                                              @Field("idInstagramFrom") String idInstagramFrom);

    @FormUrlEncoded
    @POST(ConstantesRestApi.KEY_POST_REGISTRAR_LIKE_INSTAGRAM)
    Call<UsuarioInstagram> registrarLikeInstagram(@Field("idDispositivoFrom") String idDispositivo,
                                                  @Field("idUsuarioInstagramFrom") String idUsuarioInstagramFrom,
                                                  @Field("idUsuarioInstagramTo") String idUsuarioInstagramTo,
                                                  @Field("idFotoInstagram") String idFotoInstagram,
                                                  @Field("idInstagramFrom") String idFrom
                                                  );

    @GET(ConstantesRestApi.KEY_GET_LIKE_INSTAGRAM)
    Call<UsuarioInstagram> getLikeInstagram(@Path("id") String id);

}
