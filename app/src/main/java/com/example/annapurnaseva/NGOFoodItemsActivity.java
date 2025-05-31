package com.example.annapurnaseva;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class NGOFoodItemsActivity extends AppCompatActivity {
    private static final String TAG = "NGOFoodItemsActivity";
    private ListView listViewFoodItems;
    private ArrayAdapter<FoodItem> adapter;
    private List<FoodItem> foodItemsList;
    private DatabaseReference foodItemsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_food_items);

        listViewFoodItems = findViewById(R.id.listViewFoodItems);
        foodItemsList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, foodItemsList);
        listViewFoodItems.setAdapter(adapter);

        foodItemsRef = FirebaseDatabase.getInstance().getReference("foodItems");
        fetchFoodItems();
    }

    private void fetchFoodItems() {
        foodItemsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                foodItemsList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    FoodItem item = ds.getValue(FoodItem.class);
                    if (item != null) {
                        foodItemsList.add(item);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Database error: " + error.getMessage());
                Toast.makeText(NGOFoodItemsActivity.this, "Error retrieving food items", Toast.LENGTH_SHORT).show();
            }
        });
    }
}