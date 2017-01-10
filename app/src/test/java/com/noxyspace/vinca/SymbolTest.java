package com.noxyspace.vinca;

import com.noxyspace.vinca.canvas.Timeline;
import com.noxyspace.vinca.symbols.ActivitySymbol;
import com.noxyspace.vinca.symbols.DecisionSymbol;
import com.noxyspace.vinca.symbols.ProcessSymbol;
import com.noxyspace.vinca.symbols.ProjectSymbol;

import org.junit.Test;


import static org.junit.Assert.assertEquals;

public class SymbolTest {

    // TODO : Test symboler - Magnus

    @Test
    public void addition_isCorrect() throws Exception {

        ActivitySymbol a1 = new ActivitySymbol(1,"Activity Title 1","Activity Description 1");
        ActivitySymbol a2 = new ActivitySymbol(2,"Activity Title 2","Activity Description 2");
        ActivitySymbol a3 = new ActivitySymbol(3,"Activity Title 3","Activity Description 3");

        DecisionSymbol d1 = new DecisionSymbol(4,"Decision Title 1","Decision Description 1");
        DecisionSymbol d2 = new DecisionSymbol(5,"Decision Title 2","Decision Description 2");

        ProjectSymbol projectSymbol1 = new ProjectSymbol(6, "Project Title 1", "Project Description 1");

        ProcessSymbol process1 = new ProcessSymbol(7, "Process Title 1",  "Process Description 1");
        ProcessSymbol process2 = new ProcessSymbol(7, "Process Title 2",  "Process Description 2");

        Timeline timeline = new Timeline();





         ////

         // ADD THE FIRST PROJECT
         timeline.addProject(projectSymbol1);

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
