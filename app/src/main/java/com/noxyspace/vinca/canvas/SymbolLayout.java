package com.noxyspace.vinca.canvas;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
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
import com.noxyspace.vinca.canvas.symbols.SymbolTitle;
import com.noxyspace.vinca.canvas.symbols.specifications.activity.SymbolActivityLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.decision.SymbolDecisionLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.iteration.SymbolIterationLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.pause.SymbolPauseLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.process.SymbolProcessLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.project.SymbolProjectLayout;
import com.noxyspace.vinca.canvas.symbols.trashcan.SymbolTrashcanLayout;
import com.noxyspace.vinca.canvas.symbols.timeline.TimelineLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SymbolLayout extends SymbolLayoutDragHandler {
    public static final int SYMBOL_DIMENSION = 80;

    private String title;
    private String description;

    private boolean acceptsDrop;
    private int backgroundColor;

    public SymbolLayout(Context context, boolean acceptsDrop) {
        super(context);

        //super.addView(new SymbolTitle(context, "Title"));
        this.acceptsDrop = acceptsDrop;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        );

        if (this instanceof TimelineLayout) {
            int marginLeft = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, TimelineLayout.MARGIN_LEFT, getResources().getDisplayMetrics());
            int marginTop = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, TimelineLayout.MARGIN_TOP, getResources().getDisplayMetrics());

            params.setMargins(marginLeft, marginTop, 0, 0);
        } else if (!acceptsDrop) {
            int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
            params.setMargins(margin, 0, margin, 0);
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

    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();

        try {
            if (this instanceof TimelineLayout) {
                json.put("type", "timeline");
            } else if (this instanceof SymbolProjectLayout) {
                json.put("type", "project");
            } else if (this instanceof SymbolProcessLayout) {
                json.put("type", "process");
            } else if (this instanceof SymbolIterationLayout) {
                json.put("type", "iteration");
            } else if (this instanceof SymbolPauseLayout) {
                json.put("type", "pause");
            } else if (this instanceof SymbolDecisionLayout) {
                json.put("type", "decision");
            } else if (this instanceof SymbolActivityLayout) {
                json.put("type", "activity");
            } else {
                json.put("type", "UNKNOWN (" + this.getClass().getSimpleName() + ")");
            }

            JSONArray childArray = new JSONArray();
            List<View> children = this.fetchChildViews(this);

            for (View child : children) {
                if (child instanceof SymbolLayout) {
                    childArray.put(((SymbolLayout)child).toJsonObject());
                }
            }

            json.put("children", childArray);
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }

        return json;
    }

    public void fromJsonObject(JSONObject json) {
        try {
            SymbolLayout layout = null;
            String type = json.getString("type");

            if (type.equals("timeline")) {
                layout = this;
            } else if (type.equals("project")) {
                layout = new SymbolProjectLayout(getContext());
            } else if(type.equals("process")) {
                layout = new SymbolProcessLayout(getContext());
            } else if(type.equals("iteration")) {
                layout = new SymbolIterationLayout(getContext());
            } else if(type.equals("pause")) {
                layout = new SymbolPauseLayout(getContext());
            } else if(type.equals("decision")) {
                layout = new SymbolDecisionLayout(getContext());
            } else if(type.equals("activity")) {
                layout = new SymbolActivityLayout(getContext());
            } else {
                System.out.println("Unexpected object type: " + type);
            }

            if (layout != null) {
                JSONArray childArray = json.getJSONArray("children");

                for (int i = 0; i < childArray.length(); i++) {
                    layout.fromJsonObject(childArray.getJSONObject(i));
                }

                if (layout != this) {
                    this.addView(layout);
                }
            }
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }
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
                v.setBackgroundColor(SymbolTrashcanLayout.HIGHLIGHT_COLOR);
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
        this.moveView(view, targetView, -1);
    }

    public void moveView(View view, View targetView, int targetIndex) {
        if (view != targetView) {
            List<View> children = new ArrayList<>();
            this.fetchAllChildViews(children, view);

            if (!children.contains(targetView)) {
                ViewGroup parent = (ViewGroup)view.getParent();

                if (parent instanceof SymbolContainerLayout) {
                    ((SymbolContainerLayout)parent).removeCollapsibleView(view);
                } else {
                    parent.removeView(view);
                }

                if (targetIndex == -1) {
                    ((ViewGroup) targetView).addView(view);
                } else {
                    ((ViewGroup) targetView).addView(view, targetIndex);
                }
            } else {
                Toast.makeText(getContext(), "Cannot move a parent object into a child object", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void fetchAllChildViews(List<View> children, View parent) {
        if (parent instanceof ViewGroup) {
            if (parent instanceof SymbolContainerLayout) {
                List<View> collapsedViews = ((SymbolContainerLayout)parent).getCollapsedViews();

                for (View child : collapsedViews) {
                    children.add(child);
                    this.fetchAllChildViews(children, child);
                }
            } else {
                int childCount = ((ViewGroup) parent).getChildCount();

                for (int i = 0; i < childCount; i++) {
                    View child = ((ViewGroup) parent).getChildAt(i);

                    children.add(child);
                    this.fetchAllChildViews(children, child);
                }
            }
        }
    }

    private List<View> fetchChildViews(View parent) {
        List<View> children = new ArrayList<>();

        if (parent instanceof ViewGroup) {
            if (parent instanceof SymbolContainerLayout) {
                List<View> collapsedViews = ((SymbolContainerLayout)parent).getCollapsedViews();

                for (View child : collapsedViews) {
                    children.add(child);
                }
            } else {
                int childCount = ((ViewGroup)parent).getChildCount();

                for (int i = 0; i < childCount; i++) {
                    children.add(((ViewGroup) parent).getChildAt(i));
                }
            }
        }

        return children;
    }

    private void longpresSymbolDialog(Context context) {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.canvas_longpress_dialog, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("VINCA");
        builder.setView(layout);

        final EditText symbolTitle = (EditText)layout.findViewById(R.id.symbolTitle);
        symbolTitle.setText(this.title);

        final EditText symbolDescription = (EditText)layout.findViewById(R.id.symbolDescription);
        symbolDescription.setText(this.description);

        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                title = symbolTitle.getText().toString();
                description = symbolDescription.getText().toString();

                /* Change the stored information */
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
