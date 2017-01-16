package com.noxyspace.vinca.canvas.symbols.specifications.figures.process;

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

public class SymbolProcessLayout extends SymbolContainerLayout {
    public SymbolProcessLayout(Context context) {
        this(context, true);
    }

    public SymbolProcessLayout(Context context, boolean acceptsDrop) {
        this(context, acceptsDrop, false);
    }

    public SymbolProcessLayout(Context context, boolean acceptsDrop, boolean collapsed) {
        super(context, acceptsDrop, collapsed);

        this.createContainerSymbols(
            new SymbolContainerBracketLayout(context, R.drawable.process_start),
            new SymbolContainerBracketLayout(context, R.drawable.process_end),
            new SymbolProcess(context)
        );
    }

    @Override
    public boolean onDragDrop(View v, DragEvent event) {
        View view = (View)event.getLocalState();

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
                this.makeToast("Process objects only accept: [ Process, Iteration, Pause, Decision, Activity ]");
            }
        }

        return true;
    }
}