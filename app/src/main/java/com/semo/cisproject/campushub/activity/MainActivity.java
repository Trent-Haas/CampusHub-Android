package com.semo.cisproject.campushub.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.semo.cisproject.campushub.R;
import com.semo.cisproject.campushub.fragment.CategoryFragment;
import com.semo.cisproject.campushub.fragment.HomeFragment;
import com.semo.cisproject.campushub.fragment.NewProductFragment;
import com.semo.cisproject.campushub.fragment.OffersFragment;
import com.semo.cisproject.campushub.fragment.OrdersFragment;
import com.semo.cisproject.campushub.fragment.PopularProductFragment;
import com.semo.cisproject.campushub.fragment.ProfileFragment;
import com.semo.cisproject.campushub.model.User;
import com.semo.cisproject.campushub.util.LocalStorage;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) getSupportActionBar().setDisplayShowTitleEnabled(false);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.profile_name);
        TextView navEmail = headerView.findViewById(R.id.user_email_header);

        LocalStorage localStorage = new LocalStorage(this);
        String userStr = localStorage.getUserLogin();
        if (userStr != null && !userStr.isEmpty()) {
            try {
                User user = new Gson().fromJson(userStr, User.class);
                if (navUsername != null) navUsername.setText(user.getName());
                if (navEmail != null) navEmail.setText(user.getEmail());
            } catch (Exception ignored) {}
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, new HomeFragment()).commit();
        }

        if (getIntent() != null && getIntent().getBooleanExtra("ORDER_SUCCESS", false)) {
            showSuccessBanner();
        }

        View logoutButton = findViewById(R.id.footer_text);
        if (logoutButton != null) {
            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawer.closeDrawer(GravityCompat.START);
                    performLogout();
                }
            });
        }
    }

    private void showSuccessBanner() {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Order Confirmed! Receipt sent to your email.", Snackbar.LENGTH_INDEFINITE);
        View view = snackbar.getView();
        view.setBackgroundColor(Color.parseColor("#4CAF50"));
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        snackbar.setActionTextColor(Color.WHITE);
        snackbar.setAction("DISMISS", v -> snackbar.dismiss());
        snackbar.show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        String title = item.getTitle() != null ? item.getTitle().toString() : "";

        if (title.contains("Home") || title.contains("Search")) {
            selectedFragment = new HomeFragment();
        }
        else if (title.contains("New Products")) selectedFragment = new NewProductFragment();
        else if (title.contains("Profile")) selectedFragment = new ProfileFragment();
        else if (title.contains("Offers")) selectedFragment = new OffersFragment();
        else if (title.contains("Popular")) selectedFragment = new PopularProductFragment();
        else if (title.contains("Category")) selectedFragment = new CategoryFragment();
        else if (title.contains("Order")) selectedFragment = new OrdersFragment();
        else if (title.contains("Cart")) {
            startActivity(new Intent(this, CartActivity.class));
            drawer.closeDrawer(GravityCompat.START);
            return true;
        } else if (title.contains("Logout") || title.contains("Log Out")) {
            performLogout();
            return true;
        }

        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer, selectedFragment).commit();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void performLogout() {
        if (localStorage != null) {
            localStorage.deleteCart();
        }

        Intent intent = new Intent(MainActivity.this, LoginRegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}