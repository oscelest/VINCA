package com.noxyspace.vinca.canvas.symbols.specifications.figures.pause;

import android.content.Context;
import android.view.DragEvent;
import android.view.View;

import com.noxyspace.vinca.canvas.symbols.SymbolLayout;

public class SymbolPauseLayout extends SymbolLayout {
    private SymbolPause pause;

    public SymbolPauseLayout(Context context) {
        this(context, true);
    }

    public SymbolPauseLayout(Context context, boolean acceptsDrop) {
        super(context, acceptsDrop);

        this.addView(this.pause = new SymbolPause(context));
    }

    @Override
    protected boolean onDragDrop(View v, DragEvent event) {
        if ((View)event.getLocalState() != v) {
            this.makeToast("Pause objects does not accept children");
        }

        return false;
    }
}
