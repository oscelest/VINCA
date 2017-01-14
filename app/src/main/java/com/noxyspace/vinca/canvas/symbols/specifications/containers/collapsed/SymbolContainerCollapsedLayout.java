package com.noxyspace.vinca.canvas.symbols.specifications.containers.collapsed;

import android.content.Context;
import android.widget.LinearLayout;

import com.noxyspace.vinca.canvas.symbols.SymbolLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.additionals.SymbolTitle;

public class SymbolContainerCollapsedLayout extends SymbolLayout {
    private SymbolTitle header;
    private SymbolTitle footer;

    protected SymbolContainerCollapsed symbol;

    public SymbolContainerCollapsedLayout(Context context, SymbolContainerCollapsed symbol) {
        this(context, symbol, true);
    }

    public SymbolContainerCollapsedLayout(Context context, SymbolContainerCollapsed symbol, boolean acceptsDrop) {
        super(context, acceptsDrop);

        this.setOrientation(LinearLayout.VERTICAL);

        if (acceptsDrop) {
            this.addView(this.header = new SymbolTitle(context, "Header"));
        } else {
            this.header = null;
        }

        this.addView(this.symbol = symbol);

        if (acceptsDrop) {
            this.addView(this.footer = new SymbolTitle(context, "Footer"));
        } else {
            this.footer = null;
        }
    }

    public String getHeader() {
        return this.header.getText().toString();
    }

    public void setHeader(String message) {
        this.header.setText(message);
    }

    public String getFooter() {
        return this.footer.getText().toString();
    }

    public void setFooter(String message) {
        this.footer.setText(message);
    }
}