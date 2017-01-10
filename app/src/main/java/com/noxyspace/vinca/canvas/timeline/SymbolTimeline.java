package com.noxyspace.vinca.canvas.timeline;

import android.content.Context;
import android.util.TypedValue;
import android.widget.HorizontalScrollView;

public class SymbolTimeline extends HorizontalScrollView {
    private static final int MARGIN_TOP = 10;
    private static final int BACKGROUND_COLOR = 0xFF0000FF;

    private SymbolTimelineLayout layout;

    public SymbolTimeline(Context context) {
        super(context);

        HorizontalScrollView.LayoutParams params = new HorizontalScrollView.LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
        );

        int margin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MARGIN_TOP, getResources().getDisplayMetrics());
        params.setMargins(0, margin, 0, 0);

        this.setLayoutParams(params);
        this.setBackgroundColor(BACKGROUND_COLOR);

        this.addView(this.layout = new SymbolTimelineLayout(context));
    }
}