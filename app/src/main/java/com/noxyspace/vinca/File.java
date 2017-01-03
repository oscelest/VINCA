package com.noxyspace.vinca;

import java.text.SimpleDateFormat;
import java.util.Date;

public class File extends DirectoryObject {

    private Date lastUpdated;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy 'at' HH.mm");

    public File(int ID, String title, String ownerName, int ownerID, boolean shared, int unixTimestamp) {
        super(ID, title, ObjectType.File, ownerName, ownerID, shared);
        lastUpdated = new Date((long)unixTimestamp * 1000);
    }

    public String getLastUpdatedString() {
        return dateFormat.format(lastUpdated);
    }

    public void onClick(){

    }
}