package com.noxyspace.vinca.canvas.symbols;

import android.content.Context;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.noxyspace.vinca.R;
import com.noxyspace.vinca.canvas.SymbolLayout;

public class SymbolContainerCollapsed extends ImageView {
    public SymbolContainerCollapsed(Context context, int resId) {
        super(context);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, SymbolLayout.SYMBOL_DIMENSION, getResources().getDisplayMetrics()),
            (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, SymbolLayout.SYMBOL_DIMENSION - (2 * SymbolContainerLayout.CONTAINER_MARGIN), getResources().getDisplayMetrics())
        );

        int margin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, SymbolContainerLayout.CONTAINER_MARGIN, getResources().getDisplayMetrics());
        params.setMargins(0, margin, 0, margin);

        this.setLayoutParams(params);
        this.setImageResource(resId);
    }
}
