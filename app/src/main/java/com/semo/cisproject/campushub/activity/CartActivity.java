package com.semo.cisproject.campushub.activity;

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
import java.util.ArrayList;

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
        recyclerView = findViewById(R.id.cart_recycler_view);

        cartList = getCartList();
        if (cartList == null) cartList = new ArrayList<>();

        adapter = new CartAdapter(cartList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        updateTotalPrice();
    }

    private void setupToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
            actionBar.setDisplayHomeAsUpEnabled(true);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
            actionBar.setHomeAsUpIndicator(upArrow);

            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            TextView tv = new TextView(this);
            tv.setLayoutParams(lp);
            tv.setGravity(Gravity.CENTER);
            tv.setTypeface(null, Typeface.BOLD);
            tv.setText("Marketplace Cart");
            tv.setTextSize(20);
            tv.setTextColor(getResources().getColor(R.color.colorPrimary));
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(tv);
        }
    }

    @Override
    public void updateTotalPrice() {
        if (totalPrice != null) {
            totalPrice.setText(String.format("$%.2f", getTotalPrice()));
        }

        if (cartList == null || cartList.isEmpty()) {
            mMenuState = "HIDE_MENU";
            if (emptyCart != null) emptyCart.setVisibility(View.VISIBLE);
            if (checkoutLL != null) checkoutLL.setVisibility(View.GONE);
        } else {
            mMenuState = "SHOW_MENU";
            if (emptyCart != null) emptyCart.setVisibility(View.GONE);
            if (checkoutLL != null) checkoutLL.setVisibility(View.VISIBLE);
        }
        invalidateOptionsMenu();
    }

    public void onCheckoutClicked(View view) {
        if (cartList != null && !cartList.isEmpty()) {
            startActivity(new Intent(this, CheckoutActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        MenuItem item = menu.findItem(R.id.cart_delete);

        if (item != null) {
            item.setVisible(!mMenuState.equalsIgnoreCase("HIDE_MENU"));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.cart_delete) {
            new AlertDialog.Builder(this)
                    .setTitle("Clear Cart")
                    .setMessage("Remove all items?")
                    .setPositiveButton("Clear All", (dialog, which) -> {
                        localStorage.deleteCart();
                        if (cartList != null) {
                            cartList.clear();
                        }
                        if (adapter != null) {
                            adapter.notifyDataSetChanged();
                        }
                        updateTotalPrice();
                    })
                    .setNegativeButton("Cancel", null).show();
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}