package com.neo.media.Service;

import android.app.PendingIntent;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.util.ArrayMap;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.neo.media.Activity.ActivityMainHome;
import com.neo.media.R;

import java.util.Map;

/**
 * Created by QQ on 8/29/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static int ONGOING_NOTIFICATION_ID = 76;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getNotification() != null) {
            hienThiThongBao( remoteMessage.getFrom(), remoteMessage.getMessageType());
        } else{
           // hienThiThongBao( remoteMessage.getFrom(), remoteMessage.getMessageType());
            Map<String, String> mMap = new ArrayMap<>();
            mMap = remoteMessage.getData();
            hienThiThongBao(mMap);

        }

    }

    private void hienThiThongBao(Map<String, String> mMap) {
        Intent intent = new Intent(this, ActivityMainHome.class);
      //  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("subtype", mMap.get("subtype"));
        intent.putExtra("image", mMap.get("image"));
        intent.putExtra("type", mMap.get("type"));
        intent.putExtra("id", mMap.get("id"));
        intent.putExtra("idsinger", mMap.get("idsinger"));
        intent.putExtra("title", mMap.get("title"));
        String title = mMap.get("title");
      //  Intent intent = new Intent(this, ActivityNotifycation.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Ringtunes")
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(title))
                .setContentText(title)
                .setSound(Uri.parse("android.resource://"
                        + getApplicationContext().getPackageName() + "/"
                        + R.raw.notifi))
                //.setDefaults(NotificationCompat.DEFAULT_SOUND)
                .setContentIntent(pendingIntent);
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(100, builder.build());
    }

    private void hienThiThongBao(String title, String body) {
        int number_notifycation =0;
       // String id = mMap.get("id");
      //  String idsinger = mMap.get("idsinger");
        Intent intent = new Intent(this, ActivityMainHome.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
      //  intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        ringtone();
        //NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ringtunes_final)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(title))
                .setSound(Uri.parse("android.resource://"
                        + getApplicationContext().getPackageName() + "/"
                        + R.raw.notifi))
                .setNumber(++number_notifycation)
                .setContentIntent(pendingIntent);
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
    //    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(100, builder.build());
    }

    /*private void hienThiThongBao(Map<String, String> mMap) {
        hienThiThongBao(mMap);
    }*/
    public void ringtone(){
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
