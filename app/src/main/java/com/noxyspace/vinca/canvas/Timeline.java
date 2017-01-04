package com.noxyspace.vinca.canvas;

import com.noxyspace.vinca.symbols.Parentheses;
import com.noxyspace.vinca.symbols.Project;

import java.util.ArrayList;
import java.util.List;

public class Timeline {

    private List<Parentheses> timeline = new ArrayList<Parentheses>();
    private int currentPosition;

    /**
     * Updates the current position in the timeline, used for a curser
     * @param elementId
     */
    public void updateTimelinePosition(int elementId){
        currentPosition = searchForElement(elementId);
    }

    /**
     * Gets the current position set in the timeline
     * @return Current position
     */
    public int getCurrentPosition(){
        return currentPosition;
    }

    /**
     * Method might not work as it shuld
     * Searches for the elements position in the timeline, if no such element is found, it returns -1
     * @param elementId The element id that is searched for
     * @return The element position or -1 if none are found
     */
    public int searchForElement(int elementId){

        int elementPosition = 0;

        for (int i = 0 ; i < timeline.size() ; i++){                                                // Each Project in the timeline
            for (int j = 0 ; j < timeline.get(i).numberOfElements() ; j ++){                        // Each element in the Project

                    if (timeline.get(i).getContents().get(j).getId() == elementId){                 // Looks at each element in the Projects contents, if the id of the element == elementId, j is returned
                        return j;
                    }
                    else if (timeline.get(i).getContents().get(j) instanceof Parentheses){          // If the element is a type of Parentheses (Process or Iteration)

                        int tempPos = ((Parentheses) timeline.get(i).getContents().get(j)).searchForElementPosition(elementId, -1);     // Searches the Parentheses element for the desired element
                        if (tempPos >= 0){
                            return j + tempPos;
                        }
                    }
            }
        }
        return -1;
    }

//    public int getNumberOfElements(){
//        return 1;
//    }

    public void addProject(int id, String title, String description){
        timeline.add(new Project(id, title, description));
    }
}
