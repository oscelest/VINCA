package com.noxyspace.vinca;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


public class SymbolBar extends Fragment {

    private ImageView projectOpen, projectClose, activity, pause, iteration, process, processCol, question, answer, method;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_symbol_bar, container, false);

//        projectClose = (ImageView) view.findViewById()

        return view;
    }
}
