package com.noxyspace.vinca.canvas.symbols;

import android.content.Context;
import android.view.DragEvent;
import android.view.View;
import android.widget.LinearLayout;

public class SymbolLayoutDragHandler extends LinearLayout implements View.OnDragListener {
    public SymbolLayoutDragHandler(Context context){
        super(context);
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                return this.onDragStarted(v, event);

            case DragEvent.ACTION_DROP:
                return this.onDragDrop(v, event);

            case DragEvent.ACTION_DRAG_ENDED:
                return this.onDragEnded(v, event);

            case DragEvent.ACTION_DRAG_ENTERED:
                return this.onDragEntered(v, event);

            case DragEvent.ACTION_DRAG_EXITED:
                return this.onDragExited(v, event);

            default:
                break;
        }

        return false;
    }

    protected boolean onDragStarted(View v, DragEvent event) {
        return false;
    }

    public boolean onDragDrop(View v, DragEvent event) {
        return false;
    }

    protected boolean onDragEnded(View v, DragEvent event) {
        return false;
    }

    protected boolean onDragEntered(View v, DragEvent event) {
        return false;
    }

    protected boolean onDragExited(View v, DragEvent event) {
        return false;
    }
}