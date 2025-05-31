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

public class NGOSignupActivity extends AppCompatActivity {
    private static final String TAG = "NGOSignupActivity";
    private EditText etNGOName, etGovernmentId, etLocation, etUsername, etPassword;
    private Button btnSignUp;
    private DatabaseReference ngosRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Update the file name here if needed:
        setContentView(R.layout.activity_ngosignup);

        etNGOName = findViewById(R.id.etNGOName);
        etGovernmentId = findViewById(R.id.etGovernmentId);
        etLocation = findViewById(R.id.etLocation);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnSignUp = findViewById(R.id.btnSignUp);

        ngosRef = FirebaseDatabase.getInstance().getReference("ngos");

        btnSignUp.setOnClickListener(v -> signupUser());
    }

    private void signupUser() {
        String ngoName = etNGOName.getText().toString().trim();
        String governmentId = etGovernmentId.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(ngoName) || TextUtils.isEmpty(governmentId) ||
                TextUtils.isEmpty(location) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("ngoName", ngoName);
        userMap.put("governmentId", governmentId);
        userMap.put("location", location);
        userMap.put("username", username);
        userMap.put("password", password);

        ngosRef.child(username).setValue(userMap)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(NGOSignupActivity.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(NGOSignupActivity.this, NGOActivity.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                        finish();
                    } else {
                        String err = (task.getException() != null) ? task.getException().getMessage() : "Unknown error";
                        Toast.makeText(NGOSignupActivity.this, "Error saving details: " + err, Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error saving NGO details: " + err);
                    }
                });
    }
}