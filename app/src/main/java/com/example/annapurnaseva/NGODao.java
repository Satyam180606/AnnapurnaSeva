package com.example.annapurnaseva;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface NGODao {

    @Insert
    long insertNGO(NGO ngo);

    @Query("SELECT * FROM ngos WHERE username = :username AND password = :password LIMIT 1")
    NGO getNGOByCredentials(String username, String password);
}