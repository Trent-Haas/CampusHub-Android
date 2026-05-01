package com.semo.cisproject.campushub.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.semo.cisproject.campushub.R;
import com.semo.cisproject.campushub.adapter.CartAdapter;
import com.semo.cisproject.campushub.model.Cart;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private ImageView emptyCart;
    private LinearLayout checkoutLL;
    private TextView totalPrice;
    private String mMenuState = "SHOW_MENU";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        setupToolbar();

        emptyCart = findViewById(R.id.cart_product_image);
        checkoutLL = findViewById(R.id.cart_bottom_bar);
        totalPrice = findViewById(R.id.total_cart_price);

        refreshTotalPriceUI();

        setUpCartRecyclerview();
    }

    private void setupToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);

            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
            actionBar.setHomeAsUpIndicator(upArrow);

            applyCustomTitle(actionBar, "Marketplace Cart");
        }
    }

    private void applyCustomTitle(ActionBar actionBar, String title) {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        TextView tv = new TextView(this);
        tv.setLayoutParams(lp);
        tv.setGravity(Gravity.CENTER);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setText(title);
        tv.setTextSize(20);
        tv.setTextColor(getResources().getColor(R.color.colorPrimary));

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(tv);
    }

    private void setUpCartRecyclerview() {
        cartList = getCartList();

        if (cartList == null) {
            cartList = new ArrayList<>();
        }

        if (cartList.isEmpty()) {
            toggleEmptyState(true);
        } else {
            toggleEmptyState(false);
        }

        recyclerView = findViewById(R.id.cart_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new CartAdapter(cartList, CartActivity.this);
        recyclerView.setAdapter(adapter);
    }

    private void toggleEmptyState(boolean isEmpty) {
        if (isEmpty) {
            mMenuState = "HIDE_MENU";
            emptyCart.setVisibility(View.VISIBLE);
            checkoutLL.setVisibility(View.GONE);
        } else {
            mMenuState = "SHOW_MENU";
            emptyCart.setVisibility(View.GONE);
            checkoutLL.setVisibility(View.VISIBLE);
        }
        invalidateOptionsMenu();
    }

    private void refreshTotalPriceUI() {
        totalPrice.setText(String.format("$%.2f", getTotalPrice()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        MenuItem item = menu.findItem(R.id.cart_delete);
        item.setVisible(!mMenuState.equalsIgnoreCase("HIDE_MENU"));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.cart_delete) {
            confirmClearCart();
            return true;
        } else if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void confirmClearCart() {
        new AlertDialog.Builder(this)
                .setTitle("Clear Cart")
                .setMessage("Are you sure you want to remove all items from your cart?")
                .setIcon(R.drawable.ic_delete_black_24dp)
                .setPositiveButton("Clear All", (dialog, whichButton) -> {
                    localStorage.deleteCart();
                    cartList.clear();
                    adapter.notifyDataSetChanged();
                    toggleEmptyState(true);
                    refreshTotalPriceUI();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    public void onCheckoutClicked(View view) {
        if (!cartList.isEmpty()) {
            startActivity(new Intent(this, CheckoutActivity.class));
        }
    }

    @Override
    public void updateTotalPrice() {
        refreshTotalPriceUI();
        if (getTotalPrice() <= 0.0) {
            toggleEmptyState(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}