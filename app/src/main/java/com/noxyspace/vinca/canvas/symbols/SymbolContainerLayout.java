package com.noxyspace.vinca.canvas.symbols;

import android.content.Context;
import android.view.View;

import com.noxyspace.vinca.canvas.SymbolLayout;

public class SymbolContainerLayout extends SymbolLayout {
    public static final int CONTAINER_MARGIN = 5;

    private SymbolContainerBracket symbolStart;
    private SymbolContainerBracket symbolEnd;

    public SymbolContainerLayout(Context context) {
        this(context, true);
    }

    public SymbolContainerLayout(Context context, boolean acceptsDrop) {
        super(context, acceptsDrop);
    }

    protected void createBrackets(SymbolContainerBracket symbolStart, SymbolContainerBracket symbolEnd) {
        this.addView(this.symbolStart = symbolStart);
        this.addView(this.symbolEnd = symbolEnd);
    }

    @Override
    public void addView(View child) {
        if (this.getChildCount() >= 2) {
            this.removeViewAt(this.getChildCount() - 1);
            super.addView(child);
            super.addView(this.symbolEnd);
        } else {
            super.addView(child);
        }
    }
}
