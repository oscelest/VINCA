package com.noxyspace.vinca.canvas.actions.derivatives;

import android.view.View;
import android.view.ViewGroup;

import com.noxyspace.vinca.canvas.symbols.SymbolLayout;
import com.noxyspace.vinca.canvas.actions.ActionParameter;
import com.noxyspace.vinca.canvas.actions.ArbitraryAction;
import com.noxyspace.vinca.canvas.symbols.specifications.SymbolContainerBracketLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.timeline.TimelineLayout;

public class RemoveAction extends ArbitraryAction {
    private ActionParameter position;

    public RemoveAction(View view, ActionParameter position){
        super(view);
        this.position = position;
    }

    public void redo() {
        if (view instanceof TimelineLayout) {
            ((ViewGroup)view.getParent()).removeView(view);
        } else if (view.getParent() instanceof SymbolLayout) {
            ((SymbolLayout)view).removeViews(view);
        }
    }

    public void undo() {
        ((ViewGroup)position.getParent()).addView(view, position.getIndex());
    }
}
