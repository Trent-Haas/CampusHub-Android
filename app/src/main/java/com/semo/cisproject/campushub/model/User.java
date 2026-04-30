package com.semo.cisproject.campushub.model;

public class User {
    private String id;
    private String student_id;
    private String name;
    private String email;
    private String mobile;
    private String password;
    private String discount_tier;

    public User() {
    }

    public User(String id, String name, String email, String mobile, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
    }

    public User(String id, String student_id, String name, String email, String mobile, String password, String discount_tier) {
        this.id = id;
        this.student_id = student_id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.discount_tier = discount_tier;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getStudentId() { return student_id; }
    public void setStudentId(String student_id) { this.student_id = student_id; }
    public String getDiscountTier() { return discount_tier; }
    public void setDiscountTier(String discount_tier) { this.discount_tier = discount_tier; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}