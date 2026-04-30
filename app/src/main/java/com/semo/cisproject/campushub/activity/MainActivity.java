package com.semo.cisproject.campushub.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.semo.cisproject.campushub.R;
import com.semo.cisproject.campushub.fragment.CategoryFragment;
import com.semo.cisproject.campushub.fragment.HomeFragment;
import com.semo.cisproject.campushub.fragment.MyOrderFragment;
import com.semo.cisproject.campushub.fragment.NewProductFragment;
import com.semo.cisproject.campushub.fragment.OffrersFragment;
import com.semo.cisproject.campushub.fragment.PopularProductFragment;
import com.semo.cisproject.campushub.fragment.ProfileFragment;
import com.semo.cisproject.campushub.fragment.AdminDashboardFragment;
import com.semo.cisproject.campushub.helper.Converter;
import com.semo.cisproject.campushub.model.User;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private User currentUser;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        centerToolbarTitle(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setupNavHeader(navigationView);

        if (savedInstanceState == null) {
            displaySelectedScreen(R.id.nav_home);
        }

        if (findViewById(R.id.fab) != null) {
            findViewById(R.id.fab).setVisibility(View.GONE);
        }
    }

    private void setupNavHeader(NavigationView navigationView) {
        View headerView = navigationView.getHeaderView(0);
        TextView navUser = headerView.findViewById(R.id.nav_header_name);

        String userString = localStorage.getUserLogin();
        currentUser = gson.fromJson(userString, User.class);

        if (currentUser != null) {
            navUser.setText(currentUser.getName());
            try {
                Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Merienda-Bold.ttf");
                navUser.setTypeface(tf);
            } catch (Exception ignored) {}
        }

        LinearLayout navFooter = findViewById(R.id.footer_text);
        navFooter.setOnClickListener(view -> {
            localStorage.logoutUser();
            Intent intent = new Intent(this, LoginRegisterActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        });
    }

    @SuppressLint("ResourceAsColor")
    private void centerToolbarTitle(@NonNull final Toolbar toolbar) {
        final CharSequence title = toolbar.getTitle();
        final ArrayList<View> outViews = new ArrayList<>(1);
        toolbar.findViewsWithText(outViews, title, View.FIND_VIEWS_WITH_TEXT);
        if (!outViews.isEmpty()) {
            final TextView titleView = (TextView) outViews.get(0);
            titleView.setGravity(Gravity.CENTER);
            titleView.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary)); // Brand Red

            try {
                Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Merienda-Bold.ttf");
                titleView.setTypeface(tf);
            } catch (Exception ignored) {}

            final Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) titleView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            toolbar.requestLayout();
        }
    }

    private void displaySelectedScreen(int itemId) {
        Fragment fragment = null;

        switch (itemId) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;
            case R.id.nav_profile:
                fragment = new ProfileFragment();
                break;
            case R.id.nav_category:
                fragment = new CategoryFragment();
                break;
            case R.id.nav_popular_products:
                fragment = new PopularProductFragment();
                break;
            case R.id.nav_new_product:
                fragment = new NewProductFragment();
                break;
            case R.id.nav_offers:
                fragment = new OffrersFragment();
                break;
            case R.id.nav_my_order:
                fragment = new MyOrderFragment();
                break;
            case R.id.nav_admin_dashboard: // Hook for Data Scientist
                fragment = new AdminDashboardFragment();
                break;
            case R.id.nav_my_cart:
                startActivity(new Intent(this, CartActivity.class));
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left);
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem = menu.findItem(R.id.cart_action);

        menuItem.setIcon(Converter.convertLayoutToImage(
                MainActivity.this,
                getCartCount(),
                R.drawable.ic_shopping_basket));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.cart_action) {
            startActivity(new Intent(this, CartActivity.class));
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return true;
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
    public void updateTotalPrice() {
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}