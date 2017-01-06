package com.noxyspace.vinca.requests.directory;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.noxyspace.vinca.requests.*;

import android.util.Log;
import org.json.JSONObject;

public class CreateDirectoryObjectRequest extends HttpRequest {
    public CreateDirectoryObjectRequest(String name, int parent_id, boolean folder, Response.Listener<JSONObject> responseListener) {
        super(Method.POST, DIRECTORY_URL, responseListener,
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("CreateDirectoryError", error.getMessage() != null ? error.getMessage() : "There was an error.");
                }
            },
            new HttpParameter("name", name),
            new HttpParameter("parent_id", Integer.toString(parent_id)),
            new HttpParameter("folder", Integer.toString(folder ? 1 : 0))
        );
    }
}
