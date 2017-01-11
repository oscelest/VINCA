package com.noxyspace.vinca.canvas.symbols.activity;

import android.content.Context;
import android.view.DragEvent;
import android.view.View;
import android.widget.Toast;

import com.noxyspace.vinca.canvas.SymbolLayout;
import com.noxyspace.vinca.canvas.symbols.method.SymbolMethodLayout;

public class SymbolActivityLayout extends SymbolLayout {
    private SymbolActivity activity;

    public SymbolActivityLayout(Context context) {
        this(context, true);
    }

    public SymbolActivityLayout(Context context, boolean acceptsDrop) {
        super(context, acceptsDrop);

        this.addView(this.activity = new SymbolActivity(context));
    }

    @Override
    protected boolean onDragDrop(View v, DragEvent event) {
        View view = (View)event.getLocalState();

        if (view instanceof SymbolMethodLayout) {
            if ((view instanceof SymbolLayout) && ((SymbolLayout)view).isDropAccepted()) {
                this.moveView(view, v);
            } else {
                this.addView(new SymbolMethodLayout(getContext()));
            }
        } else {
            Toast.makeText(getContext(), "Activity objects only accept: [ Method ]", Toast.LENGTH_SHORT).show();
        }

        return true;
    }
}
