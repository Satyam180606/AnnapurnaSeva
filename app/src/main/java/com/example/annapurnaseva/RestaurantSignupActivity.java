package com.example.annapurnaseva;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RestaurantSignupActivity extends AppCompatActivity {

    private EditText etRestaurantName, etFssaiCode, etLocation, etUsername, etPassword;
    private Button btnSignUp;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_signup);

        etRestaurantName = findViewById(R.id.etRestaurantName);
        etFssaiCode = findViewById(R.id.etFssaiCode);
        etLocation = findViewById(R.id.etLocation);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnSignUp = findViewById(R.id.btnSignUp);

        db = AppDatabase.getDatabase(getApplicationContext());

        btnSignUp.setOnClickListener(v -> {
            final String restaurantName = etRestaurantName.getText().toString().trim();
            final String fssaiCode = etFssaiCode.getText().toString().trim();
            final String location = etLocation.getText().toString().trim();
            final String username = etUsername.getText().toString().trim();
            final String password = etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(restaurantName) || TextUtils.isEmpty(fssaiCode)
                    || TextUtils.isEmpty(location) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(RestaurantSignupActivity.this, "Please fill all fields", Toast.LENGTH_LONG).show();
                return;
            }

            Restaurant restaurant = new Restaurant();
            restaurant.setRestaurantName(restaurantName);
            restaurant.setFssaiCode(fssaiCode);
            restaurant.setLocation(location);
            restaurant.setUsername(username);
            restaurant.setPassword(password);

            new Thread(() -> {
                long id = db.restaurantDao().insertRestaurant(restaurant);
                runOnUiThread(() -> {
                    Toast.makeText(RestaurantSignupActivity.this, "Sign Up Successful. Please log in.", Toast.LENGTH_LONG).show();
                    finish();
                });
            }).start();
        });
    }
}