package com.noxyspace.vinca.canvas.symbols.specifications.project;

import android.content.Context;
import android.view.DragEvent;
import android.view.View;
import android.widget.Toast;

import com.noxyspace.vinca.R;
import com.noxyspace.vinca.canvas.SymbolLayout;
import com.noxyspace.vinca.canvas.symbols.SymbolContainerBracket;
import com.noxyspace.vinca.canvas.symbols.SymbolContainerLayout;
import com.noxyspace.vinca.canvas.symbols.activity.SymbolActivityLayout;
import com.noxyspace.vinca.canvas.symbols.decision.SymbolDecisionLayout;
import com.noxyspace.vinca.canvas.symbols.empty.SymbolEmptyLayout;
import com.noxyspace.vinca.canvas.symbols.iteration.SymbolIterationLayout;
import com.noxyspace.vinca.canvas.symbols.pause.SymbolPauseLayout;
import com.noxyspace.vinca.canvas.symbols.process.SymbolProcessLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.activity.SymbolActivityLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.decision.SymbolDecisionLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.iteration.SymbolIterationLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.pause.SymbolPauseLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.process.SymbolProcessLayout;

public class SymbolProjectLayout extends SymbolContainerLayout {
    public SymbolProjectLayout(Context context) {
        this(context, true);
    }

    public SymbolProjectLayout(Context context, boolean acceptsDrop) {
        super(context, acceptsDrop);

        this.createContainerSymbols(
            new SymbolContainerBracket(context, R.drawable.project_start),
            new SymbolContainerBracket(context, R.drawable.project_end),
            null
        );
    }

    @Override
    protected boolean onDragDrop(View v, DragEvent event) {
        View view = (View) event.getLocalState();

        if (view instanceof SymbolProcessLayout || view instanceof SymbolIterationLayout || view instanceof SymbolPauseLayout ||
                view instanceof SymbolDecisionLayout || view instanceof SymbolActivityLayout) {
            if ((view instanceof SymbolLayout) && ((SymbolLayout)view).isDropAccepted()) {
                this.moveView(view, v);
            } else {
                if (view instanceof SymbolProcessLayout) {
                    this.addView(new SymbolProcessLayout(getContext()));
                    this.addView(new SymbolEmptyLayout(getContext()));
                } else if (view instanceof SymbolIterationLayout) {
                    this.addView(new SymbolIterationLayout(getContext()));
                    this.addView(new SymbolEmptyLayout(getContext()));
                } else if (view instanceof SymbolPauseLayout) {
                    this.addView(new SymbolPauseLayout(getContext()));
                    this.addView(new SymbolEmptyLayout(getContext()));
                } else if (view instanceof SymbolDecisionLayout) {
                    this.addView(new SymbolDecisionLayout(getContext()));
                    this.addView(new SymbolEmptyLayout(getContext()));
                } else if (view instanceof SymbolActivityLayout) {
                    this.addView(new SymbolActivityLayout(getContext()));
                    this.addView(new SymbolEmptyLayout(getContext()));
                }
            }
        } else {
            Toast.makeText(getContext(), "Project objects only accept: [ Process, Iteration, Pause, Decision, Activity ]", Toast.LENGTH_SHORT).show();
        }

        return true;
    }
}
