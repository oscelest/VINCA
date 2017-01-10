package com.noxyspace.vinca.canvas.symbols.decision;

import android.content.Context;
import android.view.DragEvent;
import android.view.View;
import android.widget.Toast;

import com.noxyspace.vinca.canvas.SymbolLayout;

public class SymbolDecisionLayout extends SymbolLayout {
    private SymbolDecision decision;

    public SymbolDecisionLayout(Context context) {
        this(context, true);
    }

    public SymbolDecisionLayout(Context context, boolean acceptsDrop) {
        super(context, acceptsDrop);

        this.addView(this.decision = new SymbolDecision(context));
    }

    @Override
    protected boolean onDragDrop(View v, DragEvent event) {
        Toast.makeText(getContext(), "Decision objects does not accept children", Toast.LENGTH_SHORT).show();
        return false;
    }
}