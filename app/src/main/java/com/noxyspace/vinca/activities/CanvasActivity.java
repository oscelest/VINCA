package com.noxyspace.vinca.activities;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.Response;
import com.noxyspace.vinca.R;
import com.noxyspace.vinca.canvas.symbols.timeline.SymbolTimeline;
import com.noxyspace.vinca.objects.ApplicationObject;
import com.noxyspace.vinca.objects.DirectoryObject;
import com.noxyspace.vinca.requests.directory.GetDirectoryObjectRequest;
import com.noxyspace.vinca.requests.directory.UpdateDirectoryObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class CanvasActivity extends AppCompatActivity {
    DirectoryObject directoryObject;

    EditText fileName;
    Toolbar toolbar_canvas_top;

    Context context;

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
        Log.d("Canvas ID", file_id);
        getDirectoryObject(file_id);

        LinearLayout canvas = (LinearLayout)findViewById(R.id.canvas);
        canvas.addView(new SymbolTimeline(this));

        // Change update the name of the file, when focus from EditText is moved
        fileName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    directoryObject.setName(fileName.getText().toString());
                    ApplicationObject.getInstance().addRequest(new UpdateDirectoryObjectRequest(directoryObject.getId(), directoryObject.getName(), "", "",
                            new Response.Listener<JSONObject>() {
                                public void onResponse(JSONObject response) {
                                    try {
                                        if (response.getBoolean("success")) {

                                            Log.d("UpdateCanvasNameSuccess", response.toString());
                                            JSONObject content = response.getJSONObject("content");
                                            directoryObject.setName(content.getString("name"));
                                            Log.d("Updated?", directoryObject.getName());

                                        } else {
                                            Log.d("UpdateCanvasNameFailure", response.toString());
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }));
                }
            }
        });
//
//        fileName.setOnEditorActionListener(
//                new EditText.OnEditorActionListener() {
//                    DirectoryObject current_file = ApplicationObject.getInstance().getCurrentFile();
//                    @Override
//                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                        if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
//                            if (!event.isShiftPressed()) {
////                                ApplicationObject.getInstance().addRequest(new UpdateDirectoryObjectRequest(current_file,
////                                        new Response.Listener<JSONObject>() {
////                                            public void onResponse(JSONObject response) {
////                                                try {
////                                                    if (response.getBoolean("success")) {
////
////                                                        Log.d("UpdateCanvasNameSuccess", response.toString());
////                                                        JSONObject content = response.getJSONObject("content");
////                                                        current_file.setName(content.getString("name"));
////
////                                                    } else {
////                                                        Log.d("UpdateCanvasNameFailure", response.toString());
////                                                    }
////                                                } catch (JSONException e) {
////                                                    e.printStackTrace();
////                                                }
////                                            }
////                                        }));
//                                return true;
//                            }
//                        }
//                        return false;
//                    }
//                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_canvas_top, menu); //your file name
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getDirectoryObject(String object_id) {
        Log.d("DirObjetId", object_id);

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
                            } else {
                                Log.d("GetDirectoryFailure", response.toString());
                                //Toast.makeText(getApplicationContext(), "Server error, try again later.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                })
        );
    }

    // Removes focus when clicked outside EditText
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
}