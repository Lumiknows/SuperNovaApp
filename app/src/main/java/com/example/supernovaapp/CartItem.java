package com.example.supernovaapp;

public class CartItem {
    int id;
    String title;
    String studio;
    String price;
    String discount;
    int imageResource;

    // Constructor
    public CartItem(int id, String title, String studio, String price, String discount, int imageResource) {
        this.id = id;
        this.title = title;
        this.studio = studio;
        this.price = price;
        this.discount = discount;
        this.imageResource = imageResource;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getStudio() {
        return studio;
    }

    public String getPrice() {
        return price;
    }

    public String getDiscount() {
        return discount;
    }

    public int getImageResource() {
        return imageResource;
    }

    public int getId() {
        return id;
    }

    // Add this to use for price calculations
    public double getPriceAsDouble() {
        try {
            return Double.parseDouble(price.replaceAll("[^\\d.]", "")); // Remove currency symbols if any
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public void setId(int id) {
        this.id = id;
    }
}
