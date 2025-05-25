package com.example.annapurnaseva;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface RestaurantDao {

    @Insert
    long insertRestaurant(Restaurant restaurant);

    @Query("SELECT * FROM restaurants WHERE username = :username AND password = :password LIMIT 1")
    Restaurant getRestaurantByCredentials(String username, String password);

    @Query("SELECT * FROM restaurants WHERE id = :id LIMIT 1")
    Restaurant getRestaurantById(int id);
}