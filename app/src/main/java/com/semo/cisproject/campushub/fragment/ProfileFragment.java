package com.semo.cisproject.campushub.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.gson.Gson;
import com.semo.cisproject.campushub.R;
import com.semo.cisproject.campushub.activity.LoginRegisterActivity;
import com.semo.cisproject.campushub.model.User;
import com.semo.cisproject.campushub.util.LocalStorage;

public class ProfileFragment extends Fragment {
    private EditText nameEditText, emailEditText, phoneEditText, addressEditText;
    private Button updateBtn;
    private TextView logoutTv;
    private LocalStorage localStorage;
    private User user;

    public ProfileFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) getActivity().setTitle("User Profile");

        localStorage = new LocalStorage(getContext());

        nameEditText = view.findViewById(R.id.profile_name);
        emailEditText = view.findViewById(R.id.profile_email);
        phoneEditText = view.findViewById(R.id.profile_phone);
        addressEditText = view.findViewById(R.id.profile_address);
        updateBtn = view.findViewById(R.id.update_profile_btn);
        logoutTv = view.findViewById(R.id.logout_tv);

        String userJson = localStorage.getUserLogin();
        if (userJson != null && !userJson.isEmpty()) {
            try {
                user = new Gson().fromJson(userJson, User.class);
                if (nameEditText != null) nameEditText.setText(user.getName());
                if (emailEditText != null) emailEditText.setText(user.getEmail());
                if (phoneEditText != null) phoneEditText.setText(user.getMobile());
            } catch (Exception ignored) {}
        }

        if (addressEditText != null) addressEditText.setText(localStorage.getUserAddress());

        if (logoutTv != null) {
            logoutTv.setOnClickListener(v -> {
                localStorage.logoutUser();
                Intent intent = new Intent(getActivity(), LoginRegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                if (getActivity() != null) getActivity().finish();
            });
        }

        if (updateBtn != null) {
            updateBtn.setOnClickListener(v -> {
                if (user != null) {
                    if (nameEditText != null) user.setName(nameEditText.getText().toString());
                    if (phoneEditText != null) user.setMobile(phoneEditText.getText().toString());
                    localStorage.createUserLoginSession(new Gson().toJson(user));
                }
                if (addressEditText != null) localStorage.setUserAddress(addressEditText.getText().toString());
            });
        }
    }
}