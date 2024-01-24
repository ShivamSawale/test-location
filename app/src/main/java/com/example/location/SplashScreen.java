package com.example.location;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity {

    public static final long SPLASH_DURATION = 5000; // Duration of the splash screen in milliseco

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), NewUser.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DURATION);

    }
}