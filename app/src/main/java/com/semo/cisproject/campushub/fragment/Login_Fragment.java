package com.semo.cisproject.campushub.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.gson.Gson;
import com.semo.cisproject.campushub.R;
import com.semo.cisproject.campushub.activity.MainActivity;
import com.semo.cisproject.campushub.model.User;
import com.semo.cisproject.campushub.util.CustomToast;
import com.semo.cisproject.campushub.util.LocalStorage;
import com.semo.cisproject.campushub.util.SecurityUtils;
import com.semo.cisproject.campushub.util.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login_Fragment extends Fragment implements OnClickListener {
    private static View view;
    private static EditText emailid, password;
    private static Button loginButton;
    private static TextView forgotPassword, signUp;
    private static CheckBox show_hide_password;
    private static Animation shakeAnimation;
    private static FragmentManager fragmentManager;
    private ProgressDialog progressDialog;
    private LocalStorage localStorage;
    private User user;

    public Login_Fragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_layout, container, false);
        initViews();
        setListeners();
        return view;
    }

    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();
        emailid = view.findViewById(R.id.login_emailid);
        password = view.findViewById(R.id.login_password);
        loginButton = view.findViewById(R.id.loginBtn);
        forgotPassword = view.findViewById(R.id.forgot_password);
        signUp = view.findViewById(R.id.createAccount);
        show_hide_password = view.findViewById(R.id.show_hide_password);

        progressDialog = new ProgressDialog(getContext());
        localStorage = new LocalStorage(getContext());

        String userString = localStorage.getUserLogin();
        Gson gson = new Gson();
        try {
            user = gson.fromJson(userString, User.class);
        } catch (Exception e) {
            user = null;
        }

        shakeAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);

        @SuppressLint("ResourceType")
        XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(), xrp);
            forgotPassword.setTextColor(csl);
            show_hide_password.setTextColor(csl);
            signUp.setTextColor(csl);
        } catch (Exception ignored) {}
    }

    private void setListeners() {
        loginButton.setOnClickListener(this);
        forgotPassword.setOnClickListener(this);
        signUp.setOnClickListener(this);
        show_hide_password.setOnCheckedChangeListener((button, isChecked) -> {
            if (isChecked) {
                show_hide_password.setText(R.string.hide_pwd);
                password.setInputType(InputType.TYPE_CLASS_TEXT);
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                show_hide_password.setText(R.string.show_pwd);
                password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn: checkValidation(); break;
            case R.id.forgot_password:
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer, new ForgotPassword_Fragment(), Utils.ForgotPassword_Fragment).commit();
                break;
            case R.id.createAccount:
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer, new SignUp_Fragment(), Utils.SignUp_Fragment).commit();
                break;
        }
    }

    private void checkValidation() {
        final String getEmailId = emailid.getText().toString().trim();
        final String getPassword = password.getText().toString();
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        if (getEmailId.length() == 0 || getPassword.length() == 0) {
            loginButton.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(getActivity(), view, "Enter both credentials.");
            vibrate(200);
            return;
        }
        if (!m.find()) {
            new CustomToast().Show_Toast(getActivity(), view, "Your Email Id is Invalid.");
            vibrate(200);
            return;
        }
        progressDialog.setMessage("Please Wait....");
        progressDialog.show();
        new Handler().postDelayed(() -> {
            try {
                if (user == null) {
                    new CustomToast().Show_Toast(getActivity(), view, "Please register first.");
                    return;
                }
                String inputHash = SecurityUtils.sha256(getPassword);
                String storedHash = user.getPassword();
                boolean emailMatches = SecurityUtils.safeEquals(
                        user.getEmail() == null ? "" : user.getEmail().trim().toLowerCase(),
                        getEmailId.toLowerCase()
                );
                boolean passwordMatches = SecurityUtils.safeEquals(storedHash, inputHash);
                if (!emailMatches || !passwordMatches) {
                    new CustomToast().Show_Toast(getActivity(), view, "Please check email or password.");
                    vibrate(200);
                    return;
                }
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
            } finally {
                progressDialog.dismiss();
            }
        }, 1200);
    }

    public void vibrate(int duration) {
        Vibrator vibs = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        if (vibs != null) vibs.vibrate(duration);
    }
}