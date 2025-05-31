package com.example.annapurnaseva;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NGOActivity extends AppCompatActivity {
    private TextView tvWelcome;
    private Button btnDonations, btnRequestDonation, btnLogout;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo);

        // Initialize views from the layout file (ensure these IDs exist in activity_ngo.xml)
        tvWelcome = findViewById(R.id.tvWelcome);
        btnDonations = findViewById(R.id.btnDonations);
        btnRequestDonation = findViewById(R.id.btnRequestDonation);
        btnLogout = findViewById(R.id.btnLogout);

        // Retrieve the NGO's username passed from LoginActivity (or after signup)
        username = getIntent().getStringExtra("username");
        if (username == null || username.isEmpty()) {
            tvWelcome.setText("Welcome, NGO");
        } else {
            tvWelcome.setText("Welcome, " + username);
        }

        // Button to view restaurant donations (DonationsListActivity)
        btnDonations.setOnClickListener(v -> {
            Intent intent = new Intent(NGOActivity.this, DonationsListActivity.class);
            startActivity(intent);
        });

        // Button to open the "Request a Donation" form (RequestDonationActivity)
        btnRequestDonation.setOnClickListener(v -> {
            Intent intent = new Intent(NGOActivity.this, RequestDonationActivity.class);
            startActivity(intent);
        });

        // Logout button: clear saved session data and return to the common LoginActivity.
        btnLogout.setOnClickListener(v -> {
            // Retrieve SharedPreferences and clear it.
            SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Toast.makeText(NGOActivity.this, "Logged out successfully.", Toast.LENGTH_SHORT).show();

            // Create an intent that clears the activity stack to prevent back navigation.
            Intent intent = new Intent(NGOActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // Close the current activity.
        });
    }
}