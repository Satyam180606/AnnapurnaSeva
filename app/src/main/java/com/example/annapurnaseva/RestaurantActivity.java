package com.example.annapurnaseva;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RestaurantActivity extends AppCompatActivity {

    private AppDatabase db;
    private EditText etFoodName, etQuantity, etExpiry;
    private Button btnPostFood, btnSignOut;
    private TextView tvStatus;
    private int restaurantId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        // Retrieve restaurantId passed from LoginActivity
        restaurantId = getIntent().getIntExtra("restaurantId", -1);
        if (restaurantId == -1) {
            Toast.makeText(this, "Error: Restaurant ID not found.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        db = AppDatabase.getDatabase(getApplicationContext());
        etFoodName = findViewById(R.id.etFoodName);
        etQuantity = findViewById(R.id.etQuantity);
        etExpiry = findViewById(R.id.etExpiry);
        btnPostFood = findViewById(R.id.btnPostFood);
        tvStatus = findViewById(R.id.tvStatus);
        btnSignOut = findViewById(R.id.btnSignOut);

        btnPostFood.setOnClickListener(v -> {
            String foodName = etFoodName.getText().toString().trim();
            String qtyStr = etQuantity.getText().toString().trim();
            String expiry = etExpiry.getText().toString().trim();

            if (TextUtils.isEmpty(foodName) || TextUtils.isEmpty(qtyStr) || TextUtils.isEmpty(expiry)) {
                tvStatus.setText("Please fill all fields.");
                return;
            }

            int quantity;
            try {
                quantity = Integer.parseInt(qtyStr);
            } catch (NumberFormatException e) {
                tvStatus.setText("Invalid quantity.");
                return;
            }

            // Create a FoodItem with the current restaurant's ID
            FoodItem item = new FoodItem(foodName, quantity, expiry, restaurantId);

            new Thread(() -> {
                long newId = db.foodItemDao().insertFoodItem(item);
                runOnUiThread(() -> {
                    tvStatus.setText("Food item posted! New id: " + newId);
                    etFoodName.setText("");
                    etQuantity.setText("");
                    etExpiry.setText("");
                });
            }).start();
        });

        btnSignOut.setOnClickListener(v -> {
            SharedPreferences sp = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
            sp.edit().clear().apply();
            Intent intent = new Intent(RestaurantActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}