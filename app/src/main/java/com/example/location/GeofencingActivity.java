package com.example.location;

//import android.app.PendingIntent;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//
//import com.google.android.gms.common.api.ApiException;
//import com.google.android.gms.location.Geofence;
//import com.google.android.gms.location.GeofenceStatusCodes;
//import com.google.android.gms.location.GeofencingClient;
//import com.google.android.gms.location.GeofencingRequest;
//import com.google.android.gms.location.LocationServices;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class GeofencingActivity extends AppCompatActivity {
//
//    private GeofencingClient geofencingClient;
//    private PendingIntent geofencePendingIntent;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_geofencing);
//
//        geofencingClient = LocationServices.getGeofencingClient(this);
//
//        // Create a list of geofences to monitor
//        List<Geofence> geofenceList = new ArrayList<>();
//        // Populate geofenceList with Geofence objects
//
//        // Create a geofence request
//        GeofencingRequest geofencingRequest = new GeofencingRequest.Builder()
//                .addGeofences(geofenceList)
//                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
//                .build();
//
//        // Create an intent for the geofence transition event
//        Intent intent = new Intent(this, GeofenceBroadcastReceiver.class);
//        geofencePendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        // Add geofences
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        geofencingClient.addGeofences(geofencingRequest, geofencePendingIntent)
//                .addOnSuccessListener(aVoid -> {
//                    // Geofences added successfully
//                })
//                .addOnFailureListener(e -> {
//                    if (e instanceof ApiException) {
//                        ApiException apiException = (ApiException) e;
//                        int statusCode = apiException.getStatusCode();
//                        if (statusCode == GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE) {
//                            // Handle GEOFENCE_NOT_AVAILABLE error
//                            showToast("Geofencing is not available on this device.");
//                        } else if (statusCode == GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES) {
//                            // Handle GEOFENCE_TOO_MANY_GEOFENCES error
//                            showToast("Too many geofences are being monitored.");
//                        } else if (statusCode == GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS) {
//                            // Handle GEOFENCE_TOO_MANY_PENDING_INTENTS error
//                            showToast("Too many geofence pending intents.");
//                        }
//                        else {
//                            // Handle other errors
//                            showToast("An error occurred: " + apiException.getMessage());
//                        }
//                    } else {
//                        // Handle non-ApiException errors
//                        showToast("An error occurred: " + e.getMessage());
//                    }
//                });
//
//
//    }
//
//    private void showToast(String s) {
//    }
//}

//
//import android.Manifest;
//import android.app.PendingIntent;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//
//import com.google.android.gms.common.api.ApiException;
//import com.google.android.gms.location.Geofence;
//import com.google.android.gms.location.GeofenceStatusCodes;
//import com.google.android.gms.location.GeofencingClient;
//import com.google.android.gms.location.GeofencingRequest;
//import com.google.android.gms.location.LocationServices;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class GeofencingActivity extends AppCompatActivity {
//
//    private GeofencingClient geofencingClient;
//    private PendingIntent geofencePendingIntent;
//    private GeofencingRequest geofencingRequest;  // Declare geofencingRequest as a class variable
//    private static final int YOUR_PERMISSION_REQUEST_CODE = 1001; // Replace with an appropriate value
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_geofencing);
//
//        geofencingClient = LocationServices.getGeofencingClient(this);
//
//        // Create a geofence list
//        List<Geofence> geofenceList = new ArrayList<>();
//
//        // Example geofence parameters
//        String geofenceId = "unique_geofence_id";
//        double latitude = 37.7749;
//        double longitude = -122.4194;
//        float radius = 100.0f;
//        int transitionTypes = Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT;
//
//        // Create a geofence object
//        Geofence geofence = new Geofence.Builder()
//                .setRequestId(geofenceId)
//                .setCircularRegion(latitude, longitude, radius)
//                .setExpirationDuration(Geofence.NEVER_EXPIRE)
//                .setTransitionTypes(transitionTypes)
//                .build();
//
//        // Add the geofence to the list
//        geofenceList.add(geofence);
//
//        // Create a geofence request
//        geofencingRequest = new GeofencingRequest.Builder()
//                .addGeofences(geofenceList)
//                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
//                .build();
//
//        // Create an intent for the geofence transition event
//        Intent intent = new Intent(this, GeofenceBroadcastReceiver.class);
//        geofencePendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        // Add geofences
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, YOUR_PERMISSION_REQUEST_CODE);
//        } else {
//            addGeofences();
//        }
//    }
//
//    private void addGeofences() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        geofencingClient.addGeofences(geofencingRequest, geofencePendingIntent)
//                .addOnSuccessListener(aVoid -> {
//                    // Geofences added successfully
//                })
//                .addOnFailureListener(e -> {
//                    handleGeofenceError(e);
//                });
//    }
//
//    private void handleGeofenceError(Exception e) {
//        if (e instanceof ApiException) {
//            ApiException apiException = (ApiException) e;
//            int statusCode = apiException.getStatusCode();
//            // Handle different status codes
//            if (statusCode == GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE) {
//                showToast("Geofencing is not available on this device.");
//            } else if (statusCode == GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES) {
//                showToast("Too many geofences are being monitored.");
//            } else if (statusCode == GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS) {
//                showToast("Too many geofence pending intents.");
//            } else {
//                showToast("An error occurred: " + apiException.getMessage());
//            }
//        } else {
//            showToast("An error occurred: " + e.getMessage());
//        }
//    }
//
//    private void showToast(String message) {
//        // Implement your showToast method
//
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == YOUR_PERMISSION_REQUEST_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission granted, add geofences
//                addGeofences();
//            } else {
//                // Permission denied, handle accordingly
//
//            }
//        }
//    }
//}


import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

public class GeofencingActivity extends AppCompatActivity {

    FusedLocationProviderClient fusedLocationProviderClient;
    private GeofencingClient geofencingClient;
    private PendingIntent geofencePendingIntent;
    private GeofencingRequest geofencingRequest;
    private static final int YOUR_PERMISSION_REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geofencing);

        geofencingClient = LocationServices.getGeofencingClient(this);

        // Create a geofence list
        List<Geofence> geofenceList = new ArrayList<>();

        // Retrieve user's current location using FusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                            double latitude = location.getLatitude(); // Get latitude from location
                            double longitude = location.getLongitude(); // Get longitude from location

                            // Example geofence parameters
                            String geofenceId = "unique_geofence_id";
                            float radius = 100.0f;
                            int transitionTypes = Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT;

                            // Create a geofence object using user's location
                            Geofence geofence = new Geofence.Builder()
                                    .setRequestId(geofenceId)
                                    .setCircularRegion(latitude, longitude, radius)
                                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                                    .setTransitionTypes(transitionTypes)
                                    .build();

                            // Add the geofence to the list
                            geofenceList.add(geofence);

                            // Create a geofence request
                            geofencingRequest = new GeofencingRequest.Builder()
                                    .addGeofences(geofenceList)
                                    .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                                    .build();

                            // Create an intent for the geofence transition event
                            Intent intent = new Intent(this, GeofenceBroadcastReceiver.class);
                            geofencePendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                            // Add geofences
                            addGeofences();
                        }
                    });
        } else {
            // Handle the case where location permission is not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, YOUR_PERMISSION_REQUEST_CODE);
        }
    }

    private void addGeofences() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        geofencingClient.addGeofences(geofencingRequest, geofencePendingIntent)
                .addOnSuccessListener(aVoid -> {
                    // Geofences added successfully
                })
                .addOnFailureListener(e -> {
                    handleGeofenceError(e);
                });
    }

    private void handleGeofenceError(Exception e) {
        // Handle geofence error
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == YOUR_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, add geofences
                addGeofences();
            } else {
                // Permission denied, handle accordingly
            }
        }
    }
}
