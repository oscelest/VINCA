package com.noxyspace.vinca.canvas.symbols;

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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.noxyspace.vinca.R;
import com.noxyspace.vinca.canvas.actions.ActionManager;
import com.noxyspace.vinca.canvas.actions.ActionParameter;
import com.noxyspace.vinca.canvas.actions.derivatives.MoveAction;
import com.noxyspace.vinca.canvas.symbols.specifications.containers.expanded.SymbolContainerExpanded;
import com.noxyspace.vinca.canvas.symbols.specifications.containers.bracket.SymbolContainerBracketLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.containers.collapsed.SymbolContainerCollapsedLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.additionals.SymbolEmptyLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.containers.SymbolContainerLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.figures.activity.SymbolActivityLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.figures.decision.SymbolDecisionLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.figures.iteration.SymbolIterationLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.figures.method.SymbolMethodLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.figures.pause.SymbolPauseLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.figures.process.SymbolProcessLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.figures.project.SymbolProjectLayout;
import com.noxyspace.vinca.canvas.symbols.bar.trashcan.SymbolTrashcanLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.timeline.TimelineLayout;
import com.noxyspace.vinca.objects.ApplicationObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SymbolLayout extends SymbolLayoutDragHandler {
    public static final int SYMBOL_DIMENSION = 80;
    public static final int SYMBOL_DIMENSION_SMALL = 30;
    public static final int SYMBOL_DIMENSION_SMALL_LANDSCAPE = 40;
    private static boolean isMovingSymbol;

    protected static Toast toast = null;

    protected String title;
    protected String description;

    private boolean acceptsDrop;
    private int backgroundColor;

    public SymbolLayout(Context context, boolean acceptsDrop) {
        super(context);
        this.acceptsDrop = acceptsDrop;

        LinearLayout.LayoutParams params = null;

        if (this instanceof SymbolEmptyLayout) {
            params = new LinearLayout.LayoutParams(
                (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, SymbolEmptyLayout.WIDTH_COLLAPSED, getResources().getDisplayMetrics()),
                LayoutParams.MATCH_PARENT
            );
        } else {
            params = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            );
        }

        if (params != null) {
            if (this instanceof TimelineLayout) {
                int marginLeft = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, TimelineLayout.MARGIN_LEFT, getResources().getDisplayMetrics());
                int marginTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, TimelineLayout.MARGIN_TOP, getResources().getDisplayMetrics());

                params.setMargins(marginLeft, marginTop, 0, 0);
            } else if (!acceptsDrop && !(this instanceof SymbolContainerBracketLayout)) {
                int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
                params.setMargins(margin, 0, margin, 0);
            }

            this.setLayoutParams(params);
        }

        if (this instanceof TimelineLayout) {
            int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, TimelineLayout.PADDING, getResources().getDisplayMetrics());
            this.setPadding(0, 0, padding, 0);
            this.setMinimumHeight(padding);
            this.setOrientation(LinearLayout.HORIZONTAL);
        } else {
            this.setGravity(Gravity.CENTER_VERTICAL);
        }

        if (this.acceptsDrop) {
            this.setBackgroundColor(TimelineLayout.BACKGROUND_COLOR);
        }

        this.setOnDragListener(this);

        if (!(this instanceof SymbolEmptyLayout)) {
            this.setOnTouchListener(new View.OnTouchListener() {
                final Handler handler = new Handler();

                float x;
                float y;

                View dragView;

                Runnable longPressHandler = new Runnable() {
                    public void run() {
                        ((Activity) getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (dragView instanceof SymbolLayout && !(dragView instanceof SymbolTrashcanLayout)) {
                                    SymbolLayout dragLayout = ((SymbolLayout)dragView);

                                    if (dragLayout.isDropAccepted()) {
                                        if (dragLayout instanceof SymbolContainerBracketLayout) {
                                            ((SymbolLayout)dragLayout.getParent().getParent()).longpresSymbolDialog(getContext());
                                        } else {
                                            dragLayout.longpresSymbolDialog(getContext());
                                        }
                                    }

                                    dragView = null;
                                }
                            }
                        });
                    }
                };

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    System.out.println("Event: (" + event.getAction() + "): " + event.getX() + ", " + event.getY());

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            this.x = event.getX();
                            this.y = event.getY();
                            this.dragView = v;
                            handler.postDelayed(longPressHandler, 500);
                            return true;

                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            handler.removeCallbacks(longPressHandler);

                            if (event.getAction() == MotionEvent.ACTION_UP && this.dragView != null) {
                                if (v instanceof SymbolLayout) {
                                    if (!(v instanceof SymbolTrashcanLayout) && (((SymbolLayout)v).isDropAccepted() || (v.getParent() instanceof SymbolProjectLayout && ((SymbolLayout)v.getParent()).isDropAccepted()))) {
                                        if (v instanceof SymbolContainerLayout) {
                                            ((SymbolContainerLayout)v).toggleCollapse();
                                        } else if (v instanceof SymbolContainerBracketLayout) {
                                            ViewParent parent = v.getParent();

                                            if (parent != null && parent instanceof SymbolContainerExpanded) {
                                                parent = parent.getParent();

                                                if (parent != null && parent instanceof SymbolContainerLayout) {
                                                    ((SymbolContainerLayout)parent).toggleCollapse();
                                                }
                                            }
                                        }
                                    } else {
                                        if (v instanceof SymbolContainerBracketLayout && v.getParent() instanceof SymbolContainerExpanded && v.getParent().getParent() instanceof SymbolContainerLayout) {
                                            makeToast("Project\n\nA project object is...");
                                        } else if (v instanceof SymbolProcessLayout) {
                                            makeToast("Process\n\nA process object is....");
                                        } else if (v instanceof SymbolIterationLayout) {
                                            makeToast("Iteration\n\nAn iteration object is....");
                                        } else if (v instanceof SymbolPauseLayout) {
                                            makeToast("Pause\n\nA pause object is....");
                                        } else if (v instanceof SymbolDecisionLayout) {
                                            makeToast("Decision\n\nA decision object is....");
                                        } else if (v instanceof SymbolActivityLayout) {
                                            makeToast("Activity\n\nAn activity object is....");
                                        } else if (v instanceof SymbolMethodLayout) {
                                            makeToast("Method\n\nA method object is....");
                                        } else if (v instanceof SymbolTrashcanLayout) {
                                            makeToast("Trashcan\n\nThis is the trashcan....");
                                        } else {
                                            makeToast("This symbol is unknown");
                                        }
                                    }
                                }
                            }

                            break;

                        case MotionEvent.ACTION_MOVE:
                            if (this.dragView != null && Math.abs(this.x - event.getX()) > 1 && Math.abs(this.y - event.getY()) > 1 && !(this.dragView instanceof SymbolTrashcanLayout)) {
                                handler.removeCallbacks(longPressHandler);

                                if (this.dragView instanceof SymbolContainerBracketLayout) {
                                    if (this.dragView.getParent() != null && this.dragView.getParent() instanceof SymbolContainerExpanded) {
                                        if (this.dragView.getParent().getParent() != null && this.dragView.getParent().getParent() instanceof SymbolContainerLayout) {
                                            SymbolContainerLayout containerLayout = (SymbolContainerLayout)this.dragView.getParent().getParent();

                                            if (!containerLayout.isDropAccepted()) {
                                                this.dragView = (ViewGroup) this.dragView.getParent().getParent();
                                            } else if (containerLayout instanceof SymbolProjectLayout) {
                                                this.dragView = null;
                                            }
                                        }
                                    }
                                }

                                if (this.dragView != null) {
                                    ClipData data = ClipData.newPlainText("", "");
                                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(this.dragView);

                                    this.dragView.startDrag(data, shadowBuilder, this.dragView, 0);
                                    this.dragView.setVisibility(View.VISIBLE);

                                    this.dragView = null;
                                }
                            }

                            break;

                        default:
                            break;
                    }

                    return false;
                }
            });
        }
    }

    public boolean isDropAccepted() {
        return this.acceptsDrop;
    }

    public int removeViews() {
        ViewGroup parent = (ViewGroup)this.getParent();
        int currentIndex = parent.indexOfChild(this);

        SymbolLayout empty = (SymbolLayout)parent.getChildAt(currentIndex + 1);

        parent.removeView(this);
        parent.removeView(empty);

        return currentIndex;
    }

    public void moveView(View targetView) {
        this.moveView(targetView, -1);
    }

    public void moveView(View targetView, int targetIndex) {
        isMovingSymbol = true;

        boolean isContainer = ((this instanceof SymbolContainerLayout) && (targetView instanceof SymbolContainerExpanded));

        if ((isContainer && ((SymbolContainerLayout)this).getExpandedLayout() != targetView) || (!isContainer && this != targetView)) {
            List<View> children = this.fetchAllChildViews();

            if (!children.contains(targetView)) {
                if (!ActionManager.isManagingAction) {
                    ActionManager.getInstance().add(new MoveAction(this,
                        new ActionParameter((View) this.getParent(), ((ViewGroup)this.getParent()).indexOfChild(this)),
                        new ActionParameter(targetView, targetIndex))
                    );
                }

                ViewGroup parent = (ViewGroup)this.getParent();
                int currentIndex = this.removeViews();

                if (targetIndex != -1) {
                    /* Account for the two symbols we just removed, if they destination is the current parent */
                    targetIndex -= (targetView == parent && currentIndex < targetIndex ? 2 : 0);
                }

                System.out.println("Adding " + this.getClass().getSimpleName() + " to " + targetView.getClass().getSimpleName() + " with targetIndex = " + targetIndex);
                ((ViewGroup)targetView).addView(this, targetIndex);

                if (targetView instanceof SymbolContainerExpanded) {
                    ((ViewGroup)targetView).addView(new SymbolEmptyLayout(getContext()), targetIndex + 1);
                }
            } else {
                this.makeToast("Cannot move a parent object into a child object");
            }
        } else {
            this.makeToast("Cannot move an object into itself");
        }

        isMovingSymbol = false;
    }

    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();

        if (!(this instanceof SymbolEmptyLayout) && !(this instanceof SymbolContainerBracketLayout)) {
            try {
                if (this instanceof TimelineLayout) {
                    json.put("type", "timeline");
                } else if (this instanceof SymbolProjectLayout) {
                    json.put("type", "project");
                } else if (this instanceof SymbolProcessLayout) {
                    json.put("type", "process");
                    json.put("collapsed", ((SymbolProcessLayout)this).isCollapsed());
                } else if (this instanceof SymbolIterationLayout) {
                    json.put("type", "iteration");
                    json.put("collapsed", ((SymbolIterationLayout)this).isCollapsed());
                } else if (this instanceof SymbolPauseLayout) {
                    json.put("type", "pause");
                } else if (this instanceof SymbolDecisionLayout) {
                    json.put("type", "decision");
                } else if (this instanceof SymbolActivityLayout) {
                    json.put("type", "activity");
                    json.put("method", ((SymbolActivityLayout) this).hasMethod());
                } else {
                    json.put("type", "UNKNOWN (" + this.getClass().getSimpleName() + ")");
                }

                json.put("title", this.title == null ? "" : this.title);
                json.put("description", this.description == null ? "" : this.description);

                JSONArray childArray = new JSONArray();
                List<View> children = this.fetchChildViews();

                for (View child : children) {
                    if (child instanceof SymbolLayout) {
                        JSONObject jsonChild = ((SymbolLayout)child).toJsonObject();

                        if (jsonChild.length() != 0) {
                            childArray.put(jsonChild);
                        }
                    }
                }

                json.put("children", childArray);
            } catch (JSONException e) {
                System.out.println(e.getMessage());
            }
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
                layout = new SymbolProcessLayout(getContext(), true, json.has("collapsed") ? json.getBoolean("collapsed") : false);
            } else if(type.equals("iteration")) {
                layout = new SymbolIterationLayout(getContext(), true, json.has("collapsed") ? json.getBoolean("collapsed") : false);
            } else if(type.equals("pause")) {
                layout = new SymbolPauseLayout(getContext());
            } else if(type.equals("decision")) {
                layout = new SymbolDecisionLayout(getContext());
            } else if(type.equals("activity")) {
                layout = new SymbolActivityLayout(getContext());

                if (json.has("method")) {
                    ((SymbolActivityLayout) layout).setMethod(json.getBoolean("method"));
                }
            } else {
                System.out.println("Unexpected object type: " + type);
            }

            if (layout != null) {
                if (json.has("title")) {
                    layout.title = json.getString("title");

                    if (layout instanceof SymbolContainerCollapsedLayout) {
                        ((SymbolContainerCollapsedLayout)layout).setHeader(layout.title);
                    } else if (layout instanceof SymbolContainerLayout) {
                        ((SymbolContainerLayout)layout).setHeader(layout.title);
                    }
                }

                if (json.has("description")) {
                    layout.description = json.getString("description");
                }

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

    protected void makeToast(String message) {
        ApplicationObject.getInstance().makeToast(getContext(), message);
    }

    @Override
    protected boolean onDragStarted(View v, DragEvent event) {
        return this.acceptsDrop;
    }

    @Override
    protected boolean onDragEnded(View v, DragEvent event) {
        this.onHighlightExited(v);
        this.onExitSymbol();
        return true;
    }

    @Override
    protected boolean onDragEntered(View v, DragEvent event) {
        this.onHighlightEnter(v);
        this.onEnterSymbol();
        return true;
    }

    @Override
    protected boolean onDragExited(View v, DragEvent event) {
        this.onHighlightExited(v);
        this.onExitSymbol();
        return true;
    }

    protected void onEnterSymbol() {
        /* Abstract placeholder */
    }

    protected void onExitSymbol() {
        /* Abstract placeholder */
    }

    public List<View> fetchAllChildViews() {
        List<View> children = new ArrayList<>();

        for (int i = 0, childCount = this.getChildCount(); i < childCount; i++) {
            View child = this.getChildAt(i);

            if (child instanceof SymbolLayout) {
                children.add(child);

                if (child instanceof SymbolContainerLayout) {
                    children.add(((SymbolContainerLayout)child).getExpandedLayout());
                }

                children.addAll(((SymbolLayout)child).fetchAllChildViews());
            }
        }

        return children;
    }

    public List<View> fetchChildViews() {
        List<View> children = new ArrayList<>();

        for (int i = 0, childCount = this.getChildCount(); i < childCount; i++) {
            children.add(this.getChildAt(i));
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
                if (SymbolLayout.this instanceof SymbolContainerCollapsedLayout) {
                    ((SymbolContainerCollapsedLayout)SymbolLayout.this).setHeader(title = symbolTitle.getText().toString());
                } else if (SymbolLayout.this instanceof SymbolContainerLayout) {
                    ((SymbolContainerLayout)SymbolLayout.this).setHeader(title = symbolTitle.getText().toString());
                }

                description = symbolDescription.getText().toString();
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

    protected void onHighlightEnter(View v) {
        Drawable background = v.getBackground();

        if (background != null && background instanceof ColorDrawable) {
            this.backgroundColor = ((ColorDrawable)background).getColor();

            if (v instanceof SymbolTrashcanLayout) {
                v.setBackgroundColor(SymbolTrashcanLayout.HIGHLIGHT_COLOR);
            } else {
                v.setBackgroundColor(TimelineLayout.HIGHLIGHT_COLOR);
            }
        }
    }

    protected void onHighlightExited(View v) {
        if (this.backgroundColor != 0) {
            v.setBackgroundColor(this.backgroundColor);
            this.backgroundColor = 0;
        }
    }
}
