package com.noxyspace.vinca.objects;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DirectoryObject {
    private int id;

    private int owner_id;
    private String owner_first_name;
    private String owner_last_name;

    private int parent_id;
    private String name;

    private boolean folder;
    private boolean shared;

    private String time_created;
    private String time_updated;
    private String time_deleted;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    public DirectoryObject(int id, int owner_id, String owner_first_name, String owner_last_name, int parent_id, String name, boolean folder, int time_created, int time_updated, int time_deleted) {
        this.id = id;

        this.owner_id = owner_id;
        this.owner_first_name = owner_first_name;
        this.owner_last_name = owner_last_name;

        this.parent_id = parent_id;
        this.name = name;

        this.folder = folder;
        this.shared = false;

        this.time_created = dateFormat.format(new Date(time_created));
        this.time_updated = dateFormat.format(new Date(time_updated));
        this.time_deleted = dateFormat.format(new Date(time_deleted));
    }

    public int getId() {
        return this.id;
    }

    public int getOwnerId() {
        return this.owner_id;
    }

    public String getOwnerFirstName() {
        return this.owner_first_name;
    }

    public String getOwnerLastName() {
        return this.owner_last_name;
    }

    public String getOwnerFullName() {
        return (this.owner_first_name + " " + this.owner_last_name);
    }

    public int getParentId() {
        return this.parent_id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isFolder() {
        return this.folder;
    }

    public boolean isShared() {
        return this.shared;
    }

    public String getCreatedTime() {
        return this.time_created;
    }

    public String getUpdatedTime() {
        return this.time_updated;
    }

    public String getDeletedTime() {
        return this.time_deleted;
    }
}
