package com.noxyspace.vinca.canvas.symbols.specifications;

import android.content.Context;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.noxyspace.vinca.canvas.symbols.SymbolLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.SymbolContainerLayout;

public class SymbolTitle extends TextView {
    private static final int TEXT_HEIGHT = 10;

    public SymbolTitle(Context context) {
        this(context, "");
    }

    public SymbolTitle(Context context, String title) {
        super(context);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, SymbolLayout.SYMBOL_DIMENSION, getResources().getDisplayMetrics()),
            (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, TEXT_HEIGHT, getResources().getDisplayMetrics())
        );

        int margin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, SymbolContainerLayout.CONTAINER_MARGIN, getResources().getDisplayMetrics());
        params.setMargins(0, margin, 0, margin);

        this.setLayoutParams(params);
        this.setText(title);
    }

    protected void setTitle(String title) {

    }
}
