package com.noxyspace.vinca;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

import static android.graphics.Color.WHITE;

public class CanvasActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_canvas);

        Toolbar toolbar_canvas = (Toolbar) findViewById(R.id.toolbar_canvas);
        toolbar_canvas.setTitle("Filnavn");
        toolbar_canvas.setTitleTextColor(WHITE);
        setSupportActionBar(toolbar_canvas);

        Toolbar toolbar_icons = (Toolbar) findViewById(R.id.toolbar_icons);
        setSupportActionBar(toolbar_icons);

        final View canvas = (View) findViewById(R.id.canvas);
        canvas.bringToFront();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_icons, menu); //your file name
        return super.onCreateOptionsMenu(menu);
    }
}
