package com.noxyspace.vinca.activities.tabs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.noxyspace.vinca.R;
import com.noxyspace.vinca.objects.ApplicationObject;
import com.noxyspace.vinca.objects.DirectoryObject;
import com.noxyspace.vinca.requests.directory.GetDirectoryContentRequest;
import com.noxyspace.vinca.requests.directory.GetDirectoryObjectRequest;
import com.noxyspace.vinca.requests.directory.UpdateDirectoryObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MoveDialogFragment extends DialogFragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    private List<DirectoryObject> directoryObjects = new ArrayList<>();
    private List<String> folderNames = new ArrayList<>();
    private DirectoryObject directoryObject;
    private String objectToMoveID;
    private String folderToMoveTo;
    private boolean isFolder;

    private TextView currentFolder;
    private ImageView upNav;

    private MoveListAdapter adapter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.move_list_fragment, null);
        getDirectoryContent(null);
        objectToMoveID = getArguments().getString("ID");
        isFolder = getArguments().getBoolean("IS_FOLDER");
        adapter = new MoveListAdapter();

        ListView listView = (ListView) view.findViewById(R.id.list);
        listView.setOnItemClickListener(this);
        listView.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        upNav = (ImageView) view.findViewById(R.id.up);
        upNav.setOnClickListener(this);
        folderNames.add("Home");
        currentFolder = (TextView) view.findViewById(R.id.current_folder_name);
        currentFolder.setText(folderNames.get(0));

        builder.setTitle("Where do you want to move ?").setView(view).setPositiveButton(R.string.move, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (isFolder && directoryObject.getParentId().equals(objectToMoveID)) {
                    Toast.makeText(getActivity(), "Cannot move a folder to its child folder", Toast.LENGTH_LONG).show();
                } else {
                    if (currentFolder.getText().toString().equals("Home")) {
                        moveDirectoryObject(objectToMoveID, null);
                        Toast.makeText(getActivity(), "The folder is moved - if", Toast.LENGTH_SHORT).show();
                    } else {
                        moveDirectoryObject(objectToMoveID, folderToMoveTo);
                        Toast.makeText(getActivity(), "The folder is moved - else", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        ;

        return builder.create();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        getDirectoryObject(directoryObjects.get(position).getId());

        folderNames.add(directoryObjects.get(position).getName());
        currentFolder.setText(folderNames.get(folderNames.size() - 1));

        this.getDirectoryContent(directoryObjects.get(position).getId());
        adapter.notifyDataSetChanged();
    }

    private void moveDirectoryObject(String directoryId, String parentId) {
        this.updateDirectoryObject(directoryId, null, null, null, parentId);
    }

    private void updateDirectoryObject(String directoryId, String name, String data, String ownerId, String parentId) {
        ApplicationObject.getInstance().addRequest(new UpdateDirectoryObjectRequest(directoryId, name, data, ownerId, parentId,
                new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")) {
                                Log.d("UpdateDirectorySuccess", response.toString());

                                JSONObject content = response.getJSONObject("content");
                                Iterator<DirectoryObject> iterator = directoryObjects.iterator();

                                while (iterator.hasNext()) {
                                    DirectoryObject directoryObject = iterator.next();

                                    if (directoryObject.getId().equals(content.getString("_id"))) {
                                        directoryObject.setName(content.getString("name"));
                                        directoryObject.setOwnerId(content.isNull("owner") ? null : content.getJSONObject("owner").getString("_id"));
                                        directoryObject.setParentId(content.isNull("parent") ? null : content.getJSONObject("parent").getString("_id"));
                                        break;
                                    }
                                }

                                if (adapter != null) {
                                    adapter.notifyDataSetChanged();
                                }
                            } else {
                                Log.d("UpdateDirectoryFailure", response.toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                })
        );
    }

    private void getDirectoryObject(String object_id) {
        ApplicationObject.getInstance().addRequest(new GetDirectoryObjectRequest(object_id,
                new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")) {
                                Log.d("GetDirectorySuccess", response.toString());

                                JSONObject content = response.getJSONObject("content");
                                JSONObject owner = content.getJSONObject("owner");

                                directoryObject = new DirectoryObject(
                                        content.getString("_id"),
                                        owner.getString("_id"),
                                        owner.getString("first_name"),
                                        owner.getString("last_name"),
                                        content.isNull("parent") ? null : content.getJSONObject("parent").getString("_id"),
                                        content.getString("name"),
                                        content.getBoolean("folder"),
                                        content.getInt("time_created"),
                                        content.getInt("time_updated"),
                                        content.getInt("time_deleted")
                                );

                                folderToMoveTo = directoryObject.getId();
                            } else {
                                Log.d("GetDirectoryFailure", response.toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ));
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

    @Override
    public void onClick(View v) {
        if (v == upNav) {
            String s = ApplicationObject.getInstance().getCurrentParentId();
            if (s != null) {
                getDirectoryContent(s);
            }
            if (folderNames.size() > 1) {
                folderNames.remove(folderNames.size() - 1);
                currentFolder.setText(folderNames.get(folderNames.size() - 1));
            }
        }
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

            return view;
        }
    }
}
