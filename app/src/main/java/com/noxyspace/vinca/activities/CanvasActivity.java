package com.noxyspace.vinca.activities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;
import com.noxyspace.vinca.R;

import io.fabric.sdk.android.Fabric;

import static android.graphics.Color.WHITE;
import static com.noxyspace.vinca.R.id.canvas;
import static com.noxyspace.vinca.R.id.imageView;
import static com.noxyspace.vinca.R.id.toolbar_canvas;

public class CanvasActivity extends AppCompatActivity{

    Toolbar toolbar_canvas_top;
    ImageView myImageView;
    Bitmap tempBitmap;

    Rect boundary;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        toolbar_canvas_top = (Toolbar) findViewById(R.id.toolbar_canvas_top);
        setSupportActionBar(toolbar_canvas_top);

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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_canvas_top, menu); //your file name
        return super.onCreateOptionsMenu(menu);
    }
}
