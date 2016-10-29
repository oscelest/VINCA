package com.noxyspace.vinca;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OwnTab extends ListFragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private ArrayList<String> fileFolderList = new ArrayList<String>();
    ArrayAdapter adapter;
    private int folderNo = 1;
    private Button btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.own_tab_fragment, container, false);


        fileFolderList.add("Folder " + folderNo++);
        fileFolderList.add("Folder " + folderNo++);
        fileFolderList.add("Folder " + folderNo++);




        btn = (Button) view.findViewById(R.id.button);
        btn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new ArrayAdapter(getActivity(), R.layout.folder_list_item, R.id.projectTitle, fileFolderList) {
            @Override
            public View getView(int position, View cachedView, ViewGroup parent) {
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy 'at' hh.mm.ss");
                View view = super.getView(position, cachedView, parent);
                TextView createdAt = (TextView) view.findViewById(R.id.createdAt);
                createdAt.setText("09-09-2016");
                TextView createdBy = (TextView) view.findViewById(R.id.lastEdit);
                createdBy.setText("Rune (" + dateFormat.format(date) + ")");

                return view;
            }
        };
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        //fileFolderList.add("Folder " + folderNo++);

//        Date date = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy 'at' hh.mm.ss");
//
//
//        View view = super.getView(position, cachedView, parent);
//        TextView createdAt = (TextView) view.findViewById(R.id.createdAt);
//        createdAt.setText("09-09-2016");
//        TextView createdBy = (TextView) view.findViewById(R.id.lastEdit);
//        createdBy.setText("Rune (" + dateFormat.format(date) + ")");

        adapter.notifyDataSetChanged();
    }
}