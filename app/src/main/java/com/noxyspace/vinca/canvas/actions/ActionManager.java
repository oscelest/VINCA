package com.noxyspace.vinca.canvas.actions;

import com.noxyspace.vinca.canvas.actions.derivatives.RemoveAction;

import java.util.ArrayList;
import java.util.List;

public class ActionManager {
    private static ActionManager singleton = new ActionManager();
    public static boolean isManagingAction = false;

    public static ActionManager getInstance() {
        return singleton;
    }

    private List<ArbitraryAction> list;
    private int index;

    public ActionManager(){
        this.list = new ArrayList<ArbitraryAction>();
        this.clear();
    }

    public void add(ArbitraryAction a) {
        this.list.subList(++index, list.size()).clear();
        this.list.add(a);
    }

    public void clear() {
        this.list.clear();
        this.index = -1;
    }

    public boolean canRedo() {
        return (this.index < this.list.size() - 1);
    }

    public boolean canUndo() {
        return (this.index != -1);
    }

    public void redo(){
        if (this.canRedo()) {
            isManagingAction = true;
            this.list.get(++this.index).redo();
            isManagingAction = false;
        }
    }

    public void undo(){
        if (this.canUndo()) {
            isManagingAction = true;
            this.list.get(this.index--).undo();
            isManagingAction = false;
        }
    }
}
