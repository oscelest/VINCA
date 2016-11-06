package com.noxyspace.vinca;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import static com.noxyspace.vinca.R.id.NewsListWiev;

public class NewsTab extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener{

    ListView newsList;

    public NewsTab() {
        newsList = (ListView) newsList.findViewById(NewsListWiev);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news_tab_fragment, container, false);

    }

    // Test

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}