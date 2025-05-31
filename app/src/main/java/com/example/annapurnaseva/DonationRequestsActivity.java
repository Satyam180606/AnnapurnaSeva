package com.example.annapurnaseva;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DonationRequestsActivity extends AppCompatActivity {

    private ListView listViewDonationRequests;
    private ArrayAdapter<String> adapter;
    private List<String> donationRequestsList;
    private DatabaseReference donationRequestsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_requests);

        listViewDonationRequests = findViewById(R.id.listViewDonationRequests);
        donationRequestsList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, donationRequestsList);
        listViewDonationRequests.setAdapter(adapter);

        // Reference to the "donationRequests" node in Firebase
        donationRequestsRef = FirebaseDatabase.getInstance().getReference("donationRequests");

        fetchDonationRequests();
    }

    private void fetchDonationRequests() {
        donationRequestsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                donationRequestsList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    // Retrieve each donation request as an object using the model from RequestDonationActivity.
                    // Ensure that in RequestDonationActivity, DonationRequest is declared as public static.
                    RequestDonationActivity.DonationRequest request =
                            ds.getValue(RequestDonationActivity.DonationRequest.class);
                    if (request != null) {
                        donationRequestsList.add(request.toString());
                    }
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DonationRequestsActivity.this,
                        "Error fetching donation requests", Toast.LENGTH_SHORT).show();
            }
        });
    }
}