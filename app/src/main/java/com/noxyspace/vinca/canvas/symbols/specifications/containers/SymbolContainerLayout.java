package com.noxyspace.vinca.canvas.symbols.specifications.containers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.noxyspace.vinca.activities.CanvasActivity;
import com.noxyspace.vinca.canvas.actions.ActionManager;
import com.noxyspace.vinca.canvas.actions.ActionParameter;
import com.noxyspace.vinca.canvas.actions.derivatives.AddAction;
import com.noxyspace.vinca.canvas.symbols.SymbolLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.containers.bracket.SymbolContainerBracketLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.additionals.SymbolEmptyLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.additionals.SymbolTitle;
import com.noxyspace.vinca.canvas.symbols.specifications.containers.collapsed.SymbolContainerCollapsed;
import com.noxyspace.vinca.canvas.symbols.specifications.figures.activity.SymbolActivity;
import com.noxyspace.vinca.canvas.symbols.specifications.figures.activity.SymbolActivityLayout;

import java.util.ArrayList;
import java.util.List;

public class SymbolContainerLayout extends SymbolLayout {
    public static final int CONTAINER_MARGIN = 5;

    private SymbolTitle header;
    private SymbolTitle footer;

    private SymbolContainerExpanded symbolExpanded;
    private SymbolContainerCollapsed symbolCollapsed;

    private boolean collapsed;

    public SymbolContainerLayout(Context context) {
        this(context, true);
    }

    public SymbolContainerLayout(Context context, boolean acceptsDrop) {
        this(context, acceptsDrop, false);
    }

    public SymbolContainerLayout(Context context, boolean acceptsDrop, boolean collapsed) {
        super(context, acceptsDrop);

        this.symbolExpanded = new SymbolContainerExpanded(context);
        this.symbolCollapsed = null;

        this.collapsed = collapsed;
    }

    public SymbolContainerExpanded getExpandedLayout() {
        return this.symbolExpanded;
    }

    public boolean isCollapsed() {
        return this.collapsed;
    }

    public String getHeader() {
        return this.header.getText().toString();
    }

    public void setHeader(String message) {
        this.header.setText(message);
    }

    public String getFooter() {
        return this.footer.getText().toString();
    }

    public void setFooter(String message) {
        this.footer.setText(message);
    }

    public void toggleCollapse() {
        if (this.symbolExpanded != null && this.symbolCollapsed != null) {
            int index = this.indexOfChild(this.collapsed ? this.symbolCollapsed : this.symbolExpanded);
            this.removeViewAt(index);

            if (this.collapsed) {
                this.addView(this.symbolExpanded, index);
                this.collapsed = false;
            } else {
                this.addView(this.symbolCollapsed, index);
                this.collapsed = true;
            }
        }
    }

    protected void createContainerSymbols(SymbolContainerBracketLayout start, SymbolContainerBracketLayout end, SymbolContainerCollapsed collapse) {
        this.symbolExpanded.createContainerSymbols(start, end, this.isDropAccepted());
        this.symbolCollapsed = collapse;

        //this.setOrientation(LinearLayout.VERTICAL);

//        if (this.isDropAccepted()) {
//            this.addViewSuper(this.header = new SymbolTitle(getContext(), "fHeader"));
//        }

        if (this.collapsed) {
            this.addView(this.symbolCollapsed);
        } else {
            this.addView(this.symbolExpanded);
        }

//        if (this.isDropAccepted()) {
//            this.addViewSuper(this.footer = new SymbolTitle(getContext(), "hFooter"));
//        }
    }

    @Override
    public void addView(View child) {
        this.handleAddView(child, -1);
    }

    @Override
    public void addView(View child, int index) {
        this.handleAddView(child, index);
    }

    public void handleAddView(View view, int index) {
        if (view == this.symbolExpanded || view == this.symbolCollapsed) {
            super.addView(view, index);
        } else {
            if (index == -1) {
                this.symbolExpanded.addView(view, this.symbolExpanded.getChildCount() - 1);

                if (!(view instanceof SymbolEmptyLayout) && view instanceof SymbolLayout) {
                    this.symbolExpanded.addView(new SymbolEmptyLayout(getContext()), this.symbolExpanded.getChildCount() - 1);
                }
            } else {
                this.symbolExpanded.addView(view, index);

                if (!(view instanceof SymbolEmptyLayout) && view instanceof SymbolLayout) {
                    this.symbolExpanded.addView(new SymbolEmptyLayout(getContext()), index + 1);
                }
            }

            if (this.collapsed) {
                this.makeToast("Added symbol to the collapsed structure");
            }
        }
    }

    @Override
    public void removeView(View view) {
        if (view == this.symbolExpanded || view == this.symbolCollapsed) {
            super.removeView(view);
        } else {
            int index = this.symbolExpanded.indexOfChild(view);
            SymbolLayout empty = (SymbolLayout)this.symbolExpanded.getChildAt(index + 1);

            this.symbolExpanded.removeView(view);
            this.symbolExpanded.removeView(empty);
        }
    }

    @Override
    public List<View> fetchAllChildViews() {
        List<View> children = new ArrayList<>();

        for (int i = 0, childCount = this.symbolExpanded.getChildCount(); i < childCount; i++) {
            View child = this.symbolExpanded.getChildAt(i);

            if (child instanceof SymbolLayout) {
                children.add(child);

                if (child instanceof SymbolContainerLayout) {
                    children.add(((SymbolContainerLayout)child).getExpandedLayout());
                }

                children.addAll(((SymbolLayout)child).fetchAllChildViews());
            }
        }

        return children;
    }

    @Override
    public List<View> fetchChildViews() {
        List<View> children = new ArrayList<>();

        for (int i = 0, childCount = this.symbolExpanded.getChildCount(); i < childCount; i++) {
            children.add(this.symbolExpanded.getChildAt(i));
        }

        return children;
    }
}
