package com.example.annapurnaseva;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.*;

public class DonateActivity extends AppCompatActivity {
    private EditText etName, etDesc, etPrice, etImage;
    private Button btnPost;
    private DatabaseReference foodRef, restRef;

    // will hold the restaurant’s real details
    private String restName, restLocation;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_donate);

        etName  = findViewById(R.id.etFoodName);
        etDesc  = findViewById(R.id.etFoodDescription);
        etPrice = findViewById(R.id.etFoodPrice);
        etImage = findViewById(R.id.etFoodImageUrl);
        btnPost = findViewById(R.id.btnPostDonation);

        foodRef = FirebaseDatabase.getInstance()
                .getReference("foodItems");

        // load restaurant profile
        SharedPreferences prefs = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String username = prefs.getString("username", null);
        restRef = FirebaseDatabase.getInstance()
                .getReference("restaurants").child(username);

        restRef.addListenerForSingleValueEvent(new ValueEventListener(){
            @Override
            public void onDataChange(DataSnapshot snap) {
                restName     = snap.child("restaurantName").getValue(String.class);
                restLocation = snap.child("location").getValue(String.class);
                if (restName == null)     restName     = "Unknown";
                if (restLocation == null) restLocation = "Unknown";
            }
            @Override public void onCancelled(DatabaseError e){ }
        });

        btnPost.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String desc = etDesc.getText().toString().trim();
            String pstr = etPrice.getText().toString().trim();
            String img  = etImage.getText().toString().trim();

            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(desc) || TextUtils.isEmpty(pstr)){
                Toast.makeText(this,"Fill required fields",Toast.LENGTH_SHORT).show();
                return;
            }
            double price;
            try { price = Double.parseDouble(pstr); }
            catch(NumberFormatException ex){
                Toast.makeText(this,"Bad price",Toast.LENGTH_SHORT).show();
                return;
            }

            // include the restaurant’s real detail in the FoodItem
            FoodItem fi = new FoodItem(name, desc, price, img, restName, restLocation);
            foodRef.push().setValue(fi)
                    .addOnCompleteListener(t->{
                        if (t.isSuccessful()) {
                            Toast.makeText(this,"Posted!",Toast.LENGTH_SHORT).show();
                            etName.setText(""); etDesc.setText("");
                            etPrice.setText(""); etImage.setText("");
                        } else {
                            Toast.makeText(this,"Error:"+t.getException().getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        });
    }
}