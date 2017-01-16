package com.noxyspace.vinca.canvas.symbols.specifications.figures.activity;

import android.content.Context;
import android.view.DragEvent;
import android.view.View;

import com.noxyspace.vinca.R;
import com.noxyspace.vinca.canvas.symbols.SymbolLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.containers.collapsed.SymbolContainerCollapsedLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.figures.method.SymbolMethodLayout;

public class SymbolActivityLayout extends SymbolContainerCollapsedLayout {
    private boolean method;

    public SymbolActivityLayout(Context context) {
        this(context, true);
    }

    public SymbolActivityLayout(Context context, boolean acceptsDrop) {
        super(context, new SymbolActivity(context), acceptsDrop);
        this.method = false;
    }

    public boolean hasMethod() {
        return this.method;
    }

    public void setMethod(boolean method) {
        this.symbol.setImageResource((this.method = method) ? R.drawable.activity_method : R.drawable.activity);
    }

    @Override
    public boolean onDragDrop(View v, DragEvent event) {
        View view = (View)event.getLocalState();

        if (view != v) {
            if (view instanceof SymbolMethodLayout) {
                if ((view instanceof SymbolLayout) && ((SymbolLayout) view).isDropAccepted()) {
                    ((SymbolLayout)view).moveView(v);
                } else {
                    if (!this.hasMethod()) {
                        this.setMethod(true);
                    } else {
                        this.makeToast("An activity can only have a single method");
                    }
                }
            } else {
                this.makeToast("Activity objects only accept: [ Method ]");
            }
        }

        return true;
    }
}