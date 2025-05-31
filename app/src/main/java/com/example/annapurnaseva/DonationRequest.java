package com.example.annapurnaseva;

public class DonationRequest {
    private String id; // Firebase-generated key
    private String quantity;
    private String estimatedPrice;

    public DonationRequest() {
        // Default constructor required for Firebase
    }

    public DonationRequest(String quantity, String estimatedPrice) {
        this.quantity = quantity;
        this.estimatedPrice = estimatedPrice;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getEstimatedPrice() {
        return estimatedPrice;
    }

    public void setEstimatedPrice(String estimatedPrice) {
        this.estimatedPrice = estimatedPrice;
    }

    @Override
    public String toString() {
        return "Quantity: " + quantity + ", Estimated Price: ₹" + estimatedPrice;
    }
}