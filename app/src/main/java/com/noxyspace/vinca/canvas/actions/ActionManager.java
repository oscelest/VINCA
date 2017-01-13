package com.noxyspace.vinca.canvas.actions;

import com.noxyspace.vinca.canvas.actions.derivatives.RemoveAction;

import java.util.ArrayList;
import java.util.List;

public class ActionManager {
    private static ActionManager singleton = new ActionManager();

    public static ActionManager getInstance() {
        return singleton;
    }

    private List<ArbitraryAction> list;
    private int index;

    public ActionManager(){
        this.list = new ArrayList<ArbitraryAction>();
        this.index = -1;
    }

    public void add(ArbitraryAction a){
        this.list.subList(++index, list.size()).clear();
        this.list.add(a);
    }

    public void clear() {
        this.list.clear();
    }

    public boolean canRedo() {
        return (this.index < this.list.size() - 1);
    }

    public boolean canUndo() {
        return (this.index != -1);
    }

    public void redo(){
        if (this.canRedo()) {
            this.list.get(++this.index).redo();
        }
    }

    public void undo(){
        if (this.canUndo()) {
            this.list.get(this.index--).undo();
        }
    }
}
