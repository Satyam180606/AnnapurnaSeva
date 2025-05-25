package com.example.annapurnaseva;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "food_items",
        foreignKeys = @ForeignKey(entity = Restaurant.class,
                parentColumns = "id",
                childColumns = "restaurantId",
                onDelete = CASCADE))
public class FoodItem {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int quantity;
    private String expiry;
    private int restaurantId; // References the Restaurant that posted this item

    // Constructor
    public FoodItem(String name, int quantity, String expiry, int restaurantId) {
        this.name = name;
        this.quantity = quantity;
        this.expiry = expiry;
        this.restaurantId = restaurantId;
    }

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getExpiry() { return expiry; }
    public void setExpiry(String expiry) { this.expiry = expiry; }

    public int getRestaurantId() { return restaurantId; }
    public void setRestaurantId(int restaurantId) { this.restaurantId = restaurantId; }

    @Override
    public String toString() {
        return "FoodItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", expiry='" + expiry + '\'' +
                ", restaurantId=" + restaurantId +
                '}';
    }
}