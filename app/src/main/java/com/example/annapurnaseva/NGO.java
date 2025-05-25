package com.example.annapurnaseva;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ngos")
public class NGO {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String ngoName;
    private String registrationNumber;
    private String officeAddress;
    private String username;
    private String password;

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNgoName() { return ngoName; }
    public void setNgoName(String ngoName) { this.ngoName = ngoName; }

    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }

    public String getOfficeAddress() { return officeAddress; }
    public void setOfficeAddress(String officeAddress) { this.officeAddress = officeAddress; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}