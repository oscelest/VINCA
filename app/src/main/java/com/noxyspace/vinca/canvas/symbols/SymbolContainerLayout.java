package com.noxyspace.vinca.canvas.symbols;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.noxyspace.vinca.canvas.SymbolLayout;

import java.util.ArrayList;
import java.util.List;

public class SymbolContainerLayout extends SymbolLayout {
    public static final int CONTAINER_MARGIN = 5;

    private SymbolContainerBracket symbolStart;
    private SymbolContainerBracket symbolEnd;
    private SymbolContainerCollapsed symbolCollapsed;

    private List<View> collapsedViews;
    private boolean collapsed;

    public SymbolContainerLayout(Context context) {
        this(context, true);
    }

    public SymbolContainerLayout(Context context, boolean acceptsDrop) {
        super(context, acceptsDrop);

        this.collapsedViews = new ArrayList<>();
        this.collapsed = false;
    }

    public void toggleCollapse() {
        if (this.collapsed) {
            this.removeAllViews();

            for (View view : this.collapsedViews) {
                super.addView(view, -1);
            }

            this.collapsed = false;
        } else {
            if (this.symbolCollapsed != null) {
                this.removeAllViews();
                super.addView(this.symbolCollapsed, -1);
            } else {
                this.removeAllViews();
                super.addView(this.symbolStart, -1);
                super.addView(this.symbolEnd, -1);
            }

            this.collapsed = true;
        }
    }

    public List<View> getCollapsedViews() {
        return this.collapsedViews;
    }

    public void removeCollapsibleView(View child) {
        this.collapsedViews.remove(child);
        this.removeView(child);
    }

    @Override
    public void addView(View child) {
        this.handleAddView(child, -1);
    }

    @Override
    public void addView(View child, int index) {
        this.handleAddView(child, index);
    }

    private void handleAddView(View view, int index) {
        if (this.collapsed) {
            if (index == -1) {
                this.collapsedViews.add(this.collapsedViews.size() - 1, view);
            } else {
                this.collapsedViews.add(index, view);
            }
        } else {
            if (this.getChildCount() >= 2) {
                if (index == -1) {
                    this.addCollapsibleView(view, this.getChildCount() - 1);
                } else {
                    this.addCollapsibleView(view, index);
                }
            } else {
                this.addCollapsibleView(view, -1);
            }
        }
    }

    private void addCollapsibleView(View view, int index) {
        if (index == -1) {
            this.collapsedViews.add(view);
        } else {
            this.collapsedViews.add(index, view);
        }

        super.addView(view, index);
    }

    protected void createContainerSymbols(SymbolContainerBracket symbolStart, SymbolContainerBracket symbolEnd, SymbolContainerCollapsed symbolCollapsed) {
        this.addView(this.symbolStart = symbolStart);
        this.addView(this.symbolEnd = symbolEnd);

        this.symbolCollapsed = symbolCollapsed;
    }
}
