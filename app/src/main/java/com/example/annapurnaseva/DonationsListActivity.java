package com.example.annapurnaseva;

import android.os.Bundle;
import android.util.Log;
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

public class DonationsListActivity extends AppCompatActivity {
    private static final String TAG = "DonationsListActivity";
    private ListView listViewDonations;
    private ArrayAdapter<FoodItem> adapter;
    private List<FoodItem> donationsList;
    private FoodItemDao foodItemDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donations_list);

        listViewDonations = findViewById(R.id.listViewDonations);
        donationsList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, donationsList);
        listViewDonations.setAdapter(adapter);

        foodItemDao = new FoodItemDao();
        foodItemDao.getAllFoodItems(new FoodItemDao.OnFoodItemsRetrievedListener() {
            @Override
            public void onSuccess(List<FoodItem> foodItems) {
                donationsList.clear();
                donationsList.addAll(foodItems);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(DonationsListActivity.this, "Error: " + error, Toast.LENGTH_SHORT).show();
                Log.e(TAG, error);
            }
        });
    }
}