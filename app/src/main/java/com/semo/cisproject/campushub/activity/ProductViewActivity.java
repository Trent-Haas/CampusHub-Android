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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import androidx.appcompat.app.ActionBar;
import com.semo.cisproject.campushub.R;
import com.semo.cisproject.campushub.helper.Converter;
import com.semo.cisproject.campushub.model.Cart;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ProductViewActivity extends BaseActivity {

    private String _id, _title, _image, _description, _price, _attribute, _discount;
    private TextView title, description, price, attribute, discount, quantity;
    private ImageView imageView;
    private ProgressBar progressBar;
    private View addToCartBtn, shareBtn;
    private View quantityLayout;
    private int cartIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        Intent intent = getIntent();
        _id = intent.getStringExtra("id");
        _title = intent.getStringExtra("title");
        _image = intent.getStringExtra("image");
        _description = intent.getStringExtra("description");
        _price = intent.getStringExtra("price");
        _attribute = intent.getStringExtra("attribute");
        _discount = intent.getStringExtra("discount");

        setupToolbar();
        initializeViews();
        populateData();

        checkCartStatus();
    }

    private void initializeViews() {
        title = findViewById(R.id.detailed_name);
        description = findViewById(R.id.detailed_description);
        price = findViewById(R.id.detailed_price);
        attribute = findViewById(R.id.apv_attribute);
        discount = findViewById(R.id.apv_discount);
        imageView = findViewById(R.id.detailed_image);
        progressBar = findViewById(R.id.progressbar);
        addToCartBtn = findViewById(R.id.add_to_cart_btn);
        shareBtn = findViewById(R.id.apv_share);
        quantityLayout = findViewById(R.id.quantity_rl);
        quantity = findViewById(R.id.quantity);

        if (shareBtn != null) {
            shareBtn.setOnClickListener(v -> shareProduct());
        }
        if (addToCartBtn != null) {
            addToCartBtn.setOnClickListener(v -> addNewItemToCart());
        }

        View plusBtn = findViewById(R.id.quantity_plus);
        View minusBtn = findViewById(R.id.quantity_minus);

        if (plusBtn != null) plusBtn.setOnClickListener(v -> updateQuantity(1));
        if (minusBtn != null) minusBtn.setOnClickListener(v -> updateQuantity(-1));
    }

    private void setupToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            final Drawable upArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
            actionBar.setHomeAsUpIndicator(upArrow);
            applyCustomTitle(actionBar, _title);
        }
    }

    private void applyCustomTitle(ActionBar actionBar, String titleText) {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        TextView tv = new TextView(this);
        tv.setLayoutParams(lp);
        tv.setGravity(Gravity.CENTER);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setText(titleText);
        tv.setTextSize(20);
        tv.setTextColor(getResources().getColor(R.color.colorPrimary));
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(tv);
    }

    private void populateData() {
        if (title != null) title.setText(_title);
        if (description != null) description.setText(_description);
        if (attribute != null) attribute.setText(_attribute);
        if (price != null) price.setText(String.format("$%s", _price));

        if (discount != null) {
            if (_discount != null && !_discount.isEmpty()) {
                discount.setText(_discount);
                discount.setVisibility(View.VISIBLE);
            } else {
                discount.setVisibility(View.GONE);
            }
        }

        if (_image != null && imageView != null) {
            Picasso.get().load(_image).error(R.drawable.no_image).into(imageView, new Callback() {
                @Override public void onSuccess() { if(progressBar != null) progressBar.setVisibility(View.GONE); }
                @Override public void onError(Exception e) { if(progressBar != null) progressBar.setVisibility(View.GONE); }
            });
        }
    }

    private void checkCartStatus() {
        cartList = getCartList();
        if (cartList == null) {
            cartList = new ArrayList<>();
        }

        cartIndex = -1;
        for (int i = 0; i < cartList.size(); i++) {
            if (cartList.get(i).getId().equalsIgnoreCase(_id)) {
                cartIndex = i;
                showQuantityControls(cartList.get(i).getQuantity());
                return;
            }
        }
        showAddToCartButton();
    }

    private void addNewItemToCart() {
        if (cartList == null) cartList = new ArrayList<>();
        Cart cartItem = new Cart(_id, _title, _image, "$", _price, _attribute, "1", _price);
        cartList.add(cartItem);
        saveCartToStorage();
        onAddProduct();
        checkCartStatus();
    }

    private void updateQuantity(int delta) {
        if (cartIndex == -1 || quantity == null) return;

        int currentQty = Integer.parseInt(quantity.getText().toString());
        int newQty = currentQty + delta;

        if (newQty > 0) {
            double unitPrice = Double.parseDouble(_price);
            String subTotal = String.format("%.2f", unitPrice * newQty);

            cartList.get(cartIndex).setQuantity(String.valueOf(newQty));
            cartList.get(cartIndex).setSubTotal(subTotal);

            quantity.setText(String.valueOf(newQty));
            saveCartToStorage();
        } else {
            cartList.remove(cartIndex);
            saveCartToStorage();
            onRemoveProduct();
            showAddToCartButton();
            cartIndex = -1;
        }
    }

    private void saveCartToStorage() {
        localStorage.setCart(gson.toJson(cartList));
    }

    private void showQuantityControls(String qty) {
        if (addToCartBtn != null) addToCartBtn.setVisibility(View.GONE);
        if (quantityLayout != null) quantityLayout.setVisibility(View.VISIBLE);
        if (quantity != null) quantity.setText(qty);
    }

    private void showAddToCartButton() {
        if (addToCartBtn != null) addToCartBtn.setVisibility(View.VISIBLE);
        if (quantityLayout != null) quantityLayout.setVisibility(View.GONE);
    }

    private void shareProduct() {
        String shareBody = String.format("Check out this %s on CampusHub!\nPrice: $%s\n%s", _title, _price, _image);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(shareIntent, "Share Item via"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.cart_action);
        if (menuItem != null) {
            menuItem.setIcon(Converter.convertLayoutToImage(this, getCartCount(), R.drawable.ic_shopping_basket));
        }
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
    public void onAddProduct() { invalidateOptionsMenu(); }

    @Override
    public void onRemoveProduct() { invalidateOptionsMenu(); }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}