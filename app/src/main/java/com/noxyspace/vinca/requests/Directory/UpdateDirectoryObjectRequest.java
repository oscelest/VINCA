package com.noxyspace.vinca.requests.Directory;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.noxyspace.vinca.requests.CustomRequest;

import org.json.JSONObject;

public class UpdateDirectoryObjectRequest extends CustomRequest {
    public UpdateDirectoryObjectRequest(String folder_id, Response.Listener<JSONObject> responseListener) {
        super(Method.GET, DIRECTORY_URL + "/content/"+folder_id, responseListener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("GetDirContentErr", error.getMessage());
                    }
                }
        );
    }
}