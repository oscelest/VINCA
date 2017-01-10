package com.noxyspace.vinca.symbols;

import android.content.Context;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;

public class SymbolDragHandler extends ImageView implements View.OnDragListener{
    public SymbolDragHandler (Context context){
        super(context);
    }

    @Override
    public boolean onDrag(View view, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                return this.onDragStarted(view, event);

            case DragEvent.ACTION_DROP:
                return this.onDragDrop(view, event);

            case DragEvent.ACTION_DRAG_ENDED:
                return this.onDragEnded(view, event);

            case DragEvent.ACTION_DRAG_ENTERED:
                return this.onDragEntered(view, event);

            case DragEvent.ACTION_DRAG_EXITED:
                return this.onDragExited(view, event);

            default:
                break;
        }

        return false;
    }

    protected boolean onDragStarted(View view, DragEvent event) {
        return false;
    }

    protected boolean onDragDrop(View view, DragEvent event) {
        return false;
    }

    protected boolean onDragEnded(View view, DragEvent event) {
        return false;
    }

    protected boolean onDragEntered(View view, DragEvent event) {
        return false;
    }

    protected boolean onDragExited(View view, DragEvent event) {
        return false;
    }
}