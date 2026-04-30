package com.semo.cisproject.campushub.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.semo.cisproject.campushub.R;
import com.semo.cisproject.campushub.adapter.ProductAdapter;
import com.semo.cisproject.campushub.helper.Converter;
import com.semo.cisproject.campushub.model.Product;
import com.semo.cisproject.campushub.network.ApiCallback;
import com.semo.cisproject.campushub.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private ProductAdapter mAdapter;
    private List<Product> productList = new ArrayList<>();
    private String viewTypeTag = "List";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        setupToolbar();

        recyclerView = findViewById(R.id.product_rv);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadMarketplaceData();
    }

    private void setupToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);

            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
            actionBar.setHomeAsUpIndicator(upArrow);

            applyCustomTitle(actionBar, "Campus Marketplace");
        }
    }

    private void applyCustomTitle(ActionBar actionBar, String title) {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        TextView tv = new TextView(this);
        tv.setLayoutParams(lp);
        tv.setGravity(Gravity.CENTER);

        try {
            Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Merienda-Bold.ttf");
            tv.setTypeface(tf);
        } catch (Exception e) {
            tv.setTypeface(null, Typeface.BOLD);
        }

        tv.setText(title);
        tv.setTextSize(20);
        tv.setTextColor(getResources().getColor(R.color.colorPrimary));

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(tv);
    }

    private void loadMarketplaceData() {
        showProgress("Loading Marketplace...");

        ProductRepository repository = new ProductRepository();
        repository.getProducts(new ApiCallback<List<Product>>() {
            @Override
            public void onSuccess(List<Product> result) {
                hideProgress();
                productList = result;
                updateAdapter();
            }

            @Override
            public void onError(String errorMessage) {
                hideProgress();
                Log.e("API_ERROR", "Database connection failed: " + errorMessage);
            }
        });
    }

    private void updateAdapter() {
        mAdapter = new ProductAdapter(productList, ProductActivity.this, viewTypeTag);
        recyclerView.setAdapter(mAdapter);
    }

    /**
     * Logic to toggle between Single-Column List and Two-Column Grid.
     */
    public void onToggleClicked(View view) {
        if (viewTypeTag.equalsIgnoreCase("List")) {
            viewTypeTag = "Grid";
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            viewTypeTag = "List";
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        updateAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.cart_action);

        menuItem.setIcon(Converter.convertLayoutToImage(
                ProductActivity.this,
                getCartCount(),
                R.drawable.ic_shopping_basket));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.cart_action) {
            startActivity(new Intent(this, CartActivity.class));
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAddProduct() {
        invalidateOptionsMenu();
    }

    @Override
    public void onRemoveProduct() {
        invalidateOptionsMenu();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}