package com.noxyspace.vinca.canvas.symbols.specifications.figures;

import android.content.Context;
import android.view.DragEvent;
import android.view.View;

import com.noxyspace.vinca.R;
import com.noxyspace.vinca.canvas.symbols.specifications.containers.collapsed.SymbolContainerCollapsed;
import com.noxyspace.vinca.canvas.symbols.specifications.containers.collapsed.SymbolContainerCollapsedLayout;

public class SymbolDecisionLayout extends SymbolContainerCollapsedLayout {
    public SymbolDecisionLayout(Context context) {
        this(context, true);
    }

    public SymbolDecisionLayout(Context context, boolean acceptsDrop) {
        super(context, new SymbolContainerCollapsed(context, R.drawable.decision, acceptsDrop ? 0.5f : 1.0f, 1.0f), acceptsDrop);
    }

    @Override
    public boolean onDragDrop(View v, DragEvent event) {
        if ((View)event.getLocalState() != v) {
            this.makeToast("Decision objects does not accept children");
        }

        return true;
    }
}