package com.noxyspace.vinca.canvas;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noxyspace.vinca.R;
import com.noxyspace.vinca.canvas.symbols.specifications.activity.SymbolActivityLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.decision.SymbolDecisionLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.iteration.SymbolIterationLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.method.SymbolMethodLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.pause.SymbolPauseLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.process.SymbolProcessLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.project.SymbolProjectLayout;
import com.noxyspace.vinca.canvas.symbols.trashcan.SymbolTrashcanLayout;

public class SymbolBar extends Fragment {
    SymbolProjectLayout project;
    SymbolProcessLayout process;
    SymbolIterationLayout iteration;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.symbol_bar_fragment, container, false);

        view.addView(this.project = new SymbolProjectLayout(getActivity(), false));
        view.addView(this.process = new SymbolProcessLayout(getActivity(), false));
        view.addView(this.iteration = new SymbolIterationLayout(getActivity(), false));
        view.addView(new SymbolPauseLayout(getActivity(), false));
        view.addView(new SymbolDecisionLayout(getActivity(), false));
        view.addView(new SymbolMethodLayout(getActivity(), false));
        view.addView(new SymbolActivityLayout(getActivity(), false));

        view.addView(new SymbolBarSeparator(getActivity()));

        view.addView(new SymbolTrashcanLayout(getActivity()));

//        this.project.toggleCollapse();
//        this.process.toggleCollapse();
//        this.iteration.toggleCollapse();

        return view;
    }
}