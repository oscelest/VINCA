package com.noxyspace.vinca;

import com.noxyspace.vinca.canvas.Timeline;
import com.noxyspace.vinca.symbols.Activity;
import com.noxyspace.vinca.symbols.Decision;
import com.noxyspace.vinca.symbols.Process;
import com.noxyspace.vinca.symbols.Project;

import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class SymbolTest {

    // TODO : Test symboler - Magnus

    @Test
    public void addition_isCorrect() throws Exception {

        Activity a1 = new Activity(1,"Activity Title 1","Activity Description 1");
        Activity a2 = new Activity(2,"Activity Title 2","Activity Description 2");
        Activity a3 = new Activity(3,"Activity Title 3","Activity Description 3");

        Decision d1 = new Decision(4,"Decision Title 1","Decision Description 1");
        Decision d2 = new Decision(5,"Decision Title 2","Decision Description 2");

        Project project1 = new Project(6, "Project Title 1", "Project Description 1");

        Process process1 = new Process(7, "Process Title 1",  "Process Description 1");
        Process process2 = new Process(7, "Process Title 2",  "Process Description 2");

        Timeline timeline = new Timeline();





         ////

         // ADD THE FIRST PROJECT
         timeline.addProject(project1);

        // CHECK PLACEMENT OF PROJECT IN TIMELINE LIST

        int placementProject1 = timeline.searchForElement(6);

        assertEquals(placementProject1, 1);




        /**

         // ADD THE FIRST ACTIVITY TO THE PROJECT
         // TODO : ADD SYMBOL WITH AUTOMATIC PLACEMENT
         timeline.addSymbol(a1, timeline.searchForElement(6));

         // ADD THE FIRST PROCESS
         timeline.addSymbol();

         */










    }
}
