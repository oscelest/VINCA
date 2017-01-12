package com.noxyspace.vinca.canvas.actions.derivatives;

import android.view.View;

import com.noxyspace.vinca.canvas.symbols.SymbolLayout;
import com.noxyspace.vinca.canvas.actions.ActionParameter;
import com.noxyspace.vinca.canvas.actions.ArbitraryAction;

public class AddAction extends ArbitraryAction {
    private ActionParameter position;

    public AddAction(View view, ActionParameter position){
        super(view);
        this.position = position;
    }

    public void redo() {
        ((SymbolLayout)position.getParent()).addView(view, position.getIndex());
    }

    public void undo() {
        ((SymbolLayout)position.getParent()).removeView(view);
    }
}
