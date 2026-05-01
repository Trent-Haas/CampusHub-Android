package com.semo.cisproject.campushub.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.semo.cisproject.campushub.R;

public class Converter {

    public static Drawable convertLayoutToImage(Context mContext, int count, int drawableId) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.badge_icon_layout, null);

        //ImageView iconBadge = view.findViewById(R.id.icon_badge);
       //iconBadge.setImageResource(drawableId);

        if (count == 0) {
            //view.findViewById(R.id.counterValuePanel).setVisibility(View.GONE);
        } else {
            TextView textView = view.findViewById(R.id.count);
            textView.setText(String.valueOf(count));
        }

        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return new BitmapDrawable(mContext.getResources(), bitmap);
    }
}