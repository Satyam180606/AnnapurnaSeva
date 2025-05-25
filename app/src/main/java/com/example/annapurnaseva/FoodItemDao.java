package com.example.annapurnaseva;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface FoodItemDao {

    @Insert
    long insertFoodItem(FoodItem foodItem);

    @Query("SELECT * FROM food_items")
    List<FoodItem> getAllFoodItems();

    @Query("DELETE FROM food_items WHERE id = :itemId")
    int deleteFoodItemById(int itemId);

    @Update
    int updateFoodItem(FoodItem foodItem);
}