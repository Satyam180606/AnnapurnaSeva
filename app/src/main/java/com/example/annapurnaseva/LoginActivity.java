package com.example.annapurnaseva;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private EditText etUsername, etPassword;
    private RadioGroup rgUserType;
    private Button btnLogin, btnSignup;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        rgUserType = findViewById(R.id.rgUserType);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);

        sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);

        // Check if a session already exists for either NGO or Restaurant.
        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            String userType = sharedPreferences.getString("userType", "");
            String username = sharedPreferences.getString("username", "");
            if (userType.equalsIgnoreCase("NGO")) {
                Intent intent = new Intent(LoginActivity.this, NGOActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(LoginActivity.this, RestaurantActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                finish();
            }
            return;
        }

        btnLogin.setOnClickListener(v -> loginUser());
        btnSignup.setOnClickListener(v -> navigateToSignup());
    }

    private void loginUser() {
        final String username = etUsername.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedId = rgUserType.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(LoginActivity.this, "Please select user type", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton rb = findViewById(selectedId);
        final String userType = rb.getText().toString();
        DatabaseReference dbRef;
        if (userType.equalsIgnoreCase("NGO")) {
            dbRef = FirebaseDatabase.getInstance().getReference("ngos");
        } else {
            dbRef = FirebaseDatabase.getInstance().getReference("restaurants");
        }
        dbRef.child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String storedPassword = snapshot.child("password").getValue(String.class);
                    if (storedPassword != null && storedPassword.equals(password)) {
                        // Successful login; save session details.
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("isLoggedIn", true);
                        editor.putString("userType", userType);
                        editor.putString("username", username);
                        editor.apply();

                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        if (userType.equalsIgnoreCase("NGO")) {
                            Intent intent = new Intent(LoginActivity.this, NGOActivity.class);
                            intent.putExtra("username", username);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(LoginActivity.this, RestaurantActivity.class);
                            intent.putExtra("username", username);
                            startActivity(intent);
                        }
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Username not found", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(LoginActivity.this, "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Database error: " + error.getMessage());
            }
        });
    }

    private void navigateToSignup() {
        int selectedId = rgUserType.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(LoginActivity.this, "Please select user type to sign up", Toast.LENGTH_SHORT).show();
            return;
        }
        RadioButton rb = findViewById(selectedId);
        String userType = rb.getText().toString();
        if (userType.equalsIgnoreCase("NGO")) {
            Intent intent = new Intent(LoginActivity.this, NGOSignupActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(LoginActivity.this, RestaurantSignupActivity.class);
            startActivity(intent);
        }
    }
}