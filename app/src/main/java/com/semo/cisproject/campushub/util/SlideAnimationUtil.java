package com.semo.cisproject.campushub.util;

import android.content.Context;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;

import com.semo.cisproject.campushub.R;

/**
 * Utility class for handling view-level slide animations.
 * Provides a standardized way to transition elements within the CampusHub marketplace.
 */
public class SlideAnimationUtil {

    /**
     * Animates a view so that it slides in from the left of its container.
     */
    public static void slideInFromLeft(@NonNull Context context, @NonNull View view) {
        runSimpleAnimation(context, view, R.anim.slide_from_left);
    }

    /**
     * Animates a view so that it slides out of view to the left.
     */
    public static void slideOutToLeft(@NonNull Context context, @NonNull View view) {
        runSimpleAnimation(context, view, R.anim.slide_to_left);
    }

    /**
     * Animates a view so that it slides in from the right of its container.
     */
    public static void slideInFromRight(@NonNull Context context, @NonNull View view) {
        runSimpleAnimation(context, view, R.anim.slide_from_right);
    }

    /**
     * Animates a view so that it slides out of view to the right.
     */
    public static void slideOutToRight(@NonNull Context context, @NonNull View view) {
        runSimpleAnimation(context, view, R.anim.slide_to_right);
    }

    /**
     * Internal helper to load and execute an animation on a specific View.
     */
    private static void runSimpleAnimation(Context context, View view, int animationId) {
        if (context != null && view != null) {
            view.startAnimation(AnimationUtils.loadAnimation(context, animationId));
        }
    }
}