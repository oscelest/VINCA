package com.noxyspace.vinca.canvas;

import android.content.ClipData;
import android.content.Context;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.noxyspace.vinca.canvas.symbols.trashcan.SymbolTrashcanLayout;
import com.noxyspace.vinca.canvas.timeline.SymbolTimelineLayout;

public class SymbolLayout extends SymbolLayoutDragHandler {
    public static final int SYMBOL_DIMENSION = 80;

    private boolean acceptsDrop;

    public SymbolLayout(Context context, boolean acceptsDrop) {
        super(context);

        this.acceptsDrop = acceptsDrop;

        this.setLayoutParams(new LinearLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        ));

        if (!(this instanceof SymbolTrashcanLayout)) {
            this.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        ClipData data = ClipData.newPlainText("", "");
                        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);

                        v.startDrag(data, shadowBuilder, v, 0);
                        v.setVisibility(View.VISIBLE);
                        return true;
                    } else {
                        return false;
                    }
                }
            });
        }

        this.setOnDragListener(this);
    }

    public boolean isDropAccepted() {
        return this.acceptsDrop;
    }

    @Override
    protected boolean onDragStarted(View v, DragEvent event) {
        return this.acceptsDrop;
    }

    @Override
    protected boolean onDragEnded(View v, DragEvent event) {
        v.setBackgroundColor(SymbolTimelineLayout.BACKGROUND_COLOR);
        return true;
    }

    @Override
    protected boolean onDragEntered(View v, DragEvent event) {
        v.setBackgroundColor(SymbolTimelineLayout.HIGHLIGHT_COLOR);
        return true;
    }

    @Override
    protected boolean onDragExited(View v, DragEvent event) {
        v.setBackgroundColor(SymbolTimelineLayout.BACKGROUND_COLOR);
        return true;
    }
}
