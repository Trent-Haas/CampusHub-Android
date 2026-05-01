package com.semo.cisproject.campushub.model;

import java.io.Serializable;

public class Product implements Serializable {
    private String id, categoryId, department, title, description, attribute, currency, price, discount;
    private int stock;
    private int imageRes;
    private String image;

    public Product() {

    }

    public Product(String title, String price, int imageRes, String description) {
        this.title = title;
        this.price = price;
        this.imageRes = imageRes;
        this.description = description;
    }

    public Product(String id, String categoryId, String department, String title, String description,
                   String attribute, String currency, String price, String discount,
                   int stock, String image) {
        this.id = id;
        this.categoryId = categoryId;
        this.department = department;
        this.title = title;
        this.description = description;
        this.attribute = attribute;
        this.currency = currency;
        this.price = price;
        this.discount = discount;
        this.stock = stock;
        this.image = image;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageRes() {
        return imageRes;
    }
    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public String getCategoryId() {
        return categoryId;
    }
    public String getDepartment() {
        return department;
    }
    public String getAttribute() {
        return attribute;
    }
    public String getDiscount() {
        return discount;
    }
    public String getCurrency() {
        return currency;
    }
    public int getStock() {
        return stock;
    }

}