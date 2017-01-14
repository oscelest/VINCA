package com.noxyspace.vinca.canvas.symbols.specifications.figures.project;

import android.content.Context;
import android.view.DragEvent;
import android.view.View;

import com.noxyspace.vinca.R;
import com.noxyspace.vinca.canvas.symbols.SymbolLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.containers.bracket.SymbolContainerBracketLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.containers.SymbolContainerLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.figures.activity.SymbolActivityLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.figures.decision.SymbolDecisionLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.figures.iteration.SymbolIterationLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.figures.pause.SymbolPauseLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.figures.process.SymbolProcessLayout;

public class SymbolProjectLayout extends SymbolContainerLayout {
    public SymbolProjectLayout(Context context) {
        this(context, true);
    }

    public SymbolProjectLayout(Context context, boolean acceptsDrop) {
        super(context, acceptsDrop);

        this.createContainerSymbols(
            new SymbolContainerBracketLayout(context, R.drawable.project_start, false),
            new SymbolContainerBracketLayout(context, R.drawable.project_end, false),
            null
        );
    }

    @Override
    protected boolean onDragDrop(View v, DragEvent event) {
        View view = (View) event.getLocalState();

        if (view != v && !(view instanceof SymbolContainerBracketLayout && view.getParent() == v)) {
            if (view instanceof SymbolProcessLayout || view instanceof SymbolIterationLayout || view instanceof SymbolPauseLayout || view instanceof SymbolDecisionLayout || view instanceof SymbolActivityLayout) {
                if ((view instanceof SymbolLayout) && ((SymbolLayout) view).isDropAccepted()) {
                    ((SymbolLayout)view).moveView(v);
                } else {
                    if (view instanceof SymbolProcessLayout) {
                        this.addView(new SymbolProcessLayout(getContext()));
                    } else if (view instanceof SymbolIterationLayout) {
                        this.addView(new SymbolIterationLayout(getContext()));
                    } else if (view instanceof SymbolPauseLayout) {
                        this.addView(new SymbolPauseLayout(getContext()));
                    } else if (view instanceof SymbolDecisionLayout) {
                        this.addView(new SymbolDecisionLayout(getContext()));
                    } else if (view instanceof SymbolActivityLayout) {
                        this.addView(new SymbolActivityLayout(getContext()));
                    }
                }
            } else {
                this.makeToast("Project objects only accept: [ Process, Iteration, Pause, Decision, Activity ]");
            }
        }

        return true;
    }
}
