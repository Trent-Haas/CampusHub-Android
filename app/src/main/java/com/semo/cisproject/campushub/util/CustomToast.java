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

        // Layout Inflater for inflating custom view
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Inflate the layout over view
        // Note: Using the root layout ID from your custom_toast.xml
        View layout = inflater.inflate(R.layout.custom_toast,
                view.findViewById(R.id.toast_root));

        // Get TextView ID and set the message
        TextView text = layout.findViewById(R.id.toast_error);
        text.setText(message);

        Toast toast = new Toast(context);

        // Set Toast gravity to top and fill horizontal to match the app's notification style
        toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 0);

        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);

        toast.show();
    }
}