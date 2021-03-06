package com.noxyspace.vinca.requests.directory;

import com.android.volley.*;
import com.android.volley.Response;

import com.noxyspace.vinca.objects.DirectoryObject;
import com.noxyspace.vinca.requests.*;

import android.util.Log;
import org.json.JSONObject;

public class UpdateDirectoryObjectRequest extends HttpRequest {
    public UpdateDirectoryObjectRequest(String directoryId, String name, String data, String ownerId, String parentId, Response.Listener<JSONObject> responseListener) {
        super(Method.PUT, DIRECTORY_URL + "/" + directoryId, responseListener,
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("UpdateDirectoryError", error.getMessage() != null ? error.getMessage() : "There was an error.");
                }
            },
            new HttpParameter("name", name == null ? "" : name),
            new HttpParameter("data", data == null ? "" : data),
            new HttpParameter("owner_id", ownerId == null ? "" : ownerId),
            new HttpParameter("parent_id", parentId == null ? "" : parentId)
        );
    }
}
