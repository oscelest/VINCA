package com.noxyspace.vinca.canvas.symbols.specifications.figures;

import android.content.Context;
import android.view.DragEvent;
import android.view.View;

import com.noxyspace.vinca.R;
import com.noxyspace.vinca.canvas.symbols.SymbolLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.containers.collapsed.SymbolContainerCollapsed;

public class SymbolMethodLayout extends SymbolLayout {
    public SymbolMethodLayout(Context context) {
        this(context, true);
    }

    public SymbolMethodLayout(Context context, boolean acceptsDrop) {
        super(context, acceptsDrop);

        this.addView(new SymbolContainerCollapsed(context, R.drawable.method));
    }

    @Override
    public boolean onDragDrop(View v, DragEvent event) {
        if ((View)event.getLocalState() != v) {
            this.makeToast("Method objects does not accept children");
        }

        return true;
    }
}