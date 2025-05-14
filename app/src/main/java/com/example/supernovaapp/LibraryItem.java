package com.example.supernovaapp;

public class LibraryItem {
    private int id;
    private String title;
    private String studio;
    private String hrs;
    private int imageResource;

    // Constructor
    public LibraryItem(int id, String title, String studio, String hrs, int imageResource) {
        this.id = id;
        this.title = title;
        this.studio = studio;
        this.hrs = hrs;
        this.imageResource = imageResource;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getStudio() {
        return studio;
    }

    public String getHrs() {
        return hrs;
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
    public void setHrs(String price) {
        this.hrs = hrs;
    }
    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
    public void setId(int id) {this.id = id;}
}
