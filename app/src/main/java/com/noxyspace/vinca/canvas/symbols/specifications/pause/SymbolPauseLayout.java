package com.noxyspace.vinca.canvas.symbols.specifications.pause;

import android.content.Context;
import android.view.DragEvent;
import android.view.View;
import android.widget.Toast;

import com.noxyspace.vinca.canvas.SymbolLayout;

public class SymbolPauseLayout extends SymbolLayout {
    private SymbolPause pause;

    public SymbolPauseLayout(Context context) {
        this(context, true);
    }

    public SymbolPauseLayout(Context context, boolean acceptsDrop) {
        super(context, acceptsDrop);

        this.addView(this.pause = new SymbolPause(context));
    }

    @Override
    protected boolean onDragDrop(View v, DragEvent event) {
        Toast.makeText(getContext(), "Pause objects does not accept children", Toast.LENGTH_SHORT).show();
        return false;
    }
}
