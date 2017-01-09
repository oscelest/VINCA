package com.noxyspace.vinca.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.noxyspace.vinca.R;
import com.noxyspace.vinca.SymbolBar;
import com.noxyspace.vinca.canvas.CanvasManager;
import com.noxyspace.vinca.objects.ApplicationObject;
import com.noxyspace.vinca.objects.DirectoryObject;
import com.noxyspace.vinca.requests.directory.GetDirectoryObjectRequest;
import com.noxyspace.vinca.requests.directory.UpdateDirectoryObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class CanvasActivity extends AppCompatActivity {

    Toolbar toolbar_canvas_top;
    ImageView myImageView;
    EditText fileName;
    Bitmap tempBitmap;
    SymbolBar symbolbar;
    DirectoryObject directoryObject;
    //HorizontalScrollView figureList;
    LinearLayout figureList;
    CanvasManager canvasManager;


    Rect boundary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.canvas_activity);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        toolbar_canvas_top = (Toolbar) findViewById(R.id.toolbar_canvas_top);
        setSupportActionBar(toolbar_canvas_top);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        fileName = (EditText) findViewById(R.id.text_canvas_name);

        int file_id = getIntent().getIntExtra("FILE_ID", -1);
        getDirectoryObject(file_id);
        ImageView project = (ImageView) findViewById(R.id.project_start);

        figureList = (LinearLayout) findViewById(R.id.figure_list);
        figureList.setBackgroundColor(Color.BLACK);
        //figureList.invalidate();

        canvasManager = new CanvasManager(this, figureList);


        // Change update the name of the file, when focus from EditText is moved
        fileName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    directoryObject.setName(fileName.getText().toString());
                    ApplicationObject.getInstance().addRequest(new UpdateDirectoryObjectRequest(directoryObject.getId(), directoryObject.getName(), directoryObject.getOwnerId(), directoryObject.getParentId(),
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


        fileName.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    DirectoryObject current_file = ApplicationObject.getInstance().getCurrentFile();
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            if (!event.isShiftPressed()) {
//                                ApplicationObject.getInstance().addRequest(new UpdateDirectoryObjectRequest(current_file,
//                                        new Response.Listener<JSONObject>() {
//                                            public void onResponse(JSONObject response) {
//                                                try {
//                                                    if (response.getBoolean("success")) {
//
//                                                        Log.d("UpdateCanvasNameSuccess", response.toString());
//                                                        JSONObject content = response.getJSONObject("content");
//                                                        current_file.setName(content.getString("name"));
//
//                                                    } else {
//                                                        Log.d("UpdateCanvasNameFailure", response.toString());
//                                                    }
//                                                } catch (JSONException e) {
//                                                    e.printStackTrace();
//                                                }
//                                            }
//                                        }));
                                return true;
                            }
                        }
                        return false;
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_canvas_top, menu); //your file name
        return super.onCreateOptionsMenu(menu);
    }

    private void getDirectoryObject(int object_id) {
        ApplicationObject.getInstance().addRequest(new GetDirectoryObjectRequest(object_id,
                new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")) {
                                Log.d("CreateDirectorySuccess", response.toString());

                                JSONObject content = response.getJSONObject("content");

                                directoryObject = new DirectoryObject(
                                        content.getInt("id"),
                                        content.getInt("owner_id"),
                                        content.getString("owner_first_name"),
                                        content.getString("owner_last_name"),
                                        content.getInt("parent_id"),
                                        content.getString("name"),
                                        content.getBoolean("folder"),
                                        content.getInt("time_created"),
                                        content.getInt("time_updated"),
                                        content.getInt("time_deleted"));

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
    public LinearLayout getFigureList(){
        return this.figureList;
    }


}
