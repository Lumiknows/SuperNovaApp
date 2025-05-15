package com.example.supernovaapp;

public class StoreItem {
    private String title;
    private String studio;
    private String price;
    private int imageResId;

    public StoreItem(String title, String studio, String price, int imageResId) {
        this.title = title;
        this.studio = studio;
        this.price = price;
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public String getStudio() {
        return studio;
    }

    public String getPrice() {
        return price;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}
