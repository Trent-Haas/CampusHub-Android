package com.semo.cisproject.campushub.model;

public class User {
    String id;
    String student_id;
    String name;
    String email;
    String mobile;
    String password_hash;
    int student_verified;
    String discount_tier;
    int discount_used;

    public User() {
    }

    public User(String id, String student_id, String name, String email, String mobile,
                String password_hash, int student_verified, String discount_tier, int discount_used) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.password_hash = password_hash;
        this.student_verified = student_verified;
        this.discount_tier = discount_tier;
        this.discount_used = discount_used;
    }

    public User(String id, String student_id, String name, String email, String mobile, String password, String discount_tier) {
        this.id = id;
        this.student_id = student_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }

    public String getPasswordHash() {
        return password_hash;
    }

    public void setPasswordHash(String password_hash) {
        this.password_hash = password_hash;
    }

    public int getStudentVerified() {
        return student_verified;
    }

    public void setStudentVerified(int student_verified) {
        this.student_verified = student_verified;
    }

    public String getDiscountTier() {
        return discount_tier;
    }

    public void setDiscountTier(String discount_tier) {
        this.discount_tier = discount_tier;
    }

    public int getDiscountUsed() {
        return discount_used;
    }

    public void setDiscountUsed(int discount_used) {
        this.discount_used = discount_used;
    }
}
