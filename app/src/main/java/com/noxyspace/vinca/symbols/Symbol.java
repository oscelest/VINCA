package com.noxyspace.vinca.symbols;

import com.noxyspace.vinca.activities.helpers.DragHandler;

import java.util.ArrayList;
import java.util.List;

public class Symbol extends DragHandler {

    private int id;
    private String title;
    private String description;
    private boolean hasChildren = false;
    private Symbol parent;
    private List<Symbol> children;


    public Symbol(int id, String title, String description){
        this.id = id;
        this.title = title;
        this.description = description;

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



}
