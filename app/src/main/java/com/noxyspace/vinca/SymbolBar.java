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
import com.noxyspace.vinca.symbols.*;


public class SymbolBar extends Fragment {

    //private ImageView projectStart, projectEnd, activity, pause, iterationStart, iterationEnd, processStart, processEnd, decision, method;
    private Symbol project, process, iterate, pause, decision, method, activity, trashcan;

    private CanvasManager canvasManger;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.symbol_bar_fragment, container, false);

        project = new ProjectSymbol(getActivity(), view);
        process = new ProcessSymbol(getActivity(), view);
        iterate = new IterationSymbol(getActivity(), view);
        pause = new PauseSymbol(getActivity(), view);
        decision = new DecisionSymbol(getActivity(), view);
        method = new MethodSymbol(getActivity(), view);
        activity = new ActivitySymbol(getActivity(), view);

        new BarSeperator(getActivity(), view);
        trashcan = new TrashcanSymbol(getActivity(), view);


        setListeners(project);
        setListeners(activity);
        setListeners(decision);
        setListeners(process);
        setListeners(pause);
        setListeners(iterate);
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
                        View view = (View)event.getLocalState(); // Symbol from symbolBar
                        System.out.println("SymbolBar DragDrop");
                        //canvasManger.addFigure(view);


                        view.setVisibility(View.VISIBLE);

                        //View figure = new View(getActivity());
                        //figure.setLayoutParams(v.getLayoutParams());
                        Log.d("Drag", "DROP");
                        //canvasManger.addFigure(figure);
                        //canvasManger.addFigure(v);

                        break;

                    default:
                        break;
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
