package com.noxyspace.vinca.canvas.actions;

import android.view.View;

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
