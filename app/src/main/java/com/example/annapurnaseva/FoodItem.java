package com.example.annapurnaseva;

public class FoodItem {
    public String name, description, imageUrl;
    public double price;
    public String restaurantName, restaurantLocation;

    public FoodItem() { }
    public FoodItem(String n, String d, double p, String img,
                    String rName, String rLoc) {
        this.name               = n;
        this.description        = d;
        this.price              = p;
        this.imageUrl           = img;
        this.restaurantName     = rName;
        this.restaurantLocation = rLoc;
    }

    @Override
    public String toString() {
        return restaurantName + " ("+restaurantLocation+")\n"
                + name + ": ₹" + price + "\n" + description;
    }
}