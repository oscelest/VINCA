package com.noxyspace.vinca.canvas;

import android.support.annotation.NonNull;

import com.noxyspace.vinca.symbols.Activity;
import com.noxyspace.vinca.symbols.Decition;
import com.noxyspace.vinca.symbols.Method;
import com.noxyspace.vinca.symbols.Space;
import com.noxyspace.vinca.symbols.Symbol;

import java.util.ArrayList;
import java.util.List;


public class Timeline {

    private List<Symbol> timeline = new ArrayList<Symbol>();
    private int currentPosition;

    public void updateTimelinePosition(int elementId){

    }

    public int getCurrentPosition(){
        return currentPosition;
    }


    public List<Symbol> getTimeline(){
        return timeline;
    }

    public int getNumberOfElements(){

        return 1;
    }



    public void addActivity(int id, String title, String description){
        timeline.add(new Activity(id, title, description));
    }

    public void addDecition(int id, String title, String description){
        timeline.add(new Decition(id, title, description));
    }

    public void addSpace(int id, String title, String description){
        timeline.add(new Space(id, title, description));
    }

    public void addMethod(int id, String title, String description, int methodId){
        timeline.add(new Method(id, title, description, methodId));
    }

    public void addProcess(){
    }

    public void addIteration(){
    }

    public void addProject(){
    }
















}
