package com.noxyspace.vinca.canvas.actions.derivatives;

import android.view.View;
import android.view.ViewGroup;

import com.noxyspace.vinca.canvas.symbols.SymbolLayout;
import com.noxyspace.vinca.canvas.actions.ActionParameter;
import com.noxyspace.vinca.canvas.actions.ArbitraryAction;
import com.noxyspace.vinca.canvas.symbols.specifications.timeline.TimelineLayout;

public class AddAction extends ArbitraryAction {
    private ActionParameter position;

    public AddAction(View view, ActionParameter position){
        super(view);
        this.position = position;
    }

    public void redo() {
        ((ViewGroup)this.position.getParent()).addView(this.view, position.getIndex());
    }

    public void undo() {
        ((ViewGroup)this.position.getParent()).removeView(this.view);
    }
}