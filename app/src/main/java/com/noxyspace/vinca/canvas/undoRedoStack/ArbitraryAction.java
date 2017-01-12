package com.noxyspace.vinca.canvas.undoRedoStack;

import android.view.View;

/**
 * Created by Andreas on 12-01-2017.
 */

abstract class ArbitraryAction {
    private View view;

    public ArbitraryAction(View v){
        view = v;
    }

    abstract void redoAction();
    abstract void undoAction();
}
