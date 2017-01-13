package com.noxyspace.vinca.activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.noxyspace.vinca.R;
import com.noxyspace.vinca.canvas.symbols.specifications.figures.project.SymbolProjectLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.timeline.TimelineLayout;
import com.noxyspace.vinca.canvas.actions.ActionManager;
import com.noxyspace.vinca.objects.ApplicationObject;
import com.noxyspace.vinca.objects.DirectoryObject;
import com.noxyspace.vinca.requests.directory.GetDirectoryObjectRequest;
import com.noxyspace.vinca.requests.directory.UpdateDirectoryObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CanvasActivity extends AppCompatActivity implements View.OnDragListener {
    private int backgroundColor;

    DirectoryObject directoryObject;

    EditText fileName;
    Toolbar toolbar_canvas_top;

    Context context;
    LinearLayout canvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.canvas_activity);

        this.context = this;

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        toolbar_canvas_top = (Toolbar) findViewById(R.id.toolbar_canvas_top);
        setSupportActionBar(toolbar_canvas_top);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        fileName = (EditText) findViewById(R.id.text_canvas_name);

        String file_id = getIntent().getStringExtra("FILE_ID");
        getDirectoryObject(file_id);

        this.canvas = (LinearLayout) findViewById(R.id.canvas);
        this.canvas.setOnDragListener(this);
        this.canvas.setBackgroundColor(Color.WHITE);

        // Change update the name of the file, when focus from EditText is moved
        fileName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    directoryObject.setName(fileName.getText().toString());
                    updateDirectoryObject(null);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_canvas_top, menu); //your file name
        return super.onCreateOptionsMenu(menu);
    }

    Toast toast = null;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

            case R.id.undo:
                if(ActionManager.getInstance().canUndo()){
                    ActionManager.getInstance().undo();
                }else{
                    makeToast("Nothing to undo");
                }
                return true;

            case R.id.redo:
                if(ActionManager.getInstance().canRedo()){
                    ActionManager.getInstance().redo();
                }else {
                    makeToast("Nothing to redo");
                }
                return true;

            case R.id.save:
                this.updateDirectoryObject(toJsonObject().toString());
                this.makeToast("Saving canvas data...");
                return true;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getDirectoryObject(String object_id) {
        ApplicationObject.getInstance().addRequest(new GetDirectoryObjectRequest(object_id,
            new Response.Listener<JSONObject>() {
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getBoolean("success")) {
                            Log.d("GetDirectorySuccess", response.toString());

                            JSONObject content = response.getJSONObject("content");
                            JSONObject owner = content.getJSONObject("owner");

                            directoryObject = new DirectoryObject(
                                content.getString("_id"),
                                owner.getString("_id"),
                                owner.getString("first_name"),
                                owner.getString("last_name"),
                                content.isNull("parent") ? null : content.getJSONObject("parent").getString("_id"),
                                content.getString("name"),
                                content.getBoolean("folder"),
                                content.getInt("time_created"),
                                content.getInt("time_updated"),
                                content.getInt("time_deleted")
                            );

                            fileName.setText(directoryObject.getName());
                            fromJsonObject(content.getString("data"));
                        } else {
                            Log.d("GetDirectoryFailure", response.toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        ));
    }

    private void updateDirectoryObject(String data) {
        ApplicationObject.getInstance().addRequest(new UpdateDirectoryObjectRequest(directoryObject.getId(), directoryObject.getName(), data, null, null,
            new Response.Listener<JSONObject>() {
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getBoolean("success")) {
                            JSONObject content = response.getJSONObject("content");
                            directoryObject.setName(content.getString("name"));
                            makeToast("Saved");
                        } else {
                            Log.d("UpdateCanvasNameFailure", response.toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            })
        );
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }

        return super.dispatchTouchEvent(event);
    }

    public JSONObject toJsonObject() {
        JSONObject json = new JSONObject();

        try {
            json.put("type", "canvas");

            JSONArray timelineArray = new JSONArray();

            for (int i = 0, count = this.canvas.getChildCount(); i < count; i++) {
                View child = this.canvas.getChildAt(i);

                if (child instanceof TimelineLayout) {
                    timelineArray.put(((TimelineLayout)child).toJsonObject());
                } else {
                    System.out.println("Unexpected object in canvas: " + child.getClass().getSimpleName());
                }
            }

            json.put("children", timelineArray);
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }

        return json;
    }

    public void fromJsonObject(String json) {
        try {
            this.fromJsonObject(new JSONObject(json));
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }
    }

    public void fromJsonObject(JSONObject json) {
        try {
            String jsonType = json.getString("type");

            if (jsonType.equals("canvas")) {
                JSONArray timelineArray = json.getJSONArray("children");

                for (int i = 0; i < timelineArray.length(); i++) {
                    JSONObject child = timelineArray.getJSONObject(i);
                    String childType = child.getString("type");

                    if (childType.equals("timeline")) {

                        TimelineLayout timeline = new TimelineLayout(this.context);
                        timeline.fromJsonObject(child);

                        this.canvas.addView(timeline);
                    } else {
                        System.out.println("Unexpected object in canvas: " + jsonType);
                    }
                }
            } else {
                System.out.println("Unexpected canvas object: " + jsonType);
            }
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }
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
                return this.onDragEntered(v, event);

            case DragEvent.ACTION_DRAG_EXITED:
                return this.onDragExited(v, event);

            default:
                break;
        }

        return false;
    }

    private boolean onDragStarted(View v, DragEvent event) {
        return true;
    }

    protected boolean onDragDrop(View v, DragEvent event) {
        View view = (View)event.getLocalState();

        if (view instanceof TimelineLayout) {
            ((ViewGroup)view.getParent()).removeView(view);
            ((ViewGroup)v).addView(view);
        } else if (view instanceof SymbolProjectLayout) {
            if (((SymbolProjectLayout)view).isDropAccepted()) {
                /* Move object? */
            } else {
                TimelineLayout timeline = new TimelineLayout(this);
                timeline.addView(new SymbolProjectLayout(this));
                this.canvas.addView(timeline);
            }
        } else {
            this.makeToast("Canvas objects only accept symbols of type: [ Timeline, Project ]");
        }

        return true;
    }

    private boolean onDragEnded(View v, DragEvent event) {
        if (this.backgroundColor != 0) {
            v.setBackgroundColor(this.backgroundColor);
            this.backgroundColor = 0;
        }

        return true;
    }

    private boolean onDragEntered(View v, DragEvent event) {
        Drawable background = v.getBackground();

        if (background == null) {
            background = ((View) v.getParent()).getBackground();
        }

        if (background != null && background instanceof ColorDrawable) {
            this.backgroundColor = ((ColorDrawable) background).getColor();
            v.setBackgroundColor(TimelineLayout.HIGHLIGHT_COLOR);
        }

        return true;
    }

    private boolean onDragExited(View v, DragEvent event) {
        if (this.backgroundColor != 0) {
            v.setBackgroundColor(this.backgroundColor);
            this.backgroundColor = 0;
        }

        return true;
    }

    private void makeToast(String message) {
        ApplicationObject.getInstance().makeToast(this, message);
    }
}
