package com.noxyspace.vinca.canvas.symbols.specifications.containers.expanded;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.noxyspace.vinca.canvas.symbols.SymbolLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.additionals.SymbolEmptyLayout;
import com.noxyspace.vinca.canvas.symbols.specifications.containers.bracket.SymbolContainerBracketLayout;
import com.noxyspace.vinca.objects.ApplicationObject;

public class SymbolContainerExpandedLayout extends LinearLayout {
    public static final int CONTAINER_MARGIN = 5;

    private SymbolContainerBracketLayout symbolStart;
    private SymbolContainerBracketLayout symbolEnd;

    public SymbolContainerExpandedLayout(Context context) {
        super(context);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LayoutParams.WRAP_CONTENT,
            LayoutParams.WRAP_CONTENT
        );

        this.setLayoutParams(params);

        this.setOrientation(LinearLayout.HORIZONTAL);
        this.setGravity(Gravity.CENTER_VERTICAL);
    }

    public void createContainerSymbols(SymbolContainerBracketLayout start, SymbolContainerBracketLayout end, boolean acceptsDrop) {
        this.addView(this.symbolStart = start);

        if (acceptsDrop) {
            this.addView(new SymbolEmptyLayout(getContext()));
        }

        this.addView(this.symbolEnd = end);
    }

    public void resize(View viewBracket, View viewEmpty) {
        ViewGroup parentEmpty = (ViewGroup)viewEmpty.getParent();
        ViewGroup parent = (ViewGroup)this.getParent().getParent();

        if (parentEmpty == parent) {
            int indexEmpty = parent.indexOfChild(viewEmpty);
            int indexContainer = parent.indexOfChild((View)this.getParent());

            if (viewBracket == this.symbolStart) {
                if (indexEmpty < indexContainer) {
                    for (int i = indexContainer - 1; i > indexEmpty; i--) {
                        View child = parent.getChildAt(i);

                        if (!(child instanceof SymbolEmptyLayout) && child instanceof SymbolLayout) {
                            /* Move to position '2' to maintain the initial bracket and empty layout */
                            ((SymbolLayout)child).moveView(this, 2);
                        }
                    }
                } else {
                    this.makeToast("You can only expand the start symbol towards the left");
                }
            } else if (viewBracket == this.symbolEnd) {
                if (indexEmpty > indexContainer) {
                    for (int i = indexContainer + 1; i < indexEmpty; i++) {
                        View child = parent.getChildAt(i);

                        if (!(child instanceof SymbolEmptyLayout) && child instanceof SymbolLayout) {
                            ((SymbolLayout)child).moveView(this, this.getChildCount() - 1);

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

                        if (!(child instanceof SymbolEmptyLayout) && child instanceof SymbolLayout) {
                            ((SymbolLayout)child).moveView(parent, parent.indexOfChild((View)this.getParent()));

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

                        if (!(child instanceof SymbolEmptyLayout) && child instanceof SymbolLayout) {
                            /* Move to position '2' to maintain the initial bracket and empty layout */
                            ((SymbolLayout)child).moveView(parent, parent.indexOfChild((View)this.getParent()) + 2);
                        }
                    }
                } else {
                    this.makeToast("You can only shrink the end symbol towards the left");
                }
            }
        } else {
            this.makeToast("You cannot expand an enclosed object into the middle of- or around part of another");
        }
    }

    private void makeToast(String message) {
        ApplicationObject.getInstance().makeToast(getContext(), message);
    }
}
