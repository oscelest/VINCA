package com.noxyspace.vinca.canvas;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.noxyspace.vinca.R;
import com.noxyspace.vinca.canvas.symbols.SymbolContainerLayout;
import com.noxyspace.vinca.canvas.symbols.iteration.SymbolIterationLayout;
import com.noxyspace.vinca.canvas.symbols.process.SymbolProcessLayout;
import com.noxyspace.vinca.canvas.symbols.project.SymbolProjectLayout;
import com.noxyspace.vinca.canvas.symbols.trashcan.SymbolTrashcanLayout;
import com.noxyspace.vinca.canvas.timeline.TimelineLayout;

import java.util.ArrayList;
import java.util.List;

public class SymbolLayout extends SymbolLayoutDragHandler {
    public static final int SYMBOL_DIMENSION = 80;

    private String titleInput;
    private String descriptionInput;

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
                final Handler handler = new Handler();

                View dragView;

                Runnable longPressHandler = new Runnable() {
                    public void run() {
                        ((Activity)getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (dragView instanceof SymbolLayout) {
                                    SymbolLayout dragLayout = (SymbolLayout)dragView;

                                    if (dragLayout.isDropAccepted()) {
                                        dragLayout.longpresSymbolDialog(getContext());
                                    }

                                    dragView = null;
                                }
                            }
                        });
                    }
                };

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            this.dragView = v;
                            handler.postDelayed(longPressHandler, 500);
                            return true;

                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            handler.removeCallbacks(longPressHandler);

                            if (event.getAction() == MotionEvent.ACTION_UP && this.dragView != null) {
                                if ((v instanceof SymbolContainerLayout) && ((SymbolContainerLayout)v).isDropAccepted()) {
                                    ((SymbolContainerLayout)v).toggleCollapse();
                                }
                            }

                            break;

                        case MotionEvent.ACTION_MOVE:
                            if (this.dragView != null) {
                                handler.removeCallbacks(longPressHandler);

                                ClipData data = ClipData.newPlainText("", "");
                                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(this.dragView);

                                this.dragView.startDrag(data, shadowBuilder, this.dragView, 0);
                                this.dragView.setVisibility(View.VISIBLE);

                                this.dragView = null;
                            }

                            break;

                        default:
                            break;
                    }

                    return false;
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

            if (v instanceof SymbolTrashcanLayout) {
                v.setBackgroundColor(Color.RED);
            } else {
                v.setBackgroundColor(TimelineLayout.HIGHLIGHT_COLOR);
            }
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

    public void longpresSymbolDialog(Context context) {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.canvas_longpress_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("VINCA");
        builder.setView(layout);

        final EditText symbolTitle = (EditText) layout.findViewById(R.id.symbolTitle);
        final EditText symbolDescription = (EditText) layout.findViewById(R.id.symbolDescription);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                titleInput = symbolTitle.getText().toString();
                descriptionInput = symbolDescription.getText().toString();
                System.out.println(titleInput + " lalalal" + descriptionInput);

            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
