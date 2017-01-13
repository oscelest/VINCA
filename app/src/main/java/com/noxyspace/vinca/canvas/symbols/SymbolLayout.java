package com.noxyspace.vinca.canvas.symbols;

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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.noxyspace.vinca.R;
import com.noxyspace.vinca.activities.CanvasActivity;
import com.noxyspace.vinca.canvas.actions.ActionManager;
import com.noxyspace.vinca.canvas.actions.ActionParameter;
import com.noxyspace.vinca.canvas.actions.derivatives.AddAction;
import com.noxyspace.vinca.canvas.actions.derivatives.MoveAction;
import com.noxyspace.vinca.canvas.symbols.specifications.SymbolContainerBracketLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.SymbolContainerCollapsed;
import com.noxyspace.vinca.canvas.symbols.specifications.empty.SymbolEmptyLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.SymbolContainerLayout;
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

import io.fabric.sdk.android.InitializationCallback;

public class SymbolLayout extends SymbolLayoutDragHandler {
    public static final int SYMBOL_DIMENSION = 80;
    private static boolean isMovingSymbol;

    protected static Toast toast = null;

    private String title;
    private String description;

    private boolean acceptsDrop;
    private int backgroundColor;

    public SymbolLayout(Context context, boolean acceptsDrop) {
        super(context);
        //super.addView(new SymbolTitle(context, "Title"));

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
            } else if (!acceptsDrop) {
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
            this.setBackgroundColor(TimelineLayout.BACKGROUND_COLOR);
        } else if (this instanceof SymbolEmptyLayout) {
            //this.setBackgroundColor(Color.RED);
        }

        if (!(this instanceof SymbolContainerBracketLayout)) {
            this.setOnDragListener(this);
        }

        if (!(this instanceof SymbolEmptyLayout)) {
            this.setOnTouchListener(new View.OnTouchListener() {
                final Handler handler = new Handler();

                View dragView;

                Runnable longPressHandler = new Runnable() {
                    public void run() {
                        ((Activity) getContext()).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (dragView instanceof SymbolLayout && !(dragView instanceof SymbolTrashcanLayout)) {
                                    SymbolLayout dragLayout = (SymbolLayout) dragView;

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
                                if (v instanceof SymbolLayout) {
                                    if (!(v instanceof SymbolTrashcanLayout) && ((SymbolLayout)v).isDropAccepted()) {
                                        if (v instanceof SymbolContainerLayout) {
                                            ((SymbolContainerLayout)v).toggleCollapse();
                                        } else if (v instanceof SymbolContainerBracketLayout) {
                                            ViewParent parent = v.getParent();

                                            if (parent != null && parent instanceof SymbolContainerLayout) {
                                                ((SymbolContainerLayout)parent).toggleCollapse();
                                            }
                                        }
                                    } else {
                                        if (v instanceof SymbolProjectLayout || (v instanceof SymbolContainerBracketLayout && !((SymbolLayout)v.getParent()).isDropAccepted())) {
                                            makeToast("Project\n\nA project object is....");
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
                            if (this.dragView != null && !(this.dragView instanceof SymbolTrashcanLayout)) {
                                handler.removeCallbacks(longPressHandler);

                                if (this.dragView instanceof SymbolContainerBracketLayout) {
                                    if (this.dragView.getParent() instanceof SymbolProjectLayout) {
                                        this.dragView = (ViewGroup)this.dragView.getParent();
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

    @Override
    public void addView(View child) {
        this.addView(child, -1);
    }

    @Override
    public void addView(View child, int index) {
        if (child.getParent() == null && !CanvasActivity.isLoadingSymbols && !ActionManager.isManagingAction && !isMovingSymbol) {
            if (child instanceof SymbolLayout && !(this instanceof SymbolEmptyLayout) && !(child instanceof SymbolEmptyLayout)) {
                ActionManager.getInstance().add(new AddAction(child, new ActionParameter(this, index)));
            }
        }

        super.addView(child, index);

        if (!(this instanceof SymbolEmptyLayout) && !(child instanceof SymbolEmptyLayout)) {
            if (this instanceof SymbolContainerLayout && child instanceof SymbolLayout) {
                this.addView(new SymbolEmptyLayout(getContext()), index + (index == -1 ? 0 : 1));
            }
        }
    }

    protected void addViewSuper(View view) {
        this.addViewSuper(view, -1);
    }

    protected void addViewSuper(View view, int index) {
        ViewGroup.LayoutParams params = view.getLayoutParams();

        if (params == null) {
            params = generateDefaultLayoutParams();
        }

        super.addView(view, index, params);
    }

    public int removeViews(View view) {
        ViewGroup parent = (ViewGroup)view.getParent();
        int currentIndex = parent.indexOfChild(view);

        SymbolLayout empty = (SymbolLayout)parent.getChildAt(currentIndex + 1);

        if (parent instanceof SymbolContainerLayout) {
            ((SymbolContainerLayout)parent).removeCollapsibleView(view);
            ((SymbolContainerLayout)parent).removeCollapsibleView(empty);
        } else {
            parent.removeView(view);
            parent.removeView(empty);
        }

        return currentIndex;
    }

    protected void moveView(View view, View targetView) {
        this.moveView(view, targetView, -1);
    }

    public void moveView(View view, View targetView, int targetIndex) {
        this.moveView(view, targetView, targetIndex, true);
    }

    public void moveView(View view, View targetView, int targetIndex, boolean record) {
        isMovingSymbol = true;

        if (view != targetView) {
            List<View> children = new ArrayList<>();
            this.fetchAllChildViews(children, view);

            if (!children.contains(targetView)) {
                if (record) {
                    ActionManager.getInstance().add(new MoveAction(view,
                        new ActionParameter((View) view.getParent(), ((ViewGroup) view.getParent()).indexOfChild(view)),
                        new ActionParameter(targetView, targetIndex))
                    );
                }

                ViewGroup parent = (ViewGroup)view.getParent();
                int currentIndex = this.removeViews(view);

                if (targetIndex != -1) {
                    /* Account for the two symbols we just removed, if they destination is the current parent */
                    targetIndex -= (targetView == parent && currentIndex < targetIndex ? 2 : 0);
                }

                ((ViewGroup)targetView).addView(view, targetIndex);
            } else {
                this.makeToast("Cannot move a parent object into a child object");
            }
        } else {
            this.makeToast("Cannot move a an object into itself");
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
                json.put("description", this.description == null ? "" : this.title);

                JSONArray childArray = new JSONArray();
                List<View> children = this.fetchChildViews(this);

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

    private void onHighlightEnter(View v) {
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

            this.onEnterSymbol();
        }

    }

    private void onHighlightExited(View v) {
        if (this.backgroundColor != 0) {
            v.setBackgroundColor(this.backgroundColor);
            this.backgroundColor = 0;
        }
    }
}
