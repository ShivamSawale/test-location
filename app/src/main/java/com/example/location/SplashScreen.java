package com.example.location;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        createNotificationChannel();
        auth = FirebaseAuth.getInstance();

        authStateListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();

            if (user != null) {
                // The user is already signed in
                navigateToMainActivity();
            } else {
                // If the user is not signed in
                navigateToNewUser();
            }
        };

        // Add auth state listener
        auth.addAuthStateListener(authStateListener);

        // Delayed redirection after a short period (adjust as needed)
        new Handler(Looper.getMainLooper()).postDelayed(this::handleRedirection, 1000);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "Notification123"; // Choose a unique channel ID
            CharSequence channelName = "Geofence Notification"; // The user-visible name of the channel
            int importance = NotificationManager.IMPORTANCE_HIGH; // Set the importance level

            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);

            // Configure the channel's behavior
            channel.setDescription("Enter || Exit || Dwell");
            channel.enableLights(true);
            channel.enableVibration(true);

            // Register the channel with the system
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }


    private void handleRedirection() {
        // Remove auth state listener to prevent further callbacks
        auth.removeAuthStateListener(authStateListener);

        // Decide where to navigate based on the authentication state
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            // The user is already signed in
            navigateToMainActivity();
        } else {
            // If the user is not signed in
            navigateToNewUser();
        }
    }

    private void navigateToMainActivity() {
        Intent mapsIntent = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(mapsIntent);
        finish(); // Finish the splash screen activity
    }

    private void navigateToNewUser() {
        Intent newUserIntent = new Intent(SplashScreen.this, NewUser.class);
        startActivity(newUserIntent);
        finish(); // Finish the splash screen activity
    }
}
