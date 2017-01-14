package com.noxyspace.vinca.canvas.symbols.specifications.containers.bracket;

import android.content.Context;

import com.noxyspace.vinca.canvas.symbols.SymbolLayout;

public class SymbolContainerBracketLayout extends SymbolLayout {
    private SymbolContainerBracket bracket;

    public SymbolContainerBracketLayout(Context context, int resId) {
        this(context, resId, true);
    }

    public SymbolContainerBracketLayout(Context context, int resId, boolean acceptsDrop) {
        super(context, acceptsDrop);

        this.addView(this.bracket = new SymbolContainerBracket(context, resId));
    }
}