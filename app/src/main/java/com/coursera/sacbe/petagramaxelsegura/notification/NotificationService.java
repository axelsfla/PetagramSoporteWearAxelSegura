package com.coursera.sacbe.petagramaxelsegura.notification;


import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.NotificationCompat.WearableExtender;
import android.view.Gravity;

import com.coursera.sacbe.petagramaxelsegura.MainActivity;
import com.coursera.sacbe.petagramaxelsegura.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by axel on 26/10/2016.
 */

public class NotificationService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    public static final int NOTIFICATION_ID = 001;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //super.onMessageReceived(remoteMessage);
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        Object obj = remoteMessage.getData().get("text");
        String message;

        if (obj != null) {
            try {
                message = obj.toString();
            } catch (Exception e) {
                message = "";
                e.printStackTrace();
            }
        }else{
           message = remoteMessage.getNotification().getBody();
        }

        obj = remoteMessage.getData().get("title");
        String title;

        if (obj != null) {
            try {
                title = obj.toString();
            } catch (Exception e) {
                title = "";
                e.printStackTrace();
            }
        }else{
            title = "Notificación";
        }

        obj = remoteMessage.getData().get("pViewPage");
        String pViewPage;

        if (obj != null) {
            try {
                pViewPage = obj.toString();
            } catch (Exception e) {
                pViewPage = "0";
                e.printStackTrace();
            }
        }else{
            pViewPage = "0";
        }

        obj = remoteMessage.getData().get(getString(R.string.id_instagram_from));
        String pIdInstagramFrom;

        if (obj != null) {
            try {
                pIdInstagramFrom = obj.toString();
            } catch (Exception e) {
                pIdInstagramFrom = "";
                e.printStackTrace();
            }
        }else{
            pIdInstagramFrom = "";
        }

        obj = remoteMessage.getData().get(getString(R.string.id_usario_instagram_from));
        String pIdUsuarioInstagramFrom;

        if (obj != null) {
            try {
                pIdUsuarioInstagramFrom = obj.toString();
            } catch (Exception e) {
                pIdUsuarioInstagramFrom = "";
                e.printStackTrace();
            }
        }else{
            pIdUsuarioInstagramFrom = "";
        }


        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + message);

            Intent i = new Intent(); //this, MainActivity.class
            i.setAction(getString(R.string.toque_animal));
            i.putExtra(getResources().getString(R.string.pViewPage), Integer.parseInt(pViewPage));

            Intent iVerPerfil = new Intent(); //this, MainActivity.class
            iVerPerfil.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            iVerPerfil.setAction(getString(R.string.ver_perfil));

            Intent iFollowUnFollow = new Intent();
            iFollowUnFollow.putExtra(getResources().getString(R.string.pIdInstagramFrom), pIdInstagramFrom);
            iFollowUnFollow.putExtra(getResources().getString(R.string.pUsuarioInstagramFrom), pIdUsuarioInstagramFrom);
            iFollowUnFollow.setAction(getString(R.string.follow_unfollow));

            Intent iVerUsuario = new Intent();
            iVerUsuario.putExtra(getResources().getString(R.string.pIdInstagramFrom), pIdInstagramFrom);
            iVerUsuario.putExtra(getResources().getString(R.string.pUsuarioInstagramFrom), pIdUsuarioInstagramFrom);
            iVerUsuario.setAction(getString(R.string.ver_usuario));

            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT); //getActivity(this, 0, i, PendingIntent.FLAG_ONE_SHOT);
            PendingIntent pendingIntentVerPerfil = PendingIntent.getBroadcast(this, 0, iVerPerfil, PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent pendingIntentFU = PendingIntent.getBroadcast(this, 0, iFollowUnFollow, PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent pendingIntentVerUsuario = PendingIntent.getBroadcast(this, 0, iVerUsuario, PendingIntent.FLAG_UPDATE_CURRENT);

            Uri sonido = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.drawable.ic_full_poke,getString(R.string.texto_accion_toque), pendingIntent)
                    .build();

            NotificationCompat.Action actionPerfil = new NotificationCompat.Action.Builder(R.drawable.ic_full_footprint, getString(R.string.texto_accion_perfil), pendingIntentVerPerfil)
                    .build();

            NotificationCompat.Action actionFollowUnF = new NotificationCompat.Action.Builder(R.drawable.ic_full_followunfollow, getString(R.string.texto_accion_follow_unfollow), pendingIntentFU)
                    .build();

            NotificationCompat.Action actionVerUsuario = new NotificationCompat.Action.Builder(R.drawable.ic_pet_contacts, getString(R.string.texto_accion_ver_usuario), pendingIntentVerUsuario)
                    .build();

            NotificationCompat.WearableExtender wearableExtender = new NotificationCompat.WearableExtender()
                    .setHintHideIcon(true)
                    .setBackground(BitmapFactory.decodeResource(getResources(),R.drawable.bk_androidwear_notification))
                    .setGravity(Gravity.CENTER_VERTICAL)
                    ;

            NotificationCompat.Builder notificacion = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.unam_pumas48x48)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setSound(sonido)
                    .setContentIntent(pendingIntent)
                    .extend(wearableExtender.addAction(action))
                    .extend(wearableExtender.addAction(actionPerfil))
                    .extend(wearableExtender.addAction(actionFollowUnF))
                    .extend(wearableExtender.addAction(actionVerUsuario))
                    //.addAction(R.drawable.ic_full_poke, getString(R.string.texto_accion_toque), pendingIntent)
                    ;

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this); //(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(NOTIFICATION_ID, notificacion.build());

        }



    }
}