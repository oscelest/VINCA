package com.noxyspace.vinca.symbols;

import java.util.List;

public class Iteration extends Parentheses {

    public Iteration(int id, String title, String description) {
        super(id, title, description);
    }

    public Iteration(int id, String title, String description, List<Symbol> contents) {
        super(id, title, description, contents);
    }
}
