package com.noxyspace.vinca;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OwnTab extends ListFragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private ArrayList<FolderItem> folderItems = new ArrayList<FolderItem>();
    private ArrayList<FolderItem> fileItems = new ArrayList<FolderItem>();
    CustomAdapter adapter;
    private int folderNo = 1;
    FloatingActionButton fab_plus, fab_folder, fab_file;
    boolean fab_plus_toggled = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.own_tab_fragment, container, false);

        folderItems.add(new FolderItem("Folder 1", "Rune" + new Date()));
        folderItems.add(new FolderItem("Folder 2", "Magnus" + new Date()));
        folderItems.add(new FolderItem("Folder 3", "Andreas" + new Date()));

        fileItems.add(new FolderItem("Folder 1", "Oliver" + new Date()));
        fileItems.add(new FolderItem("Folder 2", "Mikkel" + new Date()));
        fileItems.add(new FolderItem("Folder 3", "Valdemar" + new Date()));

        fab_plus = (FloatingActionButton) view.findViewById(R.id.fab_plus);
        fab_folder = (FloatingActionButton) view.findViewById(R.id.fab_folder);
        fab_file = (FloatingActionButton) view.findViewById(R.id.fab_file);
        fab_plus.setOnClickListener(this);
        fab_folder.setOnClickListener(this);
        fab_file.setOnClickListener(this);


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

    @Override
    public void onClick(View v) {
        if (v == fab_plus && !fab_plus_toggled){
            fab_folder.setVisibility(View.VISIBLE);
            fab_file.setVisibility(View.VISIBLE);
            fab_plus_toggled = true;
        }else {
            fab_folder.setVisibility(View.INVISIBLE);
            fab_file.setVisibility(View.INVISIBLE);
            fab_plus_toggled = false;
        }
        if (v == fab_folder){
            folderItems.add(new FolderItem("Titel " + folderNo++, "Rune " + new Date()));
            fab_plus_toggled = false;
        } if (v == fab_file) {
            fileItems.add(new FolderItem("Titel X", "Rune " + new Date()));
            fab_plus_toggled = false;
        }
        adapter.notifyDataSetChanged();
    }

    public class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return folderItems.size() + fileItems.size();
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
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            if (position < folderItems.size()) {
                return 0;
            } else {
                return 1;
            }
        }

//        @Override
//        public boolean isEnabled(int position) {
//            return true;
//        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            int type = getItemViewType(position);
            if (view == null) {
                if (type == 0) {
                    view = getActivity().getLayoutInflater().inflate(R.layout.folder_list_item, null);
                } else {
                    view = getActivity().getLayoutInflater().inflate(R.layout.file_list_item, null);
                }
            }

            if (type == 0) {
                ImageView img = (ImageView) view.findViewById(R.id.icon);
                TextView folderName = (TextView) view.findViewById(R.id.projectTitle);
                folderName.setText(folderItems.get(position).getTitle());
                TextView editor = (TextView) view.findViewById(R.id.lastEdit);
                editor.setText(folderItems.get(position).getEditor());
            } else {
                ImageView img = (ImageView) view.findViewById(R.id.icon);
                TextView fileName = (TextView) view.findViewById(R.id.projectTitle);
                fileName.setText(fileItems.get(position - folderItems.size()).getTitle());
                TextView editor = (TextView) view.findViewById(R.id.lastEdit);
                editor.setText(fileItems.get(position - folderItems.size()).getEditor());
            }
            return view;
        }
    }

}