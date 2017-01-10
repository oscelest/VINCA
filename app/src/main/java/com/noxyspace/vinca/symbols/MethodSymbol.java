package com.noxyspace.vinca.symbols;

import android.content.Context;
import android.view.ViewGroup;

import com.noxyspace.vinca.R;

public class MethodSymbol extends Symbol {

    int methodId;

    public MethodSymbol(Context context, ViewGroup view) {

        super(context, view, R.drawable.method_symbol);
    }
}
