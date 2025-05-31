package com.example.annapurnaseva;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RestaurantActivity extends AppCompatActivity {
    private static final String TAG = "RestaurantActivity";
    private TextView tvWelcome, tvDetails;
    private Button btnDonateFood, btnDonationRequests, btnLogout;
    private DatabaseReference restaurantRef;
    private String username;
    private String restaurantName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant); // Ensure this layout resource exists

        // Initialize views
        tvWelcome = findViewById(R.id.tvWelcome);
        tvDetails = findViewById(R.id.tvDetails);
        btnDonateFood = findViewById(R.id.btnDonateFood);
        btnDonationRequests = findViewById(R.id.btnDonationRequests);
        btnLogout = findViewById(R.id.btnLogout);

        // Retrieve the username passed from LoginActivity or Signup
        username = getIntent().getStringExtra("username");
        if (username == null || username.isEmpty()) {
            Toast.makeText(this, "No username provided", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Retrieve restaurant details from Firebase under "restaurants/{username}"
        restaurantRef = FirebaseDatabase.getInstance().getReference("restaurants").child(username);
        restaurantRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    restaurantName = snapshot.child("restaurantName").getValue(String.class);
                    String fssaiCode = snapshot.child("fssaiCode").getValue(String.class);
                    String location = snapshot.child("location").getValue(String.class);
                    // Use the restaurant's name (not the username) in the welcome header.
                    tvWelcome.setText("Welcome, " + restaurantName);
                    String details = "FSSAI Code: " + fssaiCode + "\nLocation: " + location;
                    tvDetails.setText(details);
                } catch (Exception e) {
                    Log.e(TAG, "Error processing restaurant data: " + e.getMessage());
                    Toast.makeText(RestaurantActivity.this, "Error processing details", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Database error: " + error.getMessage());
                Toast.makeText(RestaurantActivity.this, "Database error", Toast.LENGTH_SHORT).show();
            }
        });

        // Button to launch the Donate Food screen
        btnDonateFood.setOnClickListener(v -> {
            Toast.makeText(RestaurantActivity.this, "Launching Donate Food screen", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RestaurantActivity.this, DonateActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });

        // Button to view Donation Requests (NGO requests)
        btnDonationRequests.setOnClickListener(v -> {
            Toast.makeText(RestaurantActivity.this, "Loading Donation Requests", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RestaurantActivity.this, DonationRequestsActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });

        // Logout button: clear stored session details and navigate back to LoginActivity.
        btnLogout.setOnClickListener(v -> {
            // Clear SharedPreferences (adjust the preference name if you use a different one)
            SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear(); // Removes all stored session data
            editor.apply();

            Toast.makeText(RestaurantActivity.this, "Logging out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RestaurantActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Ensure that RestaurantActivity is removed from the back stack
        });
    }
}