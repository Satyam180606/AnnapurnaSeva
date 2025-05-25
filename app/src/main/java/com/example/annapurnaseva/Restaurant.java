package com.example.annapurnaseva;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "restaurants")
public class Restaurant {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String restaurantName;
    private String fssaiCode;
    private String location;
    private String username;
    private String password;

    // Getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) { this.id = id; }

    public String getRestaurantName() {
        return restaurantName;
    }
    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getFssaiCode() {
        return fssaiCode;
    }
    public void setFssaiCode(String fssaiCode) {
        this.fssaiCode = fssaiCode;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) { this.location = location; }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) { this.password = password; }
}