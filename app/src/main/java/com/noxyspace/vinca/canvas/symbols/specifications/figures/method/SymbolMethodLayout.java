package com.noxyspace.vinca.canvas.symbols.specifications.figures.method;

import android.content.Context;
import android.view.DragEvent;
import android.view.View;

import com.noxyspace.vinca.canvas.symbols.SymbolLayout;

public class SymbolMethodLayout extends SymbolLayout {
    private SymbolMethod method;

    public SymbolMethodLayout(Context context) {
        this(context, true);
    }

    public SymbolMethodLayout(Context context, boolean acceptsDrop) {
        super(context, acceptsDrop);

        this.addView(this.method = new SymbolMethod(context));
    }

    @Override
    protected boolean onDragDrop(View v, DragEvent event) {
        if ((View)event.getLocalState() != v) {
            this.makeToast("Method objects does not accept children");
        }

        return false;
    }
}