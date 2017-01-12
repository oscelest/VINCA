package com.noxyspace.vinca.canvas.undoRedoStack;

import android.view.View;
import android.view.ViewGroup;

import com.noxyspace.vinca.canvas.SymbolLayout;

/**
 * Created by Andreas on 12-01-2017.
 */

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
