package com.semo.cisproject.campushub.fragment;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.semo.cisproject.campushub.R;
import com.semo.cisproject.campushub.activity.LoginRegisterActivity;
import com.semo.cisproject.campushub.util.CustomToast;
import com.semo.cisproject.campushub.util.SecurityUtils;
import com.semo.cisproject.campushub.util.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPassword_Fragment extends Fragment implements OnClickListener {
    private View view;
    private EditText emailId;
    private TextView submit, back;

    public ForgotPassword_Fragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.forgotpassword_layout, container, false);
        initViews();
        setListeners();
        return view;
    }

    private void initViews() {
        emailId = view.findViewById(R.id.registered_emailid);
        //submit = view.findViewById(R.id.forgot_button);
        //back = view.findViewById(R.id.backToLoginBtn);

        @SuppressLint("ResourceType")
        XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(), xrp);
            back.setTextColor(csl);
            submit.setTextColor(csl);
        } catch (Exception ignored) {
        }
    }

    private void setListeners() {
        //back.setOnClickListener(this);
        //submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        /*int id = v.getId();
        if (id == R.id.backToLoginBtn) {
            if (getActivity() instanceof LoginRegisterActivity) {
                ((LoginRegisterActivity) getActivity()).replaceLoginFragment();
            }
        } else if (id == R.id.forgot_button) {
            submitButtonTask();
        }*/
    }

    private void submitButtonTask() {
        String getEmailId = emailId.getText().toString().trim();
        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(getEmailId);

        if (getEmailId.isEmpty()) {
            new CustomToast().Show_Toast(getActivity(), view, "Please enter your Email Id.");
        } else if (!m.find()) {
            new CustomToast().Show_Toast(getActivity(), view, "Your Email Id is Invalid.");
        } else if (!SecurityUtils.isEduEmail(getEmailId)) {
            new CustomToast().Show_Toast(getActivity(), view, "Only .edu email addresses are allowed.");
        } else {
            Toast.makeText(getActivity(), "Recovery email sent if account exists.", Toast.LENGTH_SHORT).show();
        }
    }
}