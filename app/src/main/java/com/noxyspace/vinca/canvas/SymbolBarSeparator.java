package com.noxyspace.vinca.canvas;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class SymbolBarSeparator extends View {
    public SymbolBarSeparator(Context context) {
        super(context);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()),
            LinearLayout.LayoutParams.MATCH_PARENT
        );

        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
        params.setMargins(margin * 3, margin, margin, margin);

        this.setLayoutParams(params);
        this.setBackgroundColor(Color.BLACK);
    }
}
