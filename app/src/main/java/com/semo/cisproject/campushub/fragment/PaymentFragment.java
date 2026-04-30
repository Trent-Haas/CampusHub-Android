package com.semo.cisproject.campushub.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.semo.cisproject.campushub.R;
import com.semo.cisproject.campushub.activity.BaseActivity;

import java.util.Locale;

public class PaymentFragment extends Fragment {

    private RadioGroup paymentGroup;
    private FrameLayout cardFrame;
    private RadioButton card, cash;
    private LinearLayout payll;
    private TextView pay;

    public PaymentFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        initViews(view);

        if (getActivity() instanceof BaseActivity) {
            double amount = ((BaseActivity) getActivity()).getTotalPrice();
            pay.setText(String.format(Locale.US, "Total to Pay: $%.2f", amount));
        }

        payll.setOnClickListener(v -> navigateToConfirm());

        paymentGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.cash_on_delivery) {
                navigateToConfirm();
            }
        });

        return view;
    }

    private void initViews(View view) {
        paymentGroup = view.findViewById(R.id.payment_group);
        card = view.findViewById(R.id.card_payment);
        cash = view.findViewById(R.id.cash_on_delivery);
        cardFrame = view.findViewById(R.id.card_frame);
        payll = view.findViewById(R.id.pay_ll);
        pay = view.findViewById(R.id.total_pay);
    }

    private void navigateToConfirm() {
        if (getActivity() != null) {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.slide_from_right, R.anim.slide_to_left);
            ft.replace(R.id.content_frame, new ConfirmFragment());
            ft.commit();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            getActivity().setTitle("Payment Method");
        }
    }
}