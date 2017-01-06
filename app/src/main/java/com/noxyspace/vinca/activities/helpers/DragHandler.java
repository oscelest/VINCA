package com.noxyspace.vinca.activities.helpers;

/**
 * Created by MikkelLeth on 06/01/17.
 */

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;


public class DragHandler extends AppCompatActivity implements View.OnDragListener{

    @Override
    public boolean onDrag(View view, DragEvent event) {
        //Log.d("Editor - DragEvent", "drag event recieved: " + DragEvent.class.getDeclaredFields()[event.getAction()]);
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                Log.d("Editor - Debug", "Started dragging " + view.getId());

                break;
            case DragEvent.ACTION_DRAG_EXITED:
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                break;
            case DragEvent.ACTION_DROP:
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                Log.d("Editor - Debug", "Drop ended!");
                break;
        }
        //This listener should always receive drag-events, so always return true!
        return true;
    }
}
