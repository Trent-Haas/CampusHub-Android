package com.semo.cisproject.campushub.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.semo.cisproject.campushub.R;

public class CustomToast {

    /**
     * Displays a custom stylized toast at the top of the screen.
     * Standardized for the CampusHub marketplace error reporting.
     *
     * @param context Current application context
     * @param view    The view group used for finding the root layout
     * @param message The error or notification string to display
     */
    public void Show_Toast(@NonNull Context context, @NonNull View view, String message) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View layout = inflater.inflate(R.layout.custom_toast,
                view.findViewById(R.id.toast_message));

        TextView text = layout.findViewById(R.id.toast_message);
        text.setText(message);

        Toast toast = new Toast(context);

        toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 0);

        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);

        toast.show();
    }
}