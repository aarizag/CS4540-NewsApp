package com.example.adryel.newsapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

public class NotificationUtils {
    private static final int CANCEL_AUTO_SYNC_ID = 1437;
    private static final String NOTIFICATION_CHANNEL_ID = "notification_channel";


    public static void remindUserBecauseCharging(Context context)    {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.main_notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context,NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_android_black_24dp)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_content))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.notification_content)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        notificationManager.notify(CANCEL_AUTO_SYNC_ID, notificationBuilder.build());
    }

    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, NewsSyncIntentService.class);
        return PendingIntent.getActivity(
                context,
                CANCEL_AUTO_SYNC_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static Bitmap largeIcon(Context context) {
        Resources res = context.getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.drawable.ic_android_black_24dp);
        return largeIcon;
    }
}
