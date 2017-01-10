package com.noxyspace.vinca.activities.tabs;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import org.json.JSONArray;
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

    private ImageView home_btn;
    private FloatingActionButton fab_btn;
    private boolean toggled = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.own_tab_fragment, container, false);

        home_btn = (ImageView) view.findViewById(R.id.home);
        home_btn.setOnClickListener(this);
        fab_btn = (FloatingActionButton) view.findViewById(R.id.fab_plus);
        fab_btn.setOnClickListener(this);

        fab_folder = (FloatingActionButton) view.findViewById(R.id.fab_folder);
        fab_folder.setOnClickListener(this);

        fab_file = (FloatingActionButton) view.findViewById(R.id.fab_file);
        fab_file.setOnClickListener(this);

        return view;
    }

    public void onTabSelected() {
        ApplicationObject.getInstance().setCurrentFolderId(null);
        this.getDirectoryContent(null);
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
            String intentId = directoryObjects.get(position).getId();
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
        if (v == home_btn) {
            getDirectoryContent(null);
        } else if (v == fab_btn) {
            fab_folder.setVisibility(toggled ? View.VISIBLE : View.INVISIBLE);
            fab_file.setVisibility(toggled ? View.VISIBLE : View.INVISIBLE);
            toggled = !toggled;

        } else if (v == fab_file || v == fab_folder) {
            createDirectoryObjectDialog(v == fab_folder);
        }
    }

    // Dialog that asks for the title of a file or folder and creates the element
    public void createDirectoryObjectDialog(boolean isFolder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

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

    //TODO: Kan man ikke undgå final her?
    public void renameDirectoryObjectDialog(final DirectoryObject directoryObject) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("New name:");

        // Set up the input
        final EditText fileTitle = new EditText(getActivity());
        fileTitle.setMaxLines(1);

        builder.setView(fileTitle);

        // Set up the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                renameDirectoryObject(directoryObject.getId(), directoryObject.getName());
                Toast.makeText(getActivity(), "Woop woop rename!", Toast.LENGTH_SHORT).show();
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

    public void deleteDirectoryObjectDialog(final DirectoryObject directoryObject) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage("Are you sure you want to delete " + directoryObject.getName() + "?");

        // Set up the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                deleteDirectoryObject(directoryObject.getId());
                Toast.makeText(getActivity(), "Woop woop delete!!", Toast.LENGTH_SHORT).show();
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

    private void renameDirectoryObject(String directoryId, String name) {
        this.updateDirectoryObject(directoryId, name, null, null);
    }

    private void resetOwnerDirectoryObject(String directoryId, String ownerId) {
        this.updateDirectoryObject(directoryId, null, ownerId, null);
    }

    private void resetParentDirectoryObject(String directoryId, String parentId) {
        this.updateDirectoryObject(directoryId, null, null, parentId);
    }

    private void getDirectoryObject(String directoryId) {
        ApplicationObject.getInstance().addRequest(new GetDirectoryObjectRequest(directoryId,
                new Response.Listener<JSONObject>() {
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getBoolean("success")) {
                                Log.d("GetDirectorySuccess", response.toString());

                                JSONObject content = response.getJSONObject("content");
                                JSONObject owner = content.getJSONObject("owner");
                                String parent = (content.getJSONObject("owner") != null) ? content.getJSONObject("owner").getString("_id") : null;

                                content.getString("_id");
                                owner.getString("_id");
                                owner.getString("first_name");
                                owner.getString("last_name");
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
                                JSONObject owner = content.getJSONObject("owner");
                                String parent = (content.getJSONObject("owner") != null) ? content.getJSONObject("owner").getString("_id") : null;

                                directoryObjects.add(new DirectoryObject(
                                        content.getString("_id"),
                                        owner.getString("_id"),
                                        owner.getString("first_name"),
                                        owner.getString("last_name"),
                                        parent,
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

    private void updateDirectoryObject(String directoryId, String name, String ownerId, String parentId) {
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

                                    if (directoryObject.getId() == response.getString("folder_id")) {
                                        directoryObject.setName(content.getString("name"));
                                        directoryObject.setOwnerId(content.getString("owner_id"));
                                        directoryObject.setParentId(content.getString("parent_id"));
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

    private void deleteDirectoryObject(String directoryId) {
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

                                        if (directoryObject.getId() == response.getString("folder_id")) {
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

    private void getDirectoryContent(String folder_id) {
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

                                JSONArray content = response.getJSONArray("content");
                                for (int i = 0; i < content.length(); i++) {
                                    JSONObject dirObj = content.getJSONObject(i);
                                    JSONObject owner = dirObj.getJSONObject("owner");
                                    String parent = (dirObj.getJSONObject("owner") != null) ? dirObj.getJSONObject("owner").getString("_id") : null;
                                    directoryObjects.add(new DirectoryObject(
                                            dirObj.getString("_id"),
                                            owner.getString("_id"),
                                            owner.getString("first_name"),
                                            owner.getString("last_name"),
                                            parent,
                                            dirObj.getString("name"),
                                            dirObj.getBoolean("folder"),
                                            dirObj.getInt("time_created"),
                                            dirObj.getInt("time_updated"),
                                            dirObj.getInt("time_deleted"))
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
        public View getView(final int position, View view, ViewGroup parent) {
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
            final ImageView settings_btn = (ImageView) view.findViewById(R.id.settings_directory_object);
            settings_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(getActivity(), settings_btn);
                    popup.getMenuInflater().inflate(R.menu.menu_directory_object, popup.getMenu());
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.rename:
                                    renameDirectoryObjectDialog(directoryObjects.get(position));
                                    break;

                                case R.id.share:
                                    //shareDirectoryObject(directoryObjects.get(position));
                                    break;

                                case R.id.delete:
                                    deleteDirectoryObjectDialog(directoryObjects.get(position));
                                    break;
                            }

                            return false;
                        }
                    });
                    popup.show();
                }
            });


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