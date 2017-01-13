package com.noxyspace.vinca.canvas.actions.derivatives;

import android.view.View;

import com.noxyspace.vinca.canvas.symbols.SymbolLayout;
import com.noxyspace.vinca.canvas.actions.ActionParameter;
import com.noxyspace.vinca.canvas.actions.ArbitraryAction;

public class MoveAction extends ArbitraryAction {
    private ActionParameter oldPosition, newPosition;

    public MoveAction(View view, ActionParameter oldPosition, ActionParameter newPosition){
        super(view);
        this.oldPosition = oldPosition;
        this.newPosition = newPosition;
    }

    public void redo() {
        ((SymbolLayout)newPosition.getParent()).moveView(view, newPosition.getParent(), newPosition.getIndex(), false);
    }

    public void undo() {
        ((SymbolLayout)oldPosition.getParent()).moveView(view, oldPosition.getParent(), oldPosition.getIndex(), false);
    }
}
