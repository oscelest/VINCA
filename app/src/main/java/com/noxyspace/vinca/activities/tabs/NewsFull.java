package com.noxyspace.vinca.activities.tabs;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.noxyspace.vinca.R;

public class NewsFull extends Fragment {

    private View root;
    private TextView title, date, description, full;

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.news_full_fragment, container, false);

        title       = (TextView) root.findViewById(R.id.news_full_title);
        date        = (TextView) root.findViewById(R.id.news_full_date);
        description = (TextView) root.findViewById(R.id.news_full_description);
        full        = (TextView) root.findViewById(R.id.news_full_text);

        return root;
    }
}
