package com.noxyspace.vinca.canvas.symbols.specifications.containers.collapsed;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.util.TypedValue;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.noxyspace.vinca.canvas.symbols.SymbolLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.containers.SymbolContainerLayout;

public class SymbolContainerCollapsed extends ImageView {
    public SymbolContainerCollapsed(Context context, int resId) {
        this(context, resId, 1.0f, 1.0f);
    }

    public SymbolContainerCollapsed(Context context, int resId, float widthScale, float heightScale) {
        super(context);

        float layoutWidth = SymbolLayout.SYMBOL_DIMENSION * widthScale;
        float layoutHeight = SymbolLayout.SYMBOL_DIMENSION - (2 * SymbolContainerLayout.CONTAINER_MARGIN) * heightScale;

        Point size = new Point();
        ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getSize(size);

        if ((size.x == 1920 && size.y == 1080) || (size.x == 1080 && size.y == 1920))  {
            layoutWidth = SymbolLayout.SYMBOL_DIMENSION_SMALL * widthScale;
            layoutHeight = SymbolLayout.SYMBOL_DIMENSION_SMALL - (2 * SymbolContainerLayout.CONTAINER_MARGIN) * heightScale;
        }

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
