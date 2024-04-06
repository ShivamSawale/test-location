package com.example.location;



import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "GeofenceBroadcastReceiver";

    public static final String GEOFENCE_ACTION = "com.example.location.GEOFENCE_ACTION";


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Geofence triggered!");

        // Check if the intent is null
        if (intent == null) {
            Log.e(TAG, "Received null intent");
            return;
        }

        // Extract geofence transition details from the broadcast intent
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        // Check if geofencingEvent is null
        if (geofencingEvent == null) {
            Log.e(TAG, "GeofencingEvent is null");
            return;
        }

        if (geofencingEvent.hasError()) {
            Log.e(TAG, "GeofencingEvent error: " + geofencingEvent.getErrorCode());
            return;
        }

        // Get the geofence transition type
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        // Check if the transition type is ENTER, EXIT, or DWELL
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT ||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_DWELL) {

            // Perform your desired actions based on the geofence transition
            if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
                Log.d(TAG, "Entered geofence");

                // Show a notification for entering the geofence
                sendNotification(context, "Entered Geofence", "You have entered the geofence.", MainActivity.class);

            } else if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
                Log.d(TAG, "Exited geofence");

                // Show a notification for exiting the geofence
                sendNotification(context, "Exited Geofence", "You have exited the geofence.", MainActivity.class);

                // Show a notification for already in the geofence
            } else if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_DWELL) {
                Log.d(TAG, "Dwelling in geofence");

                // Show a notification for dwelling in the geofence
                sendNotification(context, "Dwelling in Geofence", "You are dwelling in the geofence.", MainActivity.class);
            }
        }
    }

    private void sendNotification(Context context, String title, String body, Class<?> targetActivity) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        notificationHelper.sendHighPriorityNotification(title, body, targetActivity);
    }
}