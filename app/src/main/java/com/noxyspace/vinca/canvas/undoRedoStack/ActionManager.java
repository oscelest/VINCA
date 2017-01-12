package com.noxyspace.vinca.canvas.undoRedoStack;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Created by Andreas on 12-01-2017.
 */

public class ActionManager {

    List<ArbitraryAction> list;

    public ActionManager(){
        list = new ArrayList<ArbitraryAction>();
    }

    public ArrayList<ArbitraryAction> getList(){
        return (ArrayList<ArbitraryAction>) list;
    }

}
