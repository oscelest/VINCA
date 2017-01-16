package com.noxyspace.vinca.activities.tabs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.noxyspace.vinca.R;
import com.noxyspace.vinca.objects.DirectoryObject;

import java.util.ArrayList;
import java.util.List;

public class MoveFragment extends DialogFragment {
    private List<DirectoryObject> folderObj = new ArrayList<>();
    private MyProjectsTab mpt = new MyProjectsTab();
    private ListAdapter adapter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.move_list_fragment, null);

        adapter = new ListAdapter() {
            @Override
            public void registerDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public int getCount() {
                return 0;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return null;
            }

            @Override
            public int getItemViewType(int position) {
                return 0;
            }

            @Override
            public int getViewTypeCount() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean areAllItemsEnabled() {
                return false;
            }

            @Override
            public boolean isEnabled(int position) {
                return false;
            }
        };

        setLis

        list = (ListView) v.findViewById(R.id.dirObjList);

        final DirObjAdapter adapter = new DirObjAdapter(this, android.R.layout.simple_list_item_1, mOfficeListItems);

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_move_title).setItems()
                .setPositiveButton(R.string.move, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // FIRE ZE MISSILES!
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }


    public class DialogListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return folderObj.size();
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
            if (view == null) {
                view = getActivity().getLayoutInflater().inflate(R.layout.directory_object_item, null);
            }


            return null;
        }
    }
}
