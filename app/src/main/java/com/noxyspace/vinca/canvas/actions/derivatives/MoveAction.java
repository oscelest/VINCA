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
        ((SymbolLayout)this.view).moveView(this.newPosition.getParent(), this.newPosition.getIndex());
    }

    public void undo() {
        ((SymbolLayout)this.view).moveView(this.oldPosition.getParent(), this.oldPosition.getIndex());
    }
}
