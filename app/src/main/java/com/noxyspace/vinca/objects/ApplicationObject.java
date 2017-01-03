package com.noxyspace.vinca.objects;

import android.content.Context;

import com.android.volley.*;
import com.android.volley.toolbox.*;

import com.noxyspace.vinca.requests.CustomRequest;


import java.util.List;

public final class ApplicationObject {
    private static ApplicationObject singleton = new ApplicationObject();

    public static ApplicationObject getInstance() {
        return singleton;
    }

    private static UserObject user;
    private static List<DirectoryObject> directory;

//    private RequestQueue mRequestQueue;
//    private Context mContext; ILLEGAL AND WILL CRASH

//    private ApplicationObject() {
//        this.mRequestQueue = null;
//    }

//    public void addRequest(CustomRequest request) {
//        this.mRequestQueue.add(request);
//    }

//    public void initializeRequestQueue(Context context) {
//        if (this.mRequestQueue == null) {
//            this.mContext = context;
//
//            Cache cache = new DiskBasedCache(mContext.getCacheDir(), 1024 * 1024);
//            Network network = new BasicNetwork(new HurlStack());
//
//            this.mRequestQueue = new RequestQueue(cache, network);
//            this.mRequestQueue.start();
//        }
//    }

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
