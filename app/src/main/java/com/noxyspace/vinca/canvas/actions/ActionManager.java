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
        this.list.add(new RemoveAction(null, null));
        this.index = -1;
    }

    public void add(ArbitraryAction a){
        this.list.subList(index, list.size() - 1).clear();
        this.list.add(a);
        this.index++;
    }

    public boolean canRedo() {
        return (this.index < this.list.size());
    }

    public boolean canUndo() {
        return (this.index != -1);
    }

    public void redo(){
        if (this.canRedo()) {
            this.index++;
            //this.list.get(this.index++).redo();
        }
    }

    public void undo(){
        if (this.canUndo()) {
            this.index--;
            //this.list.get(this.index--).undo();
        }
    }
}
