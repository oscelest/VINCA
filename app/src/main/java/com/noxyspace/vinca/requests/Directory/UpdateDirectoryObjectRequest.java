package com.noxyspace.vinca.requests.Directory;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.noxyspace.vinca.objects.DirectoryObject;
import com.noxyspace.vinca.requests.CustomRequest;
import com.noxyspace.vinca.requests.HttpParameter;

import org.json.JSONObject;

public class UpdateDirectoryObjectRequest extends CustomRequest {
    public UpdateDirectoryObjectRequest(DirectoryObject dirobj, Response.Listener<JSONObject> responseListener) {
        super(Method.GET, DIRECTORY_URL + "/" + dirobj.getID(), responseListener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("GetDirContentErr", error.getMessage());
                    }
                },
                new HttpParameter("name", dirobj.getName()),
                new HttpParameter("owner_id", Integer.toString(dirobj.getOwnerID())),
                new HttpParameter("parent_id", Integer.toString(dirobj.getParentId())),
                new HttpParameter("data", dirobj.getData())
        );
    }
}