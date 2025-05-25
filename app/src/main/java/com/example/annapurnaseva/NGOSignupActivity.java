package com.example.annapurnaseva;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NGOSignupActivity extends AppCompatActivity {

    private EditText etNgoName, etRegistrationNumber, etOfficeAddress, etUsername, etPassword;
    private Button btnSignUp;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_signup);

        etNgoName = findViewById(R.id.etNgoName);
        etRegistrationNumber = findViewById(R.id.etRegistrationNumber);
        etOfficeAddress = findViewById(R.id.etOfficeAddress);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnSignUp = findViewById(R.id.btnSignUp);

        db = AppDatabase.getDatabase(getApplicationContext());

        btnSignUp.setOnClickListener(v -> {
            final String ngoName = etNgoName.getText().toString().trim();
            final String regNumber = etRegistrationNumber.getText().toString().trim();
            final String officeAddress = etOfficeAddress.getText().toString().trim();
            final String username = etUsername.getText().toString().trim();
            final String password = etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(ngoName) || TextUtils.isEmpty(regNumber)
                    || TextUtils.isEmpty(officeAddress) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                Toast.makeText(NGOSignupActivity.this, "Please fill all fields", Toast.LENGTH_LONG).show();
                return;
            }

            NGO ngo = new NGO();
            ngo.setNgoName(ngoName);
            ngo.setRegistrationNumber(regNumber);
            ngo.setOfficeAddress(officeAddress);
            ngo.setUsername(username);
            ngo.setPassword(password);

            new Thread(() -> {
                long id = db.ngoDao().insertNGO(ngo);
                runOnUiThread(() -> {
                    Toast.makeText(NGOSignupActivity.this, "Sign Up Successful. Please log in.", Toast.LENGTH_LONG).show();
                    finish();
                });
            }).start();
        });
    }
}