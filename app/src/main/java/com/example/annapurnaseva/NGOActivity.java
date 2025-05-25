package com.example.annapurnaseva;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class NGOActivity extends AppCompatActivity {

    private AppDatabase db;
    private TextView tvFoodList;
    private Button btnSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo);

        db = AppDatabase.getDatabase(getApplicationContext());
        tvFoodList = findViewById(R.id.tvFoodList);
        btnSignOut = findViewById(R.id.btnSignOut);

        btnSignOut.setOnClickListener(v -> {
            SharedPreferences sp = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
            sp.edit().clear().apply();
            Intent intent = new Intent(NGOActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(() -> {
            List<FoodItem> foodItems = db.foodItemDao().getAllFoodItems();
            final StringBuilder sb = new StringBuilder();
            for (FoodItem item : foodItems) {
                Restaurant res = db.restaurantDao().getRestaurantById(item.getRestaurantId());
                sb.append(item.toString());
                if (res != null) {
                    sb.append("\nPosted by: ").append(res.getRestaurantName())
                            .append(", Address: ").append(res.getLocation());
                }
                sb.append("\n\n");
            }
            runOnUiThread(() -> tvFoodList.setText(sb.toString()));
        }).start();
    }
}