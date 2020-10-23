package com.example.wgutermmanager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import static android.support.v4.content.ContextCompat.getSystemService;

public class AlertReceiver extends BroadcastReceiver {

    static int notificationID;
    String channel_id = "this";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");

        createNotificationChannel(context, channel_id);

        Notification n = new NotificationCompat.Builder(context, channel_id)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("WGU Notification")
                .setContentText(intent.getStringExtra("key"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true).build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID++, n);
    }

    private void createNotificationChannel(Context context, String CHANNEL_ID) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notification Channel";
            String description = "Notification description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
        }
    }
}
