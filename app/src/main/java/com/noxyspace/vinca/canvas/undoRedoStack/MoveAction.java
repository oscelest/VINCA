package com.noxyspace.vinca.canvas.undoRedoStack;

import android.app.Notification;
import android.view.View;

import com.noxyspace.vinca.canvas.SymbolLayout;

/**
 * Created by Andreas on 12-01-2017.
 */

public class MoveAction extends ArbitraryAction {
    private ActionParameter oldPosition, newPosition;

    public MoveAction(View view, ActionParameter oldPosition, ActionParameter newPosition){
        super(view);
        this.oldPosition = oldPosition;
        this.newPosition = newPosition;
    }

    public void redo() {
        ((SymbolLayout)newPosition.getParent()).moveView(view, newPosition.getParent(), newPosition.getIndex());
    }

    public void undo() {
        ((SymbolLayout)oldPosition.getParent()).moveView(view, oldPosition.getParent(), oldPosition.getIndex());
    }
}
