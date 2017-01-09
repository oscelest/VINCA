package com.noxyspace.vinca;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.noxyspace.vinca.canvas.CanvasManager;


public class SymbolBar extends Fragment {

    private ImageView projectStart, projectEnd, activity, pause, iterationStart, iterationEnd, processStart, processEnd, decision, method;

    private CanvasManager canvasManger;


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

                        break;

                    case DragEvent.ACTION_DROP:
                        View view = (View)event.getLocalState();
                        canvasManger.addFigure(view);


                        view.setVisibility(View.VISIBLE);

                        //View figure = new View(getActivity());
                        //figure.setLayoutParams(v.getLayoutParams());
                    Log.d("Drag", "DROP");
                        //canvasManger.addFigure(figure);
                        //canvasManger.addFigure(v);

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





}
