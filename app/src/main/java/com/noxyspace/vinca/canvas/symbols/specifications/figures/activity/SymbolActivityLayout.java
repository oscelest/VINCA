package com.noxyspace.vinca.canvas.symbols.specifications.figures.activity;

import android.content.Context;
import android.view.DragEvent;
import android.view.View;

import com.noxyspace.vinca.R;
import com.noxyspace.vinca.canvas.symbols.SymbolLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.figures.method.SymbolMethodLayout;

public class SymbolActivityLayout extends SymbolLayout {
    private SymbolActivity activity;
    private boolean method;

    public SymbolActivityLayout(Context context) {
        this(context, true);
    }

    public SymbolActivityLayout(Context context, boolean acceptsDrop) {
        super(context, acceptsDrop);

        this.method = false;
        this.addView(this.activity = new SymbolActivity(context));
    }

    public boolean hasMethod() {
        return this.method;
    }

    public void setMethod(boolean method) {
        this.activity.setImageResource((this.method = method) ? R.drawable.activity_method : R.drawable.activity);
    }

    @Override
    protected boolean onDragDrop(View v, DragEvent event) {
        View view = (View)event.getLocalState();

        if (view instanceof SymbolMethodLayout) {
            if ((view instanceof SymbolLayout) && ((SymbolLayout)view).isDropAccepted()) {
                this.moveView(view, v);
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

        return true;
    }
}
