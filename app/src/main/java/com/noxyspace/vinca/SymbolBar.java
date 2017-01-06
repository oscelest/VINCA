package com.noxyspace.vinca;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class SymbolBar extends Fragment {

    private ImageView projectStart, projectEnd, activity, pause, iterationStart, iterationEnd, processStart, processEnd, decision, method;


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

        projectStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startDrag();
            }
        });
        projectEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        iterationStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        iterationEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        processStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        processEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        decision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        method.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




        return view;
    }
}
