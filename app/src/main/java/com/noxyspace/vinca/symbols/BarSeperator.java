package com.noxyspace.vinca.symbols;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import static android.support.design.R.attr.background;

/**
 * Created by Andreas on 10-01-2017.
 */

public class BarSeperator extends View {
    public BarSeperator(Context context, ViewGroup view) {
        super(context);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()),
            LinearLayout.LayoutParams.MATCH_PARENT
        );

        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
        params.setMargins(margin, margin, margin, margin);

        this.setLayoutParams(params);
        this.setBackgroundColor(Color.BLACK);

        view.addView(this);
    }
}
