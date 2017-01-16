package com.noxyspace.vinca.canvas.symbols;

import android.content.Context;
import android.graphics.Rect;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.noxyspace.vinca.canvas.symbols.specifications.containers.collapsed.SymbolContainerCollapsedLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.timeline.TimelineLayout;

public class SymbolLayoutDragHandler extends LinearLayout implements View.OnDragListener {
    public SymbolLayoutDragHandler(Context context){
        super(context);
    }

    private static boolean PtInRect(Rect rc, float x, float y) {
        return (rc.left <= x && rc.right >= x && rc.top <= y && rc.bottom >= y);
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                return this.onDragStarted(v, event);

            case DragEvent.ACTION_DROP:
                return this.onDragDrop(v, event);

            case DragEvent.ACTION_DRAG_ENDED:
                return this.onDragEnded(v, event);

            case DragEvent.ACTION_DRAG_ENTERED:
                int[] position = new int[2];
                ((View) this.getParent()).getLocationOnScreen(position);

                float x = event.getX() + position[0];
                float y = event.getY() + position[1];

                for (int i = 0, childCount = this.getChildCount(); i < childCount; i++) {
                    Rect childRect = new Rect(0, 0, 0, 0);
                    this.getChildAt(i).getGlobalVisibleRect(childRect);

                    if (!(this.getChildAt(i) instanceof ImageView) && !(this.getChildAt(i) instanceof TextView)) {
                        if (PtInRect(childRect, x, y)) {
                            return false;
                        }
                    }
                }

                return this.onDragEntered(v, event);

            case DragEvent.ACTION_DRAG_EXITED:
                return this.onDragExited(v, event);

            default:
                break;
        }

        return false;
    }

    protected boolean onDragStarted(View v, DragEvent event) {
        return false;
    }

    public boolean onDragDrop(View v, DragEvent event) {
        return false;
    }

    protected boolean onDragEnded(View v, DragEvent event) {
        return false;
    }

    protected boolean onDragEntered(View v, DragEvent event) {
        return false;
    }

    protected boolean onDragExited(View v, DragEvent event) {
        return false;
    }
}