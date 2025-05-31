package com.example.annapurnaseva;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RequestDonationActivity extends AppCompatActivity {
    private static final String TAG = "RequestDonationActivity";
    private EditText etQuantity, etEstimatedPrice;
    private Button btnRequestDonation;
    private DatabaseReference donationRequestsRef;

    // For now, we use hard-coded dummy values.
    // Replace these with actual data from your logged-in NGO's profile.
    private String ngoName = "Helping Hands NGO";
    private String ngoAddress = "123 Charity Street";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_donation);

        // Initialize the views
        etQuantity = findViewById(R.id.etQuantity);
        etEstimatedPrice = findViewById(R.id.etEstimatedPrice);
        btnRequestDonation = findViewById(R.id.btnRequestDonation);

        // Reference to "donationRequests" node in Firebase
        donationRequestsRef = FirebaseDatabase.getInstance().getReference("donationRequests");

        btnRequestDonation.setOnClickListener(v -> requestDonation());
    }

    private void requestDonation() {
        String quantity = etQuantity.getText().toString().trim();
        String estimatedPrice = etEstimatedPrice.getText().toString().trim();

        if (TextUtils.isEmpty(quantity) || TextUtils.isEmpty(estimatedPrice)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create the donation request object with NGO details
        DonationRequest donationRequest = new DonationRequest(quantity, estimatedPrice, ngoName, ngoAddress);

        String key = donationRequestsRef.push().getKey();
        if (key == null) {
            Toast.makeText(this, "Error generating key", Toast.LENGTH_SHORT).show();
            return;
        }

        donationRequestsRef.child(key)
                .setValue(donationRequest)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(RequestDonationActivity.this, "Donation Request Posted", Toast.LENGTH_SHORT).show();
                        etQuantity.setText("");
                        etEstimatedPrice.setText("");
                    } else {
                        String err = task.getException() != null ? task.getException().getMessage() : "Unknown error";
                        Toast.makeText(RequestDonationActivity.this, "Error: " + err, Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error posting donation request: " + err);
                    }
                });
    }

    // Public static inner class for the donation request model.
    // Firebase needs a no-argument constructor.
    public static class DonationRequest {
        public String quantity;
        public String estimatedPrice;
        public String ngoName;
        public String ngoAddress;

        // Required empty constructor for Firebase deserialization
        public DonationRequest() { }

        public DonationRequest(String quantity, String estimatedPrice, String ngoName, String ngoAddress) {
            this.quantity = quantity;
            this.estimatedPrice = estimatedPrice;
            this.ngoName = ngoName;
            this.ngoAddress = ngoAddress;
        }

        @Override
        public String toString() {
            return "NGO: " + ngoName + " (" + ngoAddress + ")\n" +
                    "Quantity: " + quantity + ", Estimated Price: ₹" + estimatedPrice;
        }
    }
}