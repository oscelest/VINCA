package com.noxyspace.vinca.canvas.symbols.specifications.containers;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.noxyspace.vinca.activities.CanvasActivity;
import com.noxyspace.vinca.canvas.actions.ActionManager;
import com.noxyspace.vinca.canvas.symbols.SymbolLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.containers.bracket.SymbolContainerBracketLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.additionals.SymbolEmptyLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.additionals.SymbolTitle;
import com.noxyspace.vinca.canvas.symbols.specifications.containers.collapsed.SymbolContainerCollapsed;
import com.noxyspace.vinca.canvas.symbols.specifications.containers.expanded.SymbolContainerExpanded;

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

        this.setOrientation(LinearLayout.VERTICAL);

        this.header = new SymbolTitle(context, "");
        this.footer = new SymbolTitle(context, "");

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
            this.removeAllViews();

            if (this.collapsed) {
                super.addView(this.symbolExpanded, -1);
                this.collapsed = false;
            } else {
                this.showCollapsed();
                this.collapsed = true;
            }
        }
    }

    protected void createContainerSymbols(SymbolContainerBracketLayout start, SymbolContainerBracketLayout end, SymbolContainerCollapsed collapse) {
        this.symbolExpanded.createContainerSymbols(start, end, this.isDropAccepted());
        this.symbolCollapsed = collapse;

        if (this.collapsed) {
            this.showCollapsed();
        } else {
            super.addView(this.symbolExpanded, -1);
        }
    }

    private void showCollapsed() {
        if (this.isDropAccepted()) {
            super.addView(this.header, -1);
        }

        super.addView(this.symbolCollapsed, -1);

        if (this.isDropAccepted()) {
            super.addView(this.footer, -1);
        }
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
        if (this.symbolExpanded != null) {
            this.symbolExpanded.addView(view, (index == -1) ? (this.symbolExpanded.getChildCount() - 1) : index);

            if (!(view instanceof SymbolEmptyLayout) && view instanceof SymbolLayout) {
                this.symbolExpanded.addView(new SymbolEmptyLayout(getContext()), (index == -1) ? (this.symbolExpanded.getChildCount() - 1) : (index + 1));
            }

            if (this.collapsed && !ActionManager.isManagingAction && !CanvasActivity.isLoadingSymbols) {
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
