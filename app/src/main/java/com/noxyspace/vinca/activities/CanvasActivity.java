package com.noxyspace.vinca.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.crashlytics.android.Crashlytics;
import com.noxyspace.vinca.R;

import io.fabric.sdk.android.Fabric;

import static android.graphics.Color.WHITE;
import static com.noxyspace.vinca.R.id.toolbar_canvas;

public class CanvasActivity extends AppCompatActivity{

    Toolbar toolbar_canvas_top;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);

        toolbar_canvas_top = (Toolbar) findViewById(R.id.toolbar_canvas_top);
        setSupportActionBar(toolbar_canvas_top);





        final View canvas = findViewById(R.id.canvas);
        canvas.bringToFront();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_canvas_top, menu); //your file name
        return super.onCreateOptionsMenu(menu);
    }
}