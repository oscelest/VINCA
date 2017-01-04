package com.noxyspace.vinca.objects;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DirectoryObject {

    private String name, owner_name, icon_name;
    private int id, owner_id, parent_id;
    private boolean shared;
    private boolean folder;
    private String created;
    private String updated;
    private String data;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    public DirectoryObject(int id, int owner_id, int parent_id, String name, String owner_name, String data, boolean folder, int created, int updated) {
        this.id = id;
        this.name = name;
        this.data = data;
        this.owner_name = owner_name;
        this.owner_id = owner_id;
        this.parent_id = parent_id;
        this.shared = false;
        this.folder = folder;
        this.created = dateFormat.format(new Date(created));
        this.updated = dateFormat.format(new Date(updated));
    }

    public int getID() {
        return id;
    }

    public int getOwnerID() {
        return owner_id;
    }

    public int getParentId() {
        return parent_id;
    }

    public String getOwnerName() {
        return owner_name;
    }

    public String getName() {
        return name;
    }

    public String getIconName() {
        return icon_name;
    }

    public boolean isShared() {
        return shared;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isFolder() {
        return folder;
    }

    public String getCreatedDate() {
        return created;
    }

    public String getUpdatedDate() {
        return updated;
    }
}
