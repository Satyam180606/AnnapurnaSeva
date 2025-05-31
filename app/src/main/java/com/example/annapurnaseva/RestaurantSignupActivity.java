package com.example.annapurnaseva;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RestaurantSignupActivity extends AppCompatActivity {

    private static final String TAG = "RestaurantSignup";
    private EditText etRestaurantName, etFssaiCode, etLocation, etUsername, etPassword;
    private Button btnSignUp;
    private DatabaseReference restaurantsRef;

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

        restaurantsRef = FirebaseDatabase.getInstance().getReference("restaurants");

        btnSignUp.setOnClickListener(v -> signupUser());
    }

    private void signupUser() {
        String restaurantName = etRestaurantName.getText().toString().trim();
        String fssaiCode = etFssaiCode.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(restaurantName) || TextUtils.isEmpty(fssaiCode) ||
                TextUtils.isEmpty(location) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(RestaurantSignupActivity.this, "Fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Store user details in Firebase under "restaurants"
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("restaurantName", restaurantName);
        userMap.put("fssaiCode", fssaiCode);
        userMap.put("location", location);
        userMap.put("username", username);
        userMap.put("password", password);

        restaurantsRef.child(username).setValue(userMap)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(RestaurantSignupActivity.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RestaurantSignupActivity.this, RestaurantActivity.class));
                        finish();
                    } else {
                        Log.e(TAG, "Error saving details: " + task.getException());
                        Toast.makeText(RestaurantSignupActivity.this, "Error saving user details", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}