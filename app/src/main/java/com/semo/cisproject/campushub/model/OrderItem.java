package com.semo.cisproject.campushub.model;


public class OrderItem {
    String id;
    String order_id;
    String product_id;
    int quantity;
    String unit_price;

    public OrderItem() {
    }

    public OrderItem(String id, String order_id, String product_id, int quantity, String unit_price) {
        this.id = id;
        this.order_id = order_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.unit_price = unit_price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return order_id;
    }

    public void setOrderId(String order_id) {
        this.order_id = order_id;
    }

    public String getProductId() {
        return product_id;
    }

    public void setProductId(String product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnitPrice() {
        return unit_price;
    }

    public void setUnitPrice(String unit_price) {
        this.unit_price = unit_price;
    }
}
