package com.semo.cisproject.campushub.model;

public class Order {
    private String id;
    private String orderId;
    private String date;
    private String total;
    private String status;
    private String pickup_location;
    private String address_id;

    public Order() {
    }

    public Order(String id, String orderId, String date, String total, String status) {
        this.id = id;
        this.orderId = orderId;
        this.date = date;
        this.total = total;
        this.status = status;
    }

    public Order(String id, String orderId, String date, String total, String status, String pickup_location, String address_id) {
        this.id = id;
        this.orderId = orderId;
        this.date = date;
        this.total = total;
        this.status = status;
        this.pickup_location = pickup_location;
        this.address_id = address_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPickupLocation() {
        return pickup_location;
    }

    public void setPickupLocation(String pickup_location) {
        this.pickup_location = pickup_location;
    }

    public String getAddressId() {
        return address_id;
    }

    public void setAddressId(String address_id) {
        this.address_id = address_id;
    }
}