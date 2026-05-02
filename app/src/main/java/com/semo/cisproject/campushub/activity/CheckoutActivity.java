package com.semo.cisproject.campushub.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

import com.google.gson.Gson;
import com.semo.cisproject.campushub.R;
import com.semo.cisproject.campushub.model.User;
import com.semo.cisproject.campushub.util.LocalStorage;

public class CheckoutActivity extends BaseActivity {

    private TextView checkoutName, subtotalTv, taxTv, totalTv;
    private Button confirmBtn;
    private LocalStorage localStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        localStorage = new LocalStorage(this);

        setupToolbar();
        initializeViews();
        populateCheckoutData();
    }

    private void setupToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);

            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
            actionBar.setHomeAsUpIndicator(upArrow);

            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            TextView tv = new TextView(this);
            tv.setLayoutParams(lp);
            tv.setGravity(Gravity.CENTER);
            tv.setTypeface(null, Typeface.BOLD);
            tv.setText("Secure Checkout");
            tv.setTextSize(20);
            tv.setTextColor(getResources().getColor(R.color.colorPrimary));

            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(tv);
        }
    }

    private void initializeViews() {
        checkoutName = findViewById(R.id.checkout_name);
        subtotalTv = findViewById(R.id.checkout_subtotal);
        taxTv = findViewById(R.id.checkout_tax);
        totalTv = findViewById(R.id.checkout_total);
        confirmBtn = findViewById(R.id.confirm_order_btn);

        confirmBtn.setOnClickListener(v -> processDemoOrder());
    }

    private void populateCheckoutData() {
        String userStr = localStorage.getUserLogin();
        if (userStr != null && !userStr.isEmpty()) {
            try {
                User user = new Gson().fromJson(userStr, User.class);
                if (user.getName() != null) {
                    checkoutName.setText(user.getName());
                }
            } catch (Exception ignored) {}
        }

        double subtotal = getTotalPrice();
        double tax = subtotal * 0.07;
        double total = subtotal + tax;

        subtotalTv.setText(String.format("$%.2f", subtotal));
        taxTv.setText(String.format("$%.2f", tax));
        totalTv.setText(String.format("$%.2f", total));
    }

    private void processDemoOrder() {
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Processing Payment securely...");
        pd.setCancelable(false);
        pd.show();

        new Handler().postDelayed(() -> {
            pd.dismiss();
            localStorage.deleteCart();

            Intent intent = new Intent(CheckoutActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("ORDER_SUCCESS", true);
            startActivity(intent);
            finish();
        }, 2000);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}