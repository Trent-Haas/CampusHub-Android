package com.semo.cisproject.campushub.model;

public class Category {
    String id, title, image;

    public Category() {}

    public Category(String id, String title, String image) {
        this.id = id;
        this.title = title;
        this.image = image;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getImage() { return image; }
}