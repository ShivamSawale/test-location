package com.example.location;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddDevice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_device);

        TextView btn = findViewById(R.id.Smartphone);
        TextView btn1 = findViewById(R.id.Smartwatch);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myInt = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(myInt);

                Toast.makeText(AddDevice.this, "Your Smart Phone is Successfully Added", Toast.LENGTH_SHORT).show();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myInt = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(myInt);

                Toast.makeText(AddDevice.this, "Your Smart Watch is Successfully Added", Toast.LENGTH_SHORT).show();
            }
        });
    }
}