package com.example.location;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateNewPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_password);
        Button btn = findViewById(R.id.done2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myInt = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(myInt);

                Toast.makeText(CreateNewPassword.this, "Password Added Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}