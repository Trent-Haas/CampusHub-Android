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
import com.semo.cisproject.campushub.util.Data;
import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private ProductAdapter mAdapter;
    private List<Product> productList = new ArrayList<>();
    private String viewTypeTag = "List";
    private String categoryTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        categoryTitle = getIntent().getStringExtra("category_title");
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

            String title = (categoryTitle != null) ? categoryTitle : "Campus Marketplace";
            applyCustomTitle(actionBar, title);
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

    private void loadMarketplaceData() {
        // Use local Data helper instead of repository
        Data localData = new Data();
        List<Product> allProducts = localData.getNewList();
        productList = new ArrayList<>();

        if (categoryTitle != null) {
            for (Product p : allProducts) {
                if (p.getAttribute().equalsIgnoreCase(categoryTitle)) {
                    productList.add(p);
                }
            }
        } else {
            productList = allProducts;
        }

        updateAdapter();
    }

    private void updateAdapter() {
        mAdapter = new ProductAdapter(productList, ProductActivity.this, viewTypeTag);
        recyclerView.setAdapter(mAdapter);
    }

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
        menuItem.setIcon(Converter.convertLayoutToImage(this, getCartCount(), R.drawable.ic_shopping_basket));
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAddProduct() { invalidateOptionsMenu(); }

    @Override
    public void onRemoveProduct() { invalidateOptionsMenu(); }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}