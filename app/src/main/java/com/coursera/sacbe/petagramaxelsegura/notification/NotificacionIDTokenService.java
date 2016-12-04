package com.coursera.sacbe.petagramaxelsegura.notification;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by axel on 26/10/2016.
 */

public class NotificacionIDTokenService extends FirebaseInstanceIdService {

    private static final String TAG = "My_Firebase_Token";

    @Override
    public void onTokenRefresh() {
        //super.onTokenRefresh();

        String token = FirebaseInstanceId.getInstance().getToken();
        enviarTokenRegistro(token);
    }

    private void enviarTokenRegistro(String token){

        Log.d(TAG, token);

    }
}