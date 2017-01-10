package com.noxyspace.vinca.symbols;

import android.content.ClipData;
import android.content.Context;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.noxyspace.vinca.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Symbol extends SymbolDragHandler {
    static private int idCounter = 0;

    private int id;
    private String title;
    private String description;
    private boolean hasChildren = false;
    private Symbol parent;
    private ArrayList<Symbol> children;


    public Symbol(Context context, ViewGroup view, int resId) {
        super(context);
        this.id = idCounter++;
        this.children = new ArrayList<Symbol>();

        this.setLayoutParams(new LinearLayout.LayoutParams(
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 75, getResources().getDisplayMetrics()),
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 75, getResources().getDisplayMetrics())
        ));

        this.setImageResource(resId);

        if (!(this instanceof TrashcanSymbol)) {
            this.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        ClipData data = ClipData.newPlainText("", "");
                        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);

                        v.startDrag(data, shadowBuilder, v, 0);
                        v.setVisibility(View.VISIBLE);
                        return true;
                    } else {
                        return false;
                    }
                }
            });
        }

        view.addView(this);
    }

    public int getId() {
        return id;
    }

    public String getSymbolTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public boolean hasChildren(){
        return hasChildren;
    }
    public Symbol getSymbolParent(){
        return parent;
    }
    public void setParent(Symbol symbol){
        this.parent = symbol;
    }
    static public void setIdCounter(int newCounter){
        idCounter = newCounter;
    }
    public ArrayList<Symbol> getChildren(){
        return children;
    }

    //DragEvent Methods
    @Override
    protected boolean onDragStarted(View view, DragEvent event) {
        return true;
    }

    @Override
    protected boolean onDragDrop(View view, DragEvent event) {
        return true;
    }

    @Override
    protected boolean onDragEnded(View view, DragEvent event) {
        return true;
    }

}
