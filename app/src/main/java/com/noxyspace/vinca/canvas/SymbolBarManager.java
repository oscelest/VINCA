package com.noxyspace.vinca.canvas;

import com.noxyspace.vinca.symbols.Activity;
import com.noxyspace.vinca.symbols.Decision;
import com.noxyspace.vinca.symbols.Iteration;
import com.noxyspace.vinca.symbols.Method;
import com.noxyspace.vinca.symbols.Process;
import com.noxyspace.vinca.symbols.Project;
import com.noxyspace.vinca.symbols.Space;
import com.noxyspace.vinca.symbols.Symbol;

import java.util.ArrayList;

public class SymbolBarManager {

    private Activity activity;
    private Decision decision;
    private Iteration iteration;
    private Method method;
    private Process process;
    private Project project;
    private Space space;
    private ArrayList<Symbol> list = new ArrayList<Symbol>();

    public SymbolBarManager(){
        activity = new Activity(0, null, null);
        decision = new Decision(0, null, null);
        iteration = new Iteration(0, null, null);
        method = new Method(0, null, null, 0);
        process = new Process(0, null, null);
        project = new Project(0, null, null);
        space = new Space(0, null, null);
    }

    public boolean checkSymbolListForElement(Symbol symbol){
        return list.contains(symbol);
    }
}
