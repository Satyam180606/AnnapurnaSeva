package com.example.annapurnaseva;

import android.util.Log;
import androidx.annotation.NonNull;
// REMOVE: import androidx.room.Dao; // Not a Room DAO

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

// This class is specifically for Firebase Realtime Database operations for FoodItems
// It is NOT a Room DAO.
public class FoodItemDao { // Changed from 'interface' to 'class'
    private static final String TAG = "FoodItemFirebaseDao"; // Renamed TAG for clarity
    private final DatabaseReference dbRef;

    public FoodItemDao() {
        // Points to the "foodItems" node where food item entries are stored.
        dbRef = FirebaseDatabase.getInstance().getReference("foodItems");
    }

    // Your addFoodItem and getAllFoodItems methods would be here,
    // exactly as you had them in your original code, BUT:
    // The line 'foodItem.setId(key);' implies your FoodItem.java
    // has a setId(String key) method.
    // If FoodItem.id is an int (for Room's @PrimaryKey(autoGenerate=true)),
    // then you cannot directly set a String Firebase key into an int ID.
    // You would need a separate String field in FoodItem for the Firebase key,
    // or your FoodItem's 'id' field itself would need to be a String.

    public void addFoodItem(@NonNull FoodItem foodItem, @NonNull final OnFoodItemOperationListener listener) {
        try {
            String key = dbRef.push().getKey();
            if (key == null) {
                listener.onFailure("Unable to generate key for FoodItem.");
                return;
            }

            // foodItem.setFirebaseKey(key); // If you have a specific String field for the Firebase key
            // OR if your FoodItem.id is a String:
            // foodItem.setId(key); // This assumes FoodItem's id is a String.

            dbRef.child(key).setValue(foodItem).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    listener.onSuccess("Food item added successfully. Firebase Key: " + key);
                } else {
                    String err = task.getException() != null ? task.getException().getMessage() : "Unknown error.";
                    Log.e(TAG, "Error adding food item: " + err);
                    listener.onFailure(err);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Exception in addFoodItem: " + e.getMessage());
            listener.onFailure("Exception: " + e.getMessage());
        }
    }

    public void getAllFoodItems(@NonNull final OnFoodItemsRetrievedListener listener) {
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<FoodItem> foodItems = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FoodItem foodItem = snapshot.getValue(FoodItem.class);
                    if (foodItem != null) {
                        // String firebaseKey = snapshot.getKey();
                        // foodItem.setFirebaseKey(firebaseKey); // If you store the key back into the object
                        foodItems.add(foodItem);
                    }
                }
                listener.onSuccess(foodItems);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, "Error getting food items: " + databaseError.getMessage());
                listener.onFailure(databaseError.getMessage());
            }
        });
    }

    // Listener interfaces remain the same
    public interface OnFoodItemOperationListener {
        void onSuccess(String message);
        void onFailure(String error);
    }

    public interface OnFoodItemsRetrievedListener {
        void onSuccess(List<FoodItem> foodItems);
        void onFailure(String error);
    }
}