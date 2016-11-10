package com.noxyspace.vinca;

public class Folder extends DirectoryObject {

    public Folder(int ID, String title, String ownerName, int ownerID, boolean shared) {
        super(ID, title, ObjectType.Folder, ownerName, ownerID, shared);
    }
}
