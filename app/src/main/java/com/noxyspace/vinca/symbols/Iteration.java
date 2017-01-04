package com.noxyspace.vinca.symbols;

import java.util.ArrayList;

public class Iteration extends Parentheses {

    public Iteration(int id, String title, String description) {
        super(id, title, description);
    }

    public Iteration(int id, String title, String description, ArrayList<Symbol> contents) {
        super(id, title, description, contents);
    }
}
