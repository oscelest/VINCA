package com.noxyspace.vinca.objects;

import com.android.volley.*;
import com.android.volley.toolbox.*;

import com.noxyspace.vinca.requests.*;

import java.io.File;

public final class ApplicationObject {
    private static ApplicationObject singleton = new ApplicationObject();

    public static ApplicationObject getInstance() {
        return singleton;
    }

    private RequestQueue requestQueue;

    private UserObject user;
    private String user_token;
    private DirectoryObject current_file;

    private int currentFolderId;

    private ApplicationObject() {
        this.user = null;
        this.user_token = null;
        this.current_file = null;

        this.currentFolderId = 0;

        this.requestQueue = null;
    }

    public void addRequest(HttpRequest request) {
        if (this.requestQueue != null) {
            this.requestQueue.add(request);
        }
    }

    public void initializeRequestQueue(File cacheDir) {
        if (this.requestQueue == null) {
            Cache cache = new DiskBasedCache(cacheDir, 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());

            this.requestQueue = new RequestQueue(cache, network);
            this.requestQueue.start();
        }
    }

    public UserObject getUser() {
        return this.user;
    }

    public void setUser(UserObject newUser) {
        this.user = newUser;
    }

    public String getUserToken() {
        return this.user_token;
    }

    public void setUserToken(String token) {
        this.user_token = token;
    }

    public DirectoryObject getCurrentFile() {
        return this.current_file;
    }

    public void setCurrentFile(DirectoryObject current_file) {
        this.current_file = current_file;
    }

    public int getCurrentFolderId() {
        return this.currentFolderId;
    }

    public void setCurrentFolderId(int directoryId) {
        this.currentFolderId = directoryId;
    }
}
