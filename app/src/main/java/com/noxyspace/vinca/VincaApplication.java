package com.noxyspace.vinca;

import android.app.Application;

import com.noxyspace.vinca.objects.ApplicationObject;

public class VincaApplication extends Application {
    public void onCreate() {
        ApplicationObject.getInstance().initializeRequestQueue(this.getCacheDir());
    }
}