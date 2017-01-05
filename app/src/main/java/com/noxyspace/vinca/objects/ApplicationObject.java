package com.noxyspace.vinca.objects;

import com.android.volley.*;
import com.android.volley.toolbox.*;

import com.noxyspace.vinca.requests.*;

import java.io.File;
import java.util.Collections;
import java.util.List;

public final class ApplicationObject {
    private static ApplicationObject singleton = new ApplicationObject();

    public static ApplicationObject getInstance() {
        return singleton;
    }

    private UserObject user;
    private String user_token;
    private List<DirectoryObject> directoryList;

    private RequestQueue requestQueue;

    private ApplicationObject() {
        this.user = null;
        this.user_token = null;
        this.directoryList = Collections.emptyList();

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

    public List<DirectoryObject> getDirectoryList() {
        return this.directoryList;
    }

    public void setDirectoryList(List<DirectoryObject> newDirectoryList) {
        this.directoryList = newDirectoryList;
    }
}
