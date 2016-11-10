package com.noxyspace.vinca;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DirectoryObject {
    public enum ObjectType {
        File, Folder
    }

    private String title, ownerName, iconName;
    private int ID, ownerID;
    private boolean shared;
    private ObjectType type;
    private String date;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy 'at' HH.mm");

    public DirectoryObject(int ID, String title, ObjectType type, String ownerName, int ownerID, boolean shared) {
        this.ID = ID;
        this.title = title;
        this.ownerName = ownerName;
        this.ownerID = ownerID;
        this.shared = shared;
        this.type = type;
        date = dateFormat.format(new Date());
    }

    public String getTitle() {
        return title;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getIconName() {
        return iconName;
    }

    public int getID() {
        return ID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public boolean isShared() {
        return shared;
    }

    public ObjectType getType() {
        return type;
    }

    public String getDate() {
        return date;
    }
}
