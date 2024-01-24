package com.example.location;







// This is for Firebase collection of data




//import android.annotation.SuppressLint;
//import android.app.Service;
//import android.content.Intent;
//import android.location.Location;
//import android.os.Bundle;
//import android.os.IBinder;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationCallback;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationResult;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.location.Priority;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//public class LocationUpdateService extends Service {
//
//    private static final String TAG = "LocationUpdateService";
//    private FusedLocationProviderClient fusedLocationClient;
//    private LocationCallback locationCallback;
//    private FirebaseFirestore db;
//    private LocationRequest locationRequest;
//
//    @SuppressLint("MissingPermission")
//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//        db = FirebaseFirestore.getInstance();
//        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference locationsRef = database.getReference("users").child("locations");
//
//
//        // Setting up the location request with a shorter interval and distance
////        LocationRequest locationRequest = new LocationRequest();
////        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
////        locationRequest.setInterval(1000); // 1 second
////        locationRequest.setFastestInterval(500); // 0.5 seconds
////        locationRequest.setSmallestDisplacement(5f); // 5 meters
//
//        locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 100)
//                .setWaitForAccurateLocation(false)
//                .setMinUpdateIntervalMillis(2000)
//                .setMaxUpdateDelayMillis(100)
//                .build();

//        locationCallback = new LocationCallback() {
//            @Override
//            public void onLocationResult(@NonNull LocationResult locationResult) {
//                super.onLocationResult(locationResult);
//                for (Location location : locationResult.getLocations()) {
//                    Log.d(TAG, "Location Updated: " + location.getLatitude() + ", " + location.getLongitude());
//
//                    // Update Firestore with the new location
//                    updateLocationInFirestore(location.getLatitude(), location.getLongitude());
//                }
//            }
//        };
//
//        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
//    }
//
//    // Method to update location data in Firestore
//    private void updateLocationInFirestore(double latitude, double longitude) {
//        // Assume userId is the unique identifier for each user
//        String userId = "user123";
//
//        // Create a new location model
//        LocationModel location = new LocationModel();
//        location.setLatitude(latitude);
//        location.setLongitude(longitude);
//        location.setTimestamp(System.currentTimeMillis());
//
//        // Add the location to Firestore
//        db.collection("users")
//                .document(userId)
//                .collection("locations")
//                .add(location)
//                .addOnSuccessListener(documentReference -> {
//                    Log.d(TAG, "Location added with ID: " + documentReference.getId());
//                })
//                .addOnFailureListener(e -> {
//                    Log.e(TAG, "Error adding location", e);
//                });
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (fusedLocationClient != null && locationCallback != null) {
//            fusedLocationClient.removeLocationUpdates(locationCallback);
//        }
//    }
//    private void updateLocationInRealtimeDatabase(double latitude, double longitude) {
//        // Assume userId is the unique identifier for each user
//        String userId = "user123";
//
//        // Create a new location model
//        LocationModel location = new LocationModel();
//        location.setLatitude(latitude);
//        location.setLongitude(longitude);
//        location.setTimestamp(System.currentTimeMillis());
//
//        // Add the location to Realtime Database
//        locationsRef.child(userId).push().setValue(location, (databaseError, databaseReference) -> {
//            if (databaseError == null) {
//                Log.d(TAG, "Location added successfully");
//            } else {
//                Log.e(TAG, "Error adding location", databaseError.toException());
//            }
//        });
//    }
//}


import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LocationUpdateService extends Service {

    private static final String TAG = "LocationUpdateService";
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;

    private LocationRequest locationRequest;
    private DatabaseReference locationsRef;

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
       // locationsRef = database.getReference("users").child("locations");
        locationsRef = database.getReference("users");
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Setting up the location request with a shorter interval and distance
        locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 100)
                .setWaitForAccurateLocation(false)
                .setMinUpdateIntervalMillis(2000)
                .setMaxUpdateDelayMillis(100)
                .build();

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                for (Location location : locationResult.getLocations()) {
                    Log.d(TAG, "Location Updated: " + location.getLatitude() + ", " + location.getLongitude());

                    // Update Realtime Database with the new location
//                    updateLocationInRealtimeDatabase(location.getLatitude(), location.getLongitude());
                }
            }
        };

        // Request location updates
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    // Method to update location data in Realtime Database
//    private void updateLocationInRealtimeDatabase(double latitude, double longitude) {
//        // Assume userId is the unique identifier for each user
////        String userId = "user123";
////
////        // Create a new location model with the updated values
////        LocationModel location = new LocationModel(latitude, longitude, System.currentTimeMillis());
////
////        // Add the location to Realtime Database
////        locationsRef.child(userId).push().setValue(location, (databaseError, databaseReference) -> {
////            if (databaseError == null) {
////                Log.d(TAG, "Location added successfully");
////            } else {
////                Log.e(TAG, "Error adding location", databaseError.toException());
////            }
////        });
//    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (fusedLocationClient != null && locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }
}
