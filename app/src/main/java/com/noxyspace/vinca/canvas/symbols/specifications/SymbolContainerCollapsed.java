package com.noxyspace.vinca.canvas.symbols.specifications;

import android.content.Context;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.noxyspace.vinca.canvas.symbols.SymbolLayout;

public class SymbolContainerCollapsed extends ImageView {
    public SymbolContainerCollapsed(Context context, int resId) {
        this(context, resId, 1.0f, 1.0f);
    }

    public SymbolContainerCollapsed(Context context, int resId, float widthScale, float heightScale) {
        super(context);

        float layoutWidth = SymbolLayout.SYMBOL_DIMENSION * widthScale;
        float layoutHeight = SymbolLayout.SYMBOL_DIMENSION - (2 * SymbolContainerLayout.CONTAINER_MARGIN) * heightScale;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, layoutWidth, getResources().getDisplayMetrics()),
            (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, layoutHeight, getResources().getDisplayMetrics())
        );

        int margin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, SymbolContainerLayout.CONTAINER_MARGIN, getResources().getDisplayMetrics());
        params.setMargins(0, margin, 0, margin);

        this.setLayoutParams(params);
        this.setImageResource(resId);
    }
}