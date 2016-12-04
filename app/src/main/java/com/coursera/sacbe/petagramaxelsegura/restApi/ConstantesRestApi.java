package com.coursera.sacbe.petagramaxelsegura.restApi;

/**
 * Created by axel on 30/09/2016.
 */
public final class ConstantesRestApi {

    //https://api.instagram.com/v1/
    public static final String INICIO_QUERY_STRING = "?";
    public static final String APPEND_QUERY_STRING = "&";
    public static final String VERSION = "/v1/";
    public static final String ROOT_URL = "https://api.instagram.com" + VERSION;
    public static final String ACCESS_TOKEN = "3978956593.bb1703f.56b842ce54bc4e34a5417f8bc900f6d3";
    public static final String KEY_ACCESS_TOKEN = "access_token=";
    public static final String KEY_GET_RECENT_MEDIA_USER = "users/self/media/recent/";
    //https://api.instagram.com/v1/users/self/media/recent/?access_token=ACCESS-TOKEN
    public static final String URL_GET_RECENT_MEDIA_USER = KEY_GET_RECENT_MEDIA_USER + INICIO_QUERY_STRING + KEY_ACCESS_TOKEN + ACCESS_TOKEN;

    //Para buscar un usuario
    //https://api.instagram.com/v1/users/search?q=jack&access_token=ACCESS-TOKEN
    public static final String KEY_GET_USER_SEARCH = "users/search";
    //public static final String KEY_USER_SEARCH = INICIO_QUERY_STRING + "q={user-name}";
    public static final String URL_SEARCH_USER_BY_NAME = KEY_GET_USER_SEARCH + INICIO_QUERY_STRING + KEY_ACCESS_TOKEN + ACCESS_TOKEN;

    //Para traer las fotos más recientes de un usuario a aprtir de su ID
    //https://api.instagram.com/v1/users/{user-id}/media/recent/?access_token=ACCESS-TOKEN
    public static final String KEY_GET_RECENT_MEDIA_USER_ID = "users/{user-id}/media/recent/";
    public static final String URL_GET_RECENT_MEDIA_USER_ID = KEY_GET_RECENT_MEDIA_USER_ID + INICIO_QUERY_STRING + KEY_ACCESS_TOKEN + ACCESS_TOKEN;

    //Para obtener los seguidores
    //https://api.instagram.com/v1/users/self/followed-by?access_token=ACCESS-TOKEN
    public static final String KEY_GET_FOLLOWED_BY = "users/self/followed-by";
    public static final String URL_GET_FOLLOWED_BY = KEY_GET_FOLLOWED_BY + INICIO_QUERY_STRING + KEY_ACCESS_TOKEN + ACCESS_TOKEN;

    //Para dar like a una foto de un usuario:
    //curl -F 'access_token=ACCESS-TOKEN' https://api.instagram.com/v1/media/{media-id}/likes
    public static final String URL_POST_MEDIA_LIKE = "media/{media-id}/likes";

    //https://api.instagram.com/v1/users/{user-id}/relationship?access_token=ACCESS-TOKEN
    //Para saber que relacion se tiene con el usuario y determinar si dar follow o unfollow
    public static final String URL_GET_RELATIONSHIP = "users/{user-id}/relationship" + INICIO_QUERY_STRING + KEY_ACCESS_TOKEN + ACCESS_TOKEN;

    //https://api.instagram.com/v1/users/{user-id}/relationship?access_token=ACCESS-TOKEN
    //Para dar follow o Unfollow:
    public static final String URL_POST_RELATIONSHIP = "users/{user-id}/relationship" + INICIO_QUERY_STRING + KEY_ACCESS_TOKEN + ACCESS_TOKEN;



    //Para heroku
    public static final String HEROKU_ROOT_URL = "https://evening-caverns-26985.herokuapp.com/";
    public static final String KEY_POST_ID_TOKEN = "token-device/";
    public static final String KEY_POST_REGISTRAR_USUARIO = "registrar-usuario/";
    public static final String KEY_POST_REGISTRAR_LIKE_INSTAGRAM = "like-instagram/";
    public static final String KEY_GET_LIKE_INSTAGRAM = "like-foto-instagram/{id}";



}
