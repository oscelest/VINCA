package com.noxyspace.vinca.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
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
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.noxyspace.vinca.R;
import com.noxyspace.vinca.SymbolBar;
import com.noxyspace.vinca.activities.tabs.OwnTab;
import com.noxyspace.vinca.objects.ApplicationObject;
import com.noxyspace.vinca.objects.DirectoryObject;
import com.noxyspace.vinca.objects.UserObject;
import com.noxyspace.vinca.requests.directory.CreateDirectoryObjectRequest;
import com.noxyspace.vinca.requests.directory.GetDirectoryContentRequest;
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


        myImageView = (ImageView) findViewById(R.id.canvas);
        myImageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                myImageView.getViewTreeObserver().removeOnPreDrawListener(this);
                int height = myImageView.getHeight();
                int width = myImageView.getWidth();

                //Create a new image bitmap and attach a brand new canvas to it
                tempBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                tempBitmap.eraseColor(Color.WHITE);

                //Attach the canvas to the ImageView
                myImageView.setImageBitmap(tempBitmap);

                //Draw the image bitmap into the canvas
                Canvas tempCanvas = new Canvas(tempBitmap);

                tempCanvas.drawBitmap(tempBitmap, 0, 0, null);
                return false;
            }
        });

        ((EditText) findViewById(R.id.text_canvas_name)).setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    DirectoryObject current_file = ApplicationObject.getInstance().getCurrentFile();
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE || event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                            if (!event.isShiftPressed()) {
                                ApplicationObject.getInstance().addRequest(new UpdateDirectoryObjectRequest(current_file,
                                        new Response.Listener<JSONObject>() {
                                            public void onResponse(JSONObject response) {
                                                try {
                                                    if (response.getBoolean("success")) {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_canvas_top, menu); //your file name
        return super.onCreateOptionsMenu(menu);
    }

    public Bitmap getBitmap(){
        return tempBitmap;
    }





}
