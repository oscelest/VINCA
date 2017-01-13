package com.noxyspace.vinca.objects;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DirectoryObject {
    private String id;

    private String owner_id, owner_first_name, owner_last_name;

    private String parent_id, name;

    private boolean folder;
    private boolean shared;

    private String time_created, time_updated, time_deleted;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    public DirectoryObject(String id, String owner_id, String owner_first_name, String owner_last_name, String parent_id, String name, boolean folder, long time_created, long time_updated, long time_deleted) {
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

    public String getId() {
        return this.id;
    }

    public String getOwnerId() {
        return this.owner_id;
    }

    public void setOwnerId(String ownerId) {
        this.owner_id = ownerId;
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

    public String getParentId() {
        return this.parent_id;
    }

    public void setParentId(String parentId) {
        this.parent_id = parentId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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
