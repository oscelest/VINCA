package com.noxyspace.vinca.activities.helpers;

/**
 * Created by MikkelLeth on 06/01/17.
 */

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;

public class DragHandler implements View.OnDragListener{
    @Override
    public boolean onDrag(View view, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                return this.onDragStarted();

            case DragEvent.ACTION_DROP:
                return this.onDragDrop();

            case DragEvent.ACTION_DRAG_ENDED:
                return this.onDragEnded();

            case DragEvent.ACTION_DRAG_ENTERED:
                return this.onDragEntered();

            case DragEvent.ACTION_DRAG_EXITED:
                return this.onDragExited();

            default:
                break;
        }

        return false;
    }

    protected boolean onDragStarted() {
        return false;
    }

    protected boolean onDragDrop() {
        return false;
    }

    protected boolean onDragEnded() {
        return false;
    }

    protected boolean onDragEntered() {
        return false;
    }

    protected boolean onDragExited() {
        return false;
    }
}