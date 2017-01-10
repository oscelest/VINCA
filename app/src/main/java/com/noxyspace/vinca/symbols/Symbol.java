package com.noxyspace.vinca.symbols;

import android.content.Context;

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

    public Symbol(Context context){
        super(context);
        this.id = idCounter++;

        this.children = new ArrayList<Symbol>();
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
    protected boolean onDragStarted() {
        return true;
    }

    @Override
    protected boolean onDragDrop() {
        return true;
    }

    @Override
    protected boolean onDragEnded() {
        return true;
    }
}
