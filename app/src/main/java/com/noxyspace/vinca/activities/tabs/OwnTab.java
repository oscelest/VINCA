package com.noxyspace.vinca.activities.tabs;

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

import com.android.volley.Response;
import com.noxyspace.vinca.activities.CanvasActivity;
import com.noxyspace.vinca.R;
import com.noxyspace.vinca.objects.ApplicationObject;
import com.noxyspace.vinca.objects.DirectoryObject;
import com.noxyspace.vinca.requests.directory.CreateDirectoryObjectRequest;
import com.noxyspace.vinca.requests.directory.GetDirectoryContentRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OwnTab extends ListFragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private CustomAdapter adapter;
    private List<DirectoryObject> directoryObjects = new ArrayList<>();

    private FloatingActionButton fab_folder;
    private FloatingActionButton fab_file;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.own_tab_fragment, container, false);

        ((FloatingActionButton)view.findViewById(R.id.fab_plus)).setOnClickListener(new View.OnClickListener() {
            private boolean toggled = false;

            @Override
            public void onClick(View v) {
                fab_folder.setVisibility(toggled ? View.VISIBLE : View.INVISIBLE);
                fab_file.setVisibility(toggled ? View.VISIBLE : View.INVISIBLE);
                toggled = !toggled;
            }
        });

        fab_folder = (FloatingActionButton)view.findViewById(R.id.fab_folder);
        fab_folder.setOnClickListener(this);

        fab_file = (FloatingActionButton)view.findViewById(R.id.fab_file);
        fab_file.setOnClickListener(this);

        return view;
    }

    public void onTabSelected() {
        // this.fetchFolderContent(0);

        directoryObjects.add(new DirectoryObject(
            0,
            1,
            "Valdemar",
            "Carøe",
            3,
            "Filnavn",
            "....",
            false,
            (int) (System.currentTimeMillis() / 1000),
            (int) (System.currentTimeMillis() / 1000),
            (int) (System.currentTimeMillis() / 1000))
        );

        if (this.adapter != null) {
            this.adapter.notifyDataSetChanged();
        }
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
            startActivity(new Intent(getActivity(), CanvasActivity.class));
        } else {
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

        builder.setView(inflater.inflate(R.layout.create_directory_object_dialog_fragment, null));
        builder.setTitle(isFolder ? R.string.foldersName : R.string.filesName);

        // Set up the input
        final EditText fileTitle = new EditText(getActivity());
        fileTitle.setMaxLines(1);

        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        builder.setView(fileTitle);

        // Set up the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ApplicationObject.getInstance().addRequest(new CreateDirectoryObjectRequest(fileTitle.getText().toString(), "0", (isFolder ? "1" : "0"),
                    new Response.Listener<JSONObject>() {
                        public void onResponse(JSONObject response) {
                            try {
                                if (response.getBoolean("success")) {
                                    Log.d("CreateDirectorySuccess", response.toString());

                                    JSONObject content = response.getJSONObject("content");

                                    directoryObjects.add(new DirectoryObject(
                                        content.getInt("id"),
                                        content.getInt("owner_id"),
                                        content.getString("owner_first_name"),
                                        content.getString("owner_last_name"),
                                        content.getInt("parent_id"),
                                        content.getString("name"),
                                        content.getString("data"),
                                        content.getInt("folder") == 1,
                                        content.getInt("time_created"),
                                        content.getInt("time_updated"),
                                        content.getInt("time_deleted"))
                                    );

                                    if (adapter != null) {
                                        adapter.notifyDataSetChanged();
                                    }
                                } else {
                                    Log.d("CreateDirectoryFailure", response.toString());
                                    //Toast.makeText(getApplicationContext(), "Server error, try again later.", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    })
                );
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void fetchFolderContent(int folder_id) {
        directoryObjects.clear();

        ApplicationObject.getInstance().addRequest(new GetDirectoryContentRequest(Integer.toString(folder_id),
            new Response.Listener<JSONObject>() {
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getBoolean("success")) {
                            Log.d("GetDirectorySuccess", response.toString());

                            JSONObject content = response.getJSONObject("content");

                            /* TO DO: Enumerate content-files and add them all. */
//                            directoryObjects.add(new DirectoryObject(
//                                content.getInt("id"),
//                                content.getInt("owner_id"),
//                                content.getString("owner_first_name"),
//                                content.getString("owner_last_name"),
//                                content.getInt("parent_id"),
//                                content.getString("name"),
//                                content.getString("data"),
//                                content.getInt("folder") == 1,
//                                content.getInt("time_created"),
//                                content.getInt("time_updated"),
//                                content.getInt("time_deleted"))
//                            );

                            if (adapter != null) {
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.d("GetDirectoryFailure", response.toString());
                            //Toast.makeText(getApplicationContext(), "Server error, try again later.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        ));
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
                editor.setText(directoryObjects.get(position).getOwnerFullName() + " " + directoryObjects.get(position).getCreatedTime());
            }

            TextView folderName = (TextView) view.findViewById(R.id.projectTitle);
            folderName.setText(directoryObjects.get(position).getName());
            TextView createdAt = (TextView) view.findViewById(R.id.createdAt);
            createdAt.setText(directoryObjects.get(position).getCreatedTime());

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