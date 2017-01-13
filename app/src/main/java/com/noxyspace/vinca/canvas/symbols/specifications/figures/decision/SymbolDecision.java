package com.noxyspace.vinca.canvas.symbols.specifications.figures.decision;

import android.content.Context;

import com.noxyspace.vinca.R;
import com.noxyspace.vinca.canvas.symbols.specifications.SymbolContainerCollapsed;

public class SymbolDecision extends SymbolContainerCollapsed {
    public SymbolDecision(Context context, boolean acceptsDrop) {
        super(context, R.drawable.decision, acceptsDrop ? 0.5f : 1.0f, 1.0f);
    }
}