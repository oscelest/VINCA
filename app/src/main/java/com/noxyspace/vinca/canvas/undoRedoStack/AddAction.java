package com.noxyspace.vinca.canvas.undoRedoStack;

import android.view.View;

import com.noxyspace.vinca.canvas.SymbolLayout;

/**
 * Created by Andreas on 12-01-2017.
 */

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
