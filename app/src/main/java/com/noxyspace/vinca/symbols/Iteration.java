package com.noxyspace.vinca.symbols;

public class Iteration extends Symbol {

    private boolean isStart;

    public Iteration(int id, String title, String description) {
        super(id, title, description);

        this.isStart = isStart;
    }

    public boolean getIsStart(){
        return isStart;
    }
}
