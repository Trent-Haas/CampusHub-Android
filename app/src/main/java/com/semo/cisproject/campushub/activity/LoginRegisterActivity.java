package com.semo.cisproject.campushub.activity;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.semo.cisproject.campushub.R;
import com.semo.cisproject.campushub.fragment.Login_Fragment;
import com.semo.cisproject.campushub.util.Utils;

public class LoginRegisterActivity extends BaseActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frameContainer, new Login_Fragment(), Utils.Login_Fragment)
                    .commit();
        }

        findViewById(R.id.close_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginRegisterActivity.this.finish();
                LoginRegisterActivity.this.overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
        });
    }

     public void replaceLoginFragment() {
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.frameContainer, new Login_Fragment(), Utils.Login_Fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        Fragment signUpFragment = fragmentManager.findFragmentByTag(Utils.SignUp_Fragment);
        Fragment forgotPasswordFragment = fragmentManager.findFragmentByTag(Utils.ForgotPassword_Fragment);

        if (signUpFragment != null || forgotPasswordFragment != null) {
            replaceLoginFragment();
        } else {
            super.onBackPressed();
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        }
    }
}