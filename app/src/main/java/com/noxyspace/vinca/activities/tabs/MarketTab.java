package com.noxyspace.vinca.activities.tabs;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.noxyspace.vinca.R;
import com.noxyspace.vinca.objects.DirectoryObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MarketTab extends ListFragment {

    //private ArrayList<DirectoryObject> recentList = new ArrayList<>();
    //private int fileNo = 1;
    //CustomAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.market_tab_fragment, container, false);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
        return view;
    }

    public void onTabSelected() {

    }
    /**

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
                editor.setText(recentList.get(position).getOwnerFullName() + " " + recentList.get(position).getCreatedTime());
            }

            TextView folderName = (TextView) view.findViewById(R.id.projectTitle);
            folderName.setText(recentList.get(position).getName());
            TextView createdAt = (TextView) view.findViewById(R.id.createdAt);
            createdAt.setText(recentList.get(position).getCreatedTime());

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
    }**/
}
