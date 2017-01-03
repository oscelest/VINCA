package com.noxyspace.vinca.Objects;

import java.util.List;

public final class ApplicationObject {

    private static UserObject user;
    private static List<DirectoryObject> directory;

    private ApplicationObject(){

    }

    public static UserObject getUser() {
        return user;
    }

    public static void setUser(UserObject u) {
        user = u;
    }

    public static List<DirectoryObject> getDirectory() {
        return directory;
    }

    public static void setDirectory(List<DirectoryObject> dirlist) {
        directory = dirlist;
    }

}
