package com.noxyspace.vinca.canvas.symbols.specifications.figures.decision;

import android.content.Context;
import android.view.DragEvent;
import android.view.View;

import com.noxyspace.vinca.canvas.symbols.SymbolLayout;

public class SymbolDecisionLayout extends SymbolLayout {
    private SymbolDecision decision;

    public SymbolDecisionLayout(Context context) {
        this(context, true);
    }

    public SymbolDecisionLayout(Context context, boolean acceptsDrop) {
        super(context, acceptsDrop);

        this.addView(this.decision = new SymbolDecision(context, acceptsDrop));
    }

    @Override
    protected boolean onDragDrop(View v, DragEvent event) {
        this.makeToast("Decision objects does not accept children");
        return false;
    }
}