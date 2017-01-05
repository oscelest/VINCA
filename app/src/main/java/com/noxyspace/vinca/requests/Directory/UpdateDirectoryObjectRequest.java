package com.noxyspace.vinca.requests.directory;

import com.android.volley.*;
import com.android.volley.Response;

import com.noxyspace.vinca.objects.DirectoryObject;
import com.noxyspace.vinca.requests.*;

import android.util.Log;
import org.json.JSONObject;

public class UpdateDirectoryObjectRequest extends HttpRequest {
    public UpdateDirectoryObjectRequest(DirectoryObject directoryObject, Response.Listener<JSONObject> responseListener) {
        super(Method.GET, DIRECTORY_URL + "/" + directoryObject.getID(), responseListener,
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("UpdateDirectoryError", error.getMessage() != null ? error.getMessage() : "There was an error.");
                }
            },
            new HttpParameter("name", directoryObject.getName()),
            new HttpParameter("owner_id", Integer.toString(directoryObject.getOwnerID())),
            new HttpParameter("parent_id", Integer.toString(directoryObject.getParentId())),
            new HttpParameter("data", directoryObject.getData())
        );
    }
}