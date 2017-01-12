package com.noxyspace.vinca.canvas.actions.derivatives;

import android.view.View;
import android.view.ViewGroup;

import com.noxyspace.vinca.canvas.symbols.SymbolLayout;
import com.noxyspace.vinca.canvas.actions.ActionParameter;
import com.noxyspace.vinca.canvas.actions.ArbitraryAction;

public class RemoveAction extends ArbitraryAction {
    private ActionParameter position;

    public RemoveAction(View view, ActionParameter position){
        super(view);
        this.position = position;
    }

    public void redo() {
        ((SymbolLayout)position.getParent()).removeView(view);
    }

    public void undo() {
        ((ViewGroup)position.getParent()).addView(view, position.getIndex());
    }
}
