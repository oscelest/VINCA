package com.noxyspace.vinca.canvas;

import android.content.ClipData;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.noxyspace.vinca.canvas.symbols.trashcan.SymbolTrashcanLayout;
import com.noxyspace.vinca.canvas.timeline.TimelineLayout;

import java.util.ArrayList;
import java.util.List;

public class SymbolLayout extends SymbolLayoutDragHandler {
    public static final int SYMBOL_DIMENSION = 80;

    private boolean acceptsDrop;
    private int backgroundColor;

    public SymbolLayout(Context context, boolean acceptsDrop) {
        super(context);

        this.acceptsDrop = acceptsDrop;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        );

        if (this instanceof TimelineLayout) {
            int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, TimelineLayout.PADDING, getResources().getDisplayMetrics());
            params.setMargins(margin, 0, 0, 0);
        }

        this.setLayoutParams(params);

        if (this instanceof TimelineLayout) {
            int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, TimelineLayout.PADDING, getResources().getDisplayMetrics());
            this.setPadding(0, 0, padding, 0);
            this.setMinimumHeight(padding);
            this.setOrientation(LinearLayout.HORIZONTAL);
            this.setBackgroundColor(TimelineLayout.BACKGROUND_COLOR);
        }

        if (!(this instanceof SymbolTrashcanLayout)) {
            this.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        ClipData data = ClipData.newPlainText("", "");
                        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);

                        v.startDrag(data, shadowBuilder, v, 0);
                        v.setVisibility(View.VISIBLE);
                        return true;
                    } else {
                        return false;
                    }
                }
            });
        }

        this.setOnDragListener(this);
    }

    public boolean isDropAccepted() {
        return this.acceptsDrop;
    }

    @Override
    protected boolean onDragStarted(View v, DragEvent event) {
        return this.acceptsDrop;
    }

    @Override
    protected boolean onDragEnded(View v, DragEvent event) {
        if (this.backgroundColor != 0) {
            v.setBackgroundColor(this.backgroundColor);
            this.backgroundColor = 0;
        }

        return true;
    }

    @Override
    protected boolean onDragEntered(View v, DragEvent event) {
        Drawable background = v.getBackground();

        if (background == null) {
            background = ((View)v.getParent()).getBackground();
        }

        if (background != null && background instanceof ColorDrawable) {
            this.backgroundColor = ((ColorDrawable)background).getColor();
            v.setBackgroundColor(TimelineLayout.HIGHLIGHT_COLOR);
        }

        return true;
    }

    @Override
    protected boolean onDragExited(View v, DragEvent event) {
        if (this.backgroundColor != 0) {
            v.setBackgroundColor(this.backgroundColor);
            this.backgroundColor = 0;
        }

        return true;
    }

    protected void moveView(View view, View targetView) {
        if (view != targetView) {
            List<View> children = new ArrayList<>();
            this.fetchAllChildViews(children, view);

            if (!children.contains(targetView)) {
                ((ViewGroup)view.getParent()).removeView(view);
                ((ViewGroup)targetView).addView(view);
            } else {
                Toast.makeText(getContext(), "Cannot move a parent object into a child object", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void fetchAllChildViews(List<View> children, View parent) {
        if (parent instanceof ViewGroup) {
            int childCount = ((ViewGroup)parent).getChildCount();

            for (int i = 0; i < childCount; i++) {
                View child = ((ViewGroup)parent).getChildAt(i);
                children.add(child);

                this.fetchAllChildViews(children, child);
            }
        }
    }
}
