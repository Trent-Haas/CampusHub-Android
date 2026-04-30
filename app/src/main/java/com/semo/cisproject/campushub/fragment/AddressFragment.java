package com.semo.cisproject.campushub.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.semo.cisproject.campushub.R;
import com.semo.cisproject.campushub.model.User;
import com.semo.cisproject.campushub.model.UserAddress;
import com.semo.cisproject.campushub.util.LocalStorage;
import com.semo.cisproject.campushub.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressFragment extends Fragment {

    private EditText name, email, mobile, address, zip;
    private Spinner citySpinner, stateSpinner;
    private ArrayList<String> stateList, cityList;
    private String selectedState, selectedCity;
    private LocalStorage localStorage;
    private Gson gson;
    private UserAddress userAddress;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_address, container, false);

        localStorage = new LocalStorage(getContext());
        gson = new Gson();

        initViews(v);
        loadUserData();
        setupSpinners();

        v.findViewById(R.id.txt_pyment).setOnClickListener(view -> validateAndProceed());

        return v;
    }

    private void initViews(View v) {
        name = v.findViewById(R.id.sa_name);
        email = v.findViewById(R.id.sa_email);
        mobile = v.findViewById(R.id.sa_mobile);
        address = v.findViewById(R.id.sa_address);
        zip = v.findViewById(R.id.sa_zip);
        citySpinner = v.findViewById(R.id.citySpinner);
        stateSpinner = v.findViewById(R.id.stateSpinner);
    }

    private void loadUserData() {
        String userString = localStorage.getUserLogin();
        User user = gson.fromJson(userString, User.class);

        String addressString = localStorage.getUserAddress();
        userAddress = gson.fromJson(addressString, UserAddress.class);

        if (user != null) {
            name.setText(user.getName());
            email.setText(user.getEmail());
            mobile.setText(user.getMobile());
        }

        if (userAddress != null) {
            name.setText(userAddress.getName());
            email.setText(userAddress.getEmail());
            mobile.setText(userAddress.getMobile());
            address.setText(userAddress.getAddress());
            zip.setText(userAddress.getZip());
        }
    }

    private void setupSpinners() {
        stateList = new ArrayList<>();
        cityList = new ArrayList<>();

        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinnertextview, cityList);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(cityAdapter);

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("state.json"));
            JSONArray states = obj.getJSONArray("statelist");
            for (int i = 0; i < states.length(); i++) {
                stateList.add(states.getJSONObject(i).getString("State"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinnertextview, stateList);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateAdapter);

        if (userAddress != null) {
            stateSpinner.setSelection(stateAdapter.getPosition(userAddress.getState()));
        }

        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedState = stateSpinner.getSelectedItem().toString();
                updateCitySpinner(selectedState, cityAdapter);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCity = citySpinner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void updateCitySpinner(String stateName, ArrayAdapter<String> adapter) {
        cityList.clear();
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset("cityState.json"));
            JSONArray cities = obj.getJSONArray("citylist");
            for (int i = 0; i < cities.length(); i++) {
                JSONObject jo = cities.getJSONObject(i);
                if (jo.getString("State").equalsIgnoreCase(stateName)) {
                    cityList.add(jo.getString("city"));
                }
            }
            adapter.notifyDataSetChanged();
            if (userAddress != null && stateName.equalsIgnoreCase(userAddress.getState())) {
                citySpinner.setSelection(adapter.getPosition(userAddress.getCity()));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void validateAndProceed() {
        String _name = name.getText().toString().trim();
        String _email = email.getText().toString().trim();
        String _mobile = mobile.getText().toString().trim();
        String _address = address.getText().toString().trim();
        String _zip = zip.getText().toString().trim();

        Pattern p = Pattern.compile(Utils.regEx);
        Matcher m = p.matcher(_email);

        if (_name.isEmpty()) {
            name.setError("Enter Name");
            name.requestFocus();
        } else if (_email.isEmpty() || !m.find()) {
            email.setError("Enter Valid Email");
            email.requestFocus();
        } else if (_mobile.length() < 10) {
            mobile.setError("Enter Valid Mobile Number");
            mobile.requestFocus();
        } else if (_address.isEmpty()) {
            address.setError("Enter Address");
            address.requestFocus();
        } else if (_zip.isEmpty()) {
            zip.setError("Enter Zip Code");
            zip.requestFocus();
        } else {
            userAddress = new UserAddress(_name, _email, _mobile, _address, selectedState, selectedCity, _zip);
            localStorage.setUserAddress(gson.toJson(userAddress));

            if (getActivity() != null) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left);
                ft.replace(R.id.content_frame, new PaymentFragment());
                ft.commit();
            }
        }
    }

    private String loadJSONFromAsset(String fileName) {
        String json;
        try {
            InputStream is = getContext().getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            getActivity().setTitle("Delivery Address");
        }
    }
}