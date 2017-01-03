package com.noxyspace.vinca;

import android.content.DialogInterface;
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
import com.android.volley.VolleyError;
import com.noxyspace.vinca.objects.ApplicationObject;
import com.noxyspace.vinca.objects.DirectoryObject;
import com.noxyspace.vinca.requests.Directory.CreateDirectoryObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OwnTab extends ListFragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private ArrayList<DirectoryObject> directoryObjects = new ArrayList<DirectoryObject>();

    private CustomAdapter adapter;
    private FloatingActionButton fab_plus, fab_folder, fab_file;
    private boolean fab_plus_toggled = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.own_tab_fragment, container, false);

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
        if (!directoryObjects.get(position).isFolder()) {
            Toast.makeText(getActivity(), "File: " + position, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Folder: " + position, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == fab_plus && !fab_plus_toggled) {
            fab_folder.setVisibility(View.VISIBLE);
            fab_file.setVisibility(View.VISIBLE);
            fab_plus_toggled = true;
        } else {
            fab_folder.setVisibility(View.INVISIBLE);
            fab_file.setVisibility(View.INVISIBLE);
            fab_plus_toggled = false;
        }
        if (v == fab_folder) {
            createFolderDialog();

            fab_plus_toggled = false;
        }
        if (v == fab_file) {
            createFileDialog();
            fab_plus_toggled = false;
        }
        int i = 0;
        for (DirectoryObject s : directoryObjects) {
            Log.d("Object" + i++, s.getTitle() + " Type: " + (s.isFolder() ? "Folder" : "File"));
        }
        Log.d("size:", "" + directoryObjects.size());

        adapter.notifyDataSetChanged();
    }

    // Dialog that asks for the title of a file or folder and creates the element
    public void createFileDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.fragment_create_folder_dialog, null));
        builder.setTitle(R.string.filesName);

//          Set up the input
        final EditText fileTitle = new EditText(getActivity());
        fileTitle.setMaxLines(1);
//          Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        builder.setView(fileTitle);

//          Set up the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {

            // Creates a dialog box that asks for the new folders name
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String s = fileTitle.getText().toString();
                // Create Create File DirectoryObject Request.
                // directoryObjects.add(new DirectoryObject(ApplicationObject.nextFileId(), s, "Rune", 1, false, (int) (System.currentTimeMillis() / 1000)));
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

    // Dialog that asks for the title of a file or folder and creates the element
    public void createFolderDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.fragment_create_folder_dialog, null));
        builder.setTitle(R.string.foldersName);

//          Set up the input
        final EditText folderTitle = new EditText(getActivity());
        folderTitle.setMaxLines(1);
//          Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        builder.setView(folderTitle);

//          Set up the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("title", folderTitle.getText().toString());
                        params.put("owner_id", Integer.toString(ApplicationObject.getUser().getId()));
                        new CreateDirectoryObjectRequest(params, new Response.Listener<JSONObject>() {
                            public void onResponse(JSONObject response) {
                                try {
                                    if (response.getBoolean("success")) {
                                        Log.d("CreateDirObjSuccess", response.toString());
                                        List<DirectoryObject> dir = ApplicationObject.getDirectory();
                                        JSONObject newDirObj = response.getJSONObject("content");
                                        dir.add(new DirectoryObject(
                                                newDirObj.getInt("id"),
                                                newDirObj.getString("title"),
                                                newDirObj.getString("owner_name"),
                                                newDirObj.getInt("owner_id"),
                                                newDirObj.getInt("owner_id") == 1,
                                                newDirObj.getInt("time_created"),
                                                newDirObj.getInt("time_updated")
                                        ));
                                    } else {
                                        Log.d("CreateDirObjFailure", response.toString());
                                /* Do failure-stuff */
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            public void onErrorResponse(VolleyError error) {
                                Log.d("CreateDirObjErr", error.getMessage());
                            }
                        });
                    }
                }

        );
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()

                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }

        );
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
            folderName.setText(directoryObjects.get(position).getTitle());
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