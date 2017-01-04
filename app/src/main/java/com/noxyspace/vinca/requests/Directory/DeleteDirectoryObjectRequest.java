package com.noxyspace.vinca.requests.Directory;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.noxyspace.vinca.requests.CustomRequest;

import org.json.JSONObject;

public class DeleteDirectoryObjectRequest extends CustomRequest {
    public DeleteDirectoryObjectRequest(String folder_id, Response.Listener<JSONObject> responseListener) {
        super(Method.DELETE, DIRECTORY_URL + "/" + folder_id, responseListener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("GetDirContentErr", error.getMessage());
                    }
                }
        );
    }
}