package com.noxyspace.vinca.canvas.symbols.specifications.containers.bracket;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.noxyspace.vinca.canvas.symbols.SymbolLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.containers.SymbolContainerLayout;

public class SymbolContainerBracket extends ImageView {
    public SymbolContainerBracket(Context context, int resId) {
        super(context);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display screen = wm.getDefaultDisplay();
        Point size = new Point();
        screen.getSize(size);
        int width = size.x;
        int height = size.y;

        LinearLayout.LayoutParams params;
        if ((width < 1100 && height < 600) || (width < 600 && height < 1100) || Build.MODEL.equals("GT-I9505"))  {
            int dimens;
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                dimens = SymbolLayout.SYMBOL_DIMENSION_SMALL_LANDSCAPE;
            } else {
                dimens = SymbolLayout.SYMBOL_DIMENSION_SMALL;
            }

            params = new LinearLayout.LayoutParams(
                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dimens / 2, getResources().getDisplayMetrics()),
                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dimens - (2 * SymbolContainerLayout.CONTAINER_MARGIN), getResources().getDisplayMetrics())
            );
        } else {
            params = new LinearLayout.LayoutParams(
                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, SymbolLayout.SYMBOL_DIMENSION / 2, getResources().getDisplayMetrics()),
                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, SymbolLayout.SYMBOL_DIMENSION - (2 * SymbolContainerLayout.CONTAINER_MARGIN), getResources().getDisplayMetrics())
            );
        }

        int margin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, SymbolContainerLayout.CONTAINER_MARGIN, getResources().getDisplayMetrics());
        params.setMargins(0, margin, 0, margin);

        this.setLayoutParams(params);
        this.setImageResource(resId);
    }
}
