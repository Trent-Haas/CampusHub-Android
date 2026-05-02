package com.semo.cisproject.campushub.util;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalStorage {
    private SharedPreferences sharedPreferences;

    public LocalStorage(Context context) {
        sharedPreferences = context.getSharedPreferences("CampusHubPrefs", Context.MODE_PRIVATE);
    }

    public void createUserLoginSession(String user) {
        sharedPreferences.edit().putBoolean("isLoggedIn", true).apply();
        sharedPreferences.edit().putString("userLogin", user).apply();
    }

    public void setUsername(String username) {
        sharedPreferences.edit().putString("username", username).apply();
    }

    public String getUsername() {
        return sharedPreferences.getString("username", "Student");
    }

    public String getCart() {
        return sharedPreferences.getString("cart", "");
    }

    public void setCart(String cart) {
        sharedPreferences.edit().putString("cart", cart).apply();
    }

    public void deleteCart() {
        sharedPreferences.edit().remove("cart").apply();
    }

    public boolean isUserLoggedIn() {
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }

    public void logoutUser() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", false);
        editor.remove("cart");
        editor.apply();
    }

    public String getUserLogin() {
        return sharedPreferences.getString("userLogin", "");
    }

    public String getUserAddress() {
        return sharedPreferences.getString("userAddress", "");
    }

    public void setUserAddress(String address) {
        sharedPreferences.edit().putString("userAddress", address).apply();
    }

    public void setOrder(String order) {
        sharedPreferences.edit().putString("order", order).apply();
    }
}