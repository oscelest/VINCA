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

    /**
     * Searches for the element in the Parentheses
     * @param elementId Element that is searched for
     * @param lastElementPosition The last number it reached, 0 if first time it is run
     * @return The position of the given element in the contents of the Parentheses, if the Parentheses is the given element it returns 0, and if one are found it also returns 0
     */

    public int searchForElementPosition(int elementId, int lastElementPosition){

        int elementPosition = lastElementPosition;

            for (int i = 0 ; i < contents.size() ; i++){
                if (contents.get(i).getId() == elementId) {
                    elementPosition++;

                    return elementPosition;
                }
                else if (contents.get(i) instanceof Parentheses){

                        return ((Parentheses) contents.get(i)).searchForElementPosition(elementId, elementPosition + 1);
                }
                else{
                    elementPosition++;
                }
            }
        return -1000000;
    }

    public int numberOfElements(){
        int n = 1;
        for(int i = 0 ; i < contents.size() ; i++){
            if (contents.get(i) instanceof Parentheses){
                n = n + ((Parentheses) contents.get(i)).numberOfElements();
            }
            else {
                n++;
            }
        }
        return n;
    }

    /**
     * Adds a symbol to the content list
     * @param symbol
     */

    // TODO : Not finished
    public void addSymbolToContents(Symbol symbol, int placement){


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
