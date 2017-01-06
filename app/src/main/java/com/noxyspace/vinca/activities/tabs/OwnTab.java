package com.noxyspace.vinca.activities.tabs;

import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.test.ApplicationTestCase;
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
import com.noxyspace.vinca.requests.directory.DeleteDirectoryObjectRequest;
import com.noxyspace.vinca.requests.directory.GetDirectoryContentRequest;
import com.noxyspace.vinca.requests.directory.GetDirectoryObjectRequest;
import com.noxyspace.vinca.requests.directory.UpdateDirectoryObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
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
        ApplicationObject.getInstance().setCurrentFolderId(0);
        this.getDirectoryContent(0);
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
            int intentId = directoryObjects.get(position).getId();
            Intent intent = new Intent(getActivity(), CanvasActivity.class);
            intent.putExtra("FILE_ID", intentId);
            startActivity(intent);
        } else {
            this.getDirectoryContent(directoryObjects.get(position).getId());
            //this.renameDirectoryObject(directoryObjects.get(position).getId(), "Sup bro?");
        }
    }

    @Override
    public void onClick(View v) {
        CreateDirectoryObjectDialog(v == fab_folder);
    }

    // Dialog that asks for the title of a file or folder and creates the element
    public void CreateDirectoryObjectDialog(boolean isFolder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.create_directory_object_dialog_fragment, null));
        builder.setTitle(isFolder ? R.string.foldersName : R.string.filesName);

        // Set up the input
        final EditText fileTitle = new EditText(getActivity());
        fileTitle.setMaxLines(1);

        final boolean folder;
        folder = isFolder;

        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        builder.setView(fileTitle);

        // Set up the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                createDirectoryObject(fileTitle.getText().toString(), folder);
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

    private void renameDirectoryObject(int directoryId, String name) {
        this.updateDirectoryObject(directoryId, name, -1, -1);
    }

    private void resetOwnerDirectoryObject(int directoryId, int ownerId) {
        this.updateDirectoryObject(directoryId, null, ownerId, -1);
    }

    private void resetParentDirectoryObject(int directoryId, int parentId) {
        this.updateDirectoryObject(directoryId, null, -1, parentId);
    }

    private void getDirectoryObject(int directoryId) {
        ApplicationObject.getInstance().addRequest(new GetDirectoryObjectRequest(directoryId,
            new Response.Listener<JSONObject>() {
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getBoolean("success")) {
                            Log.d("GetDirectorySuccess", response.toString());

                            JSONObject content = response.getJSONObject("content");

                            content.getInt("id");
                            content.getInt("owner_id");
                            content.getString("owner_first_name");
                            content.getString("owner_last_name");
                            content.getInt("parent_id");
                            content.getString("name");
                            content.getBoolean("folder");
                            content.getInt("time_created");
                            content.getInt("time_updated");
                            content.getInt("time_deleted");

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

    private void createDirectoryObject(String directoryName, boolean isFolder) {
        ApplicationObject.getInstance().addRequest(new CreateDirectoryObjectRequest(directoryName, ApplicationObject.getInstance().getCurrentFolderId(), isFolder,
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
                                content.getBoolean("folder"),
                                content.getInt("time_created"),
                                content.getInt("time_updated"),
                                content.getInt("time_deleted"))
                            );

                            if (adapter != null) {
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.d("CreateDirectoryFailure", response.toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            })
        );
    }

    private void updateDirectoryObject(int directoryId, String name, int ownerId, int parentId) {
        ApplicationObject.getInstance().addRequest(new UpdateDirectoryObjectRequest(directoryId, name, ownerId, parentId,
            new Response.Listener<JSONObject>() {
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getBoolean("success")) {
                            Log.d("UpdateDirectorySuccess", response.toString());

                            JSONObject content = response.getJSONObject("content");
                            Iterator<DirectoryObject> iterator = directoryObjects.iterator();

                            while (iterator.hasNext()) {
                                DirectoryObject directoryObject = iterator.next();

                                if (directoryObject.getId() == response.getInt("folder_id")) {
                                    directoryObject.setName(content.getString("name"));
                                    directoryObject.setOwnerId(content.getInt("owner_id"));
                                    directoryObject.setParentId(content.getInt("parent_id"));
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

    private void deleteDirectoryObject(int directoryId) {
        ApplicationObject.getInstance().addRequest(new DeleteDirectoryObjectRequest(directoryId,
            new Response.Listener<JSONObject>() {
                public void onResponse(JSONObject response) {
                    try {
                        if (response.getBoolean("success")) {
                            Log.d("DeleteDirectorySuccess", response.toString());

                            if (response.getBoolean("content")) {
                                Iterator<DirectoryObject> iterator = directoryObjects.iterator();

                                while (iterator.hasNext()) {
                                    DirectoryObject directoryObject = iterator.next();

                                    if (directoryObject.getId() == response.getInt("folder_id")) {
                                        iterator.remove();
                                        break;
                                    }
                                }

                                if (adapter != null) {
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        } else {
                            Log.d("DeleteDirectoryFailure", response.toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            })
        );
    }

    private void getDirectoryContent(int folder_id) {
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

                            ApplicationObject.getInstance().setCurrentFolderId(response.getInt("folder_id"));

                            JSONObject content = response.getJSONObject("content");
                            Iterator<String> iterator = content.keys();

                            while (iterator.hasNext()) {
                                String key = iterator.next();
                                JSONObject directoryContent = content.getJSONObject(key);

                                directoryObjects.add(new DirectoryObject(
                                    directoryContent.getInt("id"),
                                    directoryContent.getInt("owner_id"),
                                    directoryContent.getString("owner_first_name"),
                                    directoryContent.getString("owner_last_name"),
                                    directoryContent.getInt("parent_id"),
                                    directoryContent.getString("name"),
                                    directoryContent.getBoolean("folder"),
                                    directoryContent.getInt("time_created"),
                                    directoryContent.getInt("time_updated"),
                                    directoryContent.getInt("time_deleted"))
                                );
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