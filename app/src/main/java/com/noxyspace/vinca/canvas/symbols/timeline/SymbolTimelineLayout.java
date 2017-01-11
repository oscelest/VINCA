package com.noxyspace.vinca.canvas.symbols.timeline;

import android.content.Context;
import android.view.DragEvent;
import android.view.View;
import android.widget.Toast;

import com.noxyspace.vinca.canvas.SymbolLayout;

public class SymbolTimelineLayout extends SymbolLayout {
    private SymbolTimeline timeline;

    public SymbolTimelineLayout(Context context) {
        this(context, true);
    }

    public SymbolTimelineLayout(Context context, boolean acceptsDrop) {
        super(context, acceptsDrop);

        this.addView(this.timeline = new SymbolTimeline(context));
    }

    @Override
    protected boolean onDragDrop(View v, DragEvent event) {
        Toast.makeText(getContext(), "Timeline objects does not accept children", Toast.LENGTH_SHORT).show();
        return false;
    }
}
