package com.noxyspace.vinca.canvas.symbols.bar.trashcan;

import android.content.Context;
import android.graphics.Color;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;

import com.noxyspace.vinca.canvas.actions.ActionManager;
import com.noxyspace.vinca.canvas.actions.ActionParameter;
import com.noxyspace.vinca.canvas.actions.derivatives.RemoveAction;
import com.noxyspace.vinca.canvas.symbols.SymbolLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.SymbolContainerBracketLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.SymbolContainerLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.figures.activity.SymbolActivityLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.timeline.TimelineLayout;
import com.noxyspace.vinca.objects.ApplicationObject;

public class SymbolTrashcanLayout extends SymbolLayout {
    public static final int HIGHLIGHT_COLOR = 0xFFFF4C4C;
    public static final int HIGHLIGHT_COLOR_1 = Color.BLACK;

    private SymbolTrashcan trashcan;

    public SymbolTrashcanLayout(Context context) {
        this(context, true);
    }

    public SymbolTrashcanLayout(Context context, boolean acceptsDrop) {
        super(context, acceptsDrop);

        this.addView(this.trashcan = new SymbolTrashcan(context));
    }

    @Override
    protected boolean onDragDrop(View v, DragEvent event) {
        View view = (View)event.getLocalState();

        if ((view instanceof SymbolLayout) && ((SymbolLayout)view).isDropAccepted()) {
            if (view instanceof SymbolActivityLayout && ((SymbolActivityLayout)view).hasMethod()) {
                ((SymbolActivityLayout)view).setMethod(false);
            } else {
                if (view instanceof TimelineLayout) {
                    ActionManager.getInstance().add(new RemoveAction(view, new ActionParameter((ViewGroup)view.getParent(), ((ViewGroup)view.getParent()).indexOfChild(view))));
                    ((ViewGroup)view.getParent()).removeView(view);
                } else if (view instanceof SymbolContainerBracketLayout) {
                    this.makeToast("Cannot delete container brackets");
                } else if (view.getParent() instanceof SymbolLayout) {
                    ActionManager.getInstance().add(new RemoveAction(view, new ActionParameter((ViewGroup)view.getParent(), ((ViewGroup)view.getParent()).indexOfChild(view))));
                    this.removeViews(view);
                } else {
                    this.makeToast("Cannot delete this symbol");
                }
            }
        } else {
            this.makeToast("Cannot remove symbolbar objects");
        }

        return true;
    }
}
