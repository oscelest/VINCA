package com.noxyspace.vinca.objects;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DirectoryObject {

    private String title, owner_name, icon_name;
    private int id, owner_id;
    private boolean shared;
    private boolean folder;
    private String created;
    private String updated;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    public DirectoryObject(int id, String title, String owner_name, int owner_id, boolean folder, int created, int updated) {
        this.id = id;
        this.title = title;
        this.owner_name = owner_name;
        this.owner_id = owner_id;
        this.shared = false;
        this.created = dateFormat.format(new Date(created));
        this.updated = dateFormat.format(new Date(updated));
    }

    public int getID() {
        return id;
    }

    public int getOwnerID() {
        return owner_id;
    }

    public String getOwnerName() {
        return owner_name;
    }

    public String getTitle() {
        return title;
    }

    public String getIconName() {
        return icon_name;
    }

    public boolean isShared() {
        return shared;
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
