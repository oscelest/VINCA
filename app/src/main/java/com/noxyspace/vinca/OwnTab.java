package com.noxyspace.vinca;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class OwnTab extends ListFragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private ArrayList<DirectoryObject> directoryObjects = new ArrayList<DirectoryObject>();
    private int fileID = 0;
    private int folderID = 0;


    private ArrayList<FolderItem> folderItems = new ArrayList<FolderItem>();
    private ArrayList<FolderItem> fileItems = new ArrayList<FolderItem>();
    private CustomAdapter adapter;
    private int folderNo = 1;
    private FloatingActionButton fab_plus, fab_folder, fab_file;
    private boolean fab_plus_toggled = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.own_tab_fragment, container, false);

        directoryObjects.add(new Folder(folderID++, "Title 1", "Rune1", 1, false));
        directoryObjects.add(new File(fileID++, "File 1", "Rune", 1, false, (int)(System.currentTimeMillis() / 1000)));
        directoryObjects.add(new Folder(folderID++, "Title 2", "Rune2", 2, false));
        directoryObjects.add(new File(fileID++, "File 1", "Rune", 1, false, (int)(System.currentTimeMillis() / 1000)));

        Collections.sort(directoryObjects, new Comparator<DirectoryObject>() {
            @Override
            public int compare(DirectoryObject dirObject1, DirectoryObject dirObject2)
            {
                if(dirObject1 instanceof Folder && dirObject2 instanceof File)
                {
                    return -1;
                }
                else if(dirObject1 instanceof File && dirObject2 instanceof Folder)
                {
                    return 1;
                }

                return 0;
            }
        });


//        file1 = new Leaf("File 1", "Rune1");
//        file2 = new Leaf("File 2", "Rune2");
//        file3 = new Leaf("File 3", "Rune3");
//        file4 = new Leaf("File 4", "Rune4");
//        file5 = new Leaf("File 5", "Rune5");
//        file6 = new Leaf("File 6", "Rune6");
//        files1 = new ArrayList<Leaf>();
//        files1.add(file1);
//        files1.add(file2);
//        files1.add(file3);
//
//        files2.add(file4);
//        files2.add(file5);
//
//        files3.add(file6);
//
//
//        folder2 = new Node(new ArrayList(), files2, "Folder 2", "Editor2");
//        folder3 = new Node(new ArrayList(), files3, "Folder 3", "Editor3");
//
//        folder1 = new Node(dirs1, files1, "Folder 1", "Editor1");
//
//        dirs2 = new ArrayList<Node>();
//        dirs2.add(new Node(new ArrayList(), files2, "Dirs 2", "Rune2"));
//
//        dirs1 = new ArrayList<Node>();
//        dirs1.add(folder2);
//        dirs1.add((folder3));




        folderItems.add(new FolderItem("Folder 1", "Rune "));
        folderItems.add(new FolderItem("Folder 2", "Magnus "));
        folderItems.add(new FolderItem("Folder 3", "Andreas "));

        fileItems.add(new FolderItem("Folder 1", "Oliver "));
        fileItems.add(new FolderItem("Folder 2", "Mikkel "));
        fileItems.add(new FolderItem("Folder 3", "Valdemar "));

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
        if (directoryObjects.get(position).getType() == DirectoryObject.ObjectType.File) {
            Toast.makeText(getActivity(), "File: " + position, Toast.LENGTH_SHORT).show();
        } else if (directoryObjects.get(position).getType() == DirectoryObject.ObjectType.Folder) {
            Toast.makeText(getActivity(), "Folder: " + position, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {
        if (v == fab_plus && !fab_plus_toggled){
            fab_folder.setVisibility(View.VISIBLE);
            fab_file.setVisibility(View.VISIBLE);
            fab_plus_toggled = true;
        }else {
            fab_folder.setVisibility(View.INVISIBLE);
            fab_file.setVisibility(View.INVISIBLE);
            fab_plus_toggled = false;
        }
        if (v == fab_folder){
            createFolderDialog();

            fab_plus_toggled = false;
        } if (v == fab_file) {
            createFileDialog();
            fab_plus_toggled = false;
        }
        adapter.notifyDataSetChanged();
    }

    // Dialog that asks for the title of a file or folder and creates the element
    public void createFileDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.fragment_create_folder_dialog, null));
            builder.setTitle("Filens navn:");

//          Set up the input
        final EditText fileTitle = new EditText(getActivity());
        fileTitle.setMaxLines(1);
//          Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        builder.setView(fileTitle);

//          Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            // Creates a dialog box that asks for the new folders name
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String s = fileTitle.getText().toString();
                directoryObjects.add(new File(fileID++, s, "Rune", 1, false, (int)(System.currentTimeMillis() / 1000)));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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
        builder.setTitle("Filens navn:");

//          Set up the input
        final EditText folderTitle = new EditText(getActivity());
        folderTitle.setMaxLines(1);
//          Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        builder.setView(folderTitle);

//          Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            // Creates a dialog box that asks for the new folders name
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String s = folderTitle.getText().toString();
                directoryObjects.add(new Folder(fileID++, s, "Rune", 1, false));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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
            if (position < directoryObjects.size()) {
                return 0;
            } else {
                return 1;
            }
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (view == null) {
                if (directoryObjects.get(position).getType() == DirectoryObject.ObjectType.Folder) {
                    view = getActivity().getLayoutInflater().inflate(R.layout.folder_list_item, null);
                } else {
                    view = getActivity().getLayoutInflater().inflate(R.layout.file_list_item, null);
                }
            }

            if (directoryObjects.get(position).getType() == DirectoryObject.ObjectType.Folder) {
                ImageView img = (ImageView) view.findViewById(R.id.icon);
                TextView folderName = (TextView) view.findViewById(R.id.projectTitle);
                folderName.setText(directoryObjects.get(position).getTitle());
                TextView editor = (TextView) view.findViewById(R.id.lastEdit);
                editor.setText(directoryObjects.get(position).getOwnerName() + " (" + folderItems.get(position).getUpdatedAt() + ")");
            } else {
                ImageView img = (ImageView) view.findViewById(R.id.icon);
                TextView fileName = (TextView) view.findViewById(R.id.projectTitle);
                fileName.setText(directoryObjects.get(position).getTitle());
                TextView editor = (TextView) view.findViewById(R.id.lastEdit);
                editor.setText(directoryObjects.get(position).getOwnerName());
            }
            return view;
        }
    }
}