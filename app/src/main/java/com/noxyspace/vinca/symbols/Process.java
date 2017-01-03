package com.noxyspace.vinca.symbols;

public class Process extends Symbol {

    private boolean isStart;

    public Process(int id, String title, String description, boolean isStart) {
        super(id, title, description);

        this.isStart = isStart;
    }

    public boolean getIsStart(){
        return isStart;
    }
}
