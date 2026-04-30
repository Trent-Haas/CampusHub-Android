package com.semo.cisproject.campushub.model;

public class Offer {
    private String id;
    private String image;
    private String product_id;
    private String discount_percent;
    private String valid_until;

    public Offer() {
    }

    public Offer(String id, String image, String product_id, String discount_percent, String valid_until) {
        this.id = id;
        this.image = image;
        this.product_id = product_id;
        this.discount_percent = discount_percent;
        this.valid_until = valid_until;
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

    public String getProductId() {
        return product_id;
    }

    public void setProductId(String product_id) {
        this.product_id = product_id;
    }

    public String getDiscountPercent() {
        return discount_percent;
    }

    public void setDiscountPercent(String discount_percent) {
        this.discount_percent = discount_percent;
    }

    public String getValidUntil() {
        return valid_until;
    }

    public void setValidUntil(String valid_until) {
        this.valid_until = valid_until;
    }
}