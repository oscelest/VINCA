package com.noxyspace.vinca.symbols;

import java.util.ArrayList;

public class Process extends Parentheses {

    public Process(int id, String title, String description) {
        super(id, title, description);
    }

    public Process(int id, String title, String description, ArrayList<Symbol> contents) {
        super(id, title, description, contents);
    }
}
