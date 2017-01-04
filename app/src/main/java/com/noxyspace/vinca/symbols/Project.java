package com.noxyspace.vinca.symbols;

import java.util.ArrayList;

public class Project extends Parentheses {

    public Project(int id, String title, String description) {
        super(id, title, description);
    }

    public Project(int id, String title, String description, ArrayList<Symbol> contents) {
        super(id, title, description, contents);
    }
}
