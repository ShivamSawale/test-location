package com.example.location;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;



public class MainActivity extends FragmentActivity implements GoogleMap.OnMapLongClickListener , GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener , OnMapReadyCallback{
    private final int FINE_PERMISSION_CODE = 1;
    private GoogleMap mMap;
    private GeofencingClient geofencingClient;

    private GeofenceHelper geofenceHelper;

    Location currentLocation;
    private static final int BACKGROUND_LOCATION_ACCESS_REQUEST_CODE = 1001;
    private float GEOFENCE_RADIUS = 200f;
    private String GEOFENCE_ID = "Some Geofence_ID";
    FusedLocationProviderClient fusedLocationProviderClient;
    private GeofenceBroadcastReceiver geofenceBroadcastReceiver;

    public static final String GEOFENCE_ACTION = "com.example.location.GEOFENCE_ACTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        geofencingClient = LocationServices.getGeofencingClient(this);

        geofenceHelper = new GeofenceHelper(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        // This is new on 12/12/2023 up to intentfilter
        // Initialize and register the GeofenceReceiver
        geofenceBroadcastReceiver = new GeofenceBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(GEOFENCE_ACTION);
        registerReceiver(geofenceBroadcastReceiver, intentFilter);



        // This is for update the location every second
        Intent serviceIntent = new Intent(MainActivity.this, LocationUpdateService.class);
        startService(serviceIntent);



    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},FINE_PERMISSION_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if (location != null){
                currentLocation = location;
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(MainActivity.this);
            }
        });
    }
    /**
     *
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        // Add a marker in Sydney and move the camera
//        LatLng MyLocation = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
//        mMap.addMarker(new MarkerOptions().position(MyLocation).title("My Location"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(MyLocation));
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(MyLocation,16f));
//
//        mMap.setMapType( mMap.getMapType() ==
//                GoogleMap.MAP_TYPE_NORMAL ?
//                GoogleMap.MAP_TYPE_SATELLITE :
//                GoogleMap.MAP_TYPE_NORMAL);
//        mMap.setOnMapLongClickListener(this);
//
//    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.setMapType( mMap.getMapType() ==
                GoogleMap.MAP_TYPE_NORMAL ?
                GoogleMap.MAP_TYPE_SATELLITE :
                GoogleMap.MAP_TYPE_NORMAL);
        // TODO: Before enabling the My Location layer, you must request
        // location permission from the user. This sample does not include
        // a request for location permission.
        map.setMyLocationEnabled(true);
        map.setOnMyLocationButtonClickListener(this);
        map.setOnMyLocationClickListener(this);
        map.setOnMapLongClickListener(this);
    }
    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    @SuppressLint("MissingPermission")
    private void enableMyLocation() {
        // 1. Check if permissions are granted, if so, enable the my location layer
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            return;
        }
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT)
                .show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }



    public void onMapLongClick(@NonNull LatLng latLng) {


        if (Build.VERSION.SDK_INT >= 29) {
            //We need background permission
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_BACKGROUND_LOCATION) ==PackageManager.PERMISSION_GRANTED) {
                handelMapLongClick(latLng);
            }else {
                if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_BACKGROUND_LOCATION)){
                    // We show a dialogue and ask for permission
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION},BACKGROUND_LOCATION_ACCESS_REQUEST_CODE);
                }else{
                    ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION},BACKGROUND_LOCATION_ACCESS_REQUEST_CODE);
                }
            }
        } else {

            handelMapLongClick(latLng);
        }
    }
    private void handelMapLongClick(LatLng latLng){
        //  mMap.clear();  /** this is to create only one geofence */
        addMarker(latLng);
        addCircle(latLng, GEOFENCE_RADIUS);
        addGeofence(latLng, GEOFENCE_RADIUS);

    }
    private void addGeofence(LatLng latLng, float radius) {
        Geofence geofence = geofenceHelper.getGeofence(GEOFENCE_ID, latLng, radius, Geofence.GEOFENCE_TRANSITION_ENTER |
                Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT);
        GeofencingRequest geofencingRequest = geofenceHelper.geofencingRequest(geofence);
        PendingIntent pendingIntent = geofenceHelper.getPendingIntent(GEOFENCE_ACTION);

        Intent serviceIntent = new Intent(this, GeofenceService.class);
        this.startService(serviceIntent);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},FINE_PERMISSION_CODE);
            return;
        }

        geofencingClient.addGeofences(geofencingRequest, pendingIntent)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "onSuccess: Geofence Added...");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        String errorMessage = geofenceHelper.getErrorString(e);
                        Log.d(TAG,"onFailure:" + errorMessage);
                    }
                });
    }
    private void checkRegisteredGeofences() {
        geofenceHelper.logRegisteredGeofences();
    }
    private void addMarker(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions().position(latLng);
        mMap.addMarker(markerOptions);
    }
    private void addCircle(LatLng latLng , float radius ) {
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(latLng);
        circleOptions.radius(radius);
        circleOptions.strokeColor(Color.argb(255 ,255 , 0 , 0));
        circleOptions.fillColor(Color.argb(64 ,255 , 0 , 0));
        circleOptions.strokeWidth(4);
        mMap.addCircle(circleOptions);
    }


    // This is new on 12/12/2023
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister the GeofenceReceiver when the activity is destroyed
        if (geofenceBroadcastReceiver != null) {
            unregisterReceiver(geofenceBroadcastReceiver);
        }
    }
}