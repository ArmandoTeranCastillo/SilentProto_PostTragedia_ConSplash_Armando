package com.example.silentproto;


import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

public class MClaseiNoti extends NotificationListenerService {

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        cancelAllNotifications();
        cancelNotification(sbn.getKey());

    }

}
