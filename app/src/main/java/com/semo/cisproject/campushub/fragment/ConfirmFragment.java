package com.semo.cisproject.campushub.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.semo.cisproject.campushub.R;
import com.semo.cisproject.campushub.activity.BaseActivity;
import com.semo.cisproject.campushub.activity.CartActivity;
import com.semo.cisproject.campushub.activity.MainActivity;
import com.semo.cisproject.campushub.adapter.CheckoutCartAdapter;
import com.semo.cisproject.campushub.model.Cart;
import com.semo.cisproject.campushub.model.Order;
import com.semo.cisproject.campushub.util.LocalStorage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class ConfirmFragment extends Fragment {

    private LocalStorage localStorage;
    private List<Cart> cartList = new ArrayList<>();
    private Gson gson;
    private RecyclerView recyclerView;
    private CheckoutCartAdapter adapter;
    private TextView total, shipping, totalAmount;
    private double totalPriceValue, shippingValue, finalAmountValue;
    private ProgressDialog progressDialog;
    private List<Order> orderList = new ArrayList<>();
    private String orderNo;
    private String id;

    public ConfirmFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirm, container, false);

        initViews(view);

        localStorage = new LocalStorage(getContext());
        gson = new Gson();

        if (getActivity() != null) {
            orderList = ((BaseActivity) getActivity()).getOrderList();
            totalPriceValue = ((BaseActivity) getActivity()).getTotalPrice();
        }

        orderNo = "Order #" + (100000 + new Random().nextInt(900000));
        id = orderList.isEmpty() ? "1" : String.valueOf(orderList.size() + 1);

        calculatePricing();
        setUpCartRecyclerview();

        view.findViewById(R.id.back).setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), CartActivity.class));
            if (getActivity() != null) {
                getActivity().overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            }
        });

        view.findViewById(R.id.place_order).setOnClickListener(v -> {
            progressDialog.setMessage("Processing Order...");
            progressDialog.show();
            processOrderPlacement();
        });

        return view;
    }

    private void initViews(View v) {
        recyclerView = v.findViewById(R.id.cart_rv);
        totalAmount = v.findViewById(R.id.total_amount);
        total = v.findViewById(R.id.total);
        shipping = v.findViewById(R.id.shipping_amount);
        progressDialog = new ProgressDialog(getContext());
    }

    private void calculatePricing() {
        shippingValue = 0.0;
        finalAmountValue = totalPriceValue + shippingValue;

        total.setText(String.format(Locale.US, "$%.2f", totalPriceValue));
        shipping.setText(String.format(Locale.US, "$%.2f", shippingValue));
        totalAmount.setText(String.format(Locale.US, "$%.2f", finalAmountValue));
    }

    private void processOrderPlacement() {
        new Handler().postDelayed(() -> {
            progressDialog.dismiss();
            saveOrderLocally();
            showSuccessDialog();
        }, 2500);
    }

    private void saveOrderLocally() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        Order order = new Order(id, orderNo, currentDateandTime, String.format(Locale.US, "$%.2f", finalAmountValue), "Pending");
        orderList.add(order);

        localStorage.setOrder(gson.toJson(orderList));
        localStorage.deleteCart();

        if (getActivity() != null) {
            ((BaseActivity) getActivity()).onRemoveProduct();
        }
    }

    private void showSuccessDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.success_dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnCancelListener(d -> {
            startActivity(new Intent(getContext(), MainActivity.class));
            if (getActivity() != null) {
                getActivity().finish();
            }
        });
        dialog.show();
    }

    private void setUpCartRecyclerview() {
        if (getContext() != null) {
            cartList = ((BaseActivity) getContext()).getCartList();
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            adapter = new CheckoutCartAdapter(cartList, getContext());
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            getActivity().setTitle("Confirm Order");
        }
    }
}