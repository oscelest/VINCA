package com.noxyspace.vinca.canvas.symbols.bar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noxyspace.vinca.R;
import com.noxyspace.vinca.canvas.symbols.specifications.figures.SymbolActivityLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.figures.SymbolDecisionLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.figures.SymbolIterationLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.figures.SymbolMethodLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.figures.SymbolPauseLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.figures.SymbolProcessLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.figures.SymbolProjectLayout;
import com.noxyspace.vinca.canvas.symbols.bar.trashcan.SymbolTrashcanLayout;

public class SymbolBar extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.symbol_bar_fragment, container, false);

        view.addView(new SymbolProjectLayout(getActivity(), false));
        view.addView(new SymbolProcessLayout(getActivity(), false, true));
        view.addView(new SymbolIterationLayout(getActivity(), false, true));
        view.addView(new SymbolPauseLayout(getActivity(), false));
        view.addView(new SymbolDecisionLayout(getActivity(), false));
        view.addView(new SymbolActivityLayout(getActivity(), false));
        view.addView(new SymbolMethodLayout(getActivity(), false));

        view.addView(new SymbolBarSeparator(getActivity()));

        view.addView(new SymbolTrashcanLayout(getActivity()));

        return view;
    }
}