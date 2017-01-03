package com.noxyspace.vinca.symbols;

public class Method extends Symbol {

    private int methodId;

    public Method(int id, String title, String description, int methodId) {
        super(id, title, description);

        this.methodId = methodId;
    }

    public int getMethodId() {
        return methodId;
    }
}
