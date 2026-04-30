package com.semo.cisproject.campushub.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.AppCompatEditText;

public class CampusHubTypography {

    private static void applyFont(Context context, android.widget.TextView view, String fontPath) {
        if (!view.isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/" + fontPath);
            view.setTypeface(tf);
        }
    }


    public static class MyTextView extends AppCompatTextView {
        public MyTextView(Context c, AttributeSet a) { super(c, a); applyFont(c, this, "MavenPro-Regular.ttf"); }
        public MyTextView(Context c, AttributeSet a, int s) { super(c, a, s); applyFont(c, this, "MavenPro-Regular.ttf"); }
    }

    public static class MyTextViewMeriendaRegular extends AppCompatTextView {
        public MyTextViewMeriendaRegular(Context c, AttributeSet a) { super(c, a); applyFont(c, this, "Merienda-Regular.ttf"); }
    }

    public static class MyTextViewMeriendaBold extends AppCompatTextView {
        public MyTextViewMeriendaBold(Context c, AttributeSet a) { super(c, a); applyFont(c, this, "Merienda-Bold.ttf"); }
    }

    public static class MyTextViewOleoScriptBold extends AppCompatTextView {
        public MyTextViewOleoScriptBold(Context c, AttributeSet a) { super(c, a); applyFont(c, this, "OleoScript-Bold.ttf"); }
    }

    public static class MyTextViewChangoRegular extends AppCompatTextView {
        public MyTextViewChangoRegular(Context c, AttributeSet a) { super(c, a); applyFont(c, this, "Chango-Regular.ttf"); }
    }

    public static class MyTextViewRanchoRegular extends AppCompatTextView {
        public MyTextViewRanchoRegular(Context c, AttributeSet a) { super(c, a); applyFont(c, this, "Rancho-Regular.ttf"); }
    }

    public static class MyTextViewSansBold extends AppCompatTextView {
        public MyTextViewSansBold(Context c, AttributeSet a) { super(c, a); applyFont(c, this, "OpenSans-Semibold.ttf"); }
    }

    public static class MyTextViewSansRegular extends AppCompatTextView {
        public MyTextViewSansRegular(Context c, AttributeSet a) { super(c, a); applyFont(c, this, "OpenSans-Regular.ttf"); }
    }

    public static class MyEditText extends AppCompatEditText {
        public MyEditText(Context c, AttributeSet a) { super(c, a); applyFont(c, this, "MavenPro-Regular.ttf"); }
        public MyEditText(Context c, AttributeSet a, int s) { super(c, a, s); applyFont(c, this, "MavenPro-Regular.ttf"); }
    }
}