package com.noxyspace.vinca;

import com.noxyspace.vinca.Objects.DirectoryObject;

public class Folder extends DirectoryObject {

    public Folder(int ID, String title, String ownerName, int ownerID, boolean shared) {
        super(ID, title, ObjectType.Folder, ownerName, ownerID, shared);
    }
}
