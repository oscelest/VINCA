package com.noxyspace.vinca.canvas.symbols.bar;

import android.content.Context;
import android.graphics.Color;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;

import com.noxyspace.vinca.R;
import com.noxyspace.vinca.canvas.actions.ActionManager;
import com.noxyspace.vinca.canvas.actions.ActionParameter;
import com.noxyspace.vinca.canvas.actions.derivatives.RemoveAction;
import com.noxyspace.vinca.canvas.symbols.SymbolLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.containers.SymbolContainerLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.containers.collapsed.SymbolContainerCollapsed;
import com.noxyspace.vinca.canvas.symbols.specifications.containers.expanded.SymbolContainerExpandedLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.containers.bracket.SymbolContainerBracketLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.figures.SymbolActivityLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.figures.SymbolProjectLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.timeline.TimelineLayout;

public class SymbolTrashcanLayout extends SymbolLayout {
    public static final int HIGHLIGHT_COLOR = 0xFFFF4C4C;
    public static final int HIGHLIGHT_COLOR_1 = Color.BLACK;

    private SymbolContainerCollapsed trashcan;

    public SymbolTrashcanLayout(Context context) {
        this(context, true);
    }

    public SymbolTrashcanLayout(Context context, boolean acceptsDrop) {
        super(context, acceptsDrop);

        this.addView(this.trashcan = new SymbolContainerCollapsed(context, android.R.drawable.ic_menu_delete));
        this.trashcan.setColorFilter(Color.BLACK);
    }

    @Override
    public boolean onDragDrop(View v, DragEvent event) {
        View view = (View)event.getLocalState();

        if ((view instanceof SymbolLayout) && ((SymbolLayout)view).isDropAccepted()) {
            if (view instanceof SymbolActivityLayout && ((SymbolActivityLayout)view).hasMethod()) {
                ((SymbolActivityLayout)view).setMethod(false);
            } else {
                ViewGroup parent = (ViewGroup)view.getParent();

                if (view instanceof TimelineLayout) {
                    ActionManager.getInstance().add(new RemoveAction(view, new ActionParameter(parent, parent.indexOfChild(view))));
                    parent.removeView(view);
                    this.makeToast("Removed timeline");
                } else if (view instanceof SymbolProjectLayout) {
                    if (parent != null && parent instanceof TimelineLayout) {
                        ActionManager.getInstance().add(new RemoveAction(parent, new ActionParameter((ViewGroup)parent.getParent(), ((ViewGroup)parent.getParent()).indexOfChild(view))));
                        ((ViewGroup)parent.getParent()).removeView(parent);
                        this.makeToast("Removed project (and timeline)");
                    }
                } else if (view instanceof SymbolContainerBracketLayout) {
                    this.makeToast("Cannot delete container brackets");
                } else if (parent instanceof SymbolContainerExpandedLayout && parent.getParent() instanceof SymbolContainerLayout) {
                    ((SymbolContainerLayout)parent.getParent()).removeView(view);
                    this.makeToast("Removed symbol");
                } else {
                    this.makeToast("Cannot delete this symbol: " + view.getClass().getSimpleName() + ", with parent: " + parent.getClass().getSimpleName());
                }
            }
        } else {
            this.makeToast("Cannot remove symbolbar objects");
        }

        return true;
    }
}
