package com.noxyspace.vinca.canvas.symbols.timeline;

import android.content.Context;
import android.view.DragEvent;
import android.view.View;
import android.widget.Toast;

import com.noxyspace.vinca.canvas.SymbolLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.project.SymbolProjectLayout;

public class TimelineLayout extends SymbolLayout {
    public static final int PADDING = 50;
    public static final int MARGIN_LEFT = 10;
    public static final int MARGIN_TOP = 10;

    public static final int HIGHLIGHT_COLOR = 0xFFFFFAE3;
    public static final int BACKGROUND_COLOR = 0xFFEEEEEE;

    public TimelineLayout(Context context) {
        super(context, true);
    }

    @Override
    protected boolean onDragStarted(View v, DragEvent event) {
        return true;
    }

    @Override
    protected boolean onDragDrop(View v, DragEvent event) {
        View view = (View)event.getLocalState();

        if (view instanceof SymbolProjectLayout) {
            if ((view instanceof SymbolLayout) && ((SymbolLayout)view).isDropAccepted()) {
                this.moveView(view, v);
            } else {
                this.addView(new SymbolProjectLayout(getContext()));
            }
        } else {
            Toast.makeText(getContext(), "Timeline objects only accept: [ Project ]", Toast.LENGTH_SHORT).show();
        }

        return true;
    }
}