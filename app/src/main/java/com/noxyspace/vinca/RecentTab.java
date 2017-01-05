package com.noxyspace.vinca;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.noxyspace.vinca.objects.DirectoryObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RecentTab extends ListFragment {

    private ArrayList<DirectoryObject> recentList = new ArrayList<>();
    private int fileNo = 1;
    CustomAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recent_tab_fragment, container, false);

        recentList.add(new DirectoryObject(0, 1, 0, "File 1", "Rune", null, false, (int) (System.currentTimeMillis() / 1000), (int) (System.currentTimeMillis() / 1000)));
        recentList.add(new DirectoryObject(1, 2, 0, "File 2", "Mikkel", null, false, (int) (System.currentTimeMillis() / 1000), (int) (System.currentTimeMillis() / 1000)));
        recentList.add(new DirectoryObject(2, 3, 0, "File 3", "Andreas", null, false, (int) (System.currentTimeMillis() / 1000), (int) (System.currentTimeMillis() / 1000)));
        recentList.add(new DirectoryObject(3, 4, 0, "File 4", "Magnus", null, false, (int) (System.currentTimeMillis() / 1000), (int) (System.currentTimeMillis() / 1000)));
        recentList.add(new DirectoryObject(4, 5, 0, "File 5", "Oliver", null, false, (int) (System.currentTimeMillis() / 1000), (int) (System.currentTimeMillis() / 1000)));

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
            if (!recentList.get(position).isFolder()) {
                img.setImageResource(R.drawable.file_white);
                TextView editor = (TextView) view.findViewById(R.id.lastEdit);
                editor.setText(recentList.get(position).getOwnerName() + " " + recentList.get(position).getCreatedDate());
            }

            TextView folderName = (TextView) view.findViewById(R.id.projectTitle);
            folderName.setText(recentList.get(position).getName());
            TextView createdAt = (TextView) view.findViewById(R.id.createdAt);
            createdAt.setText(recentList.get(position).getCreatedDate());

            Collections.sort(recentList, new Comparator<DirectoryObject>() {
                @Override
                public int compare(DirectoryObject dirObject1, DirectoryObject dirObject2) {
                    if (dirObject1.isFolder() && !dirObject2.isFolder()) {
                        return -1;
                    } else if (!dirObject1.isFolder() && dirObject2.isFolder()) {
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
