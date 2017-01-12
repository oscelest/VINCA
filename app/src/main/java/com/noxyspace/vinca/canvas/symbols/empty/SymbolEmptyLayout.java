package com.noxyspace.vinca.canvas.symbols.empty;

import android.content.Context;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.noxyspace.vinca.canvas.SymbolLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.activity.SymbolActivity;
import com.noxyspace.vinca.canvas.symbols.specifications.activity.SymbolActivityLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.decision.SymbolDecisionLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.iteration.SymbolIterationLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.pause.SymbolPauseLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.process.SymbolProcessLayout;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class SymbolEmptyLayout extends SymbolLayout {

    private SymbolEmpty empty;

    public SymbolEmptyLayout(Context context) {
        this(context, true);
    }

    public SymbolEmptyLayout(Context context, boolean acceptsDrop) {
        super(context, acceptsDrop);

        this.addView(this.empty = new SymbolEmpty(context, 0));
    }

    @Override
    protected boolean onDragDrop(View v, DragEvent event) {
        View view = (View)event.getLocalState();

        // view = drag
        // v = target

        ViewGroup parent = (ViewGroup)this.getParent();

        int index = parent.indexOfChild(this);

        if ((view instanceof SymbolLayout) && ((SymbolLayout)view).isDropAccepted()) {
            this.moveView(view, v);
        } else {
            if (view instanceof SymbolProcessLayout) {
                parent.addView(new SymbolProcessLayout(getContext()), index + 1);
                parent.addView(new SymbolEmptyLayout(getContext()), index + 2);
            } else if (view instanceof SymbolIterationLayout) {
                parent.addView(new SymbolIterationLayout(getContext()), index + 1);
                parent.addView(new SymbolEmptyLayout(getContext()), index + 2);
            } else if (view instanceof SymbolPauseLayout) {
                parent.addView(new SymbolPauseLayout(getContext()), index + 1);
                parent.addView(new SymbolEmptyLayout(getContext()), index + 2);
            } else if (view instanceof SymbolDecisionLayout) {
                parent.addView(new SymbolDecisionLayout(getContext()), index + 1);
                parent.addView(new SymbolEmptyLayout(getContext()), index + 2);
            } else if (view instanceof SymbolActivityLayout) {
                parent.addView(new SymbolActivityLayout(getContext()), index + 1);
                parent.addView(new SymbolEmptyLayout(getContext()), index + 2);
            }
        }

        return true;
    }

    @Override
    protected void onEnterSymbol() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)this.getLayoutParams();
        params.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
        setLayoutParams(params);
    }

    @Override
    protected void onExitSymbol() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)this.getLayoutParams();
        params.width = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());
        setLayoutParams(params);
    }
}
