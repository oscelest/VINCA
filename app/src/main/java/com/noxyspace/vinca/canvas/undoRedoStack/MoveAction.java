package com.noxyspace.vinca.canvas.undoRedoStack;

import android.view.View;

import com.noxyspace.vinca.canvas.SymbolLayout;

/**
 * Created by Andreas on 12-01-2017.
 */

public class MoveAction {

    private SymbolLayout symbol;

    public MoveAction(SymbolLayout symbol){
        this.symbol = symbol;
    }

    public moveFromList(){
        View parent = symbol.getParent();
        View symbolIndex = symbol.getIndex();


        //Should i check if it's the right id??

        symbol.moveView();

    }
}
