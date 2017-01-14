package com.noxyspace.vinca.canvas.actions.derivatives;

import android.view.View;
import android.view.ViewGroup;

import com.noxyspace.vinca.canvas.symbols.SymbolLayout;
import com.noxyspace.vinca.canvas.actions.ActionParameter;
import com.noxyspace.vinca.canvas.actions.ArbitraryAction;
import com.noxyspace.vinca.canvas.symbols.specifications.additionals.SymbolEmptyLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.containers.SymbolContainerExpanded;
import com.noxyspace.vinca.canvas.symbols.specifications.timeline.TimelineLayout;

public class RemoveAction extends ArbitraryAction {
    private ActionParameter position;

    public RemoveAction(View view, ActionParameter position){
        super(view);
        this.position = position;
    }

    public void redo() {
        if (this.view instanceof TimelineLayout) {
            ((ViewGroup)this.view.getParent()).removeView(this.view);
        } else if (this.view instanceof SymbolLayout && this.view.getParent() instanceof SymbolContainerExpanded) {
            ((SymbolLayout)this.view).removeViews();
        }
    }

    public void undo() {
        ((ViewGroup)position.getParent()).addView(this.view, position.getIndex());

        if (position.getParent() instanceof SymbolContainerExpanded) {
            ((SymbolContainerExpanded)position.getParent()).addView(new SymbolEmptyLayout(position.getParent().getContext()), position.getIndex() + 1);
        }
    }
}
