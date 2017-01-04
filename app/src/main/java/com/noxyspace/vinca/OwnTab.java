package com.noxyspace.vinca;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.noxyspace.vinca.objects.ApplicationObject;
import com.noxyspace.vinca.objects.DirectoryObject;
import com.noxyspace.vinca.requests.Directory.CreateDirectoryObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OwnTab extends ListFragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private ArrayList<DirectoryObject> directoryObjects = new ArrayList<>();

    private CustomAdapter adapter;
    private FloatingActionButton fab_folder, fab_file;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.own_tab_fragment, container, false);

        directoryObjects.add(new DirectoryObject(0, 1, 0, "File 1", "Rune", "{}", false, (int) (System.currentTimeMillis() / 1000), (int) (System.currentTimeMillis() / 1000)));
        directoryObjects.add(new DirectoryObject(1, 2, 0, "File 2", "Mikkel", "{}", false, (int) (System.currentTimeMillis() / 1000), (int) (System.currentTimeMillis() / 1000)));
        directoryObjects.add(new DirectoryObject(2, 3, 0, "File 3", "Andreas", "{}", false, (int) (System.currentTimeMillis() / 1000), (int) (System.currentTimeMillis() / 1000)));
        directoryObjects.add(new DirectoryObject(3, 4, 0, "File 4", "Magnus", "{}", false, (int) (System.currentTimeMillis() / 1000), (int) (System.currentTimeMillis() / 1000)));
        directoryObjects.add(new DirectoryObject(4, 5, 0, "File 5", "Oliver", "{}", false, (int) (System.currentTimeMillis() / 1000), (int) (System.currentTimeMillis() / 1000)));

        FloatingActionButton fab_plus = (FloatingActionButton) view.findViewById(R.id.fab_plus);
        fab_plus.setOnClickListener(new View.OnClickListener() {
            private boolean toggled = false;

            @Override
            public void onClick(View v) {
                fab_folder.setVisibility(toggled ? View.VISIBLE : View.INVISIBLE);
                fab_file.setVisibility(toggled ? View.VISIBLE : View.INVISIBLE);
                toggled = !toggled;
            }
        });

        fab_folder = (FloatingActionButton) view.findViewById(R.id.fab_folder);
        fab_file = (FloatingActionButton) view.findViewById(R.id.fab_file);
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
        if (!directoryObjects.get(position).isFolder()) {
            Toast.makeText(getActivity(), "File: " + position, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), CanvasActivity.class));
        } else {
            Toast.makeText(getActivity(), "Folder: " + position, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        CreateDirectoryObjectDialog(v == fab_folder);
    }

    // Dialog that asks for the title of a file or folder and creates the element
    public void CreateDirectoryObjectDialog(final boolean isFolder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.fragment_create_directory_object_dialog, null));
        builder.setTitle(isFolder ? R.string.foldersName : R.string.filesName);

// Set up the input
        final EditText fileTitle = new EditText(getActivity());
        fileTitle.setMaxLines(1);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        builder.setView(fileTitle);

// Set up the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        new CreateDirectoryObjectRequest(fileTitle.getText().toString(), "0", (isFolder ? "1" : "0"), new Response.Listener<JSONObject>() {
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.getBoolean("success")) {
                                        Log.d("CreateDirObjSuccess", response.toString());
                                        List<DirectoryObject> dir = ApplicationObject.getDirectory();
                                        JSONObject newDirObj = response.getJSONObject("content");
                                        dir.add(new DirectoryObject(
                                                newDirObj.getInt("id"),
                                                newDirObj.getInt("owner_id"),
                                                newDirObj.getInt("parent_id"),
                                                newDirObj.getString("title"),
                                                newDirObj.getString("owner_name"),
                                                newDirObj.getString("data"),
                                                newDirObj.getBoolean("folder"),
                                                newDirObj.getInt("time_created"),
                                                newDirObj.getInt("time_updated")
                                        ));
                                        adapter.notifyDataSetChanged();
                                    } else {
                                        Log.d("CreateDirObjFailure", response.toString());
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
        );
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public class CustomAdapter extends BaseAdapter {

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
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            if (!directoryObjects.get(position).isFolder()) {
                return 0;
            } else {
                return 1;
            }
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                view = getActivity().getLayoutInflater().inflate(R.layout.directory_object_item, null);
            }

            //view = getActivity().getLayoutInflater().inflate(R.layout.directory_object_item, null);
            ImageView img = (ImageView) view.findViewById(R.id.folder_icon);
            if (!directoryObjects.get(position).isFolder()) {
                img.setImageResource(R.drawable.file_white);
                TextView editor = (TextView) view.findViewById(R.id.lastEdit);
                editor.setText(directoryObjects.get(position).getOwnerName() + " " + directoryObjects.get(position).getCreatedDate());
            }

            TextView folderName = (TextView) view.findViewById(R.id.projectTitle);
            folderName.setText(directoryObjects.get(position).getName());
            TextView createdAt = (TextView) view.findViewById(R.id.createdAt);
            createdAt.setText(directoryObjects.get(position).getCreatedDate());

            Collections.sort(directoryObjects, new Comparator<DirectoryObject>() {
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