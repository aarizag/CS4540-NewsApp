package com.example.adryel.newsapp;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

public class NewsSyncIntentService extends IntentService {
    public NewsSyncIntentService() {
        super("NewsSyncIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if ("dismiss-notification".equals(intent.getAction()))
            clearAllNotifications(this);
    }

    public static void clearAllNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
}
