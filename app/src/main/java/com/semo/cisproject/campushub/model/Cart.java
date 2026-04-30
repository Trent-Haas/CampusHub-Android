package com.semo.cisproject.campushub.model;

public class Cart {
    private String id;
    private String image;
    private String title;
    private String currency;
    private String price;
    private String attribute;
    private String quantity;
    private String subTotal;

    public Cart() {
    }

    public Cart(String id, String title, String image, String currency, String price, String attribute, String quantity, String subTotal) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.currency = currency;
        this.price = price;
        this.attribute = attribute;
        this.quantity = quantity;
        this.subTotal = subTotal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}