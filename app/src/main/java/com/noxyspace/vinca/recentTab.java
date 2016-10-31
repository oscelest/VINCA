package com.noxyspace.vinca;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class recentTab extends ListFragment {

    private ArrayList<String> recentList = new ArrayList<String>();
    private int fileNo = 1;
    ArrayAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recent_tab_fragment, container, false);
        recentList.add("File " + fileNo++);
        recentList.add("File " + fileNo++);
        recentList.add("File " + fileNo++);


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new ArrayAdapter(getActivity(), R.layout.file_list_item, R.id.projectTitle, recentList);
        setListAdapter(adapter);

    }
}