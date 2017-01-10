package com.noxyspace.vinca.canvas.timeline;

import android.content.Context;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.noxyspace.vinca.canvas.SymbolLayoutDragHandler;
import com.noxyspace.vinca.canvas.symbols.project.SymbolProjectLayout;

public class SymbolTimelineLayout extends SymbolLayoutDragHandler {
    private static final int MARGIN_LEFT = 50;
    private static final int MINIMUM_WIDTH = 50;
    private static final int MINIMUM_HEIGHT = 50;

    public static final int HIGHLIGHT_COLOR = 0xFFFFFAE3;
    public static final int BACKGROUND_COLOR = 0xFFEEEEEE;

    public SymbolTimelineLayout(Context context) {
        super(context);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        );

        int margin = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MARGIN_LEFT, getResources().getDisplayMetrics());
        params.setMargins(margin, 0, 0, 0);

        this.setLayoutParams(params);

        int minWidth = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MINIMUM_WIDTH, getResources().getDisplayMetrics());
        this.setMinimumWidth(minWidth);

        int minHeight = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MINIMUM_HEIGHT, getResources().getDisplayMetrics());
        this.setMinimumHeight(minHeight);

        this.setOrientation(LinearLayout.HORIZONTAL);
        this.setBackgroundColor(BACKGROUND_COLOR);

        this.setOnDragListener(this);
    }

    @Override
    protected boolean onDragStarted(View v, DragEvent event) {
        return true;
    }

    @Override
    protected boolean onDragDrop(View v, DragEvent event) {
        View view = (View)event.getLocalState();

        if (view instanceof SymbolProjectLayout) {
            this.addView(new SymbolProjectLayout(getContext()));
        } else {
            Toast.makeText(getContext(), "Timeline objects only accept symbols of type: [ Project ]", Toast.LENGTH_SHORT).show();
        }

        return true;
    }
}