package com.semo.cisproject.campushub.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.semo.cisproject.campushub.R;
import com.semo.cisproject.campushub.util.LocalStorage;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_welcome);
        localStorage = new LocalStorage(getApplicationContext());

        new Handler().postDelayed(() -> {

            if (localStorage.isUserLoggedIn()) {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            } else {
                startActivity(new Intent(WelcomeActivity.this, LoginRegisterActivity.class));
            }

            finish();
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);

        }, 2500);
    }
}