package com.noxyspace.vinca.canvas.undoRedoStack;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Created by Andreas on 12-01-2017.
 */

public class ActionManager {
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
