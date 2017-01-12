package com.noxyspace.vinca.canvas.actions;

import android.view.View;

abstract public class ArbitraryAction {
    protected View view;

    public ArbitraryAction(View v){
        this.view = v;
    }

    abstract public void redo();
    abstract public void undo();
}
