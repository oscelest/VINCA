package com.noxyspace.vinca.activities.tabs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.noxyspace.vinca.R;
import com.noxyspace.vinca.objects.ApplicationObject;
import com.noxyspace.vinca.objects.DirectoryObject;
import com.noxyspace.vinca.requests.directory.GetDirectoryContentRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MoveFragment extends DialogFragment implements AdapterView.OnItemClickListener {
    private List<DirectoryObject> directoryObjects = new ArrayList<>();

    private TextView title, currentFolder;
    private MoveListAdapter adapter;

    private ListView listView;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.move_list_fragment, null);
        getDirectoryContent(null);

        adapter = new MoveListAdapter();

        listView = (ListView) view.findViewById(R.id.list);
        listView.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        currentFolder = (TextView) view.findViewById(R.id.current_folder);

        builder.setTitle("Where do you want to move the file?").setView(view);

        return builder.create();
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        adapter = new MoveListAdapter();
//
//        setListAdapter(adapter);
//        getListView().setOnItemClickListener(this);
//    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.getDirectoryContent(directoryObjects.get(position).getId());

    }

    public void getDirectoryContent(String folder_id) {
        directoryObjects.clear();

        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }

        ApplicationObject.getInstance().addRequest(new GetDirectoryContentRequest(folder_id,
                new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")) {
                                Log.d("GetDirContentSuccess", response.toString());

                                ApplicationObject.getInstance().setCurrentFolderId(response.getString("folder_id"));
                                ApplicationObject.getInstance().setCurrentParentId(response.getString("parent_id"));
                                JSONArray content = response.getJSONArray("content");

                                for (int i = 0; i < content.length(); i++) {
                                    JSONObject dirObj = content.getJSONObject(i);
                                    JSONObject owner = dirObj.getJSONObject("owner");

                                    directoryObjects.add(new DirectoryObject(
                                            dirObj.getString("_id"),
                                            owner.getString("_id"),
                                            owner.getString("first_name"),
                                            owner.getString("last_name"),
                                            dirObj.isNull("parent") ? null : dirObj.getJSONObject("parent").getString("_id"),
                                            dirObj.getString("name"),
                                            dirObj.getBoolean("folder"),
                                            dirObj.getLong("time_created"),
                                            dirObj.getLong("time_updated"),
                                            dirObj.getLong("time_deleted"))
                                    );
                                }

                                for (int i = directoryObjects.size() - 1; i > 0; i--) {
                                    if (!directoryObjects.get(i).isFolder()) {
                                        directoryObjects.remove(i);
                                        Log.d("FOR", ""+i);
                                    }
                                }

                                if (adapter != null) {
                                    adapter.notifyDataSetChanged();
                                }
                            } else {
                                Log.d("GetDirContentFailure", response.toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ));
    }


    public class MoveListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return directoryObjects.size();
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
                view = getActivity().getLayoutInflater().inflate(R.layout.move_list_item, null);
            }

            TextView folderName = (TextView) view.findViewById(R.id.projectTitle);
            folderName.setText(directoryObjects.get(position).getName());



//            try {
//                currentFolder.setText("Current folder: " + directoryObjects.get(directoryObjects.get(position).getParentId()).getName());
//            } catch (NullPointerException e) {
//                currentFolder.setText("Home");
//                e.printStackTrace();
//            }



            return view;
        }
    }
}
