package com.semo.cisproject.campushub.model;


public class Product {
    String id;
    String categoryId;
    String department;
    String title;
    String description;
    String attribute;
    String currency;
    String price;
    String discount_percent;
    int stock_quantity;
    String image;

    public Product() {
    }

    public Product(String id, String categoryId, String department, String title, String description, String attribute, String price, String discount_percent, int stock_quantity, String image) {
        this.id = id;
        this.categoryId = categoryId;
        this.department = department;
        this.title = title;
        this.description = description;
        this.attribute = attribute;
        this.price = price;
        this.discount_percent = discount_percent;
        this.stock_quantity = stock_quantity;
        this.image = image;
    }

    public Product(String id, String categoryId, String department, String title, String description, String attribute, String currency, String price, String discount_percent, int stock_quantity, String image) {
        this.id = id;
        this.categoryId = categoryId;
        this.department = department;
        this.title = title;
        this.description = description;
        this.attribute = attribute;
        this.currency = currency;
        this.price = price;
        this.discount_percent = discount_percent;
        this.stock_quantity = stock_quantity;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscountPercent() {
        return discount_percent;
    }

    public void setDiscountPercent(String discount_percent) {
        this.discount_percent = discount_percent;
    }

    public int getStockQuantity() {
        return stock_quantity;
    }

    public void setStockQuantity(int stock_quantity) {
        this.stock_quantity = stock_quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
