package com.noxyspace.vinca.requests.Directory;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.noxyspace.vinca.requests.CustomRequest;
import com.noxyspace.vinca.requests.HttpParameter;

import org.json.JSONObject;

public class CreateDirectoryObjectRequest extends CustomRequest {
    public CreateDirectoryObjectRequest(String name, String parent_id, String folder, Response.Listener<JSONObject> responseListener) {
        super(Method.POST, DIRECTORY_URL, responseListener,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("LoginRequestError", error.getMessage());
                    }
                },
                new HttpParameter("name", name),
                new HttpParameter("parent_id", parent_id),
                new HttpParameter("folder", folder)
        );
    }
}