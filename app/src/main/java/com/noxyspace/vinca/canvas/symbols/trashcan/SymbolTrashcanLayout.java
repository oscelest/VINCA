package com.noxyspace.vinca.canvas.symbols.trashcan;

import android.content.Context;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.noxyspace.vinca.canvas.SymbolLayout;
import com.noxyspace.vinca.canvas.timeline.TimelineLayout;

public class SymbolTrashcanLayout extends SymbolLayout {
    private SymbolTrashcan trashcan;

    public SymbolTrashcanLayout(Context context) {
        this(context, true);
    }

    public SymbolTrashcanLayout(Context context, boolean acceptsDrop) {
        super(context, acceptsDrop);

        this.addView(this.trashcan = new SymbolTrashcan(context));
    }

    @Override
    protected boolean onDragDrop(View v, DragEvent event) {
        View view = (View)event.getLocalState();

        if ((view instanceof SymbolLayout) && ((SymbolLayout)view).isDropAccepted()) {
            ViewGroup parent = (ViewGroup)view.getParent();

            if (view instanceof TimelineLayout) {
                ((ViewGroup)parent.getParent()).removeView(parent);
            } else {
                parent.removeView(view);
            }
        } else {
            Toast.makeText(getContext(), "Cannot remove symbol bar objects", Toast.LENGTH_SHORT).show();
        }

        return true;
    }
}
