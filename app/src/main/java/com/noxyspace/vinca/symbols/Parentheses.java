package com.noxyspace.vinca.symbols;

import java.util.List;

public class Parentheses extends Symbol {

    private List<Symbol> contents;
    private boolean isColapsed;

    public Parentheses(int id, String title, String description) {
        super(id, title, description);
    }

    public Parentheses(int id, String title, String description, List<Symbol> contents) {
        super(id, title, description);

        this.contents = contents;

        isColapsed = false;
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
