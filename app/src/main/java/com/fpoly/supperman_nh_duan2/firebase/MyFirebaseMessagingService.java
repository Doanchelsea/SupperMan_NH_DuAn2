package com.fpoly.supperman_nh_duan2.firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.fpoly.supperman_nh_duan2.R;
import com.fpoly.supperman_nh_duan2.api.eventbus.CancelEvent;
import com.fpoly.supperman_nh_duan2.api.eventbus.MainEvent;
import com.fpoly.supperman_nh_duan2.ui.main.MainActivity;
import com.fpoly.supperman_nh_duan2.untils.Constans;
import com.fpoly.supperman_nh_duan2.untils.StringUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    public MyFirebaseMessagingService() {

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Map<String, String> data = remoteMessage.getData();

        if (data == null) {
            return;
        }
        String id = data.get(Constans.ID);
        String code = data.get(Constans.CODE);
        String message = data.get(Constans.MESSAGE);
        String name = data.get(Constans.NAME);

        if (StringUtils.isEmpty(code)) {
            return;
        }
        if (StringUtils.isEmpty(message)) {
            return;
        }
        if (StringUtils.isEmpty(name)) {
            return;
        }

        if (code.equals(Constans.HOUSTSUCCESS)){
            EventBus.getDefault().post(new MainEvent());
            showNotification(name+" "+message);
        }
        if (code.equals(Constans.HOUSTERROR)){
            if (StringUtils.isEmpty(id)){
                return;
            }
            EventBus.getDefault().post(new CancelEvent(id));
            showNotification(name+" "+message);
        }


    }

    private void showNotification(String message) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.project_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.logoapp)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logoapp))
                        .setContentTitle(getString(R.string.app_name))
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setPriority(NotificationManager.IMPORTANCE_HIGH);
//                        .addAction(new NotificationCompat.Action(
//                                android.R.drawable.sym_call_missed,
//                                "Cancel",
//                                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)))
//                        .addAction(new NotificationCompat.Action(
//                                android.R.drawable.sym_call_outgoing,
//                                "OK",
//                                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)));

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);

            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notificationBuilder.build());
    }
}
