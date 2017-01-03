package com.noxyspace.vinca;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.noxyspace.vinca.Objects.DirectoryObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RecentTab extends ListFragment {

    private ArrayList<DirectoryObject> recentList = new ArrayList<DirectoryObject>();
    private int fileNo = 1;
    CustomAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recent_tab_fragment, container, false);

        recentList.add(new File(0, "File 1", "Rune", 1, false, (int) (System.currentTimeMillis() / 1000)));
        recentList.add(new File(1, "File 2", "Mikkel", 2, false, (int) (System.currentTimeMillis() / 1000)));
        recentList.add(new File(2, "File 3", "Andreas", 3, false, (int) (System.currentTimeMillis() / 1000)));
        recentList.add(new File(3, "File 4", "Magnus", 4, false, (int) (System.currentTimeMillis() / 1000)));
        recentList.add(new File(4, "File 5", "Oliver", 5, false, (int) (System.currentTimeMillis() / 1000)));

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new CustomAdapter();
        //adapter = new ArrayAdapter(getActivity(), R.layout.directory_object_item, R.id.projectTitle, recentList);
        setListAdapter(adapter);
    }

    public class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return recentList.size();
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
            return 1;
        }

        @Override
        public int getItemViewType(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = getActivity().getLayoutInflater().inflate(R.layout.directory_object_item, null);
            }

            //view = getActivity().getLayoutInflater().inflate(R.layout.directory_object_item, null);
            ImageView img = (ImageView) view.findViewById(R.id.folder_icon);
            if (recentList.get(position).getType() == DirectoryObject.ObjectType.File) {
                img.setImageResource(R.drawable.file_white);
                TextView editor = (TextView) view.findViewById(R.id.lastEdit);
                editor.setText(recentList.get(position).getOwnerName() + " " + recentList.get(position).getDate());
            }

            TextView folderName = (TextView) view.findViewById(R.id.projectTitle);
            folderName.setText(recentList.get(position).getTitle());
            TextView createdAt = (TextView) view.findViewById(R.id.createdAt);
            createdAt.setText(recentList.get(position).getDate());

            Collections.sort(recentList, new Comparator<DirectoryObject>() {
                @Override
                public int compare(DirectoryObject dirObject1, DirectoryObject dirObject2) {
                    if (dirObject1 instanceof Folder && dirObject2 instanceof File) {
                        return -1;
                    } else if (dirObject1 instanceof File && dirObject2 instanceof Folder) {
                        return 1;
                    }

                    return 0;
                }
            });
            notifyDataSetChanged();
            return view;
        }
    }


}
