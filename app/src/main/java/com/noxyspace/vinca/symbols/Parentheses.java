package com.noxyspace.vinca.symbols;

import java.util.ArrayList;
import java.util.List;

public class Parentheses extends Symbol {

    private List<Symbol> contents = new ArrayList<Symbol>();
    private boolean isColapsed;

    public Parentheses(int id, String title, String description) {
        super(id, title, description);
    }

    public Parentheses(int id, String title, String description, ArrayList<Symbol> contents) {
        super(id, title, description);

        this.contents = contents;

        isColapsed = false;
    }

    public int numberOfElements(){
        int n = 0;
        for(int i = 0 ; i < contents.size() ; i++){
            if (contents.get(i) instanceof Parentheses){
                n = n + ((Parentheses) contents.get(i)).numberOfElements();
            }
            else {
                n++;
            }
        }
        return n + 2;
    }

    /**
     * Adds a symbol to the content list
     * @param symbol
     */

    public void addSymbolToContents(Symbol symbol){
        contents.add(symbol);
    }

    /**
     * Returns the full list of all the content
     * @return
     */
    public List<Symbol> getContents() {
        return contents;
    }

    /**
     * Sets the isColapsed to true
     */

    public void colapse(){
        isColapsed = true;
    }

    /**
     * Sets the isColapsed to false
     */

    public void expand(){
        isColapsed = false;
    }

    public boolean isColapsed(){
        return isColapsed;
    }

}
