package com.example.location;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WhomToAdd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whom_to_add);

        TextView btn = findViewById(R.id.Child);
        TextView btn1 = findViewById(R.id.family);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myInt = new Intent(getApplicationContext(), ChildInformation.class);
                startActivity(myInt);

                Toast.makeText(WhomToAdd.this, "Just a few Steps for protecting your Privacy", Toast.LENGTH_SHORT).show();
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myInt = new Intent(getApplicationContext(), FamilyMemberInformation.class);
                startActivity(myInt);
                Toast.makeText(WhomToAdd.this, "Just a few Steps for protecting your Privacy", Toast.LENGTH_SHORT).show();
            }
        });
    }
}