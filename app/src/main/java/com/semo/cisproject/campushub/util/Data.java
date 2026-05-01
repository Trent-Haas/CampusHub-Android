package com.semo.cisproject.campushub.util;

import com.semo.cisproject.campushub.R;
import com.semo.cisproject.campushub.model.Product; // Ensure this model exists
import java.util.ArrayList;
import java.util.List;

public class Data {

    public Data() {
    }

    public List<Product> getOfferList() {
        List<Product> offerList = new ArrayList<>();

        offerList.add(new Product("Student Discount: MacBook", "$899", R.drawable.logo, "Tech deals for SEMO students"));
        offerList.add(new Product("Half-Price Coffee", "$2.50", R.drawable.logo, "Redeem at the Student Union"));

        return offerList;
    }

    public List<Product> getCategoryList() {
        return new ArrayList<>();
    }

    public List<Product> getNewList() {
        return getProductList();
    }

    public List<Product> getPopularList() {
        return getProductList();
    }

    public List<Product> getProductList() {
        return getOfferList();
    }

}