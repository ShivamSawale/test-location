package com.example.location;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChildInformation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_information);

        Button btn = findViewById(R.id.done);
        FirebaseDatabase databaseRef = FirebaseDatabase.getInstance();
        DatabaseReference myRef = databaseRef.getReference("message");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Edited by jayesh for storing data
                // Write a message to the database


                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        myRef.setValue("hello");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ChildInformation.this, "error", Toast.LENGTH_SHORT);
                    }
                });




                Toast.makeText(ChildInformation.this, "We are working on the OTP Section", Toast.LENGTH_SHORT).show();

                Intent myInt = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(myInt);
            }
        });
    }
}