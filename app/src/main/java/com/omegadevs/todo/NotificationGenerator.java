package com.omegadevs.todo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

/**
 * Created by manasvi on 19-05-2016.
 */
public class NotificationGenerator extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        notifyme(context,"Reminder");
    }

    public void notifyme(Context context, String msg){


        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(
                context)
                // Set Icon
                .setSmallIcon(R.drawable.logo)
                // Set Ticker Message
                .setTicker("Task Upcoming!!")
                // Set Title
                .setContentTitle("Task Upcoming..")
                // Set Text
                .setContentText("Open the app to continue...")
                // Add an Action Button below Notification
                .addAction(android.R.drawable.divider_horizontal_textfield, "Action Button", pIntent)
                // Set PendingIntent into Notification
                .setContentIntent(pIntent)
                // Dismiss Notification
                .setAutoCancel(true);

        NotificationManager notificationmanager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Notification n = new Notification(android.R.drawable.ic_dialog_alert,"Task Upcoming", System.currentTimeMillis());
        n.defaults = Notification.DEFAULT_VIBRATE;

    }
}
