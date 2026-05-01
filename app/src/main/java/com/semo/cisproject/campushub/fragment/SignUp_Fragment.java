package com.semo.cisproject.campushub.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.google.gson.Gson;
import com.semo.cisproject.campushub.R;
import com.semo.cisproject.campushub.activity.LoginRegisterActivity;
import com.semo.cisproject.campushub.activity.MainActivity;
import com.semo.cisproject.campushub.model.User;
import com.semo.cisproject.campushub.util.CustomToast;
import com.semo.cisproject.campushub.util.SecurityUtils;
import com.semo.cisproject.campushub.util.Utils;
import com.semo.cisproject.campushub.util.LocalStorage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp_Fragment extends Fragment implements OnClickListener {
    private static View view;
    private static EditText fullName, emailId, mobileNumber, password;
    private static TextView login;
    private static Button signUpButton;
    private static CheckBox terms_conditions;
    private ProgressDialog progressDialog;
    private User user;
    private LocalStorage localStorage;
    private Gson gson;

    public SignUp_Fragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.signup_layout, container, false);
        initViews();
        setListeners();
        return view;
    }

    private void initViews() {
        fullName = view.findViewById(R.id.fullName);
        emailId = view.findViewById(R.id.userEmailId);
        mobileNumber = view.findViewById(R.id.mobileNumber);
        password = view.findViewById(R.id.password);
        signUpButton = view.findViewById(R.id.signUpBtn);
        login = view.findViewById(R.id.already_user);
        terms_conditions = view.findViewById(R.id.terms_conditions);
        progressDialog = new ProgressDialog(getContext());
        @SuppressLint("ResourceType")
        XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(), xrp);
            login.setTextColor(csl);
            terms_conditions.setTextColor(csl);
        } catch (Exception ignored) {}
    }

    private void setListeners() {
        signUpButton.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpBtn: checkValidation(); break;
            case R.id.already_user: new LoginRegisterActivity().replaceLoginFragment(); break;
        }
    }

    private void checkValidation() {
        String getFullName = fullName.getText().toString().trim();
        String getEmailId = emailId.getText().toString().trim().toLowerCase();
        String getMobileNumber = mobileNumber.getText().toString().trim();
        String getPassword = password.getText().toString();
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        if (getFullName.length() == 0) {
            fullName.setError("Enter your name");
            fullName.requestFocus(); return;
        }
        if (getEmailId.length() == 0) {
            emailId.setError("Enter your email");
            emailId.requestFocus(); return;
        }
        if (!m.find()) {
            emailId.setError("Enter a valid email");
            emailId.requestFocus(); return;
        }
        if (!SecurityUtils.isEduEmail(getEmailId)) {
            emailId.setError("Only .edu email addresses are allowed");
            emailId.requestFocus(); return;
        }
        if (getMobileNumber.length() == 0) {
            mobileNumber.setError("Enter your mobile number");
            mobileNumber.requestFocus(); return;
        }
        if (!SecurityUtils.isStrongEnoughPassword(getPassword)) {
            password.setError("Password must be at least 6 characters");
            password.requestFocus(); return;
        }
        if (!terms_conditions.isChecked()) {
            new CustomToast().Show_Toast(getActivity(), view, "Accept Terms & Conditions");
            return;
        }
        String hashedPassword = SecurityUtils.sha256(getPassword);
        user = new User();
        user.setId("1");
        user.setName(getFullName);
        user.setEmail(getEmailId);
        user.setMobile(getMobileNumber);
        user.setPassword(hashedPassword);
        user.setDiscountTier("student");
        gson = new Gson();
        String userString = gson.toJson(user);
        localStorage = new LocalStorage(getContext());
        localStorage.createUserLoginSession(userString);
        progressDialog.setMessage("Registering Data....");
        progressDialog.show();
        new Handler().postDelayed(() -> {
            progressDialog.dismiss();
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();
            getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        }, 1200);
    }
}