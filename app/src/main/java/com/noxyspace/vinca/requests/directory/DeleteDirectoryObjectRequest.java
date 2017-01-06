package com.noxyspace.vinca.requests.directory;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.noxyspace.vinca.requests.*;

import android.util.Log;
import org.json.JSONObject;

public class DeleteDirectoryObjectRequest extends HttpRequest {
    public DeleteDirectoryObjectRequest(int folder_id, Response.Listener<JSONObject> responseListener) {
        super(Method.DELETE, DIRECTORY_URL + "/" + folder_id, responseListener,
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("DeleteDirectoryError", error.getMessage() != null ? error.getMessage() : "There was an error.");
                }
            }
        );
    }
}
