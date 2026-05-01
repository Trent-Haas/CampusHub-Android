package com.semo.cisproject.campushub.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.semo.cisproject.campushub.interfaces.AddorRemoveCallbacks;
import com.semo.cisproject.campushub.model.Cart;
import com.semo.cisproject.campushub.model.Order;
import com.semo.cisproject.campushub.model.Product;
import com.semo.cisproject.campushub.util.LocalStorage;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BaseActivity extends AppCompatActivity implements AddorRemoveCallbacks {
    public static final String TAG = "BaseActivity";
    private static final int PERMISSION_REQUEST_CODE = 100;

    protected List<Cart> cartList = new ArrayList<>();
    protected List<Order> orderList = new ArrayList<>();
    protected Gson gson;
    protected LocalStorage localStorage;
    protected ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        localStorage = new LocalStorage(getApplicationContext());
        gson = new Gson();
        progressDialog = new ProgressDialog(this);

        updateLocalCartData();
    }


    public void updateLocalCartData() {
        String jsonCart = localStorage.getCart();
        if (jsonCart != null) {
            Type type = new TypeToken<List<Cart>>() {}.getType();
            cartList = gson.fromJson(jsonCart, type);
        }
    }

    public int getCartCount() {
        updateLocalCartData();
        return cartList != null ? cartList.size() : 0;
    }

    public List<Cart> getCartList() {
        updateLocalCartData();
        return cartList;
    }

    public double getTotalPrice() {

        updateLocalCartData();
        if (cartList == null) {
            return 0.0;
        }

        double total = 0.0;
        for (Cart item : cartList) {
            try {
                total += Double.parseDouble(item.getSubTotal());
            } catch (Exception e) {
                Log.e(TAG, "Price calculation error", e);
            }
        }
        return total;
    }

    public List<Order> getOrderList() {
        return new ArrayList<>();
    }

    public void showProgress(String message) {
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void hideProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    public void enableCampusHubPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = {
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA
            };

            List<String> listPermissionsNeeded = new ArrayList<>();
            for (String p : permissions) {
                if (ContextCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED) {
                    listPermissionsNeeded.add(p);
                }
            }

            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(this,
                        listPermissionsNeeded.toArray(new String[0]), PERMISSION_REQUEST_CODE);
            }
        }
    }

    @Override public void onAddProduct() {}
    @Override public void onRemoveProduct() {}
    @Override public void updateTotalPrice() {}
}