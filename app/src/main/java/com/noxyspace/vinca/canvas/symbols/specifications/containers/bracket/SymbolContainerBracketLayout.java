package com.noxyspace.vinca.canvas.symbols.specifications.containers.bracket;

import android.content.Context;
import android.view.DragEvent;
import android.view.View;

import com.noxyspace.vinca.canvas.symbols.SymbolLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.containers.SymbolContainerLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.containers.expanded.SymbolContainerExpanded;

public class SymbolContainerBracketLayout extends SymbolLayout {
    private SymbolContainerBracket bracket;

    public SymbolContainerBracketLayout(Context context, int resId) {
        this(context, resId, true);
    }

    public SymbolContainerBracketLayout(Context context, int resId, boolean acceptsDrop) {
        super(context, acceptsDrop);

        this.addView(this.bracket = new SymbolContainerBracket(context, resId));
    }

    @Override
    public boolean onDragDrop(View v, DragEvent event) {
        if (this.getParent() instanceof SymbolContainerExpanded && this.getParent().getParent() instanceof SymbolContainerLayout) {
            return ((SymbolLayout)this.getParent().getParent()).onDragDrop(v, event);
        }

        return false;
    }
}