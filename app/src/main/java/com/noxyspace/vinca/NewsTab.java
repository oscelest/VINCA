package com.noxyspace.vinca;

import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NewsTab extends ListFragment implements AdapterView.OnItemClickListener {

    private ArrayList<SingleRow> newsList = new ArrayList<SingleRow>();
    private CustomAdapter adapter;
    private int id = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_tab_fragment, container, false);

        newsList.add(new SingleRow("Titel " + id, "Beskrivelse " + id++));
        newsList.add(new SingleRow("Titel " + id, "Beskrivelse " + id++));
        newsList.add(new SingleRow("Titel " + id, "Beskrivelse " + id++));

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new CustomAdapter();
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }

    class SingleRow {

        String title, description;
        int image;

        SingleRow(String title, String description) {
            this.title = title;
            this.description = description;
            //this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }
    }

    public class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return newsList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            int type = getItemViewType(position);
            if (view == null) {
                    view = getActivity().getLayoutInflater().inflate(R.layout.news_element, null);
            }
                ImageView img = (ImageView) view.findViewById(R.id.newsImage);
                TextView title = (TextView) view.findViewById(R.id.newsTitle);
                title.setText(newsList.get(position).getTitle());
                TextView description = (TextView) view.findViewById(R.id.newsDescription);
                description.setText(newsList.get(position).getDescription());

            return view;
        }
    }
}