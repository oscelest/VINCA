package com.noxyspace.vinca.symbols;

public class Project extends Symbol {

    private boolean isStart;

    public Project(int id, String title, String description) {
        super(id, title, description);

        this.isStart = isStart;
    }

    public boolean getIsStart(){
        return isStart;
    }
}
