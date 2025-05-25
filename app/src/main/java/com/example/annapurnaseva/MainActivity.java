package com.example.annapurnaseva;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnRestaurant, btnNGO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRestaurant = findViewById(R.id.btnRestaurant);
        btnNGO = findViewById(R.id.btnNGO);

        btnRestaurant.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, RestaurantActivity.class);
            startActivity(intent);
        });

        btnNGO.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NGOActivity.class);
            startActivity(intent);
        });
    }
}