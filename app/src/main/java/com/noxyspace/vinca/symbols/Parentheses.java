package com.noxyspace.vinca.symbols;

import java.util.List;

public class Parentheses extends Symbol {

    private List<Symbol> contents;

    public Parentheses(int id, String title, String description) {
        super(id, title, description);
    }

    public Parentheses(int id, String title, String description, List<Symbol> contents) {
        super(id, title, description);

        this.contents = contents;
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
     * Placeholder method for colapsing the Process, returns this to the callee
     * @return this
     */

    public Symbol colapse(){
        return this;
    }

    /**
     * Placeholder method for expanding the Process, returns this to the callee
     * @return this
     */

    public Symbol expand(){
        return this;
    }

}
