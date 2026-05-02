package com.semo.cisproject.campushub.model;

public class Product {
    String id, title, image, currency, price, attribute, discount, description, extra1, extra2;
    int count;

    public Product() {}

    public Product(String id, String title, int count, String extra2) {
        this.id = id;
        this.title = title;
        this.count = count;
        this.extra2 = extra2;
    }

    public Product(String id, String title, String image, String price, String attribute, String discount) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.price = price;
        this.attribute = attribute;
        this.discount = discount;
    }

    public Product(String id, String title, String image, String currency, String price, String attribute, String discount, String description, String extra1, int count, String extra2) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.currency = currency;
        this.price = price;
        this.attribute = attribute;
        this.discount = discount;
        this.description = description;
        this.extra1 = extra1;
        this.count = count;
        this.extra2 = extra2;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getImage() { return image; }
    public String getCurrency() { return currency; }
    public String getPrice() { return price; }
    public String getAttribute() { return attribute; }
    public String getDiscount() { return discount; }
    public String getDescription() { return description; }
}