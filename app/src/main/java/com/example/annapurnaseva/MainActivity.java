package com.example.annapurnaseva;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_main);
        } catch (Exception e) {
            Log.e(TAG, "Error in setContentView: " + e.getMessage());
            Toast.makeText(this, "Error loading the main screen", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            Button btnRestaurant = findViewById(R.id.btnRestaurant);
            Button btnNGO = findViewById(R.id.btnNGO);

            if (btnRestaurant == null || btnNGO == null) {
                throw new NullPointerException("One or both button references are null");
            }

            btnRestaurant.setOnClickListener(view -> {
                try {
                    Intent intent = new Intent(MainActivity.this, RestaurantActivity.class);
                    // Optionally, pass extra data if needed.
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e(TAG, "Error navigating to RestaurantActivity: " + e.getMessage());
                    Toast.makeText(MainActivity.this, "Error navigating to Restaurant screen", Toast.LENGTH_SHORT).show();
                }
            });

            btnNGO.setOnClickListener(view -> {
                try {
                    Intent intent = new Intent(MainActivity.this, NGOActivity.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e(TAG, "Error navigating to NGOActivity: " + e.getMessage());
                    Toast.makeText(MainActivity.this, "Error navigating to NGO screen", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Error initializing views: " + e.getMessage());
            Toast.makeText(this, "Error initializing main activity views", Toast.LENGTH_LONG).show();
        }
    }
}