package com.noxyspace.vinca.canvas.symbols.specifications.empty;

import android.content.Context;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.noxyspace.vinca.canvas.symbols.SymbolLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.figures.activity.SymbolActivityLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.figures.decision.SymbolDecisionLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.figures.iteration.SymbolIterationLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.figures.pause.SymbolPauseLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.figures.process.SymbolProcessLayout;

public class SymbolEmptyLayout extends SymbolLayout {
    public static final int WIDTH_COLLAPSED = 10;
    public static final int WIDTH_EXPANDED = 50;

    public SymbolEmptyLayout(Context context) {
        super(context, true);
    }

    @Override
    protected boolean onDragDrop(View v, DragEvent event) {
        View view = (View)event.getLocalState();
        ViewGroup parent = (ViewGroup)this.getParent();

        if (view instanceof SymbolProcessLayout || view instanceof SymbolIterationLayout || view instanceof SymbolPauseLayout || view instanceof SymbolDecisionLayout || view instanceof SymbolActivityLayout) {
            int index = parent.indexOfChild(this);

            if ((view instanceof SymbolLayout) && ((SymbolLayout)view).isDropAccepted()) {
                this.moveView(view, parent, index + 1);
            } else {
                if (view instanceof SymbolProcessLayout) {
                    parent.addView(new SymbolProcessLayout(getContext()), index + 1);
                } else if (view instanceof SymbolIterationLayout) {
                    parent.addView(new SymbolIterationLayout(getContext()), index + 1);
                } else if (view instanceof SymbolPauseLayout) {
                    parent.addView(new SymbolPauseLayout(getContext()), index + 1);
                } else if (view instanceof SymbolDecisionLayout) {
                    parent.addView(new SymbolDecisionLayout(getContext()), index + 1);
                } else if (view instanceof SymbolActivityLayout) {
                    parent.addView(new SymbolActivityLayout(getContext()), index + 1);
                }
            }
        } else {
            this.makeToast("Empty padding objects only accept: [ Process, Iteration, Pause, Decision, Activity ]");
        }

        return true;
    }

    @Override
    protected void onEnterSymbol() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)this.getLayoutParams();
        params.width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, WIDTH_EXPANDED, getResources().getDisplayMetrics());
        setLayoutParams(params);
    }

    @Override
    protected void onExitSymbol() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)this.getLayoutParams();
        params.width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, WIDTH_COLLAPSED, getResources().getDisplayMetrics());
        setLayoutParams(params);
    }
}
