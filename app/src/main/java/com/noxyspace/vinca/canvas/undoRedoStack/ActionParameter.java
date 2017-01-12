package com.noxyspace.vinca.canvas.undoRedoStack;

import android.view.View;

/**
 * Created by Andreas on 12-01-2017.
 */

public class ActionParameter {
    private View parent;
    private int index;

    public ActionParameter(View parent, int index){
        this.parent = parent;
        this.index = index;
    }

    public View getParent(){
        return parent;
    }
    public int getIndex(){
        return index;
    }
}
