package com.example.annapurnaseva;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private RadioGroup rgUserType;
    private Button btnLogin, btnSignUp;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Check if a session exists
        SharedPreferences sp = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        String savedUserType = sp.getString("userType", null);
        if (savedUserType != null) {
            if (savedUserType.equals("Restaurant")) {
                int restaurantId = sp.getInt("userId", -1);
                if (restaurantId != -1) {
                    startActivity(new Intent(LoginActivity.this, RestaurantActivity.class)
                            .putExtra("restaurantId", restaurantId));
                    finish();
                    return;
                }
            } else if (savedUserType.equals("NGO")) {
                startActivity(new Intent(LoginActivity.this, NGOActivity.class));
                finish();
                return;
            }
        }

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        rgUserType = findViewById(R.id.rgUserType);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        db = AppDatabase.getDatabase(getApplicationContext());

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = etUsername.getText().toString().trim();
                final String password = etPassword.getText().toString().trim();
                int selectedId = rgUserType.getCheckedRadioButtonId();
                RadioButton rb = findViewById(selectedId);
                final String userType = rb.getText().toString(); // "Restaurant" or "NGO"

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (userType.equals("Restaurant")) {
                            Restaurant restaurant = db.restaurantDao().getRestaurantByCredentials(username, password);
                            if (restaurant != null) {
                                // Save session details.
                                SharedPreferences sp = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
                                sp.edit().putString("userType", "Restaurant")
                                        .putInt("userId", restaurant.getId())
                                        .apply();
                                Intent intent = new Intent(LoginActivity.this, RestaurantActivity.class);
                                intent.putExtra("restaurantId", restaurant.getId());
                                startActivity(intent);
                                finish();
                            } else {
                                runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Invalid credentials. Please sign up.", Toast.LENGTH_LONG).show());
                            }
                        } else { // NGO
                            NGO ngo = db.ngoDao().getNGOByCredentials(username, password);
                            if (ngo != null) {
                                SharedPreferences sp = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
                                sp.edit().putString("userType", "NGO")
                                        .putInt("userId", ngo.getId())
                                        .apply();
                                Intent intent = new Intent(LoginActivity.this, NGOActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Invalid credentials. Please sign up.", Toast.LENGTH_LONG).show());
                            }
                        }
                    }
                }).start();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = rgUserType.getCheckedRadioButtonId();
                RadioButton rb = findViewById(selectedId);
                String userType = rb.getText().toString();
                if (userType.equals("Restaurant")) {
                    startActivity(new Intent(LoginActivity.this, RestaurantSignupActivity.class));
                } else {
                    startActivity(new Intent(LoginActivity.this, NGOSignupActivity.class));
                }
            }
        });
    }
}