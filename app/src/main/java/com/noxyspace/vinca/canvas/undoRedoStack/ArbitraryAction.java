package com.noxyspace.vinca.canvas.undoRedoStack;

import android.view.View;

/**
 * Created by Andreas on 12-01-2017.
 */

abstract class ArbitraryAction {
    protected View view;

    public ArbitraryAction(View v){
        this.view = v;
    }

    abstract public void redo();
    abstract public void undo();
}
