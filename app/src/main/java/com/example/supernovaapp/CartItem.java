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
    public int getId() {return id;}

    // Setters (if needed)
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
    public void setId(int id) {this.id = id;}
}
