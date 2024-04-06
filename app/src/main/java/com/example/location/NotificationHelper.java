package com.example.location;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Random;

public class NotificationHelper extends ContextWrapper {

    private static final String CHANNEL_ID = "Notification123";

    private static final String CHANNEL_NAME = "Geofence Notification";

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
            Log.d(TAG, "sendNotification: Creating notification");

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
        );
        channel.enableVibration(true);
        channel.setShowBadge(true);

        NotificationManager manager = getSystemService(NotificationManager.class);
        if (manager != null) {
            manager.createNotificationChannel(channel);
        }
    }

    public void sendHighPriorityNotification(String title, String content, Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.baseline_notifications_24)
                .setContentTitle(title)
                .setContentText(content)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.baseline_notifications_24))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        if (ActivityCompat.checkSelfPermission(this, "com.example.permission.POST_NOTIFICATIONS") != PackageManager.PERMISSION_GRANTED) {
            // Request the permission if not granted
//            ActivityCompat.requestPermissions(this, new String[]{"com.example.permission.POST_NOTIFICATIONS"}, 1);
        }
        NotificationManagerCompat.from(this).notify(generateNotificationId(), builder.build());
    }

    private int generateNotificationId() {
        return new Random().nextInt();
    }
}