package com.noxyspace.vinca;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.noxyspace.vinca.activities.CanvasActivity;

import static android.R.attr.bitmap;
import static com.noxyspace.vinca.R.id.canvas;


public class SymbolBar extends Fragment {

    private ImageView projectStart, projectEnd, activity, pause, iterationStart, iterationEnd, processStart, processEnd, decision, method;
    private Bitmap oldBitmap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.symbol_bar_fragment, container, false);

        projectStart = (ImageView) view.findViewById(R.id.project_start);
        projectEnd = (ImageView) view.findViewById(R.id.project_end);
        activity = (ImageView) view.findViewById(R.id.activity);
        pause = (ImageView) view.findViewById(R.id.pause);
        iterationStart = (ImageView) view.findViewById(R.id.iteration_start);
        iterationEnd = (ImageView) view.findViewById(R.id.iteration_end);
        processStart = (ImageView) view.findViewById(R.id.process_start);
        processEnd = (ImageView) view.findViewById(R.id.process_end);
        decision  = (ImageView) view.findViewById(R.id.decision);
        method = (ImageView) view.findViewById(R.id.method);

        setListeners(projectStart);
        setListeners(projectEnd);
        setListeners(activity);
        setListeners(decision);
        setListeners(processStart);
        setListeners(processEnd);
        setListeners(pause);
        setListeners(iterationStart);
        setListeners(iterationEnd);
        setListeners(method);

        return view;
    }//End of onCreate()

    public void setListeners(ImageView view){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipData.Item item = new ClipData.Item((CharSequence)v.getTag());
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};

                ClipData dragData = new ClipData(v.getTag().toString(),mimeTypes, item);
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(v);

                v.startDrag(dragData,myShadow,null,0);
            }
        });

        view.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch(event.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                        // Do nothing
                        break;

                    case DragEvent.ACTION_DRAG_ENTERED:

                        break;

                    case DragEvent.ACTION_DRAG_EXITED :

                        break;

                    case DragEvent.ACTION_DRAG_LOCATION  :

                        break;

                    case DragEvent.ACTION_DRAG_ENDED   :
                        drawToCanvas(((CanvasActivity) getActivity()).getBitmap(), imageViewToBitmap((ImageView) v));
                        break;

                    case DragEvent.ACTION_DROP:
                    Log.d("Drag", "DROP");
                        break;
                    default: break;
                }
                return true;
            }
        });

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ClipData data = ClipData.newPlainText("", "");
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);

                    v.startDrag(data, shadowBuilder, v, 0);
                    v.setVisibility(View.VISIBLE);
                    return true;
                } else {
                    return false;
                }
            }
        });


    }//End of setlisteners

    public Bitmap imageViewToBitmap(ImageView view){
        view.buildDrawingCache();
        return view.getDrawingCache();
    }

    public Bitmap drawToCanvas(Bitmap oldBitmap, Bitmap overlayBitmap){
        Log.d("bitmap width", "" + oldBitmap.getWidth());
        Log.d("obitmap width", "" + overlayBitmap.getWidth());

        Bitmap tempBitmap = Bitmap.createBitmap(oldBitmap.getWidth(), oldBitmap.getHeight(), oldBitmap.getConfig());
        Canvas tempCanvas = new Canvas(tempBitmap);
        tempCanvas.drawBitmap(oldBitmap, 0, 0, null);
        tempCanvas.drawBitmap(overlayBitmap, 0, 0, null);
        return tempBitmap;
    }


}
