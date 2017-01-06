package com.noxyspace.vinca.activities.helpers;

/**
 * Created by MikkelLeth on 06/01/17.
 */

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.noxyspace.vinca.R;

import java.io.File;

public class dragHandler extends AppCompatActivity implements View.OnDragListener{

    @Override
    public boolean onDrag(View view, DragEvent event) {
        //Log.d("Editor - DragEvent", "drag event recieved: " + DragEvent.class.getDeclaredFields()[event.getAction()]);
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                Log.d("Editor - Debug", "Started dragging " + view.getId());
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                if (view == trashCan) {
                    view.setBackgroundColor(0);
                } else {
                    Log.d("Editor - Debug", "Drag exited view: " + view.getId() + view.toString());
                    viewManager.highlightView(viewManager.getCursor());
                }
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                if (view == trashCan) {
                    view.setBackgroundColor(Color.RED);
                }
                else {
                    Log.d("Editor - Debug", "Drag entered into view: " + view.toString());
                    viewManager.highlightView(view);
                }
                break;
            case DragEvent.ACTION_DROP:
                VincaElementView draggedView;
                try {
                    draggedView = (VincaElementView) event.getLocalState();
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    break;
                }
                draggedView.setVisibility(View.VISIBLE);

                //User dropped a view on top of itself?
                if (view == draggedView) {
                    break;
                }
                //User dropped a view into trash bin?
                if (view == trashCan) {
                    Log.d("Editor - Debug", "Dropped on view trashbin! - Deleting");
                    view.setBackgroundColor(0);
                    viewManager.deleteElement(draggedView);

                    break;
                }
                Log.d("Editor - Debug", "Dropped on view: " + ((VincaElementView) view).type + "\n"
                        + ((VincaElementView) view).element.toString());

                if (view instanceof VincaElementView) {
                    if (draggedView instanceof VincaElementView) {
                        viewManager.moveElement(draggedView, (VincaElementView) view);
                    }
                    viewManager.highlightView(viewManager.getCursor());
                    break;
                }
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                Log.d("Editor - Debug", "Drop ended!");
                try {
                    draggedView = (VincaElementView) event.getLocalState();
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    break;
                }
                draggedView.setVisibility(View.VISIBLE);
                break;
        }
        //This listener should always receive drag-events, so always return true!
        return true;
    }
}
