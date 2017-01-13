package com.noxyspace.vinca.canvas.symbols.specifications;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.noxyspace.vinca.canvas.symbols.SymbolLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.empty.SymbolEmptyLayout;

import java.util.ArrayList;
import java.util.List;

public class SymbolContainerLayout extends SymbolLayout {
    public static final int CONTAINER_MARGIN = 5;

    private SymbolContainerBracketLayout symbolStart;
    private SymbolContainerBracketLayout symbolEnd;
    private SymbolContainerCollapsed symbolCollapsed;

    private List<View> collapsedViews;
    private boolean collapsed;

    public SymbolContainerLayout(Context context) {
        this(context, true);
    }

    public SymbolContainerLayout(Context context, boolean acceptsDrop) {
        this(context, acceptsDrop, false);
    }

    public SymbolContainerLayout(Context context, boolean acceptsDrop, boolean collapsed) {
        super(context, acceptsDrop);

        this.collapsedViews = new ArrayList<>();
        this.collapsed = collapsed;
    }

    public boolean isCollapsed() {
        return this.collapsed;
    }

    public void toggleCollapse() {
        if (this.collapsed) {
            this.removeAllViews();

            for (View view : this.collapsedViews) {
                this.addViewSuper(view);
            }

            this.collapsed = false;
        } else {
            if (this.symbolCollapsed != null) {
                this.removeAllViews();
                super.addView(this.symbolCollapsed, -1);

                this.collapsed = true;
            }
        }
    }

    public void resize(View viewBracket, View viewEmpty) {
        ViewGroup parentEmpty = (ViewGroup)viewEmpty.getParent();
        ViewGroup parent = (ViewGroup)this.getParent();

        if (parentEmpty == parent) {
            int indexEmpty = parent.indexOfChild(viewEmpty);
            int indexContainer = parent.indexOfChild(this);

            if (viewBracket == this.symbolStart) {
                if (indexEmpty < indexContainer) {
                    for (int i = indexContainer - 1; i > indexEmpty; i--) {
                        View child = parent.getChildAt(i);

                        if (!(child instanceof SymbolEmptyLayout)) {
                            /* Move to position '2' to maintain the initial bracket and empty layout */
                            this.moveView(child, this, 2);
                        }
                    }
                } else {
                    this.makeToast("You can only expand the start symbol towards the left");
                }
            } else if (viewBracket == this.symbolEnd) {
                if (indexEmpty > indexContainer) {
                    for (int i = indexContainer + 1; i < indexEmpty; i++) {
                        View child = parent.getChildAt(i);

                        if (!(child instanceof SymbolEmptyLayout)) {
                            this.moveView(child, this);

                            i -= 2;
                            indexEmpty -= 2;
                        }
                    }
                } else {
                    this.makeToast("You can only expand the end symbol towards the right");
                }
            }
        } else if (parentEmpty == this) {
            int indexEmpty = this.indexOfChild(viewEmpty);
            int indexBracket = this.indexOfChild(viewBracket);

            if (viewBracket == this.symbolStart) {
                if (indexEmpty > indexBracket) {
                    for (int i = indexBracket + 1; i < indexEmpty; i++) {
                        View child = this.getChildAt(i);

                        if (!(child instanceof SymbolEmptyLayout)) {
                            this.moveView(child, parent, parent.indexOfChild(this));

                            i -= 2;
                            indexEmpty -= 2;
                        }
                    }
                } else {
                    this.makeToast("You can only shrink the start symbol towards the right");
                }
            } else if (viewBracket == this.symbolEnd) {
                if (indexEmpty < indexBracket) {
                    for (int i = indexBracket - 1; i > indexEmpty; i--) {
                        View child = this.getChildAt(i);

                        if (!(child instanceof SymbolEmptyLayout)) {
                            /* Move to position '2' to maintain the initial bracket and empty layout */
                            this.moveView(child, parent, parent.indexOfChild(this) + 2);
                        }
                    }
                } else {
                    this.makeToast("You can only shrink the end symbol towards the left");
                }
            }
        } else {
            this.makeToast("You cannot expand an enclosed object into the middle of another");
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

    public void handleAddView(View view, int index) {
        if (this.collapsed) {
            if (index == -1) {
                this.collapsedViews.add(this.collapsedViews.size() - 1, view);
                this.collapsedViews.add(this.collapsedViews.size() - 1, new SymbolEmptyLayout(getContext()));
            } else {
                this.collapsedViews.add(index, view);
                this.collapsedViews.add(index, new SymbolEmptyLayout(getContext()));
            }

            this.makeToast("Added symbol to the collapsed structure");
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

        if (view instanceof SymbolEmptyLayout) {
            this.addViewSuper(view, index);
        } else {
            super.addView(view, index);
        }
    }

    protected void createContainerSymbols(SymbolContainerBracketLayout symbolStart, SymbolContainerBracketLayout symbolEnd, SymbolContainerCollapsed symbolCollapsed) {
        this.symbolStart = symbolStart;
        this.symbolEnd = symbolEnd;
        this.symbolCollapsed = symbolCollapsed;

        if (this.collapsed) {
            super.addView(this.symbolCollapsed, -1);
        } else {
            this.collapsedViews.add(this.symbolStart);
            this.addViewSuper(this.symbolStart);

            if (this.isDropAccepted()) {
                SymbolEmptyLayout empty = new SymbolEmptyLayout(getContext());
                this.collapsedViews.add(empty);
                this.addViewSuper(empty);
            }

            this.collapsedViews.add(this.symbolEnd);
            this.addViewSuper(this.symbolEnd);
        }
    }
}
