package com.noxyspace.vinca.canvas;

import android.view.View;
import android.widget.LinearLayout;

import com.noxyspace.vinca.activities.CanvasActivity;
import com.noxyspace.vinca.symbols.Symbol;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MikkelLeth on 09/01/17.
 */

public class CanvasManager {
    private List<Symbol> elementsList = new ArrayList<Symbol>();
    private LinearLayout symbols;

    CanvasActivity canvasActivity;

    public CanvasManager(CanvasActivity canvasActivity, LinearLayout list){
        this.canvasActivity = canvasActivity;
        this.symbols = list;

    }

    public void addFigure(View view){

        symbols.addView(view);
        symbols.invalidate();

    }
}
