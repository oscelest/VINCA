package com.noxyspace.vinca.symbols;

import java.util.List;

public class Process extends Parentheses {

    public Process(int id, String title, String description) {
        super(id, title, description);
    }

    public Process(int id, String title, String description, List<Symbol> contents) {
        super(id, title, description, contents);
    }
}
